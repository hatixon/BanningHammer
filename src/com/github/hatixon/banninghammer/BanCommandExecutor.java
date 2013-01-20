package com.github.hatixon.banninghammer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BanCommandExecutor implements CommandExecutor
{
	public static BanningHammer plugin;
	
	public BanCommandExecutor(BanningHammer instance)
	{
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
		String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").toString();
		if(cmd.getName().equalsIgnoreCase("bh"))
		{
			if(sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You can not use this. Players only!").toString());
			}else
			{
				Player player = (Player)sender;
				String uName = player.getName();
				if(!(args.length == 0))
				{
					player.sendMessage(new StringBuilder(pre).append("Parameters are 'on' and 'off' only!").toString());
				}else
				{
					if(player.hasPermission("bh.admin"))
					{	
						if(!plugin.getConfig().contains(new StringBuilder().append("Toggled.").append(uName).toString()))
						{
							plugin.getConfig().set(new StringBuilder().append("Toggled.").append(uName).toString(), true);
							plugin.saveConfig();
							player.sendMessage(new StringBuilder(pre).append("Activated!").toString());
							return true;
						}
						if(plugin.getConfig().getBoolean(new StringBuilder().append("Toggled.").append(uName).toString()) == true)
						{
							plugin.getConfig().set(new StringBuilder().append("Toggled.").append(uName).toString(), false);
							plugin.saveConfig();
							player.sendMessage(new StringBuilder(pre).append("Deactivated!").toString());
						}else
						if(plugin.getConfig().getBoolean(new StringBuilder().append("Toggled.").append(uName).toString()) == false)
						{
							plugin.getConfig().set(new StringBuilder().append("Toggled.").append(uName).toString(), true);
							plugin.saveConfig();
							player.sendMessage(new StringBuilder(pre).append("Activated!").toString());
						}
					}else
					{
						player.sendMessage(new StringBuilder(pre).append("You don't have permission").toString());
					}
				}
			}
		}
		return false;
	}
}