package me.grundyboy34.deeds.network;

import io.netty.buffer.ByteBuf;
import me.grundyboy34.deeds.render.ChunkRenders;
import me.grundyboy34.deeds.render.ChunkRenders.RGBColor;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SetChunkRenderPacket implements IMessage, IMessageHandler<SetChunkRenderPacket, IMessage> {

	private ChunkPos pos;
	private RGBColor color;

	@Override
	public IMessage onMessage(SetChunkRenderPacket message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			ChunkRenders.setColor(pos, color);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			pos = new ChunkPos(buf.readInt(), buf.readInt());
			color = new RGBColor(buf.readByte(), buf.readByte(), buf.readByte());
		} catch (Exception e) {

		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try {
			buf.writeInt(pos.chunkXPos);
			buf.writeInt(pos.chunkZPos);
			buf.writeByte(color.red);
			buf.writeByte(color.green);
			buf.writeByte(color.blue);
		} catch (Exception e) {

		}
	}
}
