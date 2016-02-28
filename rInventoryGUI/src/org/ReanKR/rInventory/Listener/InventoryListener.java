package org.ReanKR.rInventory.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ReanKR.rInventory.rInventoryGUI;
import org.ReanKR.rInventory.API.ItemExtender;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class InventoryListener implements Listener
{
	// private Map<Player, Integer> Tid = new HashMap();
	
	@EventHandler
	public void InventoryOpen(InventoryOpenEvent event)
	{
		Player player = (Player) event.getPlayer();
		Inventory Inventory = event.getInventory();
		if(rInventoryGUI.InventoryExtendedItem.containsKey(Inventory))
		{
			player.playSound(event.getPlayer().getLocation(), Sound.NOTE_PLING ,2.0F, 1.3F);
			for(Integer Index : rInventoryGUI.InventoryExtendedIndex.get(Inventory))
			{
				ItemStack ItemStack = Inventory.getItem(Index);
				ItemMeta ItemMeta = Inventory.getItem(Index).getItemMeta();
				Map<String, ItemExtender> ItemExtends = rInventoryGUI.InventoryExtendedItem.get(Inventory);
				if(ItemMeta.hasLore())
				{
					List<String> Lore = ItemMeta.getLore();
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
					if(world.getPlayers().size() != 0)
					{
						ItemStack.setAmount(world.getPlayers().size());
					}
					else
					{
						ItemStack.setType(Material.BARRIER);
					}
					ItemMeta.setLore(ReplaceLore);
					ItemStack.setItemMeta(ItemMeta);
					Inventory.setItem(Index, ItemStack);
				}
				else
				{
					continue;
				}
			}
		}
		else
		{
			return;
		}
		
	}
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event)
	{
		Inventory Inventory = event.getInventory();
		if(rInventoryGUI.InventoryExtendedItem.containsKey(Inventory))
		{
			ItemExtender ExtendedItem;
			if(rInventoryGUI.InventoryExtendedItem.containsKey(event.getClickedInventory()) && event.getCurrentItem().hasItemMeta() && !(event.getCurrentItem().getType() == Material.AIR))
			{
				if(rInventoryGUI.InventoryExtendedItem.get(Inventory).get(ChatColor.stripColor(ChatColor.
						translateAlternateColorCodes('&',event.getCurrentItem().getItemMeta().getDisplayName()))).hasMethod())
				{
					if(rInventoryGUI.InventoryExtendedItem.get(Inventory).get(ChatColor.stripColor(ChatColor.
							translateAlternateColorCodes('&',event.getCurrentItem().getItemMeta().getDisplayName()))).getMethod().equalsIgnoreCase("CLOSE"))
					{
						event.getWhoClicked().closeInventory();
						return;
					}
				}
				if(rInventoryGUI.InventoryExtendedItem.get(Inventory).containsKey(ChatColor.stripColor(ChatColor.
						translateAlternateColorCodes('&',event.getCurrentItem().getItemMeta().getDisplayName()))))
				{
					ExtendedItem = rInventoryGUI.InventoryExtendedItem.get(Inventory).
						get(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',event.getCurrentItem().getItemMeta().getDisplayName())));
				}
				else
				{
					return;
				}
			}
			else
			{
				return;
			}
			event.getWhoClicked().teleport(ExtendedItem.getLocation());
			event.setCancelled(true);
		}
		else
		{
			return;
		}
	}
}
