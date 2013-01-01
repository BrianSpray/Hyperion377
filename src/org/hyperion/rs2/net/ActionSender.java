package org.hyperion.rs2.net;

import java.awt.Color;

import org.hyperion.rs2.Constants;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.NPCDefinition;
import org.hyperion.rs2.model.Palette;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.Palette.PaletteTile;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.container.Equipment;
import org.hyperion.rs2.model.container.Inventory;
import org.hyperion.rs2.model.container.impl.EquipmentContainerListener;
import org.hyperion.rs2.model.container.impl.InterfaceContainerListener;
import org.hyperion.rs2.model.container.impl.WeaponContainerListener;
import org.hyperion.rs2.net.Packet.Type;

/**
 * A utility class for sending packets.
 * @author Graham Edgecombe
 *
 */
public class ActionSender {
	
	/**
	 * The player.
	 */
	private Player player;
	
	/**
	 * Creates an action sender for the specified player.
	 * @param player The player to create the action sender for.
	 */
	public ActionSender(Player player) {
		this.player = player;
	}
	
	/**
	 * Sends an inventory interface.
	 * @param interfaceId The interface id.
	 * @param inventoryInterfaceId The inventory interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceInventory(int interfaceId, int inventoryInterfaceId) {
		player.getInterfaceState().interfaceOpened(interfaceId);
		player.write(new PacketBuilder(128).putShortA(interfaceId).putLEShortA(inventoryInterfaceId).toPacket());
		return this;
	}
	
	/**
	 * Sends all the login packets.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendLogin() {
		player.setActive(true);
		sendDetails();
		sendMessage("Welcome to RuneScape.");
		
		sendRunEnergy();
		
		sendFriendServer(2); 
		//sendFriendStatus(1); // 1 is World1, -45 is Classic1
		
		//sendSynchronizeConfigs();
		
		sendMapRegion();
		
		NPC npc = new NPC(NPCDefinition.forId(1));
		npc.setLocation(Location.create(3222, 3223, 0));
		
		sendSidebarInterfaces();
		
		//sendWelcomeScreen();

		sendWalkableInterface(197);
		int wildernessLevel = 1 + (player.getLocation().getY() - 3520) / 8;
		sendString(199, "Level: " + wildernessLevel);		
		sendMultiWayIcon(1);
		
		sendTextColor(7332, Color.GREEN);
				
		sendSetInterfaceHidden(12323, false);
		sendConfig(300, 100 * 10);
		sendConfig(301, 0);

		sendSetInterfaceHidden(4240, true);
		Item[] whips = {new Item(1673), new Item(1675), new Item(1677) ,new Item(1679) ,new Item(1681) , new Item(1683), new Item(6579)};
		sendUpdateItems(4245, whips);


		
		sendInteractionOption("Follow", 2, true);
		sendInteractionOption("Trade with", 3, true);
		
		InterfaceContainerListener inventoryListener = new InterfaceContainerListener(player, Inventory.INTERFACE);
		player.getInventory().addListener(inventoryListener);
		
		InterfaceContainerListener equipmentListener = new InterfaceContainerListener(player, Equipment.INTERFACE);
		player.getEquipment().addListener(equipmentListener);
		player.getEquipment().addListener(new EquipmentContainerListener(player));
		player.getEquipment().addListener(new WeaponContainerListener(player));
		
		return this;
	}
	
	/**
	 * Sends the packet to construct a map region.
	 * @param palette The palette of map regions.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendConstructMapRegion(Palette palette) {
		player.setLastKnownRegion(player.getLocation());
		PacketBuilder bldr = new PacketBuilder(53, Type.VARIABLE_SHORT);
		bldr.putShortA(player.getLocation().getRegionX() + 6);
		bldr.startBitAccess();
		for(int z = 0; z < 4; z++) {
			for(int x = 0; x < 13; x++) {
				for(int y = 0; y < 13; y++) {
					PaletteTile tile = palette.getTile(x, y, z);
					bldr.putBits(1, tile != null ? 1 : 0);
					if(tile != null) {
						bldr.putBits(26, tile.getX() << 14 | tile.getY() << 3 | tile.getZ() << 24 | tile.getRotation() << 1);
					}
				}
			}
		}
		bldr.finishBitAccess();
		bldr.putShortA(player.getLocation().getRegionY() + 6);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends the initial login packet (e.g. members, player id).
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendDetails() {
		player.write(new PacketBuilder(126).put((byte) (player.isMembers() ? 1 : 0)).putLEShort(player.getIndex()).toPacket());
		player.write(new PacketBuilder(148).toPacket());
		return this;
	}
	
	/**
	 * Sends the player's skills.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSkills() {
		for(int i = 0; i < Skills.SKILL_COUNT; i++) {
			sendSkill(i);
		}
		return this;
	}
	
	/**
	 * Sends a specific skill.
	 * @param skill The skill to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSkill(int skill) {
		PacketBuilder bldr = new PacketBuilder(49);
		bldr.putByteC((byte) skill);
		bldr.put((byte) player.getSkills().getLevel(skill));
		bldr.putInt((int) player.getSkills().getExperience(skill));
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender sendSidebarIconFlash(int sideBar) {
		player.getSession().write(new PacketBuilder(238).put((byte) sideBar).toPacket());
		return this;
	}
	/**
	 * Sends all the sidebar interfaces.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSidebarInterfaces() {
		final int[] icons = Constants.SIDEBAR_INTERFACES[0];
		final int[] interfaces = Constants.SIDEBAR_INTERFACES[1];
		for(int i = 0; i < icons.length; i++) {
			sendSidebarInterface(icons[i], interfaces[i]);
		}
		return this;
	}
	
	/**
	 * Sends a specific sidebar interface.
	 * @param icon The sidebar icon.
	 * @param interfaceId The interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSidebarInterface(int icon, int interfaceId) {
		player.write(new PacketBuilder(10).putByteS((byte) icon).putShortA(interfaceId).toPacket());
		return this;
	}
	
	public ActionSender sendInterface(int interfaceId) {
	    PacketBuilder packet = new PacketBuilder(159);
	    packet.putLEShortA(interfaceId);
	    player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendChatBoxInterface(int interfaceId) {
	    PacketBuilder packet = new PacketBuilder(109);
	    packet.putShort(interfaceId);
	    player.write(packet.toPacket());
		return this;
	}	
	
	/**
	 * Does not close when walking, or opening another interface
	 * For Tutorial Island, I'm assuming.
	 */
	public ActionSender sendDialogueInterface(int interfaceId) {
		player.getSession().write(new PacketBuilder(158).putLEShort(interfaceId).toPacket());
		return this;
	}	
	
