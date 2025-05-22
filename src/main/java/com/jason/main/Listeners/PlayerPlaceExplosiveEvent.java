package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;

public class PlayerPlaceExplosiveEvent implements Listener {

    @EventHandler
    public void onFireball(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (player.getItemInHand().getType() == Material.FIREBALL) {
            if (e.getAction() != Action.RIGHT_CLICK_AIR) {
                e.setCancelled(true);
            }
            if (player.getItemInHand().getAmount() == 1) {
                player.getInventory().remove(player.getItemInHand());
            }else {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            }

            Fireball fireball = player.launchProjectile(Fireball.class);
            Vector direction = player.getLocation().getDirection().normalize();
            fireball.setVelocity(direction.multiply(1));

            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 2, false, false));

            Bukkit.getScheduler().runTaskLater(bedwars.getMainInstance(), () -> {
                player.setVelocity(player.getVelocity().multiply(2));
            }, 2);
        }
    }

    @EventHandler
    public void onFireballExplode(EntityExplodeEvent e) {
        if (!(e.getEntity() instanceof Fireball)) return;

        List<Block> allowedBlocks = Arenas.getArena(e.getEntity().getWorld()).blockList;
        Iterator<Block> it = e.blockList().iterator();

        while (it.hasNext()) {
            Block block = it.next();
            if (!allowedBlocks.contains(block)) {
                it.remove();
            }
        }
    }

    @EventHandler
    public void onFireballHitPlayer(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Fireball)) return;

        Player victim = (Player) e.getEntity();
        Fireball fireball = (Fireball) e.getDamager();

        // Check if the fireball was shot by a player
        if (fireball.getShooter() instanceof Player) {
            Player shooter = (Player) fireball.getShooter();
            if (victim.equals(shooter)) return; // Don't knockback the shooter
        }

        // Vector from fireball to victim
        Vector knockback = victim.getLocation().toVector().subtract(fireball.getLocation().toVector()).normalize().multiply(2);

        Bukkit.getScheduler().runTaskLater(bedwars.getMainInstance(), () -> {
            victim.setVelocity(knockback);
        }, 2);
    }
}