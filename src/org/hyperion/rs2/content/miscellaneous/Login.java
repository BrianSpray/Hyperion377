package org.hyperion.rs2.content.miscellaneous;

import java.awt.Color;

import org.hyperion.rs2.handler.LogicConstructorHandler;
import org.hyperion.rs2.handler.LogicType;
import org.hyperion.rs2.handler.impl.LoginHandler;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.container.Equipment;
import org.hyperion.rs2.model.container.Inventory;
import org.hyperion.rs2.model.container.impl.EquipmentContainerListener;
import org.hyperion.rs2.model.container.impl.InterfaceContainerListener;
import org.hyperion.rs2.model.container.impl.WeaponContainerListener;
import org.hyperion.rs2.net.ActionSender;

public class Login extends LogicConstructorHandler implements LoginHandler {

	public Login() {
		super(LogicType.DEFAULT);
	}

	@Override
	public boolean handleLogin(Player player) throws Throwable {
		player.setActive(true);
		ActionSender.sendDetails(player);
		ActionSender.sendMessage(player, "Welcome to RuneScape.");
		
		ActionSender.sendRunEnergy(player);
		
		ActionSender.sendFriendServer(player, 2); 
		//sendFriendStatus(1); // 1 is World1, -45 is Classic1
		
		//sendSynchronizeConfigs();
		
		ActionSender.sendMapRegion(player);
		
		ActionSender.sendGroundItem(player, Location.create(3222, 3223, 0), 1511, 0, 1);
		
		//sendHintIconLocation(player, 2, 3225, 3222,(byte) 0);
		ActionSender.sendSidebarInterfaces(player);
		
		//sendWelcomeScreen();

		//sendWalkableInterface(player, 197);
		int ycoord = player.getLocation().getY();
		if (ycoord > 6400) {
			ycoord -= 6400;
		}
		int wildernessLevel = 1 + (ycoord - 3520) / 8;
		//sendString(player, 199, "Level: " + wildernessLevel);		
		//sendMultiWayIcon(player, 1);
		
		ActionSender.sendTextColor(player, 7332, Color.GREEN);
				
		ActionSender.sendInterfaceConfig(player, 12323, false);
		ActionSender.sendConfig(player, 300, 100 * 10);
		ActionSender.sendConfig(player, 301, 0);

		ActionSender.sendInterfaceConfig(player, 4240, true);
		Item[] whips = {new Item(1673), new Item(1675), new Item(1677) ,new Item(1679) ,new Item(1681) , new Item(1683), new Item(6579)};
		ActionSender.sendUpdateItems(player, 4245, whips);

		ActionSender.sendInteractionOption(player, "Follow", 3, true);
		ActionSender.sendInteractionOption(player, "Trade with", 4, true);
		
		InterfaceContainerListener inventoryListener = new InterfaceContainerListener(player, Inventory.INTERFACE);
		player.getInventory().addListener(inventoryListener);
		
		InterfaceContainerListener equipmentListener = new InterfaceContainerListener(player, Equipment.INTERFACE);
		player.getEquipment().addListener(equipmentListener);
		player.getEquipment().addListener(new EquipmentContainerListener(player));
		player.getEquipment().addListener(new WeaponContainerListener(player));
		return true;
	}

	@Override
	public Object getInstance() {
		return this;
	}

}
