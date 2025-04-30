package com.jason.main.Commands.addCommands.Bases;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.jason.main.bedwars;

public class addGen implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        String world = player.getWorld().getName();

        String color = args[0];

        player.sendMessage("Added generator for team " + color + " at " + playerLocation.toVector().toString());

        bedwars.setData("plugins/BedwarsInfo", world.substring(1) + ".yml", color + ".gen.x", playerLocation.getX());
        bedwars.setData("plugins/BedwarsInfo", world.substring(1) + ".yml", color + ".gen.y", playerLocation.getY() + 1);
        bedwars.setData("plugins/BedwarsInfo", world.substring(1) + ".yml", color + ".gen.z", playerLocation.getZ());

        return false;
    }
}
