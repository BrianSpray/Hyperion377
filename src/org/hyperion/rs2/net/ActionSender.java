package org.hyperion.rs2.net;

import java.awt.Color;

import org.hyperion.rs2.Constants;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Palette;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.Palette.PaletteTile;
import org.hyperion.rs2.net.Packet.Type;

/**
 * A utility class for sending packets.
 * @author Graham Edgecombe
 *
 */
public class ActionSender {
	
	
	/**
	 * Sends the packet to construct a map region.
	 * @param palette The palette of map regions.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendConstructMapRegion(Player player, Palette palette) {
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
	}

	/**
	 * Sends the initial login packet (e.g. members, player id).
	 * @return The action sender instance, for chaining.
	 */
	public static void sendDetails(Player player) {
		player.write(new PacketBuilder(126).put((byte) (player.isMembers() ? 1 : 0)).putLEShort(player.getIndex()).toPacket());
		player.write(new PacketBuilder(148).toPacket());
	}
	
	/**
	 * Sends the player's skills.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendSkills(Player player) {
		for(int i = 0; i < Skills.SKILL_COUNT; i++) {
			sendSkill(player, i);
		}
	}
	
	/**
	 * Sends a specific skill.
	 * @param skill The skill to send.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendSkill(Player player, int skill) {
		PacketBuilder bldr = new PacketBuilder(49);
		bldr.putByteC((byte) skill);
		bldr.put((byte)  player.getSkills().getLevelForExperience(skill));
		bldr.putInt((int) player.getSkills().getExperience(skill));
		player.write(bldr.toPacket());
	}

	public static void sendSidebarIconFlash(Player player, int sideBar) {
		player.getSession().write(new PacketBuilder(238).put((byte) sideBar).toPacket());
	}
	/**
	 * Sends all the sidebar interfaces.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendSidebarInterfaces(Player player) {
		final int[] icons = Constants.SIDEBAR_INTERFACES[0];
		final int[] interfaces = Constants.SIDEBAR_INTERFACES[1];
		for(int i = 0; i < icons.length; i++) {
			sendSidebarInterface(player, icons[i], interfaces[i]);
		}
	}
	
	/**
	 * Sends a specific sidebar interface.
	 * @param icon The sidebar icon.
	 * @param interfaceId The interface id.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendSidebarInterface(Player player, int icon, int interfaceId) {
		player.write(new PacketBuilder(10).putByteS((byte) icon).putShortA(interfaceId).toPacket());
	}
	
	public static void sendInterface(Player player, int interfaceId) {
	    PacketBuilder packet = new PacketBuilder(159);
	    packet.putLEShortA(interfaceId);
	    player.write(packet.toPacket());
	}
	
	public static void sendChatBoxInterface(Player player, int interfaceId) {
	    PacketBuilder packet = new PacketBuilder(109);
	    packet.putShort(interfaceId);
	    player.write(packet.toPacket());
	}		
	
	/**
	 * Sends an inventory interface.
	 * @param interfaceId The interface id.
	 * @param inventoryInterfaceId The inventory interface id.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendInterfaceInventory(Player player, int interfaceId, int inventoryInterfaceId) {
		player.getInterfaceState().interfaceOpened(interfaceId);
		player.write(new PacketBuilder(128).putShortA(interfaceId).putLEShortA(inventoryInterfaceId).toPacket());
	}
	
	/**
	 * Does not close when walking, or opening another interface
	 * For Tutorial Island, I'm assuming.
	 */
	public static void sendDialogueInterface(Player player, int interfaceId) {
		player.getSession().write(new PacketBuilder(158).putLEShort(interfaceId).toPacket());
	}	
	
	public static void sendPlayerHead(Player player, int interfaceId) {
		PacketBuilder packet = new PacketBuilder(255);
		packet.putLEShortA(interfaceId);
		player.write(packet.toPacket());
	}
	
