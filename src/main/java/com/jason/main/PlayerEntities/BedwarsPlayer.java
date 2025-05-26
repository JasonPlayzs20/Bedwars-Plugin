package com.jason.main.PlayerEntities;

import com.jason.main.Emums.DiamondUpgrade;
import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jason.main.bedwars.getData;
import static com.jason.main.bedwars.serverpath;
import static com.jason.main.inventory.InventoryListener.SWORDS;

public class BedwarsPlayer {
    public static final double BASE_RADIUS = 20;

    public Teams team;
    public int kills = 0;
    public int finals = 0;
    Player player;
    GameManager gameManager;
    World world;
    public Location mainSpawn;
    public Location baseSpawn;
    String name;
    String teamName;
    public boolean hasBed = true;
    public Player lastHit = null;


    public BedwarsPlayer(Player player, GameManager gameManager, Teams teams) {
        this.player = player;
        this.gameManager = gameManager;
        this.team = teams;
        this.world = gameManager.world;

        name = gameManager.world.getName();

//        player.sendMessage(team.teamColors.name());
//        player.sendMessage(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", "lobbySpawn.y"));

    }

    public Player getPlayer() {
        return player;
    }

    public void start() {
        mainSpawn = new Location(world, 0, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", "lobbySpawn.y")) - 30, 0);
        baseSpawn = new Location(world, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.x")), Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.y")) + 0.5, Double.parseDouble(getData("plugins/BedwarsInfo", name.substring(1) + ".yml", team.teamColors.name().toString().substring(0, 1).toLowerCase() + ".p.z")));
//        player.sendMessage(String.valueOf(baseSpawn.getY()));
//        player.sendMessage(String.valueOf(baseSpawn.getX()));
        if (Math.abs(baseSpawn.getZ()) > Math.abs(baseSpawn.getX())) {
            if (baseSpawn.getZ() < 0) {
//                player.sendMessage("less than 0 Y");
//                baseSpawn.setDirection(new Vector(0, 0, 0));
            } else {
//                player.sendMessage("greater than 0 Y");
                baseSpawn.setDirection(new Vector(0, 0, -2500));
            }
        } else {
            if (baseSpawn.getX() < 0) {
//                player.sendMessage("less than 0 X");
                baseSpawn.setDirection(new Vector(90, 0, 0));
            } else {
//                player.sendMessage("greater than 0 X");
                baseSpawn.setDirection(new Vector(-90, 0, 0));
            }
        }

        player.teleport(baseSpawn);
    }

    public Location baseLoc() {
        player.sendMessage(String.valueOf(baseSpawn));
        return baseSpawn;
    }

    public Teams getTeam() {
        return team;
    }

    /**
     * Applies all diamond upgrades other than traps.
     */
    public void applyDiamondUpgrades() {
        Map<DiamondUpgrade, Integer> upgrades = team.getDiamondUpgrades();

        if (upgrades.get(DiamondUpgrade.PROTECTION) != 0) {
            PlayerInventory inv = player.getInventory();
            for (ItemStack itemStack : inv.getArmorContents())
                itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, upgrades.get(DiamondUpgrade.PROTECTION));
        }

        if (upgrades.get(DiamondUpgrade.SHARPNESS) != 0) {
            PlayerInventory inv = player.getInventory();
            for (ItemStack itemStack : inv.getContents()) {
                if (itemStack == null) continue;
                if (SWORDS.contains(itemStack.getType())) itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
        }

        if (upgrades.get(DiamondUpgrade.HASTE) != 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, upgrades.get(DiamondUpgrade.HASTE)));
        }

        if (upgrades.get(DiamondUpgrade.HEALPOOL) != 0) {
            double dist = baseSpawn.distance(player.getLocation());
            // heal pool with a radius of 20 blocks
            if (dist <= BASE_RADIUS)
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
            else player.removePotionEffect(PotionEffectType.REGENERATION);
        }

        int forge = upgrades.get(DiamondUpgrade.FORGE);
        if (forge == 1) { // +50% speed

        } else if (forge == 2) { // +100% speed

        } else if (forge == 3) { // spawn emeralds

        } else if (forge == 4) { // +200% speed

        }
    }

    /**
     * Checks if this player should trigger another team's trap.
     */
    public void applyTrapEffects() {
        List<Player> players = player.getWorld().getPlayers();

        for (Player other : players) {
            if (other.equals(player)) continue;

            BedwarsPlayer enemy = Arenas.getPlayer(other);
            if (enemy == null || enemy.getTeam().equals(this.getTeam())) continue;

            // out of range
            if (player.getLocation().distance(enemy.baseSpawn) > BASE_RADIUS) continue;

            Teams enemyTeam = enemy.getTeam();
            DiamondUpgrade enemyTrap = enemyTeam.getTrap();

            // end trap check after triggering a trap
            if (enemyTrap == DiamondUpgrade.MININGTRAP) {
                players.forEach(player1 -> {
                    if (gameManager.bedwarsPlayers.get(player1).team.teamColors == enemy.team.teamColors) {

                        player1.sendTitle(ChatColor.RED + "Your Mining Fatigue Trap",ChatColor.YELLOW +"Has Been Triggered");
                    }
                });
//                player.sendMessage("trapped");
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 8 * 20, 1));
                enemyTeam.getDiamondUpgrades().put(DiamondUpgrade.MININGTRAP, 0);
                return;
            } else if (enemyTrap == DiamondUpgrade.ITSATRAP) {
                enemyTeam.getDiamondUpgrades().put(DiamondUpgrade.ITSATRAP, 0);
                return;
            } else if (enemyTrap == DiamondUpgrade.COUNTERTRAP) {
                enemyTeam.getDiamondUpgrades().put(DiamondUpgrade.COUNTERTRAP, 0);
                return;
            } else if (enemyTrap == DiamondUpgrade.ALARMTRAP) {
                enemyTeam.getDiamondUpgrades().put(DiamondUpgrade.ALARMTRAP, 0);
                return;
            }
        }
    }
}
