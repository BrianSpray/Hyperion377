package org.hyperion.rs2.packet;

import org.hyperion.rs2.handler.HandlerManager;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;

/**
 * Handles clicking on most buttons in the interface.
 * @author Graham Edgecombe
 *
 */
public class ButtonPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) throws Throwable {
		final int button = packet.getShort();
		
		if(HandlerManager.handleButton(player, button)) {
			return;
		}

	}

}
