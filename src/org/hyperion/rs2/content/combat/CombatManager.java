package org.hyperion.rs2.content.combat;

import org.hyperion.rs2.handler.LogicConstructorHandler;
import org.hyperion.rs2.handler.LogicType;
import org.hyperion.rs2.handler.impl.NpcOptionHandler;
import org.hyperion.rs2.handler.impl.PlayerOptionHandler;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;

public class CombatManager extends LogicConstructorHandler implements PlayerOptionHandler, NpcOptionHandler{

	public CombatManager() {
		super(LogicType.DEFAULT, 
				new Object[][] {
				{PlayerOptionHandler.class, NpcOptionHandler.class}
		});
	}

	@Override
	public boolean handleNpcOption(Player player, int packetId, int option, NPC npc) throws Throwable {
		return false;
	}

	@Override
	public boolean handlePlayerOption(Player player, int packetId, int index) throws Throwable {
		if (packetId == 245) {
			final Entity target = World.getWorld().getPlayers().get(index);
			if (target == null || player == null || player == target) {
				// remove interacting entity
				// remove face entity
				return false;
			}
			// Move to target
			// Engage Combat
		}
		return false;
	}

	@Override
	public Object getInstance() {
		return this;
	}

}
