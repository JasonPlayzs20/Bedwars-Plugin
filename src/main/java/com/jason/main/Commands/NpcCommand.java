package com.jason.main.Commands;

import com.jason.main.invmenu.shops.QuickBuyShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NpcCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            QuickBuyShop shop = new QuickBuyShop((Player) commandSender);
            shop.open();
        }
        return false;
    }
}
