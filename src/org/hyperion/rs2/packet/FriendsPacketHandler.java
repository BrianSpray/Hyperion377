package org.hyperion.rs2.packet;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;

public class FriendsPacketHandler implements PacketHandler {
	
	private static final int ADD_FRIEND = 120;
	
	private static final int REMOVE_FRIEND = 141;
	
	private static final int ADD_IGNORE = 217;
	
	private static final int REMOVE_IGNORE = 160;
	
	@Override
	public void handle(Player player, Packet packet) {
		switch(packet.getOpcode()) {
		case ADD_FRIEND:
			handleAddFriend(player, packet);
			break;
		case REMOVE_FRIEND:
			handleRemoveFriend(player, packet);
			break;
		case ADD_IGNORE:
			handleAddIgnore(player, packet);
			break;
		case REMOVE_IGNORE:
			handRemoveIgnore(player, packet);
			break;
		
		}
		
	}	

	private void handleAddFriend(Player player, Packet packet) {
		
	}
	
	private void handleRemoveFriend(Player player, Packet packet) {
		
	}
	
	private void handleAddIgnore(Player player, Packet packet) {
			
	}

	private void handRemoveIgnore(Player player, Packet packet) {
		
	}
	
}
