package me.grundyboy34.deeds.network;

import io.netty.buffer.ByteBuf;
import me.grundyboy34.deeds.savedata.DeedSaveHandler;
import me.grundyboy34.deeds.savedata.SerializedChunkPos;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class StartDeedPacket implements IMessage, IMessageHandler<StartDeedPacket, IMessage> {

	@Override
	public IMessage onMessage(StartDeedPacket message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			final EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			if (player != null) {
				player.getServerWorld().addScheduledTask(new Runnable() {

					@Override
					public void run() {
						DeedSaveHandler.instance().getCurrentSave().addDeed(new SerializedChunkPos(player.getPosition()), player.getUniqueID(), player.dimension, player.getGameProfile().getName());
					}
				});
			} 
		}

		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}
}
