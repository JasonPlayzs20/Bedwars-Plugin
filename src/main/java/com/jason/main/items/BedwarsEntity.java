package com.jason.main.items;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.bedwars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BedwarsEntity {
    private static final double AGRO_DIST = 10;

    public static final Map<Creature, BedwarsEntity> MANAGED_ENTITIES = new HashMap<>();

    private final Creature entity;
    private final String name;
    private final BedwarsPlayer spawner;

    private Player targetEnemy = null;

    public BedwarsEntity(Creature entity, String name, BedwarsPlayer spawner) {
        this.entity = entity;
        this.name = name;
        this.spawner = spawner;
    }

    public Player getTargetEnemy() {
        return targetEnemy;
    }

    public void run() {
        MANAGED_ENTITIES.put(entity, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    MANAGED_ENTITIES.remove(entity);
                    this.cancel();
                    return;
                }

                targetEnemy = findClosestEnemy();
                entity.setTarget(targetEnemy);

                entity.setCustomName(spawner.getPlayer().getDisplayName() + "'s " + name + ChatColor.RED + " ❤ " + (int) entity.getHealth());
                entity.setCustomNameVisible(true);
                entity.setTicksLived(1);
            }
        }.runTaskTimer(bedwars.getMainInstance(), 0, 1);
    }

    private Player findClosestEnemy() {
        Player closestEnemy = null;
        double closestDist = -1;

        List<Entity> nearbyEntities = entity.getNearbyEntities(AGRO_DIST, AGRO_DIST, AGRO_DIST);
        for (Entity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Player) {
                Player enemy = (Player) nearbyEntity;
                BedwarsPlayer bwEnemy = Arenas.getPlayer(enemy);

                if (bwEnemy != null && !bwEnemy.getTeam().teamColors.equals(spawner.getTeam().teamColors)) {
                    double dist = bwEnemy.getPlayer().getLocation().distance(spawner.getPlayer().getLocation());
                    if (closestEnemy == null || dist < closestDist) {
                        closestEnemy = bwEnemy.getPlayer();
                        closestDist = dist;
                    }
                }
            }
        }

        return closestEnemy;
    }
}
