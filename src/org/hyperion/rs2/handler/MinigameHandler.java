package org.hyperion.rs2.handler;

import java.util.LinkedList;

import org.hyperion.rs2.handler.interfaces.ButtonHandler;
import org.hyperion.rs2.handler.interfaces.CommandHandler;
import org.hyperion.rs2.handler.interfaces.DeathHandler;
import org.hyperion.rs2.handler.interfaces.DropHandler;
import org.hyperion.rs2.handler.interfaces.EquipmentHandler;
import org.hyperion.rs2.handler.interfaces.IdleHandler;
import org.hyperion.rs2.handler.interfaces.InitiationHandler;
import org.hyperion.rs2.handler.interfaces.InterfaceInputHandler;
import org.hyperion.rs2.handler.interfaces.ItemOnItemHandler;
import org.hyperion.rs2.handler.interfaces.ItemOnNpcHandler;
import org.hyperion.rs2.handler.interfaces.ItemOnObjectHandler;
import org.hyperion.rs2.handler.interfaces.ItemOnPlayerHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionFiveHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionFourHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionOneHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionSixHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionThreeHandler;
import org.hyperion.rs2.handler.interfaces.ItemOptionTwoHandler;
import org.hyperion.rs2.handler.interfaces.LoginHandler;
import org.hyperion.rs2.handler.interfaces.NpcOptionOneHandler;
import org.hyperion.rs2.handler.interfaces.NpcOptionTwoHandler;
import org.hyperion.rs2.handler.interfaces.ObjectOptionOneHandler;
import org.hyperion.rs2.handler.interfaces.ObjectOptionThreeHandler;
import org.hyperion.rs2.handler.interfaces.ObjectOptionTwoHandler;
import org.hyperion.rs2.handler.interfaces.PickupHandler;
import org.hyperion.rs2.handler.interfaces.PlayerOptionFourHandler;
import org.hyperion.rs2.handler.interfaces.PlayerOptionOneHandler;
import org.hyperion.rs2.handler.interfaces.PlayerOptionThreeHandler;
import org.hyperion.rs2.handler.interfaces.PlayerOptionTwoHandler;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;


public abstract class MinigameHandler {
	
	private static final LinkedList<InitiationHandler> initiationHandlers = new LinkedList<InitiationHandler>();
	
	private static final LinkedList<LoginHandler> loginHandlers = new LinkedList<LoginHandler>();
	
	private static final LinkedList<CommandHandler> commandHandlers = new LinkedList<CommandHandler>();
	
	private static final LinkedList<ButtonHandler> buttonHandlers = new LinkedList<ButtonHandler>();
	
	private static final LinkedList<InterfaceInputHandler> interfaceInputHandlers = new LinkedList<InterfaceInputHandler>();
	
	private static final LinkedList<EquipmentHandler> equipmentHandlers = new LinkedList<EquipmentHandler>();
	
	private static final LinkedList<PickupHandler> pickupHandlers = new LinkedList<PickupHandler>();
	
	private static final LinkedList<DropHandler> dropHandlers = new LinkedList<DropHandler>();
	
	private static final LinkedList<IdleHandler> idleHandlers = new LinkedList<IdleHandler>();
	
	private static final LinkedList<DeathHandler> deathHandlers = new LinkedList<DeathHandler>();
	
	private static final LinkedList<ObjectOptionOneHandler> objectOptionOneHandlers = new LinkedList<ObjectOptionOneHandler>();
	
	private static final LinkedList<ObjectOptionTwoHandler> objectOptionTwoHandlers = new LinkedList<ObjectOptionTwoHandler>();
	
	private static final LinkedList<ObjectOptionThreeHandler> objectOptionThreeHandlers = new LinkedList<ObjectOptionThreeHandler>();

	private static final LinkedList<PlayerOptionOneHandler> playerOptionOneHandlers = new LinkedList<PlayerOptionOneHandler>();
	
	private static final LinkedList<PlayerOptionTwoHandler> playerOptionTwoHandlers = new LinkedList<PlayerOptionTwoHandler>();
	
	private static final LinkedList<PlayerOptionThreeHandler> playerOptionThreeHandlers = new LinkedList<PlayerOptionThreeHandler>();
	
	private static final LinkedList<PlayerOptionFourHandler> playerOptionFourHandlers = new LinkedList<PlayerOptionFourHandler>();
	
