package org.hyperion.rs2.packet;

import org.hyperion.rs2.handler.HandlerManager;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.ActionSender;
import org.hyperion.rs2.net.Packet;

public class ButtonPacketHandler implements PacketHandler {

	@Override
	public void handle(Player player, Packet packet) throws Throwable {
		final int buttonId = packet.getShort();
		if(HandlerManager.handleButton(player, packet.getOpcode(), buttonId)) {
			return;
		}
		if (player.getRights().equals(Player.Rights.ADMINISTRATOR)) {
			ActionSender.sendMessage(player, "ButtonId: " + buttonId);
		}
	}
}