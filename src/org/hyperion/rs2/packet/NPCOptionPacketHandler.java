package org.hyperion.rs2.packet;

import org.hyperion.rs2.Constants;
import org.hyperion.rs2.action.impl.AttackAction;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.net.Packet;

public class NPCOptionPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) {
		switch(packet.getOpcode()) {
		case 67:
			/*
			 * Option 1.
			 */
			option1(player, packet);
			break;
		}
	}

	private void option1(Player player, Packet packet) {
		int id = packet.getShortA() & 0xFFFF;
		/*if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}*/
		System.out.println(id);
		NPC victim = (NPC) World.getWorld().getNPCs().get(id);
		if(victim != null && player.getLocation().isWithinInteractionDistance(victim.getLocation())) {
			player.getActionQueue().addAction(new AttackAction(player, victim));
		}
	}
}
