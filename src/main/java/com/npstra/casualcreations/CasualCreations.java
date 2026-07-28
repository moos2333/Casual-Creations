package com.npstra.casualcreations;

import com.npstra.casualcreations.config.ConfigHandler;
import com.npstra.casualcreations.entities.EntityModularArrow;
import com.npstra.casualcreations.items.ModItems;
import com.npstra.casualcreations.materials.MaterialLoader;
import com.npstra.casualcreations.materials.projectile.RangeMaterialLoader;
import com.npstra.casualcreations.recipes.ModularToolRecipe;
import com.npstra.casualcreations.recipes.ModularArrowRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class CasualCreations {

    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;

    @Mod.Instance(MODID)
    public static CasualCreations instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event);
        MaterialLoader.loadMaterials();
        RangeMaterialLoader.loadMaterials();

        int entityId = 0;
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "modular_arrow"),
                EntityModularArrow.class,
                "modular_arrow",
                entityId++,
                instance,
                64, 1, true
        );
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
            event.getRegistry().register(ModItems.KNIFE);
            event.getRegistry().register(ModItems.BATTLEAXE);
            event.getRegistry().register(ModItems.WAR_HAMMER);
            event.getRegistry().register(ModItems.WAR_SHOVEL);
            event.getRegistry().register(ModItems.ARROW);
            event.getRegistry().register(ModItems.SCYTHE);
            if (ConfigHandler.enableGoldenTome) {
                event.getRegistry().register(ModItems.GOLDEN_TOME);
            }
            if (ConfigHandler.enableEmeraldTome) {
                event.getRegistry().register(ModItems.EMERALD_TOME);
            }
        }
    }

    @Mod.EventBusSubscriber
    public static class RecipeHandler {
        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            event.getRegistry().register(new ModularToolRecipe().setRegistryName(MODID, "modular_tool"));
            event.getRegistry().register(new ModularArrowRecipe().setRegistryName(MODID, "modular_arrow"));
        }
    }

    @Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID)
    public static class ClientHandler {
        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            RenderingRegistry.registerEntityRenderingHandler(EntityModularArrow.class, net.minecraft.client.renderer.entity.RenderTippedArrow::new);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerColors(ColorHandlerEvent.Item event) {
            event.getItemColors().registerItemColorHandler(new com.npstra.casualcreations.client.ItemColorHandler(),
                    ModItems.SWORD, ModItems.PICKAXE, ModItems.AXE, ModItems.SHOVEL, ModItems.HOE, ModItems.KNIFE, ModItems.BATTLEAXE, ModItems.WAR_HAMMER, ModItems.WAR_SHOVEL, ModItems.ARROW, ModItems.SCYTHE);
        }
    }
}