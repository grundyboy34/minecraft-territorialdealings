package me.grundyboy34.deeds.items;

import me.grundyboy34.deeds.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBase extends Item
{
	private String name;	// The internal item name
	
	
	// To be called by the items that extend from us
	protected void registerWithName(String name)
	{
		if (name == null || name.isEmpty())
		{
			name = "unknown";
		}
		
		this.name = name;
		
		this.setRegistryName(this.name);
		this.setUnlocalizedName(Reference.MODID + "_" + this.name);
	}
	

	public void registerModel() 
	{
		// Item, metadata, resource location
		ModelResourceLocation loc = new ModelResourceLocation(Reference.MODID + ":" + this.name);
		ModelLoader.setCustomModelResourceLocation(this, 0, loc);
	}


	public void registerRecipes() 
	{
		// To be overriden by the individual items
	}

}
