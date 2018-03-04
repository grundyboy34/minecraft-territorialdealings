package me.grundyboy34.deeds.savedata;

import java.io.Serializable;
import java.util.UUID;

public class ChunkSaveData implements Serializable {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 398175364746169435L;
	
	
	private UUID ownerId;
	private boolean hasProtections;
	
	public ChunkSaveData(UUID owner) {
		this(owner, false);
	}
	
	public ChunkSaveData(UUID owner, boolean protections) {
		this.ownerId = owner;
		this.hasProtections = protections;
	}
	
	public UUID getOwner() {
		return ownerId;
	}
	
	public boolean hasProtections() {
		return hasProtections;
	}
	
	public void setProtections(boolean protections) {
		this.hasProtections = protections;
	}
	
	
	@Override
	public boolean equals(Object data) {
		if (this == data) {
			return true;
		} else if (!(data instanceof ChunkSaveData)) {
			return false;
		} else {
			ChunkSaveData otherData = (ChunkSaveData) data;
			return otherData.getOwner().equals(this.getOwner());
		}
	}

}
