package org.ReanKR.rInventory.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ReanKR.rInventory.rInventoryGUI;
import org.ReanKR.rInventory.API.InventoryExtender;
import org.ReanKR.rInventory.API.ItemExtender;
import org.ReanKR.rInventory.util.FileManager;
import org.ReanKR.rInventory.util.SectionManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
public class InventoryConfig
{
	@SuppressWarnings("deprecation")
	public static void InventoryLoad()
	{
		YamlConfiguration Section = FileManager.LoadSourceFile("Inventory");
		Set<String> InventoryName = Section.getKeys(false);

		for(String Name : InventoryName)
		{
			Map<String, ItemExtender> ExtendedItem = new HashMap<String, ItemExtender>();
			List<Integer> SpecIndex = new ArrayList<Integer>();
			String Title = null;
			int Rows = 9;
			if(Section.contains(Name + ".Name")) Title = Section.getString(Name + ".Name");
			if(Section.contains(Name + ".Rows")) Rows = Section.getInt(Name + ".Rows");
			Inventory inv = Bukkit.createInventory(null, Rows*9, ChatColor.translateAlternateColorCodes('&', Title));
			InventoryExtender InvExtended = new InventoryExtender(inv);
			if(Section.contains(Name + ".Command"))
			{
				InvExtended.setCommand(Section.getString(Name + ".Command"));
				rInventoryGUI.InventoryCommands.put("/" + Section.getString(Name + ".Command"), inv);
			}
			if(Section.contains(Name + ".Sound")) InvExtended.setSound(Section.getBoolean(Name + "." + "Sound"));
			if(Section.contains(Name + ".Items"))
			{
				Set<String> ItemName = SectionManager.addSection(Section, Name + ".Items").getKeys(false);
				for(String IN : ItemName)
				{
					ConfigurationSection itemSection = Section.getConfigurationSection(Name + ".Items." + IN);
					Location location = new Location(null, 0, 0, 0); // Default location
					ItemStack Itemstack = new ItemStack(Material.BEDROCK); // Default location
					int X = 0;
					int Y = 0;
					byte Data = 0;
					int ID = 1;
					int Amount = 1;
					ItemMeta meta = Itemstack.getItemMeta();
					if(itemSection.contains("TYPE"))
					{
						if(itemSection.getString("TYPE").equalsIgnoreCase("CLOSE"))
						{
							if(itemSection.contains("ID")) ID = itemSection.getInt("ID");
							if(itemSection.contains("DATA-VALUE")) Data = Byte.parseByte(itemSection.getString("DATA-VALUE"));
							if(itemSection.contains("ITEM-X")) X = itemSection.getInt("ITEM-X");
							if(itemSection.contains("ITEM-Y")) Y = itemSection.getInt("ITEM-Y");
							Itemstack = new MaterialData(ID, Data).toItemStack(Amount);
							Itemstack.setItemMeta(meta);
							inv.setItem((X-1)+((Y - 1)* 9), new ItemStack(Itemstack));
							SpecIndex.add((X-1)+((Y - 1)* 9));
							ItemExtender ItemExtend = new ItemExtender(itemSection.getString("NAME"));
							ItemExtend.setType(Itemstack.getType());
							ItemExtend.setLore(itemSection.getStringList("LORE"));
							ItemExtend.setMethod("CLOSE");
							rInventoryGUI.InventoryIndexName.put(Name, inv);
							ExtendedItem.put(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', itemSection.getString("NAME"))), ItemExtend);
						}
					}
					else
					{
						if(itemSection.contains("NAME")) meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemSection.getString("NAME")));
						if(itemSection.contains("ID")) ID = itemSection.getInt("ID");
						if(itemSection.contains("DATA-VALUE")) Data = Byte.parseByte(itemSection.getString("DATA-VALUE"));
						//if(itemSection.contains("AMOUNT")) Amount = itemSection.getInt("AMOUNT");
						// if(itemSection.contains("ENCHANTMENT")) /* Do generate */
						if(itemSection.contains("WORLD")) location.setWorld(Bukkit.getWorld(itemSection.getString("WORLD")));
						if(itemSection.contains("LORE")) meta.setLore(itemSection.getStringList("LORE"));
						if(itemSection.contains("ITEM-X")) X = itemSection.getInt("ITEM-X");
						if(itemSection.contains("ITEM-Y")) Y = itemSection.getInt("ITEM-Y");
						if(itemSection.contains("LOCATION.X")) location.setX(Double.parseDouble(itemSection.getString("LOCATION.X")));
						if(itemSection.contains("LOCATION.Y")) location.setY(Double.parseDouble(itemSection.getString("LOCATION.Y")));
						if(itemSection.contains("LOCATION.Z")) location.setZ(Double.parseDouble(itemSection.getString("LOCATION.Z")));
						if(itemSection.contains("LOCATION.Yaw")) location.setPitch(Float.parseFloat(itemSection.getString("LOCATION.Yaw")));
						if(itemSection.contains("LOCATION.Pitch")) location.setPitch(Float.parseFloat(itemSection.getString("LOCATION.Pitch")));
						Itemstack = new MaterialData(ID, Data).toItemStack(Amount);
						Itemstack.setItemMeta(meta);
						inv.setItem((X-1)+((Y - 1)* 9), new ItemStack(Itemstack));
						SpecIndex.add((X-1)+((Y - 1)* 9));
						ItemExtender ItemExtend = new ItemExtender(itemSection.getString("NAME"));
						ItemExtend.setType(Itemstack.getType());
						ItemExtend.setLore(itemSection.getStringList("LORE"));
						rInventoryGUI.InventoryIndexName.put(Name, inv);
						ItemExtend.setLocation(location);
						ExtendedItem.put(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', itemSection.getString("NAME"))), ItemExtend);
					}
				}
			}
			rInventoryGUI.InventoryExtendedItem.put(inv, ExtendedItem);
			rInventoryGUI.InventoryExtendedIndex.put(inv, SpecIndex);
		}
	}
}