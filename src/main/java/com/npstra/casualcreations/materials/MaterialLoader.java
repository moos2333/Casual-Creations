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

    public static void loadMaterials() {
        Map<String, HeadMaterial> heads = new HashMap<>();
        Map<String, RodMaterial> rods = new HashMap<>();

        if (ConfigHandler.enableExternalMaterials) {
            loadHeadsFromConfig(heads);
            loadRodsFromConfig(rods);
        }

        loadHeadsFromAssets(heads);
        loadRodsFromAssets(rods);

        if (heads.isEmpty()) {
            heads.put("wood", new HeadMaterial("wood", "Wood", 0x8B5A2B, 30, 0.0f, 0.0f, 2.0f, 15));
            heads.put("stone", new HeadMaterial("stone", "Stone", 0xC0C0C0, 96, 1.0f, -0.1f, 4.0f, 5));
            heads.put("iron", new HeadMaterial("iron", "Iron", 0xF8F8F8, 226, 2.0f, 0.0f, 5.0f, 14));
            heads.put("gold", new HeadMaterial("gold", "Gold", 0xFFE86E, 2, 0.0f, 0.2f, 12.0f, 22));
            heads.put("diamond", new HeadMaterial("diamond", "Diamond", 0x88F0FF, 1531, 3.0f, 0.1f, 8.0f, 18));
            heads.put("obsidian", new HeadMaterial("obsidian", "Obsidian", 0x4A3F6E, 142, 2.5f, -0.1f, 6.0f, 3));
            generateDefaultHeadFiles(heads);
        }
        if (rods.isEmpty()) {
            rods.put("wood", new RodMaterial("wood", "Wooden Rod", 0x8B5A2B, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f));
            rods.put("bone", new RodMaterial("bone", "Bone Rod", 0xF0F0F0, 0.9f, 1.1f, 1.1f, 1.05f, 1.1f));
            rods.put("blaze", new RodMaterial("blaze", "Blaze Rod", 0xFFA500, 1.1f, 1.2f, 0.9f, 1.15f, 0.9f));
            rods.put("emerald", new RodMaterial("emerald", "Emerald Rod", 0x50C878, 1.4f, 0.9f, 0.9f, 1.0f, 1.5f));
            generateDefaultRodFiles(rods);
        }

        MaterialRegistry.setHeads(heads);
        MaterialRegistry.setRods(rods);
    }

    private static void loadHeadsFromAssets(Map<String, HeadMaterial> map) {
        String[] materials = {"wood", "stone", "iron", "gold", "diamond", "obsidian"};
        for (String name : materials) {
            try {
                String path = "/assets/" + CasualCreations.MODID + "/materials/heads/" + name + ".json";
                try (InputStreamReader reader = new InputStreamReader(CasualCreations.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
                    HeadMaterial material = GSON.fromJson(reader, HeadMaterial.class);
                    map.put(name, material);
                }
            } catch (Exception e) {
            }
        }
    }

    private static void loadRodsFromAssets(Map<String, RodMaterial> map) {
        String[] materials = {"wood", "bone", "blaze", "emerald"};
        for (String name : materials) {
            try {
                String path = "/assets/" + CasualCreations.MODID + "/materials/rods/" + name + ".json";
                try (InputStreamReader reader = new InputStreamReader(CasualCreations.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
                    RodMaterial material = GSON.fromJson(reader, RodMaterial.class);
                    map.put(name, material);
                }
            } catch (Exception e) {
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
            } catch (Exception e) {
                e.printStackTrace();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void generateDefaultHeadFiles(Map<String, HeadMaterial> defaults) {
        File headsDir = new File(ConfigHandler.configDir, "materials/head");
        headsDir.mkdirs();
        for (Map.Entry<String, HeadMaterial> entry : defaults.entrySet()) {
            File file = new File(headsDir, entry.getKey() + ".json");
            if (!file.exists()) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    GSON.toJson(entry.getValue(), writer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void generateDefaultRodFiles(Map<String, RodMaterial> defaults) {
        File rodsDir = new File(ConfigHandler.configDir, "materials/rod");
        rodsDir.mkdirs();
        for (Map.Entry<String, RodMaterial> entry : defaults.entrySet()) {
            File file = new File(rodsDir, entry.getKey() + ".json");
            if (!file.exists()) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    GSON.toJson(entry.getValue(), writer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}