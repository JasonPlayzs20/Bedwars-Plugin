package com.jason.main.Commands.addCommands;

import com.jason.main.bedwars;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Sign;
import org.bukkit.scheduler.BukkitRunnable;

import static com.jason.main.bedwars.*;
import static org.bukkit.Statistic.Type.BLOCK;

public class ScanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        scan(player, player.getWorld());
        return false;
    }

    public void scan(Player player, World world) {
        player.sendMessage("Scanning");
//        Scan top layer
        String dataFolder = "plugins/BedwarsInfo";
        new BukkitRunnable() {
            @Override
            public void run() {
                int firstLevel = Integer.parseInt((getData(dataFolder, world.getName() + ".yml", "lobbySpawn.y")));
                Location loc1 = null;
                Location loc2 = null;
                player.sendMessage(world.getName() + ".yml");
                for (int x = -150; x <= 150; x++) {
                    for (int z = -150; z <= 150; z++) {
                        Location scanningLoc = new Location(world, x, firstLevel, z);
                        if (scanningLoc.getBlock().getType() == Material.SPONGE) {
                            loc1 = scanningLoc;
//                            if (loc1 == null) {
//                                loc1 = scanningLoc;
//                            } else {
//                                loc2 = scanningLoc;
//                            }

                        }
                    }
                }
//                player.sendMessage("Found sponge at: " + loc1.toString() + " " + loc2.toString());
//                for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
//                    for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
//                        for (int y = 76; y <= 76; y++) {
//                            Location scanningLoc = new Location(world, x, y, z);
////                            player.sendMessage(scanningLoc.getBlock().getType().toString());
////                    world.playEffect(scanningLoc, Effect.NOTE,3,300);
//                            if (scanningLoc.getBlock().getType() == Material.SIGN_POST) {
//                                player.sendMessage("scanned at: " + scanningLoc);
//                                org.bukkit.block.Sign signBlock = (org.bukkit.block.Sign) scanningLoc.getBlock();
//                                String data = signBlock.getLine(0);
//                                setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".x", signBlock.getX());
//                                setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".y", signBlock.getY());
//                                setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".z", signBlock.getZ());
//
//                            }
//                        }
//                    }
//                }
                player.sendMessage("rinning");
            }
        }.runTaskAsynchronously(bedwars.getMainInstance());

    }
}
//public void scan(Player player, World world) {
//    player.sendMessage("Scanning");
////        Scan top layer
//    String dataFolder = "plugins/BedwarsInfo";
//    new BukkitRunnable() {
//        @Override
//        public void run() {
//            int firstLevel = Integer.parseInt((getData(dataFolder, world.getName() + ".yml", "lobbySpawn.y")));
//            Location loc1 = null;
//            Location loc2 = null;
//            player.sendMessage(world.getName() + ".yml");
//            for (int x = -150; x <= 150; x++) {
//                for (int z = -150; z <= 150; z++) {
//                    Location scanningLoc = new Location(world, x, firstLevel, z);
//                    if (scanningLoc.getBlock().getType() == Material.SPONGE) {
//                        if (loc1 == null) {
//                            loc1 = scanningLoc;
//                        } else {
//                            loc2 = scanningLoc;
//                        }
//                    }
//                }
//            }
//            player.sendMessage("Found sponge at: " + loc1.toString() + " " + loc2.toString());
//            for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
//                for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
//                    for (int y = 76; y <= 76; y++) {
//                        Location scanningLoc = new Location(world, x, y, z);
//                        player.sendMessage(scanningLoc.getBlock().getType().toString());
////                    world.playEffect(scanningLoc, Effect.NOTE,3,300);
//                        if (scanningLoc.getBlock().getType() == Material.SIGN) {
//                            player.sendMessage("scanned at: " + scanningLoc);
//                            org.bukkit.block.Sign signBlock = (org.bukkit.block.Sign) scanningLoc.getBlock();
//                            String data = signBlock.getLine(0);
//                            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".x", signBlock.getX());
//                            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".y", signBlock.getY());
//                            setData("plugins/BedwarsInfo", world.getName() + ".yml", data.charAt(1) + "." + data.charAt(0) + ".z", signBlock.getZ());
//
//                        }
//                    }
//                }
//            }
//        }
//    }.runTaskAsynchronously(bedwars.getMainInstance());
//
//}
//}