package org.hyperion.rs2.handler.impl;

import org.hyperion.rs2.model.Player;


public abstract interface LoginHandler {
	
	public abstract boolean handleLogin(Player player) throws Throwable;

}
