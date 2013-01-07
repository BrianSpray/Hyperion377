package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;

public abstract interface ItemOnObjectHandler {

	public abstract boolean handleItemOnObject(Player player, int interfaceId, int itemId, int slot, RSObject object, GameObjectDefinition definition) throws Throwable;
	
}
