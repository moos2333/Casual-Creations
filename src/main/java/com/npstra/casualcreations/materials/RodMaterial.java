package com.npstra.casualcreations.materials;

public class RodMaterial {
    private final String name;
    private final String displayName;
    private final int color;
    private final float durabilityMultiplier;
    private final float damageMultiplier;
    private final float attackSpeedMultiplier;
    private final float speedMultiplier;
    private final float enchantabilityMultiplier;

    public RodMaterial(String name, String displayName, int color, float durabilityMultiplier, float damageMultiplier, float attackSpeedMultiplier, float speedMultiplier, float enchantabilityMultiplier) {
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.durabilityMultiplier = durabilityMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.enchantabilityMultiplier = enchantabilityMultiplier;
    }

    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public int getColor() { return color; }
    public float getDurabilityMultiplier() { return durabilityMultiplier; }
    public float getDamageMultiplier() { return damageMultiplier; }
    public float getAttackSpeedMultiplier() { return attackSpeedMultiplier; }
    public float getSpeedMultiplier() { return speedMultiplier; }
    public float getEnchantabilityMultiplier() { return enchantabilityMultiplier; }
}