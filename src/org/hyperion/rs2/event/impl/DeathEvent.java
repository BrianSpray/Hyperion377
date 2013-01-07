package org.hyperion.rs2.event.impl;

import org.hyperion.rs2.event.Event;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.net.ActionSender;

/**
 * The death event handles player and npc deaths. Drops loot, does animation, teleportation, etc.
 * @author Graham
 *
 */
@SuppressWarnings("unused")
public class DeathEvent extends Event {
	
	private Entity entity;
	
	private int cycle = 0;

	/**
	 * Creates the death event for the specified entity.
	 * @param entity The player or npc whose death has just happened.
	 */
	public DeathEvent(Entity entity) {
		super(3000);
		this.entity = entity;
	}

	@Override
	public void execute() {
		if (cycle == 0) {
			if(entity instanceof Player) {
				Player player = (Player) entity;
				player.getSkills().setLevel(Skills.HITPOINTS, player.getSkills().getLevelForExperience(Skills.HITPOINTS));
				entity.setDead(false);
				ActionSender.sendMessage(player, "Oh dear, you are dead!");
				player.playAnimation(Animation.create(2304, 0));
				entity.resetInteractingEntity();
				cycle++;
			} else {
				this.stop();
				entity.setTeleportTarget(Entity.DEFAULT_LOCATION);
				entity.playAnimation(Animation.create(2304, 0));
			}
		}
	}

}