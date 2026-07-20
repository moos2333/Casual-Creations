package com.npstra.casualcreations.materials;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {
    private static Map<String, HeadMaterial> heads = new HashMap<>();
    private static Map<String, RodMaterial> rods = new HashMap<>();
    private static Map<String, String> headItemMap = new HashMap<>();
    private static Map<String, String> rodItemMap = new HashMap<>();
    private static Map<String, String> headOreMap = new HashMap<>();
    private static Map<String, String> rodOreMap = new HashMap<>();
    private static int version = 0;

    public static void setHeads(Map<String, HeadMaterial> map) {
        heads = Collections.unmodifiableMap(new HashMap<>(map));
        buildHeadItemMap();
        buildHeadOreMap();
        version++;
    }

    public static void setRods(Map<String, RodMaterial> map) {
        rods = Collections.unmodifiableMap(new HashMap<>(map));
        buildRodItemMap();
        buildRodOreMap();
        version++;
    }

    public static int getVersion() { return version; }

    public static Map<String, HeadMaterial> getHeads() { return heads; }
    public static Map<String, RodMaterial> getRods() { return rods; }
    public static HeadMaterial getHead(String name) { return heads.get(name); }
    public static RodMaterial getRod(String name) { return rods.get(name); }

    public static String getHeadNameByItem(String itemRegistryName) {
        return headItemMap.get(itemRegistryName);
    }

    public static String getRodNameByItem(String itemRegistryName) {
        return rodItemMap.get(itemRegistryName);
    }

    public static String getHeadNameByOreDict(String oreName) {
        return headOreMap.get(oreName);
    }

    public static String getRodNameByOreDict(String oreName) {
        return rodOreMap.get(oreName);
    }

    private static void buildHeadItemMap() {
        headItemMap.clear();
        for (Map.Entry<String, HeadMaterial> entry : heads.entrySet()) {
            String item = entry.getValue().getItem();
            if (item != null && !item.isEmpty()) {
                headItemMap.put(item, entry.getKey());
            }
        }
    }

    private static void buildRodItemMap() {
        rodItemMap.clear();
        for (Map.Entry<String, RodMaterial> entry : rods.entrySet()) {
            String item = entry.getValue().getItem();
            if (item != null && !item.isEmpty()) {
                rodItemMap.put(item, entry.getKey());
            }
        }
    }

    private static void buildHeadOreMap() {
        headOreMap.clear();
        for (Map.Entry<String, HeadMaterial> entry : heads.entrySet()) {
            String ore = entry.getValue().getOreDict();
            if (ore != null && !ore.isEmpty()) {
                headOreMap.put(ore, entry.getKey());
            }
        }
        String[][] fallback = {
                {"wood", "plankWood"},
                {"stone", "stone"},
                {"iron", "ingotIron"},
                {"gold", "ingotGold"},
                {"diamond", "gemDiamond"},
                {"obsidian", "obsidian"}
        };
        for (String[] pair : fallback) {
            if (heads.containsKey(pair[0]) && !headOreMap.containsKey(pair[1])) {
                headOreMap.put(pair[1], pair[0]);
            }
        }
    }

    private static void buildRodOreMap() {
        rodOreMap.clear();
        for (Map.Entry<String, RodMaterial> entry : rods.entrySet()) {
            String ore = entry.getValue().getOreDict();
            if (ore != null && !ore.isEmpty()) {
                rodOreMap.put(ore, entry.getKey());
            }
        }
        String[][] fallback = {
                {"wood", "stickWood"},
                {"bone", "bone"},
                {"blaze", "blazeRod"},
                {"emerald", "gemEmerald"}
        };
        for (String[] pair : fallback) {
            if (rods.containsKey(pair[0]) && !rodOreMap.containsKey(pair[1])) {
                rodOreMap.put(pair[1], pair[0]);
            }
        }
    }
}