package com.npstra.casualcreations.block;

import com.npstra.casualcreations.CasualCreations;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import javax.annotation.Nullable;

public class BlockDiamondAnvil extends BlockAnvil {

    public BlockDiamondAnvil() {
        super();
        setSoundType(SoundType.ANVIL);
        setHardness(5.0F);
        setResistance(2000.0F);
        setHarvestLevel("pickaxe", 2);
        setCreativeTab(net.minecraft.creativetab.CreativeTabs.DECORATIONS);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityDiamondAnvil();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityDiamondAnvil) {
            TileEntityDiamondAnvil anvil = (TileEntityDiamondAnvil) te;
            int used = stack.getItemDamage();
            int remaining = TileEntityDiamondAnvil.MAX_DURABILITY - used;
            anvil.setDurability(remaining);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityDiamondAnvil) {
            TileEntityDiamondAnvil anvil = (TileEntityDiamondAnvil) te;
            ItemStack drop = new ItemStack(this);
            int used = TileEntityDiamondAnvil.MAX_DURABILITY - anvil.getDurability();
            drop.setItemDamage(used);
            drops.add(drop);
            for (int i = 0; i < anvil.getInventory().getSlots(); i++) {
                ItemStack stack = anvil.getInventory().getStackInSlot(i);
                if (!stack.isEmpty()) {
                    drops.add(stack);
                }
            }
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        FMLNetworkHandler.openGui(playerIn, CasualCreations.instance, CasualCreations.GUI_ID_DIAMOND_ANVIL, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}