package com.npstra.casualcreations.recipes;

import com.npstra.casualcreations.items.IModularTool;
import com.npstra.casualcreations.items.ModItems;
import com.npstra.casualcreations.materials.MaterialRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModularToolRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private static final Map<String, ToolPattern> PATTERNS = new HashMap<>();

    static {
        PATTERNS.put("sword", new ToolPattern("sword", new String[]{" M ", " M ", " R "}, null, 2, 1, () -> ModItems.SWORD));
        PATTERNS.put("pickaxe", new ToolPattern("pickaxe", new String[]{"MMM", " R ", " R "}, null, 3, 2, () -> ModItems.PICKAXE));
        PATTERNS.put("axe", new ToolPattern("axe", new String[]{"MM ", "MR ", " R "}, null, 3, 2, () -> ModItems.AXE));
        PATTERNS.put("shovel", new ToolPattern("shovel", new String[]{" M ", " R ", " R "}, null, 1, 2, () -> ModItems.SHOVEL));
        PATTERNS.put("hoe", new ToolPattern("hoe", new String[]{"MM ", " R ", " R "}, null, 2, 2, () -> ModItems.HOE));
        PATTERNS.put("knife", new ToolPattern("knife", new String[]{"   ", " M ", " R "}, new String[]{" M", " R"}, 1, 1, () -> ModItems.KNIFE));
        PATTERNS.put("battleaxe", new ToolPattern("battleaxe", new String[]{"MMM", "MRM", " R "}, null, 5, 2, () -> ModItems.BATTLEAXE));
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return getResult(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ToolResult result = getResult(inv);
        if (result != null) {
            ItemStack stack = result.tool;
            ((IModularTool) stack.getItem()).setMaterials(stack, result.head, result.rod);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return (width >= 2 && height >= 2) || (width >= 3 && height >= 3);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.getItem() == ModItems.FORGE_CORE) {
                remaining.set(i, stack.copy());
            }
        }
        return remaining;
    }

    private ToolResult getResult(InventoryCrafting inv) {
        int width = inv.getWidth();
        int height = inv.getHeight();
        if (width == 2 && height == 2) {
            return match2x2(inv);
        } else if (width == 3 && height == 3) {
            return match3x3(inv);
        }
        return null;
    }

    private ToolResult match2x2(InventoryCrafting inv) {
        ItemStack core = inv.getStackInSlot(2);
        if (core.isEmpty() || core.getItem() != ModItems.FORGE_CORE) return null;

        ToolPattern pattern = PATTERNS.get("knife");
        if (pattern == null) return null;

        String headMaterial = null;
        String rodMaterial = null;
        int headCount = 0;
        int rodCount = 0;
        boolean match = true;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                int index = row * 2 + col;
                if (index == 2) continue;
                char c = pattern.shape2x2[row].charAt(col);
                ItemStack stack = inv.getStackInSlot(index);
                if (c == 'M') {
                    if (stack.isEmpty()) { match = false; break; }
                    String mat = getHeadMaterial(stack);
                    if (mat == null) { match = false; break; }
                    if (headMaterial == null) headMaterial = mat;
                    else if (!headMaterial.equals(mat)) { match = false; break; }
                    headCount++;
                } else if (c == 'R') {
                    if (stack.isEmpty()) { match = false; break; }
                    String mat = getRodMaterial(stack);
                    if (mat == null) { match = false; break; }
                    if (rodMaterial == null) rodMaterial = mat;
                    else if (!rodMaterial.equals(mat)) { match = false; break; }
                    rodCount++;
                } else {
                    if (!stack.isEmpty()) { match = false; break; }
                }
            }
            if (!match) break;
        }

        if (match && headCount == pattern.headCount && rodCount == pattern.rodCount && headMaterial != null && rodMaterial != null) {
            return new ToolResult(pattern.toolSupplier.get(), headMaterial, rodMaterial);
        }
        return null;
    }

    private ToolResult match3x3(InventoryCrafting inv) {
        ItemStack core = inv.getStackInSlot(6);
        if (core.isEmpty() || core.getItem() != ModItems.FORGE_CORE) return null;

        for (Map.Entry<String, ToolPattern> entry : PATTERNS.entrySet()) {
            ToolPattern pattern = entry.getValue();
            if (pattern.shape == null) continue;
            String headMaterial = null;
            String rodMaterial = null;
            int headCount = 0;
            int rodCount = 0;
            boolean match = true;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int index = row * 3 + col;
                    if (index == 6) continue;
                    char c = pattern.shape[row].charAt(col);
                    ItemStack stack = inv.getStackInSlot(index);
                    if (c == 'M') {
                        if (stack.isEmpty()) { match = false; break; }
                        String mat = getHeadMaterial(stack);
                        if (mat == null) { match = false; break; }
                        if (headMaterial == null) headMaterial = mat;
                        else if (!headMaterial.equals(mat)) { match = false; break; }
                        headCount++;
                    } else if (c == 'R') {
                        if (stack.isEmpty()) { match = false; break; }
                        String mat = getRodMaterial(stack);
                        if (mat == null) { match = false; break; }
                        if (rodMaterial == null) rodMaterial = mat;
                        else if (!rodMaterial.equals(mat)) { match = false; break; }
                        rodCount++;
                    } else {
                        if (!stack.isEmpty()) { match = false; break; }
                    }
                }
                if (!match) break;
            }

            if (match && headCount == pattern.headCount && rodCount == pattern.rodCount && headMaterial != null && rodMaterial != null) {
                return new ToolResult(pattern.toolSupplier.get(), headMaterial, rodMaterial);
            }
        }
        return null;
    }

    private String getHeadMaterial(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.IRON_INGOT) return "iron";
        if (item == Items.GOLD_INGOT) return "gold";
        if (item == Items.DIAMOND) return "diamond";
        if (item == Item.getItemFromBlock(Blocks.PLANKS)) return "wood";
        if (item == Item.getItemFromBlock(Blocks.COBBLESTONE)) return "stone";
        if (item == Item.getItemFromBlock(Blocks.OBSIDIAN)) return "obsidian";
        String regName = item.getRegistryName().toString();
        for (String name : MaterialRegistry.getHeads().keySet()) {
            if (regName.contains(name)) return name;
        }
        return null;
    }

    private String getRodMaterial(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.STICK) return "wood";
        if (item == Items.BONE) return "bone";
        if (item == Items.BLAZE_ROD) return "blaze";
        if (item == Items.EMERALD) return "emerald";
        String regName = item.getRegistryName().toString();
        for (String name : MaterialRegistry.getRods().keySet()) {
            if (regName.contains(name)) return name;
        }
        return null;
    }

    private static class ToolPattern {
        final String name;
        final String[] shape;
        final String[] shape2x2;
        final int headCount;
        final int rodCount;
        final Supplier<Item> toolSupplier;

        ToolPattern(String name, String[] shape, String[] shape2x2, int headCount, int rodCount, Supplier<Item> toolSupplier) {
            this.name = name;
            this.shape = shape;
            this.shape2x2 = shape2x2;
            this.headCount = headCount;
            this.rodCount = rodCount;
            this.toolSupplier = toolSupplier;
        }
    }

    private static class ToolResult {
        final ItemStack tool;
        final String head;
        final String rod;

        ToolResult(Item tool, String head, String rod) {
            this.tool = new ItemStack(tool);
            this.head = head;
            this.rod = rod;
        }
    }
}