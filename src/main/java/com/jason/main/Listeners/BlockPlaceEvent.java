package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.bedwars;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.jason.main.bedwars.getData;

public class BlockPlaceEvent implements Listener {

    private static final Map<UUID, Boolean> fastBridgeMode = new HashMap<>();
    private static final Map<UUID, Long> lastToggleTime = new HashMap<>();

    @EventHandler
    public static void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Block block = event.getBlockPlaced();
        Location blockLoc = block.getLocation();
        if (blockLoc.getY() > Integer.parseInt(getData("plugins/BedwarsInfo", world.getName().substring(1) + ".yml", "lobbySpawn.y"))-40) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot build here!!");
        }

        String folder = "plugins/BedwarsInfo";
        String file = world.getName().substring(1) + ".yml";

        for (int i = 1; i < 12; i++) {
            String baseKey = "blockBreakLimit." + i;
//            player.sendMessage(world.getName());

            // Get one coordinate to test if the region exists
            String x1Str = getData(folder, file, baseKey + ".x1");
            if (x1Str == null) continue;

            // Load all values once
            double x1 = Double.parseDouble(x1Str);
            double y1 = Double.parseDouble(getData(folder, file, baseKey + ".y1"));
            double z1 = Double.parseDouble(getData(folder, file, baseKey + ".z1"));

            double x2 = Double.parseDouble(getData(folder, file, baseKey + ".x2"));
            double y2 = Double.parseDouble(getData(folder, file, baseKey + ".y2"));
            double z2 = Double.parseDouble(getData(folder, file, baseKey + ".z2"));

            // Get bounding box
            double minX = Math.min(x1, x2), maxX = Math.max(x1, x2);
            double minY = Math.min(y1, y2), maxY = Math.max(y1, y2);
            double minZ = Math.min(z1, z2), maxZ = Math.max(z1, z2);

            // Check if block is inside the region
            double bx = blockLoc.getX();
            double by = blockLoc.getY();
            double bz = blockLoc.getZ();

            if (bx >= minX && bx <= maxX &&
                    by >= minY && by <= maxY &&
                    bz >= minZ && bz <= maxZ) {

                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot place blocks in this restricted region.");
                return;
            }

        }
        if (block.getType().equals(Material.WOOL)) {
            if (!fastBridgeMode.getOrDefault(player.getUniqueId(), false)) return;
//            player.sendMessage("Wooled");
            Block placed = event.getBlockPlaced();
            Block against = event.getBlockAgainst();
            BlockFace face = getPlacedFace(placed, against);
            if (face == null) return;
//            player.sendMessage("not null");
            if (face == BlockFace.UP || face == BlockFace.DOWN) {
//                event.setCancelled(true);
//                player.sendMessage(ChatColor.RED + "You cannot place wool on the top or bottom of blocks while speed bridging.");

            }
            else {
                Block current = placed;

                for (int g = 1; g <= 5; g++) {
//                    player.sendMessage("Loopwed");
                    current = current.getRelative(face);
                    Block below = current.getRelative(BlockFace.DOWN);


                    if (below.getType() != Material.AIR) {
//                        player.sendMessage("!Air");
                        break;
                    }

                    current.setType(Material.WOOL);
                    current.setData(placed.getData()); // Use original wool color
                    Arenas.getArena(player.getWorld()).blockList.add(current);
//                    player.sendMessage("Placed at: " + current.getLocation().toVector().toString());
                }
            }
        }

        GameManager gameManager = Arenas.getArena(world);
        gameManager.blockList.add(block);
    }
    private static BlockFace getPlacedFace(Block against, Block placed) {
        for (BlockFace face : BlockFace.values()) {
            if (against.getRelative(face).equals(placed)) {
                return face.getOppositeFace(); // get the face the player clicked
            }
        }
        return null;
    }

    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Bukkit.getLogger().info("[FastBridge] Player interacted: " + player.getName());

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Bukkit.getLogger().info("[FastBridge] Detected left click");

            ItemStack item = player.getInventory().getItemInHand();
            if (item != null && item.getType() == Material.WOOL) {
                Bukkit.getLogger().info("[FastBridge] Player is holding wool");

                UUID uuid = player.getUniqueId();
                long currentTime = System.currentTimeMillis();
                long lastTime = lastToggleTime.getOrDefault(uuid, 0L);
                if (currentTime - lastTime < 500) {
                    Bukkit.getLogger().info("[FastBridge] Toggle blocked by cooldown");
                    return;
                }
                lastToggleTime.put(uuid, currentTime);

                boolean current = fastBridgeMode.getOrDefault(uuid, false);
                fastBridgeMode.put(uuid, !current);
                Bukkit.getLogger().info("[FastBridge] FastBridge toggled to: " + !current);
                player.sendMessage(ChatColor.YELLOW + "Fast Bridge Mode: " + (current ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled"));
            } else {
                Bukkit.getLogger().info("[FastBridge] Player is not holding wool");
            }
        }
    }

    @EventHandler
    public static void saturationEvent(PlayerMoveEvent event) {
//        event.getPlayer().sendMessage("saturation");
        event.getPlayer().setSaturation(25);
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().getWorld().setTime(1000);
        event.getPlayer().getWorld().setStorm(false);
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false));
        if (event.getPlayer().getLocation().getBlockY() <= 20) {
            Player playerDied = event.getPlayer();
            if (playerDied.getWorld().getName().equals("world")) {
                playerDied.teleport(new Location(Bukkit.getWorld("world"), -41, 73, 0).setDirection(new Vector(90,0,0)));
                return;
            }
//            playerDied.sendMessage("Died");

            playerDied.setGameMode(GameMode.SPECTATOR);
            playerDied.setHealth(20);
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).team));
            playerDied.getWorld().getPlayers().forEach(player -> {player.sendMessage(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).team.chatColor + playerDied.getName().toString() + ChatColor.RED + " has died in the void.");});
//            playerDied.sendMessage(String.valueOf(playerDied.getWorld()));
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld())));
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName()))));
//            playerDied.sendMessage(String.valueOf(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn));
            playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(Bukkit.getPlayer(playerDied.getName())).mainSpawn);
            playerDied.getInventory().clear();
            playerDied.getInventory().setItem(0,new ItemStack(Material.WOOD_SWORD));
            if (!Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).hasBed) {
                playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Your bed is broken, you will not respawn."));
                return;
            }

            new BukkitRunnable() {
                int timer = 5;

                @Override
                public void run() {
                    if (timer == 0) {
                        cancel();
                        return;
                    }
                    playerDied.sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + ("Respawning in : " + timer));
                    timer--;
                }
            }.runTaskTimerAsynchronously(bedwars.getMainInstance(), 0, 20);

            new BukkitRunnable() {

                @Override
                public void run() {
                    playerDied.setHealth(20);
                    playerDied.setGameMode(GameMode.SURVIVAL);
                    playerDied.teleport(Arenas.getArena(playerDied.getWorld()).bedwarsPlayers.get(playerDied).baseSpawn);
                    playerDied.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20, 189, false,false));
                }
            }.runTaskLater(bedwars.getMainInstance(), 5 * 20);

        }
    }

}
