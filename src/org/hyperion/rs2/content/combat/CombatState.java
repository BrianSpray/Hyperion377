package org.hyperion.rs2.content.combat;

import org.hyperion.rs2.model.Entity;

public class CombatState {
	
	@SuppressWarnings("unused")
	private final Entity mob;
	
	private boolean canAnimate = true;

	public CombatState(Entity mob) {
		this.mob = mob;
	}

	public void setCanAnimate(boolean canAnimate) {
		this.canAnimate = canAnimate;
	}

	public boolean canAnimate() {
		return canAnimate;
	}
}
