package com.jason.main.Commands;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.GameDisplay.State;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.util.Vector;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import static com.jason.main.bedwars.getData;
import static com.jason.main.bedwars.serverpath;

public class JoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("yes");
        if (Objects.equals(args[0], "a")) {
            Location tpLoc = new Location(Bukkit.getWorld("fang"), 0, 100, 0, 3, 0);
            Vector vector = player.getEyeLocation().getDirection().setY(90);
            tpLoc.setDirection(vector);

            player.teleport(tpLoc);
            player.getEyeLocation().setYaw((float) 90);
            return true;
        }
        if (Objects.equals(args[0], "world")) {
            player.teleport(new Location(Bukkit.getWorld("world"), -41, 73, 0));
        } else if (!Objects.equals(args[0], "0")) {


            String nameWorld = args[0];

//            player.sendMessage(String.valueOf(Bukkit.getWorld(nameWorld)));
            if (Bukkit.getWorld(nameWorld) == null) {
                player.sendMessage("kirk");
            }
            Path sourceFolderPath = Paths.get("/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/" + nameWorld);
            Path newFolderPath = Paths.get("/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/", ("." + nameWorld));
            if (!Files.exists(newFolderPath)) {
                player.sendMessage("need to dupew");
                try {
                    Files.createDirectory(newFolderPath);
                    System.out.println("Folder created successfully: ");
                } catch (FileAlreadyExistsException e) {
                    System.err.println("Folder already exists: ");
                } catch (IOException e) {
                    System.err.println("Error creating folder: " + e.getMessage());
                }
                Path destinationFolderPath = Paths.get("/Users/jason/Desktop/coding_stuffes/spigot_servers/SpigotServer1.8/" + "." + nameWorld);

                try {
                    Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if (!file.getFileName().toString().equals("uid.dat")) {
                                Path relativePath = sourceFolderPath.relativize(file);
                                Path destinationFile = destinationFolderPath.resolve(relativePath);
                                Files.copy(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            Path relativePath = sourceFolderPath.relativize(dir);
                            Path destinationDir = destinationFolderPath.resolve(relativePath);
                            Files.createDirectories(destinationDir);
                            return FileVisitResult.CONTINUE;
                        }
                    });

                    System.out.println("Folder copied successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new WorldCreator("." + nameWorld).createWorld();

            }
//            new WorldCreator("."+nameWorld).createWorld();
            World tpWorld = Bukkit.getWorld("." + nameWorld);
//            tpWorld.setAutoSave(false);
//            tpWorld.setMonsterSpawnLimit(1);
//            tpWorld.setDifficulty(Difficulty.EASY);
//            tpWorld.setAnimalSpawnLimit(0);
//            tpWorld.setAmbientSpawnLimit(0);
//            tpWorld.setPVP(true);

            int yLevel = 190;
            try {
                yLevel = Integer.parseInt(getData("plugins/BedwarsInfo", args[0] + ".yml", "lobbySpawn.y"));
            } catch (NullPointerException e) {
                yLevel = 100;
            }

            player.teleport(new Location(tpWorld, 0, yLevel + 1, 0));
            player.sendMessage(player.getWorld().getName());

            return false;
        } else {
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


    public static void joinWorld(Player player, String nameWorld) {



//            player.sendMessage(String.valueOf(Bukkit.getWorld(nameWorld)));
        if (Bukkit.getWorld(nameWorld) == null) {
            player.sendMessage("kirk");
        }
        Path sourceFolderPath = Paths.get(serverpath + nameWorld);
        Path newFolderPath = Paths.get(serverpath, ("." + nameWorld));
        if (!Files.exists(newFolderPath)) {
            player.sendMessage("need to dupew");
            try {
                Files.createDirectory(newFolderPath);
                System.out.println("Folder created successfully: ");

            } catch (FileAlreadyExistsException e) {
                System.err.println("Folder already exists: ");
            } catch (IOException e) {
                System.err.println("Error creating folder: " + e.getMessage());
            }
            Path destinationFolderPath = Paths.get(serverpath + "." + nameWorld);

            try {
                Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (!file.getFileName().toString().equals("uid.dat")) {
                            Path relativePath = sourceFolderPath.relativize(file);
                            Path destinationFile = destinationFolderPath.resolve(relativePath);
                            Files.copy(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path relativePath = sourceFolderPath.relativize(dir);
                        Path destinationDir = destinationFolderPath.resolve(relativePath);
                        Files.createDirectories(destinationDir);
                        return FileVisitResult.CONTINUE;
                    }
                });

                System.out.println("Folder copied successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            new WorldCreator("." + nameWorld).createWorld();
            World tpWorld = Bukkit.getWorld("." + nameWorld);
            if (!(Arenas.getArenas().containsKey(tpWorld))) {
                Arenas.addArena(tpWorld,new GameManager(tpWorld,player));
            }
        }
        World tpWorld = Bukkit.getWorld("." + nameWorld);
//            new WorldCreator("."+nameWorld).createWorld();

//            tpWorld.setAutoSave(false);
//            tpWorld.setMonsterSpawnLimit(1);
//            tpWorld.setDifficulty(Difficulty.EASY);
//            tpWorld.setAnimalSpawnLimit(0);
//            tpWorld.setAmbientSpawnLimit(0);
//            tpWorld.setPVP(true);

        int yLevel = 190;
        try {
            yLevel = Integer.parseInt(getData("plugins/BedwarsInfo", nameWorld + ".yml", "lobbySpawn.y"));
        } catch (NullPointerException e) {
            yLevel = 100;
        }
        if (Arenas.getArena(tpWorld).getState() == State.RECRUITING) {
            player.teleport(new Location(tpWorld, 0, yLevel + 1, 0));
            player.sendMessage(player.getWorld().getName());
            Arenas.getArena(tpWorld).startArena();
        }

    }


}
