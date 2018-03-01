package me.grundyboy34.deeds.container.slots;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DeedStorageSlot extends SlotItemHandler {
	
	public DeedStorageSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == Items.EMERALD 
				|| stack.getItem() == Items.DIAMOND 
				|| stack.getItem() == Items.GOLD_INGOT
				|| stack.getItem() == Items.EMERALD
				|| stack.getItem() == Item.getItemFromBlock(Blocks.EMERALD_BLOCK)
				|| stack.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK)
				|| stack.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}
}