	public ActionSender sendPlayerHead(int interfaceId) {
		PacketBuilder packet = new PacketBuilder(255);
		packet.putLEShortA(interfaceId);
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendNpcHead(int interfaceId, int npcId) {
		PacketBuilder packet = new PacketBuilder(162);
		packet.putShortA(npcId);
		packet.putLEShort(interfaceId);
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendInterfaceAnimation(int interfaceId, int animationId) {
		PacketBuilder packet = new PacketBuilder(2);
		packet.putLEShortA(interfaceId);
		packet.putShortA(animationId);
		player.write(packet.toPacket());
		return this;
	}
	
	/*
	 * Wildy Icon top left = 196
	 * Wildy Icon Correct place = 197
	 * Duel Arena Icon = 201
	 */
	public ActionSender sendWalkableInterface(int interfaceId) {
		player.getSession().write(new PacketBuilder(50).putShort(interfaceId).toPacket());
		return this;
	}
	
	public ActionSender sendFullScreenInterface(int interfaceId, int interfaceId2) {
		player.getSession().write(new PacketBuilder(253).putLEShort(interfaceId).putShortA(interfaceId2).toPacket());
		return this;
	}
	
	public ActionSender sendCloseInterfaces() {
	    player.write(new PacketBuilder(29).toPacket());
	    return this;
	}
	
	public ActionSender sendSetInterfaceHidden(int id, boolean value) {
		PacketBuilder bldr = new PacketBuilder(82);
		bldr.put((byte)(value ? 1 : 0));
		bldr.putShort(id);
		player.getSession().write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends a message.
	 * @param message The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendMessage(String message) {
		player.write(new PacketBuilder(63, Type.VARIABLE).putRS2String(message).toPacket());
		return this;
	}
	
	/**
	 * Sends the map region load command.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendMapRegion() {
		player.setLastKnownRegion(player.getLocation());
		player.write(new PacketBuilder(222).putShort(player.getLocation().getRegionY() + 6).putLEShortA(player.getLocation().getRegionX() + 6).toPacket());
		return this;
	}
	
	/**
	 * Sends the logout packet.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendLogout() {
		player.write(new PacketBuilder(5).toPacket()); // TODO IoFuture
		return this;
	}
	

	/**
	 * Sends a packet to update a group of items.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param items
	 *            The items.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItems(int interfaceId, Item[] items) {
		PacketBuilder bldr = new PacketBuilder(206, Type.VARIABLE_SHORT);
		bldr.putShort(interfaceId);
		bldr.putShort(items.length);
		for (Item item : items) {
			if (item != null) {
				bldr.putLEShortA(item.getId() + 1);
				int count = item.getCount();
				if (count > 254) {
					bldr.putByteC((byte) 255);
					bldr.putLEInt(count);
				} else {
					bldr.putByteC((byte) count);
				}
			} else {
				bldr.putLEShortA(0);
				bldr.putByteC((byte) 0);
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender sendUpdateItem(int interfaceId, int slot, Item item) {
		PacketBuilder bldr = new PacketBuilder(134, Type.VARIABLE_SHORT);
		bldr.putShort(interfaceId).putSmart(slot);
		if (item != null) {
			bldr.putShort(item.getId() + 1);
			int count = item.getCount();
			if (count > 254) {
				bldr.put((byte) 255);
				bldr.putInt(count);
			} else {
				bldr.put((byte) count);
			}
		} else {
			bldr.putShort(0);
			bldr.put((byte) 0);
		}
		player.write(bldr.toPacket());
		return this;
	}
	
	public ActionSender sendUpdateItems(int interfaceId, int[] slots,
			Item[] items) {
		PacketBuilder bldr = new PacketBuilder(134, Type.VARIABLE_SHORT)
				.putShort(interfaceId);
		for (int slot : slots) {
			Item item = items[slot];
			bldr.putSmart(slot);
			if (item != null) {
				bldr.putShort(item.getId() + 1);
				int count = item.getCount();
				if (count > 254) {
					bldr.put((byte) 255);
					bldr.putInt(count);
				} else {
					bldr.put((byte) count);
				}
			} else {
				bldr.putShort(0);
				bldr.put((byte) 0);
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends the enter amount interface.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendEnterAmountInterface() {
		player.write(new PacketBuilder(58).toPacket());
		return this;
	}
	
	/**
	 * Sends the player an option.
	 * @param slot The slot to place the option in the menu.
	 * @param top Flag which indicates the item should be placed at the top.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInteractionOption(String option, int slot, boolean top) {
		PacketBuilder bldr = new PacketBuilder(157, Type.VARIABLE);
		bldr.putByteC((byte) slot);
		bldr.putRS2String(option);
		bldr.put(top ? (byte) 0 : (byte) 1);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a string.
	 * @param id The interface id.
	 * @param string The string.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendString(int id, String string) {
		PacketBuilder bldr = new PacketBuilder(232, Type.VARIABLE_SHORT);
		bldr.putLEShortA(id);
		bldr.putRS2String(string);
		player.write(bldr.toPacket());
		return this;
	}
	
	/**
	 * Sends a model in an interface.
	 * @param id The interface id.
	 * @param zoom The zoom.
	 * @param model The model id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceModel(int id, int zoom, int model) {
		PacketBuilder bldr = new PacketBuilder(21);
		bldr.putShort(zoom).putLEShort(model).putLEShortA(id);
		player.write(bldr.toPacket());
		return this;
	}
	
	public ActionSender sendTextColor(int childId, Color colour) {
	      int r = (colour.getRed() >> 3) & 0x1F;
	      int g = (colour.getGreen() >> 3) & 0x1F;
	      int b = (colour.getBlue() >> 3) & 0x1F;
	      PacketBuilder packet = new PacketBuilder(218);
	      packet.putShort(childId);   
	      packet.putShortA((r << 10) | (g << 5) | b);
	      player.write(packet.toPacket());
	      return this;
	}
	
	public ActionSender sendFriendServer(int status) {
	    PacketBuilder packet = new PacketBuilder(251);
	    packet.put((byte) status);
	    player.write(packet.toPacket());
	    return this;
	}
	
	public ActionSender sendFriendStatus(long name, int worldId) {
		PacketBuilder packet = new PacketBuilder(78);
		if (worldId != 0) {
			worldId += 9;
		}
		packet.putLong(name);
		packet.put((byte) (worldId));
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendPrivateMessage(long name, int rights, byte[] message, int messageSize) {
		PacketBuilder packet = new PacketBuilder(135);
		packet.putLong(name);
		//packet.putLEInt(player.getPrivateMessage().getLastMessageIndex());
		packet.put((byte) rights);
		packet.put(message, 0, messageSize);
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendRunEnergy() {
		player.getSession().write(new PacketBuilder(125).put((byte) player.getRunEnergy()).toPacket());
		return this;
	}
	
	/**
	* Sends a system update to all online players.
	* @param time is the amount of time it takes to
	*  count down to zero. Remember that time is the
	*  percentage of the minute. This means that if
	*  <code>time</code> is 50, then it counts down
	*  from 0:30 because 30 seconds is 50% of a minute.
	*  If <code>time</code> is 200, then it will count
	*  down from 2:00. Get it? Good!
	*/
	public ActionSender sendSystemUpdate(int time) {
		player.getSession().write(new PacketBuilder(190).putLEShort(time).toPacket());
		return this;
	}
	
	public ActionSender sendMiniMapState(int state) {
		player.getSession().write(new PacketBuilder(156).put((byte) state).toPacket());
		return this;
	}	

	public ActionSender sendMultiWayIcon(int state) {
	    player.write(new PacketBuilder(233).put((byte) state).toPacket());
	    return this;
	}
	
	public ActionSender sendWelcomeScreen() {
		PacketBuilder packet = new PacketBuilder(76);
		packet.putLEShort(0);
		packet.putLEShortA(0);
		packet.putShort(0);
		packet.putShort(0);
		packet.putLEShort(1337); // Days Ago
		packet.putShortA(2); // Messages
		packet.putShortA(0);
		packet.putShort(0);
		packet.putLEInt(0);
		packet.putLEShort(0);
		packet.putByteS((byte) 0);
		sendFullScreenInterface(5993, 15244);
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendGroundItem(int itemId, int offset, int itemAmount) {
		PacketBuilder packet = new PacketBuilder(106);
		packet.putShort(itemId);
		packet.putByteC(offset);
		packet.putShortA(itemAmount);
		player.write(packet.toPacket());
		return this;
	}
	
	/**
	 * Sends the Current placement in an 8x8 region
	 * @return
	 */
	public ActionSender sendSetCurrentPlacement(int placementX, int placementY) {
		PacketBuilder packet = new PacketBuilder(75);
		packet.putByteC(placementX);
		packet.putByteA(placementY);
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendConfig(int id, int value) {
		if (value < 255) {
			sendConfig1(id, value);
		} else {
			sendConfig2(id, value);
		}
		return this;
	}
	
	public ActionSender sendConfig1(int id, int value) {
		System.out.println("config 1 value = "+value);
		PacketBuilder bldr = new PacketBuilder(182);
		bldr.putShortA(id);
		bldr.putByteS((byte) value);
		player.getSession().write(bldr.toPacket());
		return this;
	}
	
	
	public ActionSender sendConfig2(int settingId, int settingState) {
		PacketBuilder packet = new PacketBuilder(115);
		packet.putInt2(settingState);
		packet.putLEShort(settingId);
		player.write(packet.toPacket());
		return this;
	}
	
	public ActionSender sendSynchronizeConfigs() {
		player.write(new PacketBuilder(113).toPacket()); 
		return this;
	}
	
}
