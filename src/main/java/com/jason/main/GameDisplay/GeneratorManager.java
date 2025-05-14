package com.jason.main.GameDisplay;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

import static com.jason.main.bedwars.getData;

public class GeneratorManager {
    String dataFolder;
    List<Location> diamondLoc;
    List<Location> genLoc;
    List<Location> emLoc;
    List<Location> shopLoc;
    List<Location> diaShopLoc;

    World world;

    public GeneratorManager(World world, List<Location> diamondLoc, List<Location> genLoc, List<Location> emLoc, List<Location> shopLoc, List<Location> diaShopLoc) {
        this.dataFolder = "plugins/BedwarsInfo";
        this.world = world;
        this.diamondLoc = diamondLoc;
        this.genLoc = genLoc;
        this.emLoc = emLoc;
        this.shopLoc = shopLoc;
        this.diaShopLoc = diaShopLoc;
    }

    void start() {

        diamondGen();
        emGen();
        ironGen();
        goldGen();
    }

    void diamondGen() {
        Bukkit.getPlayer("IamSorry_").sendMessage(diamondLoc.toString());
        for (Location location : diamondLoc) {
            ItemStack diamond = new ItemStack(Material.DIAMOND);
            Entity item = world.dropItem(location, diamond);
            item.setVelocity(new Vector(0, 0, 0));
        }

    }

    void emGen() {
        for (Location loc : emLoc) {
            ItemStack em = new ItemStack(Material.EMERALD);
            Entity item = world.dropItem(loc, em);
            item.setVelocity(new Vector(0, 0, 0));


        }
    }

    void ironGen() {
        for (Location loc : genLoc) {
            ItemStack iron = new ItemStack(Material.IRON_INGOT);
            Entity item = world.dropItem(loc, iron);
            item.setVelocity(new Vector(0, 0, 0));


        }
    }

    void goldGen() {
        for (Location loc : genLoc) {
            ItemStack gold = new ItemStack(Material.GOLD_INGOT);
            Entity item = world.dropItem(loc, gold);
            item.setVelocity(new Vector(0, 0, 0));


        }
    }

}
