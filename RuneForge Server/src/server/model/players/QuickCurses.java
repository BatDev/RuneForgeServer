package server.model.players;

public class QuickCurses {

	/**
	 * @author I'm A Jerk
	 */

	private Client c;
	public QuickCurses(Client c) {
		this.c = c;
	}

	public static final int MAX_CURSES = 20;
	
	public void clickCurse(int actionId) {
		canBeSelected(actionId);
		switch (actionId) {
			case 67050:
				if (!c.quickCurses2[0]) {
					c.quickCurses2[0] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[0] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67051:
				if (!c.quickCurses2[1]) {
					c.quickCurses2[1] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[1] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67052:
				if (!c.quickCurses2[2]) {
					c.quickCurses2[2] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[2] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67053:
				if (!c.quickCurses2[3]) {
					c.quickCurses2[3] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[3] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67054:
				if (!c.quickCurses2[4]) {
					c.quickCurses2[4] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[4] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67055:
				if (!c.quickCurses2[5]) {
					c.quickCurses2[5] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[5] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67056:
				if (!c.quickCurses2[6]) {
					c.quickCurses2[6] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[6] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67057:
				if (!c.quickCurses2[7]) {
					c.quickCurses2[7] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[7] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67058:
				if (!c.quickCurses2[8]) {
					c.quickCurses2[8] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[8] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67059:
				if (!c.quickCurses2[9]) {
					c.quickCurses2[9] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[9] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67060:
				if (!c.quickCurses2[10]) {
					c.quickCurses2[10] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[10] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67061:
				if (!c.quickCurses2[11]) {
					c.quickCurses2[11] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[11] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67062:
				if (!c.quickCurses2[12]) {
					c.quickCurses2[12] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[12] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67063:
				if (!c.quickCurses2[13]) {
					c.quickCurses2[13] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[13] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67064:
				if (!c.quickCurses2[14]) {
					c.quickCurses2[14] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[14] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67065:
				if (!c.quickCurses2[15]) {
					c.quickCurses2[15] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[15] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67066:
				if (!c.quickCurses2[16]) {
					c.quickCurses2[16] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[16] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67067:
				if (!c.quickCurses2[17]) {
					c.quickCurses2[17] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[17] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67068:
				if (!c.quickCurses2[18]) {
					c.quickCurses2[18] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[18] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;

			case 67069:
				if (!c.quickCurses2[19]) {
					c.quickCurses2[19] = true;
					c.sendMessage("You have selected this prayer.");
				} else {
					c.quickCurses2[19] = false;
					c.sendMessage("You have deselected this prayer.");
				}
			break;
		}
	}

	public void canBeSelected(int actionId) {
		switch (actionId) {
			case 67051:
				c.quickCurses2[10] = false;
				c.quickCurses2[19] = false;
			break;

			case 67052:
				c.quickCurses2[11] = false;
				c.quickCurses2[19] = false;
			break;

			case 67053:
				c.quickCurses2[12] = false;
				c.quickCurses2[19] = false;
			break;

			case 67054:
				c.quickCurses2[16] = false;
				break;

			case 67057:
				c.quickCurses2[8] = false;
				c.quickCurses2[9] = false;
				c.quickCurses2[17] = false;
				c.quickCurses2[18] = false;
			break;

			case 67058:
				c.quickCurses2[7] = false;
				c.quickCurses2[9] = false;
				c.quickCurses2[17] = false;
				c.quickCurses2[18] = false;
			break;

			case 67059:
				c.quickCurses2[7] = false;
				c.quickCurses2[8] = false;
				c.quickCurses2[17] = false;
				c.quickCurses2[18] = false;
			break;

			case 67060:
				c.quickCurses2[1] = false;
				c.quickCurses2[19] = false;
			break;

			case 67061:
				c.quickCurses2[2] = false;
				c.quickCurses2[19] = false;
			break;

			case 67062:
				c.quickCurses2[3] = false;
				c.quickCurses2[19] = false;
			break;

			case 67063:
			case 67064:
				c.quickCurses2[19] = false;
			break;

			case 67066:
				c.quickCurses2[4] = false;
			break;

			case 67067:
				for (int i = 7; i < 10; i++)
					c.quickCurses2[i] = false;
					c.quickCurses2[18] = false;
			break;

			case 67068:
				for (int i = 7; i < 10; i++)
					c.quickCurses2[i] = false;
					c.quickCurses2[17] = false;
			break;

			case 67069:
				for (int i = 1; i < 5; i++) {
					for (int j = 10; j < 15; j++) {
						c.quickCurses2[i] = false;
						c.quickCurses2[j] = false;
					}
				}
			break;

		}
	}

	public void turnOnQuicks() {
		if (c.altarPrayed == 0) {
			for (int i = 0; i < c.quickPrayers2.length; i++) {
				if (c.quickPrayers2[i] && !c.prayerActive[i]){
					c.quickPray = true;
					c.getCombat().activatePrayer(i);
				}
				if (!c.quickPrayers2[i]) {
					c.prayerActive[i] = false;
					c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
					c.getPA().requestUpdates();
				}
			}			
		} else {
			for (int i = 0; i < c.quickCurses2.length; i++) {
				if (c.quickCurses2[i] && !c.curseActive[i]){
					c.quickCurse = true;
					c.getCurse().activateCurse(i);
				}
				if (!c.quickCurses2[i]) {
					c.curseActive[i] = false;
					c.getPA().sendFrame36(c.CURSE_GLOW[i], 0);
					c.getPA().requestUpdates();
				}
			}
		}
	}
	
	public final String[] CURSE_NAMES = {
		"Protect Item", "Sap Warrior", "Sap Ranger", "Sap Mage", "Sap Spirit",
		"Berserker", "Deflect Summoning", "Deflect Magic","Deflect Missiles", "Deflect Melee",
		"Leech Attack", "Leech Ranged", "Leech Magic", "Leech Defence", "Leech Strength",
		"Leech Energy", "Leech Special Attack", "Wrath", "Soul Split", "Turmoil"
	};	

	public void turnOffQuicks() {	
		c.getCombat().resetPrayers();
		c.getCurse().resetCurse();
		c.quickPray = false;
		c.quickCurse = false;
		c.headIcon = -1;
		c.getPA().requestUpdates();
	}

	public void selectQuickInterface() {
		if (c.altarPrayed == 0) {
			c.setSidebarInterface(5, 17234);
		//} else {
			//c.setSidebarInterface(5, 17234);
		}
	}

	public void clickConfirm() {
		if(c.altarPrayed == 0) {
			//c.setSidebarInterface(5, 5608);
		//} else {
			c.setSidebarInterface(5, 22500);
		}
	}
}
