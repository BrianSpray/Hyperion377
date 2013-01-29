package org.hyperion.rs2.handler;

import java.io.File;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.hyperion.rs2.handler.impl.InitiationHandler;
import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;
import org.hyperion.rs2.util.ClassLoaderUtils;

public final class HandlerManager {
	
	private static final Logger logger = Logger.getLogger(HandlerManager.class.getName());
	
	private static final ClassLoaderUtils classLoader = new ClassLoaderUtils(HandlerManager.class.getClassLoader());
	
	public static void init() throws Throwable {
		for (LogicType type : LogicType.values()) {
			type.getHandler().clear();
		}
		logger.info("Loading content...");
		long started = System.nanoTime();
		LinkedList<String> classDirectorys = new LinkedList<String>();
		LinkedList<String> directorys = new LinkedList<String>();
		for (File file : new File("./bin/org/hyperion/rs2/content/").listFiles()) {
			System.out.println("File Name: " + file.getName());
			if (file.isFile()) {
				classDirectorys.add(file.getPath().replace(".class", "").replace(".\\bin\\", "").replace("\\", "."));
			} else if (file.isDirectory()) {
				directorys.add(file.getPath().replace("\\", "/"));
			}
		}
		while (!directorys.isEmpty()) {
			String directory = directorys.poll();
			for (File file : new File(directory).listFiles()) {
				if (file.getName().contains("conflicted copy")) {
					file.delete();
					continue;
				}
				if (file.isFile()) {
					classDirectorys.add(file.getPath().replace(".class", "").replace(".\\bin\\", "").replace("\\", "."));
				} else if (file.isDirectory()) {
					directorys.add(file.getPath().replace("\\", "/"));
				}
			}
		}
		for (final String directory : classDirectorys) {
			try {
				classLoader.loadClass(directory.replace("./bin/", "").replace("/", ".")).newInstance();
			} catch (Throwable t) {
				continue;
			}
		}
		long taken = System.nanoTime() - started;
		logger.info("Time taken to load content: "+((double) taken / 1000000000.0)+" seconds.");
	}
	
	public static void handleInitiation() throws Throwable {
		logger.info("Initiating content...");
		long started = System.nanoTime();
		for (LogicType type : LogicType.values()) {
			for (InitiationHandler handler : type.getHandler().getInitiationHandlers()) {
				handler.init();
			}
		}
		long taken = System.nanoTime() - started;
		logger.info("Initiation of content in: "+((double) taken / 1000000000.0)+" seconds.");
		System.gc();
	}
	
	public static boolean handleLogin(Player player) throws Throwable {
		for (LogicType type : LogicType.values()) {
			if (type.getHandler().login(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean handleButton(Player player, int packetId, int buttonId) throws Throwable {
		for (LogicType type : LogicType.values()) {
			if (type.getHandler().button(player, packetId, buttonId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean handleNpcOption(Player player, int packetId, int option, NPC npc) throws Throwable {
		for (LogicType type : LogicType.values()) {
			if (type.getHandler().npcOption(player, packetId, option, npc)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean handleObjectOption(Player player, int packetId, int option, RSObject object, GameObjectDefinition definition) throws Throwable {
		for (LogicType type : LogicType.values()) {
			if (type.getHandler().objectOption(player, packetId, option, object, definition)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean handlePlayerOption(Player player, int packetId, int option) throws Throwable {
		for (LogicType type : LogicType.values()) {
			if (type.getHandler().playerOption(player, packetId, option)) {
				return true;
			}
		}
		return false;
	}
}