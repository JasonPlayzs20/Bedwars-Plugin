package com.jason.main.invmenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class InventoryMenu {
    public static final Map<Inventory, InventoryMenu> REGISTRY = new HashMap<>();

    protected final Inventory inventory;
    protected final Map<Integer, IMenuAction> clickListeners = new HashMap<>();
    protected final Player player;
    protected final int rows;

    protected InventoryMenu(Player player, int rows, String title) {
        this.player = player;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        REGISTRY.put(inventory, this);
    }

    public void open() {
        this.populateMenu();
        player.openInventory(inventory);
    }

    public void onClick(int slot, ClickType clickType) {
        IMenuAction action = clickListeners.getOrDefault(slot, null);
        if (action != null) action.execute(this, inventory.getItem(slot), clickType);
    }

    protected void setButton(int row, int col, ItemStack itemStack, IMenuAction action) {
        setItem(row, col, itemStack);
        clickListeners.put(row * 9 + col, action);
    }

    protected void setItem(int row, int col, ItemStack itemStack) {
        inventory.setItem(row * 9 + col, itemStack);
    }

    protected abstract void populateMenu();
}
