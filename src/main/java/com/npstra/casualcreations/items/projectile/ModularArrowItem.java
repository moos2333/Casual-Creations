package com.npstra.casualcreations.items.projectile;

import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.entities.EntityModularArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import java.util.List;

public class ModularArrowItem extends ItemArrow implements IModularArrow {
    public ModularArrowItem() {
        setTranslationKey(CasualCreations.MODID + ".arrow");
        setRegistryName("arrow");
        setMaxStackSize(64);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String head = getHeadMaterial(stack);
        if (head != null) {
            String headName = I18n.translateToLocal("casualcreations.material." + head);
            String toolName = I18n.translateToLocal(this.getTranslationKey() + ".name");
            return headName + toolName;
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public EntityArrow createArrow(World world, ItemStack stack, EntityLivingBase shooter) {
        EntityModularArrow arrow = new EntityModularArrow(world, shooter);
        String head = getHeadMaterial(stack);
        String shaft = getShaftMaterial(stack);
        if (head != null) arrow.setHeadMaterial(head);
        if (shaft != null) arrow.setShaftMaterial(shaft);

        float baseDamage = ModularArrowHelper.getBaseDamage(stack);
        arrow.setDamage(baseDamage);

        float speed = ModularArrowHelper.getFinalSpeed(stack);
        arrow.setCustomVelocity(speed);

        return arrow;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.EntityPlayer player) {
        if (!ModularArrowHelper.isRenew(stack)) return false;
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(
                net.minecraft.init.Enchantments.INFINITY, bow);
        return enchant > 0;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        ModularArrowHelper.addTraitLines(stack, tooltip);
        float damage = (float) ModularArrowHelper.getFinalDamage(stack, 3.0f);
        float speed = ModularArrowHelper.getFinalSpeed(stack);
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.tooltip.damage") + ": " + String.format("%.1f", damage));
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.tooltip.speed") + ": " + String.format("%.1f", speed));
    }
}