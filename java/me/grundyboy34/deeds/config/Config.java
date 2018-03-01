/*package me.grundyboy34.deeds.config;

import java.io.File;

import com.arcaneware.unifier.shared.info.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {
	private static Configuration config = null;
	private static File configFile = null;
	private static Config instance;

	// configs
	public boolean onItemTossEnable;
	public boolean onItemPickupEnable;
	public boolean inPlayerInventoryEnable;
	public boolean inOtherInventoryEnable;
	public boolean onLivingDropsEnable;
	public boolean onBlockHarvestEnable;

	public boolean onChunkLoadEnable;
	public boolean inTileEntityEnable;

	public String[] allowedPrefixes;
	public String[] disallowedNames;
	public String[] disallowedIds;
	public String[] priorityModIds;

	private Config() {
		try {
			configFile = new File(Loader.instance().getConfigDir(), Reference.CONFIG_FILE);
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
			onItemTossEnable = config.getBoolean("onItemTossEnable", Reference.CONFIG_CATEGORY,
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
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/