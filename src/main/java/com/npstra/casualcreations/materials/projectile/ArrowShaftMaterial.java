package com.npstra.casualcreations.materials.projectile;

public class ArrowShaftMaterial {
    private final String name;
    private final float damageMultiplier;
    private final float speedMultiplier;
    private final int color;
    private final String item;
    private final int meta;
    private final String oreDict;
    private final String trait;

    public ArrowShaftMaterial(String name, float damageMultiplier, float speedMultiplier, int color, String item, int meta, String oreDict, String trait) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.color = color;
        this.item = item;
        this.meta = meta;
        this.oreDict = oreDict;
        this.trait = trait;
    }

    public String getName() { return name; }
    public float getDamageMultiplier() { return damageMultiplier; }
    public float getSpeedMultiplier() { return speedMultiplier; }
    public int getColor() { return color; }
    public String getItem() { return item; }
    public int getMeta() { return meta; }
    public String getOreDict() { return oreDict; }
    public String getTrait() { return trait; }
}