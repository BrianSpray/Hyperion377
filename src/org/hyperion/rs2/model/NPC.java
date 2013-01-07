package org.hyperion.rs2.model;

import org.hyperion.rs2.event.impl.DeathEvent;
import org.hyperion.rs2.model.Damage.Hit;
import org.hyperion.rs2.model.Damage.HitType;
import org.hyperion.rs2.model.UpdateFlags.UpdateFlag;
import org.hyperion.rs2.model.region.Region;

/**
 * <p>Represents a non-player character in the in-game world.</p>
 * @author Graham Edgecombe
 *
 */
public class NPC extends Entity {
	
	/**
	 * The definition.
	 */
	private final NPCDefinition definition;
	
	private int health;
	
	private int transformInto;
	
	private String forcedChat = "";
	
	/**
	 * Creates the NPC with the specified definition.
	 * @param definition The definition.
	 */
	public NPC(NPCDefinition definition) {
		super();
		this.definition = definition;
		this.health = getMaxHealth();
	}
	
	/**
	 * Gets the NPC definition.
	 * @return The NPC definition.
	 */
	public NPCDefinition getDefinition() {
		return definition;
	}

	@Override
	public void addToRegion(Region region) {
		region.addNpc(this);
	}

	@Override
	public void removeFromRegion(Region region) {
		region.removeNpc(this);
	}

	@Override
	public int getClientIndex() {
		return this.getIndex();
	}

	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return 100;
	}
	
	public int getTransformInto() {
		return transformInto;
	}

	public void transformNpc(int npcid) {
		this.transformInto = npcid;
		this.getUpdateFlags().flag(UpdateFlag.TRANSFORM);
	}
	
	public String getForcedChat() {
		return forcedChat;
	}
	
	public void requestForcedChat(String text) {
		this.forcedChat = text;
		this.getUpdateFlags().flag(UpdateFlag.FORCED_CHAT);
	}
	
	@Override
	public void inflictDamage(int damage, HitType type) {
		// TODO Auto-generated method stub
		
	}

	public void inflictDamage(Hit damage, Entity aggressor) {
		if (!getUpdateFlags().get(UpdateFlag.HIT)) {
			getDamage().setHit1(damage);
			getUpdateFlags().flag(UpdateFlag.HIT);
		} else {
			if (!getUpdateFlags().get(UpdateFlag.HIT_2)) {
				getDamage().setHit2(damage);
				getUpdateFlags().flag(UpdateFlag.HIT_2);
			}
		}
		health -= damage.getDamage();
		if (health <= 0) {
			if (!this.isDead()) {
				World.getWorld().submit(new DeathEvent(this));
			}
			this.setDead(true);
		}
		
	}

}
