package com.npstra.casualcreations.items.projectile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModularArrow {
    String TAG_HEAD = "ArrowHead";
    String TAG_SHAFT = "ArrowShaft";

    default void setArrowMaterials(ItemStack stack, String head, String shaft) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        tag.setString(TAG_HEAD, head);
        tag.setString(TAG_SHAFT, shaft);
    }

    default String getHeadMaterial(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(TAG_HEAD) ? tag.getString(TAG_HEAD) : null;
    }

    default String getShaftMaterial(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(TAG_SHAFT) ? tag.getString(TAG_SHAFT) : null;
    }
}