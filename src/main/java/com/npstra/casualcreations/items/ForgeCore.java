package com.npstra.casualcreations.items;

import com.npstra.casualcreations.CasualCreations;
import net.minecraft.item.Item;

public class ForgeCore extends Item {
    public ForgeCore() {
        setTranslationKey(CasualCreations.MODID + ".forge_core");
        setRegistryName("forge_core");
        setMaxStackSize(1);
    }
}