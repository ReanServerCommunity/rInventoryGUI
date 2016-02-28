package org.ReanKR.rInventory.API;

import org.bukkit.inventory.Inventory;

public class InventoryExtender
{
	private boolean Sound;
	private String Command;
	private Inventory Inv;
	
	public InventoryExtender(Inventory Inventory)
	{
		this.Inv = Inventory;
	}
	
	public void setCommand(String Command)
	{
		this.Command = Command;
	}

	public void setSound(boolean b)
	{
		this.Sound = b;
	}

	public boolean isSoundEnabled()
	{			
		return this.Sound;
	}
	
	public String getCommand()
	{
		return this.Command;
	}
	
	public String getName()
	{
		return this.Inv.getName();
	}
}