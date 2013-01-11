package org.hyperion.rs2.content.skill;

import org.hyperion.rs2.handler.LogicConstructorHandler;
import org.hyperion.rs2.handler.LogicType;
import org.hyperion.rs2.handler.impl.ItemOnItemHandler;
import org.hyperion.rs2.model.Player;


public class Herblore extends LogicConstructorHandler implements ItemOnItemHandler {

	public Herblore() {
		super(LogicType.SKILL,
			new Object[][] {
				{ItemOnItemHandler.class, Potions.array()}
			}
		);
	}
	
	public static enum Potions {
		
		ATTACK_POTION(1, 25, 249, 221, 91, 121),
		ANTIPOISON_POTION(5, 37, 251, 235, 93, 175),
		STRENGTH_POTION(12, 50, 253, 225, 95, 115),
		RESTORE_POTION(22, 62, 255, 223, 97, 127),
		ENERGY_POTION(26, 67, 255, 1975, 97, 3010),
		DEFENCE_POTION(30, 75, 257, 239, 99, 133),
		AGILITY_POTION(34, 80, 2998, 2152, 3002, 3034),
		COMBAT_POTION(36, 84, 255, 9735, 97, 9741),
		PRAYER_POTION(38, 87, 257, 231, 99, 139),
		SUPER_ATTACK_POTION(45, 100, 259, 221, 101, 145),
		SUPER_ANTIPOISON_POTION(48, 106, 259, 235, 101, 181),
		SUPER_ENERGY_POTION(52, 117, 259, 2970, 103, 3018),
		SUPER_STRENGTH_POTION(55, 125, 263, 225, 105, 157),
		WEAPON_POISON_POTION(60, 137, 263, 241, 105, 5937),
		SUPER_RESTORE_POTION(63, 142, 3000, 223, 3004, 3026),
		SUPER_DEFENCE_POTION(66, 150, 265, 239, 107, 163),
		ANTIFIRE_POTION(69, 157, 2481, 241, 2483, 2454),
		MAGIC_POTION(76, 172, 2481, 221, 2483, 3042),
		RANGING_POTION(72, 162, 267, 245, 109, 169),
		ZAMORAK_BREW_POTION(76, 175, 269, 247, 111, 189),
		SARADOMIN_BREW_POTION(81, 180, 269, 6693, 3002, 6687);
		
		private final int requiredLevel, herb, secondaryIngredient, unfinishedPotion, finishedPotion;
		private final double xp;

		private Potions(int requiredLevel, double xp, int herb, int secondaryIngredient, int unfinishedPotion, int finishedPotion) {
			this.requiredLevel = requiredLevel;
			this.xp = xp;
			this.herb = herb;
			this.secondaryIngredient = secondaryIngredient;
			this.unfinishedPotion = unfinishedPotion;
			this.finishedPotion = finishedPotion;
		}
		
		public int getRequiredLevel() {
			return requiredLevel;
		}
		
		public int getHerb() {
			return herb;
		}
		
		public int getSecondaryIngredient() {
			return secondaryIngredient;
		}
		
		public int getUnfinishedPotion() {
			return unfinishedPotion;
		}
		
		public int getFinishedPotion() {
			return finishedPotion;
		}
		
		public double getXp() {
			return xp;
		}
		
		public static int[] array() {
			int[] items = new int[Potions.values().length];
			int offset = 0;
			for (Potions potion : Potions.values()) {
				items[offset] = potion.getHerb();
				items[offset + 1] = potion.getSecondaryIngredient();
				items[offset + 2] = potion.getUnfinishedPotion();
				offset += 3;
			}
			return items;
		}
	}

	@Override
	public boolean handleItemOnItem(Player player, int packetId, int interfaceId, int usedId, int usedSlot, int withId, int withSlot) throws Throwable {
		return false;
	}

	@Override
	public Object getInstance() {
		return this;
	}
}
