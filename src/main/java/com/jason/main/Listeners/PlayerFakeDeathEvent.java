package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.bedwars;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerFakeDeathEvent implements Listener {
    @EventHandler
    public static void onDeath(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player playerDied = (Player) e.getEntity();
            playerDied.sendMessage("sriughfo");
            if ((playerDied.getHealth() - e.getFinalDamage()) < 1) {
                playerDied.sendMessage("Died");
                e.setCancelled(true);
                playerDied.setGameMode(GameMode.SPECTATOR);
                playerDied.setHealth(20);
                playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);
                playerDied.getInventory().clear();

                new BukkitRunnable() {
                    int timer = 5;
                    @Override
                    public void run() {
                        if (timer == 0) {cancel(); return;}
                        playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Respawning in : " + timer));
                        timer--;
                    }
                }.runTaskTimerAsynchronously(bedwars.getMainInstance(),0,20);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        playerDied.setHealth(20);
                        playerDied.setGameMode(GameMode.SURVIVAL);
                        playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).baseSpawn);
                    }
                }.runTaskLater(bedwars.getMainInstance(),5*20);
            }

        }

    }
}
