package org.hyperion.rs2.handler.impl;

import org.hyperion.rs2.model.Player;

public abstract interface PlayerOptionHandler {
	
	public abstract boolean handlePlayerOption(Player player, int packetId, int option, Player other) throws Throwable;

}
