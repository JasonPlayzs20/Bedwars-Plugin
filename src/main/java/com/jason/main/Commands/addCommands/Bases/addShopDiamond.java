package com.jason.main.Commands.addCommands.Bases;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.jason.main.bedwars.setData;

public class addShopDiamond implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        String color = args[0];

        player.sendMessage("Added diamond shop for team " + color + " at " + playerLocation.toVector().toString());

        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", color + ".diamondShop.x", playerLocation.getX());
        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", color + ".diamondShop.y", playerLocation.getY());
        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", color + ".diamondShop.z", playerLocation.getZ());
        return false;
    }
}
