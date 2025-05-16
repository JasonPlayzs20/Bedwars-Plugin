package com.jason.main.Listeners;

import com.jason.main.bedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class  PlayerJoinEvent implements Listener {
    @EventHandler
    public static void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
//                e.getPlayer().sendMessage(e.getPlayer().getWorld().getName());
                if (Objects.equals(e.getPlayer().getWorld().getName(), "world")) {
                    e.getPlayer().setAllowFlight(true);
                    e.getPlayer().setFlying(true);
                }
            }
        }.runTaskLater(bedwars.getMainInstance(),20);

    }
}
