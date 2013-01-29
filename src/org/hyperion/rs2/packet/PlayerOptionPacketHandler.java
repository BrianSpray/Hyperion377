package org.hyperion.rs2.packet;

import org.hyperion.rs2.Constants;
import org.hyperion.rs2.handler.HandlerManager;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;

public class PlayerOptionPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) throws Throwable {
		switch(packet.getOpcode()) {
		case 245:
			/*
			 * Option 1.
			 */
			option1(player, packet);
			break;
		case 233:
			/*
			 * Option 2.
			 */
			option2(player,  packet);
			break;
		case 194:
			/*
			 * Option 3.
			 */
			option3(player, packet);
			break;
		case 116:
			/*
			 * Option 4.
			 */
			option4(player, packet);
			break;
		case  45:
			/*
			 * Option 5.
			 */
			option5(player, packet);
			break;
		}
	}

	/**
	 * Handles the first option on a player option menu.
	 * @param player
	 * @param packet
	 * @throws Throwable 
	 */
	private void option1(final Player player, Packet packet) throws Throwable {
		int id = packet.getLEShortA() & 0xFFFF;
		if(id < 0 || id >= Constants.MAX_PLAYERS) {
			return;
		}
		if (HandlerManager.handlePlayerOption(player, packet.getOpcode(), id)) {
			return;
		}
	}
	
	/**
	 * Handles the second option on a player option menu.
	 * @param player
	 * @param packet
	 */
	private void option2(Player player, Packet packet) {
		int id = packet.getShort() & 0xFFFF;
		if(id < 0 || id >= Constants.MAX_PLAYERS) {
			return;
		}
	}
	
	/**
	 * Handles the third option on a player option menu.
	 * @param player
	 * @param packet
	 */
	private void option3(Player player, Packet packet) {
		int id = packet.getLEShort() & 0xFFFF;
		if(id < 0 || id >= Constants.MAX_PLAYERS) {
			return;
		}
	}
	

	private void option4(Player player, Packet packet) {
		int playerIndex = packet.getLEShort()  & 0xFFFF;
		if(playerIndex < 0 || playerIndex >= Constants.MAX_PLAYERS) {
			return;
		}
		
	}
	

	private void option5(Player player, Packet packet) {
		int playerIndex = packet.getShortA() & 0xFFFF;
		if(playerIndex < 0 || playerIndex >= Constants.MAX_PLAYERS) {
			return;
		}
		
	}
		
}

