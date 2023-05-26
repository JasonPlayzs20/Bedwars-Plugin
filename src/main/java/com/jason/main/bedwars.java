package com.jason.main;

import com.jason.main.Commands.JoinCommand;
import com.jason.main.Commands.addCommands.Bases.*;
import com.jason.main.Commands.addCommands.Others.addDiamondGen;
import com.jason.main.Commands.addCommands.Others.addEmeraldGen;
import com.jason.main.Commands.startCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.IOException;

public final class bedwars extends JavaPlugin {
    private static bedwars mainInstance;

    @Override
    public void onEnable() {

        System.out.println(getData("plugins/BedwarsInfo", "Airshow.yml", "red.spawn.x"));
        mainInstance = this;

        // Plugin startup logic
        this.getCommand("join").setExecutor(new JoinCommand());
        this.getCommand("addGen").setExecutor(new addGen());
        this.getCommand("addShop").setExecutor(new addShop());
        this.getCommand("addShopDiamond").setExecutor(new addShopDiamond());
        this.getCommand("addSpawnColor").setExecutor(new addSpawnColor());
        this.getCommand("addSpawnTotal").setExecutor(new addSpawnTotal());
        this.getCommand("addDiamondGen").setExecutor(new addDiamondGen());
        this.getCommand("addEmeraldGen").setExecutor(new addEmeraldGen());
        this.getCommand("start").setExecutor(new startCommand());
        System.out.println(getDataFolder().toString());
    }

    public static bedwars getMainInstance() {
        return mainInstance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static String getData(String dataFolder, String fileName, String path) {
        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            System.out.println("FILE NOT FOUND");
        }
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        try {
            return modifyFile.get(path).toString();
        }catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setData(String dataFolder, String fileName, String path, String value) {
        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            System.out.println("FILE NOT FOUND,CREATING A NEW ONE");
            try {
                file.createNewFile();
                YamlConfiguration modifyFilein = YamlConfiguration.loadConfiguration(file);
                modifyFilein.load("Airshow.yml");
                modifyFilein.save(dataFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        modifyFile.set(path, value);
        try {
            modifyFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
