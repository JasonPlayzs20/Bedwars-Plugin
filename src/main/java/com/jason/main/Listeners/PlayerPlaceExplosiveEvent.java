package com.jason.main.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerPlaceExplosiveEvent implements Listener {
    @EventHandler
    public static void onFireball(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand().getType().equals(Material.FIREBALL)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            }
//            Fireball fireball = (Fireball) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREBALL);
            player.launchProjectile(Fireball.class);
        }
    }
}
