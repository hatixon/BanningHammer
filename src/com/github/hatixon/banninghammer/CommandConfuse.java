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
			if(args.length > 2)
			{
				sender.sendMessage(new StringBuilder(pre).append("Too many arguments!").toString());
				return true;
			}
			if(args.length < 2)
			{
				sender.sendMessage(new StringBuilder(pre).append("Specify a player and how long you would like them to be confused for. 6000 = 5 minutes").toString());
				return true;
			}
			if(args.length == 2)
			{
				String player = args[0];
				int time = Integer.valueOf(args[1]);
                Player arr[] = plugin.getServer().getOnlinePlayers();
                int len = arr.length;
                for(int i = 0; i < len; i++)
                {
                	Player player1 = arr[i];
                	if(player1.getName().toLowerCase().equals(player.toLowerCase()))
                	{
                		player1.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, 1));
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
