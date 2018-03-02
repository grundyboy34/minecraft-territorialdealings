package me.grundyboy34.deeds.savedata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

public class SaveData implements Serializable {
	

	/**
	 * generated
	 */
	private static final long serialVersionUID = 8882998640523378936L;

	// This is what gets saved to file
	private HashMap<UUID, DeedSaveData> deedMap = new HashMap<UUID, DeedSaveData>();
	private HashMap<UUID, VaultSaveData> vaultMap = new HashMap<UUID, VaultSaveData>();

	private int currentSaveTick; // Keeping track across server restarts how long we've gone without saving
	private int currentUpkeepTick; // tick for processing deed upkeeps


	
	public DeedSaveData getDeedSaveData(UUID owner) {
		return deedMap.get(owner);
	}
	
	public VaultSaveData getVaultSaveData(UUID owner) {
		return vaultMap.get(owner);
	}
	
	public HashMap<UUID, DeedSaveData> getDeedMap() {
		return this.deedMap;
	}

	public Iterator<Entry<UUID, DeedSaveData>> getDeedMapIterator() {
		return this.deedMap.entrySet().iterator();
	}
	
	public HashMap<UUID, VaultSaveData> getVaultMap() {
		return this.vaultMap;
	}
	
	public Iterator<Entry<UUID, VaultSaveData>> getVaultMapIterator() {
		return this.vaultMap.entrySet().iterator();
	}

	public int getUpkeepTick() {
		return this.currentUpkeepTick;
	}

	public void setUpkeepTick(int set) {
		this.currentUpkeepTick = set;
	}

	public int getSaveTick() {
		return this.currentSaveTick;
	}

	public void setSaveTick(int set) {
		this.currentSaveTick = set;
	}
}
