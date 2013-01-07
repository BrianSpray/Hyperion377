package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public interface ItemOptionSixHandler {

	public abstract boolean handleItemOptionSix(Player player, int interfaceId, int slot, int itemId) throws Throwable;
	
}
