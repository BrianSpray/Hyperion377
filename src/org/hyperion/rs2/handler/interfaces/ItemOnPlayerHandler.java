package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface ItemOnPlayerHandler {

	public abstract boolean handleItemOnPlayer(Player player, Player other, int itemId, int interfaceId, int slot) throws Throwable;
	
}