	public static void sendNpcHead(Player player, int interfaceId, int npcId) {
		PacketBuilder packet = new PacketBuilder(162);
		packet.putShortA(npcId);
		packet.putLEShort(interfaceId);
		player.write(packet.toPacket());
	}
	
	public static void sendInterfaceAnimation(Player player, int interfaceId, int animationId) {
		PacketBuilder packet = new PacketBuilder(2);
		packet.putLEShortA(interfaceId);
		packet.putShortA(animationId);
		player.write(packet.toPacket());
	}
	
	/*
	 * Wildy Icon top left = 196
	 * Wildy Icon Correct place = 197
	 * Duel Arena Icon = 201
	 */
	public static void sendWalkableInterface(Player player, int interfaceId) {
		player.getSession().write(new PacketBuilder(50).putShort(interfaceId).toPacket());
	}
	
	public static void sendFullScreenInterface(Player player, int interfaceId, int interfaceId2) {
		player.getSession().write(new PacketBuilder(253).putLEShort(interfaceId).putShortA(interfaceId2).toPacket());
	}
	
	public static void sendCloseInterfaces(Player player) {
	    player.write(new PacketBuilder(29).toPacket());
	}
	
	public static void sendInterfaceConfig(Player player, int id, boolean value) {
		PacketBuilder bldr = new PacketBuilder(82);
		bldr.put((byte)(value ? 1 : 0));
		bldr.putShort(id);
		player.getSession().write(bldr.toPacket());
	}
	
	/**
	 * Sends a message.
	 * @param message The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendMessage(Player player, String message) {
		player.write(new PacketBuilder(63, Type.VARIABLE).putRS2String(message).toPacket());
	}
	
	/**
	 * Sends the map region load command.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendMapRegion(Player player) {
		player.setLastKnownRegion(player.getLocation());
		player.write(new PacketBuilder(222).putShort(player.getLocation().getRegionY() + 6).putLEShortA(player.getLocation().getRegionX() + 6).toPacket());
	}
	
	/**
	 * Sends the logout packet.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendLogout(Player player) {
		player.write(new PacketBuilder(5).toPacket()); // TODO IoFuture
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
	public static void sendUpdateItems(Player player, int interfaceId, Item[] items) {
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
	}

	public static void sendUpdateItem(Player player, int interfaceId, int slot, Item item) {
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
	}
	
	public static void sendUpdateItems(Player player, int interfaceId, int[] slots,
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
	}

	/**
	 * Sends the enter amount interface.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendEnterAmountInterface(Player player) {
		player.write(new PacketBuilder(58).toPacket());
	}
	
	/**
	 * Sends the player an option.
	 * @param slot The slot to place the option in the menu.
	 * @param top Flag which indicates the item should be placed at the top.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendInteractionOption(Player player, String option, int slot, boolean top) {
		PacketBuilder bldr = new PacketBuilder(157, Type.VARIABLE);
		bldr.putByteC((byte) slot);
		bldr.putRS2String(option);
		bldr.put(top ? (byte) 0 : (byte) 1);
		player.write(bldr.toPacket());
	}

	/**
	 * Sends a string.
	 * @param id The interface id.
	 * @param string The string.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendString(Player player, int id, String string) {
		PacketBuilder bldr = new PacketBuilder(232, Type.VARIABLE_SHORT);
		bldr.putLEShortA(id);
		bldr.putRS2String(string);
		player.write(bldr.toPacket());
	}
	
	/**
	 * Sends a model in an interface.
	 * @param id The interface id.
	 * @param zoom The zoom.
	 * @param model The model id.
	 * @return The action sender instance, for chaining.
	 */
	public static void sendInterfaceModel(Player player, int id, int zoom, int model) {
		PacketBuilder bldr = new PacketBuilder(21);
		bldr.putShort(zoom).putLEShort(model).putLEShortA(id);
		player.write(bldr.toPacket());
	}
	
