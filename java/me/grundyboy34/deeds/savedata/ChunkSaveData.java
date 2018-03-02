package me.grundyboy34.deeds.savedata;

import java.io.Serializable;

import net.minecraft.util.math.ChunkPos;

public class ChunkSaveData implements Serializable
{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 7834634919669225883L;
	
	
	public int posX;
	public int posZ;
	
	//frontier chunks mark future expansion of deeds. Protections may not be active, but no deeds may be founded on or adjacent to them
	public boolean isFrontier; // is the chunk considered frontier?
	
	public ChunkSaveData(ChunkPos chunk) 
	{
		this.posX = chunk.chunkXPos;
		this.posZ = chunk.chunkZPos;
	}


	@Override
	public boolean equals(Object obj)
    {
        if (this == obj) 
        { 
        	return true; 	// this is us, so matches
        }	
        
        else if (!(obj instanceof ChunkSaveData))
        {
            return false;	// Cannot possibly be us
        }
        else
        {
        	// Does our position match?
        	ChunkSaveData chunkpos = (ChunkSaveData) obj;
        	
            return this.posX == chunkpos.posX && this.posZ == chunkpos.posZ;
        }
    }
}
