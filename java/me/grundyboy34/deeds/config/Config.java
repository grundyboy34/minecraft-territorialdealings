package me.grundyboy34.deeds.config;

import java.io.File;

import me.grundyboy34.deeds.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {
	private static Configuration config = null;
	private static File configFolder = null;
	private static File configFile = null;
	private static Config instance;

	public boolean isBuildProtected; // do deeds have build protections?
	public boolean isInteractProtected; // do deeds have interaction protections? (right-click, using doors, etc)
	public boolean isExplosionProtected; // do deeds have explosion protections?
	public boolean isFireProtected; // do deeds have fire-spread protections?
	public boolean isSleepProtected; //do deeds have sleep protections? (others can't sleep on deed)
	public boolean isPvpProtected; //do deeds have pvp protection?
	
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
			/*onItemTossEnable = config.getBoolean("onItemTossEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultOnItemTossEnable, "Enables unification when a player drops items.");
			onItemPickupEnable = config.getBoolean("onItemPickupEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultOnItemPickupEnable, "Enables unification when items are picked up.");
			inPlayerInventoryEnable = config.getBoolean("inPlayerInventoryEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultInPlayerInventoryEnable, "Enables unification in player inventory.");
			inOtherInventoryEnable = config.getBoolean("inOtherInventoryEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultInOtherInventoryEnable,
					"Enables unification in other inventories accessed by player. IE: Player opening a chest, now unifies contents of chest.");
			onLivingDropsEnable = config.getBoolean("onLivingDropsEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultOnLivingDropsEnable,
					"Enables unification on living entity drops. IE: player and mob deaths");
			onBlockHarvestEnable = config.getBoolean("onBlockHarvestEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultOnBlockHarvestEnable, "Enables unification on block drops. IE: mining a block");
			onChunkLoadEnable = config.getBoolean("onChunkLoadEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultOnChunkLoadEnable,
					"Enables unification on chunk load. #WARNING#, can cause lag! Only enable to unify blocks in the world that have already been places/generated");
			inTileEntityEnable = config.getBoolean("inTileEntityEnable", Reference.CONFIG_CATEGORY,
					Reference.defaultInTileEntityEnable,
					"Enables unification in tile entities (chests, etc) on world tick. #WARNING# Can possibly cause lag if lots of chests are around.");
			allowedPrefixes = config.getStringList("allowedPrefixes", Reference.CONFIG_CATEGORY,
					Reference.defaultAllowedPrefixes, "Allowed oreDictionary Prefixes");
			disallowedNames = config.getStringList("disallowedNames", Reference.CONFIG_CATEGORY,
					Reference.defaultDisallowedNames,
					"Disallowed oreDictionary names: Use f3+h in your client WITH THE MOD to see oreDict names");
			disallowedIds = config.getStringList("disallowedIds", Reference.CONFIG_CATEGORY,
					Reference.defaultDisallowedIds, "Disallowed item Ids: Use f3+h in your client to see item ids");
			priorityModIds = config.getStringList("priorityModIds", Reference.CONFIG_CATEGORY,
					Reference.defaultPriorityModIds, "Mods with priority high enough to be specified above");
			*/
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
