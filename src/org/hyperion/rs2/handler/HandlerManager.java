package org.hyperion.rs2.handler;

import java.io.File;

import java.util.LinkedList;
import java.util.logging.Logger;

import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;
import org.hyperion.rs2.util.ClassLoaderUtils;


public final class HandlerManager {
	
	private static final Logger logger = Logger.getLogger(HandlerManager.class.getName());
	
	public static void init() throws Throwable {
		logger.info("Loading content...");
		long started = System.nanoTime();
		LinkedList<String> classDirectorys = new LinkedList<String>();
		LinkedList<String> directorys = new LinkedList<String>();
		for (File file : new File("./bin/").listFiles()) {
			if (file.isFile()) {
				classDirectorys.add(file.getPath().replace(".class", "").replace(".\\bin\\", "").replace("\\", "."));
			} else if (file.isDirectory()) {
				directorys.add(file.getPath().replace("\\", "/"));
			}
		}
		while (!directorys.isEmpty()) {
			String directory = directorys.poll();
			for (File file : new File(directory).listFiles()) {
				if (file.isFile()) {
					classDirectorys.add(file.getPath().replace(".class", "").replace(".\\bin\\", "").replace("\\", "."));
				} else if (file.isDirectory()) {
					directorys.add(file.getPath().replace("\\", "/"));
				}
			}
		}
		for (String directory : classDirectorys) {
			try {
				ClassLoaderUtils.getLoader().loadClass(directory.replace("./bin/", "").replace("/", ".")).newInstance();
			} catch (Exception e) {
				continue;
			}
		}
		long taken = System.nanoTime() - started;
		logger.info("Time taken to load content: "+((double) taken / 1000000000.0)+" seconds.");
		System.gc();
	}
	
	public static void handleInitiation() throws Throwable {
		logger.info("Initiating content...");
		long started = System.nanoTime();
		LogicHandler.handleInitiation();
		SkillHandler.handleInitiation();
		MinigameHandler.handleInitiation();
		QuestHandler.handleInitiation();
		long taken = System.nanoTime() - started;
		logger.info("Initiation of content in: "+((double) taken / 1000000000.0)+" seconds.");
	}
	
	public static boolean handleLogin(Player player) throws Throwable {
		if (!QuestHandler.login(player)) {
			if (!MinigameHandler.login(player)) {
				if (!SkillHandler.login(player)) {
					return LogicHandler.login(player);
				}
			}
		}
		return true;
	}
	
	public static boolean handleCommand(Player player, String command) throws Throwable {
		if (!QuestHandler.command(player, command)) {
			if (!MinigameHandler.command(player, command)) {
				if (!SkillHandler.command(player, command)) {
					return LogicHandler.command(player, command);
				}
			}
		}
		return true;
	}
	
