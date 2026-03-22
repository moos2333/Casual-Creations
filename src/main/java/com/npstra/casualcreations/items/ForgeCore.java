package com.npstra.casualcreations.items;

import com.npstra.casualcreations.CasualCreations;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class ForgeCore extends Item {
    public ForgeCore() {
        setTranslationKey(CasualCreations.MODID + ".forge_core");
        setRegistryName("forge_core");
        setMaxStackSize(1);
    }
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.translateToLocal("item.casualcreations.forge_core.tooltip"));
    }
}