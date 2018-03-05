package me.grundyboy34.deeds.proxy;

import me.grundyboy34.deeds.items.DeedLedger;
import me.grundyboy34.deeds.items.ItemRegistryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends SharedProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ItemRegistryHelper.registerModels();
	}


	@Override
	public void lastRender(RenderWorldLastEvent event) {
		ItemStack mainHand = Minecraft.getMinecraft().thePlayer.getHeldItemMainhand();
		if (mainHand != null && mainHand.isItemEqual(ItemRegistryHelper.DEED_ITEMS.DEED_LEDGER.asItemStack())) {
			
		}
		Minecraft.getMinecraft().theWorld.getChunkProvider();
		event.getContext().drawBoundingBox(minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
	}
}
