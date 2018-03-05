package me.grundyboy34.deeds.network;

import io.netty.buffer.ByteBuf;
import me.grundyboy34.deeds.render.ChunkRenders;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class RemoveChunkRenderPacket implements IMessage, IMessageHandler<RemoveChunkRenderPacket, IMessage> {

	private ChunkPos pos;

	@Override
	public IMessage onMessage(RemoveChunkRenderPacket message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			ChunkRenders.setColor(pos, null);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			pos = new ChunkPos(buf.readInt(), buf.readInt());
		} catch (Exception e) {
			
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try {
			buf.writeInt(pos.chunkXPos);
			buf.writeInt(pos.chunkZPos);
		} catch (Exception e) {
			
		}
	}
}
