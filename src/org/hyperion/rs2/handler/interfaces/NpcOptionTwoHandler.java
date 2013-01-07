package org.hyperion.rs2.handler.interfaces;

import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;

public abstract interface NpcOptionTwoHandler {

	public abstract boolean handleNpcOptionTwo(Player player, NPC npc) throws Throwable;
	
}