	public static void sendTextColor(Player player, int childId, Color colour) {
	      int r = (colour.getRed() >> 3) & 0x1F;
	      int g = (colour.getGreen() >> 3) & 0x1F;
	      int b = (colour.getBlue() >> 3) & 0x1F;
	      PacketBuilder packet = new PacketBuilder(218);
	      packet.putShort(childId);   
	      packet.putShortA((r << 10) | (g << 5) | b);
	      player.write(packet.toPacket());
	}
	
	public static void sendFriendServer(Player player, int status) {
	    PacketBuilder packet = new PacketBuilder(251);
	    packet.put((byte) status);
	    player.write(packet.toPacket());
	}
	
	public static void sendFriendStatus(Player player, long name, int worldId) {
		PacketBuilder packet = new PacketBuilder(78);
		if (worldId != 0) {
			worldId += 9;
		}
		packet.putLong(name);
		packet.put((byte) (worldId));
		player.write(packet.toPacket());
	}
	
	public static void sendPrivateMessage(Player player, long name, int rights, byte[] message, int messageSize) {
		PacketBuilder packet = new PacketBuilder(135);
		packet.putLong(name);
		//packet.putLEInt(player.getPrivateMessage().getLastMessageIndex());
		packet.put((byte) rights);
		packet.put(message, 0, messageSize);
		player.write(packet.toPacket());
	}
	
