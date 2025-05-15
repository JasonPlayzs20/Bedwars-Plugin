package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.bedwars;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.annotation.Annotation;

import static com.jason.main.bedwars.getData;

public class BlockPlaceEvent implements Listener {
    @EventHandler
    public static void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        for (int i = 1; i < 12; i ++) {
//            player.sendMessage(String.valueOf(getData("plugins/BedwarsInfo", world.getName().substring(1), "blockBreakLimit")) + " UGIOHSNDYGUIOISHBGU*IOJKHUV*S)(PIDJKLVHUO*IKLJNBDSIUHOI");
//            if (getData("plugins"))
        }

        GameManager gameManager = Arenas.getArena(world);
        gameManager.blockList.add(event.getBlock());

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

            playerDied.sendMessage("Died");

            playerDied.setGameMode(GameMode.SPECTATOR);
            playerDied.setHealth(20);
            playerDied.getWorld().getPlayers().forEach(player -> {player.sendMessage(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor + playerDied.getName().toString() + ChatColor.RED + " has died in the void.");});
            playerDied.sendMessage(String.valueOf(playerDied.getWorld()));
            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld())));
            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied)));
            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn));
            playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);
            playerDied.getInventory().clear();

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
