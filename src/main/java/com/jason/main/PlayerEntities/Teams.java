package com.jason.main.PlayerEntities;

import com.jason.main.Emums.DiamondUpgrade;
import com.jason.main.Emums.TeamColors;

import java.util.HashMap;

public class Teams {
    private HashMap<DiamondUpgrade, Integer> diamondUpgrades = new HashMap<>();
    public static TeamColors teamColors;
    public Teams(TeamColors teamColors) {
        diamondUpgrades.put(DiamondUpgrade.SHARPNESS, 0);
        this.teamColors = teamColors;
    }
}
