package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Player;

public interface PickupHandler {
	
	public boolean handlePickUp(Player player, int id, Location location) throws Throwable;
	
}