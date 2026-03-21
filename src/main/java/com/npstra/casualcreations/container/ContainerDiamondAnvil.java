package com.npstra.casualcreations.container;

import com.npstra.casualcreations.block.TileEntityDiamondAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerDiamondAnvil extends ContainerRepair {

    private final TileEntityDiamondAnvil te;
    private boolean isTakingOutput;

    public ContainerDiamondAnvil(InventoryPlayer playerInv, TileEntityDiamondAnvil te, World world, EntityPlayer player) {
        super(playerInv, world, te.getPos(), player);
        this.te = te;
        this.isTakingOutput = false;
    }

    @Override
    public void updateRepairOutput() {
        super.updateRepairOutput();
        this.materialCost = 0;
        this.repairItemCost = 0;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack original = super.transferStackInSlot(playerIn, index);
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack() && index == 2) {
            te.damage(1);
        }
        return original;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, int clickType, EntityPlayer player) {
        ItemStack result = super.slotClick(slotId, dragType, clickType, player);
        Slot slot = slotId >= 0 && slotId < this.inventorySlots.size() ? this.inventorySlots.get(slotId) : null;
        if (slot != null && slot.getHasStack() && slotId == 2 && !isTakingOutput) {
            isTakingOutput = true;
            te.damage(1);
            isTakingOutput = false;
        }
        return result;
    }
}