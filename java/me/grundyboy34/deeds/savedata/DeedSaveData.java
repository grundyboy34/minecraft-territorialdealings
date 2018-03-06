package me.grundyboy34.deeds.savedata;

import java.io.Serializable;
import java.util.ArrayList;

import me.grundyboy34.deeds.config.Config;
import net.minecraft.util.math.ChunkPos;

public class DeedSaveData implements Serializable {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -1671404989009350726L;

	private String deedName; // Name of the deed
	private int dimensionID; // Deed is world specific
	private int offlineCycles;
	private ArrayList<String> trustees; // player names who bypass deed permissions
	private ArrayList<SerializedChunkPos> chunks; // our chunks. - This list has no authority, and simply takes updates
													// from main save data

	private int frontierRadius, capitolRadius, growCounter, deedTier;

	public DeedSaveData(int world, String name) {
		this.dimensionID = world;
		this.deedName = name;
		this.offlineCycles = 0;
		this.deedTier = 0;
		this.growCounter = 0;
		this.frontierRadius = 0;
		this.capitolRadius = -1;

		// Init
		this.trustees = new ArrayList<String>(15); // 15 lines on editable page
		this.chunks = new ArrayList<SerializedChunkPos>();
	}
	
	public void addChunk(SerializedChunkPos pos) {
		this.chunks.add(pos);
	}
	
	public void removeChunk(SerializedChunkPos pos) {
		this.chunks.remove(pos);
	}
	
	public ArrayList<SerializedChunkPos> getChunks() {
		return chunks;
	}

	public int getFrontierRadius() {
		return frontierRadius;
	}
	
	public boolean isAtMaxGrowCounter() {
		return isAtMaxGrowCounter(getFrontierRadius());
	}
	
	public boolean isAtMaxGrowCounter(int radius) {
		return getGrowCounter() == maxGrowCounter(radius);
	}
	
	public boolean isAtMinGrowCounter() {
		return getGrowCounter() == minGrowCounter();
	}
	
	public int minGrowCounter() {
		return 0;
	}
	
	public int maxGrowCounter() {
		return maxGrowCounter(getFrontierRadius());
	}
	
	public int maxGrowCounter(int radius) {
		if (radius < 1) {
			return 0;
		}
		return (radius * 8) - 1;
	}

	public int getGrowCounter() {
		return growCounter;
	}

	public void setGrowCounter(int count) {
		int min = minGrowCounter(), max = maxGrowCounter();
		if (count < min) {
			count = min;
		} else if (count > max) {
			count = max;
		}
		this.growCounter = count;
	}

	public int getCapitolRadius() {
		return capitolRadius;
	}

	public int getDeedTier() {
		if (deedTier < 0) {
			deedTier = 0;
		} else if (deedTier > Config.instance().maxDeedTier()) {
			deedTier = Config.instance().maxDeedTier();
		}
		return deedTier;
	}

	public void setFrontierRadius(int radius) {
		this.frontierRadius = radius;
	}

	public void setCapitolRadius(int radius) {
		this.capitolRadius = radius;
	}

	public void setDeedTier(int tier) {
		if (tier < 0) {
			tier = 0;
		} else if (tier > Config.instance().maxDeedTier()) {
			tier = Config.instance().maxDeedTier();
		}
		this.deedTier = tier;
	}
	
	public boolean addTrustee(String playerName) {
		if (trustees.size() == 15 || trustees.contains(playerName)) {
			return false;
		} else {
			trustees.add(playerName.toLowerCase());
			return true;
		}
	}

	public boolean isTrustee(String playerName) {
		return trustees.contains(playerName.toLowerCase());
	}

	public int getOfflineCycles() {
		return offlineCycles;
	}

	public void resetOfflineCycles() {
		offlineCycles = 0;
	}

	public void incrementOfflineCycle() {
		offlineCycles++;
	}

	public SerializedChunkPos getCapitolChunkPos() {
		if (this.chunks.size() > 0) {
			return this.chunks.get(0);
		} else {
			return null;
		}
	}

	public boolean inRange(ChunkPos pos, int range) {
		SerializedChunkPos capitolChunk = getCapitolChunkPos();
		if (capitolChunk == null) {
			return false;
		}
		return Math.abs(pos.chunkXPos - capitolChunk.chunkXPos) <= range
				&& Math.abs(pos.chunkZPos - capitolChunk.chunkZPos) <= range;
	}

	public int getDimensionID() {
		return this.dimensionID;
	}

	public String getName() {
		return this.deedName;
	}

	public void setName(String name) {
		this.deedName = name;
	}

}
