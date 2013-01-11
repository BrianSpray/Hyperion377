package org.hyperion;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hyperion.fileserver.FileServer;
import org.hyperion.rs2.RS2Server;
import org.hyperion.rs2.handler.HandlerManager;
import org.hyperion.rs2.model.World;

/**
 * A class to start both the file and game servers.
 * @author Graham Edgecombe
 *
 */
public class Server {
	
	/**
	 * The protocol version.
	 */
	public static final int VERSION = 377;
	
	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(Server.class.getName());

	/**
	 * The entry point of the application.
	 * @param args The command line arguments.
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		logger.info("Starting Hyperion...");
		World.getWorld(); // this starts off background loading
		HandlerManager.init();
		HandlerManager.handleInitiation();
		try {
			new FileServer().bind().start();
			new RS2Server().bind(RS2Server.PORT).start();
		} catch(Exception ex) {
			logger.log(Level.SEVERE, "Error starting Hyperion.", ex);
			System.exit(1);
		}
	}

}
