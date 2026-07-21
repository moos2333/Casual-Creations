package com.npstra.casualcreations.materials.projectile;

import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArrowMaterialRegistry {
    private static Map<String, ArrowHeadMaterial> heads = new HashMap<>();
    private static Map<String, ArrowShaftMaterial> shafts = new HashMap<>();
    private static Map<String, String> headItemMap = new HashMap<>();
    private static Map<String, String> shaftItemMap = new HashMap<>();
    private static Map<String, String> headOreMap = new HashMap<>();
    private static Map<String, String> shaftOreMap = new HashMap<>();

    public static void setHeads(Map<String, ArrowHeadMaterial> map) {
        heads = Collections.unmodifiableMap(new HashMap<>(map));
        buildHeadItemMap();
        buildHeadOreMap();
    }

    public static void setShafts(Map<String, ArrowShaftMaterial> map) {
        shafts = Collections.unmodifiableMap(new HashMap<>(map));
        buildShaftItemMap();
        buildShaftOreMap();
    }

    public static Map<String, ArrowHeadMaterial> getHeads() { return heads; }
    public static Map<String, ArrowShaftMaterial> getShafts() { return shafts; }
    public static ArrowHeadMaterial getHead(String name) { return heads.get(name); }
    public static ArrowShaftMaterial getShaft(String name) { return shafts.get(name); }

    public static String getHeadNameByItem(ItemStack stack) {
        String regName = stack.getItem().getRegistryName().toString();
        int meta = stack.getMetadata();
        String key = regName + ":" + meta;
        String result = headItemMap.get(key);
        if (result == null) {
            result = headItemMap.get(regName + ":0");
        }
        return result;
    }

    public static String getShaftNameByItem(ItemStack stack) {
        String regName = stack.getItem().getRegistryName().toString();
        int meta = stack.getMetadata();
        String key = regName + ":" + meta;
        String result = shaftItemMap.get(key);
        if (result == null) {
            result = shaftItemMap.get(regName + ":0");
        }
        return result;
    }

    public static String getHeadNameByOreDict(String oreName) {
        return headOreMap.get(oreName);
    }

    public static String getShaftNameByOreDict(String oreName) {
        return shaftOreMap.get(oreName);
    }

    private static void buildHeadItemMap() {
        headItemMap.clear();
        for (Map.Entry<String, ArrowHeadMaterial> e : heads.entrySet()) {
            String item = e.getValue().getItem();
            int meta = e.getValue().getMeta();
            if (item != null && !item.isEmpty()) {
                headItemMap.put(item + ":" + meta, e.getKey());
            }
        }
    }

    private static void buildShaftItemMap() {
        shaftItemMap.clear();
        for (Map.Entry<String, ArrowShaftMaterial> e : shafts.entrySet()) {
            String item = e.getValue().getItem();
            int meta = e.getValue().getMeta();
            if (item != null && !item.isEmpty()) {
                shaftItemMap.put(item + ":" + meta, e.getKey());
            }
        }
    }

    private static void buildHeadOreMap() {
        headOreMap.clear();
        for (Map.Entry<String, ArrowHeadMaterial> e : heads.entrySet()) {
            String ore = e.getValue().getOreDict();
            if (ore != null && !ore.isEmpty()) {
                headOreMap.put(ore, e.getKey());
            }
        }
    }

    private static void buildShaftOreMap() {
        shaftOreMap.clear();
        for (Map.Entry<String, ArrowShaftMaterial> e : shafts.entrySet()) {
            String ore = e.getValue().getOreDict();
            if (ore != null && !ore.isEmpty()) {
                shaftOreMap.put(ore, e.getKey());
            }
        }
    }
}