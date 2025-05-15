package com.jason.main.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public static void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        if (e.getPlayer().getWorld().getName() == "world") {
            e.getPlayer().setAllowFlight(true);
        }
    }
}
