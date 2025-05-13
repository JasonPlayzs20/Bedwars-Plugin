package com.jason.main.GameDisplay;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

public class Arenas {
    static HashMap<World, GameManager> arenas = new HashMap<>();

    public static void addArena(World w, GameManager gm) {
        arenas.put(w, gm);
    }
    public static void removeArena(World w) {
        arenas.remove(w);
    }

    public static GameManager getArena(World w) {
        return arenas.get(w);
    }


    public static HashMap getArenas() {
        return arenas;
    }
}
