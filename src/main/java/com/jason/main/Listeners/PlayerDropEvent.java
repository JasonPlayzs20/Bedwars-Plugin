package com.jason.main.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerDropEvent implements Listener {
    @EventHandler
    public static void onPlayerDropItem(PlayerDropItemEvent e) {
        ItemStack itemStack = e.getItemDrop().getItemStack();
        e.getPlayer().sendMessage(String.valueOf(itemStack.getType()));
        if (String.valueOf(itemStack.getType()).toLowerCase().contains("wood_sword")) {
            e.setCancelled(true);
        }
        if (String.valueOf(itemStack.getType()).toLowerCase().contains("axe")) {
            e.setCancelled(true);
        }
    }
}
