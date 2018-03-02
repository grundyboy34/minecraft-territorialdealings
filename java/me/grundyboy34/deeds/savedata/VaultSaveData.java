package me.grundyboy34.deeds.savedata;

import java.io.Serializable;

public class VaultSaveData implements Serializable {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 945592997028805L;

	private int vault;
	
	public VaultSaveData() {
		this(0);
	}
	
	public VaultSaveData(int vaultValue) {
		this.vault = vaultValue;
	}
	
	public int getAmount() {
		return vault;
	}
	
	public void addAmount(int val) {
		if (val < 0) {
			subtractAmount(val * -1);
		} else if(Integer.MAX_VALUE - val >= vault) {
			vault += val;
		}
	}
	
	public void subtractAmount(int val) {
		if (val < 0) {
			addAmount(val * -1);
		} else if (Integer.MIN_VALUE + val <= vault) {
			vault -= val;
		}
	}
}
