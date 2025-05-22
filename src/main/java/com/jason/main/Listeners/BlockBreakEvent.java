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
            blockLoc.add(0.5,1.5,0.5);
            world.getPlayers().forEach(player -> {
                        String letter = gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0, 1).toLowerCase();
//                        player.sendMessage(letter);
//                        player.sendMessage(gameManager.bedLoc.get(blockLoc).toString());

                        if (letter.equals(gameManager.bedLoc.get(blockLoc))) {
                            if (letter.equals(gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0,1).toLowerCase()))  {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + "You cannot break your own bed!!!");
                            }else {
                                gameManager.bedwarsPlayers.get(player).hasBed = false;
                                player.sendTitle(ChatColor.RED + "YOU HAVE NO BED!", ChatColor.YELLOW + "YOU WILL NOT RESPAWN!!!");
                                Arenas.getArena(world).bedwarsPlayers.get(player).hasBed = false;
                            }
                        }
            });

            block.getDrops().clear();
            if (!gameManager.blockList.contains(block) && !block.getType().equals(Material.BED_BLOCK)) {
                e.setCancelled(true);
                gameManager.blockList.remove(block);
            } else {

            }

        }


    }


}
