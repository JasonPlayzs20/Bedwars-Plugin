package com.jason.main.invmenu.shops;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PurchaseHandler {
    private final Player player;

    PurchaseHandler(Player player) {
        this.player = player;
    }

    boolean takeResource(ItemStack itemStack, ItemStack secondary) {
        Inventory inventory = player.getInventory();

        if (inventory.containsAtLeast(itemStack, itemStack.getAmount()) && (secondary == null || inventory.containsAtLeast(secondary, 1))) {
            inventory.removeItem(itemStack);
            if (secondary != null) inventory.removeItem(secondary);
            return true;
        }

        return false;
    }

    boolean takeResource(ItemStack itemStack) {
        return takeResource(itemStack, null);
    }

    void giveItems(ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
    }
}
