package com.jason.main.GameDisplay;

import org.bukkit.Location;

public class Arena {
    State state;
    Location spawn;

    public Arena(State state, Location spawn) {
        this.state = State.RECRUITING;
        this.spawn = spawn;
    }


}
