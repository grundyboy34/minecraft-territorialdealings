package me.grundyboy34.deeds.gui;

import me.grundyboy34.deeds.Reference;
import me.grundyboy34.deeds.container.DeedStorageContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiDeedStorage extends GuiContainer {

	private EntityPlayer player;
	
	public GuiDeedStorage(EntityPlayer player) {
		super(new DeedStorageContainer(player.inventory));
		setGuiSize(176, 166);
		this.player = player;
	}
	
	

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Reference.getResourceLocation("textures/gui/deed_storage.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
