package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PickUpItemListener implements Listener {
    private static final double SPLIT_DIST = 3;

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent event) {
        Player picker = event.getPlayer();
        BedwarsPlayer bwPicker = Arenas.getPlayer(picker);
        if (bwPicker == null) return;
        if (Arenas.getArena(picker.getWorld()).droppedItems.contains(event.getItem())){return;}
        ItemStack is = event.getItem().getItemStack();
        if (is.getType().equals(Material.IRON_INGOT) || is.getType().equals(Material.GOLD_INGOT))
            picker.getNearbyEntities(SPLIT_DIST, SPLIT_DIST, SPLIT_DIST).stream()
                    .map(entity -> {
                        if (entity instanceof Player && !entity.equals(picker) && picker.getLocation().distance(entity.getLocation()) < SPLIT_DIST) {
                            BedwarsPlayer teammate = Arenas.getPlayer((Player) entity);
                            if (teammate == null || !teammate.getTeam().teamColors.equals(bwPicker.getTeam().teamColors)) return null;
                            return (Player) entity;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .forEach(player -> player.getInventory().addItem(is));
    }
}
