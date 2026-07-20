package com.npstra.casualcreations.items;

import com.google.common.collect.Multimap;
import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.materials.HeadMaterial;
import com.npstra.casualcreations.materials.RodMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import java.util.List;

public class ModularBattleAxe extends ItemAxe implements IModularTool {
    public ModularBattleAxe() {
        super(ToolMaterial.WOOD);
        setTranslationKey(CasualCreations.MODID + ".battleaxe");
        setRegistryName("battleaxe");
    }

    @Override
    public boolean shouldHideFlags() {
        return false;
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
        if (head == null || rod == null) return 5.0f;
        float base = 5.0f;
        float headBonus = head.getAttackDamage();
        float rodMult = rod.getDamageMultiplier() + 0.3f;
        return (base + headBonus) * rodMult;
    }

    private float calculateAttackSpeed(ItemStack stack) {
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return 1.0f;
        return (1.0f + head.getAttackSpeed()) * rod.getAttackSpeedMultiplier();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return 30;
        int base = 30;
        int headBonus = head.getDurability();
        float rodMult = rod.getDurabilityMultiplier() + 0.3f;
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
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        float original = super.getDestroySpeed(stack, state);
        if (original <= 1.0f) return original;
        HeadMaterial head = ModularToolHelper.getHead(stack);
        RodMaterial rod = ModularToolHelper.getRod(stack);
        if (head == null || rod == null) return original;
        float base = 1.0f;
        float headBonus = head.getMiningSpeed();
        float rodMult = rod.getSpeedMultiplier() - 0.25f;
        return (base + headBonus) * rodMult;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
        if ("axe".equals(toolClass)) return ModularToolHelper.getHarvestLevel(stack, toolClass);
        return -1;
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