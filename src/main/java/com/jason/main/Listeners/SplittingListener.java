package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SplittingListener implements Listener {
    // List of generator locations (block-level precision)

    // Tracks items dropped by players to avoid splitting them
    private final Set<Item> playerDroppedItems = new HashSet<>();

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        playerDroppedItems.add(e.getItemDrop());
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Item item = e.getItem();
        ItemStack stack = item.getItemStack();
        Material type = stack.getType();
        Player player = e.getPlayer();
        List<Location> genLoc = Arenas.getArena(player.getWorld()).genLoc;

        // Only split iron or gold ingots
        if (type != Material.IRON_INGOT && type != Material.GOLD_INGOT) return;

        // If player dropped it manually, skip
        if (playerDroppedItems.remove(item)) return;

        /// Check if it came from a registered generator location
        Location itemLoc = item.getLocation().getBlock().getLocation();
        boolean isFromGenerator = genLoc.stream().anyMatch(loc -> loc.equals(itemLoc));
        if (!isFromGenerator) return;

        // Find all nearby players within 1-block radius (1.5 blocks distance)
        List<Player> nearbyPlayers = item.getLocation().getWorld().getPlayers().stream()
                .filter(p -> p.getLocation().distance(item.getLocation()) <= 1.5)
                .collect(Collectors.toList());

        if (nearbyPlayers.isEmpty()) return;

        int amount = stack.getAmount();

        // Cancel pickup and remove original item
        e.setCancelled(true);
        item.remove();

        // Give full stack to each nearby player
        for (Player p : nearbyPlayers) {
            p.getInventory().addItem(new ItemStack(type, amount));
        }
    }
}
