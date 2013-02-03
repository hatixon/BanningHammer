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
		Entity rightclick = event.getRightClicked();
		if(rightclick instanceof Player)
		{	
			Player rightclicked = (Player)rightclick;
			if(plugin.isActivated(player.getName()))
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
					if(plugin.isActivated(banner.getName()))
					{
						if(banner.getItemInHand().getTypeId() == plugin.getConfig().getInt("BanItemId"))
						{
							plugin.banPlayer(banner, bannee);
						}
						if(banner.getItemInHand().getTypeId() == plugin.getConfig().getInt("EncasingTool"))
						{
							trapPlayer(bannee);
							banner.sendMessage(new StringBuilder(pre).append(bannee.getName()).append(" has been trapped by you").toString());
							
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
        
        if(player.getLocation().add(0,2,0).getBlock().isEmpty())
        {
        	player.getLocation().add(0,2,0).getBlock().setTypeId(plugin.getConfig().getInt("BlockForEncasing"));
        }
        
        if(player.getLocation().add(0,-1,0).getBlock().isEmpty())
        {
        	player.getLocation().add(0,-1,0).getBlock().setTypeId(plugin.getConfig().getInt("BlockForEncasing"));
        }
    }
	@EventHandler
	public void onPlayerLogoff(PlayerQuitEvent e)
	{
		if(plugin.isActivated(e.getPlayer().getName()))
		{
			plugin.togglePlayer(e.getPlayer());
		}
	}
}
