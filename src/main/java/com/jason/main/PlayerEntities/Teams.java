package com.jason.main.PlayerEntities;

import com.jason.main.Emums.DiamondUpgrade;
import com.jason.main.Emums.TeamColors;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import java.util.HashMap;

public class Teams {
    private HashMap<DiamondUpgrade, Integer> diamondUpgrades;
    public Teams(TeamColors teamColors) {
        diamondUpgrades.put(DiamondUpgrade.SHARPNESS, 0);
    }
}
