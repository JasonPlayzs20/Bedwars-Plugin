package com.jason.main.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;



import java.util.Objects;

import static com.jason.main.bedwars.getData;

public class JoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!Objects.equals(args[0], "0")) {
            World tpWorld = Bukkit.getWorld(args[0]);
            Bukkit.createWorld(new WorldCreator("Airshow"));
            Bukkit.createWorld(new WorldCreator("Ashfire"));
            Bukkit.createWorld(new WorldCreator("Babylon"));
            Bukkit.createWorld(new WorldCreator("Cascade"));
            Bukkit.createWorld(new WorldCreator("Dragonstar"));
            Bukkit.createWorld(new WorldCreator("Gateway"));
            Bukkit.createWorld(new WorldCreator("Hollow"));
            Bukkit.createWorld(new WorldCreator("Lighthouse"));
            Bukkit.createWorld(new WorldCreator("Orchestra"));
            Bukkit.createWorld(new WorldCreator("Playground"));




            int yLevel = Integer.parseInt(getData("plugins/BedwarsInfo",args[0]+".yml", "lobbySpawn.y"));
            player.teleport(new Location(tpWorld, 0, yLevel+1, 0));
            return false;
        }
        else {
            player.sendMessage("Airshow \n" +
                    "apollo \n" +
                    "ashfire \n" +
                    "babylon \n" +
                    "cascade \n" +
                    "dragonstar\n" +
                    "dockyard NOT AVALIBLE \n" +
                    "gateway \n" +
                    "hollow \n" +
                    "lighthouse\n" +
                    "orchestra\n" +
                    "playground ");
        }
        return false;
    }
}
