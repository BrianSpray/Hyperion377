package org.hyperion.rs2.content.combat;

import org.hyperion.rs2.content.defintion.WeaponDefinition;
import org.hyperion.rs2.event.Event;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.ItemDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.container.Equipment;

/**
 * @author Brian 
 * Credits to Scu11, and Hybrent
 */

public class Combat {
	
	/** 
	 * TODO: Check whether or not the entity is already in combat
	 * Checks if an entity can another another entity.
	 */
	public boolean canAttack(Entity attacker, Entity victim, boolean messages, boolean cannon) {
		// Checks if the attacker and victim are dead or null; if either are true you cannot attack.
		if (attacker.isDead() || victim.isDead() || attacker == null || victim == null) {
			return false;
		}
		return true;		
	}
	
	/**
	 * Starts a combat session.
	 * @param attacker
	 * @param victim
	 */
	public void attack(final Entity attacker, Entity victim) {
		World.getWorld().submit(new Event(1) {

			@Override
			public void execute() {
				attacker.getCombatState().setCanAnimate(true);
				stop();
			}
			
		});
	}
	
	protected boolean attack(final Entity attacker, final Entity victim, boolean retaliating) {
		
		return false;
	}
	
	public void defend(Entity attacker, Entity victim, boolean blockAnimation) {
		if (victim instanceof Player ? ((Player) victim).getSettings().isAutoRetaliating() : true) {
			// Check if the interacting entity is not the attacker
				// if not then we check the combat state and delay the attacker
			// set the active combat session to attack the attacker.
		}
		if (blockAnimation && victim.getCombatState().canAnimate()) {
			int defendAnimation = 424;
			Item shield = null;
			Item weapon = null;
			String shieldName = null;
			if (victim instanceof Player) {
				shield = ((Player) victim).getEquipment().get(Equipment.SLOT_SHIELD);
				weapon = ((Player) victim).getEquipment().get(Equipment.SLOT_WEAPON);
				shieldName = shield != null ? ItemDefinition.forId(shield.getId()).name : "";
				if(shield != null && shieldName.contains("shield") || shieldName.contains("ket-xil")) {
					//defendAnimation = EquipmentDefinition.forId(shield.getId()).defendAnimation;
				} else if (weapon != null) {
					defendAnimation = WeaponDefinition.forId(weapon.getId()).blockAnimation;
				}
				if (((Player) victim).getSettings().isAutoRetaliating()) {
					victim.setInteractingEntity(attacker);
				}
			} else {
				victim.setInteractingEntity(attacker);
			}
			victim.playAnimation(Animation.create(defendAnimation));
		}
	}
	
	
	/**
	 * Calculates the damage of a player.
	 */
	public int damage(int maxHit, Entity attacker, Entity victim, AttackType attackType, int skill, int prayer, boolean special, boolean ignorePrayer) {
		maxHit = 1;
		return maxHit;	
	}
	
	public static enum AttackType {
		STAB(0),
		SLASH(1),
		CRUSH(2),
		MAGIC(3),
		RANGE(4);

		private final int id;

		private AttackType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	public static enum CombatStyle {

		ACCURATE(0, new int[] { Skills.ATTACK, Skills.HITPOINTS }, new double[] { 4, 1.33 }), 
		AGGRESSIVE_1(1,new int[] { Skills.STRENGTH }, new double[] { 4 }),
		AGGRESSIVE_2(2, new int[] { Skills.STRENGTH }, new double[] { 4 }),
		DEFENSIVE(3, new int[] { Skills.DEFENCE }, new double[] { 4, 1.33 }),
		CONTROLLED_1(4, new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, new double[] { 1.33, 1.33, 1.33 }),
		CONTROLLED_2(5, new int[] {Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, new double[] { 1.33, 1.33, 1.33 }),
		CONTROLLED_3(6, new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, new double[] { 1.33, 1.33, 1.33 }),
		AUTOCAST(7, new int[] { Skills.MAGIC }, new double[] { 2 }),
		DEFENSIVE_AUTOCAST(8, new int[] {Skills.MAGIC, Skills.DEFENCE}, new double[] { 1.33, 1 });

		private final int id;

		private final int[] skills;

		private final double[] experiences;

		private CombatStyle(int id, int[] skills, double[] experiences) {
			this.id = id;
			this.skills = skills;
			this.experiences = experiences;
		}

		public int getId() {
			return id;
		}

		public int[] getSkills() {
			return skills;
		}

		public int getSkill(int index) {
			return skills[index];
		}

		public double[] getExperiences() {
			return experiences;
		}

		public double getExperience(int index) {
			return experiences[index];
		}
	}
	
}