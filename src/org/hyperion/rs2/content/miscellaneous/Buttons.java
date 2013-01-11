package org.hyperion.rs2.content.miscellaneous;

import org.hyperion.rs2.handler.LogicConstructorHandler;
import org.hyperion.rs2.handler.LogicType;
import org.hyperion.rs2.handler.impl.ButtonHandler;
import org.hyperion.rs2.model.Animation;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.ActionSender;

public class Buttons extends LogicConstructorHandler implements ButtonHandler {
	
	public Buttons() {
		super(LogicType.DEFAULT,
			new Object[][] {
				{ButtonHandler.class, new int[] {152, 153, 161, 162, 163, 164, 165}}
			}
		);
	}

	@Override
	public boolean handleButton(Player player, int packetId, int buttonId) throws Throwable {
		switch(buttonId) {
			case 152:
				if(player.getWalkingQueue().isRunningToggled()) {
					player.getWalkingQueue().setRunningToggled(false);
				}
				return true;
			case 153:
				if(!player.getWalkingQueue().isRunningToggled()) {
					player.getWalkingQueue().setRunningToggled(true);
				}
				return true;
			case 161:
				player.playAnimation(Animation.CRY);
				return true;
			case 162:
				player.playAnimation(Animation.THINKING);
				return true;
			case 163:
				player.playAnimation(Animation.WAVE);
				return true;
			case 164:
				player.playAnimation(Animation.BOW);
				return true;
			case 165:
				player.playAnimation(Animation.ANGRY);
				return true;
			case 166:
				player.playAnimation(Animation.DANCE);
				return true;
			case 167:
				player.playAnimation(Animation.BECKON);
				return true;
			case 168:
				player.playAnimation(Animation.YES_EMOTE);
				return true;
			case 169:
				player.playAnimation(Animation.NO_EMOTE);
				return true;
			case 170:
				player.playAnimation(Animation.LAUGH);
				return true;
			case 171:
				player.playAnimation(Animation.CHEER);
				return true;
			case 172:
				player.playAnimation(Animation.CLAP);
				return true;
			case 13362:
				player.playAnimation(Animation.PANIC);
				return true;
			case 13363:
				player.playAnimation(Animation.JIG);
				return true;
			case 13364:
				player.playAnimation(Animation.SPIN);
				return true;
			case 13365:
				player.playAnimation(Animation.HEADBANG);
				return true;
			case 13366:
				player.playAnimation(Animation.JOYJUMP);
				return true;
			case 13367:
				player.playAnimation(Animation.RASPBERRY);
				return true;
			case 13368:
				player.playAnimation(Animation.YAWN);
				return true;
			case 13383:
				player.playAnimation(Animation.GOBLIN_BOW);
				return true;
			case 13384:
				player.playAnimation(Animation.GOBLIN_DANCE);
				return true;
			case 13369:
				player.playAnimation(Animation.SALUTE);
				return true;
			case 13370:
				player.playAnimation(Animation.SHRUG);
				return true;
			case 11100:
				player.playAnimation(Animation.BLOW_KISS);
				return true;
			case 667:
				player.playAnimation(Animation.GLASS_BOX);
				return true;
			case 6503:
				player.playAnimation(Animation.CLIMB_ROPE);
				return true;
			case 6506:
				player.playAnimation(Animation.LEAN);
				return true;
			case 666:
				player.playAnimation(Animation.GLASS_WALL);
				return true;
			case 2458:
				ActionSender.sendLogout(player);
				return true;
			case 5387:
				player.getSettings().setWithdrawAsNotes(false);
				return true;
			case 5386:
				player.getSettings().setWithdrawAsNotes(true);
				return true;
			case 8130:
				player.getSettings().setSwapping(true);
				return true;
			case 8131:
				player.getSettings().setSwapping(false);
				return true;
			case 3651:
				ActionSender.sendCloseInterfaces(player);
				return true;
		}
		return false;
	}

	@Override
	public Object getInstance() {
		return this;
	}
}
