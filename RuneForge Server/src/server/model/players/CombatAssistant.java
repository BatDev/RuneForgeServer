package server.model.players;

import server.Config;
import server.Server;
import server.util.Misc;
import server.model.players.Client;
import server.model.players.Player;
import server.event.EventManager;
import server.model.npcs.NPC;
import server.model.npcs.NPCHandler;
import server.event.EventContainer;
import server.event.Event;
import java.util.Random;


public class CombatAssistant{

	private Client c;
	public CombatAssistant(Client Client) {
		this.c = Client;
	}
	
	private final Random random = new Random();
	
	public int[][] slayerReqs = {{1648,5},{1612,15},{1643,45},{1618,50},{1624,65},{1610,75},{1613,80},{1615,85},{2783,90},{3590,95},{3592,85},{3591,85},{5666,96},{1472,90}};
	
	public boolean goodSlayer(int i) {
		for (int j = 0; j < slayerReqs.length; j++) {
			if (slayerReqs[j][0] == NPCHandler.npcs[i].npcType) {
				if (slayerReqs[j][1] > c.playerLevel[c.playerSlayer]) {
					c.sendMessage("You need a slayer level of " + slayerReqs[j][1] + " to harm this NPC.");
					return false;
				}
			}
		}
		return true;
	}
	
	public int getHitType(boolean range, boolean magic) {
		if(range)
			return 2;
		if(magic)
			return 3;
		return 1;
	}
	
	/*
	*Multi Barrage
	*/
	public void multiSpellOnNPC(int originalNpc) {
		//c.PlayerAttType = 2;
		if (!multis() || !c.inMulti())
			return;
		NPC o2 = (NPC) NPCHandler.npcs[originalNpc];
		int origX = o2.absX;
		int origY = o2.absY;
		for (int indx = 0; indx < NPCHandler.maxNPCs; indx++) {
			if (NPCHandler.npcs[indx] != null) {
				NPC o = (NPC) NPCHandler.npcs[indx];
				if (!(o.absX >= origX - 2 && o.absX <= origX + 2
						&& o.absY >= origY - 2 && o.absY <= origY + 2)
						|| !o.inMulti())
					continue;
				if (!checkNpcReq(indx) || indx == originalNpc)
					continue;
				int bonusAttack = getBonusAttack(indx);
				if (Misc.random(NPCHandler.npcs[indx].defence) > 10
						+ Misc.random(mageAtk()) + bonusAttack) {
					if (getEndGfxHeight() == 100) { // end GFX
						o.gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
					} else {
						o.gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
					}
					int damage = Misc.random(finalMagicDamage(c));
					c.getPA().addSkillXP(
							(c.MAGIC_SPELLS[c.oldSpellId][7] + damage
									* Config.MAGIC_EXP_RATE), 6);
					c.getPA().addSkillXP(
							(c.MAGIC_SPELLS[c.oldSpellId][7] + damage
									* Config.MAGIC_EXP_RATE / 3), 3);
					spellEffects(damage, indx);
					setColorMasks(damage, finalMagicDamage(c), 1);
					if (damage > -1) {
						NPCHandler.npcs[indx].hitDiff = damage;
						NPCHandler.npcs[indx].HP -= damage;
						NPCHandler.npcs[indx].hitUpdateRequired = true;
						c.totalDamageDealt += damage;
					}
				} else {
					int damage = 0;
					o.gfx100(85);
					NPCHandler.npcs[indx].hitDiff = damage;
					NPCHandler.npcs[indx].hitUpdateRequired = true;
					c.totalDamageDealt += damage;
				}
			}
		}
	}

	public void multiSpellOnPlayer(int originalPlayer) {
		if (!multis() || !c.inMulti())
			return;
		//c.PlayerAttType = 2;
		Client o2 = (Client) PlayerHandler.players[originalPlayer];
		int origX = o2.absX;
		int origY = o2.absY;
		for (int indx = 0; indx < Config.MAX_PLAYERS; indx++) {
			if (PlayerHandler.players[indx] != null) {
				Client o = (Client) PlayerHandler.players[indx];
				if (!(o.absX >= origX - 2 && o.absX <= origX + 2
						&& o.absY >= origY - 2 && o.absY <= origY + 2)
						|| !o.inMulti())
					continue;
				if (!checkPlayerReq(indx) || indx == originalPlayer
						|| indx == c.playerId)
					continue;
				if (Misc.random(o.getCombat().mageDef()) > Misc
						.random(mageAtk())) {
					if (getEndGfxHeight() == 100) { // end GFX
						o.gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
					} else {
						o.gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
					}
					int damage = Misc.random(finalMagicDamage(c));
					if(damage > 20) { // Soak Mage
						o.soaked = (int) (damage * getBonus(o, ABSORB_MAGIC_BONUS));
						damage -= o.soaked;
					}
					c.getPA().addSkillXP(
							(c.MAGIC_SPELLS[c.oldSpellId][7] + damage
									* Config.MAGIC_EXP_RATE), 6);
					c.getPA().addSkillXP(
							(c.MAGIC_SPELLS[c.oldSpellId][7] + damage
									* Config.MAGIC_EXP_RATE / 3), 3);
					spellEffects(damage, indx);
					hitPlayer(indx, damage);
				} else {
					o.gfx100(85);
					hitPlayer(indx, 0);
				}
			}
		}
	}
	
	public void spellEffects(int damage, int indx) {
		switch (c.MAGIC_SPELLS[c.oldSpellId][0]) {
		case 12901:
		case 12919:
		case 12911:
		case 12929:
			int heal = Misc.random(damage / 2);
			if (c.playerLevel[3] + heal >= c.getPA().getLevelForXP(
					c.playerXP[3])) {
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			} else {
				c.playerLevel[3] += heal;
			}
			c.getPA().refreshSkill(3);
			break;
		}
		int freezeDelay = getFreezeTime();
		if (c.playerIndex > 0) {
			if(freezeDelay > 0 && PlayerHandler.players[indx].freezeTimer == 0)
				PlayerHandler.players[indx].freezeTimer = freezeDelay;
		} else if (c.npcIndex > 0) {
			if(freezeDelay > 0 && NPCHandler.npcs[indx].freezeTimer == 0)
				NPCHandler.npcs[indx].freezeTimer = freezeDelay;
		}
	}
	
	public void hitPlayer(int damage, int i) {
		Client o = (Client) PlayerHandler.players[i];
		if(o == null)
			return;
		o.setHitDiff(damage);
		o.setHitUpdateRequired(true);
		o.dealDamage(damage);
		o.updateRequired = true;
		o.underAttackBy = c.playerId;
		o.logoutDelay = System.currentTimeMillis();
		o.singleCombatDelay = System.currentTimeMillis();
	}

	public boolean checkNpcReq(int i) {
		if (NPCHandler.npcs[i].isDead || NPCHandler.npcs[i].MaxHP <= 0) {
			c.usingMagic = false;
			c.faceUpdate(0);
			c.npcIndex = 0;
			return false;
		}			
		if(c.respawnTimer > 0) {
			c.npcIndex = 0;
			return false;
		}
		if (NPCHandler.npcs[i].underAttackBy > 0 && NPCHandler.npcs[i].underAttackBy != c.playerId && !NPCHandler.npcs[i].inMulti()) {
			c.npcIndex = 0;
			c.sendMessage("This monster is already in combat.");
			return false;
		}
		if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.underAttackBy2 != i && !c.inMulti()) {
			resetPlayerAttack();
			c.sendMessage("I am already under attack.");
			return false;
		}
		if (!goodSlayer(i)) {
			resetPlayerAttack();
			return false;
		}

