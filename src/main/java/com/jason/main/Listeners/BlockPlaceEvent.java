package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.annotation.Annotation;

public class BlockPlaceEvent implements Listener {
    @EventHandler
    public static void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        GameManager gameManager = Arenas.getArena(world);
        gameManager.blockList.add(event.getBlock());
    }

    @EventHandler
    public static void saturationEvent(PlayerMoveEvent event) {
        event.getPlayer().setSaturation(20);
    }

}
