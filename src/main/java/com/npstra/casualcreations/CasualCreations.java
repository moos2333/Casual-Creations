package com.npstra.casualcreations;

import com.npstra.casualcreations.config.ConfigHandler;
import com.npstra.casualcreations.items.ModItems;
import com.npstra.casualcreations.materials.MaterialLoader;
import com.npstra.casualcreations.recipes.ModularToolRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = CasualCreations.MODID, name = CasualCreations.NAME, version = CasualCreations.VERSION)
public class CasualCreations {

    public static final String MODID = "casualcreations";
    public static final String NAME = "Casual Creations";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static CasualCreations instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event);
        MaterialLoader.loadMaterials();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(ModItems.FORGE_CORE);
            event.getRegistry().register(ModItems.SWORD);
            event.getRegistry().register(ModItems.PICKAXE);
            event.getRegistry().register(ModItems.AXE);
            event.getRegistry().register(ModItems.SHOVEL);
            event.getRegistry().register(ModItems.HOE);
        }
    }

    @Mod.EventBusSubscriber
    public static class RecipeHandler {
        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            event.getRegistry().register(new ModularToolRecipe().setRegistryName(MODID, "modular_tool"));
        }
    }

    @Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID)
    public static class ClientHandler {
        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerColors(ColorHandlerEvent.Item event) {
            event.getItemColors().registerItemColorHandler(new com.npstra.casualcreations.client.ItemColorHandler(),
                    ModItems.SWORD, ModItems.PICKAXE, ModItems.AXE, ModItems.SHOVEL, ModItems.HOE);
        }
    }
}