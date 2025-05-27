package com.jason.main.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.jason.main.bedwars.serverpath;

public class ShutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) return false;
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
