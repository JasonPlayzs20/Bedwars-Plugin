package com.jason.main;


import com.jason.main.Commands.*;
import com.jason.main.Commands.addCommands.Bases.*;
import com.jason.main.Commands.addCommands.Others.addDiamondGen;
import com.jason.main.Commands.addCommands.Others.addEmeraldGen;
import com.jason.main.Commands.addCommands.ScanCommand;
import com.jason.main.Listeners.*;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.invmenu.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

public final class bedwars extends JavaPlugin {
    private static bedwars mainInstance;
    public static String serverpath;


    public static bedwars getMainInstance() {
        return mainInstance;
    }
    @Override
    public void onEnable() {

        System.out.println(getData("plugins/BedwarsInfo", "Airshow.yml", "red.spawn.x"));
        setData("plugins/BedwarsInfo", "Airshow.yml", "red.spawn.x", -40);
        System.out.println(getData("plugins/BedwarsInfo", "Airshow.yml", "red.spawn.x"));
        mainInstance = this;
        serverpath = getData("plugins/BedwarsInfo", "serverpath.yml", "path");
//        LobbyJerry.registerInventory(this.getServer());


        // Plugin startup logic
        this.getCommand("join").setExecutor(new JoinCommand());
        this.getCommand("addGen").setExecutor(new addGen());
        this.getCommand("addShop").setExecutor(new addShop());
        this.getCommand("addShopDiamond").setExecutor(new addShopDiamond());
        this.getCommand("addSpawnColor").setExecutor(new addSpawnColor());
        this.getCommand("addSpawnTotal").setExecutor(new addSpawnTotal());
        this.getCommand("addDiamondGen").setExecutor(new addDiamondGen());
        this.getCommand("addEmeraldGen").setExecutor(new addEmeraldGen());
        this.getCommand("addArea").setExecutor(new BlockSelection(mainInstance));
        this.getCommand("start").setExecutor(new startCommand());
        this.getCommand("end").setExecutor(new EndCommand());
        this.getCommand("scan").setExecutor(new ScanCommand());
        this.getCommand("npc").setExecutor(new NpcCommand());

        Bukkit.getPluginManager().registerEvents(new BlockSelection(mainInstance), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileListener(), this);
//        Bukkit.getPlayer("IamSorry_").sendMessage(getDataFolder().getParentFile().getAbsolutePath());
//        System.out.println(getDataFolder().getParentFile().getAbsolutePath());

        this.getCommand("jerry").setExecutor(new LobbyJerry());
        this.getCommand("l").setExecutor(new LeaveCommand());

        Bukkit.getPluginManager().registerEvents(new BlockSelection(mainInstance), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFakeDeathEvent(), this);
//        Bukkit.getPlayer("IamSorry_").sendMessage(getDataFolder().getParentFile().getAbsolutePath());
//        System.out.println(getDataFolder().getParentFile().getAbsolutePath());
        Bukkit.getPluginManager().registerEvents(new LobbyJerry(), this);


//        Bukkit.createWorld(new WorldCreator("Airshow"));
//        Bukkit.createWorld(new WorldCreator("Ashfire"));
//        Bukkit.createWorld(new WorldCreator("Babylon"));
//        Bukkit.createWorld(new WorldCreator("Cascade"));
//        Bukkit.createWorld(new WorldCreator("Dragonstar"));
//        Bukkit.createWorld(new WorldCreator("Gateway"));
//        Bukkit.createWorld(new WorldCreator("Hollow"));
//        Bukkit.createWorld(new WorldCreator("Lighthouse"));
//        Bukkit.createWorld(new WorldCreator("Orchestra"));
//        Bukkit.createWorld(new WorldCreator("Playground"));
        Bukkit.createWorld(new WorldCreator("Fang"));

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static String getData(String dataFolder, String fileName, String path)  {
        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            System.out.println("FILE NOT FOUND");
            try {
                System.out.println("Creating a new file");
                file.createNewFile();
//                FileWriter writer = new FileWriter(file.getName());
//                writer.write(sample.sample.toString()   );
            }catch (IOException ignored) {

            }
        }
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        String ans = null;
        if (modifyFile.contains(path)) {
            ans = modifyFile.getString(path);
        }
        return ans;

    }

    public static void setData(String dataFolder, String fileName, String path, double value) {
        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            System.out.println("FILE NOT FOUND,CREATING A NEW ONE");
            try {
                file.createNewFile();
                YamlConfiguration modifyFilein = YamlConfiguration.loadConfiguration(file);
                modifyFilein.load(fileName);
                modifyFilein.save(dataFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        modifyFile.set(path, Optional.of(value));
        try {
            modifyFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDataINT(String dataFolder, String fileName, String path, int value) {
        File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            System.out.println("FILE NOT FOUND,CREATING A NEW ONE");
            try {
                file.createNewFile();
                YamlConfiguration modifyFilein = YamlConfiguration.loadConfiguration(file);
                modifyFilein.load("airshow.yml");
                modifyFilein.save(dataFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(file);
        modifyFile.set(path, Optional.of(value));
        try {
            modifyFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
