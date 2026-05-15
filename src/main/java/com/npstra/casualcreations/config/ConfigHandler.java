package com.npstra.casualcreations.config;

import com.npstra.casualcreations.CasualCreations;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;
    public static File configDir;
    public static boolean enableExternalMaterials;
    public static boolean enableGoldenTome;
    public static boolean enableTomeBookMerge;
    public static String[] tomeBookMergeEnchantments;
    public static int maxEnchantmentLevel;

    public static void init(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), CasualCreations.MODID);
        config = new Configuration(new File(configDir, "casualcreations.cfg"));
        syncConfig();
    }

    public static void syncConfig() {
        try {
            config.load();
            enableExternalMaterials = config.getBoolean("enableExternalMaterials", "general", true,
                    "Enable loading custom materials from config/materials folder");
            enableGoldenTome = config.getBoolean("enableGoldenTome", "general", true,
                    "Enable the Golden Tome item");
            enableTomeBookMerge = config.getBoolean("enableTomeBookMerge", "goldenTome", true,
                    "Allow Golden Tome to merge with enchanted books on an anvil (like creative mode)");
            tomeBookMergeEnchantments = config.getStringList("tomeBookMergeEnchantments", "goldenTome",
                    new String[]{
                            "minecraft:protection",
                            "minecraft:fire_protection",
                            "minecraft:blast_protection",
                            "minecraft:projectile_protection",
                            "minecraft:unbreaking",
                            "minecraft:smite",
                            "minecraft:sharpness",
                            "minecraft:bane_of_arthropods",
                            "minecraft:power"
                    },
                    "List of enchantment IDs allowed when merging tomes/books");
            maxEnchantmentLevel = config.getInt("maxEnchantmentLevel", "goldenTome", 32767, 1, Integer.MAX_VALUE,
                    "Maximum enchantment level allowed on tools/books upgraded with the Golden Tome");
        } finally {
            if (config.hasChanged()) config.save();
        }
    }
}