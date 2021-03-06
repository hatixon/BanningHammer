package com.github.hatixon.banninghammer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
@SuppressWarnings({"unchecked", "rawtypes"})
public class BanningHammer extends JavaPlugin
{
    public final Logger logger = Logger.getLogger("Minecraft");
    public final HitListener hitS = new HitListener(this);
    public Map activated;
    private final String pre = new StringBuilder().append(ChatColor.RED).append("[BanningHammer] ").append(ChatColor.YELLOW).toString();
    public final ArrayList<Player> frozenPlayers = new ArrayList<Player>();
	public void onEnable()
	{
		logger.info("The BanningHammer has been activated!");
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(hitS, this);
        getCommand("bh").setExecutor(new CommandBH(this));
        getCommand("confuse").setExecutor(new CommandConfuse(this));
        getCommand("freeze").setExecutor(new CommandFreeze(this));
        loadConfig();
        if(getConfig().getBoolean("CheckForUpdates"))
        {
        	if(isUpdated())
        	{
        		logger.info(new StringBuilder(pre).append("An updated version of BanningHammer is available at http://dev.bukkit.org/server-mods/banninghammer").toString());
        	}
        }
	}
	
	public BanningHammer()
	{
		activated = new HashMap();
	}
	public void loadConfig()
	{
		getConfig().addDefault("BanItemId", Integer.valueOf(293));
		getConfig().addDefault("BanCommand", "ban");
		getConfig().addDefault("BanReason", "You were banned by the BanHammer");
		getConfig().addDefault("EncasingTool", Integer.valueOf(352));
		getConfig().addDefault("BlockForEncasing", Integer.valueOf(7));
		getConfig().addDefault("FreezingTool", Integer.valueOf(79));
		getConfig().addDefault("CheckForUpdates", false);
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
			banner.performCommand(new StringBuilder(comSplit[0]).append(" ").append(uName).append(" ").append(comSplit[1]).append(" ").append(reason).toString());
		}else
		if(comSplit.length == 3)
		{
			banner.performCommand(new StringBuilder(comSplit[0]).append(" ").append(uName).append(" ").append(comSplit[1]).append(" ").append(comSplit[2]).append(" ").append(reason).toString());
		}
	}
	
	public boolean togglePlayer(Player player)
	{
		String uName = player.getName();
		if(activated.containsKey(uName))	
		{
			activated.remove(uName);
			return false;
		}else
		{
			activated.put(uName, "");
			return true;
		}
	}
	
	public boolean isActivated(String uName)
	{
		if(activated.containsKey(uName))
		{
			return true;
		}else{
			return false;
		}
	}
	
    public String getLatest()
    {
        StringBuilder responseData = new StringBuilder();
    	try
    	{
        String uri = "http://pastebin.com/raw.php?i=gczpzJTs";
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while((line = in.readLine()) != null) 
        {
            responseData.append(line);
        }
    	}catch(Exception e)
    	{
    		
    	}
        return responseData.toString();
    }
    
    public boolean isUpdated()
    {
    	PluginDescriptionFile pdffile = getDescription();
    	String current = pdffile.getVersion();
    	String latest = getLatest();
    	boolean updated = false;
    	if(!current.equalsIgnoreCase(latest))
    	{
    		updated = true;
    	}
    	return updated;
    }

	public boolean freezePlayer(Player player) 
	{
		if(frozenPlayers.contains(player))
		{
			frozenPlayers.remove(player);
			return false;
		}else
		{
			frozenPlayers.add(player);
			return true;
		}
	}
}

	