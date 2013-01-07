package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface PlayerOptionFourHandler {

	public abstract boolean handlePlayerOptionFour(Player player, Player other) throws Throwable;
}
