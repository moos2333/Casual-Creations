package com.npstra.casualcreations.materials.projectile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.npstra.casualcreations.CasualCreations;
import com.npstra.casualcreations.config.ConfigHandler;
import com.npstra.casualcreations.materials.ColorAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RangeMaterialLoader {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(int.class, new ColorAdapter())
            .registerTypeAdapter(Integer.class, new ColorAdapter())
            .create();
    private static final String[] ARROW_HEAD_MATERIALS = {"wood", "stone", "iron", "gold", "diamond", "obsidian", "prismarine", "chorus"};
    private static final String[] ARROW_SHAFT_MATERIALS = {"wood", "bone", "blaze"};

    public static void loadMaterials() {
        Map<String, ArrowHeadMaterial> heads = new HashMap<>();
        Map<String, ArrowShaftMaterial> shafts = new HashMap<>();

        heads.putAll(getDefaultArrowHeads());
        shafts.putAll(getDefaultArrowShafts());

        loadArrowHeadsFromAssets(heads);
        loadArrowShaftsFromAssets(shafts);

        if (ConfigHandler.enableExternalMaterials) {
            loadArrowHeadsFromConfig(heads);
            loadArrowShaftsFromConfig(shafts);
            generateDefaultArrowFiles();
        }

        ArrowMaterialRegistry.setHeads(heads);
        ArrowMaterialRegistry.setShafts(shafts);
    }

    private static void loadArrowHeadsFromAssets(Map<String, ArrowHeadMaterial> map) {
        for (String name : ARROW_HEAD_MATERIALS) {
            try {
                String path = "/assets/" + CasualCreations.MODID + "/materials/arrow_heads/" + name + ".json";
                try (InputStreamReader reader = new InputStreamReader(
                        CasualCreations.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
                    ArrowHeadMaterial material = GSON.fromJson(reader, ArrowHeadMaterial.class);
                    if (material.getName() != null && isValidArrowHead(material)) {
                        map.put(name, material);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void loadArrowShaftsFromAssets(Map<String, ArrowShaftMaterial> map) {
        for (String name : ARROW_SHAFT_MATERIALS) {
            try {
                String path = "/assets/" + CasualCreations.MODID + "/materials/arrow_shafts/" + name + ".json";
                try (InputStreamReader reader = new InputStreamReader(
                        CasualCreations.class.getResourceAsStream(path), StandardCharsets.UTF_8)) {
                    ArrowShaftMaterial material = GSON.fromJson(reader, ArrowShaftMaterial.class);
                    if (material.getName() != null && isValidArrowShaft(material)) {
                        map.put(name, material);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void loadArrowHeadsFromConfig(Map<String, ArrowHeadMaterial> map) {
        File headsDir = new File(ConfigHandler.configDir, "materials/arrow_heads");
        if (!headsDir.exists()) return;
        File[] files = headsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return;
        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                ArrowHeadMaterial material = GSON.fromJson(reader, ArrowHeadMaterial.class);
                if (material.getName() != null && isValidArrowHead(material)) {
                    map.put(material.getName(), material);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void loadArrowShaftsFromConfig(Map<String, ArrowShaftMaterial> map) {
        File shaftsDir = new File(ConfigHandler.configDir, "materials/arrow_shafts");
        if (!shaftsDir.exists()) return;
        File[] files = shaftsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return;
        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                ArrowShaftMaterial material = GSON.fromJson(reader, ArrowShaftMaterial.class);
                if (material.getName() != null && isValidArrowShaft(material)) {
                    map.put(material.getName(), material);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static boolean isValidArrowHead(ArrowHeadMaterial material) {
        return material.getBaseDamage() > 0f && material.getItem() != null && !material.getItem().isEmpty();
    }

    private static boolean isValidArrowShaft(ArrowShaftMaterial material) {
        return material.getDamageMultiplier() > 0f && material.getItem() != null && !material.getItem().isEmpty();
    }

    private static Map<String, ArrowHeadMaterial> getDefaultArrowHeads() {
        Map<String, ArrowHeadMaterial> defaults = new HashMap<>();
        defaults.put("wood", new ArrowHeadMaterial("wood", 0.5f, 2.5f, 0.25f, 0x9B6A3B, "minecraft:planks", 0, "plankWood", "recycle"));
        defaults.put("stone", new ArrowHeadMaterial("stone", 1.0f, 2.0f, 0.0f, 0xC0C0C0, "minecraft:cobblestone", 0, "stone", "heavy"));
        defaults.put("iron", new ArrowHeadMaterial("iron", 2.0f, 3.0f, 0.2f, 0xF8F8F8, "minecraft:iron_ingot", 0, "ingotIron", null));
        defaults.put("gold", new ArrowHeadMaterial("gold", 1.0f, 3.3f, 0.0f, 0xFFE86E, "minecraft:gold_ingot", 0, "ingotGold", "renew"));
        defaults.put("diamond", new ArrowHeadMaterial("diamond", 3.0f, 3.2f, 0.70f, 0x88F0FF, "minecraft:diamond", 0, "gemDiamond", "smash"));
        defaults.put("obsidian", new ArrowHeadMaterial("obsidian", 2.2f, 2.5f, 0.0f, 0x3C3056, "minecraft:obsidian", 0, "obsidian", "heavy"));
        defaults.put("prismarine", new ArrowHeadMaterial("prismarine", 2.0f, 2.5f, 0.0f, 0x2D8C7A, "minecraft:prismarine_shard", 0, "gemPrismarine", "swift"));
        defaults.put("chorus", new ArrowHeadMaterial("chorus", 1.5f, 2.5f, 0.80f, 0x9B59B6, "minecraft:chorus_fruit_popped", 0, null, "recycle"));
        return defaults;
    }

    private static Map<String, ArrowShaftMaterial> getDefaultArrowShafts() {
        Map<String, ArrowShaftMaterial> defaults = new HashMap<>();
        defaults.put("wood", new ArrowShaftMaterial("wood", 1.0f, 1.0f, 0x9B6A3B, "minecraft:stick", 0, "stickWood", "recycle"));
        defaults.put("bone", new ArrowShaftMaterial("bone", 1.0f, 1.08f, 0xF0F0F0, "minecraft:bone", 0, "bone", "swift"));
        defaults.put("blaze", new ArrowShaftMaterial("blaze", 1.15f, 1.0f, 0xFFA500, "minecraft:blaze_rod", 0, "blazeRod", "smash"));
        return defaults;
    }

    private static void generateDefaultArrowFiles() {
        File headsDir = new File(ConfigHandler.configDir, "materials/arrow_heads");
        File shaftsDir = new File(ConfigHandler.configDir, "materials/arrow_shafts");
        headsDir.mkdirs();
        shaftsDir.mkdirs();

        Map<String, ArrowHeadMaterial> defaultHeads = getDefaultArrowHeads();
        Map<String, ArrowShaftMaterial> defaultShafts = getDefaultArrowShafts();

        for (Map.Entry<String, ArrowHeadMaterial> entry : defaultHeads.entrySet()) {
            File file = new File(headsDir, entry.getKey() + ".json");
            if (!file.exists()) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    GSON.toJson(entry.getValue(), writer);
                } catch (Exception ignored) {
                }
            }
        }

        for (Map.Entry<String, ArrowShaftMaterial> entry : defaultShafts.entrySet()) {
            File file = new File(shaftsDir, entry.getKey() + ".json");
            if (!file.exists()) {
                try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                    GSON.toJson(entry.getValue(), writer);
                } catch (Exception ignored) {
                }
            }
        }
    }
}