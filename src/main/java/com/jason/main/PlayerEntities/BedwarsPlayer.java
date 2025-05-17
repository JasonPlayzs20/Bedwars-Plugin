package com.jason.main.PlayerEntities;

import com.jason.main.GameDisplay.GameManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

import static com.jason.main.bedwars.getData;
import static com.jason.main.bedwars.serverpath;

public class BedwarsPlayer {
    public Teams team;
    Player player;
    GameManager gameManager;
    World world;
    public Location mainSpawn;
    public Location baseSpawn;
    String name;
    String teamName;


    public BedwarsPlayer(Player player, GameManager gameManager, Teams teams) {
        this.player = player;
        this.gameManager = gameManager;
        this.team = teams;
        this.world = gameManager.world;

        name = gameManager.world.getName();

        player.sendMessage(team.teamColors.name());
        player.sendMessage(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", "lobbySpawn.y"));

    }

    public void start() {
        mainSpawn = new Location(world, 0, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", "lobbySpawn.y")) - 30, 0);
        baseSpawn = new Location(world, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.x")), Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.y")) + 0.5, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.z")));
        player.sendMessage(String.valueOf(baseSpawn.getY()));
        player.sendMessage(String.valueOf(baseSpawn.getX()));
        if (Math.abs(baseSpawn.getZ()) > Math.abs(baseSpawn.getX())) {
            if (baseSpawn.getZ() < 0) {
                player.sendMessage("less than 0 Y");
//                baseSpawn.setDirection(new Vector(0, 0, 0));
            } else {
                player.sendMessage("greater than 0 Y");
                baseSpawn.setDirection(new Vector(0, 0, -2500));
            }
        }else  {
            if (baseSpawn.getX() < 0) {
                player.sendMessage("less than 0 X");
                baseSpawn.setDirection(new Vector(90, 0, 0));
            } else {
                player.sendMessage("greater than 0 X");
                baseSpawn.setDirection(new Vector(-90, 0, 0));
            }
        }

        player.teleport(baseSpawn);
    }

    public Location baseLoc() {
        return baseSpawn;
    }

    public Teams getTeam() {
        return team;
    }
}
