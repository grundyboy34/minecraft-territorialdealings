package me.grundyboy34.deeds.config;

import java.io.File;

import me.grundyboy34.deeds.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {
	private static Configuration config = null;
	public static File configFolder = null;
	private static File configFile = null;
	private static Config instance;

	public boolean isBuildProtected; // do deeds have build protections?
	public boolean isInteractProtected; // do deeds have interaction protections? (right-click, using doors, etc)
	public boolean isExplosionProtected; // do deeds have explosion protections?
	public boolean isFireProtected; // do deeds have fire-spread protections?
	public boolean isSleepProtected; //do deeds have sleep protections? (others can't sleep on deed)
	public boolean isPvpProtected; //do deeds have pvp protection?
	
	
	public int daysBeforeDowngrade, upkeepCost, freeChunks, maxVault, emeraldValue, diamondValue, goldValue;
	public String[] deedTiers;
	
	private Config() {
		try {
			configFolder = new File(Loader.instance().getConfigDir(), Reference.CONFIG_CATEGORY);
			configFolder.mkdirs();
			
			configFile = new File(configFolder, Reference.CONFIG_FILE);
			config = new Configuration(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Config instance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public void reload() {
		try {
			config.load();
			isBuildProtected = config.getBoolean("isBuildProtected", Reference.CONFIG_CATEGORY, Reference.defaultIsBuildProtected, Reference.isBuildProtectedComment);
			isInteractProtected = config.getBoolean("isInteractProtected", Reference.CONFIG_CATEGORY, Reference.defaultIsInteractProtected, Reference.isInteractProtectedComment);
			isExplosionProtected = config.getBoolean("isExplosionProtected", Reference.CONFIG_CATEGORY, Reference.defaultIsExplosionProtected, Reference.isExplosionProtectedComment);
			isFireProtected = config.getBoolean("isFireProtected", Reference.CONFIG_CATEGORY, Reference.defaultIsFireProtected, Reference.isFireProtectedComment);
			isSleepProtected = config.getBoolean("isSleepProtected", Reference.CONFIG_CATEGORY, Reference.defaultIsSleepProtected, Reference.isSleepProtectedComment);
			isPvpProtected = config.getBoolean("isPvpProtected", Reference.CONFIG_CATEGORY, Reference.defaultIsPvpProtected, Reference.isPvpProtectedComment);
			
			daysBeforeDowngrade = config.getInt("daysBeforeDowngrade", Reference.CONFIG_CATEGORY, Reference.defaultDaysBeforeDowngrade, 0, Integer.MAX_VALUE, Reference.daysBeforeDowngradeComment);
			upkeepCost = config.getInt("upkeepCost", Reference.CONFIG_CATEGORY, Reference.defaultUpkeepCost, 0, Integer.MAX_VALUE, Reference.upkeepCostComment);
			freeChunks = config.getInt("freeChunks", Reference.CONFIG_CATEGORY, Reference.defaultFreeChunks, 0, Integer.MAX_VALUE, Reference.freeChunksComment);
			maxVault = config.getInt("maxVault", Reference.CONFIG_CATEGORY, Reference.defaultMaxVault, 0, Integer.MAX_VALUE, Reference.maxVaultComment);
			emeraldValue = config.getInt("emeraldValue", Reference.CONFIG_CATEGORY, Reference.defaultEmeraldValue, 0, maxVault, Reference.emeraldValueComment);
			diamondValue = config.getInt("diamondValue", Reference.CONFIG_CATEGORY, Reference.defaultDiamondValue, 0, maxVault, Reference.diamondValueComment);
			goldValue = config.getInt("goldValue", Reference.CONFIG_CATEGORY, Reference.defaultGoldValue, 0, maxVault, Reference.goldValueComment);
			deedTiers = config.getStringList("deedTiers", Reference.CONFIG_CATEGORY, Reference.defaultDeedTiers, Reference.deedTiersComment);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getDeedTierRadius(int tier) {
		if (deedTiers.length - 1 >= tier && tier >= 0) {
			String[] split = deedTiers[tier].split(":");
			if (split.length == 2) {
				try {
					return Integer.parseInt(split[0]);
				} catch (Exception e) {
					return 0;
				}
			}
		}
		return 0;
	}
	
	public int maxDeedTier() {
		return deedTiers.length - 1;
	}
	
	public int getDeedTierCost(int tier) {
		if (deedTiers.length - 1 >= tier && tier >= 0) {
			String[] split = deedTiers[tier].split(":");
			if (split.length == 2) {
				try {
					return Integer.parseInt(split[1]);
				} catch (Exception e) {
					return 0;
				}
			}
		}
		return 0;
	}
}
