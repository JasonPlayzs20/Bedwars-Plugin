package com.jason.main.Commands;

import com.jason.main.Emums.TeamColors;
import com.jason.main.GameDisplay.Arenas;
import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.createInventory;
import static org.bukkit.Bukkit.getServer;

public class LobbyJerry implements CommandExecutor, Listener {
    public static Inventory jerry = createInventory(null,54,"Worlds");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (Objects.equals(args[0], "a")) {
//            Player player = (Player) sender;
//            player.sendMessage("opened inv");
//            player.openInventory(jerry);
//            return true;
//        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
//            player.sendMessage("yo");
            Location loc = player.getLocation();
            Villager villager = loc.getWorld().spawn(loc, Villager.class);
            villager.setCustomName("Jerry");
            villager.setCustomNameVisible(false);
            villager.setCanPickupItems(false);
            villager.setMaxHealth(2048);
            villager.setHealth(2048);
            ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
            armorStand.setPassenger(villager);
            armorStand.setMarker(true);
            armorStand.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
            armorStand.setVisible(false);
            armorStand.setCustomName("Jerry");
            registerInventory(player.getServer());
        }
        return false;
    }
    public static void registerInventory(Server server) {
        jerry.clear();
        List<World> loadedWorlds = server.getWorlds();
        for (World world : loadedWorlds) {
            if (world.getName().contains("world")) {continue;}
            else if (world.getName().contains(".")) {continue;}
            else {
                ItemStack itemStack = new ItemStack(Material.FIREWORK_CHARGE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(world.getName());
                itemStack.setItemMeta(itemMeta);
                jerry.addItem(itemStack);
            }
        }
//        Player player = server.getPlayer("IamSorry_");
//        player.sendMessage("loaded");
//        player.sendMessage(String.valueOf(jerry));
    }



    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e) {
//        Arenas.getPlayer(e.getPlayer()).team.teamColors = TeamColors.NA;
//        Arenas.getPlayer(e.getPlayer()).team.chatColor = ChatColor.WHITE;
        e.getPlayer().setDisplayName((ChatColor.WHITE) + e.getPlayer().getDisplayName()+ ChatColor.WHITE + "");
        e.getPlayer().setPlayerListName((ChatColor.WHITE) + e.getPlayer().getPlayerListName().toString());
        if (e.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) e.getRightClicked();
            if (villager.getCustomName() != null && villager.getCustomName().equals("Jerry")) {
                e.setCancelled(true);
                villager.setHealth(2048);
                Player player = e.getPlayer();
                registerInventory(player.getServer());
                player.sendMessage("registered");
                e.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(bedwars.getMainInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.closeInventory();
//                        player.sendMessage("jerry");
//                player.closeInventory();
                        player.openInventory(jerry);
//                        player.sendMessage("Loaded inv");
                    }
                },1);

            }
        }

    }
    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getInventory().getName().equals("Worlds")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            p.getInventory().clear();
            p.setAllowFlight(false);
            JoinCommand.joinWorld((Player) e.getWhoClicked(), e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
        }
    }
}
