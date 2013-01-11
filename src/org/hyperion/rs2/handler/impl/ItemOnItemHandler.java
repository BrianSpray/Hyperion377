package org.hyperion.rs2.handler.impl;

import org.hyperion.rs2.model.Player;

public abstract interface ItemOnItemHandler {

	public abstract boolean handleItemOnItem(Player player, int packetId, int interfaceId, int usedId, int usedSlot, int withId, int withSlot) throws Throwable;

}
