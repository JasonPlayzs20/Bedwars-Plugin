package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.bedwars;
import com.jason.main.items.BedwarsItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static com.jason.main.Util.consumeItem;
import static com.sun.javafx.util.Utils.clamp;

public class PlayerFakeDeathEvent implements Listener {
    @EventHandler
    public static void onDeath(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            e.getEntity().sendMessage("e");
            if (e.getDamage() > 6) {
                e.setDamage(6);
//                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player) {
            Player playerDied = (Player) e.getEntity();
            playerDied.getWorld().getPlayers().forEach(player -> {
                Arenas.getArena(player.getWorld()).updateScoreBoard();
            });
//            playerDied.getActivePotionEffects().forEach(potionEffect -> {playerDied.removePotionEffect(potionEffect.getType());});
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                return;
            }
//            playerDied.sendMessage("sriughfo");
            if ((playerDied.getHealth() - e.getFinalDamage()) < 1) {
                playerDied.getActivePotionEffects().forEach(potionEffect -> {playerDied.removePotionEffect(potionEffect.getType());});
//                playerDied.sendMessage("Died"
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
                    playerDied.getWorld().getPlayers().forEach(player -> {
                        if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                            player.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died from fire.");
                        }else {
                            player.sendMessage(ChatColor.BLUE + "FINAL KILL! " +(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died from fire.");
//                            player.sendMessage(ChatColor.BLUE + "FINAL KILL! " + (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " was killed by " + e.getDamager().getCustomName());
                        }
//                        player.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died from fire.");
                    });
                }
                else if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    playerDied.getWorld().getPlayers().forEach(player -> {
                        if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                            player.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " broke their ankles.");
                        }else {
                            player.sendMessage(ChatColor.AQUA + "FINAL KILL! " +(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " broke their ankles.");
                        }
                    });
                }
                else {
                    playerDied.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died... smh");
                    playerDied.getWorld().getPlayers().forEach(player -> {
                        if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                            player.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died... smh");
                        }else {
                            player.sendMessage(ChatColor.AQUA + "FINAL KILL! " +(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " died... smh");
                        }
                    });
                }

