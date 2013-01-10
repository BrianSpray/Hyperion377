package org.hyperion.rs2.packet;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;

public class MagicPacketHandler implements PacketHandler {
	
	private static final int MAGIC_ON_PLAYER = 31;
	
	private static final int MAGIC_ON_NPC = 104;
	
	private static final int MAGIC_ON_GROUND_ITEM = 83;
	
	private static final int MAGIC_ON_INVENTORY_ITEM = 36;
	
	@Override
	public void handle(Player player, Packet packet) throws Throwable {
		switch(packet.getOpcode()) {
			case MAGIC_ON_PLAYER:
				handleMagicOnPlayer(player, packet);
				break;
			case MAGIC_ON_NPC:
				handMagicOnNpc(player, packet);
				break;
			case MAGIC_ON_GROUND_ITEM:
				handleMagicOnGroundItem(player, packet);
				break;
			case MAGIC_ON_INVENTORY_ITEM:
				handleMagicOnInventoryItem(player, packet);
				break;
		}
	}	
	
	/**
	 * Handles the casting a spell on a Player.
	 * @param player
	 * @param packet
	 */
	private void handleMagicOnPlayer(Player player, Packet packet) {
		int playerIndex = packet.getShort();
		int spellId = packet.getLEShort();
		System.out.println("Player Index: " + playerIndex + " Spell Id: " + spellId);
		
	}
	
	/**
	 * Handles the casting of a spell on an NPC.
	 * @param player
	 * @param packet
	 */
	private void handMagicOnNpc(Player player, Packet packet) {
		int spellId = packet.getShortA();
		int npcIndex = packet.getLEShort();
		System.out.println("NPC Index: " + npcIndex + " Spell Id: " + spellId);
	}
	
	/**
	 * Handles casting a spell on a Ground Item. e.g. Telekenetic Grab
	 * @param player
	 * @param packet
	 */
	private void handleMagicOnGroundItem(Player player, Packet packet) {
		int groundItemId = packet.getLEShort();
		int yCoord = packet.getShort();
		int spellId = packet.getLEShort();
		int xCoord = packet.getLEShortA();
		System.out.println("Ground Item: " + groundItemId + " X: " + xCoord + " Y: " + yCoord + " Spell Id: " + spellId);
	}

	/**
	 * Handles casting a spell on an inventory item. e.g. Alchemy, and Enchantment spells
	 * @param player
	 * @param packet
	 */
	private void handleMagicOnInventoryItem(Player player, Packet packet) {
		int spellId = packet.getShort();
		int interfaceId = packet.getShortA();
		int slotId = packet.getShortA();
		int itemId = packet.getShortA();
		System.out.println("Item Id: " + itemId + " Slot Id: " + slotId + " Interface Id: " + interfaceId + " Spell Id: " + spellId);
	}	

}
