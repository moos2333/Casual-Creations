package com.npstra.casualcreations.materials;

import com.google.gson.annotations.JsonAdapter;

public class RodMaterial {
    private final String name;
    @JsonAdapter(ColorAdapter.class)
    private final int color;
    private final float durabilityMultiplier;
    private final float damageMultiplier;
    private final float attackSpeedMultiplier;
    private final float speedMultiplier;
    private final float enchantabilityMultiplier;
    private final String item;
    private final int meta;
    private final String oreDict;
    private final String trait;

    public RodMaterial(String name, int color, float durabilityMultiplier, float damageMultiplier, float attackSpeedMultiplier, float speedMultiplier, float enchantabilityMultiplier, String item, int meta, String oreDict, String trait) {
        this.name = name;
        this.color = color;
        this.durabilityMultiplier = durabilityMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.enchantabilityMultiplier = enchantabilityMultiplier;
        this.item = item;
        this.meta = meta;
        this.oreDict = oreDict;
        this.trait = trait;
    }

    public String getName() { return name; }
    public int getColor() { return color; }
    public float getDurabilityMultiplier() { return durabilityMultiplier; }
    public float getDamageMultiplier() { return damageMultiplier; }
    public float getAttackSpeedMultiplier() { return attackSpeedMultiplier; }
    public float getSpeedMultiplier() { return speedMultiplier; }
    public float getEnchantabilityMultiplier() { return enchantabilityMultiplier; }
    public String getItem() { return item; }
    public int getMeta() { return meta; }
    public String getOreDict() { return oreDict; }
    public String getTrait() { return trait; }
}