package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface PlayerOptionOneHandler {

	public abstract boolean handlePlayerOptionOne(Player player,  Player other) throws Throwable;
}
