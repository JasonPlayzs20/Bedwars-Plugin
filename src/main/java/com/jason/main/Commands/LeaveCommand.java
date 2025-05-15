package com.jason.main.Commands;

import com.jason.main.GameDisplay.Arenas;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            player.teleport(new Location(Bukkit.getWorld("world"), -41, 73, 0));
            Arenas.getArena(world).startArena();
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.setAllowFlight(true);

        }
        return false;
    }
}
