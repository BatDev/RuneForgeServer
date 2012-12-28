package server.model.players;

import server.util.Misc;

/**
 * @author I'm A Jerk
 */

public class SummonAddOn {
	
	
	private Client c;
	public SummonAddOn (Client c) {
		this.c = c;	
	}
	
	public void handleSummonScrolls(int lastsummon) {
		if (c.lastsummon < 0) {
			c.sendMessage("You don't have an npc summoned.");
			return;
		}
		if(System.currentTimeMillis() - c.lastScroll < 2500) {
			c.sendMessage("Your summon has to cool down.");
			return;
		}
		if(System.currentTimeMillis() - c.lastScroll >= 2500) {
			switch (c.lastsummon) {
				case 6874: //pack yak
					if (c.getItems().playerHasItem(12435, 1)) {
						c.sendMessage("Use the scroll with an item in your inventory to send it to the bank.");
					} else {
						c.sendMessage("You don't have a summon scroll!");
					}
				break;
				
				case 6870: //wolpertinger
					if(c.getItems().playerHasItem(12437, 1)) {
						c.getItems().deleteItem(12437, 1);
						c.gfx0(1311);
						c.startAnimation(7660);
						if(c.playerLevel[6] > c.getLevelForXP(c.playerXP[6])) {
							c.playerLevel[6] = c.getLevelForXP(c.playerXP[6]);
						} else {
							c.playerLevel[6] += (c.getLevelForXP(c.playerXP[6]) * .1);
							c.getPA().refreshSkill(6);
							c.sendMessage("Your Magic bonus has increased!");
						}
					} else {
						c.sendMessage("You don't have a summon scroll!");
					}
				break;
				
				case 6823: //unicorn
				if (c.summAmount >= 20) {
					if (c.getItems().playerHasItem(12434, 1)) {
						c.getItems().deleteItem2(12434, 1);
						int random = Misc.random(6);
							switch (random) {
								case 0:
									c.startAnimation(7660);
									c.gfx0(1300);
								break;
								case 1:
									c.startAnimation(7660);
									c.gfx0(1296);
								break;
								case 2:
									c.startAnimation(7660);
									c.gfx0(1310);
								break;
								case 3:
									c.startAnimation(7660);
									c.gfx0(1307);
								break;
								case 4:
									c.startAnimation(7660);
									c.gfx0(1308);
								break;
								case 5:
									c.startAnimation(7660);
									c.gfx0(1309);
								break;
								}
							c.playerLevel[3] += (c.getLevelForXP(c.playerXP[3]) * .15);
						if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
							c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
							c.getPA().refreshSkill(3);
							c.summAmount -= 20;
							c.getPA().sendFrame126("Summoning Special: "+(int)c.summAmount+"/60", 17040);
							c.getPA().refreshSkill(1);
							c.sendMessage("You heal yourself...and feel slightly rejuvenated");
						} else {
							c.sendMessage("You don't have a summon scroll!");
						}
					} else {
						c.sendMessage("You don't have enough summon special.");
					}
				break;
				
				case 7354:
					if (c.summAmount < 20) {
						c.sendMessage("You don't have enough summon special.");
						return;
					}
					if (c.getItems().playerHasItem(12434, 1)) {
						c.getItems().deleteItem2(12434, 1);
						c.playerLevel[3] += (c.getLevelForXP(c.playerXP[3]) + 8);
						c.playerLevel[1] += (c.getLevelForXP(c.playerXP[1]) * .125);
						c.getPA().refreshSkill(3);
						c.getPA().refreshSkill(1);
						c.sendMessage("You boost your Defence and HP.");
						if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]) + 8) {
							c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
							c.getPA().refreshSkill(3);
						}
						if (c.playerLevel[1] > c.getLevelForXP(c.playerXP[1])) {
							c.playerLevel[1] = c.getLevelForXP(c.playerXP[1]);
							c.getPA().refreshSkill(1);
						}
					} else {
						c.sendMessage("You don't have a summon scroll!");
					}
				break;
			}
		}
		c.lastScroll = System.currentTimeMillis();
	}
	

}