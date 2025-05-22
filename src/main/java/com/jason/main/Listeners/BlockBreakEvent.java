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
            final Location blockLoc = block.getLocation();
            if (Math.abs(blockLoc.getX()) > Math.abs(blockLoc.getZ())) {
                if (blockLoc.getX() > 0) {
                    for (int i = 1; i < 3; i++) {
//                                go +X 2 blocks
                        if (bed(p, world, gameManager, blockLoc.getBlock().getLocation().add(i, 0, 0),1,0)) {
                            break;
                        }
                    }

                } else {
                    for (int i = 1; i < 3; i++) {
//                                go -X 2 blocks
                        if (bed(p, world, gameManager, blockLoc.add(-i, 0, 0),-1,0)) {
                            break;
                        }
                    }
                }
            } else {
                if (blockLoc.getZ() > 0) {
                    for (int i = 1; i < 3; i++) {
//                                go +X 2 blocks\

                        if (bed(p, world, gameManager, blockLoc.add(0, 0, -1),0,1)) {
                            break;
                        }

                    }
                } else {
                    for (int i = 1; i < 3; i++) {
//                                go -Z 2 blocks
                        p.sendMessage("-2");
                        if (bed(p, world, gameManager, blockLoc.getBlock().getLocation().add(0, 0, -i),0,-1)) {
                            break;
                        }
                    }
                }
            }
//            p.sendMessage(String.valueOf(gameManager.bedLoc));
//

//                p.sendMessage(String.valueOf(block.getLocation()));

            block.getDrops().clear();
            if (!gameManager.blockList.contains(block) && !block.getType().equals(Material.BED_BLOCK)) {
                e.setCancelled(true);
                gameManager.blockList.remove(block);
            } else {

            }

        }


    }

    public static boolean bed(Player p, World world, GameManager gameManager, Location location, int x, int z) {
        p.sendMessage(String.valueOf(location.add(0.5, 0.5, 0.5)));
        p.sendMessage("Locatuo out");
        for (Location bedLocLoc : gameManager.bedLoc.keySet()) {
            if (Math.abs(bedLocLoc.getX()- location.getX())<9 && Math.abs(bedLocLoc.getY()- location.getY())<9 && Math.abs(bedLocLoc.getZ()- location.getZ())<9) {
                p.sendMessage("locatino In");
                //            p.sendMessage(String.valueOf(i));
                p.sendMessage(String.valueOf(location));
                p.sendMessage(gameManager.bedwarsPlayers.get(p).team.teamColors.name().substring(0, 1).toLowerCase());
                world.getPlayers().forEach(player -> {
                    if (gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0, 1).toLowerCase().equals(gameManager.bedLoc.get(location))) {
                        gameManager.bedwarsPlayers.get(player).hasBed = false;
                        player.sendTitle(ChatColor.RED + "YOU HAVE NO BED!", ChatColor.YELLOW + "YOU WILL NOT RESPAWN!!!");
                        Arenas.getArena(world).bedwarsPlayers.get(player).hasBed = false;
                    }
                    if (gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0, 1).toLowerCase().equals(gameManager.bedLoc.get(location.add(x,0,z)))) {
                        gameManager.bedwarsPlayers.get(player).hasBed = false;
                        player.sendTitle(ChatColor.RED + "YOU HAVE NO BED!", ChatColor.YELLOW + "YOU WILL NOT RESPAWN!!!");
                        Arenas.getArena(world).bedwarsPlayers.get(player).hasBed = false;
                    }
                    //
                    //                    String team = gameManager.bedLoc.get(block.getLocation());


                    //                return true;
                });
                return true;
            }
        }
        return false;
    }
}
