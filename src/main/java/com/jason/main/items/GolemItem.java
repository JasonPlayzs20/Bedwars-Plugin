package com.jason.main.items;

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
        if (clickedBlock != null) {
            IronGolem golem = (IronGolem) event.getPlayer().getWorld().spawnEntity(clickedBlock.getLocation().add(0, 1, 0), EntityType.IRON_GOLEM);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (golem.isDead()) this.cancel();

                    golem.setCustomName(event.getPlayer().getDisplayName() + "'s Iron Golem" + ChatColor.RED + " ❤ " + (int) golem.getHealth());
                    golem.setCustomNameVisible(true);
                    golem.setTicksLived(1);

                    // TODO add target
                    golem.setTarget(null);
                }
            }.runTaskTimer(bedwars.getMainInstance(), 0, 1);
        }
    }
}
