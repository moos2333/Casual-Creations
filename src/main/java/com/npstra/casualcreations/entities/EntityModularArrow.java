package com.npstra.casualcreations.entities;

import com.npstra.casualcreations.items.ModItems;
import com.npstra.casualcreations.items.projectile.IModularArrow;
import com.npstra.casualcreations.items.projectile.ModularArrowHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityModularArrow extends EntityTippedArrow {
    private String headMaterial;
    private String shaftMaterial;
    private float customVelocity = -1.0f;

    public EntityModularArrow(World world) { super(world); }
    public EntityModularArrow(World world, double x, double y, double z) { super(world, x, y, z); }
    public EntityModularArrow(World world, EntityLivingBase shooter) { super(world, shooter); }

    public void setHeadMaterial(String head) { this.headMaterial = head; }
    public void setShaftMaterial(String shaft) { this.shaftMaterial = shaft; }
    public void setCustomVelocity(float velocity) { this.customVelocity = velocity; }

    @Override
    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
        if (customVelocity >= 0.0f) {
            super.shoot(shooter, pitch, yaw, p_184547_4_, customVelocity, inaccuracy);
            customVelocity = -1.0f;
        } else {
            super.shoot(shooter, pitch, yaw, p_184547_4_, velocity, inaccuracy);
        }
    }

    @Override
    public double getDamage() {
        if (headMaterial == null || shaftMaterial == null) return 2.0D;
        ItemStack mockStack = new ItemStack(ModItems.ARROW);
        IModularArrow arrow = (IModularArrow) mockStack.getItem();
        arrow.setArrowMaterials(mockStack, headMaterial, shaftMaterial);
        return ModularArrowHelper.getBaseDamage(mockStack);
    }

    @Override
    public int getColor() {
        return -1;
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        super.arrowHit(living);
        if (!living.isEntityAlive()) return;
        if (headMaterial == null || shaftMaterial == null) return;

        ItemStack mockStack = new ItemStack(ModItems.ARROW);
        IModularArrow arrow = (IModularArrow) mockStack.getItem();
        arrow.setArrowMaterials(mockStack, headMaterial, shaftMaterial);
        float rate = ModularArrowHelper.getRecoveryRate(mockStack);
        if (rate > 0.0f && world.rand.nextFloat() < rate) {
            ItemStack dropStack = new ItemStack(ModItems.ARROW);
            arrow.setArrowMaterials(dropStack, headMaterial, shaftMaterial);
            entityDropItem(dropStack, 0.1f);
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        if (headMaterial == null || shaftMaterial == null) {
            return new ItemStack(ModItems.ARROW);
        }
        ItemStack stack = new ItemStack(ModItems.ARROW);
        IModularArrow arrow = (IModularArrow) stack.getItem();
        arrow.setArrowMaterials(stack, headMaterial, shaftMaterial);
        return stack;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        if (headMaterial != null) tag.setString("ArrowHead", headMaterial);
        if (shaftMaterial != null) tag.setString("ArrowShaft", shaftMaterial);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        if (tag.hasKey("ArrowHead")) headMaterial = tag.getString("ArrowHead");
        if (tag.hasKey("ArrowShaft")) shaftMaterial = tag.getString("ArrowShaft");
    }
}