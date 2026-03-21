package com.npstra.casualcreations.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModularTool {
    String TAG_HEAD = "HeadMaterial";
    String TAG_ROD = "RodMaterial";

    default void setMaterials(ItemStack stack, String head, String rod) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        tag.setString(TAG_HEAD, head);
        tag.setString(TAG_ROD, rod);
    }

    default String getHeadMaterial(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(TAG_HEAD) ? tag.getString(TAG_HEAD) : null;
    }

    default String getRodMaterial(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        return tag != null && tag.hasKey(TAG_ROD) ? tag.getString(TAG_ROD) : null;
    }
}