	private static final LinkedList<NpcOptionOneHandler> npcOptionOneHandlers = new LinkedList<NpcOptionOneHandler>();
	
	private static final LinkedList<NpcOptionTwoHandler> npcOptionTwoHandlers = new LinkedList<NpcOptionTwoHandler>();
	
	private static final LinkedList<ItemOnItemHandler> itemOnItemHandlers = new LinkedList<ItemOnItemHandler>();
	
	private static final LinkedList<ItemOnNpcHandler> itemOnNpcHandlers = new LinkedList<ItemOnNpcHandler>();
	
	private static final LinkedList<ItemOnObjectHandler> itemOnObjectHandlers = new LinkedList<ItemOnObjectHandler>();
	
	private static final LinkedList<ItemOnPlayerHandler> itemOnPlayerHandlers = new LinkedList<ItemOnPlayerHandler>();
	
	private static final LinkedList<ItemOptionHandler> itemOptionHandlers = new LinkedList<ItemOptionHandler>();
	
	private static final LinkedList<ItemOptionOneHandler> itemOptionOneHandlers = new LinkedList<ItemOptionOneHandler>();
		
	private static final LinkedList<ItemOptionTwoHandler> itemOptionTwoHandlers = new LinkedList<ItemOptionTwoHandler>();

	private static final LinkedList<ItemOptionThreeHandler> itemOptionThreeHandlers = new LinkedList<ItemOptionThreeHandler>();
	
	private static final LinkedList<ItemOptionFourHandler> itemOptionFourHandlers = new LinkedList<ItemOptionFourHandler>();
	
	private static final LinkedList<ItemOptionFiveHandler> itemOptionFiveHandlers = new LinkedList<ItemOptionFiveHandler>();
	
	private static final LinkedList<ItemOptionSixHandler> itemOptionSixHandlers = new LinkedList<ItemOptionSixHandler>();
	
	public MinigameHandler() {
		Object instance = getObject();
		if (instance instanceof InitiationHandler) {
			initiationHandlers.add((InitiationHandler) instance);
		}
		if (instance instanceof LoginHandler) {
			loginHandlers.add((LoginHandler) instance);
		}
		if (instance instanceof CommandHandler) {
			commandHandlers.add((CommandHandler) instance);
		}
		if (instance instanceof ButtonHandler) {
			buttonHandlers.add((ButtonHandler) instance);
		}
		if (instance instanceof InterfaceInputHandler) {
			interfaceInputHandlers.add((InterfaceInputHandler) instance);
		}
		if (instance instanceof EquipmentHandler) {
			equipmentHandlers.add((EquipmentHandler) instance);
		}
		if (instance instanceof PickupHandler) {
			pickupHandlers.add((PickupHandler) instance);
		}
		if (instance instanceof DropHandler) {
			dropHandlers.add((DropHandler) instance);
		}
		if (instance instanceof IdleHandler) {
			idleHandlers.add((IdleHandler) instance);
		}
		if (instance instanceof DeathHandler) {
			deathHandlers.add((DeathHandler) instance);
		}
		if (instance instanceof ObjectOptionOneHandler) {
			objectOptionOneHandlers.add((ObjectOptionOneHandler) instance);
		}
		if (instance instanceof ObjectOptionTwoHandler) {
			objectOptionTwoHandlers.add((ObjectOptionTwoHandler) instance);
		}
		if (instance instanceof ObjectOptionThreeHandler) {
			objectOptionThreeHandlers.add((ObjectOptionThreeHandler) instance);
		}
		if (instance instanceof PlayerOptionOneHandler) {
			playerOptionOneHandlers.add((PlayerOptionOneHandler) instance);
		}
		if (instance instanceof PlayerOptionTwoHandler) {
			playerOptionTwoHandlers.add((PlayerOptionTwoHandler) instance);
		}
		if (instance instanceof PlayerOptionThreeHandler) {
			playerOptionThreeHandlers.add((PlayerOptionThreeHandler) instance);
		}
		if (instance instanceof PlayerOptionFourHandler) {
			playerOptionFourHandlers.add((PlayerOptionFourHandler) instance);
		}
		if (instance instanceof NpcOptionOneHandler) {
			npcOptionOneHandlers.add((NpcOptionOneHandler) instance);
		}
		if (instance instanceof NpcOptionTwoHandler) {
			npcOptionTwoHandlers.add((NpcOptionTwoHandler) instance);
		}
		if (instance instanceof ItemOnItemHandler) {
			itemOnItemHandlers.add((ItemOnItemHandler) instance);
		}
		if (instance instanceof ItemOnNpcHandler) {
			itemOnNpcHandlers.add((ItemOnNpcHandler) instance);
		}
		if (instance instanceof ItemOnObjectHandler) {
			itemOnObjectHandlers.add((ItemOnObjectHandler) instance);
		}
		if (instance instanceof ItemOnPlayerHandler) {
			itemOnPlayerHandlers.add((ItemOnPlayerHandler) instance);
		}
		if (instance instanceof ItemOptionHandler) {
			itemOptionHandlers.add((ItemOptionHandler) instance);
		}
		if (instance instanceof ItemOptionOneHandler) {
			itemOptionOneHandlers.add((ItemOptionOneHandler) instance);
		}
		if (instance instanceof ItemOptionTwoHandler) {
			itemOptionTwoHandlers.add((ItemOptionTwoHandler) instance);
		}
		if (instance instanceof ItemOptionThreeHandler) {
			itemOptionThreeHandlers.add((ItemOptionThreeHandler) instance);
		}
		if (instance instanceof ItemOptionFourHandler) {
			itemOptionFourHandlers.add((ItemOptionFourHandler) instance);
		}
		if (instance instanceof ItemOptionFiveHandler) {
			itemOptionFiveHandlers.add((ItemOptionFiveHandler) instance);
		}
		if (instance instanceof ItemOptionSixHandler) {
			itemOptionSixHandlers.add((ItemOptionSixHandler) instance);
		}
	}
	public static void handleInitiation() throws Throwable {
		for (InitiationHandler handler : initiationHandlers) {
			handler.init();
		}
	}
	
