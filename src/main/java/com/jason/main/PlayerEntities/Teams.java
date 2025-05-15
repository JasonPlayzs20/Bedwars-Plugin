package com.jason.main.PlayerEntities;

import com.jason.main.Emums.DiamondUpgrade;
import com.jason.main.Emums.TeamColors;

import java.util.HashMap;

public class Teams {
    private HashMap<DiamondUpgrade, Integer> diamondUpgrades = new HashMap<>();
    public static TeamColors teamColors;
    public Teams(TeamColors teamColors) {
        for (int i = 0; i < DiamondUpgrade.values().length; i++) diamondUpgrades.put(DiamondUpgrade.values()[i], 0);

        this.teamColors = teamColors;
    }

    public HashMap<DiamondUpgrade, Integer> getDiamondUpgrades() {
        return diamondUpgrades;
    }
}
