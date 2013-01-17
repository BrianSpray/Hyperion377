package org.hyperion.rs2.content.defintion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.hyperion.rs2.content.skill.Herblore.Potions;
import org.hyperion.rs2.handler.LogicConstructorHandler;
import org.hyperion.rs2.handler.LogicType;
import org.hyperion.rs2.handler.impl.InitiationHandler;
import org.hyperion.rs2.handler.impl.ItemOnItemHandler;
import org.hyperion.rs2.model.ItemDefinition;

public final class WeaponDefinition {
	
	private static final Logger logger = Logger.getLogger(WeaponDefinition.class.getName());
	
	private static Map<Integer, WeaponDefinition> definitions = new HashMap<Integer, WeaponDefinition>();

	public int itemId, weaponInterfaceId;

	public int attackSpeed;

	public int walkAnimation;

	public int runAnimation;

	public int attackAnimation;

	public int blockAnimation;

	public int soundEffect;

	public int poisonType;

	private boolean hasSpecialAttack;
	
	public WeaponDefinition(int itemId, int weaponInterfaceId, int attackSpeed, int walkAnimation, int runAnimation, int attackAnimation, int blockAnimation, int soundEffect, boolean hasSpecialAttack, int poisonType) {
		this.itemId = itemId;
		this.weaponInterfaceId = weaponInterfaceId;
		this.attackSpeed = attackSpeed;
		this.walkAnimation = walkAnimation;
		this.runAnimation = runAnimation;
		this.attackAnimation = attackAnimation;
		this.blockAnimation = blockAnimation;
		this.soundEffect = soundEffect;
		this.hasSpecialAttack = hasSpecialAttack;
		this.poisonType = poisonType;
	}
	
	
	public static WeaponDefinition forId(int itemId) {
		return definitions.get(itemId);
	}

	public static class Loader extends LogicConstructorHandler implements InitiationHandler {

		public Loader() {
			super(LogicType.DEFAULT,
				new Object[][] {
					{WeaponDefinition.class}
				}
			);
		}

		@Override
		public void init() throws Throwable {
			logger.info("Loading weapon definitions....");
			File file = new File("./data/WeaponDefinitions.txt");
			if (file.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				for (int i = 0; i < 3; i++) {
					int itemId = Integer.parseInt(reader.readLine().substring(11));
					int weaponInterfaceId = Integer.parseInt(reader.readLine().substring(23));
					int attackSpeed = Integer.parseInt(reader.readLine().substring(16));
					int walkAnimation = Integer.parseInt(reader.readLine().substring(18));
					int runAnimation = Integer.parseInt(reader.readLine().substring(17));
					int attackAnimation = Integer.parseInt(reader.readLine().substring(20));
					int blockAnimation = Integer.parseInt(reader.readLine().substring(19));
					int soundEffect = Integer.parseInt(reader.readLine().substring(16));
					boolean hasSpecialAttack = reader.readLine().substring(22).equalsIgnoreCase("false") ? false : true;
					int poisonType = Integer.parseInt(reader.readLine().substring(15));			
					reader.readLine();
					
					definitions.put(itemId, new WeaponDefinition(itemId, weaponInterfaceId, attackSpeed, walkAnimation, runAnimation, attackAnimation, blockAnimation, soundEffect, hasSpecialAttack, poisonType));
				}
				reader.close();
			}
		}

		@Override
		public Object getInstance() {
			return this;
		}
	}
}
