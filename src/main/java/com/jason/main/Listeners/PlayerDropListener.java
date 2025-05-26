package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {
    @EventHandler
    public static void playerDropItemEvent(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        Arenas.getArena(player.getWorld()).droppedItems.add(e.getItemDrop());
    }
}
