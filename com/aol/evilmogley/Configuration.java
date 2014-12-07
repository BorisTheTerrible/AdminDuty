package com.aol.evilmogley;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Configuration {

    File configFile;
    FileConfiguration config;

    ArrayList<String> permissionsGivenOnDuty = new ArrayList<>();//What permissions players get when on duty
    String enablingDutyMessage;//Message shown when player enters admin duty
    String disablingDutyMessage;//Message shown when player leaves admin duty
    Boolean verbose;

    public Configuration(final Plugin plugin)
    {
        configFile = new File(plugin.getDataFolder(), "config.yml");//Used to check if a config file exists
        config = plugin.getConfig();//Actually config file referance

        if (!configFile.exists())//Creates config if one does not exist
        {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
            plugin.getLogger().info("Created config file.");
        }
        
        permissionsGivenOnDuty = (ArrayList)config.getStringList("permissionsGivenOnDuty");
        
        enablingDutyMessage = config.getString("enablingDutyMessage");
        
        disablingDutyMessage = config.getString("disablingDutyMessage");
        
        verbose = config.getBoolean("verbose");
        
        if(verbose){
            plugin.getLogger().info("Verbose mode enabled!");
        }
        
    }
}
