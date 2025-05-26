package com.jason.main.items;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.bedwars;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Wool;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

// Edge Brigg
public class BridgeEgg extends BedwarsItem {
    public BridgeEgg() {
        super(Material.EGG, "Bridge Egg");
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        // let the egg be thrown normally
    }

    public static void placeBridgeBlocks(Player spawner, Egg egg) {
        BedwarsPlayer bwPlayer = Arenas.getPlayer(spawner);
//        spawner.sendMessage(spawner.getDisplayName());
        if (bwPlayer == null) return;

        DyeColor blockColor = DyeColor.valueOf(bwPlayer.team.teamColors.name());
        Wool coloredWool = new Wool(Material.WOOL);
        coloredWool.setColor(blockColor);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (egg.isDead()) this.cancel();

                World w = spawner.getWorld();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        double x = egg.getLocation().getX();
                        double y = egg.getLocation().getY();
                        double z = egg.getLocation().getZ();

                        for (int dx = -1; dx <= 1; dx++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                double bx = x + dx;
                                double bz = z + dz;
                                Location loc = new Location(w, bx, y-2, bz);
                                Block block = w.getBlockAt(loc);
                                if (block.getType().equals(Material.AIR)){
//                                    block.setType(coloredWool.getItemType());
                                    block.setType(Material.WOOL);
                                    block.setData(coloredWool.getData());
                                    Arenas.getArena(spawner.getWorld()).blockList.add(block);
                                }
                            }
                        }
                    }
                }.runTaskLater(bedwars.getMainInstance(), 0);
            }
        }.runTaskTimer(bedwars.getMainInstance(), 0, 1);
    }
}
