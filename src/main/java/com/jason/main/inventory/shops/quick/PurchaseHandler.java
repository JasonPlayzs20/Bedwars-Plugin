package com.jason.main.inventory.shops.quick;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.jason.main.Util.consumeItem;

public class PurchaseHandler {
    private final Player player;

    PurchaseHandler(Player player) {
        this.player = player;
    }

    boolean takeResource(ItemStack itemStack, ItemStack secondary) {
        return  (consumeItem(player, itemStack.getAmount(), itemStack.getType())
        && (secondary == null || consumeItem(player, 1, secondary.getType())));
    }

    boolean takeResource(ItemStack itemStack) {
        return takeResource(itemStack, null);
    }

    void giveItems(ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
        player.updateInventory();
    }
}
