package org.hyperion.rs2.packet;

import org.hyperion.rs2.handler.HandlerManager;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.container.Bank;
import org.hyperion.rs2.model.container.Container;
import org.hyperion.rs2.model.container.Equipment;
import org.hyperion.rs2.model.container.Inventory;
import org.hyperion.rs2.net.Packet;

/**
 * Remove item options.
 * @author Graham Edgecombe
 *
 */
public class ItemOptionPacketHandler implements PacketHandler {
	
	/**
	 * Option 1 opcode.
	 */
	private static final int OPTION_1 = 3;
	
	/**
	 * Option 2 opcode.
	 */
	private static final int OPTION_2 = 177;
	
	/**
	 * Option 3 opcode.
	 */
	private static final int OPTION_3 = 91;
	
	/**
	 * Option 4 opcode.
	 */
	private static final int OPTION_4 = 231;
	
	/**
	 * Option 5 opcode.
	 */
	private static final int OPTION_5 = 158;
	
	/**
	 * Drop Item
	 */
	private static final int DROP_ITEM = 4;
	
	/**
	 * Pickup Ground Item
	 */
	private static final int TAKE_GROUND_ITEM = 71;
	
	/**
	 * Used for things such as "Light" logs.
	 */
	private static final int GROUND_ITEM_OPTION = 54;

	@Override
	public void handle(Player player, Packet packet) throws Throwable {
		switch(packet.getOpcode()) {
		case OPTION_1:
			handleItemOption1(player, packet);
			break;
		case OPTION_2:
			handleItemOption2(player, packet);
			break;
		case OPTION_3:
			handleItemOption3(player, packet);
			break;
		case OPTION_4:
			handleItemOption4(player, packet);
			break;
		case OPTION_5:
			handleItemOption5(player, packet);
			break;
		case TAKE_GROUND_ITEM:
			handleTakeGroundItem(player, packet);
			break;
		case GROUND_ITEM_OPTION:
			handleGroundItemOption(player, packet);
			break;
		case DROP_ITEM:
			handleDropItem(player, packet);
			break;
		}
	}

	/**
	 * Handles item option 1.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleItemOption1(Player player, Packet packet) {
		int id = packet.getShortA();
		int interfaceId = packet.getShort();
		int slot = packet.getShort();
		switch(interfaceId) {
		case Equipment.INTERFACE:
			if(slot >= 0 && slot < Equipment.SIZE) { 
				if(!Container.transfer(player.getEquipment(), player.getInventory(), slot, id)) {
					// indicate it failed
				}
			}
			break;
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id, 1);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, 1);
			}
			break;
		}
	}
	
	/**
	 * Handles item option 2.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleItemOption2(Player player, Packet packet) {
		int slot = packet.getShortA() & 0xFFFF;
		int id = packet.getLEShort() & 0xFFFF;
		int interfaceId = packet.getLEShort() & 0xFFFF;
		
		switch(interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id, 5);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, 5);
			}
			break;
		}
	}
	
	/**
	 * Handles item option 3.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleItemOption3(Player player, Packet packet) {
		int id = packet.getLEShort() & 0xFFFF;
		int slot = packet.getLEShortA() & 0xFFFF;
		int interfaceId = packet.getShort() & 0xFFFF;
		
		switch(interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id, 10);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, 10);
			}
			break;
		}
	}
	
	/**
	 * Handles item option 4.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleItemOption4(Player player, Packet packet) {
		int interfaceId = packet.getLEShortA() & 0xFFFF;
		int slot = packet.getLEShort() & 0xFFFF;
		int id = packet.getShort() & 0xFFFF;
		
		switch(interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Inventory.SIZE) {
				Bank.deposit(player, slot, id, player.getInventory().getCount(id));
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Bank.SIZE) {
				Bank.withdraw(player, slot, id, player.getBank().getCount(id));
			}
			break;
		}
	}
	
	/**
	 * Handles item option 5.
	 * @param player The player.
	 * @param packet The packet.
	 */
	private void handleItemOption5(Player player, Packet packet) {
		int slot = packet.getLEShortA() & 0xFFFF;
		int id = packet.getLEShortA() & 0xFFFF;
		int interfaceId = packet.getLEShort() & 0xFFFF;
		
		switch(interfaceId) {
		case Bank.PLAYER_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Inventory.SIZE) {
				player.getInterfaceState().openEnterAmountInterface(interfaceId, slot, id);
			}
			break;
		case Bank.BANK_INVENTORY_INTERFACE:
			if(slot >= 0 && slot < Bank.SIZE) {
				player.getInterfaceState().openEnterAmountInterface(interfaceId, slot, id);
			}
			break;
		}
	}

	/**
	 * Handles picking up an item.
	 * @param player
	 * @param packet
	 * @throws Throwable
	 */
	private void handleTakeGroundItem(Player player, Packet packet) throws Throwable {
		int itemId = packet.getLEShortA();
		int xCoord = packet.getByte();
		int yCoord = packet.getShortA();
		System.out.println("Item: " + itemId + " X: " + xCoord + " Y: " + yCoord + " Z: " + player.getLocation().getZ());
		//if(HandlerManager.handlePickup(player, itemId, Location.create(xCoord, yCoord, player.getLocation().getZ()))) {
		//	return;
		//}		

	}
	
	/**
	 * Handles the ground Item Option, e.g. 'Light' logs
	 * @param player
	 * @param packet
	 */
	private void handleGroundItemOption(Player player, Packet packet) {
		int groundItemId = packet.getShortA();
		int yCoord = packet.getLEShort();
		int xCoord = packet.getShort();
		System.out.println("Ground Item: " + groundItemId + " X: " + xCoord + " Y: " + yCoord);
	}
	
	/**
	 * Handles dropping an item
	 * @param player
	 * @param packet
	 * @throws Throwable
	 */
	private void handleDropItem(Player player, Packet packet) throws Throwable {
		int slotId = packet.getLEShort();
		int itemId = packet.getLEShortA();
		int interfaceId = packet.getLEShortA();
		System.out.println("Item: " + itemId + " Slot Id: " + slotId + " Interface Id: " + interfaceId);
		//if(HandlerManager.handleDrop(player, interfaceId, itemId, slotId)) {
		//	return;
		//}
	}
	
}
