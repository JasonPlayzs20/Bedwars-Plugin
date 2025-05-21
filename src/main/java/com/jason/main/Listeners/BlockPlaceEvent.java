package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.annotation.Annotation;

import static com.jason.main.bedwars.getData;

public class BlockPlaceEvent implements Listener {
    @EventHandler
    public static void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Block block = event.getBlockPlaced();
        Location blockLoc = block.getLocation();

        String folder = "plugins/BedwarsInfo";
        String file = world.getName().substring(1) + ".yml";

        for (int i = 1; i < 12; i++) {
            String baseKey = "blockBreakLimit." + i;

            // Get one coordinate to test if the region exists
            String x1Str = getData(folder, file, baseKey + ".x1");
            if (x1Str == null) continue;

            // Load all values once
            double x1 = Double.parseDouble(x1Str);
            double y1 = Double.parseDouble(getData(folder, file, baseKey + ".y1"));
            double z1 = Double.parseDouble(getData(folder, file, baseKey + ".z1"));

            double x2 = Double.parseDouble(getData(folder, file, baseKey + ".x2"));
            double y2 = Double.parseDouble(getData(folder, file, baseKey + ".y2"));
            double z2 = Double.parseDouble(getData(folder, file, baseKey + ".z2"));

            // Get bounding box
            double minX = Math.min(x1, x2), maxX = Math.max(x1, x2);
            double minY = Math.min(y1, y2), maxY = Math.max(y1, y2);
            double minZ = Math.min(z1, z2), maxZ = Math.max(z1, z2);

            // Check if block is inside the region
            double bx = blockLoc.getX();
            double by = blockLoc.getY();
            double bz = blockLoc.getZ();

            if (bx >= minX && bx <= maxX &&
                    by >= minY && by <= maxY &&
                    bz >= minZ && bz <= maxZ) {

                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot place blocks in this restricted region.");
                return;
            }
        }

        GameManager gameManager = Arenas.getArena(world);
        gameManager.blockList.add(block);
    }

    @EventHandler
    public static void saturationEvent(PlayerMoveEvent event) {
//        event.getPlayer().sendMessage("saturation");
        event.getPlayer().setSaturation(25);
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().getWorld().setTime(1000);
        event.getPlayer().getWorld().setStorm(false);
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000, 1, false, false));
        if (event.getPlayer().getLocation().getBlockY() <= 20) {
            Player playerDied = event.getPlayer();
            if (playerDied.getWorld().getName().equals("world")) {
                playerDied.teleport(new Location(Bukkit.getWorld("world"), -41, 73, 0).setDirection(new Vector(90,0,0)));
                return;
            }
//            playerDied.sendMessage("Died");

            playerDied.setGameMode(GameMode.SPECTATOR);
            playerDied.setHealth(20);
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).team));
            playerDied.getWorld().getPlayers().forEach(player -> {player.sendMessage(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).team.chatColor + playerDied.getName().toString() + ChatColor.RED + " has died in the void.");});
//            playerDied.sendMessage(String.valueOf(playerDied.getWorld()));
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld())));
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName()))));
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn));
            playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn);
            playerDied.getInventory().clear();
            playerDied.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));

            new BukkitRunnable() {
                int timer = 5;

                @Override
                public void run() {
                    if (timer == 0) {
                        cancel();
                        return;
                    }
                    playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Respawning in : " + timer));
                    timer--;
                }
            }.runTaskTimerAsynchronously(bedwars.getMainInstance(), 0, 20);

            new BukkitRunnable() {

                @Override
                public void run() {
                    playerDied.setHealth(20);
                    playerDied.setGameMode(GameMode.SURVIVAL);
                    playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).baseSpawn);
                }
            }.runTaskLater(bedwars.getMainInstance(), 5 * 20);

        }
    }

}
