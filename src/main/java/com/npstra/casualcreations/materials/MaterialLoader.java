package com.npstra.casualcreations.materials;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.config.ConfigHandler;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MaterialLoader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String[] HEAD_MATERIALS = {"wood", "stone", "iron", "gold", "diamond", "obsidian", "flint", "chorus", "netherbrick"};
    private static final String[] ROD_MATERIALS = {"wood", "bone", "blaze", "emerald", "gold", "chorus"};

    public static void loadMaterials() {
        Map<String, HeadMaterial> heads = new HashMap<>();
        Map<String, RodMaterial> rods = new HashMap<>();

        heads.putAll(getDefaultHeads());
        rods.putAll(getDefaultRods());

        loadHeadsFromAssets(heads);
        loadRodsFromAssets(rods);

        if (ConfigHandler.enableExternalMaterials) {
            loadHeadsFromConfig(heads);
            loadRodsFromConfig(rods);
        }

        if (ConfigHandler.enableExternalMaterials) {
            generateDefaultFiles(heads, rods);
        }

        MaterialRegistry.setHeads(heads);
        MaterialRegistry.setRods(rods);
    }

    private static void loadHeadsFromAssets(Map<String, HeadMaterial> map) {
        for (String name : HEAD_MATERIALS) {
            try {
                String path = "/assets/" + CasualCreations.MODID + "/materials/heads/" + name + ".json";
                try (InputStreamReader reader = new InputStreamReader(
                        CasualCreations.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
                    HeadMaterial material = GSON.fromJson(reader, HeadMaterial.class);
                    if (material.getName() != null) {
                        map.put(name, material);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void loadRodsFromAssets(Map<String, RodMaterial> map) {
        for (String name : ROD_MATERIALS) {
            try {
                String path = "/assets/" + CasualCreations.MODID + "/materials/rods/" + name + ".json";
                try (InputStreamReader reader = new InputStreamReader(
                        CasualCreations.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
                    RodMaterial material = GSON.fromJson(reader, RodMaterial.class);
                    if (material.getName() != null) {
                        map.put(name, material);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void loadHeadsFromConfig(Map<String, HeadMaterial> map) {
        File headsDir = new File(ConfigHandler.configDir, "materials/head");
        if (!headsDir.exists()) return;
        File[] files = headsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return;
        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                HeadMaterial material = GSON.fromJson(reader, HeadMaterial.class);
                if (material.getName() != null) {
                    map.put(material.getName(), material);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void loadRodsFromConfig(Map<String, RodMaterial> map) {
        File rodsDir = new File(ConfigHandler.configDir, "materials/rod");
        if (!rodsDir.exists()) return;
        File[] files = rodsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return;
        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                RodMaterial material = GSON.fromJson(reader, RodMaterial.class);
                if (material.getName() != null) {
                    map.put(material.getName(), material);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static Map<String, HeadMaterial> getDefaultHeads() {
        Map<String, HeadMaterial> defaults = new HashMap<>();
        defaults.put("wood", new HeadMaterial("wood", 0x9B6A3B, 30, 0.0f, 0.0f, 2.0f, 15, 0, "minecraft:planks", "plankWood", "tough"));
        defaults.put("stone", new HeadMaterial("stone", 0xC0C0C0, 96, 1.0f, -0.2f, 4.0f, 5, 1, "minecraft:cobblestone", "stone", "efficient"));
        defaults.put("iron", new HeadMaterial("iron", 0xF8F8F8, 226, 2.0f, 0.0f, 5.0f, 14, 2, "minecraft:iron_ingot", "ingotIron", "tough"));
        defaults.put("gold", new HeadMaterial("gold", 0xFFE86E, 2, 0.0f, 0.2f, 12.0f, 22, 0, "minecraft:gold_ingot", "ingotGold", "magic"));
        defaults.put("diamond", new HeadMaterial("diamond", 0x88F0FF, 1531, 3.0f, 0.1f, 8.0f, 18, 3, "minecraft:diamond", "gemDiamond", "sharp"));
        defaults.put("obsidian", new HeadMaterial("obsidian", 0x4A3F6E, 142, 2.5f, -0.1f, 6.0f, 3, 3, "minecraft:obsidian", "obsidian", "light"));
        defaults.put("flint", new HeadMaterial("flint", 0x6B6B6B, 100, 1.0f, -0.2f, 2.5f, 8, 1, "minecraft:flint", "flint", "sharp"));
        defaults.put("chorus", new HeadMaterial("chorus", 0x9B6F9B, 300, 2.0f, 0.1f, 5.0f, 12, 2, "minecraft:chorus_fruit_popped", null, "magic"));
        defaults.put("netherbrick", new HeadMaterial("netherbrick", 0x3E1E24, 200, 2.0f, -0.2f, 4.0f, 4, 1, "minecraft:netherbrick", null, "efficient"));
        return defaults;
    }

    private static Map<String, RodMaterial> getDefaultRods() {
        Map<String, RodMaterial> defaults = new HashMap<>();
        defaults.put("wood", new RodMaterial("wood", 0x9B6A3B, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, "minecraft:stick", "stickWood", "tough"));
        defaults.put("bone", new RodMaterial("bone", 0xF0F0F0, 0.9f, 1.05f, 1.1f, 1.05f, 1.1f, "minecraft:bone", "bone", "light"));
        defaults.put("blaze", new RodMaterial("blaze", 0xFFA500, 1.1f, 1.2f, 0.9f, 1.15f, 0.9f, "minecraft:blaze_rod", "blazeRod", "sharp"));
        defaults.put("emerald", new RodMaterial("emerald", 0x50C878, 1.4f, 0.9f, 0.9f, 1.0f, 1.5f, "minecraft:emerald", "gemEmerald", "magic"));
        defaults.put("gold", new RodMaterial("gold", 0xFFD700, 0.4f, 0.8f, 1.4f, 1.5f, 1.4f, "minecraft:gold_ingot", "ingotGold", "magic"));
        defaults.put("chorus", new RodMaterial("chorus", 0x9B59B6, 1.05f, 1.05f, 1.05f, 1.05f, 1.05f, "minecraft:chorus_fruit_popped", null, "magic"));
        return defaults;
    }

    private static void generateDefaultFiles(Map<String, HeadMaterial> heads, Map<String, RodMaterial> rods) {
        File headsDir = new File(ConfigHandler.configDir, "materials/head");
        File rodsDir = new File(ConfigHandler.configDir, "materials/rod");
        headsDir.mkdirs();
        rodsDir.mkdirs();

        for (Map.Entry<String, HeadMaterial> entry : heads.entrySet()) {
            File file = new File(headsDir, entry.getKey() + ".json");
            if (!file.exists()) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    GSON.toJson(entry.getValue(), writer);
                } catch (Exception ignored) {
                }
            }
        }

        for (Map.Entry<String, RodMaterial> entry : rods.entrySet()) {
            File file = new File(rodsDir, entry.getKey() + ".json");
            if (!file.exists()) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    GSON.toJson(entry.getValue(), writer);
                } catch (Exception ignored) {
                }
            }
        }
    }
}