package com.jason.main.Commands;

import org.bukkit.Bukkit;
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


public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        try {
            removeFile(player, "/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/.", args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean removeFile(Player player, String path, String[] args) throws IOException {

        if (Objects.equals(args[0], "world")) {
            return true;
        }
        if (Files.exists(Paths.get(path + args[0]))) {
            Bukkit.getServer().unloadWorld("." + args[0], false);
            player.sendMessage("Unloaded");
            FileUtils.deleteDirectory(Paths.get("/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/." + args[0]).toFile());
            player.sendMessage("Deleted");

        } else {
            player.sendMessage("World not exist");
            return false;
        }
        return false;
    }
}
