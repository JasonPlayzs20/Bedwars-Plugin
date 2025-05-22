package com.jason.main.items;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.bedwars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BedbugItem extends BedwarsItem {
    public BedbugItem() {
        super(Material.SNOW_BALL, "Bedbug");
        register(this);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {

    }

    public static void summonBedbug(Player spawner, Location location) {
        BedwarsPlayer bwPlayer = Arenas.getPlayer(spawner.getPlayer());
        if (bwPlayer != null) {
            Silverfish silverfish = (Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH);
            new BedwarsEntity(silverfish, "Bed Bug", bwPlayer).run();
        }
    }
}
