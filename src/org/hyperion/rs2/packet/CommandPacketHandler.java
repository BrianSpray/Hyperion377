package org.hyperion.rs2.packet;

import org.hyperion.rs2.content.combat.Combat;
import org.hyperion.rs2.content.combat.Combat.AttackType;
import org.hyperion.rs2.content.minigames.PestControl;
import org.hyperion.rs2.event.Event;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Graphic;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.NPCDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.container.Bank;
import org.hyperion.rs2.net.Packet;
import org.hyperion.rs2.pf.AStarPathFinder;
import org.hyperion.rs2.pf.Path;
import org.hyperion.rs2.pf.PathFinder;
import org.hyperion.rs2.pf.Point;
import org.hyperion.rs2.pf.Tile;
import org.hyperion.rs2.pf.TileMap;
import org.hyperion.rs2.pf.TileMapBuilder;

/**
 * Handles player commands (the ::words).
 * @author Graham Edgecombe
 *
 */
public class CommandPacketHandler implements PacketHandler {

	@Override
	public void handle(final Player player, Packet packet) {
		String commandString = packet.getRS2String();
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		try {
			if(command.equals("tele")) {
				if(args.length == 3 || args.length == 4) {
					int x = Integer.parseInt(args[1]);
					int y = Integer.parseInt(args[2]);
					int z = player.getLocation().getZ();
					if(args.length == 4) {
						z = Integer.parseInt(args[3]);
					}
					player.setTeleportTarget(Location.create(x, y, z));
				} else {
					player.getActionSender().sendMessage("Syntax is ::tele [x] [y] [z].");
				}
			} else if(command.equals("pos")) {
				player.getActionSender().sendMessage("You are at: " + player.getLocation() + ".");
			} else if(command.equals("item")) {
				if(args.length == 2 || args.length == 3) {
					int id = Integer.parseInt(args[1]);
					int count = 1;
					if(args.length == 3) {
						count = Integer.parseInt(args[2]);
					}
					player.getInventory().add(new Item(id, count));
				} else {
					player.getActionSender().sendMessage("Syntax is ::item [id] [count].");
				}
			} else if(command.equals("anim")) {
				if(args.length == 2 || args.length == 3) {
					int id = Integer.parseInt(args[1]);
					int delay = 0;
					if(args.length == 3) {
						delay = Integer.parseInt(args[2]);
					}
					player.playAnimation(Animation.create(id, delay));
				}
			} else if(command.equals("gfx")) {
				if(args.length == 2 || args.length == 3) {
					int id = Integer.parseInt(args[1]);
					int delay = 0;
					if(args.length == 3) {
						delay = Integer.parseInt(args[2]);
					}
					player.playGraphics(Graphic.create(id, delay, 0));
				}
			} else if(command.equals("bank")) {
				Bank.open(player);
			} else if(command.equals("npc")) {
				int id = Integer.parseInt(args[1]);
				NPC npc = new NPC(NPCDefinition.forId(id));
				npc.setLocation(Location.create(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
				World.getWorld().register(npc);
			} else if(command.equals("interface")) {
				int id = Integer.parseInt(args[1]);
				player.getActionSender().sendInterface(id);
			} else if(command.equals("c")) {
				int id = Integer.parseInt(args[1]);
				player.getActionSender().sendChatBoxInterface(id);
			} else if(command.equals("pc")) {
				PestControl.sendPestControlRewardsInterface(player);
			} else if (command.equals("config")) {
				int settingState = Integer.parseInt(args[1]);
				int settingId = Integer.parseInt(args[2]);
				player.getActionSender().sendConfig1(settingState, settingId);		
			} else if (command.equals("playerh")) {
				player.getActionSender().sendString(970, player.getName());
				player.getActionSender().sendString(971, "I think this text box works.");
				player.getActionSender().sendInterfaceAnimation(969, 588);
				player.getActionSender().sendPlayerHead(969);
				player.getActionSender().sendChatBoxInterface(968);
			} else if (command.equals("playerh2")) {
				player.getActionSender().sendString(12742, "Dondakan the Dwarf");
				player.getActionSender().sendString(12743, "Don't worry, it'll be all over in a moment!");
				player.getActionSender().sendInterfaceAnimation(12744, 588);
				player.getActionSender().sendNpcHead(12744, 1837);			
				
				player.getActionSender().sendString(12745, "Something goes here.");
				player.getActionSender().sendInterfaceAnimation(12740, 600);
				player.getActionSender().sendPlayerHead(12740);
				player.getActionSender().sendInterface(12737);
			}  else if (command.equals("npch")) {
				player.getActionSender().sendString(4884, "King Black Dragon");
				player.getActionSender().sendString(4885, "I think this text box works.");
				player.getActionSender().sendInterfaceAnimation(4883, 588);
				player.getActionSender().sendNpcHead(4883, 2642);
				player.getActionSender().sendChatBoxInterface(4882);
			} else if(command.equals("max")) {
				for(int i = 0; i <= Skills.SKILL_COUNT; i++) {
					player.getSkills().setLevel(i, 99);
					player.getSkills().setExperience(i, 13034431);
				}
			} else if(command.startsWith("nvn")) {
				AttackType type = AttackType.MELEE;
				NPC npc1 = (NPC) World.getWorld().getNPCs().get(Integer.parseInt(args[1]));
				NPC npc2 = (NPC) World.getWorld().getNPCs().get(Integer.parseInt(args[2]));
				Combat.doAttack(npc1, npc2, type);
			} else if(command.startsWith("empty")) {
				player.getInventory().clear();
				player.getActionSender().sendMessage("Your inventory has been emptied.");
			} else if(command.equals("config")) {
				int id = Integer.parseInt(args[1]);
				int id2 = Integer.parseInt(args[2]);
				player.getActionSender().sendConfig(id, id2);
			} else if(command.startsWith("cl")) {
				World.getWorld().submit(new Event(600) {
					int configId = 0;
					@Override
					public void execute() {
						player.getActionSender().sendConfig(configId, 15);
						player.getActionSender().sendMessage("Config " + configId);
						configId++;
					}
					
				});
			} else if(command.startsWith("lvl")) {
				try {
					player.getSkills().setLevel(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					player.getSkills().setExperience(Integer.parseInt(args[1]), player.getSkills().getXPForLevel(Integer.parseInt(args[2])) + 1);
					player.getActionSender().sendMessage(Skills.SKILL_NAME[Integer.parseInt(args[1])] + " level is now " + Integer.parseInt(args[2]) + ".");	
				} catch(Exception e) {
					e.printStackTrace();
					player.getActionSender().sendMessage("Syntax is ::lvl [skill] [lvl].");				
				}
			} else if(command.startsWith("skill")) {
				try {
					player.getSkills().setLevel(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					player.getActionSender().sendMessage(Skills.SKILL_NAME[Integer.parseInt(args[1])] + " level is temporarily boosted to " + Integer.parseInt(args[2]) + ".");	
				} catch(Exception e) {
					e.printStackTrace();
					player.getActionSender().sendMessage("Syntax is ::skill [skill] [lvl].");				
				}
			} else if(command.startsWith("enablepvp")) {
				try {
					player.updatePlayerAttackOptions(true);
					player.getActionSender().sendMessage("PvP combat enabled.");
				} catch(Exception e) {
					
				}
			} else if(command.startsWith("goto")) {
				if(args.length == 3) {
					try {
						int radius = 16;
						
						int x = Integer.parseInt(args[1]) - player.getLocation().getX() + radius;
						int y = Integer.parseInt(args[2]) - player.getLocation().getY() + radius;
												
						TileMapBuilder bldr = new TileMapBuilder(player.getLocation(), radius);
						TileMap map = bldr.build();
						
						PathFinder pf = new AStarPathFinder();
						Path p = pf.findPath(player.getLocation(), radius, map, radius, radius, x, y);
						
						if(p == null) return;
												
						player.getWalkingQueue().reset();
						for(Point p2 : p.getPoints()) {
							player.getWalkingQueue().addStep(p2.getX(), p2.getY());
						}
					} catch(Throwable ex) {
						ex.printStackTrace();
					}
				}
			} else if(command.startsWith("tmask")) {
				int radius = 0;
				TileMapBuilder bldr = new TileMapBuilder(player.getLocation(), radius);
				TileMap map = bldr.build();
				Tile t = map.getTile(0, 0);
				player.getActionSender().sendMessage("N: " + t.isNorthernTraversalPermitted() +
					" E: " + t.isEasternTraversalPermitted() +
					" S: " + t.isSouthernTraversalPermitted() +
					" W: " + t.isWesternTraversalPermitted());
			}
		} catch(Exception ex) {
			player.getActionSender().sendMessage("Error while processing command.");
		}
	}

}
