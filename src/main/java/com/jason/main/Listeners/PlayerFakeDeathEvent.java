package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.bedwars;
import com.jason.main.items.BedwarsItem;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerFakeDeathEvent implements Listener {
    @EventHandler
    public static void onDeath(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player playerDied = (Player) e.getEntity();
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                return;
            }
//            playerDied.sendMessage("sriughfo");
            if ((playerDied.getHealth() - e.getFinalDamage()) < 1) {
//                playerDied.sendMessage("Died"
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
                    playerDied.getWorld().getPlayers().forEach(player -> {
                        player.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died from fire.");
                    });
                }
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    playerDied.getWorld().getPlayers().forEach(player -> {
                        player.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " broke their ankles.");
                    });
                }
                e.setCancelled(true);
                playerDied.setGameMode(GameMode.SPECTATOR);
                playerDied.setHealth(20);
                playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);

                playerDied.getInventory().clear();

                new BukkitRunnable() {
                    int timer = 5;

                    @Override
                    public void run() {
                        if (timer == 0) {
                            cancel();
                            return;
                        }
                        playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Respawning in : " + timer));
                        timer--;
                    }
                }.runTaskTimerAsynchronously(bedwars.getMainInstance(), 0, 20);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        playerDied.setHealth(20);
                        playerDied.setGameMode(GameMode.SURVIVAL);
                        playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).baseSpawn);
                    }
                }.runTaskLater(bedwars.getMainInstance(), 5 * 20);
            }

        }

    }

    @EventHandler
    public static void onDamageByEntity(EntityDamageByEntityEvent e) {
        Player damager, playerDied;



        if (e.getDamager() instanceof Player) damager = (Player) e.getDamager();
        else {
            damager = null;
            return;
        }

        if (e.getEntity() instanceof Player) playerDied = (Player) e.getEntity();
        else {
            playerDied = null;
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                Player player = (Player) arrow.getShooter();
                if (Arenas.getArena(player.getWorld()).bedwarsPlayers.get(player).team.teamColors == Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.teamColors) {
                    e.setCancelled(true);
                    return;
                }
            }else return;
        }


        // TODO if damager and victim are the same team, ignore.
        if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.teamColors == Arenas.getArena(damager.getWorld()).bedwarsPlayers.get(damager).team.teamColors) {
            e.setCancelled(true);
            return;
        }

        if (playerDied.hasPotionEffect(PotionEffectType.INVISIBILITY))
            playerDied.removePotionEffect(PotionEffectType.INVISIBILITY);

//            playerDied.sendMessage("sriughfo");
        if ((playerDied.getHealth() - e.getFinalDamage()) < 1) {
//                playerDied.sendMessage("Died"
            e.setCancelled(true);
            playerDied.setGameMode(GameMode.SPECTATOR);
            playerDied.setHealth(20);
            playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);
//            Arenas.getArena(playerDied.getWorld()).world.sendPluginMessage(bedwars.getMainInstance(), playerDied.getName() + " was killed by " + damager );
            playerDied.getWorld().getPlayers().forEach(p -> {
                p.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() +ChatColor.RED + " was killed by " + (Arenas.getArena(damager.getWorld()).bedwarsPlayers.get(damager).team.chatColor));
            });
            for (ItemStack itemStack : playerDied.getInventory().getContents()) {
                if (itemStack.getType() == Material.IRON_INGOT) {
                    damager.getInventory().addItem(itemStack);
                }
                if (itemStack.getType() == Material.GOLD_INGOT) {
                    damager.getInventory().addItem(itemStack);
                }
                if (itemStack.getType() == Material.DIAMOND) {
                    damager.getInventory().addItem(itemStack);
                }
                if (itemStack.getType() == Material.EMERALD) {
                    damager.getInventory().addItem(itemStack);
                }
            }

            playerDied.getInventory().clear();

            Player finalPlayerDied = playerDied;
            new BukkitRunnable() {
                int timer = 5;

                @Override
                public void run() {
                    if (timer == 0) {
                        cancel();
                        return;
                    }
                    finalPlayerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Respawning in : " + timer));
                    timer--;
                }
            }.runTaskTimerAsynchronously(bedwars.getMainInstance(), 0, 20);

            Player finalPlayerDied1 = playerDied;
            new BukkitRunnable() {

                @Override
                public void run() {
                    finalPlayerDied1.setHealth(20);
                    finalPlayerDied1.setGameMode(GameMode.SURVIVAL);
                    finalPlayerDied1.teleport(Arenas.getArena(finalPlayerDied1.getWorld()).bedwarsPlayers.get(finalPlayerDied1).baseSpawn);
                }
            }.runTaskLater(bedwars.getMainInstance(), 5 * 20);
        }


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
