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
    private static final float BASE_DAMAGE = 0.0f;
    private static final float BASE_SPEED = 4.0f;
    private static final float TOOL_FACTOR = 0.1f;

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
        if (slot == EntityEquipmentSlot.MAINHAND) {
            modifiers.removeAll("generic.attackDamage");
            modifiers.removeAll("generic.attackSpeed");
            float damage = ModularToolHelper.getCachedDamage(stack, BASE_DAMAGE, TOOL_FACTOR);
            float speed = ModularToolHelper.getCachedSpeed(stack, BASE_SPEED);
            modifiers.put("generic.attackDamage", new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
            modifiers.put("generic.attackSpeed", new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed - 4.0f, 0));
        }
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