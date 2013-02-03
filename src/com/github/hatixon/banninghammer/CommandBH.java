package com.github.hatixon.banninghammer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandBH implements CommandExecutor
{
	public static BanningHammer plugin;
	private final String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").append(ChatColor.YELLOW).toString();
	public CommandBH(BanningHammer instance)
	{
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
		
		if(cmd.getName().equalsIgnoreCase("bh"))
		{
			if(sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("You can not use this. Players only!").toString());
			}else
			{
				if(args.length < 1)
				{
					sender.sendMessage(new StringBuilder(pre).append("/bh help").toString());
					return true;
				}
				Player player = (Player)sender;
				String param = args[0];
				if(param.equalsIgnoreCase("toggle"))
				{
					if(player.hasPermission("bh.toggle"))
					{	
						if(plugin.togglePlayer(player))
						{
							player.sendMessage(pre +
									"Activated!");
						}else
						{
							player.sendMessage(pre +
									"Deactivated!");
						}
						
					}else
					{
						player.sendMessage(new StringBuilder(pre).append("You don't have permission").toString());
						return true;
					}
				}else
				if(param.equalsIgnoreCase("set"))
				{
					if(!player.hasPermission("bh.change"))
					{
						player.sendMessage(new StringBuilder(pre).append("You don't have permission").toString());
						return true;
					}
					if(!(args.length > 1))
					{
						player.sendMessage(new StringBuilder(pre).append("/bh help").toString());
						return true;
					}
					String param2 = args[1];
					if(param2.equalsIgnoreCase("BanTool"))
					{
						if(!(args.length > 2))
						{
							player.sendMessage(new StringBuilder(pre).append("Please specify the ID of the tool you would like to use.").toString());
							return true;
						}
						int tool = 0;
						try{
							tool = Integer.valueOf(args[2]);
						}catch(NumberFormatException e)
						{
							player.sendMessage(new StringBuilder(pre).append("Please use numbers when specifying ID").toString());
							return true;
						}
						Material block = Material.getMaterial(tool);
						String bName = null;
						try{
							bName = block.toString();
						}catch(Exception e)
						{
							player.sendMessage(new StringBuilder(pre).append("ID doesn't exist!").toString());
							return true;
						}
						plugin.getConfig().set("BanItemId", tool);				
						plugin.saveConfig();

						player.sendMessage(new StringBuilder(pre).append("Banning tool changed to: ").append(bName).toString());
					}else
					if(param2.equalsIgnoreCase("EncaseTool"))
					{
						if(!(args.length > 2))
						{
							player.sendMessage(new StringBuilder(pre).append("Please specify the ID of the tool you would like to use.").toString());
							return true;
						}
						int tool = 0;
						try{
							tool = Integer.valueOf(args[2]);
						}catch(NumberFormatException e)
						{
							player.sendMessage(new StringBuilder(pre).append("Please use numbers when specifying ID").toString());
							return true;
						}
						Material block = Material.getMaterial(tool);
						String bName = null;
						try{
							bName = block.toString();
						}catch(Exception e)
						{
							player.sendMessage(new StringBuilder(pre).append("ID doesn't exist!").toString());
							return true;
						}
						plugin.getConfig().set("EncasngTool", tool);				
						plugin.saveConfig();
						player.sendMessage(new StringBuilder(pre).append("Encasing tool changed to: ").append(bName).toString());
					}else
					if(param2.equalsIgnoreCase("EncaseBlock"))
					{
						if(!(args.length > 2))
						{
							player.sendMessage(new StringBuilder(pre).append("Please specify the ID of the block you would like to use.").toString());
							return true;
						}	
						int block = 0;
						try{
							block = Integer.valueOf(args[2]);
						}catch(NumberFormatException e)
						{
							player.sendMessage(new StringBuilder(pre).append("Please use numbers when specifying ID").toString());
							return true;
						}
						Material b = Material.getMaterial(block);
						String bName = null;
						try{
							bName = b.toString();
						}catch(Exception e)
						{
							player.sendMessage(new StringBuilder(pre).append("ID doesn't exist!").toString());
							return true;
						}
						plugin.getConfig().set("BlockForEncasing", block);				
						plugin.saveConfig();
						player.sendMessage(new StringBuilder(pre).append("Encasing block changed to: ").append(bName).toString());
					}
				}else
				if(param.equalsIgnoreCase("help"))
				{
					player.sendMessage(pre +
							"\n/bh help - this command" +
							"\n/bh toggle - toggle on/off for tool based features" +
							"\n/bh set:" +
							"\n        BanTool - change the tool you use to ban players" +
							"\n        EncaseTool - change the tool you use to encase players" +
							"\n        EncaseBlock - change the block you encase players in" +
							"\n/bh tools - displays current tool ID allocations");
				}else
				if(param.equalsIgnoreCase("tools"))
				{
					String banT = Material.getMaterial(plugin.getConfig().getInt("BanItemId")).toString();
					String caseT = Material.getMaterial(plugin.getConfig().getInt("EncasingTool")).toString();
					String caseB = Material.getMaterial(plugin.getConfig().getInt("BlockForEncasing")).toString();
					
					player.sendMessage(pre +
										"\nBanning Tool: "+
										banT +
										"\nEncasing Tool: " +
										caseT +
										"\nEncasing Block: " +
										caseB);
				}
			}
		}
		return true;
	}
	
}
