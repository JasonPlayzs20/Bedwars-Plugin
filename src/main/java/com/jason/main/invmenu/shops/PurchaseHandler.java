package com.jason.main.invmenu.shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

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

    public static boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found) {
            return false;
        }

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed) {
                player.getInventory().setItem(index, null);
            } else {
                stack.setAmount(stack.getAmount() - removed);

            }
            if (count <= 0) {
                break;
            }
        }

        player.updateInventory();
        return true;
    }
}
