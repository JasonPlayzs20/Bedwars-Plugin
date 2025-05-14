package com.jason.main.items;

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
        World world = location.getWorld();
        Silverfish silverfish = (Silverfish) world.spawnEntity(location, EntityType.SILVERFISH);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (silverfish.isDead()) this.cancel();

                silverfish.setTarget(null);
                silverfish.setTicksLived(1);
                silverfish.setCustomName(spawner.getDisplayName() + "'s Bedbug" + ChatColor.RED + " ❤" + (int) silverfish.getHealth());
                silverfish.setCustomNameVisible(true);
            }
        }.runTaskTimer(bedwars.getMainInstance(), 0, 1);
    }
}
