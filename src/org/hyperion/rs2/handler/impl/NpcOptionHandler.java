package org.hyperion.rs2.handler.impl;

import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;

public abstract interface NpcOptionHandler {
	
	public abstract boolean handleNpcOption(Player player, int packetId, int option, NPC npc) throws Throwable;

}
