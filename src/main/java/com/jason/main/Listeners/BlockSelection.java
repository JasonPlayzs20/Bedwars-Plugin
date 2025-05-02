package com.jason.main.Listeners;

import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import static com.jason.main.bedwars.setData;

public class BlockSelection implements Listener, CommandExecutor {
    static Location loc1;
    static Location loc2;
    private static BukkitTask jason;
    private final bedwars plugin;// reference to main class
    public BlockSelection(bedwars mainInstance) {
        this.plugin = bedwars.getMainInstance();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            String worldName = player.getWorld().getName();
            String locationName = args[0];

            setData("plugins/BedwarsInfo", worldName + ".yml", "blockBreakLimit." + locationName + ".x1", loc1.getX());
            setData("plugins/BedwarsInfo", worldName + ".yml", "blockBreakLimit." + locationName + ".y1", loc1.getY());
            setData("plugins/BedwarsInfo", worldName + ".yml", "blockBreakLimit." + locationName + ".z1", loc1.getZ());
            setData("plugins/BedwarsInfo", worldName + ".yml", "blockBreakLimit." + locationName + ".x2", loc2.getX());
            setData("plugins/BedwarsInfo", worldName + ".yml", "blockBreakLimit." + locationName + ".y2", loc2.getY());
            setData("plugins/BedwarsInfo", worldName + ".yml", "blockBreakLimit." + locationName + ".z2", loc2.getZ());
            player.sendMessage("Successfully selected from " + ChatColor.GREEN + loc1.toVector() + ChatColor.WHITE + " to " + ChatColor.RED + loc2.toVector() + ChatColor.WHITE + " as an no building limit");
            jason.cancel();
        }
        return false;
    }

    @EventHandler
    public static void leftClick(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInHand().getType() == Material.STICK) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                loc1 = e.getClickedBlock().getLocation();
            }
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInHand().getType() == Material.STICK) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                loc2 = e.getClickedBlock().getLocation();
                Player player = e.getPlayer();
                player.sendMessage("Selecting from " + ChatColor.GREEN + loc1.toVector() + ChatColor.WHITE + " to " + ChatColor.RED + loc2.toVector() + ChatColor.WHITE + " as an no building limit");



                    jason = Bukkit.getScheduler().runTaskTimer(plugin, (Runnable) () -> {
                        double distance = loc1.distance(loc2);
                        int points = (int) (distance * 2); // Adjust the density of particles based on distance

                        for (int i = 0; i <= points; i++) {
                            double ratio = (double) i / points;
                            double x = loc1.getX() + (loc2.getX() - loc1.getX()) * ratio;
                            double y = loc1.getY() + (loc2.getY() - loc1.getY()) * ratio;
                            double z = loc1.getZ() + (loc2.getZ() - loc1.getZ()) * ratio;


                            player.getWorld().spigot().playEffect(new Location(player.getWorld(), x, y, z), Effect.MOBSPAWNER_FLAMES, 1, 3, 0, 0, 0, 0, 1, 500);
//                            player.sendMessage("yesed");
                        }
                    },0, 5);

            }
        }
    }
}
