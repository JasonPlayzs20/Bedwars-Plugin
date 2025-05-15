package com.jason.main.Commands;

import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import com.jason.main.invmenu.shops.diamond.DiamondShop;
import com.jason.main.invmenu.shops.quick.QuickBuyShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NpcCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            GameManager bwInstance = Arenas.getArenas().get(((Player) commandSender).getWorld());
            if (bwInstance != null) {
//                DiamondShop shop = new DiamondShop((Player) commandSender, bwInstance);
//                shop.open();
                QuickBuyShop shop = new QuickBuyShop(((Player) commandSender).getPlayer());
                shop.open();
            }
        }
        return false;
    }
}
