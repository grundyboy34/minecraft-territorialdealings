package me.grundyboy34.deeds.savedata;

import java.io.Serializable;

import me.grundyboy34.deeds.config.Config;

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
		addAmount(val, false);
	}
	
	public void addAmount(int val, boolean refund) {
		if (val < 0) {
			subtractAmount(val * -1);
		} else if(Integer.MAX_VALUE - val >= vault) {
			vault += val;
		}
		if (vault > Config.instance().maxVault && !refund) {
			vault = Config.instance().maxVault;
		}
	}
	
	public void subtractAmount(int val) {
		if (val < 0) {
			addAmount(val * -1);
		} else if (Integer.MIN_VALUE + val <= vault) {
			vault -= val;
		}
		if (vault < 0) {
			vault = 0;
		}
	}
}
