package com.jason.main.Listeners;

import com.jason.main.items.BedwarsItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Handles events related to player actions, including damage and death.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public static void onDamageByEntity(EntityDamageByEntityEvent event) {
        Player damager, victim;

        if (event.getDamager() instanceof Player) damager = (Player) event.getDamager();
        else return;

        if (event.getEntity() instanceof Player) victim = (Player) event.getEntity();
        else return;

        // TODO if damager and victim are the same team, ignore.

        if (victim.hasPotionEffect(PotionEffectType.INVISIBILITY)) victim.removePotionEffect(PotionEffectType.INVISIBILITY);
    }

    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null) return;

        BedwarsItem bedwarsItem = BedwarsItem.from(itemStack);
        if (bedwarsItem == null) return;

        bedwarsItem.onUse(event);

        if (itemStack.getAmount() == 1) event.getPlayer().getInventory().removeItem(itemStack);
        else itemStack.setAmount(itemStack.getAmount() - 1);
    }
}
