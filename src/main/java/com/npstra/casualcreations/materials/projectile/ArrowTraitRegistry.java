package com.npstra.casualcreations.materials.projectile;

public enum ArrowTraitRegistry {
    SMASH("smash", 0.4f, 0.0f, 0.0f, 0.0f, 0.0f, false),
    SWIFT("swift", 0.0f, 0.3f, 0.0f, 0.0f, 0.0f, false),
    RECYCLE("recycle", 0.0f, 0.0f, 0.10f, 0.0f, 0.0f, false),
    RENEW("renew", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, true),
    HEAVY("heavy", 0.6f, -0.3f, 0.0f, 0.0f, 0.0f, false);

    private final String id;
    private final float damageBonus;
    private final float speedBonus;
    private final float recoveryBonus;
    private final float damageMultiplierBonus;
    private final float speedMultiplierBonus;
    private final boolean isRenew;

    ArrowTraitRegistry(String id, float damageBonus, float speedBonus, float recoveryBonus, float damageMultiplierBonus, float speedMultiplierBonus, boolean isRenew) {
        this.id = id;
        this.damageBonus = damageBonus;
        this.speedBonus = speedBonus;
        this.recoveryBonus = recoveryBonus;
        this.damageMultiplierBonus = damageMultiplierBonus;
        this.speedMultiplierBonus = speedMultiplierBonus;
        this.isRenew = isRenew;
    }

    public static ArrowTraitRegistry fromId(String id) {
        for (ArrowTraitRegistry t : values()) {
            if (t.id.equals(id)) return t;
        }
        return null;
    }

    public String getId() { return id; }
    public float getDamageBonus() { return damageBonus; }
    public float getSpeedBonus() { return speedBonus; }
    public float getRecoveryBonus() { return recoveryBonus; }
    public float getDamageMultiplierBonus() { return damageMultiplierBonus; }
    public float getSpeedMultiplierBonus() { return speedMultiplierBonus; }
    public boolean isRenew() { return isRenew; }
}