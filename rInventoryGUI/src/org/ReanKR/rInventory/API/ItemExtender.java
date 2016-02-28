package org.ReanKR.rInventory.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ItemExtender
{
	private Material Type;
	private String ItemName;
	private String World;
	private org.bukkit.Location Location;
	private List<String> Lore = new ArrayList<String>();
	private String Method;

	public ItemExtender(String ItemName)
	{
		this.ItemName = ItemName;
	}
	
	public void setWorld(String World)
	{
		this.World = World;
	}
	
	public void setLocation(org.bukkit.Location Location)
	{
		this.Location = Location;
	}
	
	public String getItemName()
	{
		return this.ItemName;
	}
	
	public String getWorldName()
	{
		return this.World;
	}
	
	public void setType(Material Type)
	{
		this.Type = Type;
	}
	
	public Material getType()
	{
		return this.Type;
	}
	
	public void setLore(List<String> Lore)
	{
		List<String> ChatRep = new ArrayList<String>();
		for(String Str : Lore)
		{
			ChatRep.add(ChatColor.translateAlternateColorCodes('&', Str));
		}
		this.Lore = ChatRep;
	}
	
	public List<String> getLore()
	{
		return this.Lore;
	}
	
	public org.bukkit.Location getLocation()
	{
		return this.Location;
	}

	public void setMethod(String Method)
	{
		this.Method = Method;
	}
	
	public String getMethod()
	{
		return this.Method;
	}
	
	public boolean hasMethod()
	{
		return this.Method.equalsIgnoreCase(null) ? false : true;
	}
}

