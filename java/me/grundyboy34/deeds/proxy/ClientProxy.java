package me.grundyboy34.deeds.proxy;

import me.grundyboy34.deeds.items._ItemBase;

public class ClientProxy extends SharedProxy {
	
	@Override
	public void registerItem(_ItemBase item) {
		super.registerItem(item);
		item.registerModel();
	}

}
