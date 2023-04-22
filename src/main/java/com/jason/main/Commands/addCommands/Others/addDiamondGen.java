package com.jason.main.Commands.addCommands.Others;


import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.jason.main.bedwars.setData;

public class addDiamondGen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        String genNum = args[0];
        setData("plugins/BedwarsInfo", world.getName() + ".yml", "diamonds/gen" + genNum + "x", String.valueOf(playerLocation.getX()));
        setData("plugins/BedwarsInfo", world.getName() + ".yml", "diamonds/gen" + genNum + "y", String.valueOf(playerLocation.getY()));
        setData("plugins/BedwarsInfo", world.getName() + ".yml", "diamonds/gen" + genNum + "z", String.valueOf(playerLocation.getZ()));
        return false;
    }
}
