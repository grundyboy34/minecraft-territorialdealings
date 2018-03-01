package me.grundyboy34.deeds.gui;

import me.grundyboy34.deeds.Main;
import me.grundyboy34.deeds.container.DeedStorageContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDeedStorage extends GuiContainer {

	public GuiDeedStorage(IInventory playerInv) {
		super(new DeedStorageContainer(playerInv));
		setGuiSize(176, 166);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Main.modID, "textures/gui/deed_storage.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
	}

}
