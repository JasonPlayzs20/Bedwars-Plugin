package com.jason.main.GameDisplay;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ShopManager{
    Inventory shop;
    Inventory diamondShop;
    World world;
    Player player;
    public ShopManager(World world, Player player, Location spawnLocation) {
        this.shop = Bukkit.createInventory(null, 54, "Shop");
        this.diamondShop = Bukkit.createInventory(null, 54, "Diamond Shop");
        this.world = world;
        this.player = player;
    }

    void blocksTab() {
        setItem(0,Material.NETHER_STAR,1);
        setItem(1,Material.HARD_CLAY,1);
        setItem(2,Material.GOLD_SWORD,1);
        setItem(3,Material.CHAINMAIL_BOOTS,1);
        setItem(4,Material.STONE_PICKAXE,1);
        setItem(5,Material.BOW,1);
        setItem(6,Material.BREWING_STAND,1);
        setItem(7,Material.TNT,1);
        for (int i = 9; i <= 17; i++) {
            ItemStack itemstack = new ItemStack(Material.STAINED_GLASS,1, DyeColor.GRAY.getData());
            this.shop.setItem(i, itemstack);
        }
        ItemStack itemstack = new ItemStack(Material.STAINED_GLASS,1, DyeColor.GREEN.getData());
        this.shop.setItem(10, itemstack);
        setItem(19,Material.WOOL,16);
        setItem(20,Material.HARD_CLAY,16);
        setItem(21,Material.STAINED_GLASS,4);
        setItem(22,Material.ENDER_STONE,12);
        setItem(23,Material.LADDER,16);
        setItem(24,Material.LOG,16);
        setItem(25,Material.OBSIDIAN,4);

    }

    void spawnShop() {

    }

    void spawnDiamondShop() {

    }

    private void setItem(int slot, Material material, int amount) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(amount);
        this.shop.setItem(slot,itemStack);
    }

}
