package me.grundyboy34.deeds.proxy;

import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.config.Config;
import me.grundyboy34.deeds.gui.DeedsGuiHandler;
import me.grundyboy34.deeds.items.DeedLedger;
import me.grundyboy34.deeds.items._ItemBase;
import me.grundyboy34.deeds.network.DeedsPacketHandler;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SharedProxy {

	public void preInit(FMLPreInitializationEvent event) {
		registerItem(new DeedLedger());
		Config.instance().reload();
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Deeds.instance, new DeedsGuiHandler());
		DeedsPacketHandler.init();
	}

	public void registerItem(_ItemBase item) {
		GameRegistry.register(item);
		item.registerRecipes();
	}

	public void onBlockPlaceEvent(PlaceEvent event) {
	}

	public void onBlockHarvestEvent(HarvestDropsEvent event) {
	}

	public void onBlockBreakEvent(BreakEvent event) {
	}

	public void onRightClickBlock(RightClickBlock event) {
	}

	public void onLeftClickBlock(LeftClickBlock event) {
	}

	public void onBucketFill(FillBucketEvent event) {
	}

	public void onExplosion(Detonate event) {
	}

	public void onWorldTick(WorldTickEvent event) {
	}

	public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
	}
	
	public void serverLoad(FMLServerStartingEvent event) {
	}

	public void serverStopping(FMLServerStoppingEvent event) {
	}
}
