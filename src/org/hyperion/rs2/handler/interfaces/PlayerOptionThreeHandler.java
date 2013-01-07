package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface PlayerOptionThreeHandler {

	public abstract boolean handlePlayerOptionThree(Player player, Player other) throws Throwable;
}