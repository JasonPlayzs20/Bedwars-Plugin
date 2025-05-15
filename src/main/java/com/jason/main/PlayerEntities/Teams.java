package com.jason.main.PlayerEntities;

import com.jason.main.Emums.DiamondUpgrade;
import com.jason.main.Emums.TeamColors;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class Teams {
    private HashMap<DiamondUpgrade, Integer> diamondUpgrades = new HashMap<>();
    public TeamColors teamColors;
    public ChatColor chatColor;
    public Teams(TeamColors teamColors, ChatColor chatColor) {
        for (int i = 0; i < DiamondUpgrade.values().length; i++) diamondUpgrades.put(DiamondUpgrade.values()[i], 0);
        this.teamColors = teamColors;
        this.chatColor = chatColor;
    }

    public HashMap<DiamondUpgrade, Integer> getDiamondUpgrades() {
        return diamondUpgrades;
    }
}
