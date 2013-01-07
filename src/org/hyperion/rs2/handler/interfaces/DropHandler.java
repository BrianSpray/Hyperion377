package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public interface DropHandler {
	
	public boolean handleDrop(Player player, int interfaceId, int itemId, int slot) throws Throwable;
	
}