		if (NPCHandler.npcs[i].npcType == 6258) {
			if (92 > c.getLevelForXP(c.playerXP[c.playerPrayer])) {
				c.sendMessage("You need a prayer level of 92 to harm this NPC.");
				resetPlayerAttack();
				return false;
			}
		}
		if (NPCHandler.npcs[i].spawnedBy != c.playerId && NPCHandler.npcs[i].spawnedBy > 0) {
			resetPlayerAttack();
			c.sendMessage("This monster was not spawned for you.");
			return false;
		}
		return true;
	}

	public boolean checkPlayerReq(int i) {
		if (PlayerHandler.players[i].isDead) {
			resetPlayerAttack();
			return false;
		}
		
		if(c.respawnTimer > 0 || PlayerHandler.players[i].respawnTimer > 0) {
			resetPlayerAttack();
			return false;
		}
		
		/*if (c.teleTimer > 0 || Server.playerHandler.players[i].teleTimer > 0) {
			resetPlayerAttack();
			return;
		}*/
		
		if(!c.getCombat().checkReqs()) {
			return false;
		}
		
		if (c.getPA().getWearingAmount() < 4 && c.duelStatus < 1) {
			c.sendMessage("You must be wearing at least 4 items to attack someone.");
			resetPlayerAttack();
			return false;
		}
		boolean sameSpot = c.absX == PlayerHandler.players[i].getX() && c.absY == PlayerHandler.players[i].getY();
		if(!c.goodDistance(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), c.getX(), c.getY(), 25) && !sameSpot) {
			resetPlayerAttack();
			return false;
		}
		if(PlayerHandler.players[i].respawnTimer > 0) {
			PlayerHandler.players[i].playerIndex = 0;
			resetPlayerAttack();
			return false;
		}
		
		if (PlayerHandler.players[i].heightLevel != c.heightLevel) {
			resetPlayerAttack();
			return false;
		}
		return true;
	}
	
	
	/**
	* Attack Npcs
	*/
	public void attackNpc(int i) {	
		if (NPCHandler.npcs[i] != null) {
			if (NPCHandler.npcs[i].isDead || NPCHandler.npcs[i].MaxHP <= 0) {
				c.usingMagic = false;
				c.faceUpdate(0);
				c.npcIndex = 0;
				return;
			}

			if (c.playerEquipment[c.playerWeapon] == 15241) {
				c.gfx0(2138);
			}
			if(c.playerEquipment[c.playerWeapon] == 15241 && 15243 != c.playerEquipment[c.playerArrows]){
                c.sendMessage("You can't use Hand Cannon without the shots!");
                return;
			}

			if (NPCHandler.npcs[i].summon == true) {
			if(NPCHandler.npcs[i].index != c.playerId || c.wildLevel == 0) {
				resetPlayerAttack();
				c.sendMessage("You cannot attack this monster.");

			//c.sendMessage("This is "+Server.npcHandler.npcz[i]+" NPC.");
				return;
			}
}

	
		if(c.specEffect == 4) {
		c.specEffect = 0;
		}
		if (c.inWG()) {
			resetPlayerAttack();
			c.stopMovement();
			return;
              	  }
			if (NPCHandler.npcs[i].npcType == 1000 || NPCHandler.npcs[i].npcType == 1001 || NPCHandler.npcs[i].npcType == 3100 || NPCHandler.npcs[i].npcType == 1002 || NPCHandler.npcs[i].npcType == 1003) {
			resetPlayerAttack();
			c.stopMovement();
			return; // Bandos
              	  }

			if (NPCHandler.npcs[i].npcType == 3104) {
			c.sendMessage("You must click the gate before attacking the Corporeal Beast!");
			resetPlayerAttack();
			c.stopMovement();
			return; // Corporeal beast *fake*
              	  }

			if (NPCHandler.npcs[i].npcType == 1004 || NPCHandler.npcs[i].npcType == 1005 || NPCHandler.npcs[i].npcType == 1006 || NPCHandler.npcs[i].npcType == 1007) {
			resetPlayerAttack();
			c.stopMovement();
			return; // Arma
              	  }
			if (NPCHandler.npcs[i].npcType == 1008 || NPCHandler.npcs[i].npcType == 1009 || NPCHandler.npcs[i].npcType == 1010 || NPCHandler.npcs[i].npcType == 1011) {
			resetPlayerAttack();
			c.stopMovement();
			return; // Zammy
              	  }
			if (NPCHandler.npcs[i].npcType == 1012 || NPCHandler.npcs[i].npcType == 1013 || NPCHandler.npcs[i].npcType == 1014 || NPCHandler.npcs[i].npcType == 1015) {
			resetPlayerAttack();
			c.stopMovement();
			return; // Sara
              	  }
			if(c.respawnTimer > 0) {
				c.npcIndex = 0;
				return;
			}
			if (!goodSlayer(i)) {
				resetPlayerAttack();
				return;
			}
			if (NPCHandler.npcs[i].spawnedBy != c.playerId && NPCHandler.npcs[i].spawnedBy > 0 && NPCHandler.npcs[i].summon != true) {
				resetPlayerAttack();
				c.sendMessage("This monster was not spawned for you.");
				return;
			}
			if(NPCHandler.npcs[i].summoner == true) {
				c.sendMessage("You cannot attack your own npc idiot.");
				resetPlayerAttack();
				return;
			}
			if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.underAttackBy2 != i && !c.inMulti()) {
				resetPlayerAttack();
				c.sendMessage("I am already under attack.");
				return;
			}
			if (NPCHandler.npcs[i].underAttackBy > 0 && NPCHandler.npcs[i].underAttackBy != c.playerId && !NPCHandler.npcs[i].inMulti()) {
				c.npcIndex = 0;
				c.sendMessage("This monster is already in combat.");
				return;
			}
			if (NPCHandler.npcs[i].inMulti() && c.lastsummon > 0) {
				Server.npcHandler.attackNPC(i, c.summoningnpcid);
			}



			if (!goodSlayer(i)) {
				resetPlayerAttack();
				return;
			}
			if (NPCHandler.npcs[i].spawnedBy != c.playerId && NPCHandler.npcs[i].spawnedBy > 0) {
				resetPlayerAttack();
				c.sendMessage("This monster was not spawned for you.");
				return;
			}
			if(c.attackTimer <= 0) {
				boolean usingBow = false;
				boolean usingArrows = false;
				boolean usingOtherRangeWeapons = false;
				boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 18357;
				c.bonusAttack = 0;
				c.rangeItemUsed = 0;
				c.projectileStage = 0;
				//c.SaveGame();
				if (c.autocasting) {
					c.spellId = c.autocastId;
					c.usingMagic = true;
				}
				if(c.spellId > 0) {
				c.usingMagic = true;
				}
				c.attackTimer = getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.specAccuracy = 1.0;
				c.specDamage = 1.0;
				if(!c.usingMagic) {
					for (int bowId : Player.BOWS) {
						if(c.playerEquipment[c.playerWeapon] == bowId) {
							usingBow = true;
							for (int arrowId : Player.ARROWS) {
								if(c.playerEquipment[c.playerArrows] == arrowId) {
									usingArrows = true;
					c.gfx100(getRangeStartGFX());
								}
							}
						}
					}
					
					for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
						if(c.playerEquipment[c.playerWeapon] == otherRangeId) {
							usingOtherRangeWeapons = true;
						}
					}
				}
				
				c.typeOfDamageMask = getHitType(usingBow || usingOtherRangeWeapons || usingCross, c.usingMagic);
				c.typeOfDamageMask2 = getHitType(usingBow || usingOtherRangeWeapons || usingCross, c.usingMagic);
				
				if (armaNpc(i) && !usingCross && !usingBow && !c.usingMagic && !usingCrystalBow() && !usingOtherRangeWeapons) {				
					resetPlayerAttack();
					c.sendMessage("You can only Range/Mage Armadyl creeps!");
					return;
				}
				if((!c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 2) && (usingHally() && !usingOtherRangeWeapons && !usingBow && !usingCross && !c.usingMagic)) ||(!c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 4) && (usingOtherRangeWeapons && !usingCross && !usingBow && !c.usingMagic)) || (!c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 1) && (!usingOtherRangeWeapons && !usingHally() && !usingCross && !usingBow && !c.usingMagic)) || ((!c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 8) && (usingBow || usingCross	|| c.usingMagic)))) {
					c.attackTimer = 2;
					return;
				}
				
				if(!usingCross && !usingArrows && usingBow && (c.playerEquipment[c.playerWeapon] < 4212 || c.playerEquipment[c.playerWeapon] > 4223)) {
					if(c.playerEquipment[c.playerWeapon] == 15241)
						c.sendMessage("You have run out of Shots!");
					else
						c.sendMessage("You have run out of arrows!");
						c.stopMovement();
						c.npcIndex = 0;
						return;
				}
				
				if(usingBow || usingCross || c.usingMagic || usingOtherRangeWeapons || (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 2) && usingHally())) {
					c.stopMovement();
				}

				if(!checkMagicReqs(c.spellId)) {
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}
				
				c.faceUpdate(i);
								c.slayerHelmetEffect = c.playerEquipment[c.playerHat] == 15492 && c.slayerTask == i;
				//c.specAccuracy = 1.0;
				//c.specDamage = 1.0;
				NPCHandler.npcs[i].underAttackBy = c.playerId;
				NPCHandler.npcs[i].lastDamageTaken = System.currentTimeMillis();
				if(c.usingSpecial && !c.usingMagic) {
					if(checkSpecAmount(c.playerEquipment[c.playerWeapon])){
						c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
						c.lastArrowUsed = c.playerEquipment[c.playerArrows];
						activateSpecial(c.playerEquipment[c.playerWeapon], i);
						return;
					} else {
						c.sendMessage("You don't have the required special energy to use this attack.");
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						c.npcIndex = 0;
						return;
					}
				}
				if(usingBow || c.usingMagic || usingOtherRangeWeapons || c.playerEquipment[c.playerWeapon] == 15241) {
					c.mageFollow = true;
				} else {
					c.mageFollow = false;
				}
				c.specMaxHitIncrease = 0;
				if(!c.usingMagic) {
					c.startAnimation(getWepAnim(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()));
				} else {
					c.startAnimation(c.MAGIC_SPELLS[c.spellId][2]);
				}
				c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
				c.lastArrowUsed = c.playerEquipment[c.playerArrows];
				if(!usingBow && !usingCross && !c.usingMagic && !usingOtherRangeWeapons) { // melee hit delay
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 0;
					c.oldNpcIndex = i;
				}
				
				if(usingBow && !usingOtherRangeWeapons && !c.usingMagic || usingCross || c.playerEquipment[c.playerWeapon] == 15241) { // range hit delay					
					if (usingCross)
						c.usingBow = true;
					if (c.fightMode == 2)
						c.attackTimer--;
					c.lastArrowUsed = c.playerEquipment[c.playerArrows];
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.gfx100(getRangeStartGFX());	
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldNpcIndex = i;
					if(c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223) {
						c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
						//c.crystalBowArrowCount++;
						c.lastArrowUsed = 0;
					} else {
						c.rangeItemUsed = c.playerEquipment[c.playerArrows];
						c.getItems().deleteArrow();	
					}
					fireProjectileNpc();
				}

			if(usingBow && usingCross && c.usingMagic && usingOtherRangeWeapons) {
			c.getPA().followNpc();
			c.stopMovement();
			} else {
			c.followId = 0;
			c.followId2 = i;
			}
							
				
				if(usingOtherRangeWeapons && !c.usingMagic && !usingCross && !usingBow) {	// knives, darts, etc hit delay		
					c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
					c.getItems().deleteEquipment();
					c.gfx100(getRangeStartGFX());
					c.lastArrowUsed = 0;
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldNpcIndex = i;
					if (c.fightMode == 2)
						c.attackTimer--;
					fireProjectileNpc();	
				}

				if(c.usingMagic) {	// magic hit delay
					int pX = c.getX();
					int pY = c.getY();
					int nX = NPCHandler.npcs[i].getX();
					int nY = NPCHandler.npcs[i].getY();
					int offX = (pY - nY)* -1;
					int offY = (pX - nX)* -1;
					c.castingMagic = true;
					c.projectileStage = 2;
					if(c.MAGIC_SPELLS[c.spellId][3] > 0) {
						if(getStartGfxHeight() == 100) {
							c.gfx100(c.MAGIC_SPELLS[c.spellId][3]);
						} else {
							c.gfx0(c.MAGIC_SPELLS[c.spellId][3]);
						}
					}
					if(c.MAGIC_SPELLS[c.spellId][4] > 0) {
						c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 78, c.MAGIC_SPELLS[c.spellId][4], getStartHeight(), getEndHeight(), i + 1, 50);
					}
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.oldNpcIndex = i;
					c.oldSpellId = c.spellId;
					c.spellId = 0;
					if (!c.autocasting)
						c.npcIndex = 0;
				}
					if(c.curseActive[18]) { // SoulSplit GFX's - CAUSES CRASH
					if(c.oldNpcIndex > 0) {
					if(NPCHandler.npcs[c.oldNpcIndex] != null) {
					try {
					if(c.curseActive[18] && !c.prayerActive[23] && c.playerLevel[3] <= 99) {
						int heal = 1;
						if(c.playerLevel[3] + heal >= c.getPA().getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
					}
					final int pX = c.getX();
 					final int pY = c.getY();
					final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
					final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
					final int offX = (pY - nY)* -1;
					final int offY = (pX - nX)* -1;
					c.SSPLIT = true;
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, 50, 2263, 9, 9, c.oldNpcIndex + 1, 24, 0);
					//EventManager.getSingleton().addEvent(new Event() {
					//public void execute(EventContainer b) {
					 //Server.playerHandler.players[c.oldPlayerIndex].gfx0(2264);
					c.SSPLIT = false;
				        //b.stop();
					//}
					//}, 500);
					/*EventManager.getSingleton().addEvent(new Event() { // CAUSES CRASH
					public void execute(EventContainer b) {
					//c.getPA().createPlayersProjectile2(nX, nY, offX, offY, 50, 50, 2263, 9, 9, - c.playerId - 1, 24, 0);
				        b.stop();
					}
					}, 800);*/
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
					
					if(c.crystalBowArrowCount >= 250){
						switch(c.playerEquipment[c.playerWeapon]) {
							
							case 4223: // 1/10 bow
							c.getItems().wearItem(-1, 1, 3);
							c.sendMessage("Your crystal bow has fully degraded.");
							if(!c.getItems().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(c, 4207, c.getX(), c.getY(), 1, c.getId());
							}
							c.crystalBowArrowCount = 0;
							break;
							
							default:
							c.getItems().wearItem(++c.playerEquipment[c.playerWeapon], 1, 3);
							c.sendMessage("Your crystal bow degrades.");
							c.crystalBowArrowCount = 0;
							break;
							
						
						}
					}	
				}
			}
		}
	

	public void delayedHit(int i) { // npc hit delay
		if (NPCHandler.npcs[i] != null) {
			if (NPCHandler.npcs[i].isDead) {
				c.npcIndex = 0;
				return;
			}
			NPCHandler.npcs[i].facePlayer(c.playerId);
			
			if (NPCHandler.npcs[i].underAttackBy > 0 && Server.npcHandler.getsPulled(i)) {
				NPCHandler.npcs[i].killerId = c.playerId;			
			} else if (NPCHandler.npcs[i].underAttackBy < 0 && !Server.npcHandler.getsPulled(i)) {
				NPCHandler.npcs[i].killerId = c.playerId;
			}
			c.lastNpcAttacked = i;
			if(c.projectileStage == 0 && !c.usingMagic && !c.castingMagic) { // melee hit damage
				if (!c.usingClaws)
					applyNpcMeleeDamage(i, 1, Misc.random(calculateMeleeMaxHit()));
				if(c.doubleHit && !c.usingClaws) {
					applyNpcMeleeDamage(i, 2, Misc.random(calculateMeleeMaxHit()));
				}
				if(c.doubleHit && c.usingClaws) {
					c.delayedDamage = c.clawDamage;
					c.delayedDamage2 = c.clawDamage/2;
					applyNpcMeleeDamage(i, 1, c.clawDamage);
					applyNpcMeleeDamage(i, 2, c.clawDamage/2);
				}				
			}

			if(!c.castingMagic && c.projectileStage > 0) { // range hit damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (c.lastWeaponUsed == 14481 || c.lastWeaponUsed == 15704 || c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 15702 || c.lastWeaponUsed == 15701 || c.lastWeaponUsed == 15703 || c.lastWeaponUsed == 14482 || c.lastWeaponUsed == 19143 || c.lastWeaponUsed == 19146 || c.lastWeaponUsed == 19149 || c.bowSpecShot == 1)
					damage2 = Misc.random(rangeMaxHit());
					boolean ignoreDef = false;
				if (Misc.random(5) == 1 && c.lastArrowUsed == 9243 && c.playerEquipment[c.playerWeapon] == 9185) {
					ignoreDef = true;
					NPCHandler.npcs[i].gfx0(758);
				}	
				if (Misc.random(4) == 1 && c.lastArrowUsed == 9245 && c.playerEquipment[c.playerWeapon] == 18357) {
					ignoreDef = true;
					NPCHandler.npcs[i].gfx0(753);
				}					

				
				if(Misc.random(NPCHandler.npcs[i].defence) > Misc.random(10+calculateRangeAttack()) && !ignoreDef) {
					damage = 0;
				} else if (NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2883 || NPCHandler.npcs[i].npcType == 3340 && !ignoreDef) {
					damage = 0;
				}
				
				if (Misc.random(4) == 1 && c.lastArrowUsed == 9242 && damage > 0 && c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 18357) {
					damage = NPCHandler.npcs[i].HP/5;
					//c.handleHitMask(c.playerLevel[3]/10);				
				}
			
				
				
				if (c.lastWeaponUsed == 15701 || c.lastWeaponUsed == 15702 || c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 15703 || c.lastWeaponUsed == 15704 || c.lastWeaponUsed == 19143 || c.lastWeaponUsed == 19146 || c.lastWeaponUsed == 19149 || c.bowSpecShot == 1) {
					if (Misc.random(NPCHandler.npcs[i].defence) > Misc.random(10+calculateRangeAttack()))
						damage2 = 0;
				}
				if (c.dbowSpec) {
					NPCHandler.npcs[i].gfx100(1100);
                                        if(c.dbowDelay == 4)
					if (damage < 8)
						damage = 8;
                                        else if(c.dbowDelay == 1)
					if (damage2 < 8)
						damage2 = 8;
					c.dbowSpec = false;
				}
				if (damage > 0 && Misc.random(5) == 1 && c.lastArrowUsed == 9244 && c.playerEquipment[c.playerWeapon] == 9185) {
					damage *= 1.45;
					NPCHandler.npcs[i].gfx0(756);
				}
				if (damage > 0 && Misc.random(5) == 1 && c.lastArrowUsed == 9245 && c.playerEquipment[c.playerWeapon] == 9185) {
					damage *= 1.55;
					NPCHandler.npcs[i].gfx0(753);
				}
				
				if (NPCHandler.npcs[i].HP - damage < 0) { 
					damage = NPCHandler.npcs[i].HP;
				}
				if (NPCHandler.npcs[i].HP - damage <= 0 && damage2 > 0) {
					damage2 = 0;
				}
				if(c.fightMode == 3) {
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 4); 
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 1);				
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 3);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				} else {
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE), 4); 
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 3);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				}
				if (damage > 0) {
					if (NPCHandler.npcs[i].npcType >= 6142 && NPCHandler.npcs[i].npcType <= 6145) {
						c.pcDamage += damage;					
					}				
				}
				boolean dropArrows = true;
						
				for(int noArrowId : c.NO_ARROW_DROP) {
					if(c.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if(dropArrows) {
					c.getItems().dropArrowNpc();	
				}
				NPCHandler.npcs[i].underAttack = true;
				NPCHandler.npcs[i].hitDiff = damage;
				NPCHandler.npcs[i].HP -= damage;
				if (damage2 > -1) {
					NPCHandler.npcs[i].hitDiff2 = damage2;
					NPCHandler.npcs[i].HP -= damage2;
					c.totalDamageDealt += damage2;	
				}
				if (c.killingNpcIndex != c.oldNpcIndex) {
					c.totalDamageDealt = 0;				
				}
				c.killingNpcIndex = c.oldNpcIndex;
				c.totalDamageDealt += damage;
				NPCHandler.npcs[i].hitUpdateRequired = true;
				if (damage2 > -1)
					NPCHandler.npcs[i].hitUpdateRequired2 = true;
				NPCHandler.npcs[i].updateRequired = true;

			} else if (c.projectileStage > 0) { // magic hit damage
				int damage = Misc.random(finalMagicDamage(c));
				if(godSpells()) {
					if(System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
						damage += Misc.random(10);
					}
				}
				boolean magicFailed = false;
				//c.npcIndex = 0;
				int bonusAttack = getBonusAttack(i);
				if (Misc.random(NPCHandler.npcs[i].defence) > 10+ Misc.random(mageAtk()) + bonusAttack) {
					damage = 0;
					magicFailed = true;
				} else if (NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2882) {
					damage = 0;
					magicFailed = true;
				}
				
				if (NPCHandler.npcs[i].HP - damage < 0) { 
					damage = NPCHandler.npcs[i].HP;
				}
				
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage*Config.MAGIC_EXP_RATE), 6); 
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage*Config.MAGIC_EXP_RATE/3), 3);
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(6);
				if (damage > 0) {
					if (NPCHandler.npcs[i].npcType >= 6142 && NPCHandler.npcs[i].npcType <= 6145) {
						c.pcDamage += damage;					
					}				
				}
				if(getEndGfxHeight() == 100 && !magicFailed){ // end GFX
					NPCHandler.npcs[i].gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (!magicFailed){
					NPCHandler.npcs[i].gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
				}
				multiSpellOnNPC(i);	
				
				if(magicFailed) {	
					NPCHandler.npcs[i].gfx100(85);
				}			
				if(!magicFailed) {
					int freezeDelay = getFreezeTime();//freeze 
					if(freezeDelay > 0 && NPCHandler.npcs[i].freezeTimer == 0) {
						NPCHandler.npcs[i].freezeTimer = freezeDelay;
						NPCHandler.npcs[i].barrageorb = 1;
					}
					switch(c.MAGIC_SPELLS[c.oldSpellId][0]) { 
						case 12871:
							if (NPCHandler.npcs[i].barrageorb == 1) {
								NPCHandler.npcs[i].barrageorb = 0;
							}
						break;

						case 12891:			
							if (NPCHandler.npcs[i].barrageorb != 1) {
								NPCHandler.npcs[i].gfx50(1677);
							}
							if (NPCHandler.npcs[i].barrageorb == 1) {
								NPCHandler.npcs[i].barrageorb = 0;
							}
						break;
						case 12901:
						case 12919: // blood spells
						case 12911:
						case 12929:
						int heal = Misc.random(damage / 2);
						if(c.playerLevel[3] + heal >= c.calculateMaxLifePoints()) {
							c.playerLevel[3] = c.calculateMaxLifePoints();
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
						break;
					}

				}
				NPCHandler.npcs[i].underAttack = true;
				if(finalMagicDamage(c) != 0) {
					NPCHandler.npcs[i].hitDiff = damage;
					NPCHandler.npcs[i].HP -= damage;
					NPCHandler.npcs[i].hitUpdateRequired = true;
					c.totalDamageDealt += damage;
				}
				c.killingNpcIndex = c.oldNpcIndex;			
				NPCHandler.npcs[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				c.oldSpellId = 0;
			}
		}
	
		if(c.bowSpecShot <= 0) {
			c.oldNpcIndex = 0;
			c.projectileStage = 0;
			c.doubleHit = false;
			c.lastWeaponUsed = 0;
			c.bowSpecShot = 0;
		}
		if(c.bowSpecShot >= 2) {
			c.bowSpecShot = 0;
			//c.attackTimer = getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		}
		if(c.bowSpecShot == 1) {
			fireProjectileNpc();
			c.hitDelay = 2;
			c.bowSpecShot = 0;
		}
	}
	
	
	public void applyNpcMeleeDamage(int i, int damageMask, int damage) {
		c.previousDamage = damage;
		boolean fullVeracsEffect = c.getPA().fullVeracs() && Misc.random(3) == 1;
		if (NPCHandler.npcs[i].HP - damage < 0) { 
			damage = NPCHandler.npcs[i].HP;
		}
		
		if (!fullVeracsEffect && !c.usingClaws) {
			if (Misc.random(NPCHandler.npcs[i].defence) > 10 + Misc.random(calculateMeleeAttack())) {
				damage = 0;
			} else if (NPCHandler.npcs[i].npcType == 2882 || NPCHandler.npcs[i].npcType == 2883) {
				damage = 0;
			}
		}	
		boolean guthansEffect = false;
		if (c.getPA().fullGuthans()) {
			if (Misc.random(3) == 1) {
				guthansEffect = true;			
			}		
		}
		if(c.fightMode == 3) {
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 0); 
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 1);
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 2); 				
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 3);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(1);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
		} else {
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE), c.fightMode); 
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 3);
			c.getPA().refreshSkill(c.fightMode);
			c.getPA().refreshSkill(3);
		}
		if (damage > 0) {
			if (NPCHandler.npcs[i].npcType >= 6142 && NPCHandler.npcs[i].npcType <= 6145) {
				c.pcDamage += damage;					
			}				
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			NPCHandler.npcs[i].gfx0(398);		
		}
		NPCHandler.npcs[i].underAttack = true;
		//Server.npcHandler.npcs[i].killerId = c.playerId;
		c.killingNpcIndex = c.npcIndex;
		c.lastNpcAttacked = i;
		switch (c.specEffect) {
			case 4:
				if (damage > 0) {
					if (c.playerLevel[3] + damage > c.getLevelForXP(c.playerXP[3]))
						if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]));
						else 
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					else 
						c.playerLevel[3] += damage;
					c.getPA().refreshSkill(3);
				}
			break;
                        case 5:
                        c.clawDelay = 2;
                        //c.clawDamage = Misc.random(calculateMeleeMaxHit());
                        break;

                        case 7:
                        c.dbowDelay = 6;
                        break;
			case 100: // Korasi's
				damage = korasiDamage(null);
				NPCHandler.npcs[i].gfx0(1248);
			break;
		}
		setColorMasks(damage, calculateMeleeMaxHit(), damageMask);
		c.specEffect = 0;
		switch(damageMask) {
			case 1:
			NPCHandler.npcs[i].hitDiff = damage;
			NPCHandler.npcs[i].HP -= damage;
			c.totalDamageDealt += damage;
			NPCHandler.npcs[i].hitUpdateRequired = true;	
			NPCHandler.npcs[i].updateRequired = true;
			break;
		
			case 2:
			NPCHandler.npcs[i].hitDiff2 = damage;
			NPCHandler.npcs[i].HP -= damage;
			c.totalDamageDealt += damage;
			NPCHandler.npcs[i].hitUpdateRequired2 = true;	
			NPCHandler.npcs[i].updateRequired = true;
			c.doubleHit = false;
			break;
			
		}
	}
	
	public void fireProjectileNpc() {
		if(c.oldNpcIndex > 0) {
			if(NPCHandler.npcs[c.oldNpcIndex] != null) {
				c.projectileStage = 2;
				int pX = c.getX();
				int pY = c.getY();
				int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
				int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
				int offX = (pY - nY)* -1;
				int offY = (pX - nX)* -1;
				c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 43, 31, c.oldNpcIndex + 1, getStartDelay());
				if (usingDbow())
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 60, 31,  c.oldNpcIndex + 1, getStartDelay(), 35);
			}
		}
	}
	

	
	/**
	* Attack Players, same as npc tbh xD
	**/
	
		public void attackPlayer(int i) {
          if (c.playerEquipment[c.playerWeapon] == 13022) {
			c.gfx0(2138);
          }
		  if (c.playerEquipment[c.playerRing] == 15017) {
				double attackSpeed = (c.attackTimer / 150 * 150 + c.attackTimer);
				attackSpeed = attackSpeed;
			}
			if (c.playerEquipment[c.playerRing] == 15398) {
				double attackSpeed = (c.attackTimer / 10000 * 10000 + c.attackTimer);
				attackSpeed = attackSpeed * 10000;
			}
                if(c.vestaDelay > 0) {
                   resetPlayerAttack();
                   return;
                }
				if(c.playerEquipment[c.playerWeapon] == 18357 && 9342 != c.playerEquipment[c.playerArrows]){
                c.sendMessage("You can't use Chaotic Crossbow Without Onyx Bolts! (You dumb?)");
                return;	
				}
				if (c.playerEquipment[c.playerWeapon] == 15241) {
					c.gfx0(2138);
				}
				if(c.playerEquipment[c.playerWeapon] == 15241 && 15243 != c.playerEquipment[c.playerArrows]){
					c.sendMessage("You can't use Hand Cannon without the shots!");
					return;
				}
					if(c.curseActive[18]) { // SoulSplit GFX's - CAUSES CRASH
					String[] ssMessages = { "Ss off", "lmao ss", "pray off noob" };
int r2 = Misc.random(2);
int r8 = Misc.random(8);
					if(c.oldNpcIndex > 0) {
					if(NPCHandler.npcs[c.oldNpcIndex] != null) {
					try {
					if(c.curseActive[18] && !c.prayerActive[23] && c.playerLevel[3] <= 99) {
						int heal = 2;
						if(c.playerLevel[3] + heal >= c.getPA().getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
												if (NPCHandler.npcs[c.npcIndex].npcType == 3067 && r8 == 2)
	NPCHandler.npcs[c.npcIndex].forceChat(ssMessages[r2]);
					}
					final int pX = c.getX();
 					final int pY = c.getY();
					final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
					final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
					final int offX = (pY - nY)* -1;
					final int offY = (pX - nX)* -1;
					c.SSPLIT = true;
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, 50, 2263, 9, 9, c.oldNpcIndex + 1, 24, 0);
					EventManager.getSingleton().addEvent(new Event() {
					public void execute(EventContainer b) {
										NPCHandler.npcs[c.oldNpcIndex].gfx0(2264); // 1738
					c.SSPLIT = false;
				        b.stop();
					}
					}, 500);
					/*EventManager.getSingleton().addEvent(new Event() { // CAUSES CRASH
					public void execute(EventContainer b) {
					//c.getPA().createPlayersProjectile2(nX, nY, offX, offY, 50, 50, 2263, 9, 9, - c.playerId - 1, 24, 0);
				        b.stop();
					}
					}, 800);*/
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
				
				if(c.curseActive[19]) { // Turmoil
				c.getstr = PlayerHandler.players[i].playerLevel[2] * 10 / 100;
				c.getdef = PlayerHandler.players[i].playerLevel[1] * 15 / 100;
				c.getatt = PlayerHandler.players[i].playerLevel[0] * 15 / 100;
				c.getranged = PlayerHandler.players[i].playerLevel[4] * 15 / 100;
				c.getmagic = PlayerHandler.players[i].playerLevel[6] * 15 / 100;
				}
					
		

            for (int u : Player.Bolts)  {
                for (int y : Player.BOWS)  {
                    if(y == c.playerEquipment[c.playerWeapon] && c.playerEquipment[c.playerWeapon] != 9185 && u == c.playerEquipment[c.playerArrows] && c.playerEquipment[c.playerWeapon] != 15241 && u == c.playerEquipment[c.playerArrows]){
                        c.sendMessage("You can only use arrows with this bow.");
                        return;
                    }
                }
            }
			
		if(c.specEffect == 4) {
		c.specEffect = 0;

		}
                    /* DEGRADING */
		if (PlayerHandler.players[i] != null) {
			c.attackingId = i;
			if (PlayerHandler.players[i].isDead) {
				resetPlayerAttack();
				return;
			}
			
			if(c.respawnTimer > 0 || PlayerHandler.players[i].respawnTimer > 0) {
				resetPlayerAttack();
				return;
			}
			if(PlayerHandler.players[i].trade11 > 0) {
				c.sendMessage("You cannot attack a player that has just started the game!");
				c.sendMessage("this is to stop STG (Stop transfering gold)");
				resetPlayerAttack();
				return;
			}

			if(c.underAttackBy != 0 || c.underAttackBy2 > 0 || c.curseActive[9]) {
			}

			if (c.getPA().getWearingAmount() < 4 && c.duelStatus < 1) {
				c.sendMessage("You must be wearing at least 4 items to attack someone.");
				resetPlayerAttack();
				return;
			}
			
			boolean sameSpot = c.absX == PlayerHandler.players[i].getX() && c.absY == PlayerHandler.players[i].getY();
			if(!c.goodDistance(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), c.getX(), c.getY(), 25) && !sameSpot) {
				resetPlayerAttack();
				return;
			}

			if(PlayerHandler.players[i].respawnTimer > 0) {
				PlayerHandler.players[i].playerIndex = 0;
				resetPlayerAttack();
				return;
			}
			
			if (PlayerHandler.players[i].heightLevel != c.heightLevel) {
				resetPlayerAttack();
				return;
			}
			if (c.attackTimer == 1) {
				applyLeeches(i);
			}
			//c.sendMessage("Made it here0.");
			c.followId = i;
			c.followId2 = 0;
			if(c.attackTimer <= 0) {
				c.usingBow = false;
				c.specEffect = 0;
				c.usingRangeWeapon = false;
				c.rangeItemUsed = 0;
				boolean usingBow = false;
				boolean usingArrows = false;
				boolean usingOtherRangeWeapons = false;

				boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
				c.projectileStage = 0;
				
				if (c.absX == PlayerHandler.players[i].absX && c.absY == PlayerHandler.players[i].absY) {
					if (c.freezeTimer > 0) {
						resetPlayerAttack();
						return;
					}	
					c.followId = i;
					c.attackTimer = 0;
					return;
				}
				
				/*if ((c.inPirateHouse() && !Server.playerHandler.players[i].inPirateHouse()) || (Server.playerHandler.players[i].inPirateHouse() && !c.inPirateHouse())) {
					resetPlayerAttack();
					return;
				}*/
				//c.sendMessage("Made it here1.");
				if(!c.usingMagic) {
					for (int bowId : Player.BOWS) {
						if(c.playerEquipment[c.playerWeapon] == bowId) {
							usingBow = true;
							for (int arrowId : Player.ARROWS) {
								if(c.playerEquipment[c.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}
				
					for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
						if(c.playerEquipment[c.playerWeapon] == otherRangeId) {
							usingOtherRangeWeapons = true;
						}
					}
				}
				if (c.autocasting) {
					c.spellId = c.autocastId;
					c.usingMagic = true;
				}
				//c.sendMessage("Made it here2.");
				if(c.spellId > 0) {
                    c.usingMagic = true;
                }

				Client o2 = (Client)PlayerHandler.players[i];
				o2.typeOfDamageMask = getHitType(usingBow || usingOtherRangeWeapons || usingCross, c.usingMagic);
				o2.typeOfDamageMask2 = getHitType(usingBow || usingOtherRangeWeapons || usingCross, c.usingMagic);
				
				c.attackTimer = getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());

				if(c.duelRule[9]){
				boolean canUseWeapon = false;
					for(int funWeapon: Config.FUN_WEAPONS) {
						if(c.playerEquipment[c.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if(!canUseWeapon) {
						c.sendMessage("You can only use fun weapons in this duel!");
						resetPlayerAttack();
						return;
					}
				}
				//c.sendMessage("Made it here3.");
				if(c.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
					c.sendMessage("Range has been disabled in this duel!");
					return;
				}
				if(c.duelRule[3] && (!usingBow && !usingOtherRangeWeapons && !c.usingMagic)) {
					c.sendMessage("Melee has been disabled in this duel!");
					return;
				}
				
				if(c.duelRule[4] && c.usingMagic) {
					c.sendMessage("Magic has been disabled in this duel!");
					resetPlayerAttack();
					return;
				}
				
				if((!c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 4) && (usingOtherRangeWeapons && !usingBow && !c.usingMagic)) 
				|| (!c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 2) && (!usingOtherRangeWeapons && usingHally() && !usingBow && !c.usingMagic))
				|| (!c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), getRequiredDistance()) && (!usingOtherRangeWeapons && !usingHally() && !usingBow && !c.usingMagic)) 
				|| (!c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 10) && (usingBow || c.usingMagic))) {
					//c.sendMessage("Setting attack timer to 1");
					c.attackTimer = 1;
					if (!usingBow && !c.usingMagic && !usingOtherRangeWeapons && c.freezeTimer > 0)
						resetPlayerAttack();
					return;
				}
				
				if(!usingCross && !usingArrows && usingBow && (c.playerEquipment[c.playerWeapon] < 4212 || c.playerEquipment[c.playerWeapon] > 4223) && !c.usingMagic) {
					if(c.playerEquipment[c.playerWeapon] == 15241)
						c.sendMessage("You have run out of hand cannon shots!");
					else
					c.sendMessage("You have run out of arrows!");
					c.stopMovement();
					resetPlayerAttack();
					return;
				}
				if(correctBowAndArrows() < c.playerEquipment[c.playerArrows] && Config.CORRECT_ARROWS && usingBow && !usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185 && !c.usingMagic) {
					c.sendMessage("You can't use "+c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase()+"s with a "+c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()+".");
					c.stopMovement();
					resetPlayerAttack();
					return;
				}
				if(c.playerEquipment[c.playerArrows] != 15243 && c.playerEquipment[c.playerWeapon] == 15241) {
					c.sendMessage("You can't use "+c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase()+"s with a "+c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()+".");
					c.stopMovement();
					c.npcIndex = 0;
					return;
				}
				if (c.playerEquipment[c.playerWeapon] == 9185 && !properBolts() && !c.usingMagic) {
					c.sendMessage("You must use bolts with a crossbow.");
					c.stopMovement();
					resetPlayerAttack();
					return;				
				}
				
				
				if(usingBow || c.usingMagic || usingOtherRangeWeapons || usingHally()) {
					c.stopMovement();
				}
				
				if(!checkMagicReqs(c.spellId)) {
					c.stopMovement();
					resetPlayerAttack();
					return;
				}
				
				c.faceUpdate(i+32768);
				
				if(c.duelStatus != 5) {
					if(!c.attackedPlayers.contains(c.playerIndex) && !PlayerHandler.players[c.playerIndex].attackedPlayers.contains(c.playerId)) {
						c.attackedPlayers.add(c.playerIndex);
						c.isSkulled = true;
						c.skullTimer = Config.SKULL_TIMER;
						c.headIconPk = 0;
						c.getPA().requestUpdates();
					} 
				}
				c.specAccuracy = 1.0;
				c.specDamage = 1.0;
				c.delayedDamage = c.delayedDamage2 = 0;
				if(c.usingSpecial && !c.usingMagic) {
					if(c.duelRule[10] && c.duelStatus == 5) {
						c.sendMessage("Special attacks have been disabled during this duel!");
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						resetPlayerAttack();
						return;
					}
					if(checkSpecAmount(c.playerEquipment[c.playerWeapon])){
						c.lastArrowUsed = c.playerEquipment[c.playerArrows];
						activateSpecial(c.playerEquipment[c.playerWeapon], i);
						c.followId = c.playerIndex;
						return;
					} else {
						c.sendMessage("You don't have the required special energy to use this attack.");
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						c.playerIndex = 0;
						return;
					}	
				}
				
				if(!c.usingMagic) {
					c.startAnimation(getWepAnim(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()));
					c.mageFollow = false;
				} else {
					c.startAnimation(c.MAGIC_SPELLS[c.spellId][2]);
					c.mageFollow = true;
					c.followId = c.playerIndex;
				}
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = c.playerId;
				c.lastArrowUsed = 0;
				c.rangeItemUsed = 0;
								c.slayerHelmetEffect = false;
				if(!usingBow && !c.usingMagic && !usingOtherRangeWeapons) { // melee hit delay
					c.followId = PlayerHandler.players[c.playerIndex].playerId;
					c.getPA().followPlayer(c.playerIndex);
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.delayedDamage = Misc.random(calculateMeleeMaxHit());
					c.projectileStage = 0;
					c.oldPlayerIndex = i;
				}
								
				if(usingBow && !usingOtherRangeWeapons && !c.usingMagic || usingCross || c.playerEquipment[c.playerWeapon] == 15241) { // range hit delay	
					if(c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223) {
						c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
						c.crystalBowArrowCount++;
					} else {
						c.rangeItemUsed = c.playerEquipment[c.playerArrows];
						c.getItems().deleteArrow();
					}
					if (c.fightMode == 2)
						c.attackTimer--;
					if (usingCross)
						c.usingBow = true;
					c.usingBow = true;
					c.followId = PlayerHandler.players[c.playerIndex].playerId;
					c.getPA().followPlayer(c.playerIndex);
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.lastArrowUsed = c.playerEquipment[c.playerArrows];
					c.gfx100(getRangeStartGFX());	
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldPlayerIndex = i;
					fireProjectilePlayer();
				}
											
				if(usingOtherRangeWeapons) {	// knives, darts, etc hit delay
					c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
					c.getItems().deleteEquipment();
					c.usingRangeWeapon = true;
					c.followId = PlayerHandler.players[c.playerIndex].playerId;
					c.getPA().followPlayer(c.playerIndex);
					c.gfx100(getRangeStartGFX());
					if (c.fightMode == 2)
						c.attackTimer--;
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldPlayerIndex = i;
					fireProjectilePlayer();
				}

				if(c.usingMagic) {	// magic hit delay
					Client o = (Client)PlayerHandler.players[i];
					int pX = c.getX();
					int pY = c.getY();
					int nX = PlayerHandler.players[i].getX();
					int nY = PlayerHandler.players[i].getY();
					int offX = (pY - nY)* -1;
					int offY = (pX - nX)* -1;
					c.castingMagic = true;
					c.projectileStage = 2;
					if(c.MAGIC_SPELLS[c.spellId][3] > 0) {
						if(getStartGfxHeight() == 100) {
							c.gfx100(c.MAGIC_SPELLS[c.spellId][3]);
						} else {
							c.gfx0(c.MAGIC_SPELLS[c.spellId][3]);
						}
					}
					if(c.MAGIC_SPELLS[c.spellId][4] > 0) {
						c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 78, c.MAGIC_SPELLS[c.spellId][4], getStartHeight(), getEndHeight(), -i - 1, getStartDelay());
					}
					if (c.autocastId > 0) {
						c.followId = c.playerIndex;
						c.followDistance = 5;
					}	
					c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.oldPlayerIndex = i;
					c.oldSpellId = c.spellId;
                    c.spellId = 0;
					if(c.MAGIC_SPELLS[c.oldSpellId][0] == 12891 && o.isMoving) {
						//c.sendMessage("Barrage projectile..");
						c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 85, 368, 25, 25, -i - 1, getStartDelay());
					}
					if(Misc.random(o.getCombat().mageDef()) > Misc.random(mageAtk())) {
						c.magicFailed = true;
					} else {
						c.magicFailed = false;
					}
					int freezeDelay = getFreezeTime();//freeze time
					if(freezeDelay > 0 && PlayerHandler.players[i].freezeTimer <= -3 && !c.magicFailed) { 
						PlayerHandler.players[i].freezeTimer = freezeDelay;
						o.resetWalkingQueue();
						o.sendMessage("You have been frozen.");
						o.barrageorb = 1;
						o.frozenBy = c.playerId;
					}
					if (!c.autocasting && c.spellId <= 0)
						c.playerIndex = 0;
				}
			}
		}
		}
				/*if(usingBow && Config.CRYSTAL_BOW_DEGRADES) { // crystal bow degrading
					if(c.playerEquipment[c.playerWeapon] == 4212) { // new crystal bow becomes full bow on the first shot
						c.getItems().wearItem(4214, 1, 3);
					}
					
					if(c.crystalBowArrowCount >= 250){
						switch(c.playerEquipment[c.playerWeapon]) {
							
							case 4223: // 1/10 bow
							c.getItems().wearItem(-1, 1, 3);
							c.sendMessage("Your crystal bow has fully degraded.");
							if(!c.getItems().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(c, 4207, c.getX(), c.getY(), 1, c.getId());
							}
							c.crystalBowArrowCount = 0;
							break;
							
							default:
							c.getItems().wearItem(++c.playerEquipment[c.playerWeapon], 1, 3);
							c.sendMessage("Your crystal bow degrades.");
							c.crystalBowArrowCount = 0;
							break;
						}
					}	
				}
			}
		}
	}*/
	
	public boolean usingCrystalBow() {
		return c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223;	
	}
	
	public void appendVengeance(int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		Player o = PlayerHandler.players[otherPlayer];
		o.forcedText = "Taste Vengeance!";
		o.typeOfDamageMask = 0;
		o.forcedChatUpdateRequired = true;
		o.updateRequired = true;
		o.vengOn = false;
		if ((o.playerLevel[3] - damage) > 0) {

			damage = (int)(damage * 0.75);
			if (damage > c.playerLevel[3]) {
				damage = c.playerLevel[3];
			}
			c.setHitDiff2(damage);
			c.setHitUpdateRequired2(true);
			c.playerLevel[3] -= damage;
			c.getPA().refreshSkill(3);
		}	
		c.updateRequired = true;
	}

	public void appendVengeanceNPC(int otherPlayer, int damage) {
	int r3 = Misc.random(3);
int r5 = Misc.random(5);
String[] vengM = { "Lol veng? really?", "Dont veng...", "Ffs common stop venging" }; //replace with any phrase you want
if (NPCHandler.npcs[c.npcIndex].npcType == 3067 && r5 < 3)	NPCHandler.npcs[c.npcIndex].forceChat(vengM[r3]);
		if (damage <= 0)
			return;
		if (c.npcIndex > 0 && NPCHandler.npcs[c.npcIndex] != null) {
			c.forcedText = "Taste Vengeance!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			c.vengOn = false;
			c.typeOfDamageMask = 0;
			if ((NPCHandler.npcs[c.npcIndex].HP - damage) > 0) {
				damage = (int)(damage * 0.75);
				if (damage > NPCHandler.npcs[c.npcIndex].HP) {
					damage = NPCHandler.npcs[c.npcIndex].HP;
				}
				NPCHandler.npcs[c.npcIndex].HP -= damage;
				NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
				NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
				NPCHandler.npcs[c.npcIndex].updateRequired = true;
			}
		}	
		c.updateRequired = true;
	}
	
	public void playerDelayedHit(int i) {
		if (PlayerHandler.players[i] != null) {
			if (PlayerHandler.players[i].isDead || c.isDead || PlayerHandler.players[i].playerLevel[3] <= 0 || c.playerLevel[3] <= 0) {
				c.playerIndex = 0;
				return;
			}
			if (PlayerHandler.players[i].respawnTimer > 0) {
				c.faceUpdate(0);
				c.playerIndex = 0;
				return;
			}
			Client o = (Client) PlayerHandler.players[i];
			o.getPA().removeAllWindows();
			if (o.playerIndex <= 0 && o.npcIndex <= 0) {
				if (o.autoRet == 1) {
					o.playerIndex = c.playerId;
				}	
			}
			if(o.attackTimer <= 3 || o.attackTimer == 0 && o.playerIndex == 0 && !c.castingMagic) { // block animation
				o.startAnimation(o.getCombat().getBlockEmote());
			}
			if(o.inTrade) {
				o.getTradeAndDuel().declineTrade();
			}
			if (!o.inWild() && !o.inDuelArena() && !o.inPits) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if(c.projectileStage == 0 && !c.usingMagic && !c.castingMagic) { // melee hit damage								
					applyPlayerMeleeDamage(i, 1, Misc.random(calculateMeleeMaxHit()));
					if(c.doubleHit && !c.usingClaws) {
						applyPlayerMeleeDamage(i, 2, Misc.random(calculateMeleeMaxHit()));
					}	
					if(c.doubleHit && c.usingClaws) {
						applyPlayerMeleeDamage(i, 2, c.previousDamage / 2);
					}
			}
			
			if(!c.castingMagic && c.projectileStage > 0) { // range hit damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				int PrayerDrain = damage / 100;
				if (c.lastWeaponUsed == 15701 || c.lastWeaponUsed == 15702 || c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 15703 || c.lastWeaponUsed == 15704 || c.lastWeaponUsed == 19143 || c.lastWeaponUsed == 19146 || c.lastWeaponUsed == 19149 || c.bowSpecShot == 1)
					damage2 = Misc.random(rangeMaxHit());
				boolean ignoreDef = false;
				if (Misc.random(4) == 1 && c.lastArrowUsed == 9243 && c.playerEquipment[c.playerWeapon] == 9185) {
					ignoreDef = true;
					o.gfx0(753);
				}	
				if (Misc.random(4) == 1 && c.lastArrowUsed == 9243 && c.playerEquipment[c.playerWeapon] == 9185) {
					ignoreDef = true;
					o.gfx0(753);
				}					
				if(Misc.random(10+o.getCombat().calculateRangeDefence()) > Misc.random(10+calculateRangeAttack()) && !ignoreDef) {
					damage = 0;
				}
				
				if (c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 15701 || c.lastWeaponUsed == 15702 || c.lastWeaponUsed == 15703 || c.lastWeaponUsed == 15704 || c.lastWeaponUsed == 19143 || c.lastWeaponUsed == 19146 || c.lastWeaponUsed == 19149 || c.bowSpecShot == 1) {
					if (Misc.random(10+o.getCombat().calculateRangeDefence()) > Misc.random(10+calculateRangeAttack()))
						damage2 = 0;
				}
								
				if (c.dbowSpec) {
					o.gfx100(1100);
					if (damage < 8)
						damage = 8;
					if (damage2 < 8)
						damage2 = 8;
					c.dbowSpec = false;
				}
				if (damage > 0 && Misc.random(5) == 1 && c.lastArrowUsed == 9244 && c.playerEquipment[c.playerWeapon] == 9185) {
					damage *= 1.45;
					o.gfx0(756);
				}
				if (damage > 0 && Misc.random(5) == 1 && c.lastArrowUsed == 9342 && c.playerEquipment[c.playerWeapon] == 18357) {
					damage *= 2.45;
					o.gfx0(753);
				}
				if((o.prayerActive[17] || o.curseActive[8]) && System.currentTimeMillis() - o.protRangeDelay > 1500) { // if prayer active reduce damage by half 
					damage = (int)damage * 60 / 100;
						if (o.playerEquipment[o.playerShield] == 15023 && o.playerLevel[5] >= 1 && damage >= 1) {
						damage = (int)damage * 42 / 100;
						damage2 = (int)damage2 * 42 / 100;
						o.playerLevel[5] -= PrayerDrain;
						o.getPA().refreshSkill(5);
						if (o.playerLevel[5] <= 0) {
							o.playerLevel[5] = 0;
							o.getCombat().resetPrayers();
							o.getPA().refreshSkill(5);
						}
					}
					if (o.playerEquipment[o.playerShield] == 15026 && !o.prayerActive[17] || !o.curseActive[8] && damage >= 1) {
						if(Misc.random(4) == 3) {
							damage = (int)damage * 65 / 100;
							damage2 = (int)damage2 * 65 / 100;
						}
					}
					if (c.lastWeaponUsed == 11235 || c.lastWeaponUsed == 15701 || c.lastWeaponUsed == 15702 || c.lastWeaponUsed == 15703 || c.lastWeaponUsed == 15704 || c.lastWeaponUsed == 19143 || c.lastWeaponUsed == 19146 || c.lastWeaponUsed == 19149 || c.bowSpecShot == 1)
						damage2 = (int)damage2 * 60 / 100;
				}
					if (o.playerEquipment[o.playerWeapon] == 15486 && damage >= 1 && o.SolProtect >= 1) {
					damage = (int)damage / 2;
					damage2 = (int)damage2 / 2;
					}

					if (o.playerEquipment[o.playerShield] == 15023 && !o.prayerActive[17] || !o.curseActive[8] && o.playerLevel[5] >= 1 && damage >= 1) {
					//if(Misc.random(2) == 1) {
					damage = (int)damage * 70 / 100;
					damage2 = (int)damage2 * 70 / 100;
					o.playerLevel[5] -= PrayerDrain;
					o.getPA().refreshSkill(5);

					if (o.playerLevel[5] <= 0) {
					o.playerLevel[5] = 0;
					o.getCombat().resetPrayers();
					o.getPA().refreshSkill(5);
					//}
					}
					}
					
				if(damage > 20) { // Soak Range
					o.soaked = (int) (damage * getBonus(o, ABSORB_RANGED_BONUS));
					damage -= o.soaked;
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage < 0) { 
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage - damage2 < 0) { 
					damage2 = PlayerHandler.players[i].playerLevel[3] - damage;
				}
				if (damage < 0)
					damage = 0;
				if (damage2 < 0 && damage2 != -1)
					damage2 = 0;
				if (o.vengOn) {
					appendVengeance(i, damage);
					appendVengeance(i, damage2);
				}
				if (damage > 0)
					//applyRecoil(damage, i);
					Deflect(damage, i);
				if (damage2 > 0)
					//applyRecoil(damage2, i);
					Deflect(damage2, i);
				if(c.fightMode == 3) {
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 4); 
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 1);				
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 3);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				} else {
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE), 4); 
					c.getPA().addSkillXP((damage*Config.RANGE_EXP_RATE/3), 3);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				}
				boolean dropArrows = true;
						
				for(int noArrowId : c.NO_ARROW_DROP) {
					if(c.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if(dropArrows) {
					c.getItems().dropArrowPlayer();	
				}
				o.getCombat().setColorMasks(damage, rangeMaxHit(), 1);
				o.getCombat().setColorMasks(damage2, rangeMaxHit(), 2);
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = c.playerId;
				//Server.playerHandler.players[i].setHitDiff(damage);
				//Server.playerHandler.players[i].playerLevel[3] -= damage;
				PlayerHandler.players[i].dealDamage(damage);
				PlayerHandler.players[i].damageTaken[c.playerId] += damage;
				c.killedBy = PlayerHandler.players[i].playerId;
				PlayerHandler.players[i].handleHitMask(damage);
				int [] rangeDouble = {11235};
				for (int j = 0; j < rangeDouble.length; j++) {
				if (damage2 != -1 && rangeDouble[j] == c.playerEquipment[c.playerWeapon]) {
					//Server.playerHandler.players[i].playerLevel[3] -= damage2;
					PlayerHandler.players[i].dealDamage(damage2);
					PlayerHandler.players[i].damageTaken[c.playerId] += damage2;
					PlayerHandler.players[i].handleHitMask(damage2);
					}
				}
				o.getPA().refreshSkill(3);
					
				//Server.playerHandler.players[i].setHitUpdateRequired(true);	
				PlayerHandler.players[i].updateRequired = true;
				applySmite(i, damage);
				if (c.soulSplitDelay <= 0) {
					applySoulSplit(i, damage);
				}
				if (damage2 != -1)
					if (c.soulSplitDelay <= 0) {
						applySoulSplit(i, damage);
					}
					applySmite(i, damage2);
			
			} else if (c.projectileStage > 0) { // magic hit damage
				int damage = Misc.random(finalMagicDamage(c));
				if(godSpells()) {
					if(System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
						damage += 10;
					}
				}
				if(c.playerEquipment[c.playerWeapon] == 15486) {
					damage += 10;
				}
				int PrayerDrain = damage / 100;
				//c.playerIndex = 0;
				if (c.magicFailed)
					damage = 0;
					
				if((o.prayerActive[16] || o.curseActive[7]) && System.currentTimeMillis() - o.protMageDelay > 1500) { // if prayer active reduce damage by half 
					damage = (int)damage * 60 / 100;
 					if (o.playerEquipment[o.playerShield] == 15023 && o.playerLevel[5] >= 1 && damage >= 1) {
						damage = (int)damage * 42 / 100;
						o.playerLevel[5] -= PrayerDrain;
						o.getPA().refreshSkill(5);
						if (o.playerLevel[5] <= 0) {
							o.playerLevel[5] = 0;
							o.getCombat().resetPrayers();
							o.getPA().refreshSkill(5);
						}
					}
				}


					if (o.playerEquipment[o.playerShield] == 15026 && !o.prayerActive[16] || !o.curseActive[7] && damage >= 1) {
					if(Misc.random(4) == 3) {
					damage = (int)damage * 65 / 100;
					}
					}

					if (o.playerEquipment[o.playerWeapon] == 15486 && damage >= 1 && o.SolProtect >= 1) {
					damage = (int)damage / 2;
					}

 					if (o.playerEquipment[o.playerShield] == 15023 && !o.prayerActive[16] || !o.curseActive[7] && o.playerLevel[5] >= 1 && damage >= 1) {
					//if(Misc.random(2) == 1) {
					damage = (int)damage * 70 / 100;
					o.getPA().refreshSkill(5);
					o.playerLevel[5] -= PrayerDrain;

					if (o.playerLevel[5] <= 0) {
					o.playerLevel[5] = 0;
					o.getCombat().resetPrayers();
					o.getPA().refreshSkill(5);
					//}
					}
					}
		if(damage > 20) { // Soak Mage
			o.soaked = (int) (damage * getBonus(o, ABSORB_MAGIC_BONUS));
			damage -= o.soaked;
		}
				if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if (o.vengOn)
					appendVengeance(i, damage);
				if (damage > 0)
					//applyRecoil(damage, i);
					Deflect(damage, i);
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage*Config.MAGIC_EXP_RATE), 6); 
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage*Config.MAGIC_EXP_RATE/3), 3);
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(6);
				
				if(getEndGfxHeight() == 100 && !c.magicFailed){ // end GFX
					PlayerHandler.players[i].gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (!c.magicFailed){
					PlayerHandler.players[i].gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if(c.magicFailed) {	
					PlayerHandler.players[i].gfx100(85);
				}
				multiSpellOnPlayer(i);
				/*if(c.getCombat().getEndGfxHeight() == 100 && !c.magicFailed){ // end GFX
					Server.playerHandler.players[i].gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (!c.magicFailed){
					if (o.barrageOrb == 1 && c.MAGIC_SPELLS[c.oldSpellId][0] == 12891) {	
						Server.playerHandler.players[i].gfx0(1677);
					} else {
						Server.playerHandler.players[i].gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
					}
				} else if(c.magicFailed) {	
					Server.playerHandler.players[i].gfx100(85);
				}*/
				
				if(!c.magicFailed) {
					if(System.currentTimeMillis() - PlayerHandler.players[i].reduceStat > 35000) {
						PlayerHandler.players[i].reduceStat = System.currentTimeMillis();
						switch(c.MAGIC_SPELLS[c.oldSpellId][0]) { 
							case 12987:
							case 13011:
							case 12999:
							case 13023:
							PlayerHandler.players[i].playerLevel[0] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[0]) * 10) / 100);
							break;
						}
					}
					
					switch(c.MAGIC_SPELLS[c.oldSpellId][0]) { 	
						case 12445: //teleblock
						if (System.currentTimeMillis() - o.teleBlockDelay > o.teleBlockLength) {
							o.teleBlockDelay = System.currentTimeMillis();
							o.sendMessage("You have been teleblocked.");
							if (o.prayerActive[16] || o.curseActive[7] && System.currentTimeMillis() - o.protMageDelay > 1500)
								o.teleBlockLength = 150000;
							else
								o.teleBlockLength = 300000;
						}			
						break;
						
						case 12871:
							if (o.barrageorb == 1) {
								o.barrageorb = 0;
							}
						break;

						case 12891:			
							if (o.barrageorb != 1) {
								o.gfx50(1677);
							}
							if (o.barrageorb == 1) {
								o.barrageorb = 0;
							}
						break;
						
						case 12901:
						case 12919: // blood spells
						case 12911:
						case 12929:
						int heal = (int)(damage / 4);
						if(c.playerLevel[3] + heal > c.getPA().getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
						break;
						
						case 1153:						
						PlayerHandler.players[i].playerLevel[0] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[0]) * 5) / 100);
						o.sendMessage("Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(0);
						break;
						
						case 1157:
						PlayerHandler.players[i].playerLevel[2] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[2]) * 5) / 100);
						o.sendMessage("Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();						
						o.getPA().refreshSkill(2);
						break;
						
						case 1161:
						PlayerHandler.players[i].playerLevel[1] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[1]) * 5) / 100);
						o.sendMessage("Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();					
						o.getPA().refreshSkill(1);
						break;
						
						case 1542:
						PlayerHandler.players[i].playerLevel[1] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[1]) * 10) / 100);
						o.sendMessage("Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] =  System.currentTimeMillis();
						o.getPA().refreshSkill(1);
						break;
						
						case 1543:
						PlayerHandler.players[i].playerLevel[2] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[2]) * 10) / 100);
						o.sendMessage("Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(2);
						break;
						
						case 1562:					
						PlayerHandler.players[i].playerLevel[0] -= ((o.getPA().getLevelForXP(PlayerHandler.players[i].playerXP[0]) * 10) / 100);
						o.sendMessage("Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();					
						o.getPA().refreshSkill(0);
						break;
					}					
				}
				
				o.getCombat().setColorMasks(damage, finalMagicDamage(c), 1);
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].killerId = c.playerId;
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				if(finalMagicDamage(c) != 0) {
					//Server.playerHandler.players[i].playerLevel[3] -= damage;
					PlayerHandler.players[i].dealDamage(damage);
					PlayerHandler.players[i].damageTaken[c.playerId] += damage;
					c.totalPlayerDamageDealt += damage;
					if (!c.magicFailed) {
						//Server.playerHandler.players[i].setHitDiff(damage);
						//Server.playerHandler.players[i].setHitUpdateRequired(true);
						PlayerHandler.players[i].handleHitMask(damage);
					}
				}
				applySmite(i, damage);
				if (c.soulSplitDelay <= 0) {
					applySoulSplit(i, damage);
				}
				c.killedBy = PlayerHandler.players[i].playerId;	
				o.getPA().refreshSkill(3);
				PlayerHandler.players[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				if (o.inMulti() && multis()) {
					c.barrageCount = 0;
					for (int j = 0; j < PlayerHandler.players.length; j++) {
						if (PlayerHandler.players[j] != null) {
							if (j == o.playerId)
								continue;
							if (c.barrageCount >= 9)
								break;
							if (o.goodDistance(o.getX(), o.getY(), PlayerHandler.players[j].getX(), PlayerHandler.players[j].getY(), 1))
								appendMultiBarrage(j, c.magicFailed);
						}	
					}
				}
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(6);
				c.oldSpellId = 0;
			}
		}	
		c.getPA().requestUpdates();
		if(c.bowSpecShot <= 0) {
			c.oldPlayerIndex = 0;	
			c.projectileStage = 0;
			c.lastWeaponUsed = 0;
			c.doubleHit = false;
			c.bowSpecShot = 0;
		}
		if(c.bowSpecShot != 0) {
			c.bowSpecShot = 0;
		}
	}
	
	public boolean multis() {
		switch (c.MAGIC_SPELLS[c.oldSpellId][0]) {
			case 12891:
			case 12881:
			case 13011:
			case 13023:
			case 12919: // blood spells
			case 12929:
			case 12963:
			case 12975:
			return true;
		}
		return false;
	
	}
	public void appendMultiBarrage(int playerId, boolean splashed) {
		if (PlayerHandler.players[playerId] != null) {
			Client c2 = (Client)PlayerHandler.players[playerId];
			if (c2.isDead || c2.respawnTimer > 0)
				return;
			if (checkMultiBarrageReqs(playerId)) {
				c.barrageCount++;
				if (Misc.random(mageAtk()) > Misc.random(mageDef()) && !c.magicFailed) {
					if(getEndGfxHeight() == 100){ // end GFX
						c2.gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
					} else {
						c2.gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
					}
					int damage = Misc.random(finalMagicDamage(c));
					if (c2.prayerActive[12]) {
						damage *= (int)(.60);
					}
					if (c2.playerLevel[3] - damage < 0) {
						damage = c2.playerLevel[3];					
					}
					c2.getCombat().setColorMasks(damage, finalMagicDamage(c), 1);
					c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage*Config.MAGIC_EXP_RATE), 6); 
					c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage*Config.MAGIC_EXP_RATE/3), 3);
					//Server.playerHandler.players[playerId].setHitDiff(damage);
					//Server.playerHandler.players[playerId].setHitUpdateRequired(true);
					PlayerHandler.players[playerId].handleHitMask(damage);
					//Server.playerHandler.players[playerId].playerLevel[3] -= damage;
 					PlayerHandler.players[playerId].dealDamage(damage);
					PlayerHandler.players[playerId].damageTaken[c.playerId] += damage;
					c2.getPA().refreshSkill(3);
					c.totalPlayerDamageDealt += damage;
					multiSpellEffect(playerId, damage);
				} else {
					c2.gfx100(85);
				}			
			}		
		}	
	}
	
	public void multiSpellEffect(int playerId, int damage) {					
		switch(c.MAGIC_SPELLS[c.oldSpellId][0]) {
			case 13011:
			case 13023:
			if(System.currentTimeMillis() - PlayerHandler.players[playerId].reduceStat > 35000) {
				PlayerHandler.players[playerId].reduceStat = System.currentTimeMillis();
				PlayerHandler.players[playerId].playerLevel[0] -= ((PlayerHandler.players[playerId].getLevelForXP(PlayerHandler.players[playerId].playerXP[0]) * 10) / 100);
			}	
			break;
			case 12919: // blood spells
			case 12929:
				int heal = (int)(damage / 4);
				if(c.playerLevel[3] + heal >= c.calculateMaxLifePoints()) {
					c.playerLevel[3] = c.calculateMaxLifePoints();
				} else {
					c.playerLevel[3] += heal;
				}
				c.getPA().refreshSkill(3);
			break;
			case 12891:
			case 12881:
				if (PlayerHandler.players[playerId].freezeTimer < -4) {
					PlayerHandler.players[playerId].freezeTimer = getFreezeTime();
					PlayerHandler.players[playerId].stopMovement();
				}
			break;
		}	
	}
	public void applyPlayerClawDamage(int i, int damageMask, int damage){
		int PrayerDrain = damage / 100;
		Client o = (Client) PlayerHandler.players[i];
		if(o == null) {
			return;
		}

		c.previousDamage = damage;
		boolean veracsEffect = false;
		boolean guthansEffect = false;
		if (c.getPA().fullVeracs()) {
			if (Misc.random(4) == 1) {
				veracsEffect = true;				
			}		
		}
		if (c.getPA().fullGuthans()) {
			if (Misc.random(4) == 1) {
				guthansEffect = true;
			}		
		}
		if (damageMask == 1) {
			damage = c.delayedDamage;
			c.delayedDamage = 0;
		} else {
			damage = c.delayedDamage2;
			c.delayedDamage2 = 0;
		}
		if(Misc.random(o.getCombat().calculateMeleeDefence()) > Misc.random(calculateMeleeAttack()) && !veracsEffect) {
			damage = 0;
			c.bonusAttack = 0;
		} else if (c.playerEquipment[c.playerWeapon] == 5698 && o.poisonDamage <= 0 && Misc.random(3) == 1) {
			o.getPA().appendPoison(13);
			c.bonusAttack += damage/3;
		} else {
			c.bonusAttack += damage/3;
		}
		if((o.prayerActive[18] || o.curseActive[9]) && System.currentTimeMillis() - o.protMeleeDelay > 1500 && !veracsEffect) { // if prayer active reduce damage by 40%
					damage = (int)damage * 60 / 100;
					if (o.playerEquipment[o.playerShield] == 15023 && o.playerLevel[5] >= 1 && damage >= 1) {
					//if (Misc.random(2) == 1) {
					damage = (int)damage * 42 / 100;
  					o.playerLevel[5] -= PrayerDrain;
					o.getPA().refreshSkill(5);

					if (o.playerLevel[5] <= 0) {
					o.playerLevel[5] = 0;
					o.getCombat().resetPrayers();
					o.getPA().refreshSkill(5);
					//}
					}
					}
					}

					if (o.playerEquipment[o.playerWeapon] == 15486 && damage >= 1 && o.SolProtect >= 1) {
					damage = (int)damage / 2;
					}

					if (o.playerEquipment[o.playerShield] == 15026 && !o.prayerActive[18] || !o.curseActive[9] && damage >= 1) {
					if(Misc.random(4) == 3) {
					damage = (int)damage * 65 / 100;
					}
					}

 					if (o.playerEquipment[o.playerShield] == 15023 && !o.prayerActive[18] || !o.curseActive[9] && o.playerLevel[5] >= 1 && damage >= 1) {
					//if(Misc.random(2) == 1) {
					damage = (int)damage * 70 / 100;
					o.getPA().refreshSkill(5);
					o.playerLevel[5] -= PrayerDrain;

					if (o.playerLevel[5] <= 0) {
					o.playerLevel[5] = 0;
					o.getCombat().resetPrayers();
					o.getPA().refreshSkill(5);
					}
					//}
					}
		if(Misc.random(o.getCombat().calculateMeleeDefence()) > Misc.random(calculateMeleeAttack()) && !veracsEffect) {
			damage = 0;
			c.bonusAttack = 0;
		} else if (c.playerEquipment[c.playerWeapon] == 5698 && o.poisonDamage <= 0 && Misc.random(3) == 1) {
			o.getPA().appendPoison(13);
			c.bonusAttack += damage/3;
		} else {
			c.bonusAttack += damage/3;
		}
		if(o.prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500 && !veracsEffect) { // if prayer active reduce damage by 40%
			damage = (int)damage * 60 / 100;
		}
		if(damage > 20) { // Soak Melee
			o.soaked = (int) (damage * getBonus(o, ABSORB_MELEE_BONUS));
			damage -= o.soaked;
		}
		if (c.maxNextHit) {
			damage = calculateMeleeMaxHit();
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			o.gfx0(398);		
		}
		if (c.ssSpec && damageMask == 2) {
			damage = 5 + Misc.random(11);
			c.ssSpec = false;
		}
		if (PlayerHandler.players[i].playerLevel[3] - damage < 0) { 
			damage = PlayerHandler.players[i].playerLevel[3];
		}
		if (o.vengOn && damage > 0)
			appendVengeance(i, damage);
		if (damage > 0)
					//applyRecoil(damage, i);
					Deflect(damage, i);
		switch(c.specEffect) {
			case 1: // dragon scimmy special
			if(damage > 0) {
				if(o.prayerActive[16] || o.prayerActive[17] || o.prayerActive[18] || o.curseActive[7] || o.curseActive[8] || o.curseActive[9]) {
					o.headIcon = -1;
					o.getPA().sendFrame36(c.PRAYER_GLOW[16], 0);
					o.getPA().sendFrame36(c.PRAYER_GLOW[17], 0);
					o.getPA().sendFrame36(c.PRAYER_GLOW[18], 0);	
					o.getPA().sendFrame36(c.CURSE_GLOW[7], 0);
					o.getPA().sendFrame36(c.CURSE_GLOW[8], 0);
					o.getPA().sendFrame36(c.CURSE_GLOW[9], 0);					
				}
				o.sendMessage("You have been injured!");
				o.stopPrayerDelay = System.currentTimeMillis();
				o.prayerActive[16] = false;
				o.prayerActive[17] = false;
				o.prayerActive[18] = false;
				o.curseActive[7] = false;
				o.curseActive[8] = false;
				o.curseActive[9] = false;
				o.getPA().requestUpdates();		
			}
			break;
			case 2:
				if (damage > 0) {
					if (o.freezeTimer <= 0)
						o.freezeTimer = 30;
					o.gfx0(369);
					o.sendMessage("You have been frozen.");
					o.frozenBy = c.playerId;
					o.stopMovement();
					c.sendMessage("You freeze your enemy.");
				}		
			break;
			case 3:
				if (damage > 0) {
					o.playerLevel[1] -= damage;
					o.sendMessage("You feel weak.");
					if (o.playerLevel[1] < 1)
						o.playerLevel[1] = 1;
					o.getPA().refreshSkill(1);
				}
			break;
			case 4:
				if (damage > 0) {
					if (c.playerLevel[3] + damage > c.getLevelForXP(c.playerXP[3]))
						if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]));
						else 
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					else 
						c.playerLevel[3] += damage;
					c.getPA().refreshSkill(3);
				}
			break;
			case 5:
			c.clawDelay = 2;
			break;
                        case 6:
                        o.vestaDelay = 12;
                        break;

		}
		o.getCombat().setColorMasks(damage, calculateMeleeMaxHit(), damageMask);
		c.specEffect = 0;
		if(c.fightMode == 3) {
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 0); 
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 1);
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 2); 				
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 3);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(1);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
		} else {
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE), c.fightMode); 
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 3);
			c.getPA().refreshSkill(c.fightMode);
			c.getPA().refreshSkill(3);
		}
		PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
		PlayerHandler.players[i].underAttackBy = c.playerId;
		PlayerHandler.players[i].killerId = c.playerId;	
		PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
		if (c.killedBy != PlayerHandler.players[i].playerId)
			c.totalPlayerDamageDealt = 0;
		c.killedBy = PlayerHandler.players[i].playerId;
		applySmite(i, damage);
		if (c.soulSplitDelay <= 0) {
			applySoulSplit(i, damage);
		}
		switch(damageMask) {
			case 1:
			/*if (!Server.playerHandler.players[i].getHitUpdateRequired()){
				Server.playerHandler.players[i].setHitDiff(damage);
				Server.playerHandler.players[i].setHitUpdateRequired(true);
			} else {
				Server.playerHandler.players[i].setHitDiff2(damage);
				Server.playerHandler.players[i].setHitUpdateRequired2(true);			
			}*/
			//Server.playerHandler.players[i].playerLevel[3] -= damage;
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			o.getPA().refreshSkill(3);
			break;
		
			case 2:
			/*if (!Server.playerHandler.players[i].getHitUpdateRequired2()){
				Server.playerHandler.players[i].setHitDiff2(damage);
				Server.playerHandler.players[i].setHitUpdateRequired2(true);
			} else {
				Server.playerHandler.players[i].setHitDiff(damage);
				Server.playerHandler.players[i].setHitUpdateRequired(true);			
			}*/
			//Server.playerHandler.players[i].playerLevel[3] -= damage;
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;	
			c.doubleHit = false;
			o.getPA().refreshSkill(3);
			break;			
		}
		PlayerHandler.players[i].handleHitMask(damage);
	}
	
	
