package com.aol.evilmogley;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class FileConfigurationFields
{

    File configFile;
    FileConfiguration config;
    
    ArrayList<String> permissions = new ArrayList<>();//Used for keeping what permissions players get when on duty
    String enablingDuty;//Message shown when player enters admin duty
    String disablingDuty;//Message shown when player leaves admin duty
    
    public FileConfigurationFields(final Plugin plugin)
    {
        configFile = new File(plugin.getDataFolder(), "config.yml");//Used to check if a config file exists
        config = plugin.getConfig();//Actually config file referance
        
        if (!configFile.exists())//Creates config if one does not exist
        {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
            plugin.getLogger().info("Created config file.");
        }
        
        
    }
}
