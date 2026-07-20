package com.npstra.casualcreations.items;

import com.google.common.collect.Multimap;
import com.npstra.casualcreations.CasualCreations;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import java.util.List;

public class ModularHoe extends ItemHoe implements IModularTool {
    public ModularHoe() {
        super(ToolMaterial.WOOD);
        setTranslationKey(CasualCreations.MODID + ".hoe");
        setRegistryName("hoe");
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
        ModularToolHelper.applyAttributeModifiers(modifiers, slot, stack, 0.0f, 4.0f, 0.1f);
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
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return ModularToolHelper.isRepairable(toRepair, repair);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        ModularToolHelper.addTraitLines(stack, tooltip);
    }
}