package com.jason.main.GameDisplay;

import com.jason.main.Emums.State;
import com.jason.main.Emums.TeamColors;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.PlayerEntities.Teams;
import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.util.*;

import static com.jason.main.Commands.EndCommand.removeFile;
import static com.jason.main.bedwars.*;
import static org.bukkit.Material.SPONGE;

public class GameManager  {
    public World world;
    public State state;
    public List<Item> droppedItems = new ArrayList<>();
    List<Player> players;
    public HashMap<Player, BedwarsPlayer> bedwarsPlayers = new HashMap<>();
    public  int countdownID;
    Countdown countdown = null;
    String won = null;

    public Countdown getCountdown() {
        return countdown;
    }

    GeneratorManager generatorManager;
    Dragon dragon;
    ShopManager shopManager;
    List<Location> diamondLoc = new ArrayList<>();
    public List<Location> genLoc = new ArrayList<>();
    List<Location> emLoc = new ArrayList<>();
    List<Location> shopLoc = new ArrayList<>();
    List<Location> diaShopLoc = new ArrayList<>();
    public HashMap<Location, String > bedLoc = new HashMap<>();
    public  List<Block> blockList = new ArrayList<>();
    public HashMap<TeamColors, Integer> teamCount = new HashMap<>();
    public HashMap<TeamColors,Color> colors = new HashMap<>();
    int max_players = Integer.parseInt(getData("plugins/BedwarsInfo", "serverpath.yml", "maxPlayers"));
    int team_players = Integer.parseInt(getData("plugins/BedwarsInfo", "serverpath.yml", "teamPlayers"));


    public GameManager(World world) {
        this.world = world;
//        this.player = Bukkit.getPlayer("IamSorry_");
        this.state = State.RECRUITING;
        this.players = new ArrayList<>();
        for (TeamColors teamColor : TeamColors.values()) {
            teamCount.put(teamColor, 0);
        }
        colors.put(TeamColors.BLUE,Color.BLUE);
        colors.put(TeamColors.GREEN,Color.GREEN);
        colors.put(TeamColors.RED,Color.RED);
        colors.put(TeamColors.YELLOW, Color.YELLOW);


    }

