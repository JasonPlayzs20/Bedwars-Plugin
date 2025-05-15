package com.jason.main.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory.getType().equals(InventoryType.PLAYER) && event.getSlot() >= 36 && event.getSlot() <= 39)
            event.setCancelled(true);

        InventoryMenu menu = InventoryMenu.REGISTRY.getOrDefault(inventory, null);

        if (menu != null && inventory.equals(event.getView().getTopInventory())) {
            event.setCancelled(true);

            if (event.isLeftClick()) {
                int clickSlot = event.getSlot();
                ClickType clickType = event.getClick();
                menu.onClick(clickSlot, clickType);
                menu.populateMenu();
            }
        }
    }
}
