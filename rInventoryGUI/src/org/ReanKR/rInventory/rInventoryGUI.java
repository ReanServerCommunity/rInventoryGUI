package org.ReanKR.rInventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ReanKR.rInventory.API.ItemExtender;
import org.ReanKR.rInventory.Listener.InventoryListener;
import org.ReanKR.rInventory.Listener.InventoryRefresh;
import org.ReanKR.rInventory.Listener.PlayerListener;
import org.ReanKR.rInventory.config.InventoryConfig;
import org.ReanKR.rInventory.util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class rInventoryGUI extends JavaPlugin
{
	public static Map<Inventory, Map<String, ItemExtender>> InventoryExtendedItem = new HashMap<Inventory, Map<String, ItemExtender>>();
	public static Map<Inventory, List<Integer>> InventoryExtendedIndex = new HashMap<Inventory, List<Integer>>();
	public static Map<String, Inventory> InventoryIndexName = new HashMap<String, Inventory>();
	public static Map<String, Inventory> InventoryCommands = new HashMap<String, Inventory>();
	
	public static rInventoryGUI plugin;
	public static final String Prefix = null;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		InventoryConfig.InventoryLoad();
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new InventoryRefresh(), 0L, 100L);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		String cmd = command.getName();
		if(cmd.equalsIgnoreCase("rInventoryGUI.main"))
		{
			if(args.length < 1)
			{
				Player player = (Player)sender;
				msg(player, "&c/rw set <Inventory> <ItemName>");
				return false;
			}
			else
			{
				Player player = (Player)sender;
				if(args.length > 2)
				{
					if(args[0].equalsIgnoreCase("set"))
					{
						// InventoryItem(String Inventory, String Item);
						InventoryItem(player.getLocation(), args[1], args[2]);
						msg(player, "&aSet Complete.");
						return true;
					}
					// InventoryItem(String Inventory, String Item)
				}
				else
				{
					player.openInventory(InventoryIndexName.get(args[0]));
					return true;
				}
			}
		}
		return false;
	}
	
	private void InventoryItem(Location Location, String Inventory, String Item)
	{
		File File = FileManager.getFile("Inventory");
		FileConfiguration FileSection = FileManager.LoadSourceFile("Inventory");
		ConfigurationSection Section = FileSection.getConfigurationSection(Inventory + ".Items." + Item);
		Section.set("WORLD", Location.getWorld().getName());
		Section.set("LOCATION.X", Location.getX());
		Section.set("LOCATION.Y", Location.getY());
		Section.set("LOCATION.Z", Location.getZ());
		Section.set("LOCATION.Yaw", Location.getYaw());
		Section.set("LOCATION.Pitch", Location.getPitch());
		try
		{
			FileSection.save(File);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void msg(Player p, String str)
	{
		p.sendMessage(ChatColor.translateAlternateColorCodes('&' ,str));
	}
}

