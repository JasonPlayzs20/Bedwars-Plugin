package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class BlockBreakEvent implements Listener {
    @EventHandler
    public static void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player p = e.getPlayer();
//        p.sendMessage(p.getWorld().getName());
        if (Objects.equals(p.getWorld().getName(), "world")) {
//            p.sendMessage("broken");
            e.setCancelled(true);
        }
        else {
            World world = p.getWorld();
            Block block = e.getBlock();
            GameManager gameManager = Arenas.getArena(world);
            if (!gameManager.blockList.contains(block)) {
                e.setCancelled(true);
                gameManager.blockList.remove(block);
            }
        }


    }


    @EventHandler
    public static void Fire(PlayerInteractEvent e) {
        if (!Objects.equals(e.getPlayer().getWorld().getName(), "world")) {
            return;
        }
        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            return;
        }
//        e.getPlayer().sendMessage("Fire");
        e.setCancelled(true);

    }
}
