package me.grundyboy34.deeds.proxy;

import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;

import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.Reference;
import me.grundyboy34.deeds.config.Config;
import me.grundyboy34.deeds.gui.DeedsGuiHandler;
import me.grundyboy34.deeds.items.DeedLedger;
import me.grundyboy34.deeds.items._ItemBase;
import me.grundyboy34.deeds.network.DeedsPacketHandler;
import me.grundyboy34.deeds.savedata.ChunkSaveData;
import me.grundyboy34.deeds.savedata.DeedSaveData;
import me.grundyboy34.deeds.savedata.DeedSaveHandler;
import me.grundyboy34.deeds.savedata.SerializedChunkPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.DimensionManager;
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
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
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
		if (event.side.isServer() && event.phase == Phase.END) {
			DeedSaveHandler.instance().getCurrentSave().tick();
			if (DeedSaveHandler.instance().getCurrentSave().getTick() >= 24000) {
				Iterator<Entry<UUID, DeedSaveData>> iter = DeedSaveHandler.instance().getCurrentSave().getDeedMapIterator();

				while (iter.hasNext()) {
					Entry<UUID, DeedSaveData> currentEntry = iter.next();
					UUID owner = currentEntry.getKey();
					DeedSaveData data = currentEntry.getValue();
				
					//process offline cycles
					if (event.world.getPlayerEntityByUUID(owner) == null) {
						data.incrementOfflineCycle();
					} else {
						data.resetOfflineCycles();
					}
					
					//72 cycles per day: 1 cycle = 20 mins
					if (data.getOfflineCycles() >= Config.instance().daysBeforeDowngrade * 72) {
						data.setDeedTier(data.getDeedTier() - 1);
						data.resetOfflineCycles();
						if (data.getDeedTier() < 0) {
							DeedSaveHandler.instance().getCurrentSave().removeDeed(owner);
							continue;
						}
					}
					
					int maxRadius = Config.instance().getDeedTierRadius(data.getDeedTier());

					boolean frontierGrow = data.getFrontierRadius() < maxRadius
							&& data.getFrontierRadius() <= data.getCapitolRadius();
					boolean frontierShrink = data.getFrontierRadius() > maxRadius
							&& data.getFrontierRadius() > data.getCapitolRadius();
					boolean capitolGrow = data.getCapitolRadius() < maxRadius
							&& data.getCapitolRadius() < data.getFrontierRadius();
					boolean capitolShrink = data.getCapitolRadius() > maxRadius
							&& data.getCapitolRadius() >= data.getFrontierRadius();

					if (frontierGrow || frontierShrink) {
						int offset = frontierGrow ? 1 : 0;
						for (ChunkPos pos : getAllRadialChunks(data.getCapitolChunkPos(),
								data.getFrontierRadius() + offset)) {
							if (offset > 0) {
								DeedSaveHandler.instance().getCurrentSave().addChunk(new SerializedChunkPos(pos),
										owner);
							} else {
								DeedSaveHandler.instance().getCurrentSave().removeChunk(new SerializedChunkPos(pos));
							}
						}
						data.setFrontierRadius(data.getFrontierRadius() + (offset > 0 ? offset : -1));
						continue;
					} else if (capitolGrow) {
						// grow counter goes 0 to (8r - 1)
						ChunkPos pos = getNextRadialChunk(data.getCapitolChunkPos(), data.getCapitolRadius(),
								data.getGrowCounter());
						ensureProtections(new SerializedChunkPos(pos), true);
						data.setGrowCounter(data.getGrowCounter() + 1);
						if (data.isAtMaxGrowCounter()) {
							data.setCapitolRadius(data.getCapitolRadius() + 1);
							data.setGrowCounter(0);
						}
						continue;
					} else if (capitolShrink) {
						// grow counter goes 0 to (8r - 1)
						ChunkPos pos = getNextRadialChunk(data.getCapitolChunkPos(), data.getCapitolRadius(),
								data.getGrowCounter());
						ensureProtections(new SerializedChunkPos(pos), false);
						data.setGrowCounter(data.getGrowCounter() - 1);
						if (data.isAtMinGrowCounter()) {
							data.setCapitolRadius(data.getCapitolRadius() - 1);
							data.setGrowCounter(data.maxGrowCounter());
						}
						continue;
					} else if (data.getCapitolRadius() == 0) {
						ensureProtections(data.getCapitolChunkPos(), true);
						continue;

					}
				}

				DeedSaveHandler.instance().getCurrentSave().setTick(0);
				DeedSaveHandler.instance().saveDeeds();
			}
		}
	}

	public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
	}
	
	public void serverLoad(FMLServerStartingEvent event) {
		Reference.WORLD_DIR = DimensionManager.getCurrentSaveRootDirectory();// Hand me that world name
		DeedSaveHandler.instance().loadDeeds();
	}

	public void serverStopping(FMLServerStoppingEvent event) {
		DeedSaveHandler.instance().saveDeeds();
	}
	
	
	public void ensureProtections(SerializedChunkPos pos, boolean protections) {
		ChunkSaveData data = DeedSaveHandler.instance().getCurrentSave().getChunkSaveData(pos);
		if (data != null) {
			data.setProtections(protections);
		}
	}

	public ChunkPos[] getAllRadialChunks(ChunkPos center, int radius) {
		ChunkPos[] result = new ChunkPos[8 * radius];
		for (int counter = 0; counter < 8 * radius; counter++) {
			result[counter] = getNextRadialChunk(center, radius, counter);
		}
		return result;
	}

	// radius = distance square reaches from center
	// radialCounter = current step in iteration. Ranges from 0 to (8*radius) - 1
	public ChunkPos getNextRadialChunk(ChunkPos center, int radius, int radialCounter) {
		int modulus = radialCounter % 8;
		int division = Math.floorDiv(radialCounter, 8);
		int xOffset = 0, zOffset = 0;
		switch (modulus) {
		case 0: // ++
			xOffset = radius;
			zOffset = (division != 0 ? radius - division : 0);
			break;
		case 1:// --
			xOffset = radius * -1;
			zOffset = (division != 0 ? (radius * -1) + division : 0);
			break;
		case 2:// ++
			xOffset = (division != 0 ? radius : 0);
			zOffset = radius - division;
			break;
		case 3:// --
			xOffset = (division != 0 ? radius * -1 : 0);
			zOffset = (radius * -1) + division;
			break;
		case 4:// ++
			xOffset = radius - division;
			zOffset = radius;
			break;
		case 5:// --
			xOffset = (radius * -1) + division;
			zOffset = radius * -1;
			break;
		case 6:// +-
			xOffset = radius - division;
			zOffset = radius * -1;
			break;
		case 7:// -+
			xOffset = (radius * -1) + division;
			zOffset = radius;
			break;
		}
		return new ChunkPos(center.chunkXPos + xOffset, center.chunkZPos + zOffset);
	}
}