    public void updateScoreBoard() {
        // First, collect one representative per team to check global bed status
        Map<TeamColors, BedwarsPlayer> teamRepresentatives = new HashMap<>();
        for (Player p : world.getPlayers()) {
            BedwarsPlayer bwPlayer = Arenas.getArena(world).bedwarsPlayers.get(p);
            TeamColors color = bwPlayer.team.teamColors;
            teamRepresentatives.putIfAbsent(color, bwPlayer); // only first player from each team
        }

        // Now, update scoreboard for each player
        for (Player player : world.getPlayers()) {
            Scoreboard bedwarsScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = bedwarsScoreboard.registerNewObjective("bedwars", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Bedwars 2025");

            int i = 2;

            // Global team bed status
            for (TeamColors teamColor : TeamColors.values()) {
                if (teamColor == TeamColors.NA) continue;

                ChatColor colorCode = ChatColor.valueOf(teamColor.toString());
                String teamName = teamColor.toString().substring(0, 1) + teamColor.toString().substring(1).toLowerCase();

                BedwarsPlayer rep = teamRepresentatives.get(teamColor);
                boolean hasBed = rep != null && rep.hasBed;

                String bedStatus = hasBed ? ChatColor.GREEN + "✔" : ChatColor.RED + "X";
                Score color = obj.getScore("  " + colorCode + teamName + ": " + bedStatus);
                color.setScore(i++);
            }

            // Static headers
            Score bar = obj.getScore(ChatColor.DARK_GRAY + "------------------");
            bar.setScore(1);
            Score commtech = obj.getScore(ChatColor.GOLD.toString() + "Commtech Tournament");
            commtech.setScore(0);

            // Dynamic player stats
            Score teams = obj.getScore(ChatColor.WHITE + "Teams: ");
            teams.setScore(i++);
            Score space = obj.getScore(ChatColor.DARK_GRAY + "------------------" + ChatColor.GOLD);
            space.setScore(i++);

            BedwarsPlayer current = Arenas.getArena(world).bedwarsPlayers.get(player);

            Score kills = obj.getScore(ChatColor.RED + "Kills: " + ChatColor.WHITE + current.kills);
            kills.setScore(i++);
            Score finals = obj.getScore(ChatColor.RED + "Final Kills: " + ChatColor.WHITE + current.finals);
            finals.setScore(i++);
            Score name = obj.getScore(ChatColor.GREEN + "Name: " + player.getDisplayName());
            name.setScore(i++);
            Score gap = obj.getScore(" ");
            gap.setScore(i++);
            if (dragon.timer() < 60) {
                Score timer = obj.getScore(ChatColor.RED + "Sudden Death in: " + String.valueOf(dragon.timer()));
                timer.setScore(i++);
            }
            if (won != null) {
                Score win = obj.getScore(ChatColor.GOLD.toString() + won + " has won the match!");
                win.setScore(i++);
            }
            player.setScoreboard(bedwarsScoreboard);
        }
    }
    public void addTeam(Player player, TeamColors teamColor, ChatColor chatColor) {
        teamCount.put(teamColor, teamCount.get(teamColor) + 1);
        bedwarsPlayers.get(player).team.teamColors = teamColor;
        bedwarsPlayers.get(player).team.chatColor = chatColor;
    }

    public void addPlayer(Player player) {
        world.setPVP(false);
        bedwarsPlayers.put(player, new BedwarsPlayer(player,this, new Teams(TeamColors.NA, ChatColor.GREEN)));
        addTeam(player, TeamColors.NA, ChatColor.GREEN);
        player.setDisplayName(player.getName());

        players.add(player);
    }



    public void startArena() {
        scan(world);

        state = State.RECRUITING;
        this.players = world.getPlayers();
        if (countdown == null) {
            countdown = new Countdown(bedwars.getMainInstance(), this, Integer.parseInt(getData("plugins/BedwarsInfo", "serverpath.yml", "countdownSeconds")));
        }else {
            System.out.println("There is 2 countdowns, please restart the server!!");
        }
        if (this.players.size() == max_players) {

//            countdown.start();
            state = State.FULL;
        }
        if (this.players.size() == 0) {
//            Bukkit.getPlayer("IamSorry_").sendMessage(countdown.toString());
//            Bukkit.getPlayer("IamSorry_").sendMessage("STOPPING TIMER");
            if (countdown != null) {
                Bukkit.getScheduler().cancelTask(countdownID);
                countdown.stop();
            }
            try {
                removeFile(serverpath + ".", world.getName().substring(1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setTeams() {
//        Player golbal = Bukkit.getPlayer("IamSOrry_");
        players.sort(Comparator.comparing(HumanEntity::getName));
        for (Player player :  players) {
//            player.sendMessage("you");
            if (player.getGameMode() == GameMode.SPECTATOR) {continue;}
            player.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));
            TeamColors teamColors = bedwarsPlayers.get(player).team.teamColors;
            if (teamColors == TeamColors.NA) {
//                player.sendMessage("NA");
                for (TeamColors teamColors1 : TeamColors.values()) {
//                    golbal.sendMessage("Looping through colors: " + teamColors1);
                    if (teamColors1 == TeamColors.NA) {continue;}
                    else if (teamCount.get(teamColors1) < team_players) {
//                        golbal.sendMessage("put into" + teamColors1);
                        bedwarsPlayers.get(player).team.teamColors = teamColors1;
                        bedwarsPlayers.get(player).team.chatColor = ChatColor.valueOf(teamColors1.name());
                        teamCount.put(teamColors1,teamCount.get(teamColors1) + 1);
                        player.setDisplayName(ChatColor.valueOf(teamColors1.name()) + player.getName()+ ChatColor.WHITE + "");
                        player.setPlayerListName(ChatColor.valueOf(teamColors1.name()) + player.getName() );
//                        golbal.sendMessage(String.valueOf(teamCount.get(teamColors1)));
                       wearArmor(player,teamColors1);
                        break;
                    }
                }
            }
        }
    }

    public void wearArmor(Player player, TeamColors teamColors1) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(colors.get(teamColors1));
        helmet.setItemMeta(helmetMeta);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        chestMeta.setColor(colors.get(teamColors1));
        chest.setItemMeta(chestMeta);
        ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legMeta = (LeatherArmorMeta) leg.getItemMeta();
        legMeta.setColor(colors.get(teamColors1));
        leg.setItemMeta(legMeta);
        ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootMeta = (LeatherArmorMeta) boot.getItemMeta();
        bootMeta.setColor(colors.get(teamColors1));
        boot.setItemMeta(bootMeta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(leg);
        player.getInventory().setBoots(boot);
    }

    public void findWin() {
        if (state != State.PLAYING) {
            Bukkit.getLogger().info("[Bedwars] Skipping findWin check: Not in PLAYING state.");
            return;
        }

        Bukkit.getLogger().info("[Bedwars] Running findWin check...");

        Set<TeamColors> aliveTeams = new HashSet<>();
        int alivePlayerCount = 0;

        for (Player player : players) {
            BedwarsPlayer bwPlayer = Arenas.getPlayer(player);
            TeamColors team = bwPlayer.getTeam().teamColors;

            // Skip unassigned players
            if (team == TeamColors.NA) {
                Bukkit.getLogger().info("[Bedwars] Skipping " + player.getName() + ": TeamColors.NA");
                continue;
            }

            if (!bwPlayer.isEliminated) {
                aliveTeams.add(team);
                alivePlayerCount++;
                Bukkit.getLogger().info("[Bedwars] Alive: " + player.getName() + " from team " + team);
            } else {
                Bukkit.getLogger().info("[Bedwars] Dead: " + player.getName() + " from team " + team);
            }
        }

        Bukkit.getLogger().info("[Bedwars] Alive teams: " + aliveTeams);
        Bukkit.getLogger().info("[Bedwars] Alive players: " + alivePlayerCount);

        if (aliveTeams.size() == 1) {
            TeamColors winningTeam = aliveTeams.iterator().next();
            won = winningTeam.toString();
            world.setPVP(false);

            Bukkit.getLogger().info("[Bedwars] Winning team detected: " + winningTeam);

            for (Player player : players) {
                TeamColors playerTeam = Arenas.getPlayer(player).team.teamColors;

                if (playerTeam == winningTeam) {
                    player.sendTitle(ChatColor.GOLD + "VICTORY", ChatColor.YELLOW + "You Won!");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 50, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 50, false, false));
                    player.setAllowFlight(true);
                    player.setFlying(true);
                } else {
                    player.sendTitle(ChatColor.RED + "You Lost", ChatColor.YELLOW.toString() + winningTeam + " Won.");
                }

                player.setGameMode(GameMode.SPECTATOR);
            }

            updateScoreBoard();
        } else {
            Bukkit.getLogger().info("[Bedwars] No winner yet.");
        }
    }
    public void startGame() {
//

        world.setPVP(true);
        state = State.PLAYING;
        generatorManager = new GeneratorManager(world, diamondLoc, genLoc, emLoc, shopLoc, diaShopLoc);
        generatorManager.start();
        setTeams();
        dragon = new Dragon(bedwars.getMainInstance(),this,Integer.parseInt(getData("plugins/BedwarsInfo", "serverpath.yml","suddenDeath"))*60);
        dragon.start();
        for (Player player : players) {
            player.sendMessage(ChatColor.RED+"Items that are marked as (NOT WORKING), surprisingly, does not work! If you see a star in the shop, that means that the item is too unstable to be beta tested. Please be patient. Any breaking bugs please report thx.");
//            bedwarsPlayers.get(player).team = new Teams(TeamColors.NA, ChatColor.GREEN);
            bedwarsPlayers.get(player).start();
        }
        updateScoreBoard();


    }

    void endGame() {
        state = State.RECRUITING;
    }


    void endGenerator() {
    }

    void spawnShop(Location location) {
        this.world.spawnEntity(location, EntityType.VILLAGER);
    }

    public State getState() {
        return state;
    }

    public void scan(World world) {
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
                        getSign(locations, world);
                    }
                });
            }
        });

    }

    public void getSign(List<Location> locations, World world) {
//        player.sendMessage(locations.toString());
        for (Location loc : locations) {
            Sign sign = (Sign) loc.getBlock().getState();
            String data = sign.getLine(0).substring(0, 1);
            Location oldLoc = loc;
            world.getBlockAt(loc).setType(Material.AIR);
            loc = loc.add(0.5,0.5,0.5);
            if (data == "p") {

            } else if (data.contains("g")) {
                genLoc.add(loc);
            } else if (data.contains("s")) {
                shopLoc.add(loc);
                Villager shop = world.spawn(loc, Villager.class);
                shop.setMaxHealth(2048);
                shop.setHealth(2048);
                shop.setCustomName("Shop");
                shop.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 50, false,false));
                shop.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 50, false,false));
                shop.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000, 50, false,false));

            } else if (data.contains("d")) {
                diaShopLoc.add(loc);
                Villager shop = world.spawn(loc, Villager.class);
                shop.setMaxHealth(2048);
                shop.setHealth(2048);
                shop.setCustomName("Diamond Shop");
                shop.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 50, false,false));
                shop.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000, 50, false,false));
                shop.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000, 50, false,false));
            } else if (data.contains("f")) {
//                Bukkit.getPlayer("Iamsorry_").sendMessage(loc.toString());
                bedLoc.put(loc,sign.getLine(0).substring(1));
            }
            if (sign.getLine(0).substring(1).contains("e")) {
                emLoc.add(loc);
            } else if (sign.getLine(0).substring(1).contains("d")) {
                diamondLoc.add(loc);
            }

        }
    }



}
