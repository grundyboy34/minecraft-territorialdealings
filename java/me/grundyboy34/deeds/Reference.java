package me.grundyboy34.deeds;

import net.minecraft.util.ResourceLocation;

public class Reference {
	public static final String MODID = "deeds";
	public static final String NAME = "Deeds";
	public static final String VERSION = "3.0.0";
	public static final String DEPENDENCIES = "required-after:Forge@[10.13.2.1291,]";
	public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.10.2]";	
	public static final String ACCEPTABLE_REMOTE_VERSIONS = "*";
	public static final int OVERWORLD_DIM = 0;
	
	
	
	//Config settings
	public static final String CONFIG_FILE = "deeds.cfg";
	public static final String CONFIG_CATEGORY = "deeds";
	
	//default configs
	
	public static boolean defaultIsBuildProtected = true;
	public static boolean defaultIsInteractProtected = true;
	public static boolean defaultIsFireProtected = true;
	public static boolean defaultIsExplosionProtected = false;
	public static boolean defaultIsPvpProtected = false;
	public static boolean defaultIsSleepProtected = false;
	
	public static String isBuildProtectedComment = "Is building/destroying protected on claimed chunks?",
			isInteractProtectedComment = "Is interaction protected on claimed chunks? (opening doors, chests, etc)",
			isExplosionProtectedComment = "Are explosions protected on claimed chunks? (doesn't matter the source)",
			isFireProtectedComment = "Is fire protected on claimed chunks? (no fire spread if on)",
			isPvpProtectedComment = "Is pvp protected on claimed chunks? (no pvp damage if so)",
			isSleepProtectedComment = "Is sleep protected on claimed chunks? (Outsiders may sleep, but spawn point won't be set)",
			upkeepCostComment = "Cost per minecraft day per protected chunk",
			freeChunksComment = "amount of protected chunks immune to upkeep",
			maxVaultComment = "max amount that may be stored in player vault",
			emeraldValueComment = "amount 1 emerald is worth when deposited",
			diamondValueComment = "amount 1 diamond is worth when deposited",
			goldValueComment = "amount 1 gold ingot is worth when deposited",
			deedTiersComment = "Deed upgrade tier definitions. Array entries are radius:cost    -  Total area is (2r + 1)^2",
			daysBeforeDowngradeComment = "IRL days before deeds downgrade 1 tier. If at tier 0, deed is revoked";
	
	public static int defaultDaysBeforeDowngrade = 0;
	public static int defaultUpkeepCost = 0;
	public static int defaultFreeChunks = 1;
	public static int defaultMaxVault = 30000;
	public static int defaultEmeraldValue = 300;
	public static int defaultDiamondValue = 500;
	public static int defaultGoldValue = 100;
	public static String[] defaultDeedTiers = {"0:0", "1:1500", "3:10000", "6:25000"};
	//3528 per irl day for t1 @ 1 upkeep cost per chunk
	public static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(MODID, path);
	}
}
