package me.grundyboy34.deeds.savedata;

import java.io.Serializable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class SerializedChunkPos implements Serializable {

	 /** The X position of this Chunk Coordinate Pair */
    public final int chunkXPos;
    /** The Z position of this Chunk Coordinate Pair */
    public final int chunkZPos;
    
	/**
	 * generated
	 */
	private static final long serialVersionUID = -3604757397182129992L;

	public SerializedChunkPos() {
		this(0, 0); // only used for serialized loading
	}

	public SerializedChunkPos(int x, int z) {
		this.chunkXPos = x;
		this.chunkZPos = z;
	}

	public SerializedChunkPos(ChunkPos pos) {
		this(pos.chunkXPos, pos.chunkZPos);
	}

	public SerializedChunkPos(BlockPos pos) {
		this.chunkXPos = pos.getX() >> 4;
		this.chunkZPos = pos.getZ() >> 4;
	}
	
	public int hashCode()
    {
        int i = 1664525 * this.chunkXPos + 1013904223;
        int j = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
        return i ^ j;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof SerializedChunkPos))
        {
            return false;
        }
        else
        {
            SerializedChunkPos chunkpos = (SerializedChunkPos)p_equals_1_;
            return this.chunkXPos == chunkpos.chunkXPos && this.chunkZPos == chunkpos.chunkZPos;
        }
    }

}
