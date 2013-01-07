package org.hyperion.rs2.content.skills;

import org.hyperion.rs2.handler.SkillHandler;
import org.hyperion.rs2.handler.interfaces.ObjectOptionOneHandler;
import org.hyperion.rs2.model.GameObjectDefinition;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.RSObject;

public class Woodcutting extends SkillHandler implements ObjectOptionOneHandler {

	@Override
	public boolean handleObjectOptionOne(Player player, RSObject object, GameObjectDefinition definition) throws Throwable {
		if(object.getId() != -1) {
			
		}
		return false;
	}

	@Override
	public Object getObject() {
		return this;
	}

}
