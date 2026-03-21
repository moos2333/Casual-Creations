package com.npstra.casualcreations.gui;

import com.npstra.casualcreations.block.TileEntityDiamondAnvil;
import com.npstra.casualcreations.container.ContainerDiamondAnvil;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiDiamondAnvil extends GuiContainer {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
    private final ContainerDiamondAnvil container;
    private final InventoryPlayer playerInv;
    private final TileEntityDiamondAnvil te;
    private GuiTextField nameField;

    public GuiDiamondAnvil(InventoryPlayer playerInv, TileEntityDiamondAnvil te) {
        super(new ContainerDiamondAnvil(playerInv, te, te.getWorld(), playerInv.player));
        this.container = (ContainerDiamondAnvil) this.inventorySlots;
        this.playerInv = playerInv;
        this.te = te;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.nameField = new GuiTextField(0, fontRenderer, x + 62, y + 20, 103, 12);
        this.nameField.setMaxStringLength(35);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setTextColor(16777215);
        this.nameField.setCanLoseFocus(true);
        this.nameField.setFocused(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        drawDurabilityTooltip(mouseX, mouseY);
        this.nameField.drawTextBox();
    }

    private void drawDurabilityTooltip(int mouseX, int mouseY) {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        if (mouseX >= x + 152 && mouseX <= x + 168 && mouseY >= y + 5 && mouseY <= y + 21) {
            String text = I18n.format("gui.diamond_anvil.durability", te.getDurability(), TileEntityDiamondAnvil.MAX_DURABILITY);
            this.drawHoveringText(text, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int durability = te.getDurability();
        int barHeight = (int)((float)durability / TileEntityDiamondAnvil.MAX_DURABILITY * 13.0F);
        if (barHeight > 0) {
            this.drawTexturedModalRect(x + 152, y + 5, 176, 0, barHeight, 16);
        }

        this.drawTexturedModalRect(x + 62, y + 20, 0, 166, 103, 12);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.format("tile.casualcreations.diamond_anvil.name");
        this.fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title)) / 2, 6, 4210752);
        this.fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 4210752);

        String durabilityStr = te.getDurability() + "/" + TileEntityDiamondAnvil.MAX_DURABILITY;
        this.fontRenderer.drawString(durabilityStr, xSize - 45 - fontRenderer.getStringWidth(durabilityStr), 10, 4210752);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.player.closeScreen();
        } else {
            this.nameField.textboxKeyTyped(typedChar, keyCode);
            this.container.updateItemName(this.nameField.getText());
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.nameField.updateCursorCounter();
    }
}