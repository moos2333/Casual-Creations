package com.npstra.casualcreations.items;

import com.google.common.collect.Multimap;
import com.npstra.casualcreations.materials.HeadMaterial;
import com.npstra.casualcreations.materials.MaterialRegistry;
import com.npstra.casualcreations.materials.RodMaterial;
import com.npstra.casualcreations.materials.TraitRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.UUID;

public class ModularToolHelper {
    private static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    private static String getHeadMaterial(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_HEAD) ? tag.getString(IModularTool.TAG_HEAD) : null;
    }

    private static String getRodMaterial(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_ROD) ? tag.getString(IModularTool.TAG_ROD) : null;
    }

    public static HeadMaterial getHead(ItemStack stack) {
        String name = getHeadMaterial(stack);
        return name != null ? MaterialRegistry.getHead(name) : null;
    }

    public static RodMaterial getRod(ItemStack stack) {
        String name = getRodMaterial(stack);
        return name != null ? MaterialRegistry.getRod(name) : null;
    }

    public static void applyTraits(ItemStack stack, String headName, String rodName) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) return;
        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return;

        String headTraitId = head.getTrait();
        String rodTraitId = rod.getTrait();

        float damage = 0f, speed = 0f, mining = 0f;
        int durability = 0, enchant = 0;

        if (headTraitId == null && rodTraitId == null) {
        } else if (headTraitId != null && headTraitId.equals(rodTraitId)) {
            TraitRegistry trait = TraitRegistry.fromId(headTraitId);
            if (trait != null) {
                damage = trait.getDamagePerLevel() * 2;
                speed = trait.getSpeedPerLevel() * 2;
                durability = trait.getDurabilityPerLevel() * 2;
                mining = trait.getMiningPerLevel() * 2;
                enchant = trait.getEnchantPerLevel() * 2;
            }
        } else {
            if (headTraitId != null) {
                TraitRegistry headTrait = TraitRegistry.fromId(headTraitId);
                if (headTrait != null) {
                    damage += headTrait.getDamagePerLevel();
                    speed += headTrait.getSpeedPerLevel();
                    durability += headTrait.getDurabilityPerLevel();
                    mining += headTrait.getMiningPerLevel();
                    enchant += headTrait.getEnchantPerLevel();
                }
            }
            if (rodTraitId != null) {
                TraitRegistry rodTrait = TraitRegistry.fromId(rodTraitId);
                if (rodTrait != null) {
                    damage += rodTrait.getDamagePerLevel();
                    speed += rodTrait.getSpeedPerLevel();
                    durability += rodTrait.getDurabilityPerLevel();
                    mining += rodTrait.getMiningPerLevel();
                    enchant += rodTrait.getEnchantPerLevel();
                }
            }
        }

        tag.setFloat(IModularTool.TAG_TRAIT_DAMAGE, damage);
        tag.setFloat(IModularTool.TAG_TRAIT_SPEED, speed);
        tag.setInteger(IModularTool.TAG_TRAIT_DURABILITY, durability);
        tag.setFloat(IModularTool.TAG_TRAIT_MINING, mining);
        tag.setInteger(IModularTool.TAG_TRAIT_ENCHANT, enchant);
    }

    private static float getTraitDamage(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_TRAIT_DAMAGE) ? tag.getFloat(IModularTool.TAG_TRAIT_DAMAGE) : 0f;
    }

    private static float getTraitSpeed(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_TRAIT_SPEED) ? tag.getFloat(IModularTool.TAG_TRAIT_SPEED) : 0f;
    }

    private static int getTraitDurability(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_TRAIT_DURABILITY) ? tag.getInteger(IModularTool.TAG_TRAIT_DURABILITY) : 0;
    }

    private static float getTraitMining(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_TRAIT_MINING) ? tag.getFloat(IModularTool.TAG_TRAIT_MINING) : 0f;
    }

    private static int getTraitEnchant(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(IModularTool.TAG_TRAIT_ENCHANT) ? tag.getInteger(IModularTool.TAG_TRAIT_ENCHANT) : 0;
    }

    public static float getCachedDamage(ItemStack stack, float baseDamage, float toolFactor) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return baseDamage;
        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return baseDamage;
        float base = (baseDamage + head.getAttackDamage() * toolFactor) * rod.getDamageMultiplier();
        return base + getTraitDamage(stack);
    }

    public static float getCachedSpeed(ItemStack stack, float baseSpeed) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return baseSpeed;
        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return baseSpeed;
        float base = (baseSpeed + head.getAttackSpeed()) * rod.getAttackSpeedMultiplier();
        return base + getTraitSpeed(stack);
    }

    public static int getCachedDurability(ItemStack stack, int baseDurability) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return baseDurability;
        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return baseDurability;
        int base = (int) ((baseDurability + head.getDurability()) * rod.getDurabilityMultiplier());
        return base + getTraitDurability(stack);
    }

    public static int getCachedEnchant(ItemStack stack) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return 0;
        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return 0;
        int base = (int) (head.getEnchantability() * rod.getEnchantabilityMultiplier());
        return base + getTraitEnchant(stack);
    }

    public static float getDestroySpeed(ItemStack stack, float baseSpeed) {
        HeadMaterial head = getHead(stack);
        RodMaterial rod = getRod(stack);
        if (head == null || rod == null) return baseSpeed;
        float base = (baseSpeed + head.getMiningSpeed()) * rod.getSpeedMultiplier();
        return base + getTraitMining(stack);
    }

    public static int getHarvestLevel(ItemStack stack, String toolClass) {
        HeadMaterial head = getHead(stack);
        return head != null ? head.getHarvestLevel() : -1;
    }

    public static void applyAttributeModifiers(Multimap<String, AttributeModifier> modifiers, EntityEquipmentSlot slot, ItemStack stack, float baseDamage, float baseSpeed, float toolFactor) {
        if (slot != EntityEquipmentSlot.MAINHAND) return;
        modifiers.removeAll("generic.attackDamage");
        modifiers.removeAll("generic.attackSpeed");
        float damage = getCachedDamage(stack, baseDamage, toolFactor);
        float speed = getCachedSpeed(stack, baseSpeed);
        modifiers.put("generic.attackDamage", new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
        modifiers.put("generic.attackSpeed", new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speed - 4.0f, 0));
    }

    public static boolean isRepairable(ItemStack tool, ItemStack repair) {
        String headName = getHeadMaterial(tool);
        String rodName = getRodMaterial(tool);
        if (headName == null && rodName == null) return false;

        String repairReg = repair.getItem().getRegistryName().toString();
        if (headName != null) {
            HeadMaterial h = MaterialRegistry.getHead(headName);
            if (h != null && repairReg.equals(h.getItem())) return true;
        }
        if (rodName != null) {
            RodMaterial r = MaterialRegistry.getRod(rodName);
            if (r != null && repairReg.equals(r.getItem())) return true;
        }

        int[] oreIDs = OreDictionary.getOreIDs(repair);
        for (int id : oreIDs) {
            String ore = OreDictionary.getOreName(id);
            if (headName != null) {
                String mapped = MaterialRegistry.getHeadNameByOreDict(ore);
                if (headName.equals(mapped)) return true;
            }
            if (rodName != null) {
                String mapped = MaterialRegistry.getRodNameByOreDict(ore);
                if (rodName.equals(mapped)) return true;
            }
        }
        return false;
    }

    public static void addTooltip(ItemStack stack, List<String> tooltip, boolean advanced, float baseDamage, float baseSpeed, float toolFactor) {
        addTraitLines(stack, tooltip);
        float damage = getCachedDamage(stack, baseDamage, toolFactor) + 1.0f;
        float speed = getCachedSpeed(stack, baseSpeed);
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.tooltip.damage") + ": " + String.format("%.1f", damage));
        tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.tooltip.speed") + ": " + String.format("%.1f", speed));
    }

    public static void addTraitLines(ItemStack stack, List<String> tooltip) {
        String headName = getHeadMaterial(stack);
        String rodName = getRodMaterial(stack);
        if (headName == null || rodName == null) return;
        HeadMaterial head = MaterialRegistry.getHead(headName);
        RodMaterial rod = MaterialRegistry.getRod(rodName);
        if (head == null || rod == null) return;

        String headTrait = head.getTrait();
        String rodTrait = rod.getTrait();

        if (headTrait != null && !headTrait.isEmpty() && rodTrait != null && headTrait.equals(rodTrait)) {
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.trait." + headTrait) + " II");
        } else {
            if (headTrait != null && !headTrait.isEmpty()) {
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.trait." + headTrait) + " I");
            }
            if (rodTrait != null && !rodTrait.isEmpty()) {
                tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("casualcreations.trait." + rodTrait) + " I");
            }
        }
    }
}