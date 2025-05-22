package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.PotionMeta;

public class InvisListener implements Listener {
    @EventHandler
    public void onInvis(PlayerItemConsumeEvent e) {

//        Player p = e.getPlayer();
//
//        if (e.getItem() != null && e.getItem().hasItemMeta()) {
//            if (e.getItem().getItemMeta() instanceof PotionMeta) {
//                p.getWorld().getPlayers().forEach(player -> {
//                    if (!player.equals(p)) {
//                        if (!Arenas.getArena(p.getWorld()).bedwarsPlayers.get(player).getTeam().teamColors.equals(Arenas.getArena(p.getWorld()).bedwarsPlayers.get(p).getTeam().teamColors)) {
//                            player.hidePlayer(p);
//                        }
//                    }
//                });
//            }
//        }

    }
}
