package com.npstra.casualcreations.recipes;

import com.google.common.collect.ImmutableSet;
import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.items.ItemGoldenTome;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CasualCreations.MODID)
public class GoldenTomeHandler {

    private static final Set<String> ALLOWED_ENCHANTMENTS = ImmutableSet.of(
            "minecraft:protection",
            "minecraft:fire_protection",
            "minecraft:blast_protection",
            "minecraft:projectile_protection",
            "minecraft:unbreaking",
            "minecraft:smite",
            "minecraft:sharpness",
            "minecraft:bane_of_arthropods",
            "minecraft:power"
    );

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

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

        for (Map.Entry<Enchantment, Integer> entry : tomeEnchants.entrySet()) {
            Enchantment ench = entry.getKey();
            int addLevel = entry.getValue();
            if (addLevel <= 0) continue;

            String enchName = ench.getRegistryName().toString();
            if (!ALLOWED_ENCHANTMENTS.contains(enchName)) continue;
            if (!ench.canApply(tool)) continue;

            int originalLevel = outEnchants.getOrDefault(ench, 0);
            int newLevel = originalLevel + addLevel;
            int maxLevel = ench.getMaxLevel();

            for (int i = 1; i <= addLevel; i++) {
                int currentStep = originalLevel + i;
                if (currentStep <= maxLevel) {
                    totalCost += 10;
                } else {
                    totalCost += 30;
                }
            }

            outEnchants.put(ench, newLevel);
            applied = true;
        }

        if (!applied) return;

        EnchantmentHelper.setEnchantments(outEnchants, output);
        output.setRepairCost(0);

        event.setOutput(output);
        event.setCost(totalCost);
    }
}