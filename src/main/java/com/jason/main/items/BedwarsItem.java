package com.jason.main.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class BedwarsItem {
    private static final List<BedwarsItem> REGISTRY = new ArrayList<>();

    public static BedwarsItem from(ItemStack itemStack) {
        for (BedwarsItem item : REGISTRY) {
            if (item.name.equals(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName())) && item.material.equals(itemStack.getType()))
                return item;
        }
        return null;
    }

    protected static void register(BedwarsItem item) {
        if (!REGISTRY.contains(item)) REGISTRY.add(item);
    }

    protected final Material material;
    protected final String name;

    protected BedwarsItem(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    /**
     * Called when this item is used
     */
    public abstract void onUse(PlayerInteractEvent event);

    /**
     * Checks if a BedwarsItem is the same type as this one. The checking is based on material and name.
     * @param obj BedwarsItem
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BedwarsItem && ((BedwarsItem) obj).material.equals(this.material) && ((BedwarsItem) obj).name.equals(this.name);
    }

    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    public ItemStack getItemStack(int amount) {
        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + name);

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