	public static void sendRunEnergy(Player player) {
		player.getSession().write(new PacketBuilder(125).put((byte) player.getRunEnergy()).toPacket());
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
	public static void sendSystemUpdate(Player player, int time) {
		player.getSession().write(new PacketBuilder(190).putLEShort(time).toPacket());
	}
	
	public static void sendMiniMapState(Player player, int state) {
		player.getSession().write(new PacketBuilder(156).put((byte) state).toPacket());
	}	

	public static void sendMultiWayIcon(Player player, int state) {
	    player.write(new PacketBuilder(233).put((byte) state).toPacket());
	}
	
	public static void sendWelcomeScreen(Player player) {
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
		sendFullScreenInterface(player, 5993, 15244);
		player.write(packet.toPacket());
	}
	
	public static void sendGroundItem(Player player, Location location, int itemId, int offset, int itemAmount) {
		PacketBuilder packet = new PacketBuilder(107);
		sendSetCurrentPlacement(player, location);
		packet.putShort(itemId);
		packet.putByteC(offset);
		packet.putShortA(itemAmount);
		player.write(packet.toPacket());
	}
	
	/**
	 * Sends the Current placement in an 8x8 region
	 * @return
	 */
	public static void sendSetCurrentLocalPlacement(Player player, Location location) {
		PacketBuilder packet = new PacketBuilder(75);
		packet.putByteC(location.getLocalX());
		packet.putByteA(location.getLocalY());
		player.write(packet.toPacket());
	}
	
	/**
	 * Sends the Current placement in an 8x8 region
	 * @return
	 */
	public static void sendSetCurrentPlacement(Player player, Location location) {
		PacketBuilder packet = new PacketBuilder(75);
		packet.putByteC(location.getX() - (player.getLastKnownRegion().getRegionX() * 8));
		packet.putByteA(location.getY() - (player.getLastKnownRegion().getRegionY() * 8));
		player.write(packet.toPacket());
	}
	
	public static void sendSetCurrentEntityPlacement(Player player, Location location) {
		PacketBuilder packet = new PacketBuilder(183);
		packet.put((byte) (location.getX() - (player.getLastKnownRegion().getRegionX())));
		packet.putByteA(location.getY() - (player.getLastKnownRegion().getRegionY()));
		player.write(packet.toPacket());		
	}
	
	public static void sendConfig(Player player, int id, int value) {
		if (value < 255) {
			sendConfig1(player, id, value);
		} else {
			sendConfig2(player, id, value);
		}
	}
	
	public static void sendConfig1(Player player, int id, int value) {
		System.out.println("config 1 value = "+value);
		PacketBuilder bldr = new PacketBuilder(182);
		bldr.putShortA(id);
		bldr.putByteS((byte) value);
		player.getSession().write(bldr.toPacket());
	}
	
	
	public static void sendConfig2(Player player, int settingId, int settingState) {
		PacketBuilder packet = new PacketBuilder(115);
		packet.putInt2(settingState);
		packet.putLEShort(settingId);
		player.write(packet.toPacket());
	}
	
	public static void sendSynchronizeConfigs(Player player) {
		player.write(new PacketBuilder(113).toPacket()); 
	}
	
	public static void sendHintIconNPC(Player player, int index) {
		PacketBuilder packet = new PacketBuilder(199);
		packet.put((byte) 1);
		packet.putShort(index);
		packet.putShort(0);
		packet.put((byte) 0);
		player.write(packet.toPacket());
	}
	
	/**
	 * 
	 * @param type: 2 centered, 3 west, 4 east, 5 south, 6 north
	 * @param xCoord
	 * @param yCoord
	 * @param zCoord, 128 for it to be positioned above wall height
	 * @return
	 */
	
	public static void sendHintIconLocation(Player player, int type, int xCoord, int yCoord, byte zCoord) {
		PacketBuilder packet = new PacketBuilder(199);
		packet.put((byte) type);
		packet.putShort(xCoord);
		packet.putShort(yCoord);
		packet.put(zCoord);
		player.write(packet.toPacket());
	}
	
	public static void sendHintIconPlayer(Player player, int index) {
		PacketBuilder packet = new PacketBuilder(199);
		packet.put((byte) 10);
		packet.putShort(index);
		packet.putShort(0);
		packet.put((byte) 0);
		player.write(packet.toPacket());
	}
	
	public static void sendObjectAnimation(Player player, int objectX, int objectY, int animationId, int objectType, int orientation) {
		for (Player players : player.getRegion().getPlayers()) {
			PacketBuilder packet = new PacketBuilder(142);
			sendSetCurrentPlacement(player, Location.create(objectX, objectY, players.getLocation().getZ()));
			packet.putShort(animationId);
			packet.putByteA((byte) ((objectType << 2) + (orientation & 3)));
			packet.put((byte) 0);
			players.write(packet.toPacket());
		}
	}
	
	public static void sendAddObject(Player player, Location location, int objectId, int objectType, int orientation) {
		PacketBuilder packet = new PacketBuilder(152);
		sendSetCurrentPlacement(player, location);
		packet.putByteC((byte) (objectType << 2) + (orientation & 3));
		packet.putLEShortA(objectId);
		packet.putByteA((byte) 0);
		player.write(packet.toPacket());
	}
	
	public static void sendAddGlobalObject(Player player, Location location, int objectId, int objectType, int orientation) {
		for (Player players : player.getRegion().getPlayers()) {
			PacketBuilder packet = new PacketBuilder(152);
			sendSetCurrentPlacement(player, location);
			packet.putByteC((byte) (objectType << 2) + (orientation & 3));
			packet.putLEShortA(objectId);
			packet.putByteA((byte) 0);
			players.write(packet.toPacket());
		}
	}
	
	public static void sendProjectile(Player player, Location start, Location finish, int id, int delay, int angle, int speed,
			int startHeight, int endHeight, int lockon, int slope, int radius) {
		int offsetX = (start.getX() - finish.getX()) * -1;
		int offsetY = (start.getY() - finish.getY()) * -1;
		PacketBuilder packet = new PacketBuilder(75);
		packet.putByteC((start.getX() - (player.getLastKnownRegion().getRegionX())));
		packet.putByteA((start.getY() - (player.getLastKnownRegion().getRegionY())));
		
		packet = new PacketBuilder(181);
		packet.put((byte) angle);
		packet.put((byte) offsetY);
		packet.put((byte) offsetX);
		packet.putShort(lockon);
		packet.putShort(id);
		packet.put((byte) startHeight);
		packet.put((byte) endHeight);
		packet.putShort(delay);
		packet.putShort(speed);
		packet.put((byte) slope);
		packet.put((byte) radius);
		
		player.write(packet.toPacket());
	}
	
}
