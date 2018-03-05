package me.grundyboy34.deeds.items;

import me.grundyboy34.deeds.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistryHelper {
	
	public enum DEED_ITEMS {
		
		DEED_LEDGER(new DeedLedger(), "deedledger"),
		DEED_MAP(new DeedMap(), "deedmap");
		
		private Item item;
		private String name;
		
		DEED_ITEMS(Item item, String name) {
			this.item = item;
			this.name = name;
		}
		
		public Item getItem() {
			return item;
		}
		
		public String getName() {
			return name;
		}
		
		
		public ItemStack asItemStack() {
			return asItemStack(0);
		}
		
		public ItemStack asItemStack(int size) {
			return new ItemStack(getItem(), size);
		}
	}
	
	public static void registerItems() {
		for (DEED_ITEMS entry : DEED_ITEMS.values()) {
			GameRegistry.register(entry.getItem());
		}
		//register item recipes
	}
	
	public static void registerModels() {
		for (DEED_ITEMS entry : DEED_ITEMS.values()) {
			ModelResourceLocation loc = new ModelResourceLocation(Reference.MODID + ":" + entry.getName());
			ModelLoader.setCustomModelResourceLocation(entry.getItem(), 0, loc);
		}
	}	

}
