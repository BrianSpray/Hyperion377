package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface IdleHandler {
	
	public abstract boolean handleIdle(Player player) throws Throwable;

}