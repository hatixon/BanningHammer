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
		String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").append(ChatColor.YELLOW).toString();
		if(cmd.getName().equalsIgnoreCase("bh"))
		{
			if(sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You can not use this. Players only!").toString());
			}else
			{
				Player player = (Player)sender;
				if(player.hasPermission("bh.admin"))
				{	
					plugin.togglePlayer(player);
				}else
				{
					player.sendMessage(new StringBuilder(pre).append("You don't have permission").toString());
				}
			}
		}
		return true;
	}
	
}