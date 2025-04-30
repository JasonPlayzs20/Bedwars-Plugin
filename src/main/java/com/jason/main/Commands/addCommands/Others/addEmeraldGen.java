package com.jason.main.Commands.addCommands.Others;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.jason.main.bedwars.setData;

public class addEmeraldGen implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        String genNum = args[0];

        player.sendMessage("Added emerald generator at " + playerLocation.toVector().toString());

        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "emeralds.gen" + genNum + ".x", playerLocation.getX());
        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "emeralds.gen" + genNum + ".y", playerLocation.getY()+1);
        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "emeralds.gen" + genNum + ".z", playerLocation.getZ());
        return false;
    }
}
