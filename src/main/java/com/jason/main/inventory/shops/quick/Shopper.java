package com.jason.main.inventory.shops.quick;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Shopper {
    public static final ShopItem[] AXES = {
            ShopItem.WOOD_AXE,
            ShopItem.STONE_AXE,
            ShopItem.IRON_AXE,
            ShopItem.DIAMOND_AXE
    };
    public static final ShopItem[] PICKAXES = {
            ShopItem.WOOD_PICKAXE,
            ShopItem.IRON_PICKAXE,
            ShopItem.GOLD_PICKAXE,
            ShopItem.DIAMOND_PICKAXE
    };
    public static final ShopItem[] ARMORS = {
            ShopItem.CHAINMAIL_ARMOR,
            ShopItem.IRON_ARMOR,
            ShopItem.DIAMOND_ARMOR
    };

    public final int axeTier, pickaxeTier, armorTier;

    private final Player player;

    public Shopper(Player player) {
        this.player = player;
        axeTier = getTier(AXES);
        pickaxeTier = getTier(PICKAXES);
        armorTier = getTier(ARMORS);
    }

    public ShopItem getToolUpgrade(ShopItem[] tools, int currentTier) {
        return currentTier <= tools.length - 1 ? tools[currentTier] : null;
    }

    private int getTier(ShopItem[] tools) {
        PlayerInventory inventory = player.getInventory();
        for (int i = tools.length - 1; i >= 0; i--) {
            if ((inventory.getBoots() != null && inventory.getBoots().getType().equals(tools[i].getCleanItemStack().getType())) // armor
                    || inventory.contains(tools[i].getCleanItemStack().getType())) { // tools
                return i + 1;
            }
        }
        return 0;
    }
}
