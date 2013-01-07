package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Player;

public abstract interface CommandHandler {
	
	public abstract boolean handleCommand(Player player, String command) throws Throwable;

}