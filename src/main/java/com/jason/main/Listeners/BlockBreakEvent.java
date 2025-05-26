package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() == Material.BED) {
            event.setCancelled(true); // Prevent the bed item from spawning
        }
    }

    @EventHandler
    public static void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player p = e.getPlayer();
//        p.sendMessage(p.getWorld().getName());
        ItemStack heldItem = p.getInventory().getItemInHand();
        if (heldItem.getType().getMaxDurability() > 0) {
            heldItem.setDurability((short) -1);
        }
        if (Objects.equals(p.getWorld().getName(), "world")) {
//            p.sendMessage("broken");
            e.setCancelled(true);
        } else {

            World world = p.getWorld();
            Block block = e.getBlock();
            GameManager gameManager = Arenas.getArena(world);
            //-1 or -2
            Location blockLoc = block.getLocation();
            blockLoc.add(0.5, 1.5, 0.5);
            world.getPlayers().forEach(player -> {

                final boolean[] bed = {true};
                String letter = gameManager.bedwarsPlayers.get(player).team.teamColors.name().substring(0, 1).toLowerCase();
//                        player.sendMessage(letter);
//                        player.sendMessage(gameManager.bedLoc.get(blockLoc).toString());

                if (letter.equals(gameManager.bedLoc.get(blockLoc))) {
//                    player.sendMessage(letter);
//                    player.sendMessage(gameManager.bedwarsPlayers.get(p).team.teamColors.name().substring(0, 1).toLowerCase());
                    gameManager.bedLoc.keySet().forEach(location -> {
//                        p.sendMessage(String.valueOf(location.getX() - e.getBlock().getX()));
//                        p.sendMessage(String.valueOf(location.getZ() - e.getBlock().getZ()));
                        if ((location.getX() - e.getBlock().getX()) < 3 && (location.getZ() - e.getBlock().getZ()) < 3 && (location.getX() - e.getBlock().getX()) > -3 && (location.getZ() - e.getBlock().getZ()) > -3) {
//                            p.sendMessage(gameManager.bedwarsPlayers.get(p).team.teamColors.name().toLowerCase().substring(0,1));
                            if (gameManager.bedLoc.get(location).equals(gameManager.bedwarsPlayers.get(p).team.teamColors.name().toLowerCase().substring(0, 1))) {
                                e.setCancelled(true);

                                bed[0] = false;
//                                p.sendMessage("Broken");

                                return;
                            }

                        }
                    });
                    Bukkit.getWorld(player.getWorld().getName()).playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
//                    player.sendMessage();
                    String teamName = gameManager.bedwarsPlayers.get(player).team.teamColors.name();
//                    player.sendMessage(ChatColor.RED + teamName + "'s bed is broken!");
                    if (bed[0]) {

                        gameManager.bedwarsPlayers.get(player).hasBed = false;
                        player.sendTitle(ChatColor.RED + "YOU HAVE NO BED!", ChatColor.YELLOW + "YOU WILL NOT RESPAWN!!!");

                        Arenas.getArena(world).bedwarsPlayers.get(player).hasBed = false;

                        player.getWorld().getPlayers().forEach(player1 -> {
                            Arenas.getArena(player1.getWorld()).updateScoreBoard();
                        });
                    } else {
                        player.sendMessage(ChatColor.RED + "You cannot break your own bed!!!");
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
