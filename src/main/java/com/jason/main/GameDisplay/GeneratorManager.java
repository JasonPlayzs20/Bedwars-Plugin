package com.jason.main.GameDisplay;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import static com.jason.main.bedwars.getData;

public class GeneratorManager {
    String dataFolder;

    World world;
    public GeneratorManager(World world) {
        this.dataFolder = "plugins/BedwarsInfo";
        this.world = world;
    }
    void start() {
        diamondGen();
        emGen();
        ironGen();
        goldGen();
    }
    void diamondGen() {
        for (int i = 1; i <= 4; i++) {
            if (getData(this.dataFolder, this.world.getName().substring(1),"d." + String.valueOf(i) + ".x") != null) {
                float x = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "d." + String.valueOf(i) + ".x"));
                float y = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "d." + String.valueOf(i) + ".y"));
                float z = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "d." + String.valueOf(i) + ".z"));
                Location loc = new Location(world, x, y, z);
                ItemStack diamond = new ItemStack(Material.DIAMOND);
                Entity item = world.dropItem(loc, diamond);
            }
        }

    }
    void emGen() {
        for (int i = 1; i <= 4; i++) {
            if (getData(this.dataFolder, this.world.getName().substring(1),"e." + String.valueOf(i) + ".x") != null) {
                float x = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "e." + String.valueOf(i) + ".x"));
                float y = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "e." + String.valueOf(i) + ".y"));
                float z = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "e." + String.valueOf(i) + ".z"));
                Location loc = new Location(world, x, y, z);
                ItemStack em = new ItemStack(Material.EMERALD);
                Entity item = world.dropItem(loc, em);
            }
        }
    }
    void ironGen() {
        for (int i = 1; i <= 8; i++) {
            if (getData(this.dataFolder, this.world.getName().substring(1),"g." + String.valueOf(i) + ".x") != null) {
                float x = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "g." + String.valueOf(i) + ".x"));
                float y = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "g." + String.valueOf(i) + ".y"));
                float z = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "g." + String.valueOf(i) + ".z"));
                Location loc = new Location(world, x, y, z);
                ItemStack iron = new ItemStack(Material.IRON_INGOT);
                Entity item = world.dropItem(loc, iron);
            }
        }
    }
    void goldGen() {
        for (int i = 1; i <= 8; i++) {
            if (getData(this.dataFolder, this.world.getName().substring(1),"g." + String.valueOf(i) + ".x") != null) {
                float x = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "g." + String.valueOf(i) + ".x"));
                float y = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "g." + String.valueOf(i) + ".y"));
                float z = Float.parseFloat(getData(this.dataFolder, this.world.getName().substring(1), "g." + String.valueOf(i) + ".z"));
                Location loc = new Location(world, x, y, z);
                ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                Entity item = world.dropItem(loc, gold);
            }
        }
    }

}
