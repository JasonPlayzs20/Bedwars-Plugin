package com.jason.main.invmenu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        InventoryMenu menu = InventoryMenu.REGISTRY.getOrDefault(inventory, null);

        if (menu != null && inventory.equals(event.getView().getTopInventory())) {
            event.setCancelled(true);
            int clickSlot = event.getSlot();
            ClickType clickType = event.getClick();
            menu.onClick(clickSlot, clickType);
            menu.populateMenu();
        }
    }
}
