package com.npstra.casualcreations.recipes;

import com.npstra.casualcreations.items.ModItems;
import com.npstra.casualcreations.items.projectile.IModularArrow;
import com.npstra.casualcreations.materials.projectile.ArrowMaterialRegistry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModularArrowRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return getResult(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Result r = getResult(inv);
        if (r == null) return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(ModItems.ARROW, 4);
        ((IModularArrow) stack.getItem()).setArrowMaterials(stack, r.head, r.shaft);
        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        ItemStack core = inv.getStackInSlot(6);
        if (core.getItem() == ModItems.FORGE_CORE) remaining.set(6, core.copy());
        return remaining;
    }

    private Result getResult(InventoryCrafting inv) {
        if (inv.getWidth() < 3 || inv.getHeight() < 3) return null;
        ItemStack core = inv.getStackInSlot(6);
        if (core.isEmpty() || core.getItem() != ModItems.FORGE_CORE) return null;

        ItemStack feather = inv.getStackInSlot(7);
        if (feather.isEmpty() || !feather.getItem().getRegistryName().toString().equals("minecraft:feather")) return null;

        ItemStack headStack = inv.getStackInSlot(1);
        ItemStack shaftStack = inv.getStackInSlot(4);
        if (headStack.isEmpty() || shaftStack.isEmpty()) return null;

        String head = getHead(headStack);
        String shaft = getShaft(shaftStack);
        if (head == null || shaft == null) return null;

        for (int i = 0; i < 9; i++) {
            if (i == 1 || i == 4 || i == 6 || i == 7) continue;
            if (!inv.getStackInSlot(i).isEmpty()) return null;
        }
        return new Result(head, shaft);
    }

    private String getHead(ItemStack stack) {
        String mat = ArrowMaterialRegistry.getHeadNameByItem(stack);
        if (mat != null) return mat;
        for (int id : OreDictionary.getOreIDs(stack)) {
            String ore = OreDictionary.getOreName(id);
            String mapped = ArrowMaterialRegistry.getHeadNameByOreDict(ore);
            if (mapped != null) return mapped;
        }
        return null;
    }

    private String getShaft(ItemStack stack) {
        String mat = ArrowMaterialRegistry.getShaftNameByItem(stack);
        if (mat != null) return mat;
        for (int id : OreDictionary.getOreIDs(stack)) {
            String ore = OreDictionary.getOreName(id);
            String mapped = ArrowMaterialRegistry.getShaftNameByOreDict(ore);
            if (mapped != null) return mapped;
        }
        return null;
    }

    private static class Result {
        final String head, shaft;
        Result(String head, String shaft) { this.head = head; this.shaft = shaft; }
    }
}