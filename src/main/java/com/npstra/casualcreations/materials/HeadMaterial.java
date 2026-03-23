package com.npstra.casualcreations.materials;

public class HeadMaterial {
    private final String name;
    private final int color;
    private final int durability;
    private final float attackDamage;
    private final float attackSpeed;
    private final float miningSpeed;
    private final int enchantability;
    private final int harvestLevel;
    private final String item;

    public HeadMaterial(String name, int color, int durability, float attackDamage, float attackSpeed, float miningSpeed, int enchantability, int harvestLevel, String item) {
        this.name = name;
        this.color = color;
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.miningSpeed = miningSpeed;
        this.enchantability = enchantability;
        this.harvestLevel = harvestLevel;
        this.item = item;
    }

    public String getName() { return name; }
    public int getColor() { return color; }
    public int getDurability() { return durability; }
    public float getAttackDamage() { return attackDamage; }
    public float getAttackSpeed() { return attackSpeed; }
    public float getMiningSpeed() { return miningSpeed; }
    public int getEnchantability() { return enchantability; }
    public int getHarvestLevel() { return harvestLevel; }
    public String getItem() { return item; }
}