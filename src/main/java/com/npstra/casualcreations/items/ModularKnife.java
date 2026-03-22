package com.npstra.casualcreations.items;

import com.google.common.collect.Multimap;
import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.materials.HeadMaterial;
import com.npstra.casualcreations.materials.MaterialRegistry;
import com.npstra.casualcreations.materials.RodMaterial;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

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
            String headName = net.minecraft.util.text.translation.I18n.translateToLocal("casualcreations.material." + head);
            if (!headName.isEmpty()) {
                return headName + net.minecraft.util.text.translation.I18n.translateToLocal(this.getTranslationKey() + ".name");
            }
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
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return 1.5f;

        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return 1.5f;

        float base = 1.5f;
        float headBonus = head.getAttackDamage();
        float toolFactor = 0.8f;
        float rodMult = rod.getDamageMultiplier();

        return (base + headBonus * toolFactor) * rodMult;
    }

    private float calculateAttackSpeed(ItemStack stack) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return 2.0f;

        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return 2.0f;

        float base = 2.0f;
        float headBonus = head.getAttackSpeed();
        float rodMult = rod.getAttackSpeedMultiplier();

        return (base + headBonus) * rodMult;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return 5;

        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return 5;

        int base = 5;
        int headBonus = head.getDurability();
        float rodMult = rod.getDurabilityMultiplier();

        return (int) ((base + headBonus) * rodMult);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return 0;

        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return 0;

        int base = head.getEnchantability();
        float mult = rod.getEnchantabilityMultiplier();
        return (int) (base * mult);
    }
}