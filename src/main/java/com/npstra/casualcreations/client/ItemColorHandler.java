package com.npstra.casualcreations.client;

import com.npstra.casualcreations.items.IModularTool;
import com.npstra.casualcreations.materials.HeadMaterial;
import com.npstra.casualcreations.materials.MaterialRegistry;
import com.npstra.casualcreations.materials.RodMaterial;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemColorHandler implements IItemColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof IModularTool) {
            IModularTool tool = (IModularTool) stack.getItem();
            if (tintIndex == 0) {
                String headName = tool.getHeadMaterial(stack);
                if (headName != null) {
                    HeadMaterial head = MaterialRegistry.getHead(headName);
                    if (head != null) {
                        return head.getColor();
                    }
                }
            } else if (tintIndex == 1) {
                String rodName = tool.getRodMaterial(stack);
                if (rodName != null) {
                    RodMaterial rod = MaterialRegistry.getRod(rodName);
                    if (rod != null) {
                        return rod.getColor();
                    }
                }
            }
        }
        return 0xFFFFFF;
    }
}