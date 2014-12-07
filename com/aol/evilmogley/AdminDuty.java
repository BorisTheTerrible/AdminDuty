/*
* AdminDuty
* All Rights Reserved by BorisTheTerrible
*/
package com.aol.evilmogley;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class AdminDuty extends JavaPlugin implements Listener
{
    
    PluginManager pluginManager = getServer().getPluginManager();
    Configuration config;
    private PermissionManager permissionsManager;//PermissionManager grabbed from PEX

    ArrayList<UUID> playersOnDuty = new ArrayList<>();//Players on duty have creative and commands

    String adminDutyPermission = "adminduty";

    @Override
    public void onEnable()
    {
        config = new Configuration(this);
        
        permissionsManager = PermissionsEx.getPermissionManager();
        
        if(permissionsManager == null)
        {
            getLogger().severe("PermissionManager is null!");
            getLogger().severe("PermissionsEx was not loaded before intialization or is not installed!");
            pluginManager.disablePlugin(this);
        }
        
        pluginManager.registerEvents(this, this);
        
        for(Player player : getServer().getOnlinePlayers())
        {
         //Removes permission if player erroneously has permissions for only when he is on duty
         //This is done in case of reload
            if(player.hasPermission(adminDutyPermission))
            {
                for(String s : config.permissionsGivenOnDuty)
                {
                    verboseMessage("Removing permission " + s +" for player " + player.getName());
                    permissionsManager.getUser(player.getUniqueId()).removePermission(s);
                } 
            }
        }

    }
    
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
      //Removes permission if player erroneously has permissions for only when he is on duty
        Player player = event.getPlayer();
        
        if(player.hasPermission(adminDutyPermission) && !(playersOnDuty.contains(player.getUniqueId())))
        {
           for(String s : config.permissionsGivenOnDuty)
            {
                verboseMessage("Removing permission " + s +" for player " + player.getName());
                permissionsManager.getUser(player.getUniqueId()).removePermission(s);
            } 
        }
    }
    
    protected void verboseMessage(String message)
    {
        if(config.verbose)
        {
            getLogger().info(message);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        
        if (!(sender instanceof Player))//Makes sure sender is not console
        {
            sender.sendMessage(ChatColor.DARK_RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!(sender.hasPermission(adminDutyPermission)))//Make sure player has permission to use /adminduty
        {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
            return true;
        }

        if (playersOnDuty.contains(player.getUniqueId()))//Take away permissions, removes from playerOnDuty
        {
            player.sendMessage(ChatColor.GREEN + config.disablingDutyMessage);
            
            for(String s : config.permissionsGivenOnDuty)
            {
                verboseMessage("Removing permission " + s +" for player " + player.getName());
                permissionsManager.getUser(player.getUniqueId()).removePermission(s);
            }
            
            playersOnDuty.remove(player.getUniqueId());
        } 
        else//Gives permissions, adds to playerOnDuty
        {
            player.sendMessage(ChatColor.GREEN + config.enablingDutyMessage);
            
            for(String s : config.permissionsGivenOnDuty)
            {
                verboseMessage("Giving permission " + s +" for player " + player.getName());
                permissionsManager.getUser(player.getUniqueId()).addPermission(s);
            }
            
            playersOnDuty.add(player.getUniqueId());
        }

        return true;
    }
    
    
}
