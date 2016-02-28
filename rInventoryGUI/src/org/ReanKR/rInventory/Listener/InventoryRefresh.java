package org.ReanKR.rInventory.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ReanKR.rInventory.rInventoryGUI;
import org.ReanKR.rInventory.API.ItemExtender;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class InventoryRefresh implements Runnable
{
	@Override
	public void run()
	{
		Set<Inventory> Inventories = rInventoryGUI.InventoryExtendedItem.keySet();
		for(Inventory InvName : Inventories)
		{
			for(Integer Index : rInventoryGUI.InventoryExtendedIndex.get(InvName))
			{
				ItemStack ItemStack = InvName.getItem(Index);
				ItemMeta ItemMeta = InvName.getItem(Index).getItemMeta();
				Map<String, ItemExtender> ItemExtends = rInventoryGUI.InventoryExtendedItem.get(InvName);
				List<String> Lore = ItemExtends.get(ChatColor.stripColor(ItemMeta.getDisplayName())).getLore();
				List<String> ReplaceLore = new ArrayList<String>();
				World world = ItemExtends.get(ChatColor.stripColor(ItemMeta.getDisplayName())).getLocation().getWorld();
				for(String LoreList : Lore)
				{
					if(LoreList.contains("%world_player%"))
					{
						ReplaceLore.add(LoreList.replaceAll("%world_player%", String.valueOf(world.getPlayers().size())));
					}
					else
					{
						ReplaceLore.add(LoreList);
					}
				}
				ItemStack.setType(ItemExtends.get(ChatColor.stripColor(ItemMeta.getDisplayName())).getType());
				if(world.getPlayers().size() != 0)
				{
					ItemStack.setAmount(world.getPlayers().size());
				}
				else
				{
					ItemStack.setAmount(1);
				}
				ItemMeta.setLore(ReplaceLore);
				ItemStack.setItemMeta(ItemMeta);
				InvName.setItem(Index, ItemStack);
			}
		}
	}
}
