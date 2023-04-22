package com.jason.main;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.jason.main.bedwars.getData;

public class BedwarsMatch {
    private final bedwars core;
    private Player player;
    //lobbySpawn
    private Location lobbySpawn;

    //diamonds
    private Location Dgen1;
    private Location Dgen2;
    private Location Dgen3;
    private Location Dgen4;

    //emeralds
    private Location Egen1;
    private Location Egen2;
    private Location Egen3;
    private Location Egen4;

    //red
    private Location redSpawn;
    private Location redGen;
    private Location redShop;
    private Boolean redShopFree;
    private Location redDiamondShop;
    private Boolean redDiamondShopFree;
    private Boolean redHasBed;

    //gray
    private Location graySpawn;
    private Location grayGen;
    private Location grayShop;
    private Boolean grayShopFree;
    private Location grayDiamondShop;
    private Boolean grayDiamondShopFree;
    private Boolean grayHasBed;

    //pink
    private Location pinkSpawn;
    private Location pinkGen;
    private Location pinkShop;
    private Boolean pinkShopFree;
    private Location pinkDiamondShop;
    private Boolean pinkDiamondShopFree;
    private Boolean pinkHasBed;

    //white
    private Location whiteSpawn;
    private Location whiteGen;
    private Location whiteShop;
    private Boolean whiteShopFree;
    private Location whiteDiamondShop;
    private Boolean whiteDiamondShopFree;
    private Boolean whiteHasBed;

    //aqua
    private Location aquaSpawn;
    private Location aquaGen;
    private Location aquaShop;
    private Boolean aquaShopFree;
    private Location aquaDiamondShop;
    private Boolean aquaDiamondShopFree;
    private Boolean aquaHasBed;

    //yellow
    private Location yellowSpawn;
    private Location yellowGen;
    private Location yellowShop;
    private Boolean yellowShopFree;
    private Location yellowDiamondShop;
    private Boolean yellowDiamondShopFree;
    private Boolean yellowHasBed;

    //green
    private Location greenSpawn;
    private Location greenGen;
    private Location greenShop;
    private Boolean greenShopFree;
    private Location greenDiamondShop;
    private Boolean greenDiamondShopFree;
    private Boolean greenHasBed;

    //blue
    private Location blueSpawn;
    private Location blueGen;
    private Location blueShop;
    private Boolean blueShopFree;
    private Location blueDiamondShop;
    private Boolean blueDiamondShopFree;
    private Boolean blueHasBed;

    public BedwarsMatch(bedwars core, Player player) {
        this.core = core;
        redSpawn = getData("plugins/BedwarsData", player.getWorld().toString()+".yml","red/spawn");
    }

    public void getLocation(String color, String catiggory) {

    }





}
