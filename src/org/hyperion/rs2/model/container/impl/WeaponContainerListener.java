package org.hyperion.rs2.model.container.impl;

import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.container.Container;
import org.hyperion.rs2.model.container.ContainerListener;
import org.hyperion.rs2.model.container.Equipment;
import org.hyperion.rs2.net.ActionSender;

/**
 * A listener which updates the weapon tab.
 * @author Graham Edgecombe
 *
 */
public class WeaponContainerListener implements ContainerListener {
	
	/**
	 * The player.
	 */
	private Player player;

	/**
	 * Creates the listener.
	 * @param player The player.
	 */
	public WeaponContainerListener(Player player) {
		this.player = player;
	}

	@Override
	public void itemChanged(Container container, int slot) {
		if(slot == Equipment.SLOT_WEAPON) {
			sendWeapon();
		}
	}

	@Override
	public void itemsChanged(Container container, int[] slots) {
		for(int slot : slots) {
			if(slot == Equipment.SLOT_WEAPON) {
				sendWeapon();
				return;
			}
		}
	}

	@Override
	public void itemsChanged(Container container) {
		sendWeapon();
	}
	
	/**
	 * Sends weapon information.
	 */
	private void sendWeapon() {
		Item weapon = player.getEquipment().get(Equipment.SLOT_WEAPON);
		int id = -1;
		String name = null;
		if(weapon == null) {
			name = "Unarmed";
		} else {
			name = weapon.getDefinition().getName();
			id = weapon.getId();
		}
		String genericName = filterWeaponName(name).trim();
		sendWeapon(id, name, genericName);
	}

	/**
	 * Sends weapon information.
	 * @param id The id.
	 * @param name The name.
	 * @param genericName The filtered name.
	 */
	private void sendWeapon(int id, String name, String genericName) {
		if(name.equals("Unarmed")) {
			ActionSender.sendSidebarInterface(player, 0, 5855);
			ActionSender.sendString(player, 5857, name);
		} else if(name.endsWith("whip")) {
			ActionSender.sendSidebarInterface(player, 0, 12290);
			ActionSender.sendInterfaceModel(player, 12291, 200, id);
			ActionSender.sendString(player, 12293, name);
		} else if(name.endsWith("Scythe")) {
			ActionSender.sendSidebarInterface(player, 0, 776);
			ActionSender.sendInterfaceModel(player, 777, 200, id);
			ActionSender.sendString(player, 779, name);
		} else if(name.endsWith("bow") || name.startsWith("Crystal bow") || name.startsWith("Toktz-xil-ul")) {
			ActionSender.sendSidebarInterface(player, 0, 1764);
			ActionSender.sendInterfaceModel(player, 1765, 200, id);
			ActionSender.sendString(player, 1767, name);
		} else if(name.startsWith("Staff") || name.endsWith("staff")) {
			ActionSender.sendSidebarInterface(player, 0, 328);
			ActionSender.sendInterfaceModel(player, 329, 200, id);
			ActionSender.sendString(player, 331, name);
		} else if(genericName.startsWith("dart")) {
			ActionSender.sendSidebarInterface(player, 0, 4446);
			ActionSender.sendInterfaceModel(player, 4447, 200, id);
			ActionSender.sendString(player, 4449, name);
		} else if(genericName.startsWith("dagger")) {
			ActionSender.sendSidebarInterface(player, 0, 2276);
			ActionSender.sendInterfaceModel(player, 2277, 200, id);
			ActionSender.sendString(player, 2279, name);
		} else if(genericName.startsWith("pickaxe")) {
			ActionSender.sendSidebarInterface(player, 0, 5570);
			ActionSender.sendInterfaceModel(player, 5571, 200, id);
			ActionSender.sendString(player, 5573, name);
		} else if(genericName.startsWith("axe") || genericName.startsWith("battleaxe")) {
			ActionSender.sendSidebarInterface(player, 0, 1698);
			ActionSender.sendInterfaceModel(player, 1699, 200, id);
			ActionSender.sendString(player, 1701, name);
		} else if(genericName.startsWith("Axe") || genericName.startsWith("Battleaxe")) {
			ActionSender.sendSidebarInterface(player, 0, 1698);
			ActionSender.sendInterfaceModel(player, 1699, 200, id);
			ActionSender.sendString(player, 1701, name);
		} else if(genericName.startsWith("halberd")) {
			ActionSender.sendSidebarInterface(player, 0, 8460);
			ActionSender.sendInterfaceModel(player, 8461, 200, id);
			ActionSender.sendString(player, 8463, name);
		} else {
			ActionSender.sendSidebarInterface(player, 0, 2423);
			ActionSender.sendInterfaceModel(player, 2424, 200, id);
			ActionSender.sendString(player, 2426, name);
		}
	}

	/**
	 * Filters a weapon name.
	 * @param name The original name.
	 * @return The filtered name.
	 */
	private String filterWeaponName(String name) {
		final String[] filtered = new String[] {
			"Iron", "Steel", "Scythe", "Black", "Mithril", "Adamant",
			"Rune", "Granite", "Dragon", "Crystal", "Bronze"
		};
		for(String filter : filtered) {
			name = name.replaceAll(filter, "");
		}
		return name;
	}

}
