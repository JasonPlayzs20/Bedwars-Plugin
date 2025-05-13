package com.jason.main.GameDisplay;

import com.jason.main.bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Countdown implements Runnable {
    bedwars game;
    GameManager gameManager;
    int countdownSeconds;
    public Countdown(bedwars game, GameManager gameManager, int countdownSeconds) {
        this.game = game;
        this.gameManager = gameManager;
        this.countdownSeconds = countdownSeconds;

    }
    public void start() {
        Bukkit.getPlayer("IamSorry_").sendMessage("Countdown started");
        GameManager.state = State.COUNTDOWN;
        Bukkit.getScheduler().runTaskTimer(game, this,0,20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
            Bukkit.getPlayer("IamSorry_").sendMessage("Countdown Finished");
            Bukkit.getScheduler().cancelAllTasks();
            gameManager.startGame();
            return;
        }
        for (Player player : gameManager.world.getPlayers()) {
            player.sendTitle(String.valueOf(countdownSeconds),"");
        }
        countdownSeconds--;
    }
}
