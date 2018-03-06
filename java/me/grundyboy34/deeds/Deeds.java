package me.grundyboy34.deeds;

import me.grundyboy34.deeds.proxy.SharedProxy;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSIONS, acceptableRemoteVersions = Reference.ACCEPTABLE_REMOTE_VERSIONS)
public class Deeds {
	// Let's overhaul this... make it user-based instead, keeping track of said
	// users and what factions they're part of AND what their role is in them.
	// Also separate save data from logic, for territories and chunks
	// Additionally, get away from leader-only items, since crafting them cannot be
	// reliably checked on both server and client side.
	// Also, let items register and load their own models and recipes. (Likely be
	// deriving them all from a base item)

	@Instance
	public static Deeds instance;

	@SidedProxy(serverSide = "me.grundyboy34.deeds.proxy.ServerProxy", clientSide = "me.grundyboy34.deeds.proxy.ClientProxy")
	public static SharedProxy sharedProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		sharedProxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		sharedProxy.init(event);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		sharedProxy.serverLoad(event);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		sharedProxy.serverStopping(event);
	}

	@SubscribeEvent
	public void onBlockPlaceEvent(PlaceEvent event) {
		sharedProxy.onBlockPlaceEvent(event);
	}

	@SubscribeEvent
	public void onBlockHarvestEvent(HarvestDropsEvent event) {
		sharedProxy.onBlockHarvestEvent(event);
	}

	@SubscribeEvent
	public void onBlockBreakEvent(BreakEvent event) {
		sharedProxy.onBlockBreakEvent(event);
	}

	@SubscribeEvent
	public void onRightClickBlock(RightClickBlock event) {
		sharedProxy.onRightClickBlock(event);
	}

	@SubscribeEvent
	public void onLeftClickBlock(LeftClickBlock event) {
		sharedProxy.onLeftClickBlock(event);
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		sharedProxy.onBucketFill(event);
	}

	@SubscribeEvent
	public void onExplosion(Detonate event) {
		sharedProxy.onExplosion(event);
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		sharedProxy.onWorldTick(event);
	}

	@SubscribeEvent
	public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
		sharedProxy.onPlayerSetSpawn(event);
	}
	
	@SubscribeEvent
	public void onChunkWatch(ChunkWatchEvent.Watch event) {
		sharedProxy.onChunkWatch(event);
	}
	
	@SubscribeEvent
	public void onChunkWatch(ChunkWatchEvent.UnWatch event) {
		sharedProxy.onChunkUnWatch(event);
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		sharedProxy.onLivingHurt(event);
	}
	
	@SubscribeEvent
	public void onSpawnSet(PlayerSetSpawnEvent event) {
		sharedProxy.onSpawnSet(event);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void lastRender(RenderWorldLastEvent event) {
		/*Minecraft.getMinecraft().theWorld.getChunkProvider();
		sharedProxy.lastRender(event);*/
	}

	/*
	 * static void checkAutoSave() { int saveTick =
	 * TerritoryHandler.getSaveData().getSaveTick(); int maxTick = saveTickInterval;
	 * 
	 * if (saveTick < maxTick) { // Not yet
	 * TerritoryHandler.getSaveData().setSaveTick(saveTick + 1);
	 * 
	 * return; } else { // Reset TerritoryHandler.getSaveData().setSaveTick(0); }
	 * 
	 * console("Autosaving...");
	 * 
	 * SaveHandler.saveFactionsToFile(configDir, worldDir,
	 * TerritoryHandler.getSaveData()); }
	 */
}
