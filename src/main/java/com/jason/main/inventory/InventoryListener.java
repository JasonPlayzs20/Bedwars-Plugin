package com.jason.main.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InventoryListener implements Listener {
    public static final List<Material> AXES = Arrays.asList(Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE);
    public static final List<Material> PICKAXES = Arrays.asList(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE);
    public static final List<Material> SWORDS = Arrays.asList(Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack movingItemStack = event.getCursor().getType().equals(Material.AIR) ? event.getCurrentItem() : event.getCursor();

        if (movingItemStack != null && clickedInventory == null) { // drop item
            if (AXES.contains(movingItemStack.getType()) || PICKAXES.contains(movingItemStack.getType())) event.setCancelled(true);
            return;
        }

        // prevent clicking on armor slot
        if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            event.setCancelled(true);
            return;
        }

        InventoryAction inventoryAction = event.getAction();

        if (movingItemStack != null && (AXES.contains(movingItemStack.getType()) || PICKAXES.contains(movingItemStack.getType()))) {
            if (clickedInventory.getType().equals(InventoryType.PLAYER)) { // moving within the player inventory
                if (inventoryAction.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                    event.setCancelled(true);
                    return;
                }
            } else {
                if (inventoryAction.equals(InventoryAction.PLACE_ONE) || inventoryAction.equals(InventoryAction.PLACE_SOME) || inventoryAction.equals(InventoryAction.PLACE_ALL)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        InventoryMenu menu = InventoryMenu.REGISTRY.getOrDefault(clickedInventory, null);

        // is an inventory menu
        if (menu != null && clickedInventory.equals(event.getView().getTopInventory())) {
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
