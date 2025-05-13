package com.jason.main.GameDisplay;

import com.jason.main.bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jason.main.Commands.EndCommand.removeFile;

public class GameManager {
    World world;
    Player player;
    static State state;
    List<Player> players;
    public static int countdownID;
    Countdown countdown;

    public GameManager(World world, Player player) {
        this.world = world;
        this.player = Bukkit.getPlayer("IamSorry_");
        this.state = State.RECRUITING;
        this.players = new ArrayList<>();

    }

    public void startArena() {
        state = State.RECRUITING;
        this.players = world.getPlayers();
        player.sendMessage(String.valueOf(world.getPlayers().size()));
        if (this.players.size() == 2) {
            player.sendMessage("done recruit");
            countdown = new Countdown(bedwars.getMainInstance(), this, 30);
            countdown.start();
            state = State.PLAYING;
        }
        if (this.players.size() == 0) {
            countdown.stop();
            try {
                player.sendMessage(world.getName());
                removeFile(player, "/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/.", world.getName().substring(1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startGame() {
        this.player.sendMessage("Game Started");
        Bukkit.getPlayer("IamSorry_").sendMessage("Game Started");
        state = State.PLAYING;

    }

    void endGame() {
        this.player.sendMessage("Game Ended");
        state = State.RECRUITING;
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

    public State getState() {
        return state;
    }
}
