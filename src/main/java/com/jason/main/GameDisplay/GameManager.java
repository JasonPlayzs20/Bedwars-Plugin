package com.jason.main.GameDisplay;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class GameManager {
    World world;
    Player player;
    Arena arena;

    public GameManager(World world, Player player, Arena arena) {
        this.world = world;
        this.player = player;
        this.arena = arena;
    }

    void startGame() {
        this.player.sendMessage("Game Started");
        arena.state = State.PLAYING;

    }

    void endGame() {
        this.player.sendMessage("Game Ended");
        arena.state = State.RECRUITING;
    }

    void startGenerator(Location location) {
        this.player.sendMessage("Generator Started");
    }

    void endGenerator() {
        this.player.sendMessage("Generator Ended");
    }

    void spawnShop(Location location) {
        this.world.spawnEntity(location, EntityType.VILLAGER);
    }

}
