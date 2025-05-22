package com.jason.main.Listeners;

import com.jason.main.items.BedwarsEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BedwarsEntityListener implements Listener {
    @EventHandler
    public static void onEntityDamagePlayer(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (damager instanceof Creature && !BedwarsEntity.MANAGED_ENTITIES.containsKey(damager)) {
            BedwarsEntity bwEntity = BedwarsEntity.MANAGED_ENTITIES.get(damager);
            if (!event.getEntity().equals(bwEntity.getTargetEnemy())) event.setCancelled(true);
        }
    }
}
