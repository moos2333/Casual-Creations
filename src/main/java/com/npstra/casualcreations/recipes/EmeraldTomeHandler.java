package com.npstra.casualcreations.recipes;

import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.config.ConfigHandler;
import com.npstra.casualcreations.items.ItemEmeraldTome;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = CasualCreations.MODID)
public class EmeraldTomeHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!ConfigHandler.enableEmeraldTome) {
            return;
        }

        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) {
            return;
        }

        if (!(right.getItem() instanceof ItemEmeraldTome)) {
            return;
        }

        if (left.getItem() instanceof ItemEmeraldTome) {
            return;
        }

        int repairCost = left.getRepairCost();
        if (repairCost <= 0) {
            return;
        }

        int cost = Math.min(repairCost, 30);

        ItemStack output = left.copy();
        output.setRepairCost(0);

        event.setOutput(output);
        event.setCost(cost);
    }
}