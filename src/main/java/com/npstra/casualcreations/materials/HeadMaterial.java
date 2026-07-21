package com.npstra.casualcreations.materials;

import com.google.gson.annotations.JsonAdapter;

public class HeadMaterial {
    private final String name;
    @JsonAdapter(ColorAdapter.class)
    private final int color;
    private final int durability;
    private final float attackDamage;
    private final float attackSpeed;
    private final float miningSpeed;
    private final int enchantability;
    private final int harvestLevel;
    private final String item;
    private final int meta;
    private final String oreDict;
    private final String trait;

    public HeadMaterial(String name, int color, int durability, float attackDamage, float attackSpeed, float miningSpeed, int enchantability, int harvestLevel, String item, int meta, String oreDict, String trait) {
        this.name = name;
        this.color = color;
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.miningSpeed = miningSpeed;
        this.enchantability = enchantability;
        this.harvestLevel = harvestLevel;
        this.item = item;
        this.meta = meta;
        this.oreDict = oreDict;
        this.trait = trait;
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
    public int getMeta() { return meta; }
    public String getOreDict() { return oreDict; }
    public String getTrait() { return trait; }
}