	public static boolean login(Player player) throws Throwable {
		for (LoginHandler handler : loginHandlers) {
			return handler.handleLogin(player);
		}
		return false;
	}
	
	public static boolean command(Player player, String command) throws Throwable {
		for (CommandHandler handler : commandHandlers) {
			if (handler.handleCommand(player, command)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean button(Player player, int buttonId) throws Throwable {
		for (ButtonHandler handler : buttonHandlers) {
			if (handler.handleButton(player, buttonId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean interfaceInput(Player player, int interfaceId, int id, int child) throws Throwable {
		for (InterfaceInputHandler handler : interfaceInputHandlers) {
			if (handler.handleInterfaceInput(player, interfaceId, id, child)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean equipment(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (EquipmentHandler handler : equipmentHandlers) {
			if (handler.handleEquipment(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean pickup(Player player, int id, Location location) throws Throwable {
		for (PickupHandler handler : pickupHandlers) {
			if (handler.handlePickUp(player, id, location)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean drop(Player player, int interfaceId, int itemId, int slot) throws Throwable {
		for (DropHandler handler : dropHandlers) {
			if (handler.handleDrop(player, interfaceId, itemId, slot)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean idle(Player player) throws Throwable {
		for (IdleHandler handler : idleHandlers) {
			if (handler.handleIdle(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean death(Entity killer, Entity dead) throws Throwable {
		for (DeathHandler handler : deathHandlers) {
			if (handler.handleDead(killer, dead)) {
				return true;
			}
		}
		return false;
	}

	public static boolean objectOptionOne(Player player,  RSObject object, GameObjectDefinition definition) throws Throwable {
		for (ObjectOptionOneHandler handler : objectOptionOneHandlers) {
			if (handler.handleObjectOptionOne(player, object, definition)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean objectOptionTwo(Player player, RSObject object, GameObjectDefinition definition) throws Throwable {
		for (ObjectOptionTwoHandler handler : objectOptionTwoHandlers) {
			if (handler.handleObjectOptionTwo(player, object, definition)) {
				return true;
			}
		}
		return false;
	}

	public static boolean objectOptionThree(Player player, RSObject object, GameObjectDefinition definition) throws Throwable {
		for (ObjectOptionThreeHandler handler : objectOptionThreeHandlers) {
			if (handler.handleObjectOptionThree(player, object, definition)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean playerOptionOne(Player player, Player other) throws Throwable {
		for (PlayerOptionOneHandler handler : playerOptionOneHandlers) {
			if (handler.handlePlayerOptionOne(player, other)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean playerOptionTwo(Player player, Player other) throws Throwable {
		for (PlayerOptionTwoHandler handler : playerOptionTwoHandlers) {
			if (handler.handlePlayerOptionTwo(player, other)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean playerOptionThree(Player player, Player other) throws Throwable {
		for (PlayerOptionThreeHandler handler : playerOptionThreeHandlers) {
			if (handler.handlePlayerOptionThree(player, other)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean playerOptionFour(Player player, Player other) throws Throwable {
		for (PlayerOptionFourHandler handler : playerOptionFourHandlers) {
			if (handler.handlePlayerOptionFour(player, other)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean npcOptionOne(Player player, NPC other) throws Throwable {
		for (NpcOptionOneHandler handler : npcOptionOneHandlers) {
			if (handler.handleNpcOptionOne(player, other)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean npcOptionTwo(Player player, NPC other) throws Throwable {
		for (NpcOptionTwoHandler handler : npcOptionTwoHandlers) {
			if (handler.handleNpcOptionTwo(player, other)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOnItem(Player player, int interfaceId, int usedId, int usedSlot, int withId, int withSlot) throws Throwable {
		for (ItemOnItemHandler handler : itemOnItemHandlers) {
			if (handler.handleItemOnItem(player, interfaceId, usedId, usedSlot, withId, withSlot)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOnNpc(Player player,  NPC other, int itemId, int slot, int interfaceId) throws Throwable {
		for (ItemOnNpcHandler handler : itemOnNpcHandlers) {
			if (handler.handleItemOnNpc(player, other, itemId, slot, interfaceId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOnObject(Player player, int interfaceId, int itemId, int slot, RSObject object, GameObjectDefinition definition) throws Throwable {
		for (ItemOnObjectHandler handler : itemOnObjectHandlers) {
			if (handler.handleItemOnObject(player, interfaceId, itemId, slot, object, definition)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOnPlayer(Player player, Player other, int itemId, int interfaceId, int slot) throws Throwable {
		for (ItemOnPlayerHandler handler : itemOnPlayerHandlers) {
			if (handler.handleItemOnPlayer(player, other, itemId, interfaceId, slot)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOption(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionHandler handler : itemOptionHandlers) {
			if (handler.handleItemOption(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOptionOne(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionOneHandler handler : itemOptionOneHandlers) {
			if (handler.handleItemOptionOne(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOptionTwo(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionTwoHandler handler : itemOptionTwoHandlers) {
			if (handler.handleItemOptionTwo(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOptionThree(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionThreeHandler handler : itemOptionThreeHandlers) {
			if (handler.handleItemOptionThree(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOptionFour(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionFourHandler handler : itemOptionFourHandlers) {
			if (handler.handleItemOptionFour(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean itemOptionFive(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionFiveHandler handler : itemOptionFiveHandlers) {
			if (handler.handleItemOptionFive(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}

	public static boolean itemOptionSix(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		for (ItemOptionSixHandler handler : itemOptionSixHandlers) {
			if (handler.handleItemOptionSix(player, interfaceId, slot, itemId)) {
				return true;
			}
		}
		return false;
	}
	
	public static void clear() throws Throwable {
		initiationHandlers.clear();
		loginHandlers.clear();
		commandHandlers.clear();
		buttonHandlers.clear();
		interfaceInputHandlers.clear();
		equipmentHandlers.clear();
		pickupHandlers.clear();
		dropHandlers.clear();
		idleHandlers.clear();
		deathHandlers.clear();
		objectOptionOneHandlers.clear();
		objectOptionTwoHandlers.clear();
		objectOptionThreeHandlers.clear();
		playerOptionOneHandlers.clear();
		playerOptionTwoHandlers.clear();
		playerOptionThreeHandlers.clear();
		playerOptionFourHandlers.clear();
		npcOptionOneHandlers.clear();
		npcOptionTwoHandlers.clear();
		itemOnItemHandlers.clear();
		itemOnNpcHandlers.clear();
		itemOnObjectHandlers.clear();
		itemOnPlayerHandlers.clear();
		itemOptionHandlers.clear();
		itemOptionOneHandlers.clear();
		itemOptionTwoHandlers.clear();
		itemOptionThreeHandlers.clear();
		itemOptionFourHandlers.clear();
		itemOptionFiveHandlers.clear();
		itemOptionSixHandlers.clear();
	}

	public abstract Object getObject();

}