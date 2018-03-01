package me.grundyboy34.deeds.items;

import java.util.List;

import me.grundyboy34.deeds.Main;
import me.grundyboy34.deeds.TerritoryHandler;
import me.grundyboy34.deeds.savedata._FactionSaveData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FactionRenameOrder extends _ItemBase
{
	public FactionRenameOrder()
	{
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setCreativeTab(CreativeTabs.TOOLS);

		this.registerWithName("factionnameorder");
	}
	
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) 
	{ 
		return "Faction Name Order";
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		
		if (stack.hasDisplayName())
		{
			list.add("Red hot letters are burned");
			list.add("into this sheet of paper.");
		}
		else
		{
			list.add("A blank sheet of paper.");
			list.add("Give it a name on an anvil first.");
		}
		
		list.add(TextFormatting.BLUE + "Use this to change the");
		list.add(TextFormatting.BLUE + "name of your faction.");
		list.add(" ");
		list.add(TextFormatting.YELLOW + "LEADER-usable only.");
		list.add(TextFormatting.RED + "Used up on successful rename.");
    }
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (world.isRemote) { return new ActionResult(EnumActionResult.PASS, stack); }	// Not doing this on client side
	
		if (!stack.hasDisplayName())
		{
			// This needs a name via anvil first.
			
			return new ActionResult(EnumActionResult.FAIL, stack);
		}
		
		_FactionSaveData faction = TerritoryHandler.getFactionFromLeader(player);
		
		if (faction == null)
		{
			// You aren't the leader of anything
			return new ActionResult(EnumActionResult.FAIL, stack);
		}
		
		// Alright, you are the leader of something
		faction.setName(stack.getDisplayName());
		
		Main.console("Player " + player.getName() + " renamed faction to " + faction.getName() + ".");
		
		// Deplete the stack (Should only ever be one on here)
		stack.stackSize -= 1;
		
		// Send a notification to all faction members
		Main.sendMessageToFactionMembers(world, faction.getID(), "Your faction has been renamed to " + faction.getName() + "!");
		
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	
	@Override
	public void registerRecipes() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(this), 
				Items.PAPER, 
				Items.BLAZE_POWDER
		);
	}
}
