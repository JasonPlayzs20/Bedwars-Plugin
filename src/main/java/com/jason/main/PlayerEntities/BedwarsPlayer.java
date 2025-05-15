package com.jason.main.PlayerEntities;

import com.jason.main.GameDisplay.GameManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

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
        mainSpawn = new Location(world, 0, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", "lobbySpawn.y")) - 30, 0);
        baseSpawn = new Location(world, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.x")), Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.y")) + 0.5, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.z")));


    }

    public void start() {
        player.teleport(baseSpawn);
    }

    public Location baseLoc() {
        return baseSpawn;
    }

    public Teams getTeam() {
        return team;
    }
}
