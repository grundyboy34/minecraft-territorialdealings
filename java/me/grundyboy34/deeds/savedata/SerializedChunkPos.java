package me.grundyboy34.deeds.savedata;

import java.io.Serializable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class SerializedChunkPos extends ChunkPos implements Serializable {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -3604757397182129992L;

	public SerializedChunkPos(int x, int z) {
		super(x, z);
	}
	
	public SerializedChunkPos(ChunkPos pos) {
		super(pos.chunkXPos, pos.chunkZPos);
	}
	
	public SerializedChunkPos(BlockPos pos) {
		super(pos);
	}

}