public void applyPlayerMeleeDamage(int i, int damageMask, int damage){
		c.previousDamage = damage;
		Client o = (Client) PlayerHandler.players[i];
		if(o == null) {
			return;
		}
		// int damage = 0;
		boolean veracsEffect = false;
		boolean guthansEffect = false;
		if (c.getPA().fullVeracs()) {
			if (Misc.random(4) == 1) {
				veracsEffect = true;				
			}		
		}




		if (o.playerEquipment[o.playerShield] == 13742) {
			if (damage > 0) {
				if (Misc.random(10) <= 7) {
					damage *= 0.25;
				}
			}
		}
		
		if (o.playerEquipment[o.playerShield] == 13740) {
			if (damage > 0) {
								damage *= 0.7;
				}
	}
	



		if (c.getPA().fullGuthans()) {
			if (Misc.random(4) == 1) {
				guthansEffect = true;
			}		
		}
		/*if (damageMask == 1) {
			damage = c.delayedDamage;
			c.delayedDamage = 0;
		} else {
			damage = c.delayedDamage2;
			c.delayedDamage2 = 0;
		}*/
		if(Misc.random(o.getCombat().calculateMeleeDefence()) > Misc.random(calculateMeleeAttack()) && !veracsEffect) {
			damage = 0;
			c.bonusAttack = 0;
		} else if (c.playerEquipment[c.playerWeapon] == 5698 && o.poisonDamage <= 0 && Misc.random(3) == 1) {
			o.getPA().appendPoison(13);
			c.bonusAttack += damage/3;
		} else {
			c.bonusAttack += damage/3;
		}
		if (c.maxNextHit) {
			damage = calculateMeleeMaxHit();
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			o.gfx0(398);		
		}
		if (c.ssSpec && damageMask == 2) {
			damage = 5 + Misc.random(11);
			c.ssSpec = false;
		}
		if(damage > 20) { // Soak Melee
			o.soaked = (int) (damage * getBonus(o, ABSORB_MELEE_BONUS));
			damage -= o.soaked;
		}
		if (PlayerHandler.players[i].playerLevel[3] - damage < 0) { 
			damage = PlayerHandler.players[i].playerLevel[3];
		}
		if (o.vengOn && damage > 0)
			appendVengeance(i, damage);
		if (damage > 0)
			applyRecoil(damage, i);
		switch(c.specEffect) {
			case 1: // dragon scimmy special
			if(damage > 0) {
				if(o.prayerActive[16] || o.prayerActive[17] || o.prayerActive[18]) {
					o.headIcon = -1;
					o.getPA().sendFrame36(c.PRAYER_GLOW[16], 0);
					o.getPA().sendFrame36(c.PRAYER_GLOW[17], 0);
					o.getPA().sendFrame36(c.PRAYER_GLOW[18], 0);					
				}
				o.sendMessage("You have been injured!");
				o.stopPrayerDelay = System.currentTimeMillis();
				o.prayerActive[16] = false;
				o.prayerActive[17] = false;
				o.prayerActive[18] = false;
				o.getPA().requestUpdates();		
			}
			break;
			case 2:
				if (damage > 0) {
					if (o.freezeTimer <= 0)
						o.freezeTimer = 30;
					o.gfx0(369);
					o.sendMessage("You have been frozen.");
					o.frozenBy = c.playerId;
					o.stopMovement();
					c.sendMessage("You freeze your enemy.");
				}		
			break;
			case 3:
				if (damage > 0) {
					o.playerLevel[1] -= damage;
					o.sendMessage("You feel weak.");
					if (o.playerLevel[1] < 1)
						o.playerLevel[1] = 1;
					o.getPA().refreshSkill(1);
				}
			break;
			case 4:
				if (damage > 0) {
					if (c.playerLevel[3] + damage > c.getLevelForXP(c.playerXP[3]))
						if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]));
						else 
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
					else 
						c.playerLevel[3] += damage;
					c.getPA().refreshSkill(3);
				}
			break;
			case 5:
				c.clawDelay = 2;
			break;
		}
		c.specEffect = 0;
		if(c.fightMode == 3) {
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 0); 
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 1);
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 2); 				
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 3);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(1);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
		} else {
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE), c.fightMode); 
			c.getPA().addSkillXP((damage*Config.MELEE_EXP_RATE/3), 3);
			c.getPA().refreshSkill(c.fightMode);
			c.getPA().refreshSkill(3);
		}
		PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
		PlayerHandler.players[i].underAttackBy = c.playerId;
		PlayerHandler.players[i].killerId = c.playerId;	
		PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
		if (c.killedBy != PlayerHandler.players[i].playerId)
			c.totalPlayerDamageDealt = 0;
		c.killedBy = PlayerHandler.players[i].playerId;
		applySmite(i, damage);
		if (c.soulSplitDelay <= 0) {
			applySoulSplit(i, damage);
		}
		switch(damageMask) {
			case 1:
			/*if (!Server.playerHandler.players[i].getHitUpdateRequired()){
				Server.playerHandler.players[i].setHitDiff(damage);
				Server.playerHandler.players[i].setHitUpdateRequired(true);
			} else {
				Server.playerHandler.players[i].setHitDiff2(damage);
				Server.playerHandler.players[i].setHitUpdateRequired2(true);			
			}*/
			//Server.playerHandler.players[i].playerLevel[3] -= damage;
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			o.getPA().refreshSkill(3);
			break;
		
			case 2:
			/*if (!Server.playerHandler.players[i].getHitUpdateRequired2()){
				Server.playerHandler.players[i].setHitDiff2(damage);
				Server.playerHandler.players[i].setHitUpdateRequired2(true);
			} else {
				Server.playerHandler.players[i].setHitDiff(damage);
				Server.playerHandler.players[i].setHitUpdateRequired(true);			
			}*/
			//Server.playerHandler.players[i].playerLevel[3] -= damage;
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;	
			c.doubleHit = false;
			o.getPA().refreshSkill(3);
			break;			
		}
		PlayerHandler.players[i].handleHitMask(damage);
	}
	
	public void deflectDamage(int damage) {
		int damage2 = 0;
		if (damage < 10)
			damage2 = 0;
		else 
			damage2 = damage/10;
		c.dealDamage(damage2);
	}
	public void applySmite(int index, int damage) {
		if (!c.prayerActive[23] && !c.curseActive[18])
			return;
		if (damage <= 0)
			return;
		if (PlayerHandler.players[index] != null) { 
			Client c2 = (Client)PlayerHandler.players[index];
			if(c.curseActive[18] && !c.prayerActive[23] && c.playerLevel[3] <= 99) {
						int heal = (int)(damage/5);
						if(c.playerLevel[3] + heal >= c.getPA().getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
			}
			c2.playerLevel[5] -= (int)(damage/4);
			if (c2.playerLevel[5] <= 0) {
				c2.playerLevel[5] = 0;
				c2.getCombat().resetPrayers();
			}
			c2.getPA().refreshSkill(5);
		}
	
	}
	
	public void applySoulSplit(int index, int damage) {
		if (!c.curseActive[18])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			if (damage > 0) {
				c2.playerLevel[5] -= 1;
				if (c2.playerLevel[5] <= 0) {
					c2.playerLevel[5] = 0;
					c2.getCombat().resetPrayers();
				}
				if (c.playerLevel[3] >= c.calculateMaxLifePoints()) {
					c.playerLevel[3] += 0;
				} else {
					if (c.playerLevel[3] + ((damage * 4)/20) < c.calculateMaxLifePoints()) {
						c.playerLevel[3] += (damage * 4) / 20;
					} else {
						c.playerLevel[3] += (c.getLevelForXP(c.playerXP[3]) - c.playerLevel[3]);
					}	
				}
				c.getPA().refreshSkill(3);
				c2.getPA().refreshSkill(5);
			}
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2263, 31, 31, - c.oldPlayerIndex - 1, 0);
			c.soulSplitDelay = 4;
			/*EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.soulSplitDelay > 0) {
						c.soulSplitDelay--;
					}
					if (c.soulSplitDelay == 3) {
						c2.gfx0(1738);
					}
					if (c.soulSplitDelay == 2) {
						int offX2 = (oY - pY)* -1;
						int offY2 = (oX - pX)* -1;
						c.getPA().createPlayersProjectile(oX, oY, offX2, offY2, 50, 45, 2263, 31, 31, - c.playerId - 1, 0);
					}
					if (c.soulSplitDelay == 0) {	
						s.stop();
					}
				}
			}, 500);*/
		}
	}
	
	public void applyLeeches(int index) {
		if (Misc.random(10) == 0) {
			leechAttack(index);
		}
		if (Misc.random(10) == 0) {
			leechDefence(index);
		}
		if (Misc.random(10) == 0) {
			leechStrength(index);
		}
		if (Misc.random(10) == 0) {
			leechSpecial(index);
		}
		if (Misc.random(10) == 0) {
			leechRanged(index);
		}
		if (Misc.random(10) == 0) {
			leechMagic(index);
		}
		if (Misc.random(10) == 0) {
			leechEnergy(index);
		}
	}
	
	public void leechAttack(int index) {
		if (!c.curseActive[10])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's attack.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2231, 43, 31, - c.oldPlayerIndex - 1, 1);
			c.leechAttackDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechAttackDelay > 0) {
						c.leechAttackDelay--;
					}
					if (c.leechAttackDelay == 1) {
						c2.gfx0(2232);
					}
					if (c.leechAttackDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void leechRanged(int index) {
		if (!c.curseActive[11])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's range.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2236, 43, 31, - c.oldPlayerIndex - 1, 0);
			c.leechRangedDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechRangedDelay > 0) {
						c.leechRangedDelay--;
					}
					if (c.leechRangedDelay == 1) {
						c2.gfx0(2238);
					}
					if (c.leechRangedDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void leechMagic(int index) {
		if (!c.curseActive[12])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's magic.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2240, 43, 31, - c.oldPlayerIndex - 1, 2);
			c.leechMagicDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechMagicDelay > 0) {
						c.leechMagicDelay--;
					}
					if (c.leechMagicDelay == 1) {
						c2.gfx0(2242);
					}
					if (c.leechMagicDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void leechDefence(int index) {
		if (!c.curseActive[13])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's defence.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2244, 43, 31, - c.oldPlayerIndex - 1, 3);
			c.leechDefenceDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechDefenceDelay > 0) {
						c.leechDefenceDelay--;
					}
					if (c.leechDefenceDelay == 1) {
						c2.gfx0(2246);
					}
					if (c.leechDefenceDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void leechStrength(int index) {
		if (!c.curseActive[14])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's strength.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2248, 43, 31, - c.oldPlayerIndex - 1, 4);
			c.leechStrengthDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechStrengthDelay > 0) {
						c.leechStrengthDelay--;
					}
					if (c.leechStrengthDelay == 1) {
						c2.gfx0(2250);
					}
					if (c.leechStrengthDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void leechEnergy(int index) {
		if (!c.curseActive[15])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's run energy.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2252, 43, 31, - c.oldPlayerIndex - 1, 5);
			c.leechEnergyDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechEnergyDelay > 0) {
						c.leechEnergyDelay--;
					}
					if (c.leechEnergyDelay == 1) {
						c2.gfx0(2254);
					}
					if (c.leechEnergyDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void leechSpecial(int index) {
		if (!c.curseActive[16])
			return;
		if (PlayerHandler.players[index] != null) { 
			final Client c2 = (Client)PlayerHandler.players[index];
			final int pX = c.getX();
			final int pY = c.getY();
			final int oX = c2.getX();
			final int oY = c2.getY();
			int offX = (pY - oY)* -1;
			int offY = (pX - oX)* -1;
			c.sendMessage("You leech your opponent's special attack.");
			c.startAnimation(12575);
			c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2256, 43, 31, - c.oldPlayerIndex - 1, 6);
			c.leechSpecialDelay = 2;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer s) {
					if (c.leechSpecialDelay > 0) {
						c.leechSpecialDelay--;
					}
					if (c.leechSpecialDelay == 1) {
						c2.gfx0(2258);
						if (c.specAmount >= 10)
							return;
						if (c2.specAmount <= 0)
							return;
						c.specAmount += 1;
						c2.specAmount -= 1;
						c2.sendMessage("Your special attack has been drained.");
					}
					if (c.leechSpecialDelay == 0) {	
						s.stop();
					}
				}
			}, 500);
		}
	}
	
	public void fireProjectilePlayer() {
		if(c.oldPlayerIndex > 0) {
			if(PlayerHandler.players[c.oldPlayerIndex] != null) {
				c.projectileStage = 2;
				int pX = c.getX();
				int pY = c.getY();
				int oX = PlayerHandler.players[c.oldPlayerIndex].getX();
				int oY = PlayerHandler.players[c.oldPlayerIndex].getY();
				int offX = (pY - oY)* -1;
				int offY = (pX - oX)* -1;	
				if(c.playerEquipment[c.playerWeapon] == 15241) {
					if(!c.specGfx) {
						c.gfx0(2138);
					}
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, 55, getRangeProjectileGFX(), 22, 22, c.oldPlayerIndex - 1, getStartDelay(), -1);
					c.handCannonDestory();	
					c.specGfx = false;	
				} else if(!c.msbSpec)
					c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 43, 31, - c.oldPlayerIndex - 1, getStartDelay());
				else if (c.msbSpec) {
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 43, 31, - c.oldPlayerIndex - 1, getStartDelay(), 10);
					c.msbSpec = false;
				}
				if (usingDbow())
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 60, 31, - c.oldPlayerIndex - 1, getStartDelay(), 35);
			}
		}
	}
	
	public boolean usingDbow() {
		return  (c.playerEquipment[c.playerWeapon] == 11235  || c.playerEquipment[c.playerWeapon] == 15701 || c.playerEquipment[c.playerWeapon] == 15702 || c.playerEquipment[c.playerWeapon] == 15703 || c.playerEquipment[c.playerWeapon] == 15704 || c.playerEquipment[c.playerWeapon] == 19143 || c.playerEquipment[c.playerWeapon] == 19146 || c.playerEquipment[c.playerWeapon] == 19149 ); 
	}
	
	
	

	
	/**Prayer**/
		
	public void activatePrayer(int i) {
		if(c.duelRule[7]){
			for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows 
				c.prayerActive[p] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);	
			}
			c.sendMessage("Prayer has been disabled in this duel!");
			return;
		}
		if(c.inRFD()){
			for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows 
				c.prayerActive[p] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);	
			}
			c.sendMessage("You cannot use prayer in here!");
			return;
		}
		if (i == 24 && c.getPA().getLevelForXP(c.playerXP[1]) < 70) {
			c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
			c.sendMessage("You need 60 Defence to use Chivarly");
			return;
		}
		if (i == 25 && c.getPA().getLevelForXP(c.playerXP[1]) < 70) {
			c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
			c.sendMessage("You need 70 defence to use Piety");
			return;
		}

		int[] defPray = {0,5,13,24,25};
		int[] strPray = {1,6,14,24,25};
		int[] atkPray = {2,7,15,24,25};
		int[] rangePray = {3,11,19};
		int[] magePray = {4,12,20};

		if(c.playerLevel[5] > 0 || !Config.PRAYER_POINTS_REQUIRED){
			if(c.getPA().getLevelForXP(c.playerXP[5]) >= c.PRAYER_LEVEL_REQUIRED[i] || !Config.PRAYER_LEVEL_REQUIRED) {
				boolean headIcon = false;
				switch(i) {
					case 0:
					case 5:
					case 13:
					if(c.prayerActive[i] == false) {
						for (int j = 0; j < defPray.length; j++) {
							if (defPray[j] != i) {
								c.prayerActive[defPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[defPray[j]], 0);
							}								
						}
					}
					break;
					
					case 1:
					case 6:
					case 14:
					if(c.prayerActive[i] == false) {
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								c.prayerActive[strPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[strPray[j]], 0);
							}								
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								c.prayerActive[rangePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[rangePray[j]], 0);
							}								
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								c.prayerActive[magePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[magePray[j]], 0);
							}								
						}
					}
					break;
					
					case 2:
					case 7:
					case 15:
					if(c.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								c.prayerActive[atkPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[atkPray[j]], 0);
							}								
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								c.prayerActive[rangePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[rangePray[j]], 0);
							}								
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								c.prayerActive[magePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[magePray[j]], 0);
							}								
						}
					}
					break;
					
					case 3://range prays
					case 11:
					case 19:
					if(c.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								c.prayerActive[atkPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[atkPray[j]], 0);
							}								
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								c.prayerActive[strPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[strPray[j]], 0);
							}								
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								c.prayerActive[rangePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[rangePray[j]], 0);
							}								
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								c.prayerActive[magePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[magePray[j]], 0);
							}								
						}
					}
					break;
					case 4:
					case 12:
					case 20:
					if(c.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								c.prayerActive[atkPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[atkPray[j]], 0);
							}								
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								c.prayerActive[strPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[strPray[j]], 0);
							}								
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								c.prayerActive[rangePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[rangePray[j]], 0);
							}								
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								c.prayerActive[magePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[magePray[j]], 0);
							}								
						}
					}
					break;
					case 10:
						c.lastProtItem = System.currentTimeMillis();
					break;
					

					case 16:					
					case 17:
					case 18:
					if(System.currentTimeMillis() - c.stopPrayerDelay < 5000) {
						c.sendMessage("You have been injured and can't use this prayer!");
						c.getPA().sendFrame36(c.PRAYER_GLOW[16], 0);
						c.getPA().sendFrame36(c.PRAYER_GLOW[17], 0);
						c.getPA().sendFrame36(c.PRAYER_GLOW[18], 0);
						return;
					}
					if (i == 16)
						c.protMageDelay = System.currentTimeMillis();
					else if (i == 17)
						c.protRangeDelay = System.currentTimeMillis();
					else if (i == 18)
						c.protMeleeDelay = System.currentTimeMillis();
						
					case 21:
					case 22:
					case 23:
					headIcon = true;		
					for(int p = 16; p < 24; p++) {
						if(i != p && p != 19 && p != 20) {
							c.prayerActive[p] = false;
							c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);
						}
					}
					break;
					case 24:
					if (c.prayerActive[i] == false) {

						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								c.prayerActive[atkPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[atkPray[j]], 0);
							}								
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								c.prayerActive[strPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[strPray[j]], 0);
							}								
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								c.prayerActive[rangePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[rangePray[j]], 0);
							}								
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								c.prayerActive[magePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[magePray[j]], 0);
							}								
						}
						for (int j = 0; j < defPray.length; j++) {
							if (defPray[j] != i) {
								c.prayerActive[defPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[defPray[j]], 0);
							}								
						}
					}
					break;
					case 25:

					if (c.prayerActive[i] == false) {

						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								c.prayerActive[atkPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[atkPray[j]], 0);
							}								
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								c.prayerActive[strPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[strPray[j]], 0);
							}								
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								c.prayerActive[rangePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[rangePray[j]], 0);
							}								
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								c.prayerActive[magePray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[magePray[j]], 0);
							}								
						}
						for (int j = 0; j < defPray.length; j++) {
							if (defPray[j] != i) {
								c.prayerActive[defPray[j]] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[defPray[j]], 0);
							}								
						}
					}
					break;
				}
				
				if(!headIcon) {
					if(c.prayerActive[i] == false) {
						c.prayerActive[i] = true;
						c.getPA().sendFrame36(c.PRAYER_GLOW[i], 1);					
					} else {
						c.prayerActive[i] = false;
						c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
					}
				} else {
					if(c.prayerActive[i] == false) {
						c.prayerActive[i] = true;
						c.getPA().sendFrame36(c.PRAYER_GLOW[i], 1);
						c.headIcon = c.PRAYER_HEAD_ICONS[i];
						c.getPA().requestUpdates();
					} else {
						c.prayerActive[i] = false;
						c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
						c.headIcon = -1;
						c.getPA().requestUpdates();
					}
				}
			} else {
				c.getPA().sendFrame36(c.PRAYER_GLOW[i],0);
				c.getPA().sendFrame126("You need a @blu@Prayer level of "+c.PRAYER_LEVEL_REQUIRED[i]+" to use "+c.PRAYER_NAME[i]+".", 357);
				c.getPA().sendFrame126("Click here to continue", 358);
				c.getPA().sendFrame164(356);
				c.nextChat = 0;
			}
		} else {
			c.getPA().sendFrame36(c.PRAYER_GLOW[i],0);
			c.sendMessage("You have run out of prayer points!");
		}	
				
	}
		
	/**
	*Specials
	**/
	
	public void activateSpecial(int weapon, int i){
		if(NPCHandler.npcs[i] == null && c.npcIndex > 0) {
			return;
		}
		if(PlayerHandler.players[i] == null && c.playerIndex > 0) {
			return;
		}
		c.doubleHit = false;
		c.specEffect = 0;
		c.projectileStage = 0;
		c.specMaxHitIncrease = 2;
		if(c.npcIndex > 0) {
			c.oldNpcIndex = i;
		} else if (c.playerIndex > 0){
			c.oldPlayerIndex = i;
			PlayerHandler.players[i].underAttackBy = c.playerId;
			PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
			PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
			PlayerHandler.players[i].killerId = c.playerId;
		}
		switch(weapon) {
			
			case 1305: // dragon long
			c.gfx100(248);
			c.startAnimation(1058);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specAccuracy = 1.10;
			c.specDamage = 1.20;
			break;
			
			case 1215: // dragon daggers
			case 1231:
			case 5680:
			case 5698:
c.gfx100(252);
                        c.startAnimation(1062);
                        c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
                        c.doubleHit = true;
                        c.specAccuracy = 1.50;
                        c.specDamage = 1.10;
                        break;

			case 10887:
			c.gfx100(1027);
            c.specAccuracy = 5.0;
			c.specDamage = 1.15;
            c.startAnimation(5870);
            c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 14484:
				EventManager.getSingleton().addEvent(new Event() {
					@Override
					public void execute(EventContainer e) {	
						if (c.clawDelay > 0) {
							c.clawDelay--;
						}
						if (c.clawDelay == 1) {
							c.delayedDamage = c.clawDamage / 4;
							c.delayedDamage2 = (c.clawDamage / 4) + 1;
							if (c.clawType == 2) {
								c.getCombat().applyNpcMeleeDamage(c.clawIndex, 1, c.clawDamage / 4);
							}
							if (c.clawType == 1) {
								c.getCombat().applyPlayerMeleeDamage(c.clawIndex, 1, c.clawDamage / 4);
							}
							if (c.clawType == 2) {
								c.getCombat().applyNpcMeleeDamage(c.clawIndex, 2, (c.clawDamage / 4) + 1);
							}
							if (c.clawType == 1) {
								c.getCombat().applyPlayerMeleeDamage(c.clawIndex, 2, (c.clawDamage / 4) + 1);
							}
							c.clawDelay = 0;
							c.specEffect = 0;
							c.previousDamage = 0;
							c.usingClaws = false;
							c.clawType = 0;
							e.stop();
						}
					}
				},500);

				c.gfx0(1950);
				c.startAnimation(10961);
				c.specAccuracy = 19.9;
				c.clawDamage = 0;

				if (c.playerIndex > 0) {
					Client o = (Client) PlayerHandler.players[c.playerIndex];
					if (Misc.random(calculateMeleeAttack() * 2) > Misc.random(o.getCombat().calculateMeleeDefence())) {
						c.clawDamage = Misc.random(calculateMeleeMaxHit()) + (calculateMeleeMaxHit() / 3);
					}
					c.clawIndex = c.playerIndex;
					c.clawType = 1;
				} else if (c.npcIndex > 0) {
					NPC n = NPCHandler.npcs[c.npcIndex];

					if (Misc.random(calculateMeleeAttack()) > Misc.random(n.defence)) {
						c.clawDamage = Misc.random(calculateMeleeMaxHit() + Misc.random(2));
					}
					c.clawIndex = c.npcIndex;
					c.clawType = 2;
				}

				c.doubleHit = true;
				c.usingClaws = true;
				c.specEffect = 5;
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 4151: // whip
			if(NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.startAnimation(1658);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 15441: // whip
			if(NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.startAnimation(1658);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 15442: // whip
			if(NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.startAnimation(1658);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 15443: // whip
			if(NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.startAnimation(1658);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 15444: // whip
			if(NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.startAnimation(1658);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
				case 11694: // ags
				c.startAnimation(7074);
				c.specDamage = 1.65;
				c.specAccuracy = 1.10;
				c.gfx0(1222);
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				break;

			case 19780:
			c.gfx0(1247); 
			c.startAnimation(4000); 
			c.typeOfDamageMask = 3;
			if (c.playerIndex > 0) { 
				Client opp = (Client)PlayerHandler.players[c.playerIndex]; 
				if(opp != null)
					opp.gfx0(1248);
					opp.typeOfDamageMask = 3;
			} else if (c.npcIndex > 0) { 
				NPC opp = NPCHandler.npcs[c.npcIndex]; 
				if(opp != null) 
					opp.gfx0(1248);
			}
			c.specAccuracy = 5.00; 
			c.specDamage = 1.80; 
			c.ssSpec = true;
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]) .toLowerCase());
			break;
			
			/*case 19780:
				c.startAnimation(4000);
				c.specAccuracy = 9.00;
				c.specDamage = 2.25;
				c.gfx100(1247);
				c.typeOfDamageMask = 3;
				c.Kspec = true;
         			EventManager.getSingleton().addEvent(new Event() {
					int Time = 2;
					public void execute(EventContainer KorasiSpec) {
           		 			Client o = (Client)Server.playerHandler.players[c.playerIndex];
                	   	 		NPC n = Server.npcHandler.npcs[c.npcIndex];
								if (Time == 1) {
										if (c.npcIndex > 0) {
											n.gfx100(1248);
											npcMageDamage((int) (Misc.random(calculateMeleeMaxHit())));
											n.underAttack = true;
										} else if (c.playerIndex > 0) {
											o.gfx100(1248);
											o.typeOfDamageMask = 3;
											applyMageDamage((int) (Misc.random(calculateMeleeMaxHit())));
										}
								}
						if (Time < 1) {
							KorasiSpec.stop();
							c.Kspec = false;
     	                	    		return; 
						}
						if (Time > 0) {
							Time--;
						}
					}
				}, 290);
			break;*/

			case 11730:
			c.gfx100(1224);
			c.startAnimation(7072);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.doubleHit = true;
			c.ssSpec = true;
			c.specAccuracy = 1.30;
			break;

				case 13905: // Vesta spear
				c.startAnimation(10499);
				c.gfx0(1835);
                                c.specAccuracy = 1.25;
                                c.specEffect = 6;
                                c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				break;

				case 13899: // Vesta LongSword
				c.startAnimation(10502);
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()+1);
				c.specDamage = 1.35;
				c.specAccuracy = 2.00;
				break;
				case 13902: // Statius
				c.startAnimation(10505);
				c.gfx0(1840);
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()+1);
				c.specDamage = 1.35;
				c.specAccuracy = 2.25;
				break;
			
			case 11700:
				c.startAnimation(7070);		
				c.gfx0(1221);
				if (c.playerIndex > 0) {
			        } else {
				NPCHandler.npcs[c.npcIndex].gfx0(2104);
				}
				c.specAccuracy = 1.35;
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.specEffect = 2;
			break;
			
			case 11696:
				c.startAnimation(7073);
				c.gfx0(1223);
				c.specDamage = 1.30;
				c.specAccuracy = 1.85;
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.specEffect = 3;
			break;
			
			case 11698:
				c.startAnimation(7071);
				c.gfx0(1220);
				c.specAccuracy = 1.25;
				c.specEffect = 4;
				c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 3204: // d hally
			c.gfx100(282);
			c.startAnimation(1203);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if(NPCHandler.npcs[i] != null && c.npcIndex > 0) {
				if(!c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 1)){
					c.doubleHit = true;
				}
			}
			if(PlayerHandler.players[i] != null && c.playerIndex > 0) {
				if(!c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[i].getX(),PlayerHandler.players[i].getY(), 1)){
					c.doubleHit = true;
					c.delayedDamage2 = Misc.random(calculateMeleeMaxHit());
				}
			}
			break;
			
			case 4153: // maul
			c.startAnimation(1667);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			/*if (c.playerIndex > 0)
				gmaulPlayer(i);
			else
				gmaulNpc(i);*/
			c.gfx100(337);
			break;
			
			case 4587: // dscimmy
			c.gfx100(347);
			c.specEffect = 1;
			c.startAnimation(1872);
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 1434: // mace
			c.startAnimation(1060);
			c.gfx100(251);
			c.specMaxHitIncrease = 3;
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase())+1;
			c.specDamage = 1.20;
			c.specAccuracy = 1.15;
			break;
			
			case 859: // magic long
			c.usingBow = true;
			c.bowSpecShot = 3;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();	
			c.lastWeaponUsed = weapon;
			c.startAnimation(426);
			c.gfx100(250);	
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.projectileStage = 1;
			if (c.fightMode == 2)
				c.attackTimer--;
			break;
			
			case 15241: // hand cannon spec!!
			c.usingBow = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();	
			c.lastWeaponUsed = weapon;
			c.startAnimation(12175);
			c.specAccuracy = 10.0;
			c.specDamage = 1.75;
			c.hitDelay = 5;
			c.attackTimer-= 7;
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
			if (c.playerIndex > 0)
			fireProjectilePlayer();
			else if (c.npcIndex > 0)
			fireProjectileNpc();
			break;
			
			case 861: // magic short	
			c.usingBow = true;			
			c.bowSpecShot = 1;
			c.specAccuracy = 1.60;
			c.specDamage = 2.10;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();	
			c.lastWeaponUsed = weapon;
			c.startAnimation(1074);
			c.hitDelay = 3;
			c.projectileStage = 1;
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				fireProjectilePlayer();
			else if (c.npcIndex > 0)
				fireProjectileNpc();	
			break;
			
			
			case 19143:
			case 19146:
			case 19149:
			c.usingBow = true;
			c.dbowSpec = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			c.lastWeaponUsed = weapon;
			c.hitDelay = 3;
			c.startAnimation(426);
			c.projectileStage = 1;
			c.gfx100(getRangeStartGFX());
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				fireProjectilePlayer();
			else if (c.npcIndex > 0)
				fireProjectileNpc();
			c.specAccuracy = 1.75;
			c.specDamage = 1.50;
			break;
			case 11235: // dark bow
			case 15701:
			case 15702:
			case 15703:
			case 15704:
			c.usingBow = true;
			c.dbowSpec = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			c.lastWeaponUsed = weapon;
			c.hitDelay = 3;
			c.startAnimation(426);
			c.projectileStage = 1;
			c.gfx100(getRangeStartGFX());
			c.hitDelay = getHitDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				fireProjectilePlayer();
			else if (c.npcIndex > 0)
				fireProjectileNpc();
			c.specAccuracy = 2.40;
			c.specDamage = 1.75;
			break;
		}
		c.delayedDamage = Misc.random(calculateMeleeMaxHit());
		c.delayedDamage2 = Misc.random(calculateMeleeMaxHit());
		c.usingSpecial = false;
		c.getItems().updateSpecialBar();
	}
	
	
	
	public boolean checkSpecAmount(int weapon) {
		switch(weapon) {
			case 1249:
			case 1215:
			case 1231:
			case 5680:
			case 5698:
			case 1305:
			case 1434:
			case 13899:
			if (c.playerEquipment[c.playerRing] == 19669 && c.specAmount >= 2.5 * .9) {//ring of vigour
				c.specAmount -= 2.5 * .9;
				c.getItems().addSpecialBar(weapon);
				return true;
			} else if (c.specAmount >= 2.5) {
				c.specAmount -= 2.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			case 4151:
			case 15441:
			case 15442:
			case 15443:
			case 15444:
            case 11694:
			case 15241:
			case 14484:
			case 11698:
			case 4153:
			case 13902:
			case 13905: 
			case 15016: 
			case 15015: 
			case 10887:
			if (c.playerEquipment[c.playerRing] == 19669 && c.specAmount >= 5 * .9) {//ring of vigour				
				c.specAmount -= 5 * .9;
				c.getItems().addSpecialBar(weapon);
				return true;				
			} else if (c.specAmount >= 5) {
				c.specAmount -= 5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			
			case 3204:
			if (c.playerEquipment[c.playerRing] == 19669 && c.specAmount >= 3 * .9) {//ring of vigour
				c.specAmount -= 3 * .9;
				c.getItems().addSpecialBar(weapon);
				return true;
			} else if (c.specAmount >= 3) {
				c.specAmount -= 3;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			
			case 1377:
			case 11696:
			case 11730:
			case 15486:
			if (c.playerEquipment[c.playerRing] == 19669 && c.specAmount >= 10 * .9) {//ring of vigour
				c.specAmount -= 10 * .9;
				c.getItems().addSpecialBar(weapon);
				return true;
			} else if (c.specAmount >= 10) {
				c.specAmount -= 10;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			
			case 19780:
			if (c.playerEquipment[c.playerRing] == 19669 && c.specAmount >= 6 * .9) {//ring of vigour
				c.specAmount -= 6 * .9;
				c.getItems().addSpecialBar(weapon);
				return true;
			} else if (c.specAmount >= 6) {
				c.specAmount -= 6;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			
			case 4587:
			case 859:
			case 861:
			case 19143:
			case 19146:
			case 19149:
			case 11235:
			case 15701:
			case 15702:
			case 15703:
			case 15704:
			case 11700:
			if (c.playerEquipment[c.playerRing] == 19669 && c.specAmount >= 5.5 * .9) {//ring of vigour
				c.specAmount -= 5.5 * .9;
				c.getItems().addSpecialBar(weapon);
				return true;
			} else if (c.specAmount >= 5.5) {
				c.specAmount -= 5.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

			
			default:
			return true; // incase u want to test a weapon
		}
	}
	
	public void resetPlayerAttack() {
		c.usingMagic = false;
		c.npcIndex = 0;
		c.faceUpdate(0);
		c.playerIndex = 0;
		c.attackingId = 0;
		c.getPA().resetFollow();
		//c.sendMessage("Reset attack.");
	}
	
	public int getCombatDifference(int combat1, int combat2) {
		if(combat1 > combat2) {
			return (combat1 - combat2);
		}
		if(combat2 > combat1) {
			return (combat2 - combat1);
		}	
		return 0;
	}
	
	/**
	*Get killer id 
	**/
	
	public int getKillerId(int playerId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {	
			if (PlayerHandler.players[i] != null) {
				if(PlayerHandler.players[i].killedBy == playerId) {
					if (PlayerHandler.players[i].withinDistance(PlayerHandler.players[playerId])) {
						if(PlayerHandler.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = PlayerHandler.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}	
					PlayerHandler.players[i].totalPlayerDamageDealt = 0;
					PlayerHandler.players[i].killedBy = 0;
				}	
			}
		}				
		return killerId;
	}
		
	
	
		double[] prayerData = {
                1, // Thick Skin.
                1, // Burst of Strength.
                1, // Clarity of Thought.
                1, // Sharp Eye.
                1, // Mystic Will.
                2, // Rock Skin.
                2, // SuperHuman Strength.
                2, // Improved Reflexes.
                0.4, // Rapid restore.
                0.6, // Rapid Heal.
                0.6, // Protect Items.
                1.5, // Hawk eye.
                2, // Mystic Lore.
                4, // Steel Skin.
                4, // Ultimate Strength.
                4, // Incredible Reflexes.
                4, // Protect from Magic.
                4, // Protect from Missiles.
                4, // Protect from Melee.
                4, // Eagle Eye.
                4, // Mystic Might.
                1, // Retribution.
                2, // Redemption.
                6, // Smite.
                8, // Chivalry.
                8, // Piety.
        };

        double[] curseData = {
		1, // Protect Item
		1, // Sap Warrior
		1, // Sap Range
		1, // Sap Mage
		1, // Sap Spirit
		2, // Berserker
		3, // Deflect Summoning
		3, // Deflect Mage 7
		3, // Deflect Range 8 
		3, // Deflect Melee 9
		3.5, // Leech Attack
		3.5, // Leech Range
		3.5, // Leech Mage
		3.5, // Leech Defence
		3.5, // Leech Strength
		3.5, // Leech Energy
		3.5, // Leech Special
		2, // Wrath
		7, // Soul Split
		8, // Turmoil
	};
	
        public void handlePrayerDrain() {
		c.usingPrayer = false;
		double toRemove = 0.0;
		for (int j = 0; j < prayerData.length; j++) {
			if (c.prayerActive[j]) {
				toRemove += prayerData[j]/20;
				c.usingPrayer = true;
			}
		}
		for (int j = 0; j < curseData.length; j++) {
			if (c.curseActive[j]) {
				toRemove += curseData[j]/20;
				c.usingPrayer = true;
			}
		}
		if (toRemove > 0) {
			toRemove /= (1 + (0.035 * getBonus(c, PRAYER_BONUS)));		
		}
		c.prayerPoint -= toRemove;
		if (c.prayerPoint <= 0) {
			c.prayerPoint = 1.0 + c.prayerPoint;
			reducePrayerLevel();
		}
	}
	
	public void reducePrayerLevel() {
		if(c.playerLevel[5] - 1 > 0) {
			c.playerLevel[5] -= 1;
		} else {
			c.sendMessage("You have run out of prayer points!");
			c.playerLevel[5] = 0;
			c.getCombat().resetPrayers();
			c.prayerId = -1;	
		}
		c.getPA().refreshSkill(5);
	}
	
	public void resetPrayers() {
		for(int i = 0; i < c.prayerActive.length; i++) {
			c.prayerActive[i] = false;
			c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
		}
		for(int i = 0; i < c.curseActive.length; i++) {
			c.curseActive[i] = false;
			c.getPA().sendFrame36(c.CURSE_GLOW[i], 0);
		}
		c.headIcon = -1;
		c.getPA().requestUpdates();
	}
	
	/**
	* Wildy and duel info
	**/
	
	public boolean checkReqs() {
		if(PlayerHandler.players[c.playerIndex] == null) {
			return false;
		}
		if (c.playerIndex == c.playerId)
			return false;
		if (c.inPits && PlayerHandler.players[c.playerIndex].inPits)
			return true;
		if(PlayerHandler.players[c.playerIndex].inDuelArena() && c.duelStatus != 5 && !c.usingMagic) {
			if(c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return false;
			}
			c.getTradeAndDuel().requestDuel(c.playerIndex);
			return false;
		}
		if(c.duelStatus == 5 && PlayerHandler.players[c.playerIndex].duelStatus == 5) {
			if(PlayerHandler.players[c.playerIndex].duelingWith == c.getId()) {
				return true;
			} else {
				c.sendMessage("This isn't your opponent!");
				c.getCombat().resetPlayerAttack();
				return false;
			}
		}
		if (c.getPA().getWearingAmount() < 4 && c.duelStatus < 1) {
			c.sendMessage("You must be wearing at least 4 items to attack someone.");
			resetPlayerAttack();
			return false;
		}
		if(!PlayerHandler.players[c.playerIndex].inWild()) {
			c.sendMessage("That player is not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}


		if(!c.inWild()) {
			c.sendMessage("You are not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if(Config.SINGLE_AND_MULTI_ZONES) {
			if(!PlayerHandler.players[c.playerIndex].inMulti()) {	// single combat zones
				if(PlayerHandler.players[c.playerIndex].underAttackBy != c.playerId  && PlayerHandler.players[c.playerIndex].underAttackBy != 0) {
					c.sendMessage("That player is already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
				if(PlayerHandler.players[c.playerIndex].playerId != c.underAttackBy && c.underAttackBy != 0 || c.underAttackBy2 > 0) {
					c.sendMessage("You are already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkMultiBarrageReqs(int i) {
		if(PlayerHandler.players[i] == null) {
			return false;
		}
		if (i == c.playerId)
			return false;
		if (c.inPits && PlayerHandler.players[i].inPits)
			return true;
		if(!PlayerHandler.players[i].inWild()) {
			return false;
		}
		
		if(Config.SINGLE_AND_MULTI_ZONES) {
			if(!PlayerHandler.players[i].inMulti()) {	// single combat zones
				if(PlayerHandler.players[i].underAttackBy != c.playerId  && PlayerHandler.players[i].underAttackBy != 0) {
					c.sendMessage("That player is already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
				if(PlayerHandler.players[i].playerId != c.underAttackBy && c.underAttackBy != 0) {
					c.sendMessage("You are already in combat.");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	*Weapon stand, walk, run, etc emotes
	**/
	
	public void getPlayerAnimIndex(String weaponName){
		c.playerStandIndex = 0x328;
		c.playerTurnIndex = 0x337;
		c.playerWalkIndex = 0x333;
		c.playerTurn180Index = 0x334;
		c.playerTurn90CWIndex = 0x335;
		c.playerTurn90CCWIndex = 0x336;
		c.playerRunIndex = 0x338;
	
		if(weaponName.contains("halberd") || weaponName.contains("guthan")) {
			c.playerStandIndex = 809;
			c.playerWalkIndex = 1146;
			c.playerRunIndex = 1210;
			return;
		}	
		if(weaponName.contains("dharok")) {
			c.playerStandIndex = 0x811;
			c.playerWalkIndex = 0x67F;
			c.playerRunIndex = 0x680;
			return;
		}
		if(weaponName.contains("chaotic maul")) {
			c.playerStandIndex = 1662;
			c.playerWalkIndex = 1663;
			c.playerRunIndex = 1664;
			return;
		}	
		if(weaponName.contains("ahrim")) {
			c.playerStandIndex = 809;
			c.playerWalkIndex = 1146;
			c.playerRunIndex = 1210;
			return;
		}
		if(weaponName.contains("verac")) {
			c.playerStandIndex = 0x328;
			c.playerWalkIndex = 0x333;
			c.playerRunIndex = 824;
			return;
		}
		if (weaponName.contains("chaotic staff")) {
			c.playerStandIndex = 808;
			c.playerRunIndex = 1210;
			c.playerWalkIndex = 1146;
			return;
		}
		if (weaponName.contains("wand") || weaponName.contains("staff") || weaponName.contains("staff") || weaponName.contains("spear")) {
			c.playerStandIndex = 8980;
			c.playerRunIndex = 1210;
			c.playerWalkIndex = 1146;
			return;
		}
		if(weaponName.contains("karil")) {
			c.playerStandIndex = 2074;
			c.playerWalkIndex = 2076;
			c.playerRunIndex = 2077;
			return;
		}
 if(weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("saradomin sw")) {
			c.playerStandIndex = 7047;
			c.playerWalkIndex = 7046;
			c.playerRunIndex = 7039;
			return;
		}							
		if(weaponName.contains("bow")) {
			c.playerStandIndex = 808;
			c.playerWalkIndex = 819;
			c.playerRunIndex = 824;
			return;
		}

		switch(c.playerEquipment[c.playerWeapon]) {	
			case 15241:
			c.playerStandIndex = 12155;
			c.playerWalkIndex = 12154;
			c.playerRunIndex = 12154;
			break;
 			case 4151:
			c.playerStandIndex = 11973;
			c.playerWalkIndex = 11975;
			c.playerRunIndex = 1661;
			break;
			case 15441:
			c.playerStandIndex = 11973;
			c.playerWalkIndex = 11975;
			c.playerRunIndex = 1661;
			break;
			case 15442:
			c.playerStandIndex = 11973;
			c.playerWalkIndex = 11975;
			c.playerRunIndex = 1661;
			break;
			case 15443:
			c.playerStandIndex = 11973;
			c.playerWalkIndex = 11975;
			c.playerRunIndex = 1661;
			break;
			case 15444:
			c.playerStandIndex = 11973;
			c.playerWalkIndex = 11975;
			c.playerRunIndex = 1661;
			break;
			case 10887:
			c.playerStandIndex = 5869;
			c.playerWalkIndex = 5867;
			c.playerRunIndex = 5868;
			break;
			case 6528:
				c.playerStandIndex = 0x811;
				c.playerWalkIndex = 2064;
				c.playerRunIndex = 1664;
			break;
			case 4153:
			c.playerStandIndex = 1662;
			c.playerWalkIndex = 1663;
			c.playerRunIndex = 1664;
			break;
			case 13022:
			c.playerStandIndex = 12155;
			c.playerWalkIndex = 12155;
			c.playerRunIndex = 12154;
			break;
			case 11694:
			case 11696:
			case 11730:
			case 11698:
			case 11700:
			c.playerStandIndex = 4300;
			c.playerWalkIndex = 4306;
			c.playerRunIndex = 4305;
			break;
			case 1305:
			c.playerStandIndex = 809;
			break;
		}
	}
	
	/**
	* Weapon emotes
	**/
	
	public int getWepAnim(String weaponName) {
		if(c.playerEquipment[c.playerWeapon] <= 0) {
			switch(c.fightMode) {
				case 0:
				return 422;			
				case 2:
				return 423;			
				case 1:
				return 451;
			}
		}
		if(weaponName.contains("knife") || weaponName.contains("dart") || weaponName.contains("javelin") || weaponName.contains("thrownaxe") || weaponName.contains("throwing axe")){
			return 806;
		}
		if(weaponName.contains("halberd")) {
			return 440;
		}
		if(weaponName.startsWith("dragon dagger")) {
			return 402;
		}	
		if(weaponName.endsWith("dagger")) {
			return 412;
		}
		if (weaponName.contains("crossbow")) {
			return 4230;
		}
 		if(weaponName.contains("chaotic rapier")) {
			return 386;
		}
 				if(weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("saradomin sword")) {
					switch(c.fightMode) {
				case 0:
				return 7041;		
				case 2:
				return 7041;			
				case 1:
				return 7048;
			}	
		}	
		if(weaponName.contains("scimitar") || weaponName.contains("longsword")) {
			switch(c.fightMode) {
				case 0:
				return 12029;	
				case 1: // New Scimmi models
				return 12029;		
				case 2:
				return 12029;	
				case 3:
				return 12028;		
			}
		}
		if(weaponName.contains("rapier")) {
			switch(c.fightMode) {
				case 0:
				return 390;	
				case 1:
				return 390;		
				case 2:
				return 390;	
				case 3:
				return 386;
			}
		}
              if(weaponName.contains("dharok")) {
                   switch(c.fightMode) {
                          case 0: 
                          return 2066;
                          case 1: 
                          return 2066;
                          case 2: 
                          return 2066;
                          case 3:
                          return 2067;
			}
		}
		if(weaponName.contains("sword")) {
			return 451;
		}
		if(weaponName.contains("karil")) {
			return 2075;
		}
		//if(weaponName.contains("bow") && !weaponName.contains("'bow")) {
			//return 426;
		//}
		if (weaponName.contains("'bow"))
			return 4230;
		if (weaponName.contains("Hand cannon"))
			return 4230;
		switch(c.playerEquipment[c.playerWeapon]) { // if you don't want to use strings
			case 15241:
			return 12153;
			case 841:
			case 843:
			case 845:
			case 847:
			case 849:
			case 851:
			case 853:		
			case 855:
			case 857:
			case 859:
			case 861:
			case 11235:
			case 15701:
			case 15702:
			case 15703:
			case 15704:
			case 19143:
			case 19146:
			case 19149:
			return 426;
			case 18357:
			return 4230;
			case 6522:
			return 2614;
            case 13905:
			case 11716:
			return 2080;
			case 4153: // granite maul
			return 1665;
			case 18349: // Item ID 
			return 386; //Animation ID Chaotic rapier
			case 18351: //Item ID 
			return 451; //Animation Id Chaotic longsword
			case 4726: // guthan 
			return 2080;
			case 15015:
			case 15016:
			return 806;
			case 14484: //  Dclaw
			return 393;
			case 18353: //  Chaotic maul
			return 2661;
			case 13022:
			return 12153;
			case 4747: // torag
			return 0x814;
			case 4710: // ahrim
			return 406;
			case 4755: // verac
			return 2062;
			case 4734: // karil
			return 2075;
			case 10887:
			return 5865;
			case 4151:
			case 15441:
			case 15442:
			case 15443:
			case 15444:
			return 1658;
			case 6528:
			return 2661;
			default:
			return 451;
		}
	}
	
	/**
	* Block emotes
	*/
	public int getBlockEmote() {
		if (c.playerEquipment[c.playerShield] >= 8844 && c.playerEquipment[c.playerShield] <= 8850 && c.playerEquipment[c.playerShield] == 13351) {
			return 4177;
		}
		switch(c.playerEquipment[c.playerWeapon]) {
		
	        case 20072:
            return 4177;			
		
			case 15241:
			return 1666;
		
			case 19780:
			return 12030;
			
			case 8844:
			case 8850:
			case 16714:
			return 4177;
			
			case 4755:
			return 2063;
			
			case 10887:
			return 5866;

			case 4718:
			return 12004;

			case 4153:
			return 1666;
			
			case 13022:
			return 12156;
			
			case 18353:
			return 12004;
			
			case 4151:
			case 15441:
			case 15442:
			case 15443:
			case 15444:
			return 11974;
			
			case 11694:
			case 11698:
			case 11700: // scimmy anim 12030
			case 11696:
			case 11730:
		        //case 861:
			return -1;

			default:
			//return 404;
			return 424;
		}
	}
			
	/**
	* Weapon and magic attack speed!
	**/
	
	public int getAttackDelay(String s) {
		if(c.usingMagic) {
			switch(c.MAGIC_SPELLS[c.spellId][0]) {
				case 12871: // ice blitz
				case 13023: // shadow barrage
				case 12891: // ice barrage
				return 5;
				
				default:
				return 5;
			}
		}
		if(c.playerEquipment[c.playerWeapon] == -1)
			return 4; //unarmed
			
		switch (c.playerEquipment[c.playerWeapon]) {
			case 15241:
			return 9;	
	     	case 15038:
			return 5;
			case 15701:
			case 15702:
			case 15703:
			case 15704:
			case 11235:
			return 9;
			case 19143:
			case 19146:
			case 19149:
			return 5;
			case 15015:
			return 8;
			case 15037:
			case 11730:
			return 4;
			case 14484:
			return 5;
case 9185:
return 6;
case 18349:
return 4;
			case 15016:
			return 6;
			case 10887:
			case 6528:
			case 18353:
			return 7;
			case 11716:
            case 13905:
            return 5;
		}
		
		if(s.endsWith("greataxe"))
			return 7;
		else if(s.equals("torags hammers"))
			return 5;
		else if(s.equals("guthans warspear"))
			return 5;
		else if(s.equals("veracs flail"))
			return 5;
		else if(s.equals("ahrims staff"))
			return 6;
		else if(s.contains("staff")){
			if(s.contains("zamarok") || s.contains("guthix") || s.contains("saradomian") || s.contains("slayer") || s.contains("ancient"))
				return 4;
			else
				return 5;
		} else if(s.contains("aril")){
			if(s.contains("composite") || s.equals("seercull"))
				return 5;
			else if(s.contains("hi"))
				return 4;
			else if(s.contains("Ogre"))
				return 8;
			else if(s.contains("short") || s.contains("hunt") || s.contains("sword"))
				return 4;
			else if(s.contains("long") || s.contains("crystal"))
				return 6;
			else if(s.contains("'bow"))
				return 7;
			
			return 5;
		}
		else if(s.contains("dagger"))
			return 4;
		else if(s.contains("godsword") || s.contains("2h"))
			return 6;
		else if(s.contains("longsword"))
			return 5;
		else if(s.contains("sword"))
			return 4;
		else if(s.contains("scimitar"))
			return 4;
		else if(s.contains("mace"))
			return 5;
		else if(s.contains("battleaxe"))
			return 6;
		else if(s.contains("pickaxe"))
			return 5;
		else if(s.contains("thrownaxe"))
			return 5;
		else if(s.contains("axe"))
			return 5;
		else if(s.contains("warhammer"))
			return 6;
		else if(s.contains("2h"))
			return 7;
		else if(s.contains("spear"))
			return 5;
		else if(s.contains("claw"))
			return 4;
		else if(s.contains("halberd"))
			return 7;
		
		//sara sword, 2400ms
		else if(s.equals("granite maul"))
			return 7;
		else if(s.equals("toktz-xil-ak"))//sword
			return 4;
		else if(s.equals("tzhaar-ket-em"))//mace
			return 5;
		else if(s.equals("tzhaar-ket-om"))//maul
			return 7;
                else if(s.equals("chaotic maul"))//maul
			return 7;
		else if(s.equals("toktz-xil-ek"))//knife
			return 4;
		else if(s.equals("chaotic rapier"))//knife
			return 3;
		else if(s.equals("chaotic longsword"))//knife
			return 4;
		else if(s.equals("toktz-xil-ul"))//rings
			return 4;
		else if(s.equals("toktz-mej-tal"))//staff
			return 6;
		else if(s.contains("whip"))
			return 4;
		else if(s.contains("dart"))
			return 3;
		else if(s.contains("knife"))
			return 3;
		else if(s.contains("javelin"))
			return 6;
		return 5;
	}
	/**
	* How long it takes to hit your enemy
	**/
	public int getHitDelay(String weaponName) {
		if(c.usingMagic) {
			switch(c.MAGIC_SPELLS[c.spellId][0]) {			
				case 12891:
				return 4;
				case 12871:
				return 6;
				default:
				return 4;
			}
		} else {

			if(weaponName.contains("knife") || weaponName.contains("dart") || weaponName.contains("javelin") || weaponName.contains("thrownaxe")){
				return 3;
			}
			if(weaponName.contains("cross") || weaponName.contains("c'bow")) {
				return 4;
			}
			if (c.playerEquipment[c.playerWeapon] == 15241) {
				return 3;
			}
			if(weaponName.contains("bow") && !c.dbowSpec) {
				return 4;
			} else if (c.dbowSpec) {
				return 4;
			}

			switch(c.playerEquipment[c.playerWeapon]) {	
				case 6522: // Toktz-xil-ul
				return 3;
				
				
				default:
				return 2;
			}
		}
	}
	
	public int getRequiredDistance() {
		if (c.followId > 0 && c.freezeTimer <= 0 && !c.isMoving)
			return 2;
		else if(c.followId > 0 && c.freezeTimer <= 0 && c.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}
	
	public boolean usingHally() {
		switch(c.playerEquipment[c.playerWeapon]) {
			case 3190:
			case 3192:
			case 3194:
			case 3196:
			case 3198:
			case 3200:
			case 3202:
			case 3204:
			return true;
			
			default:
			return false;
		}
	}
	
	/**
	* Melee
	**/
	
	public int calculateMeleeAttack() {
		int attackLevel = c.playerLevel[0];
		//2, 5, 11, 18, 19
					if (c.slayerHelmetEffect && c.slayerTask != 0)
			attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.15;
        if (c.prayerActive[2]) {
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.05;
        } else if (c.prayerActive[7]) {
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.1;
        } else if (c.prayerActive[15]) {
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.15;
        } else if (c.prayerActive[24]) {
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.15;
        } else if (c.prayerActive[25]) {
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.2;
		} else if (c.curseActive[19]) {
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 1.0;
        }
        if (c.fullVoidMelee())
            attackLevel += c.getLevelForXP(c.playerXP[c.playerAttack]) * 0.1;
		attackLevel *= c.specAccuracy;
		//c.sendMessage("Attack: " + (attackLevel + (c.playerBonus[bestMeleeAtk()] * 2)));
        double i = c.playerBonus[bestMeleeAtk()];
		i += c.bonusAttack;
		if (c.playerEquipment[c.playerAmulet] == 11128 && c.playerEquipment[c.playerWeapon] == 6528) {
			i *= 1.30;
		}
		return (int)(attackLevel + (attackLevel * 0.15) + (i + i * 0.05));
	}
	public int bestMeleeAtk()
    {
        if(getBonus(c, STAB_BONUS) > getBonus(c, SLASH_BONUS) && getBonus(c, STAB_BONUS) > getBonus(c, CRUSH_BONUS))
            return 0;
        if(getBonus(c, SLASH_BONUS) > getBonus(c, STAB_BONUS) && getBonus(c, SLASH_BONUS) > getBonus(c, CRUSH_BONUS))
            return 1;
        return getBonus(c, CRUSH_BONUS) <= getBonus(c, SLASH_BONUS) || getBonus(c, CRUSH_BONUS) <= getBonus(c, STAB_BONUS) ? 0 : 2;
    }
	
	public int calculateMeleeMaxHit() {
		double maxHit = 0;
		double strBonus = getBonus(c, STRENGTH_BONUS);
		int strength = c.playerLevel[2];
		int lvlForXP = c.getLevelForXP(c.playerXP[2]);
				if (c.slayerHelmetEffect && c.slayerTask != 0)
			maxHit = (int)(maxHit * 1.15);
		if(c.prayerActive[1]) {
			strength += (int)(lvlForXP * .05);
		} else
		if(c.prayerActive[6]) {
			strength += (int)(lvlForXP * .10);
		} else
		if(c.prayerActive[14]) {
			strength += (int)(lvlForXP * .15);
		} else
		if(c.prayerActive[24]) {
			strength += (int)(lvlForXP * .18);
		} else
		if(c.prayerActive[25]) {
			strength += (int)(lvlForXP * .23);
		} else
		if(c.curseActive[19]) {
			strength += (int)(lvlForXP * .25);
		}
		if(c.playerEquipment[c.playerHat] == 2526 && c.playerEquipment[c.playerChest] == 2520 && c.playerEquipment[c.playerLegs] == 2522) {	
			maxHit += (maxHit * 10 / 100);
		}
		maxHit += 1.05D + (double)(strBonus * strength) * 0.00175D;
		maxHit += (double)strength * 0.11D;
		if(c.playerEquipment[c.playerWeapon] == 4718 && c.playerEquipment[c.playerHat] == 4716 && c.playerEquipment[c.playerChest] == 4720 && c.playerEquipment[c.playerLegs] == 4722) {	
				maxHit += (c.getPA().getLevelForXP(c.playerXP[3]) - c.playerLevel[3]) / 2;			
		}
		if (c.specDamage > 1)
			maxHit = (int)(maxHit * c.specDamage);
		if (maxHit < 0)
			maxHit = 1;
		if (c.fullVoidMelee())
			maxHit = (int)(maxHit * 1.10);
		if (c.playerEquipment[c.playerAmulet] == 11128 && c.playerEquipment[c.playerWeapon] == 6528) {
			maxHit *= 1.20;
		}
		return (int)Math.floor(maxHit);
	}
	

	public int calculateMeleeDefence()
    {
        int defenceLevel = c.playerLevel[1];
		double i = c.playerBonus[bestMeleeDef()];
        if (c.prayerActive[0]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
        } else if (c.prayerActive[5]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
        } else if (c.prayerActive[13]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
        } else if (c.prayerActive[24]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
        } else if (c.prayerActive[25]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
        }
        return (int)(defenceLevel + (defenceLevel * 0.15) + (i + i * 0.05));
    }
	
	public int bestMeleeDef()
    {
        if(getBonus(c, STAB_DEFENCE_BONUS) > getBonus(c, SLASH_DEFENCE_BONUS) && getBonus(c, STAB_DEFENCE_BONUS) > getBonus(c, CRUSH_DEFENCE_BONUS))
            return STAB_DEFENCE_BONUS;
        if(getBonus(c, SLASH_DEFENCE_BONUS) > getBonus(c, STAB_DEFENCE_BONUS) && getBonus(c, SLASH_DEFENCE_BONUS) > getBonus(c, CRUSH_DEFENCE_BONUS))
            return SLASH_DEFENCE_BONUS;
        return SLASH_DEFENCE_BONUS;
     }

	/**
	* Range
	**/
	
	public int calculateRangeAttack() {
		int attackLevel = c.playerLevel[4];
		attackLevel *= c.specAccuracy;
        if (c.fullVoidRange())
            attackLevel += c.getLevelForXP(c.playerXP[c.playerRanged]) * 0.1;
		if (c.prayerActive[3])
			attackLevel *= 1.05;
		else if (c.prayerActive[11])
			attackLevel *= 1.10;
		else if (c.prayerActive[19])
			attackLevel *= 1.15;
		//dbow spec
		if (c.fullVoidRange() && c.specAccuracy > 1.15) {
			attackLevel *= 1.75;		
		}
		if (c.playerEquipment[c.playerWeapon] == 18357) {
			attackLevel *= 3;
		}
		if (c.playerEquipment[c.playerWeapon] == 19143) {
			attackLevel *= 2.35;
		}
		if (c.playerEquipment[c.playerWeapon] == 19146) {
			attackLevel *= 2.35;
		}
		if (c.playerEquipment[c.playerWeapon] == 19149) {
			attackLevel *= 2.35;
		}
        return (int) (attackLevel + (getBonus(c, RANGE_BONUS) * 1.95));
	}
	
	public int calculateRangeDefence() {
		int defenceLevel = c.playerLevel[1];
        if (c.prayerActive[0]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
        } else if (c.prayerActive[5]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
        } else if (c.prayerActive[13]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
        } else if (c.prayerActive[24]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
        } else if (c.prayerActive[25]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
        } else if (c.curseActive[19]) { // turmoil
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15 + c.getdef;
        }
        return (int) (defenceLevel + getBonus(c, RANGE_DEFENCE_BONUS) + (getBonus(c, RANGE_DEFENCE_BONUS) / 2));
	}
	
	public boolean usingBolts() {
		return c.playerEquipment[c.playerArrows] >= 9130 && c.playerEquipment[c.playerArrows] <= 9145 
				|| c.playerEquipment[c.playerArrows] >= 9230 && c.playerEquipment[c.playerArrows] <= 9245 && c.playerEquipment[c.playerArrows] <= 9342;
	}
	
	public int rangeMaxHit() {
		int rangeLevel = c.playerLevel[4];
		int weapon = c.playerEquipment[c.playerWeapon];
		double modifier = 1.5;
		double wtf = c.specDamage;
		int itemUsed = c.usingBow ? c.lastArrowUsed : c.lastWeaponUsed;
		if (c.prayerActive[3])
			modifier += 0.08;
		else if (c.prayerActive[11])
			modifier += 0.17;
		else if (c.prayerActive[19])
			modifier += 0.20;
		if (c.fullVoidRange())
			modifier += 0.25;
		if(weapon == 15241)
			modifier *= 1.45;
		//bow here
		if (weapon == 19143)
			modifier *= 1.35;
		if (weapon == 19146)
			modifier *= 1.35;
		if (weapon == 19149)
			modifier *= 1.35;

		double c = modifier * rangeLevel;
		int rangeStr = getRangeStr(itemUsed);
		double max =(c + 8) * (rangeStr + 64) / 640;
		if (wtf != 1)
			max *= wtf;
		if (max < 1)
			max = 1;
		return (int)max;
	}
	
	public int getRangeStr(int i) {
		int str = 0;
		int[][] data = {
			{877,  10}, {9140, 46}, {9145, 36}, {9141, 64}, 
			{9142, 82}, {9143,100}, {9144,115}, {9236, 14}, 
			{9237, 30}, {9238, 48}, {9239, 66}, {9240, 83}, 
			{9241, 85}, {9242,103}, {9243,105}, {9244,117}, 
			{9245,120}, {882, 7}, {884, 10}, {886, 16}, 
			{888, 22}, {890, 31}, {892, 49},{15243, 60}, {4740, 55}, 
			{11212, 60}, {806, 1}, {807, 3}, {808, 4}, 
			{809, 7}, {810,10}, {811,14}, {11230,20},
			{864, 3},  {863, 4}, {865, 7}, {866, 10}, 
			{867, 14}, {868, 24}, {825, 6}, {826,10}, 
			{827,12}, {828,18}, {829,28}, {830,42},
			{800, 5}, {801, 7}, {802,11}, {803,16}, 
			{804,23}, {805,36}, {9976, 0}, {9977, 15},
			{4212, 70}, {4214, 70}, {4215, 70}, {4216, 70},
			{4217, 70}, {4218, 70}, {4219, 70}, {4220, 70},
			{4221, 70}, {4222, 70}, {4223, 70}, {6522, 49},
			{10034, 15}, {13883, 115}, {13879, 115}, {13880, 115},
			{13881, 115}, {13882, 115}, {19152, 65}, {19157, 65}, {19162, 65},
		};
		for(int l = 0; l < data.length; l++) {
			if(i == data[l][0]) {
				str = data[l][1];
			}
		}
		return str;
	}
	
	/*public int rangeMaxHit() {
        int rangehit = 0;
        rangehit += c.playerLevel[4] / 7.5;
        int weapon = c.lastWeaponUsed;
        int Arrows = c.lastArrowUsed;
        if (weapon == 4223) {//Cbow 1/10
            rangehit = 2;
            rangehit += c.playerLevel[4] / 7;
        } else if (weapon == 4222) {//Cbow 2/10
            rangehit = 3;
            rangehit += c.playerLevel[4] / 7;
        } else if (weapon == 4221) {//Cbow 3/10
            rangehit = 3;
            rangehit += c.playerLevel[4] / 6.5;
        } else if (weapon == 4220) {//Cbow 4/10
            rangehit = 4;
            rangehit += c.playerLevel[4] / 6.5;
        } else if (weapon == 4219) {//Cbow 5/10
            rangehit = 4;
            rangehit += c.playerLevel[4] / 6;
        } else if (weapon == 4218) {//Cbow 6/10
            rangehit = 5;
            rangehit += c.playerLevel[4] / 6;
        } else if (weapon == 4217) {//Cbow 7/10
            rangehit = 5;
            rangehit += c.playerLevel[4] / 5.5;
        } else if (weapon == 4216) {//Cbow 8/10
            rangehit = 6;
            rangehit += c.playerLevel[4] / 5.5;
        } else if (weapon == 4215) {//Cbow 9/10
            rangehit = 6;
            rangehit += c.playerLevel[4] / 5;
        } else if (weapon == 4214) {//Cbow Full
            rangehit = 7;
            rangehit += c.playerLevel[4] / 5;
        } else if (weapon == 6522) {
            rangehit = 5;
            rangehit += c.playerLevel[4] / 6;
        } else if (weapon == 9029) {//dragon darts
            rangehit = 8;
            rangehit += c.playerLevel[4] / 10;
        } else if (weapon == 811 || weapon == 868) {//rune darts
            rangehit = 2;
            rangehit += c.playerLevel[4] / 8.5;
        } else if (weapon == 810 || weapon == 867) {//adamant darts
            rangehit = 2;
            rangehit += c.playerLevel[4] / 9;
        } else if (weapon == 809 || weapon == 866) {//mithril darts
            rangehit = 2;
            rangehit += c.playerLevel[4] / 9.5;
        } else if (weapon == 808 || weapon == 865) {//Steel darts
            rangehit = 2;
            rangehit += c.playerLevel[4] / 10;
        } else if (weapon == 807 || weapon == 863) {//Iron darts
            rangehit = 2;
            rangehit += c.playerLevel[4] / 10.5;
        } else if (weapon == 806 || weapon == 864) {//Bronze darts
            rangehit = 1;
            rangehit += c.playerLevel[4] / 11;
        } else if (Arrows == 4740 && weapon == 4734) {//BoltRacks
			rangehit = 3;
            rangehit += c.playerLevel[4] / 6;
        } else if (Arrows == 11212) {//dragon arrows
            rangehit = 4;
            rangehit += c.playerLevel[4] / 5.5;
        } else if (Arrows == 892) {//rune arrows
            rangehit = 3;
            rangehit += c.playerLevel[4] / 6;
        } else if (Arrows == 890) {//adamant arrows
            rangehit = 2;
            rangehit += c.playerLevel[4] / 7;
        } else if (Arrows == 888) {//mithril arrows
            rangehit = 2;
            rangehit += c.playerLevel[4] / 7.5;
        } else if (Arrows == 886) {//steel arrows
            rangehit = 2;
            rangehit += c.playerLevel[4] / 8;
        } else if (Arrows == 884) {//Iron arrows
            rangehit = 2;
            rangehit += c.playerLevel[4] / 9;
        } else if (Arrows == 882) {//Bronze arrows
            rangehit = 1;
            rangehit += c.playerLevel[4] / 9.5;
        } else if (Arrows == 9244) {
			rangehit = 9;
			rangehit += c.playerLevel[4] / 3;
		} else if (Arrows == 9139) {
			rangehit = 12;
			rangehit += c.playerLevel[4] / 4;
		} else if (Arrows == 9140) {
			rangehit = 2;
            rangehit += c.playerLevel[4] / 7;
		} else if (Arrows == 9141) {
			rangehit = 3;
            rangehit += c.playerLevel[4] / 6;
		} else if (Arrows == 9142) {
			rangehit = 4;
            rangehit += c.playerLevel[4] / 6;
		} else if (Arrows == 9143) {
			rangehit = 7;
			rangehit += c.playerLevel[4] / 5;
		} else if (Arrows == 9144) {
			rangehit = 7;
			rangehit += c.playerLevel[4] / 4.5;
		}
        int bonus = 0;
        bonus -= rangehit / 10;
        rangehit += bonus;
        if (c.specDamage != 1)
			rangehit *= c.specDamage;
		if (rangehit == 0)
			rangehit++;
		if (c.fullVoidRange()) {
			rangehit *= 1.10;
		}
		if (c.prayerActive[3])
			rangehit *= 1.05;
		else if (c.prayerActive[11])
			rangehit *= 1.10;
		else if (c.prayerActive[19])
			rangehit *= 1.15;
		return rangehit;
    }*/
	
	public boolean properBolts() {
		return c.playerEquipment[c.playerArrows] >= 9140 && c.playerEquipment[c.playerArrows] <= 9144
				|| c.playerEquipment[c.playerArrows] >= 9240 && c.playerEquipment[c.playerArrows] <= 9245 && c.playerEquipment[c.playerArrows] <= 9342;
	}
	
	public int correctBowAndArrows() {
		if (usingBolts())
			return -1;
		switch(c.playerEquipment[c.playerWeapon]) {
		
			case 15241://hand cannon with Shots
			return 15243;
			
			case 839:
			case 841:
			return 882;
			
			case 843:
			case 845:
			return 884;
			
			case 847:
			case 849:
			return 886;
			
			case 851:
			case 853:
			return 888;        
			
			case 855:
			case 857:
			return 890;
			
			case 859:
			case 861:
			return 892;
			
			case 4734:
			case 4935:
			case 4936:
			case 4937:
			return 4740;
			
			case 11235:
			case 15701:
			case 15702:
			case 15703:
			case 15704:
			return 11212;
			
			case 19143:
			return 19152;
			case 19146:
			return 19157;
			case 19149:
			return 19162;
		}
		return -1;
	}
	
	public int getRangeStartGFX() {
		if (c.playerEquipment[c.playerWeapon] == 15241)
			return 2143;
		switch(c.rangeItemUsed) {
			            
			case 863:
			return 220;
			case 864:
			return 219;
			case 865:
			return 221;
			case 866: // knives
			return 223;
			case 867:
			return 224;
			case 868:
			return 225;
			case 869:
			return 222;
			
			case 806:
			return 232;
			case 807:
			return 233;
			case 808:
			return 234;
			case 809: // darts
			return 235;
			case 810:
			return 236;
			case 811:
			return 237;
			
			case 825:
			return 206;
			case 826:
			return 207;
			case 827: // javelin
			return 208;
			case 828:
			return 209;
			case 829:
			return 210;
			case 830:
			return 211;

			case 800:
			return 42;
			case 801:
			return 43;
			case 802:
			return 44; // axes
			case 803:
			return 45;
			case 804:
			return 46;
			case 805:
			return 48;
								
			case 882:
			return 19;
			
			case 884:
			return 18;
			
			case 886:
			return 20;

			case 888:
			return 21;
			
			case 890:
			return 22;
			
			case 892:
			return 24;
			
			case 11212:
			return 26;
			
			case 4212:
			case 4214:
			case 4215:
			case 4216:
			case 4217:
			case 4218:
			case 4219:
			case 4220:
			case 4221:
			case 4222:
			case 4223:
			return 250;
			
		}
		return -1;
	}
		
	public int getRangeProjectileGFX() {
		if(c.dbowSpec && c.playerEquipment[c.playerArrows] == 11212) {
			return 1099;
		}
		if(c.bowSpecShot > 0) {
			switch(c.rangeItemUsed) {
				default:
				return 249;
			}
		}
		if (c.playerEquipment[c.playerWeapon] == 9185)
			return 27;
		if (c.playerEquipment[c.playerWeapon] == 13879)
			return 1837;
		if (c.playerEquipment[c.playerWeapon] == 13957)
			return 1839;
		if (c.playerEquipment[c.playerWeapon] == 15241)
			return 2143;
		switch(c.rangeItemUsed) {
			
			case 863:
			return 213;
			case 864:
			return 212;
			case 865:
			return 214;
			case 866: // knives
			return 216;
			case 867:
			return 217;
			case 868:
			return 218;	
			case 869:
			return 215;  

			case 806:
			return 226;
			case 807:
			return 227;
			case 808:
			return 228;
			case 809: // darts
			return 229;
			case 810:
			return 230;
			case 811:
			return 231;	

			case 825:
			return 200;
			case 826:
			return 201;
			case 827: // javelin
			return 202;
			case 828:
			return 203;
			case 829:
			return 204;
			case 830:
			return 205;	
			
			case 6522: // Toktz-xil-ul
			return 442;

			case 800:
			return 36;
			case 801:
			return 35;
			case 802:
			return 37; // axes
			case 803:
			return 38;
			case 804:
			return 39;
			case 805:
			return 40;

			case 882:
			return 10;
			
			case 884:
			return 9;
			
			case 886:
			return 11;

			case 888:
			return 12;
			
			case 890:
			return 13;
			
			case 892:
			return 15;
			
			case 11212:
			return 17;
			
			case 4740: // bolt rack
			return 27;


			
			case 4212:
			case 4214:
			case 4215:
			case 4216:
			case 4217:
			case 4218:
			case 4219:
			case 4220:
			case 4221:
			case 4222:
			case 4223:
			return 249;
			
			
		}
		return -1;
	}
	
	public int getProjectileSpeed() {
		if (c.dbowSpec)
			return 100;
		return 70;
	}
	
	public int getProjectileShowDelay() {
		switch(c.playerEquipment[c.playerWeapon]) {
			case 15243:
			return 15; 
			case 863:
			case 864:
			case 865:
			case 866: // knives
			case 867:
			case 868:
			case 869:
			
			case 806:
			case 807:
			case 808:
			case 809: // darts
			case 810:
			case 811:
			
			case 825:
			case 826:
			case 827: // javelin
			case 828:
			case 829:
			case 830:
			case 13879:
			case 13880:
			case 13881:
			case 13882:
			
			case 800:
			case 801:
			case 802:
			case 803: // axes
			case 804:
			case 805:
			case 13883:
			
			case 4734:
            case 9185:
case 13957:
			case 4935:
			case 4936:
			case 4937:
			return 15; 
			
		
			default:
			return 5;
		}
	}
	
	/**
	* Mage
	**/

		public static int finalMagicDamage(Client c) {
		double damage = c.MAGIC_SPELLS[c.oldSpellId][6];
		double damageMultiplier = 1;
		if (c.playerLevel[c.playerMagic] > c.getLevelForXP(c.playerXP[6])
				&& c.getLevelForXP(c.playerXP[6]) >= 95)
			damageMultiplier += .03 * (c.playerLevel[c.playerMagic] - 99);
		else
			damageMultiplier = 1;
		switch (c.playerEquipment[c.playerWeapon]) {
		/*case 18371: // Gravite Staff
			damageMultiplier += .05;
			break;*/
		case 4675: // Ancient Staff
		case 4710: // Ahrim's Staff
		case 4862: // Ahrim's Staff
		case 4864: // Ahrim's Staff
		case 4865: // Ahrim's Staff
		case 6914: // Master Wand
		case 8841: // Void Knight Mace
		case 15010: // Zuriel's Staff
			damageMultiplier += .10;
			break;
		case 15486: // Staff of Light
			damageMultiplier += .15;
			break;
		case 15040: // Chaotic Staff
			damageMultiplier += .20;
			break;
		}
		switch (c.playerEquipment[c.playerAmulet]) {
		/*case 18333: // Arcane Pulse
			damageMultiplier += .05;
			break;
		case 18334:// Arcane Blast
			damageMultiplier += .10;
			break;*/
		case 16713:// Arcane Stream
			damageMultiplier += .15;
			break;
		}
		damage *= damageMultiplier;
		return (int) damage;
	}
	
	/**
	* Magic
	**/
	
	public int mageAtk()
    {
        int attackLevel = c.playerLevel[6];
       if (c.playerEquipment[c.playerWeapon] == 18335)
			attackLevel += 0.15;
		if (c.fullVoidMage())
            attackLevel += c.getLevelForXP(c.playerXP[6]) * 0.2;
        		if (c.prayerActive[4]) 
			attackLevel *= 1.05;
		else if (c.prayerActive[12])
			attackLevel *= 1.10;
		else if (c.prayerActive[20])
			attackLevel *= 1.15;
        return (int) (attackLevel + (getBonus(c, MAGIC_BONUS) * 2));
    }
	public int mageDef()
    {
        int defenceLevel = c.playerLevel[1]/2 + c.playerLevel[6]/2;
        if (c.prayerActive[0]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
        } else if (c.prayerActive[3]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
        } else if (c.prayerActive[9]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
        } else if (c.prayerActive[18]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
        } else if (c.prayerActive[19]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
        }
        return (int) (defenceLevel + getBonus(c, MAGIC_DEFENCE_BONUS) + (getBonus(c, MAGIC_DEFENCE_BONUS) / 3));
    }
	
	public boolean wearingStaff(int runeId) {
		int wep = c.playerEquipment[c.playerWeapon];
		switch (runeId) {
			case 554:
			if (wep == 1387)
				return true;
			break;
			case 555:
			if (wep == 1383)
				return true;
			break;
			case 556:
			if (wep == 1381)
				return true;
			break;
			case 557:
			if (wep == 1385)
				return true;
			break;
		}
		return false;
	}
	
	public boolean wearingTomeOfFrost(int runeId) {
		int book = c.playerEquipment[c.playerShield];
		switch (runeId) {
			case 555:
			if (book == 18346)
				return true;
			break;
		}
		return false;
	}
	
	public boolean checkMagicReqs(int spell) {
		if(c.usingMagic && Config.RUNES_REQUIRED) { // check for runes
			if((!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][8], c.MAGIC_SPELLS[spell][9]) && !wearingStaff(c.MAGIC_SPELLS[spell][8])) ||
				(!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][10], c.MAGIC_SPELLS[spell][11]) && !wearingStaff(c.MAGIC_SPELLS[spell][10])) ||
				(!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][12], c.MAGIC_SPELLS[spell][13]) && !wearingStaff(c.MAGIC_SPELLS[spell][12])) ||
				(!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][14], c.MAGIC_SPELLS[spell][15]) && !wearingStaff(c.MAGIC_SPELLS[spell][14]))){
			c.sendMessage("You don't have the required runes to cast this spell.");
			return false;
			} 
		}

		if(c.usingMagic && c.playerIndex > 0) {
			if(PlayerHandler.players[c.playerIndex] != null) {
				for(int r = 0; r < c.REDUCE_SPELLS.length; r++){	// reducing spells, confuse etc
					if(PlayerHandler.players[c.playerIndex].REDUCE_SPELLS[r] == c.MAGIC_SPELLS[spell][0]) {
						c.reduceSpellId = r;
						if((System.currentTimeMillis() - PlayerHandler.players[c.playerIndex].reduceSpellDelay[c.reduceSpellId]) > PlayerHandler.players[c.playerIndex].REDUCE_SPELL_TIME[c.reduceSpellId]) {
							PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId] = true;
						} else {
							PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId] = false;
						}
						break;
					}			
				}
				if(!PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId]) {
					c.sendMessage("That player is currently immune to this spell.");
					c.usingMagic = false;
					c.stopMovement();
					resetPlayerAttack();
					return false;
				}
			}
		}

		int staffRequired = getStaffNeeded();
		if(c.usingMagic && staffRequired > 0 && Config.RUNES_REQUIRED) { // staff required
			if(c.playerEquipment[c.playerWeapon] != staffRequired) {
				c.sendMessage("You need a "+c.getItems().getItemName(staffRequired).toLowerCase()+" to cast this spell.");
				return false;
			}
		}
		
		if(c.usingMagic && Config.MAGIC_LEVEL_REQUIRED) { // check magic level
			if(c.playerLevel[6] < c.MAGIC_SPELLS[spell][1]) {
				c.sendMessage("You need to have a magic level of " +c.MAGIC_SPELLS[spell][1]+" to cast this spell.");
				return false;
			}
		}
		if(c.usingMagic && Config.RUNES_REQUIRED) {
			if(c.MAGIC_SPELLS[spell][8] > 0) { // deleting runes
				if (!wearingStaff(c.MAGIC_SPELLS[spell][8]))
					c.getItems().deleteItem(c.MAGIC_SPELLS[spell][8], c.getItems().getItemSlot(c.MAGIC_SPELLS[spell][8]), c.MAGIC_SPELLS[spell][9]);
			}
			if(c.MAGIC_SPELLS[spell][10] > 0) {
				if (!wearingStaff(c.MAGIC_SPELLS[spell][10]))
					c.getItems().deleteItem(c.MAGIC_SPELLS[spell][10], c.getItems().getItemSlot(c.MAGIC_SPELLS[spell][10]), c.MAGIC_SPELLS[spell][11]);
			}
			if(c.MAGIC_SPELLS[spell][12] > 0) {
				if (!wearingStaff(c.MAGIC_SPELLS[spell][12]))
					c.getItems().deleteItem(c.MAGIC_SPELLS[spell][12], c.getItems().getItemSlot(c.MAGIC_SPELLS[spell][12]), c.MAGIC_SPELLS[spell][13]);
			}
			if(c.MAGIC_SPELLS[spell][14] > 0) {
				if (!wearingStaff(c.MAGIC_SPELLS[spell][14]))
					c.getItems().deleteItem(c.MAGIC_SPELLS[spell][14], c.getItems().getItemSlot(c.MAGIC_SPELLS[spell][14]), c.MAGIC_SPELLS[spell][15]);
			}
		}
		return true;
	}
	
	
	public int getFreezeTime() {
		switch(c.MAGIC_SPELLS[c.oldSpellId][0]) {
			case 1572:
			case 12861: // ice rush
			return 10;
						
			case 1582:
			case 12881: // ice burst
			return 17;
			
			case 1592:
			case 12871: // ice blitz
			return 25;
			
			case 12891: // ice barrage
			return 33;
			
			default:
			return 0;
		}
	}
	
	public void freezePlayer(int i) {
		
	
	}

	public int getStartHeight() {
		switch(c.MAGIC_SPELLS[c.spellId][0]) {
			case 1562: // stun
			return 25;
			
			case 12939:// smoke rush
			return 35;
			
			case 12987: // shadow rush
			return 38;
			
			case 12861: // ice rush
			return 15;
			
			case 12951:  // smoke blitz
			return 38;
			
			case 12999: // shadow blitz
			return 25;
			
			case 12911: // blood blitz
			return 25;
			
			default:
			return 43;
		}
	}
	

	
	public int getEndHeight() {
		switch(c.MAGIC_SPELLS[c.spellId][0]) {
			case 1562: // stun
			return 10;
			
			case 12939: // smoke rush
			return 20;
			
			case 12987: // shadow rush
			return 28;
			
			case 12861: // ice rush
			return 10;
			
			case 12951:  // smoke blitz
			return 28;
			
			case 12999: // shadow blitz
			return 15;
			
			case 12911: // blood blitz
			return 10;
				
			default:
			return 31;
		}
	}
	
	public int getStartDelay() {
		if(c.playerEquipment[c.playerWeapon] == 15241)
			return 30;
		switch(c.MAGIC_SPELLS[c.spellId][0]) {
			case 1539:
			return 60;
			
			default:
			return 53;
		}
	}
	
	public int getStaffNeeded() {
		switch(c.MAGIC_SPELLS[c.spellId][0]) {
			case 1539:
			return 1409;
			
			case 12037:
			return 4170;
			
			case 1190:
			return 2415;
			
			case 1191:
			return 2416;
			
			case 1192:
			return 2417;
			
			default:
			return 0;
		}
	}
	
	public boolean godSpells() {
		switch(c.MAGIC_SPELLS[c.spellId][0]) {	
			case 1190:
			return true;
			
			case 1191:
			return true;
			
			case 1192:
			return true;
			
			default:
			return false;
		}
	}
		
	public int getEndGfxHeight() {
		switch(c.MAGIC_SPELLS[c.oldSpellId][0]) {
			case 12987:	
			case 12901:		
			case 12861:
			case 12445:
			case 1192:
			case 13011:
			case 12919:
			case 12881:
			case 12999:
			case 12871:
			case 13023:
			case 12929:
			case 12911:
			case 12891:
			return 0;
			default:
			return 100;
		}
	}
	
	public int getStartGfxHeight() {
		switch(c.MAGIC_SPELLS[c.spellId][0]) {
			case 12871:
			case 12891:
			return 0;
			
			default:
			return 100;
		}
	}
	
	public void handleDfs() {
try {

	                  if(c.oldPlayerIndex > 0) {
			if(PlayerHandler.players[c.oldPlayerIndex] != null) {
				c.projectileStage = 2;
				final int pX = c.getX();
				final int pY = c.getY();
				final int oX = PlayerHandler.players[c.oldPlayerIndex].getX();
				final int oY = PlayerHandler.players[c.oldPlayerIndex].getY();
				final int offX = (pY - oY)* -1;
				final int offY = (pX - oX)* -1;
			if (System.currentTimeMillis() - c.dfsDelay > 30000) {
			if (c.playerIndex > 0 && PlayerHandler.players[c.playerIndex] != null) {
				final int damage = Misc.random(15) + 5;
				c.startAnimation(6696);
				c.gfx0(1165);
			c.SSPLIT = true;
			//EventManager.getSingleton().addEvent(new Event() {
				//public void execute(EventContainer b) {
 				PlayerHandler.players[c.oldPlayerIndex].gfx100(1167);
				c.SSPLIT = false;
				PlayerHandler.players[c.oldPlayerIndex].playerLevel[3] -= damage;
				PlayerHandler.players[c.oldPlayerIndex].hitDiff2 = damage;
				PlayerHandler.players[c.oldPlayerIndex].hitUpdateRequired2 = true;
				PlayerHandler.players[c.oldPlayerIndex].updateRequired = true;
				//b.stop();
				//}
				//}, 1700);
			//EventManager.getSingleton().addEvent(new Event() {
				//public void execute(EventContainer b) {
  				c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, 50, 1166, 30, 30, - c.oldPlayerIndex - 1, 30, 5);
				//b.stop();
				//}
				//}, 1000);
				c.dfsDelay = System.currentTimeMillis();					
			} else {
				c.sendMessage("I should be in combat before using this.");
			}
		} else {
			c.sendMessage("My shield hasn't finished recharging yet.");
	}		}
		}	
			} catch (Exception e) {
                         }
	}	


	public void handleZerker() {

		if(c.isDonator == 1){
		if (System.currentTimeMillis() - c.dfsDelay > 60000) {
			if (c.playerIndex > 0 && PlayerHandler.players[c.playerIndex] != null) {
				int damage = Misc.random(10) + 7;
				c.startAnimation(369);
				c.gfx0(369);
				PlayerHandler.players[c.playerIndex].playerLevel[3] -= damage;
				PlayerHandler.players[c.playerIndex].hitDiff2 = damage;
				c.forcedText = "Feel the power of the Berserker Ring!";
				PlayerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
				PlayerHandler.players[c.playerIndex].updateRequired = true;
				c.dfsDelay = System.currentTimeMillis();						
			} else {
				c.sendMessage("I should be in combat before using this.");
			}
		} else {
			c.sendMessage("My ring hasn't finished recharging yet (60 Seconds)");
			}if (c.isDonator == 0)
			c.sendMessage("Only Donators can use the ring's Special attack");	
		}
	}
	public void handleWarrior() {
		if(c.isDonator == 1){
		if (System.currentTimeMillis() - c.dfsDelay > 60000) {
			if (c.playerIndex > 0 && PlayerHandler.players[c.playerIndex] != null) {
				int damage = Misc.random(10) + 7;
				c.startAnimation(369);
				c.gfx0(369);
				PlayerHandler.players[c.playerIndex].playerLevel[3] -= damage;
				c.forcedText = "Feel the power of the Warrior Ring!";
				PlayerHandler.players[c.playerIndex].hitDiff2 = damage;
				PlayerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
				PlayerHandler.players[c.playerIndex].updateRequired = true;
				c.dfsDelay = System.currentTimeMillis();						
			} else {
				c.sendMessage("I should be in combat before using this.");
			}
		} else {
			c.sendMessage("My ring hasn't finished recharging yet (60 Seconds)");
			}if (c.isDonator == 0)
			c.sendMessage("Only Donators can use the ring's Special attack");	
		}
	}
	
	public void handleSeers() {
/*

		c.castingMagic = true;
		if(c.isDonator == 1){
		if (System.currentTimeMillis() - c.dfsDelay > 60000) {
			if (c.playerIndex > 0 && Server.playerHandler.players[c.playerIndex] != null) {
				int damage = Misc.random(10) + 7;
								c.startAnimation(1979);
								Server.playerHandler.players[c.playerIndex].gfx0(369);
								c.gfx0(368);
					Server.playerHandler.players[c.playerIndex].freezeTimer = 15;
										Server.playerHandler.players[c.playerIndex].resetWalkingQueue();
										Server.playerHandler.players[c.playerIndex].frozenBy = c.playerId;
				Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
				c.forcedText = ("Feel the power of the Seers Ring!");
				Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;

				Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
				Server.playerHandler.players[c.playerIndex].updateRequired = true;
				c.dfsDelay = System.currentTimeMillis();						
			} else {
				c.sendMessage("I should be in combat before using this.");
			}
		} else {
			c.sendMessage("My ring hasn't finished recharging yet (60 Seconds)");
			}if (c.isDonator == 0)
*/
		
	}

	public void Zammybook() {
				c.startAnimation(1670);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer b) {
		c.forcedChatUpdateRequired = true;
		c.updateRequired = true;
				c.forcedText = ("Two great warriors, joined by hand,");
				b.stop();
				}
				}, 400);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer b) {
				c.forcedText = ("to spread destruction across the land.");
		c.forcedChatUpdateRequired = true;
		c.updateRequired = true;
				b.stop();
				}
				}, 1200);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer b) {
				c.forcedText = ("In Zamorak's name, now two are one.");
		c.forcedChatUpdateRequired = true;
		c.updateRequired = true;
				b.stop();
				}
				}, 1900);					

		
	}
	
	public void handleArcher() {
		if(c.isDonator == 1){
		if (System.currentTimeMillis() - c.dfsDelay > 60000) {
			if (c.playerIndex > 0 && PlayerHandler.players[c.playerIndex] != null) {
				int damage = Misc.random(10) + 7;
				c.startAnimation(369);
				c.gfx0(369);
				PlayerHandler.players[c.playerIndex].playerLevel[3] -= damage;
				PlayerHandler.players[c.playerIndex].hitDiff2 = damage;
				c.forcedText = "Feel the power of the Archer Ring!";
				PlayerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
				PlayerHandler.players[c.playerIndex].updateRequired = true;
				c.dfsDelay = System.currentTimeMillis();						
			} else {
				c.sendMessage("I should be in combat before using this.");
			}
		} else {
			c.sendMessage("My ring hasn't finished recharging yet (60 Seconds)");
			}if (c.isDonator == 0)
			c.sendMessage("Only Donators can use the ring's Special attack");	
		}
	}
	
		public void handleDfsNPC() {
	                try {
	                 if(c.npcIndex > 0) {
			if(NPCHandler.npcs[c.npcIndex] != null) {
				c.projectileStage = 2;
				final int pX = c.getX();
				final int pY = c.getY();
				final int nX = NPCHandler.npcs[c.npcIndex].getX();
				final int nY = NPCHandler.npcs[c.npcIndex].getY();
				final int offX = (pY - nY)* -1;
				final int offY = (pX - nX)* -1;
			if (System.currentTimeMillis() - c.dfsDelay > 30000) {
			if (c.npcIndex > 0 && NPCHandler.npcs[c.npcIndex] != null) {
				final int damage = Misc.random(15) + 5;
				c.startAnimation(6696);
				c.gfx0(1165);
				NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
				NPCHandler.npcs[c.npcIndex].updateRequired = true;
				NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
				NPCHandler.npcs[c.npcIndex].HP -= damage;
				//Server.npcHandler.npcs[c.npcIndex].gfx100(1167);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer b) {
				c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 50, 1166, 31, 35, - c.npcIndex  - 1, 30);
				b.stop();
				}
				}, 1000);
if(NPCHandler.npcs[c.npcIndex].isDead == true) {
c.sendMessage("This NPC is already dead!");
return;
}
				c.dfsDelay = System.currentTimeMillis();						
			} else {
				c.sendMessage("I should be in combat before using this.");
			}
		} else {
			c.sendMessage("My shield hasn't finished recharging yet.");
	}		}	
		}	
		}
		catch (Exception e)
		{
		}
	}	
	
	public void applyRecoil(int damage, int i) {
		if (damage > 0 && PlayerHandler.players[i].playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage/10 + 1;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
				removeRecoil(c);
				c.recoilHits = recDamage;
				c.typeOfDamageMask = 4;
				c.colorHitMask = 1;
				c.setHitUpdateRequired(true);				
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
		}	
	}

	public void Deflect(int damage, int i) {
		if (damage > 0 && PlayerHandler.players[i].curseActive[7]) {
			int recDamage = damage/6;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
			c.recoilHits = recDamage;
				c.setHitUpdateRequired(true);				
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
		} else if (damage > 0  && PlayerHandler.players[i].curseActive[8]) {
			int recDamage = damage/6;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
			c.recoilHits = recDamage;
				c.setHitUpdateRequired(true);				
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
		} else if (damage > 0  && PlayerHandler.players[i].curseActive[9]) {
			int recDamage = damage/6;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
			c.recoilHits = recDamage;
				c.setHitUpdateRequired(true);				
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
			}
		}

	public void applyRecoilNPC(int damage, int i) {
		if (damage > 0 && c.playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage/10 + 1;
			NPCHandler.npcs[c.npcIndex].HP -= recDamage;
			NPCHandler.npcs[c.npcIndex].hitDiff2 = recDamage;
			NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
			NPCHandler.npcs[c.npcIndex].updateRequired = true;
			c.updateRequired = true;
			removeRecoil(c);
			c.recoilHits = damage;
		}
	}

	public void removeRecoil(Client c) {
		if(c.recoilHits >= 50) {
			c.getItems().removeItem(2550, c.playerRing);
			c.getItems().deleteItem(2550, c.getItems().getItemSlot(2550), 1);
			c.sendMessage("Your ring of recoil turned to dust!");
			c.recoilHits = 0;
		} else {
			c.recoilHits++;
		}
	}
	
	public int getBonusAttack(int i) {
		switch (NPCHandler.npcs[i].npcType) {
			case 2883:
			return Misc.random(50) + 30;
			case 2026:
			case 2027:
			case 2029:
			case 2030:
			return Misc.random(50) + 30;
		}
		return 0;
	}
	
	
	
	public void handleGmaulPlayer() {
		if (c.playerIndex > 0) {
			Client o = (Client)PlayerHandler.players[c.playerIndex];
			if (c.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(), getRequiredDistance())) {
				if (checkReqs()) {
					if (checkSpecAmount(4153)) {						
						boolean hit = Misc.random(calculateMeleeAttack()) > Misc.random(o.getCombat().calculateMeleeDefence());
						int damage = 0;
						if (hit)
							damage = Misc.random(calculateMeleeMaxHit());
						if (o.prayerActive[18] || o.curseActive[9] && System.currentTimeMillis() - o.protMeleeDelay > 1500)
							damage *= .6;
						o.getCombat().setColorMasks(damage, calculateMeleeMaxHit(), 1);
						o.handleHitMask(damage);
						c.startAnimation(1667);
						c.gfx100(340);
						o.dealDamage(damage);
					}	
				}	
			}			
		}	
	}
	
	public boolean armaNpc(int i) {
		switch (NPCHandler.npcs[i].npcType) {
			case 6222:
			case 6223:
			case 6229:
			case 6225:
			case 6230:
			case 6227:
			case 6232:
			case 6239:
			case 6233:
			case 6231:
			return true;	
		}
		return false;	
	}
	
	public int korasiDamage(Client opp) {
		double hitMultiplier = random.nextDouble() + 0.5;
		int damage = (int)(calculateMeleeMaxHit() * hitMultiplier);
		if (opp != null) {
			if (opp.prayerActive[16] || opp.curseActive[7] && System.currentTimeMillis() - opp.protMageDelay > 1500) {
				damage = (int)(damage * 0.6);
			}
		}
		return damage;
	}
	
	public void setColorMasks(int hit1, int maxhit1, int damageMask) {
		if(damageMask == 1) {
			if(hit1 >= maxhit1 *.7) {
				c.colorHitMask = 3;
			} else {
				c.colorHitMask = 1;
			}
		} else {
			if(hit1 >= maxhit1 *.7) {
				c.colorHitMask2 = 3;
			} else {
				c.colorHitMask2 = 1;
			}
		}
		//c.sendMessage("Hit1: " + hit1 + " Max1: " + (maxhit1*.9));
	}
	
    
    public static final int STAB_BONUS = 0,
    						SLASH_BONUS = 1,
    						CRUSH_BONUS = 2,
    						MAGIC_BONUS = 3,
    						RANGE_BONUS = 4,
    						STAB_DEFENCE_BONUS = 5,
    						SLASH_DEFENCE_BONUS = 6,
    						CRUSH_DEFENCE_BONUS = 7,
    						MAGIC_DEFENCE_BONUS = 8,
    						RANGE_DEFENCE_BONUS = 9,
    						SUMMONING_DEFENCE_BONUS = 10,
    						ABSORB_MELEE_BONUS = 11,
    						ABSORB_MAGIC_BONUS = 12,
    						ABSORB_RANGED_BONUS = 13,
    						STRENGTH_BONUS = 14,
    						RANGE_STRENGTH_BONUS = 15,
    						PRAYER_BONUS = 16,
    						MAGIC_DAMAGE_BONUS = 17;
    
    public static double getBonus(Client player, int skill) {
    		return player.playerBonus[skill];
    }
}     