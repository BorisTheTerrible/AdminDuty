/*
* AdminDuty
* All Rights Reserved by BorisTheTerrible
*/
package com.aol.evilmogley;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class AdminDuty extends JavaPlugin {

    File configFile = new File(getDataFolder(), "config.yml");//Used to check if a config file exists
    FileConfiguration config = getConfig();//Actually config file referance
    
    PluginManager pluginManager = getServer().getPluginManager();
    private PermissionManager permissionsManager;//PermissionManager grabbed from PEX

    ArrayList<UUID> playersOnDuty = new ArrayList<>();//Players on duty have creative and commands

    String permission = "adminduty";

    @Override
    public void onEnable()
    {
        if (!configFile.exists())//Creates config if one does not exist
        {
            getConfig().options().copyDefaults(true);
            saveConfig();
            getLogger().info("Created config file.");
        }
        
        permissionsManager = PermissionsEx.getPermissionManager();

    }
    


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        
        if (!(sender instanceof Player))//Makes sure sender is not console
        {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!(sender.hasPermission(permission)))//Make sure player has permission to use /adminduty
        {
            sender.sendMessage("You lack permission!");
            return true;
        }

        if (playersOnDuty.contains(player.getUniqueId()))//Take away permissions, removes from playerOnDuty
        {
            
        } 
        else//Gives permissions, adding to playerOnDuty
        {
            permissionsManager.getUser(player.getUniqueId()).addPermission("essentials.afk");
        }

        return false;
    }
    
}
