package com.npstra.casualcreations.item;

import com.npstra.casualcreations.block.TileEntityDiamondAnvil;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDiamondAnvil extends ItemBlock {

    public ItemDiamondAnvil(Block block) {
        super(block);
        setMaxStackSize(1);
        setMaxDamage(TileEntityDiamondAnvil.MAX_DURABILITY);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        int damage = stack.getItemDamage();
        int maxDamage = getMaxDamage(stack);
        String durability = TextFormatting.GRAY + I18n.format("gui.diamond_anvil.durability", maxDamage - damage, maxDamage);
        tooltip.add(durability);
    }
}