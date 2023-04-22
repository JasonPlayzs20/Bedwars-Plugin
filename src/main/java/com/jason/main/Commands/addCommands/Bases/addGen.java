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
        World world = player.getWorld();

        String color = args[0];

        bedwars.setData("plugins/BedwarsInfo", world + ".yml", color + "/gen/x", String.valueOf(playerLocation.getX()));
        bedwars.setData("plugins/BedwarsInfo", world + ".yml", color + "/gen/y", String.valueOf(playerLocation.getY()));
        bedwars.setData("plugins/BedwarsInfo", world + ".yml", color + "/gen/z", String.valueOf(playerLocation.getZ()));

        return false;
    }
}
