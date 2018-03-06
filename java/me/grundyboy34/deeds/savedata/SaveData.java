package me.grundyboy34.deeds.savedata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;

public class SaveData implements Serializable {
	

	/**
	 * generated
	 */
	private static final long serialVersionUID = 8882998640523378936L;

	// This is what gets saved to file
	private HashMap<SerializedChunkPos, ChunkSaveData> chunkMap = new HashMap<SerializedChunkPos, ChunkSaveData>();
	private HashMap<UUID, DeedSaveData> deedMap = new HashMap<UUID, DeedSaveData>();
	private HashMap<UUID, VaultSaveData> vaultMap = new HashMap<UUID, VaultSaveData>();

	private int currentTick;

	
	public DeedSaveData getDeedSaveData(UUID owner) {
		return deedMap.get(owner);
	}
	
	public VaultSaveData getVaultSaveData(UUID owner) {
		return vaultMap.get(owner);
	}

	public Iterator<Entry<UUID, DeedSaveData>> getDeedMapIterator() {
		return this.deedMap.entrySet().iterator();
	}
	
	public Iterator<Entry<UUID, VaultSaveData>> getVaultMapIterator() {
		return this.vaultMap.entrySet().iterator();
	}

	public int getTick() {
		return this.currentTick;
	}

	public void setTick(int set) {
		this.currentTick = set;
	}
	
	public void tick() {
		this.currentTick++;
	}
	
	public boolean hasPermissions(EntityPlayer player) {
		return hasPermissions(new ChunkPos(player.getPosition()), player);
	}
	
	public boolean hasPermissions(ChunkPos pos, EntityPlayer player) {
		ChunkSaveData chunkData = getChunkSaveData(new SerializedChunkPos(pos));
		if (chunkData == null || !chunkData.hasProtections() || chunkData.getOwner().equals(player.getUniqueID())) {
			return true;
		} else {
			DeedSaveData deedData = getDeedSaveData(chunkData.getOwner());
			return deedData.isTrustee(player.getGameProfile().getName());
		}
	}
	
	public void addDeed(SerializedChunkPos capitol, UUID owner, int world, String name) {
		if (deedMap.putIfAbsent(owner, new DeedSaveData(world, name)) == null) {
			addChunk(capitol, owner);
		}
		deedMap.get(owner).setDeedTier(2);
		System.out.println("Deed added " + deedMap.get(owner).getCapitolChunkPos() + " : " + deedMap.get(owner).getDeedTier());
	}
	
	public DeedSaveData getDeed(UUID owner) {
		return this.deedMap.get(owner);
	}
	
	public void removeDeed(UUID owner) {
		DeedSaveData deedData = deedMap.remove(owner);
		if (deedData != null) {
			for (SerializedChunkPos chunk : deedData.getChunks()) {
				removeChunk(chunk);
			}
		}
	}

	public void addChunk(SerializedChunkPos pos, UUID owner) {
		addChunk(pos, new ChunkSaveData(owner));
	}
	
	public void addChunk(SerializedChunkPos pos, ChunkSaveData chunkData) {
		if (chunkMap.putIfAbsent(pos,  chunkData) == null) {
			DeedSaveData deedData = getDeedSaveData(chunkData.getOwner());
			if (deedData != null) {
				deedData.addChunk(pos);
			}
		}
	}

	public void removeChunk(SerializedChunkPos pos) {
		ChunkSaveData chunkData = chunkMap.remove(pos);
		if (chunkData != null) {
			DeedSaveData deedData = getDeedSaveData(chunkData.getOwner());
			if (deedData != null) {
				deedData.removeChunk(pos);
			}
		}
	}

	public ChunkSaveData getChunkSaveData(SerializedChunkPos pos) {
		return chunkMap.get(pos);
	}

}
