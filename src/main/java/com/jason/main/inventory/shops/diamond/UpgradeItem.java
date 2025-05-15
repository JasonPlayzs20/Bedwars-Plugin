package com.jason.main.inventory.shops.diamond;

import org.bukkit.inventory.ItemStack;

public class UpgradeItem {
    private final ItemStack itemStack;
    private final int[] prices;

    public UpgradeItem(ItemStack itemStack, int[] prices) {
        this.itemStack = itemStack;
        this.prices = prices;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int[] getPrices() {
        return prices;
    }
}
