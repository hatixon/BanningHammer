package com.gmail.macca499.banninghammer;

import java.util.logging.Logger;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class BanningHammer extends JavaPlugin
{
    public final Logger logger = Logger.getLogger("Minecraft");
    public final HitListener hitS = new HitListener(this);
	public void onEnable()
	{
		logger.info("The BanningHammer has been activated!");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(hitS, this);
        getCommand("bh").setExecutor(new BanCommandExecutor(this));
        loadConfig();
	}

	public void loadConfig()
	{
		getConfig().addDefault("BanItemId", Integer.valueOf("293"));
		getConfig().addDefault("BanCommand", "ban");
		getConfig().addDefault("BanReason", "You were banned by the BanHammer");
        getConfig().options().copyDefaults(true);
        saveConfig();		
	}
	
	public void onDisable()
	{
		logger.info("The BanningHammer has been de-activated!");
	}

	
	public void banPlayer(Player banner, Player banning)
	{
		String reason = getConfig().getString("BanReason");
		String uName = banning.getName();
		String banCom = getConfig().getString("BanCommand");
		String comSplit[] = banCom.split(" ", 3);
		if(comSplit.length == 1)
		{
			banner.performCommand(new StringBuilder(comSplit[0]).append(" ").append(uName).append(" ").append(reason).toString());
		}else
		if(comSplit.length == 2)
		{
			banner.performCommand(new StringBuilder(comSplit[0]).append(" ").append(uName).append(" ").append(comSplit[1]).append(reason).toString());
		}else
		if(comSplit.length == 3)
		{
			banner.performCommand(new StringBuilder(comSplit[0]).append(" ").append(uName).append(" ").append(comSplit[1]).append(" ").append(comSplit[2]).append(" ").append(reason).toString());
		}
	}
}
