package org.hyperion.rs2.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.hyperion.rs2.handler.impl.*;
import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;

public final class LogicHandler {
	
	private final LinkedList<InitiationHandler> initiationHandlers = new LinkedList<InitiationHandler>();

	private final LinkedList<LoginHandler> loginHandlers = new LinkedList<LoginHandler>();
	
	private final Map<Integer, LinkedList<ButtonHandler>> buttonHandlers = new HashMap<Integer, LinkedList<ButtonHandler>>();
	
	private final Map<Integer, LinkedList<ItemOnItemHandler>> itemOnItemHandlers = new HashMap<Integer, LinkedList<ItemOnItemHandler>>();
	
	private final Map<Integer, LinkedList<NpcOptionHandler>> npcOptionHandlers = new HashMap<Integer, LinkedList<NpcOptionHandler>>();
	
	private final Map<Integer, LinkedList<ObjectOptionHandler>> objectOptionHandlers = new HashMap<Integer, LinkedList<ObjectOptionHandler>>();
	
	private final Map<Integer, LinkedList<PlayerOptionHandler>> playerOptionHandlers = new HashMap<Integer, LinkedList<PlayerOptionHandler>>();
	
	public void cast(Object instance) {
		if (instance instanceof InitiationHandler) {
			initiationHandlers.add((InitiationHandler) instance);
		}
		if (instance instanceof LoginHandler) {
			loginHandlers.add((LoginHandler) instance);
		}
	}
	
	public LinkedList<InitiationHandler> getInitiationHandlers() {
		return initiationHandlers;
	}
	
	public boolean login(Player player) throws Throwable {
		for (LoginHandler handler : loginHandlers) {
			return handler.handleLogin(player);
		}
		return false;
	}
	
	public boolean button(Player player, int packetId, int buttonId) throws Throwable {
		LinkedList<ButtonHandler> handlers = buttonHandlers.get(buttonId);
		if (handlers == null) {
			return false;
		}
		for (ButtonHandler handler : handlers) {
			if (handler.handleButton(player, packetId, buttonId)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean npcOption(Player player, int packetId, int option, NPC npc) throws Throwable {
		LinkedList<NpcOptionHandler> handlers = npcOptionHandlers.get(option);
		if (handlers == null) {
			return false;
		}
		for (NpcOptionHandler handler : handlers) {
			if (handler.handleNpcOption(player, packetId, option, npc)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean objectOption(Player player, int packetId, int option, RSObject object, GameObjectDefinition definition) throws Throwable {
		LinkedList<ObjectOptionHandler> handlers = objectOptionHandlers.get(option);
		if (handlers == null) {
			return false;
		}
		for (ObjectOptionHandler handler : handlers) {
			if (handler.handleObjectOption(player, packetId, option, object, definition)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean playerOption(Player player, int packetId, int option, Player other) throws Throwable {
		LinkedList<PlayerOptionHandler> handlers = playerOptionHandlers.get(option);
		if (handlers == null) {
			return false;
		}
		for (PlayerOptionHandler handler : handlers) {
			if (handler.handlePlayerOption(player, packetId, option, other)) {
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		initiationHandlers.clear();
		loginHandlers.clear();
		buttonHandlers.clear();
		itemOnItemHandlers.clear();
		npcOptionHandlers.clear();
		objectOptionHandlers.clear();
		playerOptionHandlers.clear();
	}
	
	public void register(String signer, Object instance, Object input) {
		System.out.println("Name" + signer);
		try {
			if (signer == ButtonHandler.class.getName()) {
				int[] interfaces = (int[]) input;
				for (int id : interfaces) {
					if (!buttonHandlers.containsKey(id)) {
						buttonHandlers.put(id, new LinkedList<ButtonHandler>());
					}
					buttonHandlers.get(id).add((ButtonHandler) instance);
				}
			} else if (signer == ItemOnItemHandler.class.getName()) {
				int[] items = (int[]) input;
				for (int id : items) {
					if (!itemOnItemHandlers.containsKey(id)) {
						itemOnItemHandlers.put(id, new LinkedList<ItemOnItemHandler>());
					}
					itemOnItemHandlers.get(id).add((ItemOnItemHandler) instance);
				}
			} else if (signer == NpcOptionHandler.class.getName()) {
				int[] ids = (int[]) input;
				for (int id : ids) {
					if (!npcOptionHandlers.containsKey(id)) {
						npcOptionHandlers.put(id, new LinkedList<NpcOptionHandler>());
					}
					npcOptionHandlers.get(id).add((NpcOptionHandler) instance);
				}
			} else if (signer == ObjectOptionHandler.class.getName()) {
				int[] ids = (int[]) input;
				for (int id : ids) {
					if (!objectOptionHandlers.containsKey(id)) {
						objectOptionHandlers.put(id, new LinkedList<ObjectOptionHandler>());
					}
					objectOptionHandlers.get(id).add((ObjectOptionHandler) instance);
				}
			} else if (signer == PlayerOptionHandler.class.getName()) {
				int[] ids = (int[]) input;
				for (int id : ids) {
					if (!playerOptionHandlers.containsKey(id)) {
						playerOptionHandlers.put(id, new LinkedList<PlayerOptionHandler>());
					}
					playerOptionHandlers.get(id).add((PlayerOptionHandler) instance);
				}
			} else {
				System.out.println("Signer not supported.");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}