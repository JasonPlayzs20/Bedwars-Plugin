package com.jason.main.Listeners;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.bedwars;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.annotation.Annotation;

import static com.jason.main.bedwars.getData;

public class BlockPlaceEvent implements Listener {
    @EventHandler
    public static void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        player.sendMessage(String.valueOf(getData(bedwars.serverpath, world.getName().substring(1),"blockBreakLimit")) + " UGIOHSNDYGUIOISHBGU*IOJKHUV*S)(PIDJKLVHUO*IKLJNBDSIUHOI");

        GameManager gameManager = Arenas.getArena(world);
        gameManager.blockList.add(event.getBlock());

    }

    @EventHandler
    public static void saturationEvent(PlayerMoveEvent event) {
//        event.getPlayer().sendMessage("saturation");
        event.getPlayer().setSaturation(25);
        event.getPlayer().setFoodLevel(20);
        event.getPlayer().getWorld().setTime(1000);
        event.getPlayer().getWorld().setStorm(false);
    }

}
