package org.hyperion.rs2.content.minigames;

import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.Skills;
import org.hyperion.rs2.net.ActionSender;

public class PestControl {
	
	private static int receivedXp(Player player, int skills) {
		int divisor = 6;
		if (skills == Skills.PRAYER) {
			divisor = 12;
		}
		return (int) (Math.pow(player.getSkills().getLevel(skills), 2) / divisor);
	}
	
	public static void sendPestControlRewardsInterface(Player player) {
		ActionSender.sendString(player, 18767, "Attack - " + receivedXp(player, Skills.ATTACK) + " xp");		
		ActionSender.sendString(player, 18768, "Strength - " + receivedXp(player, Skills.STRENGTH) + " xp");
		ActionSender.sendString(player, 18769, "Defence - " + receivedXp(player, Skills.DEFENCE) + " xp");
		ActionSender.sendString(player, 18770, "Ranged - " + receivedXp(player, Skills.RANGE) + " xp");
		ActionSender.sendString(player, 18771, "Magic - " + receivedXp(player, Skills.MAGIC) + " xp");
		ActionSender.sendString(player, 18772, "Hitpoints - " + receivedXp(player, Skills.HITPOINTS) + " xp");
		ActionSender.sendString(player, 18773, "Prayer - " + receivedXp(player, Skills.PRAYER) + " xp");
		ActionSender.sendInterface(player, 18691);
	}
	
}
