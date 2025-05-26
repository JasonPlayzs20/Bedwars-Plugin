package com.jason.main.Commands;

import com.jason.main.Emums.State;
import com.jason.main.GameDisplay.Arenas;
import com.jason.main.GameDisplay.GameManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class startCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        GameManager gameManager = Arenas.getArena(player.getWorld());
        gameManager.getCountdown().start();
        gameManager.state = State.COUNTDOWN;
        return false;
    }
}
