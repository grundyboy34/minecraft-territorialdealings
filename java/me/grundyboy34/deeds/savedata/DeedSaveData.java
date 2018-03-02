package me.grundyboy34.deeds.savedata;

import java.io.Serializable;
import java.util.ArrayList;

import net.minecraft.util.math.ChunkPos;

public class DeedSaveData implements Serializable {
	

	/**
	 * generated
	 */
	private static final long serialVersionUID = -1671404989009350726L;
	
	private String deedName; // Name of the deed
	private int dimensionID; // Deed is world specific

	private ArrayList<ChunkSaveData> chunkList; // The chunks that are part of this deed

	// private ArrayList<UUID> memberIDs; // Many members

	public DeedSaveData(int world, String name) {
		this.dimensionID = world;
		this.deedName = name;

		// Init
		this.chunkList = new ArrayList<ChunkSaveData>();

		// set protectiosn based on configs
	}

	public int getDimensionID() {
		return this.dimensionID;
	}

	public ArrayList<ChunkSaveData> getChunkList() {
		return this.chunkList;
	}

	public int getChunkAmount() {
		return this.chunkList.size();
	}

	public String getName() {
		return this.deedName;
	}

	public void setName(String name) {
		this.deedName = name;
	}

	public void addChunk(ChunkPos chunk) {
		ChunkSaveData data = new ChunkSaveData(chunk);
		if (this.chunkList.contains(data)) {
			this.chunkList.add(data);
		}
	}

	public void removeChunk(ChunkPos pos) {
		ChunkSaveData data = new ChunkSaveData(pos);
		this.chunkList.remove(data);
	}


	public boolean isChunkClaimed(ChunkPos pos) {
		ChunkSaveData data = new ChunkSaveData(pos);
		return this.chunkList.contains(data);
	}
}
