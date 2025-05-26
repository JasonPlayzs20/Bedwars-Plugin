package com.jason.main.GameDisplay;

import com.jason.main.Emums.State;
import com.jason.main.Emums.TeamColors;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.PlayerEntities.Teams;
import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Dragon implements Runnable{
    bedwars game;
    GameManager gameManager;
    int countdownSeconds;
    public Dragon(bedwars game, GameManager gameManager, int countdownSeconds) {
        this.game = game;
        this.gameManager = gameManager;
        this.countdownSeconds = countdownSeconds;

    }
    public void start() {
//        Bukkit.getPlayer("IamSorry_").sendMessage("Countdown started");
//        gameManager.state = State.COUNTDOWN;
        gameManager.countdownID = Bukkit.getScheduler().runTaskTimer(game, this,0,20).getTaskId();

    }
    public void stop() {
//        Bukkit.getPlayer("IamSorry_").sendMessage("Countdown stopped");
        gameManager.state = State.COUNTDOWN;
        Bukkit.getScheduler().cancelTask(gameManager.countdownID);
    }
    public int timer() {
        return countdownSeconds;
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
//            Bukkit.getPlayer("IamSorry_").sendMessage("Countdown Finished");
            for (Player player : gameManager.world.getPlayers()) {
                player.sendTitle(ChatColor.RED+"Bed Break/Sudden Death","The world border will shrink to 15 blocks over 2 minutes");
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1,1);
                Arenas.getPlayer(player).hasBed = false;

            }
            Set<TeamColors> aliveTeams = new HashSet<>();
            gameManager.world.getWorldBorder().setCenter(new Location(gameManager.world, 0,0,0));
            gameManager.world.getWorldBorder().setSize(220);
            gameManager.world.getWorldBorder().setSize(15,120);
// Step 1: Loop through players, collect their alive teams
            for (Player players : gameManager.world.getPlayers()) {
                if (players.getGameMode() == GameMode.SPECTATOR) {continue;}
                BedwarsPlayer bwPlayer = Arenas.getPlayer(players);
                if (bwPlayer == null) continue;

                TeamColors team = bwPlayer.team.teamColors;
                if (team != TeamColors.NA && !aliveTeams.contains(team)) {
                    aliveTeams.add(team);
                }
            }
            int i = 0;
// Step 2: Spawn a dragon for each alive team
            for (TeamColors team : aliveTeams) {
//                Bukkit.getPlayer("IamSorry_").sendMessage(String.valueOf(team));
                Location spawnLoc = new Location(gameManager.world, 0,i, 0); // Or wherever you want the dragon to appear
                EnderDragon enderDragon = (EnderDragon) gameManager.world.spawnEntity(spawnLoc, EntityType.ENDER_DRAGON);
                i+= 40;
                enderDragon.setCustomName(ChatColor.valueOf(team.name()) + team.name() + " Dragon");
            }
            Bukkit.getScheduler().cancelTask(gameManager.countdownID);
//            gameManager.startGame();
            return;
        }

        countdownSeconds--;
    }
}
