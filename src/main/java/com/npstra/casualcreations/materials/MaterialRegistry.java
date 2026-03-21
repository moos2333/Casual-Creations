package com.npstra.casualcreations.materials;

import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {
    private static Map<String, HeadMaterial> heads = new HashMap<>();
    private static Map<String, RodMaterial> rods = new HashMap<>();

    public static void setHeads(Map<String, HeadMaterial> map) {
        heads = map;
    }

    public static void setRods(Map<String, RodMaterial> map) {
        rods = map;
    }

    public static Map<String, HeadMaterial> getHeads() {
        return heads;
    }

    public static Map<String, RodMaterial> getRods() {
        return rods;
    }

    public static HeadMaterial getHead(String name) {
        return heads.get(name);
    }

    public static RodMaterial getRod(String name) {
        return rods.get(name);
    }
}