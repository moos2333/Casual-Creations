package com.npstra.casualcreations.items;

import com.google.common.collect.Multimap;
import com.npstra.casualcreations.CasualCreations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;
import java.util.List;

public class ModularShovel extends ItemSpade implements IModularTool {
    public ModularShovel() {
        super(ToolMaterial.WOOD);
        setTranslationKey(CasualCreations.MODID + ".shovel");
        setRegistryName("shovel");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String head = getHeadMaterial(stack);
        if (head != null) {
            String name = I18n.translateToLocal("casualcreations.material." + head);
            if (!name.isEmpty()) return name + I18n.translateToLocal(this.getTranslationKey() + ".name");
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        ModularToolHelper.applyAttributeModifiers(modifiers, slot, stack, 1.5f, 1.0f, 0.9f);
        return modifiers;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ModularToolHelper.getCachedDurability(stack, 30);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return ModularToolHelper.getCachedEnchant(stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        float original = super.getDestroySpeed(stack, state);
        if (original <= 1.0f) return original;
        return ModularToolHelper.getDestroySpeed(stack, 1.0f);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
        if ("shovel".equals(toolClass)) return ModularToolHelper.getHarvestLevel(stack, toolClass);
        return -1;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return ModularToolHelper.isRepairable(toRepair, repair);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        ModularToolHelper.addTooltip(stack, tooltip, flag.isAdvanced(), 1.5f, 1.0f, 0.9f);
    }
}