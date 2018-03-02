package me.grundyboy34.deeds.container;

import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.container.slots.DeedStorageSlot;
import me.grundyboy34.deeds.gui.DeedsGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class DeedStorageContainer extends Container {

	private ItemStackHandler handler;

	public DeedStorageContainer(IInventory playerInv) {
		handler = new ItemStackHandler();
		this.addSlotToContainer(new DeedStorageSlot(handler, 0, 81, 35));

		// The player's inventory slots
		int xPos = 8; // The x position of the top left player inventory slot on
						// our texture
		int yPos = 84; // The y position of the top left player inventory slot
						// on our texture

		// Player slots
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < this.handler.getSlots()) {
				// From the energy cell inventory to the player's inventory
				if (!this.mergeItemStack(current, this.handler.getSlots(), handler.getSlots() + 36, true))
					return null;
			} else {
				// From the player's inventory to the block breaker's inventory
				if (!this.mergeItemStack(current, 0, this.handler.getSlots(), false))
					return null;
			}

			if (current.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!player.worldObj.isRemote) {

			ItemStack itemstack = this.getSlot(0).getStack();

			if (itemstack != null) {
				player.dropItem(itemstack, false);
			}

		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return true;
	}

}
