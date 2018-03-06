package me.grundyboy34.deeds.proxy;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.Reference;
import me.grundyboy34.deeds.config.Config;
import me.grundyboy34.deeds.gui.DeedsGuiHandler;
import me.grundyboy34.deeds.items.ItemRegistryHelper;
import me.grundyboy34.deeds.network.DeedsPacketHandler;
import me.grundyboy34.deeds.savedata.ChunkSaveData;
import me.grundyboy34.deeds.savedata.DeedSaveData;
import me.grundyboy34.deeds.savedata.DeedSaveHandler;
import me.grundyboy34.deeds.savedata.SerializedChunkPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.DimensionManager;
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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class SharedProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ItemRegistryHelper.registerItems();
		Config.instance().reload();
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Deeds.instance, new DeedsGuiHandler());
		DeedsPacketHandler.init();
	}

	public void onBlockPlaceEvent(PlaceEvent event) {
		if (!event.getWorld().isRemote && Config.instance().isBuildProtected) {
			if (!DeedSaveHandler.instance().getCurrentSave().hasPermissions(new ChunkPos(event.getPos()),
					event.getPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	public void onBlockHarvestEvent(HarvestDropsEvent event) {
		if (!event.getWorld().isRemote && Config.instance().isBuildProtected && event.getHarvester() != null) {
			if (!DeedSaveHandler.instance().getCurrentSave().hasPermissions(new ChunkPos(event.getPos()),
					event.getHarvester())) {
				event.setCanceled(true);
			}
		}
	}

	public void onBlockBreakEvent(BreakEvent event) {
		if (!event.getWorld().isRemote && Config.instance().isBuildProtected) {
			if (!DeedSaveHandler.instance().getCurrentSave().hasPermissions(new ChunkPos(event.getPos()),
					event.getPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	public void onRightClickBlock(RightClickBlock event) {
		if (!event.getWorld().isRemote && Config.instance().isInteractProtected) {
			if (!DeedSaveHandler.instance().getCurrentSave().hasPermissions(new ChunkPos(event.getPos()),
					event.getEntityPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	public void onLeftClickBlock(LeftClickBlock event) {
		if (!event.getWorld().isRemote && Config.instance().isInteractProtected) {
			if (!DeedSaveHandler.instance().getCurrentSave().hasPermissions(new ChunkPos(event.getPos()),
					event.getEntityPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	public void onBucketFill(FillBucketEvent event) {
		if (!event.getWorld().isRemote && Config.instance().isInteractProtected) {
			if (!DeedSaveHandler.instance().getCurrentSave()
					.hasPermissions(new ChunkPos(event.getTarget().getBlockPos()), event.getEntityPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	public void onExplosion(Detonate event) {
		if (!event.getWorld().isRemote && Config.instance().isExplosionProtected
				&& event.getExplosion().getExplosivePlacedBy() instanceof EntityPlayer) {

			ChunkSaveData chunkData;
			for (Iterator<BlockPos> it = event.getAffectedBlocks().iterator(); it.hasNext();) {
				BlockPos current = it.next();
				chunkData = DeedSaveHandler.instance().getCurrentSave()
						.getChunkSaveData(new SerializedChunkPos(current));
				if (chunkData != null && chunkData.hasProtections()) {
					it.remove();
				}
			}
			for (Iterator<Entity> it = event.getAffectedEntities().iterator(); it.hasNext();) {
				Entity current = it.next();
				chunkData = DeedSaveHandler.instance().getCurrentSave()
						.getChunkSaveData(new SerializedChunkPos(current.getPosition()));
				if (chunkData != null && chunkData.hasProtections()) {
					it.remove();
				}
			}
		}
	}

	public void onLivingHurt(LivingHurtEvent event) {
		if (!event.getEntity().getEntityWorld().isRemote && Config.instance().isPvpProtected
				&& event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
			ChunkSaveData entityChunk = DeedSaveHandler.instance().getCurrentSave()
					.getChunkSaveData(new SerializedChunkPos(event.getEntity().getPosition()));
			if (entityChunk != null && entityChunk.hasProtections()) {
				event.setCanceled(true);
				return;
			}
			entityChunk = DeedSaveHandler.instance().getCurrentSave()
					.getChunkSaveData(new SerializedChunkPos(event.getSource().getSourceOfDamage().getPosition()));
			if (entityChunk != null && entityChunk.hasProtections()) {
				event.setCanceled(true);
			}
		}
	}

	public void onSpawnSet(PlayerSetSpawnEvent event) {
		if (!event.getEntity().getEntityWorld().isRemote && Config.instance().isSleepProtected) {
			if (!DeedSaveHandler.instance().getCurrentSave().hasPermissions(new ChunkPos(event.getNewSpawn()),
					event.getEntityPlayer())) {
				event.setCanceled(true);
			}
		}
	}

	public void onChunkWatch(ChunkWatchEvent.Watch event) {
		/*
		 * ChunkSaveData chunkData =
		 * DeedSaveHandler.instance().getCurrentSave().getChunkSaveData(new
		 * SerializedChunkPos(event.getChunk())); if (chunkData != null) {
		 * 
		 * }
		 */
	}

	public void onChunkUnWatch(ChunkWatchEvent.UnWatch event) {

	}

	public void lastRender(RenderWorldLastEvent event) {
	}

	public void onWorldTick(WorldTickEvent event) {
		if (event.side.isServer() && event.phase == Phase.END) {
			DeedSaveHandler.instance().getCurrentSave().tick();
			if (DeedSaveHandler.instance().getCurrentSave().getTick() >= 20) {
				Iterator<Entry<UUID, DeedSaveData>> iter = DeedSaveHandler.instance().getCurrentSave()
						.getDeedMapIterator();

				while (iter.hasNext()) {
					Entry<UUID, DeedSaveData> currentEntry = iter.next();
					UUID owner = currentEntry.getKey();
					DeedSaveData data = currentEntry.getValue();

					// process offline cycles
					if (event.world.getPlayerEntityByUUID(owner) == null) {
						data.incrementOfflineCycle();
					} else {
						data.resetOfflineCycles();
					}

					// 72 cycles per day: 1 cycle = 20 mins
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
							&& data.getFrontierRadius() > data.getCapitolRadius()
							&& data.isAtMaxGrowCounter();
					boolean capitolGrow = data.getCapitolRadius() < maxRadius
							&& data.getCapitolRadius() < data.getFrontierRadius();
					boolean capitolShrink = data.getFrontierRadius() > maxRadius
							&& data.getFrontierRadius() > 0;

					System.out.println(frontierGrow + ":" + frontierShrink + ":" + capitolGrow + ":" + capitolShrink);

					System.out.println(data.getFrontierRadius() + ":" + data.getCapitolRadius() + ":"
							+ data.getGrowCounter() + ":" + data.maxGrowCounter());

					if (frontierGrow || frontierShrink) {
						int offset = frontierGrow ? 1 : 0;
						for (SerializedChunkPos pos : getAllRadialChunks(data.getCapitolChunkPos(),
								data.getFrontierRadius() + offset)) {
							if (frontierGrow) {
								DeedSaveHandler.instance().getCurrentSave().addChunk(pos, owner);
							} else if (frontierShrink) {
								DeedSaveHandler.instance().getCurrentSave().removeChunk(pos);
							}
						}
						data.setFrontierRadius(data.getFrontierRadius() + (frontierGrow ? 1 : frontierShrink ? -1 : 0));
						continue;
					} else if (capitolGrow) {
						// grow counter goes 0 to (8r - 1)
						SerializedChunkPos pos = getNextRadialChunk(data.getCapitolChunkPos(), data.getFrontierRadius(),
								data.getGrowCounter());
						ensureProtections(pos, true);
						if (data.isAtMaxGrowCounter()) {
							data.setCapitolRadius(data.getCapitolRadius() + 1);
							data.setGrowCounter(data.minGrowCounter());
						} else {
							data.setGrowCounter(data.getGrowCounter() + 1);
						}
						continue;
					} else if (capitolShrink) {
						// grow counter goes 0 to (8r - 1)
						SerializedChunkPos pos = getNextRadialChunk(data.getCapitolChunkPos(), data.getFrontierRadius(),
								data.getGrowCounter());
						ensureProtections(pos, false);
						if (data.isAtMaxGrowCounter()) {
							data.setCapitolRadius(data.getCapitolRadius() - 1);
							data.setGrowCounter(data.minGrowCounter());
						} else {
							data.setGrowCounter(data.getGrowCounter() - 1);
						}
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
		DeedSaveHandler.instance().setCurrentSave(null);
	}

	public void ensureProtections(SerializedChunkPos pos, boolean protections) {
		ChunkSaveData data = DeedSaveHandler.instance().getCurrentSave().getChunkSaveData(pos);
		if (data != null) {
			data.setProtections(protections);
		}
	}

	public SerializedChunkPos[] getAllRadialChunks(SerializedChunkPos center, int radius) {
		SerializedChunkPos[] result = new SerializedChunkPos[8 * radius];
		for (int counter = 0; counter < 8 * radius; counter++) {
			result[counter] = getNextRadialChunk(center, radius, counter);
		}
		return result;
	}

	// radius = distance square reaches from center
	// radialCounter = current step in iteration. Ranges from 0 to (8*radius) - 1
	public SerializedChunkPos getNextRadialChunk(SerializedChunkPos center, int radius, int radialCounter) {
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
		case 2:// +-
			xOffset = (division != 0 ? radius : 0);
			zOffset = (radius * -1) + division;
			break;
		case 3:// -+
			xOffset = (division != 0 ? radius * -1 : 0);
			zOffset = radius - division;
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
		return new SerializedChunkPos(center.chunkXPos + xOffset, center.chunkZPos + zOffset);
	}
}
