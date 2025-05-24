package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class InvisListener implements Listener {
    @EventHandler
    public void onInvis(PlayerItemConsumeEvent e) {

        Player p = e.getPlayer();

        if (e.getItem() != null && e.getItem().hasItemMeta()) {
            if (e.getItem().getItemMeta() instanceof PotionMeta) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.getInventory().clear(p.getInventory().getHeldItemSlot());
                    }
                }.runTaskLater(bedwars.getMainInstance(), 3);

            }
        }

    }
}
