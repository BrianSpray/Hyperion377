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
			
		case 112:
			/*
			 * Option 2.
			 */
			option2(player, packet);
			break;
		case 13:
			/*
			 * Option 3.
			 */
			option3(player, packet);
			break;
		case 42:
			/*
			 * Option 4.
			 */
			option4(player, packet);
			break;
		case 8:
			/*
			 * Option5. 
			 */
			option5(player, packet);
			break;
		}
	}

	private void option1(Player player, Packet packet) {
		int npcIndex = packet.getShortA() & 0xFFFF;
		/*if(id < 0 || id >= Constants.MAX_NPCS) {
			return;
		}*/
		System.out.println(npcIndex);
		NPC victim = (NPC) World.getWorld().getNPCs().get(npcIndex);
		if(victim != null && player.getLocation().isWithinInteractionDistance(victim.getLocation())) {
			player.getActionQueue().addAction(new AttackAction(player, victim));
		}
	}
	

	private void option2(Player player, Packet packet) {
		int npcIndex = packet.getLEShort() & 0xFFFF;
		System.out.println(npcIndex);
		
	}
	
	private void option3(Player player, Packet packet) {
		int npcIndex = packet.getLEShortA() & 0xFFFF;
		System.out.println(npcIndex);
	}
	

	private void option4(Player player, Packet packet) {
		int npcIndex = packet.getLEShort() & 0xFFFF;
		System.out.println(npcIndex);
	}
	

	private void option5(Player player, Packet packet) {
		int npcIndex = packet.getLEShort() & 0xFFFF;
		System.out.println(npcIndex);		
	}
}
