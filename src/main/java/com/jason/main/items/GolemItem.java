package com.jason.main.items;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.PlayerEntities.BedwarsPlayer;
import com.jason.main.bedwars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class GolemItem extends BedwarsItem {
    public GolemItem() {
        super(Material.MONSTER_EGG, "Iron Golem");
        register(this);
    }

    @Override
    public void onUse(PlayerInteractEvent event) {
        event.setCancelled(true);

        Block clickedBlock = event.getClickedBlock();
        BedwarsPlayer bwPlayer = Arenas.getPlayer(event.getPlayer());
        if (bwPlayer != null && clickedBlock != null) {
            IronGolem golem = (IronGolem) event.getPlayer().getWorld().spawnEntity(clickedBlock.getLocation().add(0, 1, 0), EntityType.IRON_GOLEM);
            new BedwarsEntity(golem, "Iron Golem", bwPlayer).run();
        }
    }
}
