package com.jason.main.Commands;

import com.jason.main.GameDisplay.Arenas;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LeaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            Arenas.getArena(world).startArena();
            player.teleport(new Location(Bukkit.getWorld("world"), -41, 73, 0));

            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.setAllowFlight(true);
            player.getEnderChest().clear();

        }
        return false;
    }
}
