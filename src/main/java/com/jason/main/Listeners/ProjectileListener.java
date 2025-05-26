package com.jason.main.Listeners;

import com.jason.main.items.BedbugItem;
import com.jason.main.items.BridgeEgg;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ProjectileListener implements Listener {
    @EventHandler
    public static void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        if (projectile instanceof Snowball && shooter instanceof Player) {
            BedbugItem.summonBedbug((Player) shooter, event.getEntity().getLocation());
        }
    }

    @EventHandler
    public static void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        if (projectile instanceof Egg && shooter instanceof Player) {
            BridgeEgg.placeBridgeBlocks((Player) shooter, (Egg) projectile);
        }
    }
}
