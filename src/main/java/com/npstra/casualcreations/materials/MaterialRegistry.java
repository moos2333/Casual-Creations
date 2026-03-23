package com.npstra.casualcreations.materials;

import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {
    private static Map<String, HeadMaterial> heads = new HashMap<>();
    private static Map<String, RodMaterial> rods = new HashMap<>();
    private static Map<String, String> headItemMap = new HashMap<>();
    private static Map<String, String> rodItemMap = new HashMap<>();

    public static void setHeads(Map<String, HeadMaterial> map) {
        heads = map;
        buildHeadItemMap();
    }

    public static void setRods(Map<String, RodMaterial> map) {
        rods = map;
        buildRodItemMap();
    }

    public static Map<String, HeadMaterial> getHeads() { return heads; }
    public static Map<String, RodMaterial> getRods() { return rods; }
    public static HeadMaterial getHead(String name) { return heads.get(name); }
    public static RodMaterial getRod(String name) { return rods.get(name); }

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

    public static String getHeadNameByItem(String itemRegistryName) {
        return headItemMap.get(itemRegistryName);
    }

    public static String getRodNameByItem(String itemRegistryName) {
        return rodItemMap.get(itemRegistryName);
    }
}