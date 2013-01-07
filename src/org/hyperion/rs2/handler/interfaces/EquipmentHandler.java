package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface EquipmentHandler {
	
	public abstract boolean handleEquipment(Player player, int interfaceId, int slot, int itemId) throws Throwable;

}