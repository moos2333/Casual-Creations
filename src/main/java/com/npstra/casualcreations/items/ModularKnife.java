package com.npstra.casualcreations.items;

import com.google.common.collect.Multimap;
import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.materials.HeadMaterial;
import com.npstra.casualcreations.materials.RodMaterial;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import java.util.List;

public class ModularKnife extends ItemSword implements IModularTool {
    public ModularKnife() {
        super(ToolMaterial.WOOD);
        setTranslationKey(CasualCreations.MODID + ".knife");
        setRegistryName("knife");
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
        if (slot == EntityEquipmentSlot.MAINHAND) {
            modifiers.removeAll("generic.attackDamage");
            modifiers.removeAll("generic.attackSpeed");
            modifiers.put("generic.attackDamage", new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", calculateDamage(stack), 0));
            modifiers.put("generic.attackSpeed", new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", calculateAttackSpeed(stack) - 4.0f, 0));
        }
        return modifiers;
    }

    private float calculateDamage(ItemStack stack) {
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return 1.5f;
        float base = 2.5f;
        float headBonus = head.getAttackDamage() * 0.8f;
        float rodMult = rod.getDamageMultiplier() - 0.2f;
        return (base + headBonus) * rodMult;
    }

    private float calculateAttackSpeed(ItemStack stack) {
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return 2.0f;
        return (2.0f + head.getAttackSpeed()) * rod.getAttackSpeedMultiplier();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return 20;
        int base = 20;
        int headBonus = head.getDurability();
        float rodMult = rod.getDurabilityMultiplier() - 0.2f;
        return (int) ((base + headBonus) * rodMult);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return 0;
        return (int) (head.getEnchantability() * rod.getEnchantabilityMultiplier());
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