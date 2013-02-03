package com.github.hatixon.banninghammer;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandConfuse implements CommandExecutor
{
	public static BanningHammer plugin;
	private final String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").append(ChatColor.YELLOW).toString();
	public CommandConfuse(BanningHammer instance)
	{
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) 
	{
		if(cmd.getName().equalsIgnoreCase("confuse"))
		{
			if(!sender.hasPermission("bh.confuse"))
			{
				sender.sendMessage(new StringBuilder(pre).append("You don't have permission").toString());
			}
			if(args.length > 2)
			{
				sender.sendMessage(new StringBuilder(pre).append("Too many arguments!").toString());
				return true;
			}
			if(args.length < 1)
			{
				sender.sendMessage(new StringBuilder(pre).append("Specify a player and how long you would like them to be confused for. 6000 = 5 minutes").toString());
				return true;
			}
			if(args.length < 2)
			{
				sender.sendMessage(new StringBuilder(pre).append("Specify how long you would like the player to be confused for. 6000 = 5 minutes").toString());
				return true;
			}
			if(args.length == 2)
			{
				String player = args[0];
				int time = 0;
				try{
					time = Integer.valueOf(args[1]);
				}catch(NumberFormatException e)
				{
					sender.sendMessage(new StringBuilder(pre).append("Please use numbers when specifying ticks!").toString());
					return true;
				}
				if(time > 30000)
				{
					sender.sendMessage(new StringBuilder(pre).append(" Too high. 30000 max amount!").toString());
					return true;
				}
                Player arr[] = plugin.getServer().getOnlinePlayers();
                int len = arr.length;
                for(int i = 0; i < len; i++)
                {
                	Player player1 = arr[i];
                	if(player1.getName().toLowerCase().equals(player.toLowerCase()))
                	{
                		player1.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, 1));
                		sender.sendMessage(new StringBuilder(pre).append(player1.getName()).append(" has been confused for ").append(time).append(" ticks").toString());
                		return true;
                	}	
                }
                sender.sendMessage(new StringBuilder(pre).append(player).append(" can not be found.").toString());
                return true;
			}
		}
		return true;
	}
}
