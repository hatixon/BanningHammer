package com.github.hatixon.banninghammer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

public class HitListener implements Listener
{
	public final String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").append(ChatColor.YELLOW).toString();
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
			if(player.getItemInHand().getTypeId() == plugin.getConfig().getInt("EncasingTool"))
			{
				trapPlayer(rightclicked);
				player.sendMessage(new StringBuilder(pre).append(rightclicked.getName()).append(" has been trapped by you").toString());
				
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
	private final BlockFace[] surrounding = new BlockFace[]
		{
			BlockFace.NORTH,
			BlockFace.NORTH_EAST,
			BlockFace.EAST,
			BlockFace.SOUTH_EAST,
			BlockFace.SOUTH,
			BlockFace.SOUTH_WEST,
			BlockFace.WEST,
			BlockFace.NORTH_WEST
		};
	
	public void trapPlayer(Player player)
    {
        Location[] locs = new Location[]{player.getLocation(),
        player.getLocation().add(0,1,0),
        player.getLocation().add(0,2,0),
        player.getLocation().add(0,-1,0)};
        for(Location loc : locs)
        {
			for(BlockFace bf : surrounding)
			{
				if(loc.getBlock().getRelative(bf, 1).isEmpty())
				{
					loc.getBlock().getRelative(bf, 1).setTypeId(plugin.getConfig().getInt("BlockForEncasing"));
				}
    		}
        }
        player.getLocation().add(0,2,0).getBlock().setTypeId(plugin.getConfig().getInt("BlockForEncasing"));
        player.getLocation().add(0,-1,0).getBlock().setTypeId(plugin.getConfig().getInt("BlockForEncasing"));
    }
}
