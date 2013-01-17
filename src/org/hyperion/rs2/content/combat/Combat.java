package org.hyperion.rs2.content.combat;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.hyperion.rs2.content.defintion.WeaponDefinition;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Damage;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.Damage.Hit;
import org.hyperion.rs2.model.Damage.HitType;
import org.hyperion.rs2.model.container.Equipment;

public class Combat {
	// TODO: REDO EVERYTHING
	
	/**
	 * Attack types.
	 */
	public static enum AttackType {
		/**
		 * Melee-based attacks.
		 */
		MELEE,
		
		/**
		 * Projectile-based attacks.
		 */
		RANGED,
		
		/**
		 * Magic-based attacks.
		 */
		MAGIC,
	}
	
	public static class CombatSession {
		private int damage = 0;
		private long timestamp = 0;
		
		public CombatSession() {
			
		}
		
		public int getDamage() {
			return this.damage;
		}
	}
	
	/**
	 * Represents an instance of combat, where Entity is an assailant and Integer is the sum of their damage done. This is mapped to every victim in combat,
	 * and used to determine drops.
	 * @author Brett Russell
	 */
	public static class CollectiveCombatSession {
		private long stamp;
		private Map<Entity, CombatSession> damageMap;
		private Set<Entity> names = damageMap.keySet();
		private boolean isActive;
		private Entity victim;
		
		public CollectiveCombatSession(Entity victim) {
			java.util.Date date = new java.util.Date();
			this.stamp = date.getTime();
			this.isActive = true;
			this.victim = victim;
		}
		
		/**
		 * Gets the timestamp for this object (when the session began).
		 * @return The timestamp.
		 */
		public long getStamp() {
			return stamp;
		}
		
		/**
		 * Gets the entity with the highest damage count this session.
		 * @return The entity with the highest damage count.
		 */
		public Entity getTopDamage() {
			Entity top = null;
			int damageDone = 0;
			int currentHighest = 0;
			
			Iterator<Entity> itr = names.iterator();
			
			while(itr.hasNext()) {
				Entity currentEntity = itr.next();
				damageDone = damageMap.get(currentEntity).getDamage();
				if(damageDone > currentHighest) {
					currentHighest = damageDone;
					top = currentEntity;
				}
			}
			return top;
		}
		
		/**
		 * Returns the Map of this session's participants. If you would want it, that is...
		 * @return A Map of the participants and their damage done.
		 */
		public Map<Entity, CombatSession> getDamageCharts() {
			return damageMap;
		}
		
		/**
		 * Adds a participant to this session.
		 * @param participant The participant to add.
		 */
		public void addParticipant(Entity participant) {
			// TODO CombatSession
			damageMap.put(participant, null);
		}
		
		/**
		 * Remove a participant.
		 * @param participant The participant to remove.
		 */
		public void removeParticipant(Entity participant) {
			damageMap.remove(participant);
		}
		
		/**
		 * Sets this sessions active state.
		 * @param state A <code>boolean</code> value representing the state.
		 */
		public void setState(boolean b) {
			this.isActive = b;
		}
		
		/**
		 * Determine the active state of this session.
		 * @return The active state as a <code>boolean</code> value.
		 */
		public boolean getIsActive() {
			return this.isActive;
		}
	}

	/**
	 * Get the attackers' weapon speed.
	 * @param player The player for whose weapon we are getting the speed value.
	 * @return A <code>double</code>-type value of the weapon speed.
	 */
	public static int getAttackSpeed(Entity entity) {
		int attackSpeed = 2400;
		Item weapon = null;
		if (entity instanceof Player) {
			weapon = ((Player) entity).getEquipment().get(Equipment.SLOT_WEAPON);
			if (weapon != null) {
				attackSpeed = WeaponDefinition.forId(weapon.getId()).attackSpeed;
			}
		}
		return attackSpeed;
	}
	
