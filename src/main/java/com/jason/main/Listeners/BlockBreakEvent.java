package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class BlockBreakEvent implements Listener {
    @EventHandler
    public static void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player p = e.getPlayer();
//        p.sendMessage(p.getWorld().getName());
        if (Objects.equals(p.getWorld().getName(), "world")) {
//            p.sendMessage("broken");
            e.setCancelled(true);
        } else {
            World world = p.getWorld();
            Block block = e.getBlock();
            GameManager gameManager = Arenas.getArena(world);
            //-1 or -2
            Location blockLoc = block.getLocation();
//            if (Math.abs(blockLoc.getX()) > Math.abs(blockLoc.getZ())) {
//                if (blockLoc.getX() > 0) {
//                    for (int i = 1; i < 3; i++) {
////                                go +X 2 blocks
////                        bed();
//                    }
//
//                } else {
//                    for (int i = 1; i < 3; i--) {
////                                go -X 2 blocks
//
//                    }
//                }
//            } else {
//                if (blockLoc.getZ() > 0) {
//                    for (int i = 1; i < 3; i++) {
////                                go +X 2 blocks
//
//                    }
//                } else {
//                    for (int i = 1; i < 3; i--) {
////                                go -Z 2 blocks
//
//                    }
//                }
//            }
//            p.sendMessage(String.valueOf(gameManager.bedLoc));
//            if (gameManager.bedLoc.containsKey(block.getLocation())) {
//                world.getPlayers().forEach(player -> {
//                    if (gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0, 1).toLowerCase().equals(gameManager.bedLoc.get(block.getLocation()))) {
//                        gameManager.bedwarsPlayers.get(player).hasBed = false;
//                        player.sendTitle(ChatColor.RED + "YOU HAVE NO BED!", ChatColor.YELLOW + "YOU WILL NOT RESPAWN!!!");
//                    }
////
////                    String team = gameManager.bedLoc.get(block.getLocation());
//
//                    Arenas.getArena(world).bedwarsPlayers.get(player).hasBed = false;
//                });
//            }
//                p.sendMessage(String.valueOf(block.getLocation()));
            if (!gameManager.blockList.contains(block)) {
                e.setCancelled(true);
                gameManager.blockList.remove(block);
            }

        }


    }

    public void bed(Player p, World world, GameManager gameManager, Block block) {
        p.sendMessage(String.valueOf(gameManager.bedLoc));
        if (gameManager.bedLoc.containsKey(block.getLocation())) {
            world.getPlayers().forEach(player -> {
                if (gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0, 1).toLowerCase().equals(gameManager.bedLoc.get(block.getLocation()))) {
                    gameManager.bedwarsPlayers.get(player).hasBed = false;
                    player.sendTitle(ChatColor.RED + "YOU HAVE NO BED!", ChatColor.YELLOW + "YOU WILL NOT RESPAWN!!!");
                }
//
//                    String team = gameManager.bedLoc.get(block.getLocation());

                Arenas.getArena(world).bedwarsPlayers.get(player).hasBed = false;
            });
        }
    }
}
