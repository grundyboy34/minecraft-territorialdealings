package me.grundyboy34.deeds.proxy;

import me.grundyboy34.deeds.items.ItemBase;

public class ClientProxy extends SharedProxy {
	
	@Override
	public void registerItem(ItemBase item) {
		super.registerItem(item);
		item.registerModel();
	}

}
