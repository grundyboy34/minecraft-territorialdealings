package me.grundyboy34.deeds.items;

import me.grundyboy34.deeds.Deeds;
import me.grundyboy34.deeds.gui.DeedsGuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class DeedLedger extends _ItemBase {
	public DeedLedger() {
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setCreativeTab(CreativeTabs.TOOLS);

		this.registerWithName("deedledger");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return "Deed Ledger";
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (true) {
			player.openGui(Deeds.instance, DeedsGuiHandler.DEED_LEDGER, world, player.chunkCoordX, player.chunkCoordY,
					player.chunkCoordZ);
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		if (world.isRemote) {
			return new ActionResult(EnumActionResult.PASS, stack);
		} // Not doing this on client side

		BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
		Chunk chunk = world.getChunkFromBlockCoords(pos);

		// Step 1, does someone already own this chunk?
	//	DeedSaveData faction = TerritoryHandler.getFactionByChunkPos(world.provider.getDimension(),
		//		chunk.getChunkCoordIntPair());

		//if (faction != null) {
			// They do, so not letting this happen.
			//Deeds.sendMessageToPlayer(player, "Faction '" + faction.getName() + "' already claimed this chunk.");

		//	return new ActionResult(EnumActionResult.FAIL, stack);
		//}

		// Step 1.1, are you already the leader of a faction?
		//if (TerritoryHandler.isPlayerLeader(player)) {
			//Deeds.sendMessageToPlayer(player, "You are already the leader of a faction.");

		//	return new ActionResult(EnumActionResult.FAIL, stack);
		//}

		// Step 2, marking a new claim
	//	faction = TerritoryHandler.startFactionInChunk(world, player, chunk);

		// Step 3, consuming the item
		stack.stackSize = 0;

		// Step 4, confirmation
	/*	Deeds.sendMessageToPlayer(player, "You started a new faction! It's called'" + faction.getName() + "'.");
		Deeds.sendMessageToPlayer(player, "You can rename it with a Faction Name Order and an anvil.");

		// SFX
		Deeds.startFireworks(player);
		Deeds.startFireworks(player);*/

		return new ActionResult(EnumActionResult.PASS, stack);
	}
}
