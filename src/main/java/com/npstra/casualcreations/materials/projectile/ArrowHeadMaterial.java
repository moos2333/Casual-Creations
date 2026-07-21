package com.npstra.casualcreations.materials.projectile;

public class ArrowHeadMaterial {
    private final String name;
    private final float baseDamage;
    private final float baseSpeed;
    private final float recoveryRate;
    private final int color;
    private final String item;
    private final int meta;
    private final String oreDict;
    private final String trait;

    public ArrowHeadMaterial(String name, float baseDamage, float baseSpeed, float recoveryRate, int color, String item, int meta, String oreDict, String trait) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.baseSpeed = baseSpeed;
        this.recoveryRate = recoveryRate;
        this.color = color;
        this.item = item;
        this.meta = meta;
        this.oreDict = oreDict;
        this.trait = trait;
    }

    public String getName() { return name; }
    public float getBaseDamage() { return baseDamage; }
    public float getBaseSpeed() { return baseSpeed; }
    public float getRecoveryRate() { return recoveryRate; }
    public int getColor() { return color; }
    public String getItem() { return item; }
    public int getMeta() { return meta; }
    public String getOreDict() { return oreDict; }
    public String getTrait() { return trait; }
}