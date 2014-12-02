/*
* AdminDuty
* All Rights Reserved by BorisTheTerrible
*/
package com.aol.evilmogley;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class AdminDuty extends JavaPlugin
{
    
    PluginManager pluginManager = getServer().getPluginManager();
    private PermissionManager permissionsManager;//PermissionManager grabbed from PEX
    FileConfigurationFields config;

    ArrayList<UUID> playersOnDuty = new ArrayList<>();//Players on duty have creative and commands

    String adminDutyPermission = "adminduty";

    @Override
    public void onEnable()
    {
        config = new FileConfigurationFields(this);
        
        permissionsManager = PermissionsEx.getPermissionManager();
        
        if(permissionsManager == null)
        {
            getLogger().severe("PermissionManager is null!");
            getLogger().severe("PermissionsEx was not loaded before intialization or is not installed!");
            pluginManager.disablePlugin(this);
        }

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

        if (!(sender.hasPermission(adminDutyPermission)))//Make sure player has permission to use /adminduty
        {
            sender.sendMessage("You lack permission!");
            return true;
        }

        if (playersOnDuty.contains(player.getUniqueId()))//Take away permissions, removes from playerOnDuty
        {
            for(String s : config.permissionsGivenOnDuty)
            {
                permissionsManager.getUser(player.getUniqueId()).removePermission(s);
            }
            
            playersOnDuty.remove(player.getUniqueId());
        } 
        else//Gives permissions, adds to playerOnDuty
        {
            for(String s : config.permissionsGivenOnDuty)
            {
                permissionsManager.getUser(player.getUniqueId()).addPermission(s);
            }
            
            playersOnDuty.add(player.getUniqueId());
        }

        return false;
    }
    
}
