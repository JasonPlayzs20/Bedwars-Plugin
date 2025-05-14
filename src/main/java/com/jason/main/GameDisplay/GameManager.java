package com.jason.main.GameDisplay;

import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jason.main.Commands.EndCommand.removeFile;
import static com.jason.main.bedwars.*;
import static com.jason.main.bedwars.setData;
import static org.bukkit.Material.SPONGE;

public class GameManager {
    World world;
    Player player;
    static State state;
    List<Player> players;
    static HashMap<Player, BedwarsPlayer> bedwarsPlayers = new HashMap<>();
    public static int countdownID;
    Countdown countdown;
    GeneratorManager generatorManager;
    ShopManager shopManager;
    List<Location> diamondLoc = new ArrayList<>();
    List<Location> genLoc = new ArrayList<>();
    List<Location> emLoc = new ArrayList<>();
    List<Location> shopLoc = new ArrayList<>();
    List<Location> diaShopLoc = new ArrayList<>();
    public static List<Block> blockList = new ArrayList<>();

    public GameManager(World world, Player player) {
        this.world = world;
        this.player = Bukkit.getPlayer("IamSorry_");
        this.state = State.RECRUITING;
        this.players = new ArrayList<>();


    }

    public void startArena() {
        scan(Bukkit.getPlayer("IamSorry_"), world);
        player.sendMessage(emLoc.toString());
        state = State.RECRUITING;
        this.players = world.getPlayers();
        player.sendMessage(String.valueOf(world.getPlayers().size()));
        if (this.players.size() == 1) {
            player.sendMessage("done recruit");
            countdown = new Countdown(bedwars.getMainInstance(), this, Integer.parseInt(getData("plugins/BedwarsInfo", "serverpath.yml","countdownSeconds")));
            countdown.start();
            state = State.PLAYING;
        }
        if (this.players.size() == 0) {
//            Bukkit.getPlayer("IamSorry_").sendMessage(countdown.toString());
            Bukkit.getScheduler().cancelTask(countdownID);
            try {
                player.sendMessage(world.getName());
                removeFile(player, serverpath + ".", world.getName().substring(1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startGame() {
        this.player.sendMessage("Game Started");
        Bukkit.getPlayer("IamSorry_").sendMessage("Game Started");
        state = State.PLAYING;
        generatorManager = new GeneratorManager(world, diamondLoc, genLoc, emLoc, shopLoc, diaShopLoc);
        generatorManager.start();


    }

    void endGame() {
        this.player.sendMessage("Game Ended");
        state = State.RECRUITING;
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

    public void scan(Player player, World world) {
//        player.sendMessage("Scanning");
        String dataFolder = "plugins/BedwarsInfo";
        int firstLevel = Integer.parseInt((getData(dataFolder, world.getName().substring(1) + ".yml", "lobbySpawn.y")));
        Location loc1 = null;
        Location loc2 = null;
        for (int x = -150; x <= 150; x++) {
            for (int z = -150; z <= 150; z++) {
                Location scanningLoc = new Location(world, x, firstLevel, z);
                if (scanningLoc.getBlock().getType().equals(SPONGE)) {
                    if (loc1 == null) {
                        loc1 = scanningLoc;
                    } else {
                        loc2 = scanningLoc;
                    }
                }
            }
        }
        Location finalLoc = loc2;
        Location finalLoc1 = loc1;
        List<Location> locations = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(bedwars.getMainInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                for (int x = finalLoc1.getBlockX(); x <= finalLoc.getBlockX(); x++) {
                    for (int z = finalLoc1.getBlockZ(); z <= finalLoc.getBlockZ(); z++) {
                        for (int y = 0; y <= 100; y++) {
                            Location scanningLoc = new Location(world, x, y, z);
                            if (scanningLoc.getBlock().getState() instanceof org.bukkit.block.Sign) {
                                locations.add(scanningLoc);
                            }
                        }
                    }
                }
                Bukkit.getScheduler().runTask(bedwars.getMainInstance(), new BukkitRunnable() {
                    @Override
                    public void run() {
                        getSign(locations, player, world);
                    }
                });
            }
        });

    }

    public void getSign(List<Location> locations, Player player, World world) {
        player.sendMessage("get Sign");
//        player.sendMessage(locations.toString());
        for (Location loc : locations) {
            player.sendMessage("For looping");
            Sign sign = (Sign) loc.getBlock().getState();
            String data = sign.getLine(0).substring(0, 1);
            player.sendMessage("got data: " + data);
            Location oldLoc = loc;
            world.getBlockAt(loc).setType(Material.AIR);
            loc = loc.add(0.5,0.5,0.5);
            if (data == "p") {

            } else if (data.contains("g")) {
                player.sendMessage("Try to add genLoc");
                genLoc.add(loc);
                player.sendMessage("generator");
            } else if (data.contains("s")) {
                shopLoc.add(loc);
                player.sendMessage("shop");
            } else if (data.contains("d")) {
                diaShopLoc.add(loc);
                player.sendMessage("diasShop");
            }
            if (sign.getLine(0).substring(1).contains("e")) {
                emLoc.add(loc);
                player.sendMessage("em");
                player.sendMessage(emLoc.toString());
            } else if (sign.getLine(0).substring(1).contains("d")) {
                diamondLoc.add(loc);
                player.sendMessage("dimondLoc");
                player.sendMessage(diamondLoc.toString());
            }
            player.sendMessage("test: ");

        }
    }
}
