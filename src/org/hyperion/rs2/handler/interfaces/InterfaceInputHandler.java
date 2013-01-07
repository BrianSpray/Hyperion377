package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface InterfaceInputHandler {
	
	public abstract boolean handleInterfaceInput(Player player, int interfaceId, int id, int child) throws Throwable;

}
