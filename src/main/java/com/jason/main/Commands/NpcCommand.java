package com.jason.main.Commands;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.inventory.shops.diamond.DiamondShop;
import com.jason.main.inventory.shops.quick.QuickBuyShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NpcCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && strings.length >= 1) {
            if (strings[0].equals("d")) {
                GameManager bwInstance = Arenas.getArenas().get(((Player) commandSender).getWorld());
                if (bwInstance != null) {
                    DiamondShop shop = new DiamondShop((Player) commandSender, bwInstance);
                    shop.open();
                }
            } else if (strings[0].equals("s")) {
                QuickBuyShop shop = new QuickBuyShop((Player) commandSender);
                shop.open();
            }
        }
        return false;
    }
}
