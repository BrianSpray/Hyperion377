package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;

public abstract interface ItemOnNpcHandler {

	public abstract boolean handleItemOnNpc(Player player,  NPC other, int itemId, int slot, int interfaceId) throws Throwable;
	
}
