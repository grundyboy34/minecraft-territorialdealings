package me.grundyboy34.deeds.network;

import me.grundyboy34.deeds.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class DeedsPacketHandler {
	private static SimpleNetworkWrapper wrapper;
	private static int packetId;
	private static boolean inited = false;
	
	private DeedsPacketHandler() {
		
	}
	
	public static final SimpleNetworkWrapper getNetworkWrapper() {
		return wrapper;
	}
	
	public static final void init() {
		if (inited) {
			return;
		}
		packetId = 0;
		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
		wrapper.registerMessage(OpenStoragePacket.class, OpenStoragePacket.class, packetId++, Side.SERVER);
		wrapper.registerMessage(StartDeedPacket.class, StartDeedPacket.class, packetId++, Side.SERVER);
		inited = true;
	}
}
