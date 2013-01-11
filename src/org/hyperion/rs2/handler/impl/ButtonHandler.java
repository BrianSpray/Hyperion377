package org.hyperion.rs2.handler.impl;

import org.hyperion.rs2.model.Player;

public abstract interface ButtonHandler {
	
    public abstract boolean handleButton(Player player, int packetId, int buttonId) throws Throwable;

}
