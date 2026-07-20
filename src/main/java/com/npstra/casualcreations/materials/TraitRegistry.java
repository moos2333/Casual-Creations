package com.npstra.casualcreations.materials;

public enum TraitRegistry {
    SHARP("sharp", 0.5f, 0.0f, 0, 0.0f, 0),
    LIGHT("light", 0.0f, 0.05f, 0, 0.0f, 0),
    TOUGH("tough", 0.0f, 0.0f, 15, 0.0f, 0),
    EFFICIENT("efficient", 0.0f, 0.0f, 0, 0.2f, 0),
    MAGIC("magic", 0.0f, 0.0f, 0, 0.0f, 2);

    private final String id;
    private final float damagePerLevel;
    private final float speedPerLevel;
    private final int durabilityPerLevel;
    private final float miningPerLevel;
    private final int enchantPerLevel;

    TraitRegistry(String id, float damage, float speed, int durability, float mining, int enchant) {
        this.id = id;
        this.damagePerLevel = damage;
        this.speedPerLevel = speed;
        this.durabilityPerLevel = durability;
        this.miningPerLevel = mining;
        this.enchantPerLevel = enchant;
    }

    public static TraitRegistry fromId(String id) {
        for (TraitRegistry trait : values()) {
            if (trait.id.equals(id)) return trait;
        }
        return null;
    }

    public String getId() { return id; }
    public float getDamagePerLevel() { return damagePerLevel; }
    public float getSpeedPerLevel() { return speedPerLevel; }
    public int getDurabilityPerLevel() { return durabilityPerLevel; }
    public float getMiningPerLevel() { return miningPerLevel; }
    public int getEnchantPerLevel() { return enchantPerLevel; }
}