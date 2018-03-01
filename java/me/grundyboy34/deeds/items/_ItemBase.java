package me.grundyboy34.deeds.items;

import me.grundyboy34.deeds.Main;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class _ItemBase extends Item
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
		this.setUnlocalizedName(Main.modID + "_" + this.name);
	}
	

	public void registerModel() 
	{
		// Item, metadata, resource location
		ModelResourceLocation loc = new ModelResourceLocation(Main.modID + ":" + this.name);
		ModelLoader.setCustomModelResourceLocation(this, 0, loc);
	}


	public void registerRecipes() 
	{
		// To be overriden by the individual items
	}

}
