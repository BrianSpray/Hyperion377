package org.hyperion.rs2.handler;

public abstract class LogicConstructorHandler {
	
	public LogicConstructorHandler(LogicType type) {
		type.getHandler().cast(getInstance());
	}

	@SuppressWarnings("rawtypes")
	public LogicConstructorHandler(LogicType type, Object[][] signers) {
		type.getHandler().cast(getInstance());
		for (int i = 0; i < signers.length; i++) {
			String signer = ((Class) signers[i][0]).getName();
			type.getHandler().register(signer, getInstance(), signers[i][1]);
		}
	}
	
	public abstract Object getInstance();

}