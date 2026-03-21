package com.npstra.casualcreations;

import com.npstra.casualcreations.block.BlockDiamondAnvil;
import com.npstra.casualcreations.block.TileEntityDiamondAnvil;
import com.npstra.casualcreations.container.ContainerDiamondAnvil;
import com.npstra.casualcreations.gui.GuiDiamondAnvil;
import com.npstra.casualcreations.item.ItemDiamondAnvil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = CasualCreations.MODID, name = CasualCreations.NAME, version = CasualCreations.VERSION)
public class CasualCreations implements IGuiHandler {

    public static final String MODID = "casualcreations";
    public static final String NAME = "Casual Creations";
    public static final String VERSION = "1.0.0";
    public static final int GUI_ID_DIAMOND_ANVIL = 0;

    @Mod.Instance(MODID)
    public static CasualCreations instance;

    public static Block diamondAnvil;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityDiamondAnvil.class, new ResourceLocation(MODID, "diamond_anvil"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            diamondAnvil = new BlockDiamondAnvil();
            diamondAnvil.setRegistryName(MODID, "diamond_anvil");
            diamondAnvil.setTranslationKey(MODID + ".diamond_anvil");
            event.getRegistry().register(diamondAnvil);
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<net.minecraft.item.Item> event) {
            event.getRegistry().register(new ItemDiamondAnvil(diamondAnvil).setRegistryName(diamondAnvil.getRegistryName()));
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, net.minecraft.world.World world, int x, int y, int z) {
        if (ID == GUI_ID_DIAMOND_ANVIL) {
            net.minecraft.tileentity.TileEntity te = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
            if (te instanceof TileEntityDiamondAnvil) {
                return new ContainerDiamondAnvil(player.inventory, (TileEntityDiamondAnvil) te, world, player);
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, net.minecraft.world.World world, int x, int y, int z) {
        if (ID == GUI_ID_DIAMOND_ANVIL) {
            net.minecraft.tileentity.TileEntity te = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
            if (te instanceof TileEntityDiamondAnvil) {
                return new GuiDiamondAnvil(player.inventory, (TileEntityDiamondAnvil) te);
            }
        }
        return null;
    }
}