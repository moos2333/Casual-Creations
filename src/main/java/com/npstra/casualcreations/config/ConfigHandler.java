package com.npstra.casualcreations.config;

import com.npstra.casualcreations.CasualCreations;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;
    public static File configDir;
    public static boolean enableExternalMaterials;

    public static void init(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), CasualCreations.MODID);
        config = new Configuration(new File(configDir, "casualcreations.cfg"));
        syncConfig();
    }

    public static void syncConfig() {
        try {
            config.load();
            enableExternalMaterials = config.getBoolean("enableExternalMaterials", "general", true, "Enable loading custom materials from config/materials folder");
        } finally {
            if (config.hasChanged()) config.save();
        }
    }
}