package com.npstra.casualcreations.materials;

public class RodMaterial {
    private final String name;
    private final int color;
    private final float durabilityMultiplier;
    private final float damageMultiplier;
    private final float attackSpeedMultiplier;
    private final float speedMultiplier;
    private final float enchantabilityMultiplier;
    private final String item;

    public RodMaterial(String name, int color, float durabilityMultiplier, float damageMultiplier, float attackSpeedMultiplier, float speedMultiplier, float enchantabilityMultiplier, String item) {
        this.name = name;
        this.color = color;
        this.durabilityMultiplier = durabilityMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.enchantabilityMultiplier = enchantabilityMultiplier;
        this.item = item;
    }

    public String getName() { return name; }
    public int getColor() { return color; }
    public float getDurabilityMultiplier() { return durabilityMultiplier; }
    public float getDamageMultiplier() { return damageMultiplier; }
    public float getAttackSpeedMultiplier() { return attackSpeedMultiplier; }
    public float getSpeedMultiplier() { return speedMultiplier; }
    public float getEnchantabilityMultiplier() { return enchantabilityMultiplier; }
    public String getItem() { return item; }
}