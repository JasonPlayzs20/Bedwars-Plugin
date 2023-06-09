package com.jason.main;


import org.bukkit.entity.Player;
import com.jason.main.Emums.BedwarsModes;

import static com.jason.main.Emums.BedwarsModes.*;

public class BedwarsMatch {
    private BedwarsModes mode = SOLO;

    public void start(Player player) {
        player.sendMessage("Bedwars game started");
        generatorStart(player);
        boolean checks = shopsCheck(player);
        if (!(checks)) {
            end(player);
            player.sendMessage("failed to start game. (shops check failed)");
            return;
        }
        tpToBase(player);
    }

    //start all processed
    public void generatorStart(Player player) {
        player.sendMessage("Generator started");
    }

    public boolean shopsCheck(Player player) {
        //if all there, do nothing, else, spawn them and report them to the player
        return true;
    }

    //tps
    public void tpToBase(Player player) {
        //tp to base
    }

    //ending commands
    public void end(Player player) {
        player.sendMessage("Bedwars game will end");
        generatorStop(player);
    }

    public void generatorStop(Player player) {
        player.sendMessage("Generator stopped");
    }

    public void setMode(BedwarsModes bedwarsModes) {
        mode = bedwarsModes;
    }

    public BedwarsModes getMode() {
        return mode;
    }

}
