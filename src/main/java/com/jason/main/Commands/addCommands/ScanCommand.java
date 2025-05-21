package com.jason.main.Commands.addCommands;

import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static com.jason.main.bedwars.*;
import static org.bukkit.Material.*;

public class ScanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        scan(player, player.getWorld());
        return false;
    }

    public void scan(Player player, World world) {
        player.sendMessage("Scanning");
        String dataFolder = "plugins/BedwarsInfo";
        int firstLevel = Integer.parseInt((getData(dataFolder, world.getName() + ".yml", "lobbySpawn.y")));
        Location loc1 = null;
        Location loc2 = null;
        for (int x = -150; x <= 150; x++) {
            for (int z = -150; z <= 150; z++) {
                Location scanningLoc = new Location(world, x, firstLevel, z);
                if (scanningLoc.getBlock().getType().equals(SPONGE)) {
                    if (loc1 == null) {
                        loc1 = scanningLoc;
                    } else {
                        loc2 = scanningLoc;
                    }
                }
            }
        }
        Location finalLoc = loc2;
        Location finalLoc1 = loc1;
        List<Location> locations = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(bedwars.getMainInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                for (int x = finalLoc1.getBlockX(); x <= finalLoc.getBlockX(); x++) {
                    for (int z = finalLoc1.getBlockZ(); z <= finalLoc.getBlockZ(); z++) {
                        for (int y = 0; y <= 100; y++) {
                            Location scanningLoc = new Location(world, x, y, z);
                            if (scanningLoc.getBlock().getState() instanceof org.bukkit.block.Sign) {
                                locations.add(scanningLoc);
                            }
                        }
                    }
                }
                Bukkit.getScheduler().runTask(bedwars.getMainInstance(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        getSign(locations,player,world);
                    }
                });
            }
        });

    }

    public void getSign(List<Location> locations, Player player, World world) {
        player.sendMessage("Done");
//        player.sendMessage(locations.toString());
        for (Location loc : locations) {
            Sign sign = (Sign) loc.getBlock().getState();
            String data = sign.getLine(0);
//            player.sendMessage("test: ");
            if (getData("plugins/BedwarsInfo", world.getName() + ".yml","test.x") == null) {
                player.sendMessage("null");
            }
            player.sendMessage(getData("plugins/BedwarsInfo", world.getName() + ".yml","test.x"));
//            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + ".speed", 1);
            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".x", sign.getX());
            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".y", sign.getY());
            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".z", sign.getZ());
        }
    }
}


