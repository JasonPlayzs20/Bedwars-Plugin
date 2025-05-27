package com.jason.main.Commands;

import com.jason.main.Emums.TeamColors;
import com.jason.main.GameDisplay.Arenas;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static com.jason.main.bedwars.serverpath;


public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        World world = ((Player) sender).getWorld();
        if (!((Player)sender).isOp()) return false;
        world.getPlayers().forEach(player -> {
            TeamColors teamColors = Arenas.getArena(world).bedwarsPlayers.get(player).team.teamColors;
            Arenas.getArena(world).teamCount.put(Arenas.getArena(world).bedwarsPlayers.get(player).team.teamColors, Arenas.getArena(world).teamCount.get(Arenas.getArena(world).bedwarsPlayers.get(player).team.teamColors)-1);
            Arenas.getArena(world).teamCount.put(teamColors, Arenas.getArena(world).teamCount.get(teamColors) - 1 );
            player.teleport(new Location(Bukkit.getWorld("world"), -41, 73, 0).setDirection(new Vector(90,0,0)));
//            try {
//                removeFile(serverpath, world.getName());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            Arenas.getArena(world).startArena();
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.setAllowFlight(true);
            player.getEnderChest().clear();

        });
        try {
            removeFile(serverpath + ".", args[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean removeFile(String path, String args) throws IOException {

        if (Objects.equals(args, "world")) {
            return true;
        }
        if (Files.exists(Paths.get(path + args))) {
            Bukkit.getServer().unloadWorld("." + args, false);
            deleteDirectory(Paths.get(serverpath + "." + args).toFile());
//            FileUtils.deleteDirectory(Paths.get("/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/." + args[0]).toFile());

        } else {
            System.out.println("World not exist");
            return false;
        }
        return false;
    }
    static public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

}
