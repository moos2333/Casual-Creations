package com.npstra.casualcreations.items;

import com.npstra.casualcreations.CasualCreations;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ModItems {
    public static final Item FORGE_CORE = new ForgeCore();
    public static final Item SWORD = new ModularSword();
    public static final Item PICKAXE = new ModularPickaxe();
    public static final Item AXE = new ModularAxe();
    public static final Item SHOVEL = new ModularShovel();
    public static final Item HOE = new ModularHoe();
    public static final Item KNIFE = new ModularKnife();
    public static final Item BATTLEAXE = new ModularBattleAxe();

    public static void registerModels() {
        registerModel(FORGE_CORE, "forge_core");
        registerModel(SWORD, "sword");
        registerModel(PICKAXE, "pickaxe");
        registerModel(AXE, "axe");
        registerModel(SHOVEL, "shovel");
        registerModel(HOE, "hoe");
        registerModel(KNIFE, "knife");
        registerModel(BATTLEAXE, "battleaxe");
    }

    private static void registerModel(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(CasualCreations.MODID + ":" + name, "inventory"));
    }
}