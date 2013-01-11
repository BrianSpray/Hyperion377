package org.hyperion.rs2.handler;

public enum LogicType {

	QUEST(),

	MINIGAME(),

	SKILL(),
	
	DEFAULT();
	
	private final LogicHandler handler = new LogicHandler();

	public LogicHandler getHandler() {
		return handler;
	}
}