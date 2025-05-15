package com.jason.main.PlayerEntities;

import com.jason.main.GameDisplay.GameManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static com.jason.main.bedwars.getData;
import static com.jason.main.bedwars.serverpath;

public class BedwarsPlayer {
    Teams team;
    Player player;
    GameManager gameManager;
    World world;
    Location mainSpawn;
    Location baseSpawn;

    public BedwarsPlayer(Player player, GameManager gameManager, Teams teams) {
        this.player = player;
        this.gameManager = gameManager;
        this.team = teams;
        this.world = gameManager.world;
        mainSpawn = new Location(world, 0, Double.parseDouble(getData(serverpath, world.getName().substring(1), "lobbySpawn.y")) - 10, 0);
        baseSpawn = new Location(world, Double.parseDouble(getData(serverpath, world.getName().substring(1), team.toString().substring(0, 1) + "p.x")), Double.parseDouble(getData(serverpath, world.getName().substring(1), team.toString().substring(0, 1) + "p.y")) + 0.5, Double.parseDouble(getData(serverpath, world.getName().substring(1), team.toString().substring(0, 1) + "p.z")));

    }

    public void start() {
        player.teleport(baseSpawn);
    }

    public Location baseLoc() {
        return baseSpawn;
    }

}
