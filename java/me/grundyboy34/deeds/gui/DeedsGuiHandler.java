package me.grundyboy34.deeds.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class DeedsGuiHandler implements IGuiHandler {
	
	public static final int DEED_LEDGER = 0, DEED_STORAGE = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == DEED_STORAGE) {
			return new GuiDeedStorage(player).inventorySlots;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == DEED_LEDGER) {
			return new GuiDeedLedger(player);
		} else if (ID == DEED_STORAGE) {
			return new GuiDeedStorage(player);
		}
		return null;
	}

}