                e.setCancelled(true);
                playerDied.setGameMode(GameMode.SPECTATOR);
                playerDied.setHealth(20);
                playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);

                Material bootsMaterial = null;
                for (ItemStack armorPiece : playerDied.getInventory().getArmorContents()) {
                    if (armorPiece == null) continue;
                    if (armorPiece.getType() == Material.CHAINMAIL_BOOTS) {
                        bootsMaterial = Material.CHAINMAIL_BOOTS;
                        break;
                    } else if (armorPiece.getType() == Material.IRON_BOOTS) {
                        bootsMaterial = Material.IRON_BOOTS;
                        break;
                    } else if (armorPiece.getType() == Material.DIAMOND_BOOTS) {
                        bootsMaterial = Material.DIAMOND_BOOTS;
                        break;
                    }
                }
                if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).lastHit) != null) {
                    Player damager = Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).lastHit).getPlayer();
                    for (ItemStack itemStack : playerDied.getInventory().getContents()) {
                        // the unholy code of jason
                        if (itemStack == null) {
                            continue;
                        }
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
                }
                Material axe = null;
                Material pick = null;
                boolean shear = false;
                for (ItemStack tool : playerDied.getInventory()) {

                    if (tool == null) continue;

                    if (tool.getType().name().toLowerCase().contains("_axe")) {
                        if (tool.getType().name().toLowerCase().contains("stone")) {
                            axe = Material.WOOD_AXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("iron")) {
                            axe = Material.STONE_AXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("diamond")) {
                            axe = Material.IRON_AXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("wood")) {
                            axe = Material.WOOD_AXE;
                        }
                    } else if (tool.getType().name().toLowerCase().contains("pick")) {
                        if (tool.getType().name().toLowerCase().contains("iron")) {
                            pick = Material.WOOD_PICKAXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("gold")) {
                            pick = Material.IRON_PICKAXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("diamond")) {
                            pick = Material.GOLD_PICKAXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("wood")) {
                            pick = Material.WOOD_PICKAXE;
                        }
                    }
                    if (tool.getType().name().toLowerCase().contains("shear")) shear = true;

                }
                playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn);
                playerDied.getInventory().clear();
                playerDied.sendMessage(String.valueOf(shear));
                if (axe != null) {
                    playerDied.getInventory().addItem(new ItemStack(Material.AIR));
                    playerDied.getInventory().addItem(new ItemStack(axe));
                    playerDied.getInventory().setItem(1,new ItemStack(axe));
                    playerDied.updateInventory();
                }
                if (pick != null) {
                    playerDied.getInventory().addItem(new ItemStack(Material.AIR));
                    playerDied.getInventory().addItem(new ItemStack(pick));
                    playerDied.getInventory().setItem(2,new ItemStack(pick));
                    playerDied.updateInventory();
                }
                if (shear) {
                    playerDied.getInventory().addItem(new ItemStack(Material.SHEARS));
                    playerDied.getInventory().setItem(3,new ItemStack(Material.SHEARS));
                    playerDied.updateInventory();
                }
                if (bootsMaterial != null) {
                    switch (bootsMaterial) {
                        case CHAINMAIL_BOOTS:
                            playerDied.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            playerDied.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            break;
                        case IRON_BOOTS:
                            playerDied.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            playerDied.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                            break;
                        case DIAMOND_BOOTS:
                            playerDied.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            playerDied.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            break;
                    }
                }

                playerDied.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));
                if (!Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                    playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Your bed is broken, you will not respawn."));
                    if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).lastHit) != null) {
                        Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).lastHit).finals += 1;
                    }
                    return;
                }else {
                    if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).lastHit) != null) {
                    Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).lastHit).kills += 1;
                    }
                }
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
                Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(((Player) e.getEntity())).lastHit = null;
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        playerDied.setHealth(20);
                        playerDied.setGameMode(GameMode.SURVIVAL);
                        playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).baseSpawn);
                        playerDied.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20, 189, false,false));
                    }
                }.runTaskLater(bedwars.getMainInstance(), 5 * 20);
                playerDied.getWorld().getPlayers().forEach(player -> {
                    Arenas.getArena(player.getWorld()).updateScoreBoard();
                });
            }

        }

    }

    @EventHandler
    public static void onDamageByEntity(EntityDamageByEntityEvent e) {
        Player playerDied;

        if (e.getDamager() instanceof TNTPrimed) {
//            e.getEntity().sendMessage("e");
            if (e.getDamage() > 6) {
                e.setDamage(6);
//                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player) {
            ((Player) e.getDamager()).getInventory().getItemInHand().setDurability((short) -1);
            ((Player) e.getDamager()).updateInventory();
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                Player shooter = (Player) arrow.getShooter();
                if (Arenas.getArena(shooter.getWorld()).bedwarsPlayers.get(shooter).team.teamColors == Arenas.getArena(e.getEntity().getWorld()).bedwarsPlayers.get(e.getEntity()).team.teamColors) {
                    e.setCancelled(true);
//                    Bukkit.getServer().getPlayer("IamSorry_").sendMessage(String.valueOf(shooter));
                }
            }
        }



        Player damager = (Player) e.getDamager();

        Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(((Player) e.getEntity())).lastHit = damager;
        if (e.getEntity() instanceof Player) playerDied = (Player) e.getEntity();
        else {
            return;
        }
        playerDied.getInventory().getHelmet().setDurability((short) (-1));
        playerDied.getInventory().getChestplate().setDurability((short) (-1));
        playerDied.getInventory().getLeggings().setDurability((short) (-1));
        playerDied.getInventory().getBoots().setDurability((short) (-1));
        if (e.getDamager() instanceof Player) damager = (Player) e.getDamager();
        else {
            damager.getInventory().getHelmet().setDurability((short) (-1));
            damager.getInventory().getChestplate().setDurability((short) (-1));
            damager.getInventory().getLeggings().setDurability((short) (-1));
            damager.getInventory().getBoots().setDurability((short) (-1));
            damager.getInventory().getItemInHand().setDurability((short)-1);
            if (playerDied.hasPotionEffect(PotionEffectType.INVISIBILITY))
                playerDied.removePotionEffect(PotionEffectType.INVISIBILITY);
            playerDied.getWorld().getPlayers().forEach(player -> {
                player.showPlayer(playerDied);
            });

//            playerDied.sendMessage("sriughfo");
            if ((playerDied.getHealth() - e.getFinalDamage()) < 1) {
                playerDied.getActivePotionEffects().forEach(potionEffect -> {playerDied.removePotionEffect(potionEffect.getType());});
//                playerDied.sendMessage("Died"
                e.setCancelled(true);
                playerDied.setGameMode(GameMode.SPECTATOR);
                playerDied.setHealth(20);
                playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);
//            Arenas.getArena(playerDied.getWorld()).world.sendPluginMessage(bedwars.getMainInstance(), playerDied.getName() + " was killed by " + damager );
                playerDied.getWorld().getPlayers().forEach(p -> {
                    if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                        p.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " was killed by " + e.getDamager().getCustomName());
                    }else {
                        p.sendMessage(ChatColor.AQUA + "FINAL KILL! " + (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " was killed by " + e.getDamager().getCustomName());
                    }

                });

                Material bootsMaterial = null;
                for (ItemStack armorPiece : playerDied.getInventory().getArmorContents()) {
                    if (armorPiece == null) continue;
                    if (armorPiece.getType() == Material.CHAINMAIL_BOOTS) {
                        bootsMaterial = Material.CHAINMAIL_BOOTS;
                        break;
                    } else if (armorPiece.getType() == Material.IRON_BOOTS) {
                        bootsMaterial = Material.IRON_BOOTS;
                        break;
                    } else if (armorPiece.getType() == Material.DIAMOND_BOOTS) {
                        bootsMaterial = Material.DIAMOND_BOOTS;
                        break;
                    }
                }
                Material axe = null;
                Material pick = null;
                boolean shear = false;
                for (ItemStack tool : playerDied.getInventory()) {

                    if (tool == null) continue;
                     
                    if (tool.getType().name().toLowerCase().contains("_axe")) {
                        if (tool.getType().name().toLowerCase().contains("stone")) {
                            axe = Material.WOOD_AXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("iron")) {
                            axe = Material.STONE_AXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("diamond")) {
                            axe = Material.IRON_AXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("wood")) {
                            axe = Material.WOOD_AXE;
                        }
                    } else if (tool.getType().name().toLowerCase().contains("pick")) {
                        if (tool.getType().name().toLowerCase().contains("iron")) {
                            pick = Material.WOOD_PICKAXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("gold")) {
                            pick = Material.IRON_PICKAXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("diamond")) {
                            pick = Material.GOLD_PICKAXE;
                        }
                        if (tool.getType().name().toLowerCase().contains("wood")) {
                            pick = Material.WOOD_PICKAXE;
                        }
                    }
                    if (tool.getType().name().toLowerCase().contains("shear")) shear = true;

                }
                playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn);
                playerDied.getInventory().clear();
                if (shear) {
                    playerDied.getInventory().addItem(new ItemStack(Material.SHEARS));
                    playerDied.getInventory().setItem(3,new ItemStack(Material.SHEARS));
                    playerDied.updateInventory();
                }
                if (axe != null) {
                    playerDied.getInventory().addItem(new ItemStack(Material.AIR));
                    playerDied.getInventory().addItem(new ItemStack(axe));
                    playerDied.getInventory().setItem(1,new ItemStack(axe));
                    playerDied.updateInventory();
                }
                if (pick != null) {
                    playerDied.getInventory().addItem(new ItemStack(Material.AIR));
                    playerDied.getInventory().addItem(new ItemStack(pick));
                    playerDied.getInventory().setItem(2,new ItemStack(pick));
                    playerDied.updateInventory();
                }
                if (bootsMaterial != null) {
                    switch (bootsMaterial) {
                        case CHAINMAIL_BOOTS:
                            playerDied.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                            playerDied.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                            break;
                        case IRON_BOOTS:
                            playerDied.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            playerDied.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                            break;
                        case DIAMOND_BOOTS:
                            playerDied.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            playerDied.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            break;
                    }
                }


                playerDied.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));
