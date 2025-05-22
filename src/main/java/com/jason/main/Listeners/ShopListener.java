package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.bedwars;
import com.jason.main.inventory.shops.diamond.DiamondShop;
import com.jason.main.inventory.shops.quick.QuickBuyShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ShopListener implements Listener {
    @EventHandler
    public static void shop(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) e.getRightClicked();
            Player player = e.getPlayer();
            if (villager.getCustomName().contains("ond")) {
                player.sendMessage("Diamonds");
               Bukkit.getScheduler().runTaskLater(bedwars.getMainInstance(), new Runnable() {
                   GameManager bwInstance = Arenas.getArenas().get((player).getWorld());
                   DiamondShop shop = new DiamondShop(player, bwInstance);
                    @Override
                    public void run() {
                        player.closeInventory();
                        shop.open();
                    }
                },1);

            } else if (villager.getCustomName().contains("Shop")) {
//                player.sendMessage("sjop");


                Bukkit.getScheduler().runTaskLater(bedwars.getMainInstance(), new Runnable() {
                    GameManager bwInstance = Arenas.getArenas().get((player).getWorld());
                    QuickBuyShop shop = new QuickBuyShop(player);
                    @Override
                    public void run() {
                        player.closeInventory();
//                        player.sendMessage("jerry");
                        shop.open();
//                        player.sendMessage("Loaded inv");
                    }
                },1);

            }
        }
    }
}
