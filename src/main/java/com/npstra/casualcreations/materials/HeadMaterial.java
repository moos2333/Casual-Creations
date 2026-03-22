package com.npstra.casualcreations.materials;

public class HeadMaterial {
    private final String name;
    private final String displayName;
    private final int color;
    private final int durability;
    private final float attackDamage;
    private final float attackSpeed;
    private final float miningSpeed;
    private final int enchantability;
    private final int harvestLevel;

    public HeadMaterial(String name, String displayName, int color, int durability, float attackDamage, float attackSpeed, float miningSpeed, int enchantability, int harvestLevel) {
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.miningSpeed = miningSpeed;
        this.enchantability = enchantability;
        this.harvestLevel = harvestLevel;
    }

    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public int getColor() { return color; }
    public int getDurability() { return durability; }
    public float getAttackDamage() { return attackDamage; }
    public float getAttackSpeed() { return attackSpeed; }
    public float getMiningSpeed() { return miningSpeed; }
    public int getEnchantability() { return enchantability; }
    public int getHarvestLevel() { return harvestLevel; }
}