	/**
	 * Checks if an entity can attack another. Shamelessly stolen from another of Graham's projects.
	 * @param source The source entity.
	 * @param victim The target entity.
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public static boolean canAttack(Entity source, Entity victim) {
		if(victim.isDead() || source.isDead())
			return false;
		if((source instanceof Player) && (victim instanceof Player)) {
			// attackable zones, etc
		}
		if ((source instanceof Player)) {
			return true;
		}
		if((victim instanceof NPC)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Inflicts damage on the recipient.
	 * @param recipient The entity taking the damage.
	 * @param damage The damage to be done.
	 */
	public static void inflictDamage(Entity recipient, Entity aggressor, Hit damage) {
		// TODO: Check for shields.
		int blockAnimation = 424;
		Item weapon = null;
		if((recipient instanceof Player) && (aggressor != null)) {
			Player p = (Player) recipient;
			weapon = ((Player) recipient).getEquipment().get(Equipment.SLOT_WEAPON);
			if (weapon != null) {
				blockAnimation = WeaponDefinition.forId(weapon.getId()).blockAnimation;
			}
			p.inflictDamage(damage, aggressor);
			p.playAnimation(Animation.create(blockAnimation, 2));
		} else if ((recipient instanceof NPC) && (aggressor != null)) {
			NPC n = (NPC) recipient;
			n.inflictDamage(damage, aggressor);
			n.playAnimation(Animation.create(424, 2));
		}
		if (aggressor instanceof Player) {
			// TODO: Figure out why adding experience to hit points makes you invincible...
			((Player) aggressor).getSkills().addExperience(Skills.ATTACK /*getAttackStyle*/, damage.getDamage() * 4);
			//((Player) aggressor).getSkills().addExperience(Skills.HITPOINTS /*getAttackStyle*/, damage.getDamage() * 1.33);
		}
	}
	
	/**
	 * Calculates the damage a single hit by a player will do.
	 * @param source The attacking entity.
	 * @param victim The defending entity.
	 * @return An <code>int</code> representing the damage done.
	 */
	public static Hit calculatePlayerHit(Entity source, Entity victim, AttackType attack) {
		int verdict = 0;
		HitType hit = HitType.NORMAL_DAMAGE;
		if(victim instanceof Player) {
			Player v = (Player) victim;
			// calculations here
			verdict = 1;
			if(verdict >= v.getSkills().getLevel(Skills.HITPOINTS)) {
				verdict = v.getSkills().getLevel(Skills.HITPOINTS);
			}
		} else if(victim instanceof NPC) {
			NPC v = (NPC) victim;
			verdict = 1;
			if(verdict >= v.getHealth()) {
				verdict = v.getHealth();
			}
		}
		if(verdict == 0) {
			hit = HitType.NO_DAMAGE;
		}
		Hit thisAttack = new Hit(verdict, hit);
		return thisAttack;
	}
	
	public static Hit calculateNPCHit(Entity source, Entity victim, AttackType attack) {
		int verdict = 0;
		HitType hit = HitType.NORMAL_DAMAGE;
		if(victim instanceof Player) {
			Player v = (Player) victim;
			// calculations here
			verdict = 1;
			if(verdict >= v.getSkills().getLevel(Skills.HITPOINTS)) {
				verdict = v.getSkills().getLevel(Skills.HITPOINTS);
			}
		} else if(victim instanceof NPC) {
			NPC v = (NPC) victim;
			verdict = 1;
			if(verdict >= v.getHealth()) {
				verdict = v.getHealth();
			}
		}
		if(verdict == 0) {
			hit = HitType.NO_DAMAGE;
		}
		Hit thisAttack = new Hit(verdict, hit);
		return thisAttack;		
	}
	
	public static void initiateCombat(Entity source, Entity victim) {
		
	}
	
	/**
	 * Carries out a single attack.
	 * @param source The entity source of the attack.
	 * @param victim The entity victim of the attack.
	 * @param attackType The type of attack.
	 */
	public static void doAttack(Entity source, Entity victim, AttackType attackType) {
		int attackAnimation = 422;
		Item weapon = null;
		if(!canAttack(source, victim))
			return;
		if(source instanceof Player) {
			weapon = ((Player) source).getEquipment().get(Equipment.SLOT_WEAPON);
			source.setInteractingEntity(victim);
			if (weapon != null) {
				attackAnimation = WeaponDefinition.forId(weapon.getId()).attackAnimation;
			}
			source.playAnimation(Animation.create(attackAnimation, 0));
			inflictDamage(victim, source, calculatePlayerHit(source, victim, attackType));
		} else if(source instanceof NPC) {
			source.setInteractingEntity(victim);
			source.playAnimation(Animation.create(422, 0));			
			inflictDamage(victim, source, calculateNPCHit(source, victim, attackType));
		}
	}
	
}
