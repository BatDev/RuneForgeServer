package server.model.players.skills;

import server.model.players.*;
import server.Config;
import server.util.Misc;

/**
* @Author Sanity : Highly Edited by Exorth`
*/

public class Woodcutting {
	
	Client c;
	
	private final int VALID_AXE[] = {1351,1349,1353,1361,1355,1357,1359,6739};
	private final int[] AXE_REQS = {1,1,6,6,21,31,41,61};
	private int 
		logType,
		exp,
		wcanim = 0;
	
	public Woodcutting(Client c) {
		this.c = c;
	}
	
	public void startWoodcutting(int logType, int levelReq, int exp) {
		if (goodAxe() > 0) {
			c.turnPlayerTo(c.objectX, c.objectY);
			if (c.playerLevel[c.playerWoodcutting] >= levelReq) {
				for (int id : VALID_AXE) {
					if(id == c.playerEquipment[c.playerWeapon] && canwithaxe(c.playerEquipment[c.playerWeapon], c) || c.getItems().playerHasItem(id, 1) && canwithaxe(id, c)) {
							if (id == 1351) {
								wcanim = 879;
							}
							if (id == 1349) {
								wcanim = 877;
							}
							if (id == 1353) {
								wcanim = 875;
							}
							if (id == 1361) {
								wcanim = 873;
							}
							if (id == 1355) {
								wcanim = 871;
							}
							if (id == 1357) {
								wcanim = 869;
							}
							if (id == 1359) {
								wcanim = 867;
							}
							if (id == 6739) {
								wcanim = 2846;
							}
						}
					}
				this.logType = logType;
				this.exp = exp;
				goodAxe();
				c.wcTimer = getWcTimer();
				c.startAnimation(wcanim);
			} else {
				c.getPA().resetVariables();
				c.startAnimation(65535);
				c.sendMessage("You need a woodcutting level of " + levelReq + " to cut this tree.");
			}		
		} else {
			c.startAnimation(65535);
			c.sendMessage("You need an axe to cut this tree.");
			c.getPA().resetVariables();
		}
	}
	
	public void resetWoodcut() {
		this.logType = -1;
		this.exp = -1;
		c.wcTimer = -1;	
	}
	
	public void cutWood() {
		if (c.getItems().addItem(logType,1)) {
			c.startAnimation(wcanim);
			c.sendMessage("You get some logs.");
			c.getPA().addSkillXP(exp * Config.WOODCUTTING_EXPERIENCE, c.playerWoodcutting);
			c.getPA().refreshSkill(c.playerWoodcutting);
			c.wcTimer = getWcTimer();
		} else {
			c.getPA().resetVariables();
		}
	}
	
	public int goodAxe() {
		for (int j = VALID_AXE.length - 1; j >= 0; j--) {
			if (c.playerEquipment[c.playerWeapon] == VALID_AXE[j]) {
				if (c.playerLevel[c.playerWoodcutting] >= AXE_REQS[j])
					return VALID_AXE[j];
			}		
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			for (int j = VALID_AXE.length - 1; j >= 0; j--) {
				if (c.playerItems[i] == VALID_AXE[j] + 1) {
					if (c.playerLevel[c.playerWoodcutting] >= AXE_REQS[j])
						return VALID_AXE[j];
				}
			}		
		}
		return - 1;
	}

	private boolean canwithaxe(int i, Client c) {
		switch (i) {
		case 1351:
		case 1349:
			if (c.playerLevel[8] >= 1)
				return true;
			break;
		case 1353:
			if (c.playerLevel[8] >= 6)
				return true;
			break;
		case 1361:
			if (c.playerLevel[8] >= 10)
				return true;
			break;
		case 1355:
			if (c.playerLevel[8] >= 21)
				return true;
			break;
		case 1357:
			if (c.playerLevel[8] >= 31)
				return true;
			break;
		case 1359:
			if (c.playerLevel[8] >= 41)
				return true;
		case 6739:
			if (c.playerLevel[8] >= 61)
				return true;
		break;
		default:
			return false;
			
		}
		return false;
	}
	
	public int getWcTimer() {
		int time = Misc.random(5);
		return time;
	}

}