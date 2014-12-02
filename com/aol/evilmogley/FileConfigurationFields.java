package com.aol.evilmogley;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class FileConfigurationFields {

    File configFile;
    FileConfiguration config;

    ArrayList<String> permissionsGivenOnDuty = new ArrayList<>();//What permissions players get when on duty
    String enablingDutyMessage;//Message shown when player enters admin duty
    String disablingDutyMessage;//Message shown when player leaves admin duty

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
        
        Set<String> permissions = config.getConfigurationSection("permissionsGivenOnDuty").getKeys(false);

        for (String s : permissions)//Get permissionGivenOnDuty from config and adds it to plugins permissions referance
        {
            permissionsGivenOnDuty.add(s);
        }
        
        enablingDutyMessage = config.getString("enablingDutyMessage");
        
        disablingDutyMessage = config.getString("disablingDutyMessage");

    }
}
