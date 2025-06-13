package com.jason.main.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface IMenuAction {
    void execute(InventoryMenu menu, ItemStack itemStack, ClickType button);
}
