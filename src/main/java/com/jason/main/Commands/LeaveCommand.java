package com.jason.main.Commands;

import com.jason.main.Emums.TeamColors;
import com.jason.main.GameDisplay.Arenas;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
//import com.jason.main.GameDisplay.GameManager.bedwarsPlayers;

import java.io.IOException;

import static com.jason.main.Commands.EndCommand.removeFile;
import static com.jason.main.bedwars.serverpath;

public class LeaveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
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




        }
        return false;
    }
//    public void setTeams() {
//        Player golbal = Bukkit.getPlayer("IamSOrry_");
//        for (Player player : players) {
//            player.sendMessage("you");
//            player.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));
//            TeamColors teamColors = bedwarsPlayers.get(player).team.teamColors;
//            if (teamColors == TeamColors.NA) {
//                player.sendMessage("NA");
//                for (TeamColors teamColors1 : TeamColors.values()) {
//                    golbal.sendMessage("Looping through colors: " + teamColors1);
//                    if (teamColors1 == TeamColors.NA) {continue;}
//                    else if (teamCount.get(teamColors1) < 1) {
//                        golbal.sendMessage("put into" + teamColors1);
//                        bedwarsPlayers.get(player).team.teamColors = teamColors1;
//                        bedwarsPlayers.get(player).team.chatColor = ChatColor.valueOf(teamColors1.name());
//                        teamCount.put(teamColors1,teamCount.get(teamColors1) + 1);
//                        golbal.sendMessage(String.valueOf(teamCount.get(teamColors1)));
//                        break;
//                    }
//                }
//            }
//        }
//    }
}