//            Arenas.getArena(playerDied.getWorld()).wearArmor(playerDied,Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.teamColors);

                if (!Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                    playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Your bed is broken, you will not respawn."));
                    Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(e.getEntity()).lastHit).finals += 1;
                    return;
                }else {
                    Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(e.getEntity()).lastHit).kills += 1;
                }
                damager.getWorld().getPlayers().forEach(player -> {
                    Arenas.getArena(player.getWorld()).updateScoreBoard();
                });
                Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(((Player) e.getEntity())).lastHit = null;
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
                        playerDied.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20, 189, false,false));
                    }
                }.runTaskLater(bedwars.getMainInstance(), 5 * 20);
            }
            return;
        }

        // TODO if damager and victim are the same team, ignore.
        // TODO JASON WHY YOU NO CHECK FOR NULL???????????????????

        if (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.teamColors == Arenas.getArena(damager.getWorld()).bedwarsPlayers.get(damager).team.teamColors) {
            e.setCancelled(true);

            return;
        }

        if (playerDied.hasPotionEffect(PotionEffectType.INVISIBILITY))
            playerDied.removePotionEffect(PotionEffectType.INVISIBILITY);
            playerDied.getWorld().getPlayers().forEach(player -> {
                player.showPlayer(playerDied);
            });

//            playerDied.sendMessage("sriughfo");
        if ((playerDied.getHealth() - e.getFinalDamage()) < 1) {
            playerDied.getActivePotionEffects().forEach(potionEffect -> {playerDied.removePotionEffect(potionEffect.getType());});
//                playerDied.sendMessage("Died"
            e.setCancelled(true);
            playerDied.setGameMode(GameMode.SPECTATOR);
            playerDied.setHealth(20);
            playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).mainSpawn);
