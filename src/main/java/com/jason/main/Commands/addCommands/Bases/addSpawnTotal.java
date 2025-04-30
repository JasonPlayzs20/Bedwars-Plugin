package com.jason.main.Commands.addCommands.Bases;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.jason.main.bedwars.*;

public class addSpawnTotal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        player.sendMessage("Added total spawn at " + playerLocation.toVector().toString());

        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "lobbySpawn.x", playerLocation.getX());
        setDataINT("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "lobbySpawn.y", playerLocation.getBlockY() + 1);
        setData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "lobbySpawn.z", playerLocation.getZ());
        return false;
    }
}
