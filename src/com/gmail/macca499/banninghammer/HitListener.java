package com.gmail.macca499.banninghammer;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class HitListener implements Listener
{
	public static BanningHammer plugin;
	
	public HitListener(BanningHammer instance)
	{
		plugin = instance;
	}
	
	@EventHandler
	public void touchytouchy(PlayerInteractEntityEvent event)
	{
		Player player = (Player)event.getPlayer();
		Player rightclicked = (Player)event.getRightClicked();
		if(plugin.getConfig().getBoolean(new StringBuilder().append("Toggled.").append(player.getName()).toString()) == true)
		{
			if(player.getItemInHand().getTypeId() == plugin.getConfig().getInt("BanItemId"))
			{
				plugin.banPlayer(player, rightclicked);
			}
		}
	}
	
	@EventHandler
	public void dmg(final EntityDamageEvent event) 
	{
		if(event instanceof EntityDamageByEntityEvent)
		{
			Entity e1 = event.getEntity();
			Entity e2 = ((EntityDamageByEntityEvent) event).getDamager();
			if(e1 instanceof Player) 
			{
				if(e2 instanceof Player) 
				{
					Player bannee = (Player)e1;
					Player banner = (Player)e2;
					if(plugin.getConfig().getBoolean(new StringBuilder().append("Toggled.").append(banner.getName()).toString()) == true)
					{
						if(banner.getItemInHand().getTypeId() == plugin.getConfig().getInt("BanItemId"))
						{
							plugin.banPlayer(banner, bannee);
						}
					}
				}
			}
		}
	}
}
