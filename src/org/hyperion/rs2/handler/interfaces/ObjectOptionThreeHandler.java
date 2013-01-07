package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;

public abstract interface ObjectOptionThreeHandler {
	
	public abstract boolean handleObjectOptionThree(Player player, RSObject object, GameObjectDefinition definition) throws Throwable;

}