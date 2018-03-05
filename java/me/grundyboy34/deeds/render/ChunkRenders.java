package me.grundyboy34.deeds.render;

import java.util.HashMap;

import me.grundyboy34.deeds.savedata.SerializedChunkPos;
import net.minecraft.util.math.ChunkPos;

public class ChunkRenders {
	private static HashMap<SerializedChunkPos, RGBColor> chunkMap = new HashMap<SerializedChunkPos, RGBColor>();
	
	public static void setColor(ChunkPos pos, RGBColor color) {
		chunkMap.putIfAbsent(new SerializedChunkPos(pos), color);
	}
	
	public static RGBColor getColor(ChunkPos pos) {
		return chunkMap.get(new SerializedChunkPos(pos));
	}
	
	public static class RGBColor {
		public byte red, green, blue;
		
		public RGBColor(byte red, byte green, byte blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
	}
}
