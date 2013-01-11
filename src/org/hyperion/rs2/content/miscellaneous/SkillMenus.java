package org.hyperion.rs2.content.miscellaneous;

import org.hyperion.rs2.handler.LogicConstructorHandler;
import org.hyperion.rs2.handler.LogicHandler;
import org.hyperion.rs2.handler.LogicType;
import org.hyperion.rs2.handler.impl.ButtonHandler;
import org.hyperion.rs2.model.Item;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.net.ActionSender;

public class SkillMenus extends LogicConstructorHandler implements ButtonHandler {

	public SkillMenus() {
		super(LogicType.DEFAULT,
			new Object[][] {
				{ButtonHandler.class, new int[] {8654, 8655, 8657, 8660, 8663}}
			}
		);
	}
	
	private static String[] MENU_NAMES = {"Combat", "Ranged", "Prayer", "Magic"};
	
	private static String[][][][] REQUIRED_LEVELS = {
		{
			{ // Attack
				{"1", "Bronze."}, {"1", "Iron."}, {"5", "Steel."}, {"10", "Black."}, {"20", "Mithril."}, {"30", "Adamant."}, {"40", "Rune."}, {"60", "Dragon."}
			}, { // Defence
				{"1", "Bronze."}, {"1", "Iron."}, {"5", "Steel."}, {"10", "Black."}, {"20", "Mithril."}, {"30", "Adamant."}, {"40", "Rune."}, {"50", "Granite."}, {"60", "Dragon."}, {"70", "Crystal."}
			}
		}	
	};
	
	private static int[] MENU_IDS = {0, 1, 2, 3, 4};
	
	private static int[] NUMBER_OF_TABS = {2, 5};
	
	private static Item[][][] MENU_ITEMS = {
		{
			{ // Attack
				new Item(1205), new Item(1203), new Item(1207), new Item(1217), new Item(1209), new Item(1211), new Item(1213), new Item(1215)
			}, { // Defence
				new Item(1139), new Item(1137), new Item(1141), new Item(1151), new Item(1143), new Item(1145), new Item(1147), new Item(3122), new Item(1149), new Item(4211)
			} 
		}
	};
	
	public static void clearMenuInformation(Player player) {
		hideTabs(player, -1);
		for(int i = 0; i < 39; i++) {
			ActionSender.sendString(player, 8720 + i, "");
			ActionSender.sendString(player, 8760 + i, "");
		}
	}
	
	public static void hideTabs(Player player, int numberOfTabs) {
		//ActionSender.sendInterfaceConfig(player, 8813, true);
		if (numberOfTabs == 2) {
			ActionSender.sendInterfaceConfig(player, 8825, true);
			ActionSender.sendInterfaceConfig(player, 8828, true);
			ActionSender.sendInterfaceConfig(player, 8838, true);
			ActionSender.sendInterfaceConfig(player, 8841, true);
			ActionSender.sendInterfaceConfig(player, 8844, true);
			ActionSender.sendInterfaceConfig(player, 8850, true);
			ActionSender.sendInterfaceConfig(player, 8860, true);
			ActionSender.sendInterfaceConfig(player, 8863, true);
			ActionSender.sendInterfaceConfig(player, 15294, true);
			ActionSender.sendInterfaceConfig(player, 15304, true);
			ActionSender.sendInterfaceConfig(player, 15307, true);
		} else {
			ActionSender.sendInterfaceConfig(player, 15307, true);
		}
	}
	
	public static void displaySkillMenu(Player player, int menuId) {
		ActionSender.sendString(player, 8716, "@dre@" + MENU_NAMES[MENU_IDS[menuId]]);
		for(int i = 0; i < REQUIRED_LEVELS[0][MENU_IDS[menuId]].length; i++) {
			String levelString = REQUIRED_LEVELS[0][MENU_IDS[menuId]][i][0];
			String descriptionString = REQUIRED_LEVELS[0][MENU_IDS[menuId]][i][1];
			ActionSender.sendString(player, 8720 + i, levelString);
			ActionSender.sendString(player, 8760 + i, descriptionString);
		}

		ActionSender.sendUpdateItems(player, 8847, MENU_ITEMS[0][MENU_IDS[menuId]]);
		ActionSender.sendInterface(player, 8714);
		hideTabs(player, NUMBER_OF_TABS[menuId]);
	}
	

	@Override
	public boolean handleButton(Player player, int packetId, int buttonId) throws Throwable {
		switch (buttonId) {
			case 8654:
			case 8655:
			case 8657:
			case 8660:
				clearMenuInformation(player);
				displaySkillMenu(player, 0);
				return true;
			case 8663:
				clearMenuInformation(player);
				displaySkillMenu(player, 1);
				return true;
		}
		return false;
	}

	@Override
	public Object getInstance() {
		return this;
	}

}
