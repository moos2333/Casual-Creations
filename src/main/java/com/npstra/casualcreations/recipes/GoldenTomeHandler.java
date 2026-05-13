package com.npstra.casualcreations.recipes;

import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.config.ConfigHandler;
import com.npstra.casualcreations.items.ItemGoldenTome;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CasualCreations.MODID)
public class GoldenTomeHandler {

    private static final Set<String> TOOL_MERGE_ENCHANTMENTS = new HashSet<>();

    static {
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:protection");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:fire_protection");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:blast_protection");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:projectile_protection");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:unbreaking");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:smite");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:sharpness");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:bane_of_arthropods");
        TOOL_MERGE_ENCHANTMENTS.add("minecraft:power");
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (ConfigHandler.enableTomeBookMerge && tryMergeBooks(left, right, event)) {
            return;
        }

        ItemStack tool = null;
        ItemStack tome = null;
        if (left.getItem() instanceof ItemGoldenTome && !(right.getItem() instanceof ItemGoldenTome)) {
            tome = left;
            tool = right;
        } else if (right.getItem() instanceof ItemGoldenTome && !(left.getItem() instanceof ItemGoldenTome)) {
            tool = left;
            tome = right;
        }

        if (tool == null || tome == null || tool.isEmpty() || tome.isEmpty()) return;
        if (!tool.getItem().isEnchantable(tool)) return;

        Map<Enchantment, Integer> toolEnchants = EnchantmentHelper.getEnchantments(tool);
        Map<Enchantment, Integer> tomeEnchants = EnchantmentHelper.getEnchantments(tome);

        if (tomeEnchants.isEmpty()) return;

        ItemStack output = tool.copy();
        Map<Enchantment, Integer> outEnchants = EnchantmentHelper.getEnchantments(output);

        int totalCost = 0;
        boolean applied = false;
        int globalLimit = ConfigHandler.maxEnchantmentLevel;

        for (Map.Entry<Enchantment, Integer> entry : tomeEnchants.entrySet()) {
            Enchantment ench = entry.getKey();
            int addLevel = entry.getValue();
            if (addLevel <= 0) continue;

            String enchName = ench.getRegistryName().toString();
            if (!TOOL_MERGE_ENCHANTMENTS.contains(enchName)) continue;
            if (!ench.canApply(tool)) continue;

            int originalLevel = outEnchants.getOrDefault(ench, 0);
            int effectiveAdd = Math.min(addLevel, globalLimit - originalLevel);
            if (effectiveAdd <= 0) continue;

            int maxLevel = ench.getMaxLevel();

            for (int i = 1; i <= effectiveAdd; i++) {
                int currentStep = originalLevel + i;
                if (currentStep <= maxLevel) {
                    totalCost += 10;
                } else {
                    totalCost += 30;
                }
            }

            outEnchants.put(ench, originalLevel + effectiveAdd);
            applied = true;
        }

        if (!applied) return;

        EnchantmentHelper.setEnchantments(outEnchants, output);
        output.setRepairCost(0);

        event.setOutput(output);
        event.setCost(totalCost);
    }

    private static boolean tryMergeBooks(ItemStack left, ItemStack right, AnvilUpdateEvent event) {
        ItemStack tome = null;
        ItemStack other = null;

        if (left.getItem() instanceof ItemGoldenTome && (right.getItem() instanceof ItemGoldenTome || right.getItem() instanceof ItemEnchantedBook)) {
            tome = left;
            other = right;
        } else if (right.getItem() instanceof ItemGoldenTome && (left.getItem() instanceof ItemGoldenTome || left.getItem() instanceof ItemEnchantedBook)) {
            tome = right;
            other = left;
        }

        if (tome == null || other == null || tome.isEmpty() || other.isEmpty()) return false;

        Map<Enchantment, Integer> tomeEnchants = EnchantmentHelper.getEnchantments(tome);
        Map<Enchantment, Integer> otherEnchants = EnchantmentHelper.getEnchantments(other);

        Set<String> allowed = new HashSet<>();
        for (String id : ConfigHandler.tomeBookMergeEnchantments) {
            allowed.add(id);
        }

        ItemStack output = new ItemStack(tome.getItem());
        Map<Enchantment, Integer> merged = new HashMap<>(tomeEnchants);
        int totalCost = 0;
        int globalLimit = ConfigHandler.maxEnchantmentLevel;

        for (Map.Entry<Enchantment, Integer> entry : otherEnchants.entrySet()) {
            Enchantment ench = entry.getKey();
            int addLevel = entry.getValue();
            if (addLevel <= 0) continue;

            String id = ench.getRegistryName().toString();
            if (!allowed.contains(id)) continue;

            int originalLevel = tomeEnchants.getOrDefault(ench, 0);
            int effectiveAdd = Math.min(addLevel, globalLimit - originalLevel);
            if (effectiveAdd <= 0) continue;

            int maxLevel = ench.getMaxLevel();
            for (int i = 1; i <= effectiveAdd; i++) {
                int currentStep = originalLevel + i;
                if (currentStep <= maxLevel) {
                    totalCost += 10;
                } else {
                    totalCost += 30;
                }
            }

            merged.merge(ench, effectiveAdd, Integer::sum);
        }

        if (merged.isEmpty()) return false;

        EnchantmentHelper.setEnchantments(merged, output);
        output.setRepairCost(0);

        event.setOutput(output);
        event.setCost(other.getItem() instanceof ItemGoldenTome ? 0 : totalCost / 2);
        return true;
    }
}