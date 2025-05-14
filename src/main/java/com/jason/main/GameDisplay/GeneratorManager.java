package com.jason.main.GameDisplay;

import com.jason.main.bedwars;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.jason.main.bedwars.getData;
import static com.jason.main.bedwars.getMainInstance;

public class GeneratorManager {
    String dataFolder;
    List<Location> diamondLoc;
    List<Location> genLoc;
    List<Location> emLoc;
    List<Location> shopLoc;
    List<Location> diaShopLoc;

    List<ArmorStand> emArmorStands = new ArrayList<>();
    List<ArmorStand> diamondArmorStands = new ArrayList<>();

    public static World world;

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
    ArmorStand diamondArmor;
    void diamondGen() {
        final int[] diamondTimer = {30};
        for (Location loc : diamondLoc) {
            diamondArmor = world.spawn(loc.add(0,3,0), ArmorStand.class);
            diamondArmor.setVisible(false);
            diamondArmor.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
            diamondArmor.setCustomNameVisible(true);
            diamondArmor.setGravity(false);
            diamondArmorStands.add(diamondArmor);
            loc.add(0,-3,0);
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                if (diamondTimer[0] == 0) {
                    for (Location location : diamondLoc) {
                        ItemStack diamond = new ItemStack(Material.DIAMOND);
                        Entity item = world.dropItem(location, diamond);
                        item.setVelocity(new Vector(0, 0, 0));
                        diamondTimer[0] = 30;
                    }
                }
                diamondTimer[0]--;
                for (ArmorStand dia : diamondArmorStands) {
                    dia.setCustomName(ChatColor.AQUA + ("Next Diamond In: " + diamondTimer[0]));
                }
            }
        }.runTaskTimer(getMainInstance(), 0, 20);


    }
    ArmorStand emeraldArmor;
    void emGen() {
        final int[] diamondTimer = {30};
        for (Location loc : emLoc) {
            emeraldArmor = world.spawn(loc.add(0,3,0), ArmorStand.class);
            emeraldArmor.setVisible(false);
            emeraldArmor.setHelmet(new ItemStack(Material.EMERALD_BLOCK));
            emeraldArmor.setCustomNameVisible(true);
            emeraldArmor.setGravity(false);
            emArmorStands.add(emeraldArmor);
            loc.add(0,-3,0);
        }
        new BukkitRunnable() {


            @Override
            public void run() {
                if (diamondTimer[0] == 0) {
                    for (Location loc : emLoc) {
                        ItemStack em = new ItemStack(Material.EMERALD);
                        Entity item = world.dropItem(loc, em);
                        item.setVelocity(new Vector(0, 0, 0));
                        diamondTimer[0] = 30;

                    }
                }
                diamondTimer[0]--;
                for (ArmorStand em : emArmorStands) {
                    em.setCustomName(ChatColor.GREEN + ("Next Emerald In: " + diamondTimer[0]));
                }
            }
        }.runTaskTimer(getMainInstance(), 0, 20);


    }

    void ironGen() {
        new BukkitRunnable() {
            int diamondTimer = 1;

            @Override
            public void run() {

                for (Location loc : genLoc) {
                    ItemStack iron = new ItemStack(Material.IRON_INGOT);
                    Entity item = world.dropItem(loc, iron);
                    item.setVelocity(new Vector(0, 0, 0));


                }
                diamondTimer--;
            }
        }.runTaskTimer(getMainInstance(), 0, 15);


        for (Location loc : genLoc) {
            ItemStack iron = new ItemStack(Material.IRON_INGOT);
            Entity item = world.dropItem(loc, iron);
            item.setVelocity(new Vector(0, 0, 0));


        }
    }

    void goldGen() {
        new BukkitRunnable() {
            int diamondTimer = 1;

            @Override
            public void run() {

                for (Location loc : genLoc) {
                    ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                    Entity item = world.dropItem(loc, gold);
                    item.setVelocity(new Vector(0, 0, 0));


                }
                diamondTimer--;
            }
        }.runTaskTimer(getMainInstance(), 0, 45);
        for (Location loc : genLoc) {
            ItemStack gold = new ItemStack(Material.GOLD_INGOT);
            Entity item = world.dropItem(loc, gold);
            item.setVelocity(new Vector(0, 0, 0));


        }
    }

}
