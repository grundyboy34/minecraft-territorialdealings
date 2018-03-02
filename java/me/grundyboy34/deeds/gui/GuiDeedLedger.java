package me.grundyboy34.deeds.gui;

import java.io.IOException;

import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.network.DeedsPacketHandler;
import me.grundyboy34.deeds.network.OpenStoragePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiDeedLedger extends GuiScreen {
	public static final String DEED_DISBAND_CONTEXT = "deed_disband";

	private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation("textures/gui/book.png");
	private static final String CLAIM_TEXT = I18n.format("deeds.chunkClaim", new Object[0]),
			UNCLAIM_TEXT = I18n.format("deeds.chunkUnclaim", new Object[0]),
			DEPOSIT_TEXT = I18n.format("deeds.vaultButton", new Object[0]),
			LAND_STATUS_TITLE = I18n.format("deeds.currentClaimed", new Object[0]),
			UPKEEP_TITLE = I18n.format("deeds.currentUpkeep", new Object[0]),
			VAULT_TITLE = I18n.format("deeds.vault", new Object[0]),
			LONGEVITY_TITLE = I18n.format("deeds.upkeepLongevity", new Object[0]),
			DISBAND_CONFIRMATION_TEXT = I18n.format("deeds.disbandPrompt", new Object[0]);

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

	public EntityPlayer getPlayer() {
		return this.player;
	}

	@Override
	public void initGui() {
		x = (this.width - 192) / 2;
		midX = (x + (192 / 2));
		y = 2;
		this.buttonList.clear();
		this.chunkButton = this
				.addButton(new GuiButton(1, x + ((192 - 50) / 2), y + 182 - (192 / 3), 50, 20, CLAIM_TEXT));
		this.depositButton = this
				.addButton(new GuiButton(2, x + ((192 - 50) / 2), y + 182 - (192 / 3) + 20, 50, 20, DEPOSIT_TEXT));
		/*
		 * this.disbandButton = this.addButton(new GuiButton(3, x + ((192 - 50)/2), y +
		 * 182 - (192 / 3) + 40, 50, 15, I18n.format("deeds.disbandButton", new
		 * Object[0])));
		 */
		this.disbandButton = this.addButton(new GuiButtonClose(3, x + ((192 - 100) / 2) + 88, y + 8, 20, 20));
		this.nameField = new GuiTextField(0, fontRendererObj, x + ((192 - 100) / 2) - 10, y + 10, 95, 15);
		this.nameField.setEnabled(true);
		this.nameField.setText("test");
		this.nameField.setFocused(false);
		this.nameField.setMaxStringLength(15);
		this.nameField.setVisible(true);
		this.updateButtons();
	}

	private void updateButtons() {
		this.chunkButton.visible = true;
		this.depositButton.visible = true;
		this.disbandButton.visible = true;

		// change claim text based on if chunk is owned;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button == this.chunkButton) {
				DeedsPacketHandler.getNetworkWrapper().sendToServer(new OpenStoragePacket());
				this.player.openGui(Deeds.instance, DeedsGuiHandler.DEED_STORAGE, player.worldObj, player.chunkCoordX,
						player.chunkCoordY, player.chunkCoordZ);

			} else if (button == this.disbandButton) {
				this.mc.displayGuiScreen(new GuiConfirmation(this, DEED_DISBAND_CONTEXT, DISBAND_CONFIRMATION_TEXT));
			}
		}
	}

	@Override
	public void confirmClicked(boolean result, int id) { //occurs when GuiConfirmation performs callback
		if (!result) {
			this.mc.displayGuiScreen(this); //re-opens this gui if no was pressed
		} else { // if yes was pressed
			if (mc.currentScreen instanceof GuiConfirmation) {
				GuiConfirmation confirmScreen = (GuiConfirmation) mc.currentScreen;
				switch (confirmScreen.getContext()) {
				case DEED_DISBAND_CONTEXT:
					// code to disband deed - packet
					break;
				default:
					return;
				}
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
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
		this.drawTexturedModalRect(x, 2, 0, 0, 192, 192);
		this.nameField.drawTextBox();
		drawCenteredStringNoShadow(LAND_STATUS_TITLE, midX, y + 40, 0);
		drawCenteredStringNoShadow(UPKEEP_TITLE, midX, y + 50, 0);
		drawCenteredStringNoShadow(VAULT_TITLE, midX, y + 60, 0);
		drawCenteredStringNoShadow(LONGEVITY_TITLE, midX, y + 70, 0);
		//drawCenteredStringNoShadow() MC: IRL:
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		System.out.println("Ledger closed");
	}
	
	private void drawCenteredStringNoShadow(String text, int x, int y, int color) { //draws centered string with no shadow
		this.fontRendererObj.drawString(text, centeredPositionForText(text, x), y, color);
	}
	
	private int centeredPositionForText(String text, int xOffset) { //centers the x position
		return (xOffset - this.fontRendererObj.getStringWidth(text) / 2);
	}
}