//            Arenas.getArena(playerDied.getWorld()).world.sendPluginMessage(bedwars.getMainInstance(), playerDied.getName() + " was killed by " + damager );
            Player finalDamager = damager;
            playerDied.getWorld().getPlayers().forEach(p -> {
                if (Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                    p.sendMessage((Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " was killed by " + (Arenas.getArena(finalDamager.getWorld()).bedwarsPlayers.get(finalDamager).team.chatColor) + finalDamager.getName());
                }else {
                    p.sendMessage(ChatColor.AQUA + "FINAL KILL! " + (Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.chatColor) + playerDied.getName() + ChatColor.RED + " was killed by " + (Arenas.getArena(finalDamager.getWorld()).bedwarsPlayers.get(finalDamager).team.chatColor) + finalDamager.getName());
                }
            });

            Material bootsMaterial = null;
            for (ItemStack armorPiece : playerDied.getInventory().getArmorContents()) {
                if (armorPiece == null) continue;
                if (armorPiece.getType() == Material.CHAINMAIL_BOOTS) {
                    bootsMaterial = Material.CHAINMAIL_BOOTS;
                    break;
                } else if (armorPiece.getType() == Material.IRON_BOOTS) {
                    bootsMaterial = Material.IRON_BOOTS;
                    break;
                } else if (armorPiece.getType() == Material.DIAMOND_BOOTS) {
                    bootsMaterial = Material.DIAMOND_BOOTS;
                    break;
                }
            }

            for (ItemStack itemStack : playerDied.getInventory().getContents()) {
                // the unholy code of jason
                if (itemStack == null) {continue;}
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
            Material axe = null;
            Material pick = null;
            boolean shear = false;
            for (ItemStack tool : playerDied.getInventory()) {

                if (tool == null) continue;
                 
                if (tool.getType().name().toLowerCase().contains("_axe")) {
                    if (tool.getType().name().toLowerCase().contains("stone")) {
                        axe = Material.WOOD_AXE;
                    }
                    if (tool.getType().name().toLowerCase().contains("iron")) {
                        axe = Material.STONE_AXE;
                    }
                    if (tool.getType().name().toLowerCase().contains("diamond")) {
                        axe = Material.IRON_AXE;
                    }
                    if (tool.getType().name().toLowerCase().contains("wood")) {
                        axe = Material.WOOD_AXE;
                    }
                } else if (tool.getType().name().toLowerCase().contains("pick")) {
                    if (tool.getType().name().toLowerCase().contains("iron")) {
                        pick = Material.WOOD_PICKAXE;
                    }
                    if (tool.getType().name().toLowerCase().contains("gold")) {
                        pick = Material.IRON_PICKAXE;
                    }
                    if (tool.getType().name().toLowerCase().contains("diamond")) {
                        pick = Material.GOLD_PICKAXE;
                    }
                    if (tool.getType().name().toLowerCase().contains("wood")) {
                        pick = Material.WOOD_PICKAXE;
                    }
                }
                if (tool.getType().name().toLowerCase().contains("shear")) shear = true;

            }
            playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn);
            playerDied.getInventory().clear();
            if (shear) {
                playerDied.getInventory().addItem(new ItemStack(Material.SHEARS));
                playerDied.getInventory().setItem(3,new ItemStack(Material.SHEARS));
                playerDied.updateInventory();
            }
            if (axe != null) {
                playerDied.getInventory().addItem(new ItemStack(Material.AIR));
                playerDied.getInventory().addItem(new ItemStack(axe));
                playerDied.getInventory().setItem(1,new ItemStack(axe));
                playerDied.updateInventory();
            }
            if (pick != null) {
                playerDied.getInventory().addItem(new ItemStack(Material.AIR));
                playerDied.getInventory().addItem(new ItemStack(pick));
                playerDied.getInventory().setItem(2,new ItemStack(pick));
                playerDied.updateInventory();
            }
            Arenas.getArena(playerDied.getWorld()).wearArmor(playerDied,Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.teamColors);

            if (bootsMaterial != null) {
                switch (bootsMaterial) {
                    case CHAINMAIL_BOOTS:
                        playerDied.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        playerDied.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        break;
                    case IRON_BOOTS:
                        playerDied.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        playerDied.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                        break;
                    case DIAMOND_BOOTS:
                        playerDied.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        playerDied.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        break;
                }
            }

            playerDied.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));
