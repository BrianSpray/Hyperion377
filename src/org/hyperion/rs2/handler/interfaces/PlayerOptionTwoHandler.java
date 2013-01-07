package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface PlayerOptionTwoHandler {

	public abstract boolean handlePlayerOptionTwo(Player player,  Player other) throws Throwable;
}
