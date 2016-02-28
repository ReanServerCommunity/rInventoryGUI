package org.ReanKR.rInventory.Listener;

import org.ReanKR.rInventory.rInventoryGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void PlayerCommand(PlayerCommandPreprocessEvent event)
	{
		if(rInventoryGUI.InventoryCommands.containsKey(event.getMessage()))
		{
			event.setCancelled(true);
			event.getPlayer().openInventory(rInventoryGUI.InventoryCommands.get(event.getMessage()));
		}
	}

}
