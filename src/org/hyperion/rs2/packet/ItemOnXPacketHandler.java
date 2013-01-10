package org.hyperion.rs2.packet;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.Packet;

public class ItemOnXPacketHandler implements PacketHandler {
	
	private static final int ITEM_ON_INVENTORY_ITEM = 1;
	
	private static final int ITEM_ON_NPC = 57;
	
	private static final int ITEM_ON_PLAYER = 143;
	
	private static final int ITEM_ON_OBJECT = 152;
	
	private static final int ITEM_ON_GROUND_ITEM = 211;	

	@Override
	public void handle(Player player, Packet packet) throws Throwable {
		switch(packet.getOpcode()) {
			case ITEM_ON_INVENTORY_ITEM: 
				handItemOnInventoryItem(player, packet);
				break;			
			case ITEM_ON_NPC:
				handleItemOnNpc(player, packet);
				break;
			case ITEM_ON_PLAYER:
				handleItemOnPlayer(player, packet);
				break;
			case ITEM_ON_OBJECT:
				handleItemOnObject(player, packet);
				break;
			case ITEM_ON_GROUND_ITEM:
				handleItemOnGroundItem(player, packet);
				break;
		}
	}


	/**
	 * Handles an item used on a ground item from the player's inventory.
	 * @param player
	 * @param packet
	 */
	private void handleItemOnGroundItem(Player player, Packet packet) {
		int slotId = packet.getLEShortA();
		int itemId = packet.getShortA();
		int yCoord = packet.getLEShortA();
		int xCoord = packet.getLEShortA();
		int interfaceId = packet.getLEShort();
		int groundItemId = packet.getLEShort();
		System.out.println("Item Id: " + itemId + " Interface Id: " + interfaceId + 
				" X: " + xCoord +  " Y: " + yCoord + " Slot Id: " + slotId + " Ground Item: " + groundItemId );		
	}

	/**
	 * Handles an item used on an object from the player's inventory.
	 * @param player
	 * @param packet
	 */
	private void handleItemOnObject(Player player, Packet packet) {
		int objectId = packet.getLEShort();
		int interfaceId = packet.getLEShort();
		int itemId = packet.getLEShort();
		int yCoord = packet.getLEShort();
		int slotId = packet.getShort();
		int xCoord = packet.getLEShortA();
		System.out.println("Object Id: " + objectId + " X: " + xCoord + " Y: " + yCoord + " Interface Id: " + interfaceId + " Slot Id: " + slotId + " Item Id: " + itemId);
	}

	/**
	 * Handles an item used on a player from the player's inventory.
	 * @param player
	 * @param packet
	 */
	private void handleItemOnPlayer(Player player, Packet packet) {
		int interfaceId = packet.getLEShort();
		int playerIndex = packet.getLEShortA();
		int itemId = packet.getShort();
		int slotId = packet.getShortA();
		System.out.println("Item Id: " +itemId + " Slot Id: " + slotId + " Player Index: " + playerIndex + " Interface Id: " + interfaceId);
		
	}

	/**
	 * Handles an item used on a NPC from the player's inventory.
	 * @param player
	 * @param packet
	 */
	private void handleItemOnNpc(Player player, Packet packet) {
		int slotId = packet.getShort();
		int itemId = packet.getLEShort();
		int interfaceId = packet.getLEShortA();
		int npcIndex = packet.getShort();
		System.out.println("Item Id: " + itemId + " Slot Id: " + slotId + " Npc Index: " + npcIndex + " InterfaceId: " + interfaceId);
		
	}

	/**
	 * Handles an item used on an item within the player's inventory. 
	 * @param player
	 * @param packet
	 */
	private void handItemOnInventoryItem(Player player, Packet packet) {
		int usedWithItemId = packet.getShort();
		int usedSlotId = packet.getLEShort();
		int usedItemId = packet.getLEShort();
		int interfaceId2 = packet.getLEShortA();
		int usedWithSlotId = packet.getShortA();
		int interfaceId = packet.getShortA();
		System.out.println("Used With Item Id: " + usedWithItemId + " Used With Slot: " + usedSlotId + " Used Item Id: " + usedItemId + " Interface Id 2: " + interfaceId2 + " Slot Id: " + usedWithSlotId + " Interface Id: " + interfaceId);		

	}

}
