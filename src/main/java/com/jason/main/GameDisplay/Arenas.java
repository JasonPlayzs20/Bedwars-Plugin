package com.jason.main.GameDisplay;

import com.jason.main.PlayerEntities.BedwarsPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Arenas {
    static Map<World, GameManager> arenas = new HashMap<>();



    public static void addArena(World w, GameManager gm) {
        arenas.put(w, gm);
    }
    public static void removeArena(World w) {
        arenas.remove(w);
    }

    public static GameManager getArena(World w) {
        return arenas.get(w);
    }


    public static Map<World, GameManager> getArenas() {
        return arenas;
    }
}