//            Arenas.getArena(playerDied.getWorld()).wearArmor(playerDied,Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).team.teamColors);

            if (!Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Your bed is broken, you will not respawn."));
                Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(e.getEntity()).lastHit).finals += 1;
                return;
            } else {
                Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(e.getEntity()).lastHit).kills += 1;
            }
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

            damager.getWorld().getPlayers().forEach(player -> {
                Arenas.getArena(player.getWorld()).updateScoreBoard();
            });
            Arenas.getArena(((Player) e.getEntity()).getWorld()).bedwarsPlayers.get(((Player) e.getEntity())).lastHit = null;
            Player finalPlayerDied1 = playerDied;
            new BukkitRunnable() {

                @Override
                public void run() {
                    finalPlayerDied1.setHealth(20);
                    finalPlayerDied1.setGameMode(GameMode.SURVIVAL);
                    finalPlayerDied1.teleport(Arenas.getArena(finalPlayerDied1.getWorld()).bedwarsPlayers.get(finalPlayerDied1).baseSpawn);
                    playerDied.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20, 189, false,false));
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

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            bedwarsItem.onUse(event);

            event.getPlayer().updateInventory();
            if (event.getPlayer().getInventory().getItemInHand().getAmount() <= 1) {
                event.getPlayer().getInventory().getItemInHand().setType(Material.AIR);
                event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemSlot());
            }
            event.getPlayer().sendMessage("a");
            itemStack.setAmount(itemStack.getAmount() - 1);
            event.getPlayer().updateInventory();
        }
    }
}
