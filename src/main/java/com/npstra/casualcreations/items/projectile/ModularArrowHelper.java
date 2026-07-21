package com.npstra.casualcreations.items.projectile;

import com.npstra.casualcreations.materials.projectile.ArrowHeadMaterial;
import com.npstra.casualcreations.materials.projectile.ArrowMaterialRegistry;
import com.npstra.casualcreations.materials.projectile.ArrowShaftMaterial;
import com.npstra.casualcreations.materials.projectile.ArrowTraitRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import java.util.List;

public class ModularArrowHelper {
    public static float getBaseDamage(ItemStack stack) {
        String headName = getHead(stack);
        String shaftName = getShaft(stack);
        if (headName == null || shaftName == null) return 2.0f;
        ArrowHeadMaterial head = ArrowMaterialRegistry.getHead(headName);
        ArrowShaftMaterial shaft = ArrowMaterialRegistry.getShaft(shaftName);
        if (head == null || shaft == null) return 2.0f;

        float base = head.getBaseDamage();
        float multi = shaft.getDamageMultiplier();

        ArrowTraitRegistry headTrait = getTrait(head.getTrait());
        ArrowTraitRegistry shaftTrait = getTrait(shaft.getTrait());

        if (headTrait != null) base += headTrait.getDamageBonus();
        if (shaftTrait != null) multi += shaftTrait.getDamageMultiplierBonus();

        return base * multi;
    }

    public static double getFinalDamage(ItemStack stack, float hitSpeed) {
        return Math.ceil(getBaseDamage(stack) * Math.max(0.0f, hitSpeed));
    }

    public static float getFinalSpeed(ItemStack stack) {
        String headName = getHead(stack);
        String shaftName = getShaft(stack);
        if (headName == null || shaftName == null) return 3.0f;
        ArrowHeadMaterial head = ArrowMaterialRegistry.getHead(headName);
        ArrowShaftMaterial shaft = ArrowMaterialRegistry.getShaft(shaftName);
        if (head == null || shaft == null) return 3.0f;

        float speed = head.getBaseSpeed();
        float multi = shaft.getSpeedMultiplier();

        ArrowTraitRegistry headTrait = getTrait(head.getTrait());
        ArrowTraitRegistry shaftTrait = getTrait(shaft.getTrait());

        if (headTrait != null) {
            speed += headTrait.getSpeedBonus();
            if (headTrait == ArrowTraitRegistry.HEAVY) {
                speed -= 0.3f;
            }
        }
        if (shaftTrait != null) {
            multi += shaftTrait.getSpeedMultiplierBonus();
        }

        return Math.max(0.0f, speed * multi);
    }

    public static float getRecoveryRate(ItemStack stack) {
        String headName = getHead(stack);
        String shaftName = getShaft(stack);
        if (headName == null || shaftName == null) return 0.0f;
        ArrowHeadMaterial head = ArrowMaterialRegistry.getHead(headName);
        ArrowShaftMaterial shaft = ArrowMaterialRegistry.getShaft(shaftName);
        if (head == null || shaft == null) return 0.0f;

        float rate = head.getRecoveryRate();

        ArrowTraitRegistry headTrait = getTrait(head.getTrait());
        ArrowTraitRegistry shaftTrait = getTrait(shaft.getTrait());

        if (headTrait != null) rate += headTrait.getRecoveryBonus();
        if (shaftTrait != null) rate += shaftTrait.getRecoveryBonus();

        return Math.max(0.0f, Math.min(1.0f, rate));
    }

    public static boolean isRenew(ItemStack stack) {
        String headName = getHead(stack);
        if (headName == null) return false;
        ArrowHeadMaterial head = ArrowMaterialRegistry.getHead(headName);
        if (head == null) return false;
        ArrowTraitRegistry t = getTrait(head.getTrait());
        return t != null && t.isRenew();
    }

    public static int getArrowHeadColor(ItemStack stack) {
        String headName = getHead(stack);
        if (headName == null) return 0xFFFFFF;
        ArrowHeadMaterial head = ArrowMaterialRegistry.getHead(headName);
        return head != null ? head.getColor() : 0xFFFFFF;
    }

    public static int getArrowShaftColor(ItemStack stack) {
        String shaftName = getShaft(stack);
        if (shaftName == null) return 0xFFFFFF;
        ArrowShaftMaterial shaft = ArrowMaterialRegistry.getShaft(shaftName);
        return shaft != null ? shaft.getColor() : 0xFFFFFF;
    }

    public static void addTraitLines(ItemStack stack, List<String> tooltip) {
        String headName = getHead(stack);
        String shaftName = getShaft(stack);
        if (headName == null || shaftName == null) return;
        ArrowHeadMaterial head = ArrowMaterialRegistry.getHead(headName);
        ArrowShaftMaterial shaft = ArrowMaterialRegistry.getShaft(shaftName);
        if (head == null || shaft == null) return;

        String headTrait = head.getTrait();
        String shaftTrait = shaft.getTrait();

        if (headTrait != null && !headTrait.isEmpty() && headTrait.equals(shaftTrait)) {
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.trait." + headTrait) + " II");
        } else {
            if (headTrait != null && !headTrait.isEmpty()) {
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.trait." + headTrait) + " I");
            }
            if (shaftTrait != null && !shaftTrait.isEmpty()) {
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.trait." + shaftTrait) + " I");
            }
        }
    }

    private static String getHead(ItemStack stack) {
        if (stack.getItem() instanceof IModularArrow) {
            return ((IModularArrow) stack.getItem()).getHeadMaterial(stack);
        }
        return null;
    }

    private static String getShaft(ItemStack stack) {
        if (stack.getItem() instanceof IModularArrow) {
            return ((IModularArrow) stack.getItem()).getShaftMaterial(stack);
        }
        return null;
    }

    private static ArrowTraitRegistry getTrait(String id) {
        if (id == null || id.isEmpty()) return null;
        return ArrowTraitRegistry.fromId(id);
    }
}