	public static boolean handleButton(Player player, int buttonId) throws Throwable {
		if (!QuestHandler.button(player, buttonId)) {
			if (!MinigameHandler.button(player, buttonId)) {
				if (!SkillHandler.button(player, buttonId)) {
					return LogicHandler.button(player, buttonId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleInterfaceInput(Player player, int interfaceId, int id, int child) throws Throwable {
		if (!QuestHandler.interfaceInput(player, interfaceId, id, child)) {
			if (!MinigameHandler.interfaceInput(player, interfaceId, id, child)) {
				if (!SkillHandler.interfaceInput(player, interfaceId, id, child)) {
					return LogicHandler.interfaceInput(player, interfaceId, id, child);
				}
			}
		}
		return true;
	}
	
	public static boolean handleEquipment(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.equipment(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.equipment(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.equipment(player, interfaceId, slot, itemId)) {
					return LogicHandler.equipment(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}

	public static boolean handlePickup(Player player, int id, Location location) throws Throwable {
		//if (!QuestHandler.button(player, interfaceId, buttonId, buttonId2)) {
			//if (!MinigameHandler.button(player, interfaceId, buttonId, buttonId2)) {
				if (!SkillHandler.pickup(player, id, location)) {
					return LogicHandler.pickup(player, id, location);
				}
			//}
		//}
		return true;
	}
	
	public static boolean handleDrop(Player player, int interfaceId, int itemId, int slot) throws Throwable {
		if (!QuestHandler.drop(player, interfaceId, itemId, slot)) {
			if (!MinigameHandler.drop(player, interfaceId, itemId, slot)) {
				if (!SkillHandler.drop(player, interfaceId, itemId, slot)) {
					return LogicHandler.drop(player, interfaceId, itemId, slot);
				}
			}
		}
		return true;
	}

	public static boolean handleIdle(Player player) throws Throwable {
		if (!QuestHandler.idle(player)) {
			if (!MinigameHandler.idle(player)) {
				if (!SkillHandler.idle(player)) {
					return LogicHandler.idle(player);
				}
			}
		}
		return true;
	}
	
	public static boolean handleDeath(Entity killer, Entity dead) throws Throwable {
		if (!QuestHandler.death(killer, dead)) {
			if (!MinigameHandler.death(killer, dead)) {
				if (!SkillHandler.death(killer, dead)) {
					return LogicHandler.death(killer, dead);
				}
			}
		}
		return true;
	}
	
	public static boolean handleObjectOptionOne(Player player, RSObject object, GameObjectDefinition definition) throws Throwable {
		if (!QuestHandler.objectOptionOne(player, object, definition)) {
			if (!MinigameHandler.objectOptionOne(player, object, definition)) {
				if (!SkillHandler.objectOptionOne(player, object, definition)) {
					return LogicHandler.objectOptionOne(player, object, definition);
				}
			}
		}
		return true;
	}
	
	public static boolean handleObjectOptionTwo(Player player, RSObject object, GameObjectDefinition definition) throws Throwable {
		if (!QuestHandler.objectOptionTwo(player, object, definition)) {
			if (!MinigameHandler.objectOptionTwo(player, object, definition)) {
				if (!SkillHandler.objectOptionTwo(player, object, definition)) {
					return LogicHandler.objectOptionTwo(player, object, definition);
				}
			}
		}
		return true;
	}
	
	public static boolean handleObjectOptionThree(Player player, RSObject object, GameObjectDefinition definition) throws Throwable {
		if (!QuestHandler.objectOptionThree(player, object, definition)) {
			if (!MinigameHandler.objectOptionThree(player, object, definition)) {
				if (!SkillHandler.objectOptionThree(player, object, definition)) {
					return LogicHandler.objectOptionThree(player, object, definition);
				}
			}
		}
		return true;
	}
	
	public static boolean handlePlayerOptionOne(Player player, Player other) throws Throwable {
		if (!QuestHandler.playerOptionOne(player, other)) {
			if (!MinigameHandler.playerOptionOne(player, other)) {
				if (!SkillHandler.playerOptionOne(player, other)) {
					return LogicHandler.playerOptionOne(player, other);
				}
			}
		}
		return true;
	}
	
	public static boolean handlePlayerOptionTwo(Player player, Player other) throws Throwable {
		if (!QuestHandler.playerOptionTwo(player, other)) {
			if (!MinigameHandler.playerOptionTwo(player, other)) {
				if (!SkillHandler.playerOptionTwo(player, other)) {
					return LogicHandler.playerOptionTwo(player, other);
				}
			}
		}
		return true;
	}
	
	public static boolean handlePlayerOptionThree(Player player, Player other) throws Throwable {
		if (!QuestHandler.playerOptionThree(player, other)) {
			if (!MinigameHandler.playerOptionThree(player, other)) {
				if (!SkillHandler.playerOptionThree(player, other)) {
					return LogicHandler.playerOptionThree(player, other);
				}
			}
		}
		return true;
	}
	
	public static boolean handlePlayerOptionFour(Player player, Player other) throws Throwable {
		if (!QuestHandler.playerOptionFour(player, other)) {
			if (!MinigameHandler.playerOptionFour(player, other)) {
				if (!SkillHandler.playerOptionFour(player, other)) {
					return LogicHandler.playerOptionFour(player, other);
				}
			}
		}
		return true;
	}
	
	public static boolean handleNpcOptionOne(Player player, NPC other) throws Throwable {
		if (!QuestHandler.npcOptionOne(player, other)) {
			if (!MinigameHandler.npcOptionOne(player, other)) {
				if (!SkillHandler.npcOptionOne(player, other)) {
					return LogicHandler.npcOptionOne(player, other);
				}
			}
		}
		return true;
	}
	
	public static boolean handleNpcOptionTwo(Player player, NPC other) throws Throwable {
		if (!QuestHandler.npcOptionTwo(player, other)) {
			if (!MinigameHandler.npcOptionTwo(player, other)) {
				if (!SkillHandler.npcOptionTwo(player, other)) {
					return LogicHandler.npcOptionTwo(player, other);
				}
			}
		}
		return true;
	}

	public static boolean handleNpcOptionThree(Player player, NPC other) throws Throwable {
		//if (!QuestHandler.button(player, interfaceId, buttonId, buttonId2)) {
			//if (!MinigameHandler.button(player, interfaceId, buttonId, buttonId2)) {
				//if (!SkillHandler.npcOptionTwo(player, other)) {
					//return LogicHandler.npcOptionTwo(player, other);
				//}
			//}
		//}
		return true;
	}
	
	public static boolean handleItemOnItem(Player player, int interfaceId, int usedId, int usedSlot, int withId, int withSlot) throws Throwable {
		if (!QuestHandler.itemOnItem(player, interfaceId, usedId, usedSlot, withId, withSlot)) {
			if (!MinigameHandler.itemOnItem(player, interfaceId, usedId, usedSlot, withId, withSlot)) {
				if (!SkillHandler.itemOnItem(player, interfaceId, usedId, usedSlot, withId, withSlot)) {
					return LogicHandler.itemOnItem(player, interfaceId, usedId, usedSlot, withId, withSlot);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOnNpc(Player player,  NPC other, int itemId, int slot, int interfaceId) throws Throwable {
		if (!QuestHandler.itemOnNpc(player, other, itemId, slot, interfaceId)) {
			if (!MinigameHandler.itemOnNpc(player, other, itemId, slot, interfaceId)) {
				if (!SkillHandler.itemOnNpc(player, other, itemId, slot, interfaceId)) {
					return LogicHandler.itemOnNpc(player, other, itemId, slot, interfaceId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOnObject(Player player, int interfaceId, int itemId, int slot, RSObject object, GameObjectDefinition definition) throws Throwable {
		if (!QuestHandler.itemOnObject(player, interfaceId, itemId, slot, object, definition)) {
			if (!MinigameHandler.itemOnObject(player, interfaceId, itemId, slot, object, definition)) {
				if (!SkillHandler.itemOnObject(player, interfaceId, itemId, slot, object, definition)) {
					return LogicHandler.itemOnObject(player, interfaceId, itemId, slot, object, definition);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOnPlayer(Player player, Player other, int itemId, int interfaceId, int slot) throws Throwable {
		if (!QuestHandler.itemOnPlayer(player, other, itemId, interfaceId, slot)) {
			if (!MinigameHandler.itemOnPlayer(player, other, itemId, interfaceId, slot)) {
				if (!SkillHandler.itemOnPlayer(player, other, itemId, interfaceId, slot)) {
					return LogicHandler.itemOnPlayer(player, other, itemId, interfaceId, slot);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOption(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOption(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOption(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOption(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOption(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}

	public static boolean handleItemOptionOne(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOptionOne(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOptionOne(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOptionOne(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOptionOne(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOptionTwo(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOptionTwo(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOptionTwo(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOptionTwo(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOptionTwo(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOptionThree(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOptionThree(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOptionThree(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOptionThree(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOptionThree(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOptionFour(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOptionFour(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOptionFour(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOptionFour(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOptionFour(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOptionFive(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOptionFive(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOptionFive(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOptionFive(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOptionFive(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}
	
	public static boolean handleItemOptionSix(Player player, int interfaceId, int slot, int itemId) throws Throwable {
		if (!QuestHandler.itemOptionSix(player, interfaceId, slot, itemId)) {
			if (!MinigameHandler.itemOptionSix(player, interfaceId, slot, itemId)) {
				if (!SkillHandler.itemOptionSix(player, interfaceId, slot, itemId)) {
					return LogicHandler.itemOptionSix(player, interfaceId, slot, itemId);
				}
			}
		}
		return true;
	}
}
