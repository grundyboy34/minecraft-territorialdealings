package me.grundyboy34.deeds.network;

import io.netty.buffer.ByteBuf;
import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.gui.DeedsGuiHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class OpenStoragePacket implements IMessage, IMessageHandler<OpenStoragePacket, IMessage> {

	@Override
	public IMessage onMessage(OpenStoragePacket message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			final EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			if (player != null) {
				player.getServerWorld().addScheduledTask(new Runnable() {

					@Override
					public void run() {
						player.openGui(Deeds.instance, DeedsGuiHandler.DEED_STORAGE, player.worldObj, player.chunkCoordX,
								player.chunkCoordY, player.chunkCoordZ);
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
