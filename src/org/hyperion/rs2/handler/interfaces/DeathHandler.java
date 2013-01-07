package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Entity;

public abstract interface DeathHandler {
	
	public abstract boolean handleDead(Entity killer, Entity dead) throws Throwable;
	
}