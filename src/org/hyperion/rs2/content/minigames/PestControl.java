package org.hyperion.rs2.content.minigames;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;

public class PestControl {
	
	private static int receivedXp(Player player, int skills) {
		int divisor = 6;
		if (skills == Skills.PRAYER) {
			divisor = 12;
		}
		return (int) (Math.pow(player.getSkills().getLevel(skills), 2) / divisor);
	}
	
	public static void sendPestControlRewardsInterface(Player player) {
		player.getActionSender().sendString(18767, "Attack - " + receivedXp(player, Skills.ATTACK) + " xp");		
		player.getActionSender().sendString(18768, "Strength - " + receivedXp(player, Skills.STRENGTH) + " xp");
		player.getActionSender().sendString(18769, "Defence - " + receivedXp(player, Skills.DEFENCE) + " xp");
		player.getActionSender().sendString(18770, "Ranged - " + receivedXp(player, Skills.RANGE) + " xp");
		player.getActionSender().sendString(18771, "Magic - " + receivedXp(player, Skills.MAGIC) + " xp");
		player.getActionSender().sendString(18772, "Hitpoints - " + receivedXp(player, Skills.HITPOINTS) + " xp");
		player.getActionSender().sendString(18773, "Prayer - " + receivedXp(player, Skills.PRAYER) + " xp");
		player.getActionSender().sendInterface(18691);
	}
	
}
