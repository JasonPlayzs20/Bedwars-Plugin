package com.jason.main.GameDisplay;

import com.jason.main.bedwars;
import org.bukkit.Bukkit;

public class Countdown implements Runnable {
    bedwars game;
    Arena arena;
    int countdownSeconds;
    public Countdown(bedwars game, Arena arena, int countdownSeconds) {
        this.game = game;
        this.arena = arena;
        this.countdownSeconds = countdownSeconds;
    }
    public void start() {
        arena.state = State.COUNTDOWN;
        Bukkit.getScheduler().runTaskTimer(game, this,0,20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
            Bukkit.getScheduler().cancelAllTasks();
            return;
        }
        countdownSeconds--;
    }
}
