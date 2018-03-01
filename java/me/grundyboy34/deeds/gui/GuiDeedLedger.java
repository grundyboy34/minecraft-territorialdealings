package me.grundyboy34.deeds.gui;

import java.io.IOException;

import me.grundyboy34.deeds.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiDeedLedger extends GuiScreen {
	private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("textures/gui/book.png");

	private GuiButton chunkButton, depositButton, disbandButton;
	private GuiTextField nameField;
	private EntityPlayer player;
	
	private int x, y, midX;

	public GuiDeedLedger(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	public void initGui() {
		x =  (this.width - 192) / 2;
		midX = (x + (192/2));
		y = 2;
		this.buttonList.clear();
		this.chunkButton = this.addButton(new GuiButton(1, x + ((192 - 50)/2), y + 182 - (192 / 3), 50, 15,
				I18n.format("deeds.chunkClaim", new Object[0])));
		this.depositButton = this.addButton(new GuiButton(2, x + ((192 - 50)/2), y + 182 - (192 / 3) + 20, 50, 15,
				I18n.format("deeds.depositButton", new Object[0])));
		this.disbandButton = this.addButton(new GuiButton(3, x + ((192 - 50)/2), y + 182 - (192 / 3) + 40, 50, 15,
				I18n.format("deeds.disbandButton", new Object[0])));
		this.nameField = new GuiTextField(0, fontRendererObj, x + ((192 - 100)/2), y + 10, 100, 15);
		this.nameField.setEnabled(true);
		this.nameField.setText("test");
		this.nameField.setFocused(false);
		this.nameField.setMaxStringLength(25);
		this.nameField.setVisible(true);
		this.updateButtons();
	}

	private void updateButtons() {
		this.chunkButton.visible = true;
		this.depositButton.visible = true;
		this.disbandButton.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button == this.chunkButton) {
				this.player.openGui(Main.modID, DeedsGuiHandler.DEED_STORAGE, player.worldObj, player.chunkCoordX, player.chunkCoordY, player.chunkCoordZ);
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);

		if (this.nameField.isFocused()) {
			this.nameField.textboxKeyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	 protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
		this.drawTexturedModalRect(x, 2, 0, 0, 192, 192);
		this.nameField.drawTextBox();
		this.drawCenteredString(this.fontRendererObj, "Land Claimed: ", midX, y + 40, 0xffffff);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
