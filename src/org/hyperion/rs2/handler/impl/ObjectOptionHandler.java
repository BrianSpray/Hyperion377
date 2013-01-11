package org.hyperion.rs2.handler.impl;

import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;



public abstract interface ObjectOptionHandler {
	
	public abstract boolean handleObjectOption(Player player, int packetId, int option, RSObject object, GameObjectDefinition definition) throws Throwable;

}
