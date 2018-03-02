package me.grundyboy34.deeds.gui;

import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;

public class GuiConfirmation extends GuiYesNo {
	private String context;
	
	public GuiConfirmation(GuiYesNoCallback parent, String context, String line1) {
		this(parent, context, line1, "");
	}

	public GuiConfirmation(GuiYesNoCallback parent, String context, String line1, String line2) {
		super(parent, line1, line2, 0);
		this.context = context;
	}
	
	public String getContext() {
		return context;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
