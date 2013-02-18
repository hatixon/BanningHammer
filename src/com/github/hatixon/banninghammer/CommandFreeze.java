package com.github.hatixon.banninghammer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFreeze implements CommandExecutor
{
	public final String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").append(ChatColor.YELLOW).toString();
	public final BanningHammer plugin;
	
	public CommandFreeze(BanningHammer instance)
	{
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
		if(cmd.getName().equalsIgnoreCase("freeze"))
		{
			if(args.length < 1)
			{
				sender.sendMessage(new StringBuilder(pre).append("Please specify a player you would like to freeze!").toString());
				return true;
			}
			
			if(sender.hasPermission("bh.freeze"))
			{
                Player arr[] = plugin.getServer().getOnlinePlayers();
                int len = arr.length;
                for(int i = 0; i < len; i++)
            	{
            		Player player2 = arr[i];
            		if(player2.getName().equalsIgnoreCase(args[0]))
            		{
            			if(player2.hasPermission("bh.bypass"))
            			{
            				sender.sendMessage(new StringBuilder(pre).append("You can not freeze this player!").toString());
            				return true;
            			}
    					if(plugin.freezePlayer(player2))
    					{
    						sender.sendMessage(new StringBuilder(pre).append(player2.getName()).append(" has been frozen!").toString());
    						if(!sender.getName().equalsIgnoreCase(args[0]))
    						{
    							player2.sendMessage(new StringBuilder(pre).append("You have been frozen!").toString());
    						}
    					}else
    					{
    						sender.sendMessage(new StringBuilder(pre).append(player2.getName()).append(" has been unfrozen!").toString());
    						if(!sender.getName().equalsIgnoreCase(args[0]))
    						{
    							player2.sendMessage(new StringBuilder(pre).append("You have been unfrozen!").toString());
    						}
    					}
    					return true;
            		}
            	}
                sender.sendMessage(new StringBuilder(pre).append(args[0]).append(" is not online!").toString());
			}
		}
		return true;
	}

}
