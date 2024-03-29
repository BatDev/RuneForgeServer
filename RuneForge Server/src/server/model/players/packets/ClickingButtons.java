package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.items.GameItem;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;
import server.model.players.SkillGuides;
import server.util.Misc;
import server.model.minigames.GnomeGlider;
import server.model.npcs.NPCHandler;
import server.model.players.RequestHelp;


/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0, packetSize);
			c.getConstruction().constructionButtons(c, actionButtonId);
			GnomeGlider.flightButtons(c, actionButtonId);
			if (c.playerRights == 3 || c.playerRights == 7) {
				Misc.println(c.playerName+ " - actionbutton: "+actionButtonId);
			}
		int[] spellIds = {4128,4130,4132,4134,4136,4139,4142,4145,4148,4151,4153,4157,4159,4161,4164,4165,4129,4133,4137,6006,6007,6026,6036,6046,6056,
			4147,6003,47005,4166,4167,4168,48157,50193,50187,50101,50061,50163,50211,50119,50081,50151,50199,50111,50071,50175,50223,50129,50091};
			for(int i = 0; i < spellIds.length; i++) {
				if(actionButtonId == spellIds[i]) {
					c.autocasting = (c.autocastId != i) ? true : false;
					if (!c.autocasting) {
						c.getPA().resetAutocast();
					} else {
						c.autocastId = i;
					}
				}
			}
		switch (actionButtonId){
		case 33206:
			c.outStream.createFrame(27);
			c.attackSkill = true;
			break;
			case 33209:
			c.outStream.createFrame(27);
			c.strengthSkill = true;
			break;
			case 33212:
			c.outStream.createFrame(27);
			c.defenceSkill = true;
			break;
			case 33215:
			c.outStream.createFrame(27);
			c.rangeSkill = true;
			break;
			case 33218:
			c.outStream.createFrame(27);
			c.prayerSkill = true;
			break;
			case 33221:
			c.outStream.createFrame(27);
			c.mageSkill = true;
			break;
			case 33207:
			c.outStream.createFrame(27);
			c.healthSkill = true;
			break;
			case 77036:
c.outStream.createFrame(27);
c.summonSkill = true;
break;
			case 67050:
			case 67051:
			case 67052:
			case 67053:
			case 67054:
			case 67055:
			case 67056:
			case 67057:
			case 67058:
			case 67059:
			case 67060:
			case 67061:
			case 67062:
			case 67063:
			case 67064:
			case 67065:
			case 67066:
			case 67067:
			case 67068:
			case 67069:
			case 67070:
			case 67071:
			case 67072:
			case 67073:
			case 67074:
			case 67075:
				if (c.altarPrayed == 0)
					c.getQP().clickPray(actionButtonId);
				else
					c.getQC().clickCurse(actionButtonId);
			break;
			case 74056:
				c.getQC().turnOnQuicks();
			break;

			case 74057:
				c.getQC().turnOffQuicks();
			break;
			//start of donator shop
		case 21030: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13362, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Torva Full Helm.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21031: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13358, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Torva Platebody.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21032: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13369, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Torva Platelegs.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21033: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 150) { 
		c.getItems().addItem(18786, 1);
		c.donPoints-=150;
		c.sendMessage("You exchanged 150 donator points for a Korasi's Sword.");
		} else if (c.donPoints <=150) {
		c.sendMessage("You need 150 donator points to buy this item.");
		}
		break;	
		case 21034: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13350, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Virtus Mask.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21035: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13348, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Virtus Robetop.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21036: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13346, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Virtus Robebottom.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21037: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 150) { 
		c.getItems().addItem(15486, 1);
		c.donPoints-=150;
		c.sendMessage("You exchanged 150 donator points for a Staff Of Light.");
		} else if (c.donPoints <=150) {
		c.sendMessage("You need 150 donator points to buy this item.");
		}
		break;	
		case 21038: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13355, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Pernix Cowl.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21039: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13354, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Pernix Body.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21040: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 100) { 
		c.getItems().addItem(13355, 1);
		c.donPoints-=100;
		c.sendMessage("You exchanged 100 donator points for a Pernix Chaps.");
		} else if (c.donPoints <=100) {
		c.sendMessage("You need 100 donator points to buy this item.");
		}
		break;	
		case 21041: 
		if (c.inWild()) {
		c.sendMessage("You cannot use this feature in the wilderness.");
		} else if(c.getItems().freeSlots() <= 1) {
		c.sendMessage("You need atleast 1 free slot's to use this feature.");		
		return;
		} else if (c.donPoints >= 30) { 
		c.getItems().addItem(18509, 1);
		c.donPoints-=30;
		c.sendMessage("You exchanged 30 donator points for a Dung Cape (t).");
		} else if (c.donPoints <=30) {
		c.sendMessage("You need 30 donator points to buy this item.");
		}
		break;	
					case 88056:
				RequestHelp.setInterface(c);
			break;
			case 109114:
				RequestHelp.callForHelp(c);
			break;
		//end of the donator shop
		case 63183:
		c.getPA().removeAllWindows();
		break;
		case 63174:
		c.getPA().sendFrame126("www.area-51.net46.net/index.php", 12000);
		break;
		case 63177:
		c.getPA().sendFrame126("www.area-51.net46.net/vote.php", 12000);
		break;
		case 63180:
		c.getPA().sendFrame126("www.area-51.net46.net/donate.php", 12000);
		break;
			case 67079:
				c.getQC().clickConfirm();
			break;
			
			case 113234:
				if (c.playerRights == 0 || c.playerRights == 1 || c.playerRights == 8 && c.isDonator < 1) {
					c.sendMessage("You must be a donator to use this.");
					return;
				}
				c.getDCP().restoreSpec();
			break;
			case 113251:
				if(c.QP3 == 0) {
				c.quest1Handler.resetInfo();
				c.quest3Handler.ForgottenQuest();
				} else
				if(c.QP3 == 60) {
				c.quest3Handler.forgottenComplete();
				}
			break;
			case 113250:
				//c.sendMessage("Being Fixed!");
				if(c.QP2 >= 0 && c.QP2 <= 7) {
				c.quest1Handler.resetInfo();
				c.quest2Handler.travelersQuest();
				} else
				if(c.QP2 == 8) {
				c.quest2Handler.travelersComplete();
				c.QP2 = 9;
				} else
				if(c.QP2 == 9) {
				c.quest2Handler.travelersCompleted();
				c.sendMessage("You've already completed this quest!");
				}
				break;
			case 113249:
				//c.sendMessage("Being Fixed!");
				if(c.QP1 >= 0 && c.QP1 <= 5) {
				c.quest1Handler.resetInfo();
				c.quest1Handler.firstQuest();
				} else
				if(c.QP1 == 6) {
				c.quest1Handler.questsCompleted();
				c.QP1 = 7;
				} else
				if(c.QP1 == 7) {
				c.quest1Handler.questsCompletedd();
				c.sendMessage("You've already completed this quest!");
				}
				break;
			case 113228://Opens Achievements From Quest-Tab
				c.setSidebarInterface(2, 19500);
			break;
			case 76051:
							c.sendMessage("<shad=255>Type ::forums to go to the forums of RuneForge!");
							break;
						case 76054:
							c.sendMessage("<shad=255>Type ::vote to go and vote for RuneForge!");
							break;
							case 101055:
							c.sendMessage("<shad=255>Go to ::donate and donate to RuneForge!");
							break;
							case 101051:
							c.sendMessage("<shad=255>Go to ::donate and donate to RuneForge!");
							break;
			case 76134://Re-opens Quest Tab (From Achievements)
				c.setSidebarInterface(2, 638);
			break;
	case 144172:
				if (c.newPray == false) {
					c.newPray = true;
					c.getPA().refreshSkill(5);
					c.sendMessage("Your Prayer Is Now x10!");
				} else {
					c.newPray = false;
					c.getPA().refreshSkill(5);
					c.sendMessage("Your Prayer is now back to normal!");
				}
break;	
	case 144175:
				if (c.newHp == false) {
					c.newHp = true;
					c.getPA().refreshSkill(3);
					c.sendMessage("Your Constitution Is Now x10!");
				} else {
					c.newHp = false;
					c.getPA().refreshSkill(3);
					c.sendMessage("Your Constitution is now back to normal!");
				}
break;		
			/**
			 *Summoning Scrolls
			 *@author G Wishart
			**/
				case 58074:
		c.getBankPin().close();
		break;

	case 58025:
	case 58026:
	case 58027:
	case 58028:
	case 58029:
	case 58030:
	case 58031:
	case 58032:
	case 58033:
	case 58034:
		c.getBankPin().pinEnter(actionButtonId);
		break;
			case 66119: 
			switch(c.lastsummon) {
				case 6823:
				case 6874:
				case 7342:
					c.getSummonAddOn().handleSummonScrolls(c.lastsummon);
				break;
			}
			break;
					
			case 82020: //Deposit Inventory
        	if(c.inWild()) {
				c.sendMessage("<shad=6081134>Sorry but you can't deposit in the wilderness.");
       			return;
			} else {
       	 		for(int itemID = 0; itemID < 101; itemID++) {
					for(int invSlot = 0; invSlot < 28; invSlot++) {
						c.getItems().bankItem(itemID, invSlot, 2147000000);
          			}
        		}
			}
       	 	break;
			
			case 150:
				if (c.autoRet == 0)
					c.autoRet = 1;
				else 
					c.autoRet = 0;
			break;
			
			case 17111://stop viewing viewing orb
                c.setSidebarInterface(10, 2449);
                c.viewingOrb = false;
                c.teleportToX = 2399;
                c.teleportToY = 5171;
                c.appearanceUpdateRequired = true;
                c.updateRequired = true;
				break;

            case 59139://viewing orb southwest
                c.viewingOrb = true;
                c.teleportToX = 2388;
                c.teleportToY = 5138;
                c.appearanceUpdateRequired = true;
                c.updateRequired = true;
                break;

            case 59138://viewing orb southeast
                c.viewingOrb = true;
                c.teleportToX = 2411;
                c.teleportToY = 5137;
                c.appearanceUpdateRequired = true;
                c.updateRequired = true;
                break;

            case 59137://viewing orb northeast
                c.viewingOrb = true;
                c.teleportToX = 2409;
                c.teleportToY = 5158;
                c.appearanceUpdateRequired = true;
                c.updateRequired = true;
                break;

            case 59136://viewing orb northwest
                c.viewingOrb = true;
                c.teleportToX = 2384;
                c.teleportToY = 5157;
                c.appearanceUpdateRequired = true;
                c.updateRequired = true;
                break;

            case 59135://viewing orb middle
                c.viewingOrb = true;
                c.teleportToX = 2398;
                c.teleportToY = 5150;
                c.appearanceUpdateRequired = true;
                c.updateRequired = true;
                break;
			case 107229:
				if (c.isDonator == 1 && c.inGWD()) {
				c.Arma = 15;
				c.Band = 15;
				c.Sara = 15;
				c.Zammy = 15;
					c.sendMessage("Your magical donator rank forces your KC to raise to 15!");
				} else {
					c.sendMessage("You must be a donator and be in godwars dungeon to use this!");
				} 
			break;

			case 108003:
				if (c.xpLock == false && c.newHp == false) {
					c.xpLock = true;
					c.newHp = true;
					c.getPA().requestUpdates();
					c.updateRequired = true;
					c.sendMessage("Your XP is now LOCKED!");
				} else {
					c.xpLock = false;
					c.newHp = false;
					c.getPA().requestUpdates();
					c.updateRequired = true;
					c.sendMessage("Your XP is now UNLOCKED!");
				}				
			break;
			
				
		case 108006: //items kept on death?
		c.StartBestItemScan();
		c.EquipStatus = 0;
		for (int k = 0; k < 4; k++)
			c.getPA().sendFrame34a(10494, -1, k, 1);
		for (int k = 0; k < 39; k++)
			c.getPA().sendFrame34a(10600, -1, k, 1);
		if(c.WillKeepItem1 > 0)
			c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0, c.WillKeepAmt1);
		if(c.WillKeepItem2 > 0)
			c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1, c.WillKeepAmt2);
		if(c.WillKeepItem3 > 0)
			c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2, c.WillKeepAmt3);
		if(c.WillKeepItem4 > 0)
			c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
		for(int ITEM = 0; ITEM < 28; ITEM++){
			if(c.playerItems[ITEM]-1 > 0 && !(c.playerItems[ITEM]-1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
 			&& !(c.playerItems[ITEM]-1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
 			&& !(c.playerItems[ITEM]-1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
 			&& !(c.playerItems[ITEM]-1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)){
				c.getPA().sendFrame34a(10600, c.playerItems[ITEM]-1, c.EquipStatus, c.playerItemsN[ITEM]);
				c.EquipStatus += 1;
			} else if(c.playerItems[ITEM]-1 > 0 && (c.playerItems[ITEM]-1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot) && c.playerItemsN[ITEM] > c.WillKeepAmt1){
				c.getPA().sendFrame34a(10600, c.playerItems[ITEM]-1, c.EquipStatus, c.playerItemsN[ITEM]-c.WillKeepAmt1);
				c.EquipStatus += 1;
			} else if(c.playerItems[ITEM]-1 > 0 && (c.playerItems[ITEM]-1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot) && c.playerItemsN[ITEM] > c.WillKeepAmt2){
				c.getPA().sendFrame34a(10600, c.playerItems[ITEM]-1, c.EquipStatus, c.playerItemsN[ITEM]-c.WillKeepAmt2);
				c.EquipStatus += 1;
			} else if(c.playerItems[ITEM]-1 > 0 && (c.playerItems[ITEM]-1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot) && c.playerItemsN[ITEM] > c.WillKeepAmt3){
				c.getPA().sendFrame34a(10600, c.playerItems[ITEM]-1, c.EquipStatus, c.playerItemsN[ITEM]-c.WillKeepAmt3);
				c.EquipStatus += 1;
			} else if(c.playerItems[ITEM]-1 > 0 && (c.playerItems[ITEM]-1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot) && c.playerItemsN[ITEM] > 1){
				c.getPA().sendFrame34a(10600, c.playerItems[ITEM]-1, c.EquipStatus, c.playerItemsN[ITEM]-1);
				c.EquipStatus += 1;
			}
		}
		for(int EQUIP = 0; EQUIP < 14; EQUIP++){
			if(c.playerEquipment[EQUIP] > 0 && !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP+28 == c.WillKeepItem1Slot)
			&& !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP+28 == c.WillKeepItem2Slot)
			&& !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP+28 == c.WillKeepItem3Slot)
			&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP+28 == c.WillKeepItem4Slot)){
				c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]);
				c.EquipStatus += 1;
			} else if(c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP+28 == c.WillKeepItem1Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP]-c.WillKeepAmt1 > 0){
				c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]-c.WillKeepAmt1);
				c.EquipStatus += 1;
			} else if(c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP+28 == c.WillKeepItem2Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP]-c.WillKeepAmt2 > 0){
				c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]-c.WillKeepAmt2);
				c.EquipStatus += 1;
			} else if(c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP+28 == c.WillKeepItem3Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP]-c.WillKeepAmt3 > 0){
				c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]-c.WillKeepAmt3);
				c.EquipStatus += 1;
			} else if(c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP+28 == c.WillKeepItem4Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP]-1 > 0){
				c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]-1);
				c.EquipStatus += 1;
			}
		}
	          	c.ResetKeepItems();
				c.getPA().showInterface(17100);
			break;
			
			//1st tele option
			case 9190:
			if (c.dialogueAction == 106) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15086, 1);
						c.sendMessage("You get a six-sided die out of the dice bag.");
					}
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 107) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15092, 1);
						c.sendMessage("You get a ten-sided die out of the dice bag.");
					}
					c.getPA().closeAllWindows();
				}
				if (c.teleAction == 1) {
					//rock crabs
					c.getPA().spellTeleport(2676, 3715, 0);
				} else if (c.dialogueAction == 90) {
					c.teleAction = -1;
					c.earningPotential = 100;
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 91) {
					c.teleAction = -1;
					if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
					}
					c.setSidebarInterface(6, 1151);
					c.playerMagicBook = 0;
					c.sendMessage("You feel a drain on your memory.");
					c.autocastId = -1;
					c.getPA().resetAutocast();
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 92) {
					c.teleAction = -1;
					c.altarPrayed = 1;
					c.setSidebarInterface(5, 22500);
					c.getPA().removeAllWindows();
					c.startAnimation(645);
					c.sendMessage("You feel more powerful by switching to curses.");
					c.getCombat().resetPrayers();
					c.dialogueAction = -1;
				} else if (c.teleAction == 2) {
					//barrows
					c.getPA().spellTeleport(3565, 3314, 0);
				} else if (c.teleAction == 3) {
					//godwars
					c.sendMessage("You teleported to godwars , DONT USE SUMMONING HERE, IT CAUSES DC");
					c.getPA().spellTeleport(2882, 5310, 2);
				} else if (c.teleAction == 4) {
					//varrock wildy
					c.getPA().spellTeleport(2539, 4716, 0);
				} else if (c.teleAction == 21) {
					c.getPA().spellTeleport(2480, 3437, 0);//2480 3437
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(3046,9779,0);
				} else if (c.teleAction == 8) {
					c.getPA().spellTeleport(3339,3067,0);
				} else if (c.teleAction == 20) {
					c.getPA().spellTeleport(3222, 3218, 0);//3222 3218 
				}
				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2845, 4832, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2786, 4839, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 45) {
					c.getPA().spellTeleport(3339, 3067, 0);
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2398, 4841, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 25) {
					c.getDicing().chooseDiceBag(actionButtonId);
				}
				break;
				//mining - 3046,9779,0
			//smithing - 3079,9502,0

case 154:
	if(c.getPA().wearingdungCape(c.playerEquipment[c.playerCape])) {
		if (c.isDoingSkillcapeAnim) {
 			return;
		} else {
			c.dungtime = 16;
			c.dungemote(c);
		}
}

			if(System.currentTimeMillis() - c.logoutDelay < 8000) {
c.sendMessage("You cannot do skillcape emotes in combat!");
return;
}
		if(System.currentTimeMillis() - c.lastEmote >= 7000) {
	if(c.getPA().wearingCape(c.playerEquipment[c.playerCape])) {
		c.stopMovement();
		c.gfx0(c.getPA().skillcapeGfx(c.playerEquipment[c.playerCape]));
		c.startAnimation(c.getPA().skillcapeEmote(c.playerEquipment[c.playerCape]));
			 } else if(c.playerEquipment[c.playerCape] == 20769) {
					c.getPA().compemote(c);
			} else if(c.playerEquipment[c.playerCape] == 20767) {
					c.getPA().maxemote(c);
	} else {
		c.sendMessage("You must be wearing a Skillcape to do this emote.");
}
			c.lastEmote = System.currentTimeMillis();
}
break;

				case 9191:
				if (c.dialogueAction == 106) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15088, 1);
						c.sendMessage("You get two six-sided dice out of the dice bag.");
					}
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 107) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15094, 1);
						c.sendMessage("You get a twelve-sided die out of the dice bag.");
					}
					c.getPA().closeAllWindows();
				}
				if (c.teleAction == 1) {
					//tav dungeon
					c.getPA().spellTeleport(2884, 9798, 0);
				} else if (c.dialogueAction == 90) {
					c.teleAction = -1;
					c.pcPoints += 1;
					c.magePoints += 1;
					c.pkPoints += 1;
					c.rxPoints += 1;
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 91) {
					c.teleAction = -1;
					if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
					}
					c.setSidebarInterface(6, 12855);
					c.playerMagicBook = 1;
					c.sendMessage("You feel a drain on your memory.");
					c.autocastId = -1;
					c.getPA().resetAutocast();
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.teleAction == 2) {
					//pest control
					c.getPA().spellTeleport(2662, 2650, 0);
				} else if (c.teleAction == 3) {
					//kbd
					c.getPA().spellTeleport(3007, 3849, 0);
				} else if (c.teleAction == 4) {
					//graveyard
					c.getPA().spellTeleport(2981, 3595, 0);
				} else if (c.teleAction == 21) {
					//hunt
					c.getPA().startTeleport(2604, 4772, 0, "modern");
					c.sendMessage("<shad=6081134>Sell the impling Jar's to the general shop!");
					c.sendMessage("<shad=6081134>Buy a Butterfly Net at Bob store in bank if you dont have one");
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(3079,9502,0);
				} else if (c.teleAction == 8) {
					c.getPA().spellTeleport(2785,2701,0);	
				} else if (c.teleAction == 20) {
					c.getPA().spellTeleport(3210,3424,0);//3210 3424
				} else if (c.teleAction == 46) {
					c.getPA().spellTeleport(3052, 9578, 0);
				}
				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2796, 4818, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2527, 4833, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2464, 4834, 0);
					c.dialogueAction = -1;
				}
				break;
			//3rd tele option	

			case 9192:
			if (c.dialogueAction == 106) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15100, 1);
						c.sendMessage("You get a four-sided die out of the dice bag.");
					}
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 107) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15096, 1);
						c.sendMessage("You get a twenty-sided die out of the dice bag.");
				}
					c.getPA().closeAllWindows();
				}
				if (c.teleAction == 1) {
					//slayer tower
					c.getPA().spellTeleport(3428, 3537, 0);
				} else if (c.dialogueAction == 90) {
					c.teleAction = -1;
					c.playerLevel[0] = 1;
					c.playerXP[0] = c.getPA().getXPForLevel(1);
					c.playerLevel[1] = 1;
					c.playerXP[1] = c.getPA().getXPForLevel(1);
					c.playerLevel[2] = 1;
					c.playerXP[2] = c.getPA().getXPForLevel(1);
					c.playerLevel[3] = 1;
					c.playerXP[3] = c.getPA().getXPForLevel(1);
					c.playerLevel[4] = 1;
					c.playerXP[4] = c.getPA().getXPForLevel(1);
					c.playerLevel[5] = 1;
					c.playerXP[5] = c.getPA().getXPForLevel(1);
					c.playerLevel[6] = 1;
					c.playerXP[6] = c.getPA().getXPForLevel(1);
					c.getPA().refreshSkill(0);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(2);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
					c.getPA().refreshSkill(5);
					c.getPA().refreshSkill(6);
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 91) {
					c.teleAction = -1;
					if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
					}
					c.setSidebarInterface(6, 16640);
					c.playerMagicBook = 2;
					c.sendMessage("You feel a drain on your memory.");
					c.autocastId = -1;
					c.getPA().resetAutocast();
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.teleAction == 2) {
					//tzhaar
					c.getPA().spellTeleport(2438, 5168, 0);
					c.sendMessage("To fight Jad, enter the cave.");
				} else if (c.teleAction == 3) {
					//dag kings
					c.getPA().spellTeleport(1910, 4367, 0);
					c.sendMessage("Climb down the ladder to get into the lair.");
				} else if (c.teleAction == 4) {
					//Hillz
					c.getPA().spellTeleport(3351, 3659, 0);
									
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(2597,3408,0);
				} else if (c.teleAction == 8) {
					c.getPA().spellTeleport(3052,9576,0);
				}
				 else if (c.teleAction == 20) {
					c.getPA().spellTeleport(2757,3477,0);
				}

				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2713, 4836, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2162, 4833, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2207, 4836, 0);
					c.dialogueAction = -1;
				}
				break;
			//4th tele option
			case 9193:
			if (c.dialogueAction == 106) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15090, 1);
						c.sendMessage("You get an eight-sided die out of the dice bag.");
					}
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 107) {
					if(c.getItems().playerHasItem(c.diceID, 1)) {
						c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);	
						c.getItems().addItem(15098, 1);
						c.sendMessage("You get the percentile dice out of the dice bag.");
				}
					c.getPA().closeAllWindows();
				}
				if (c.teleAction == 1) {
					//brimhaven dungeon
					c.getPA().spellTeleport(2710, 9466, 0);
					c.sendMessage("You teleported to brimhaven dungeon, be sure to bring antifire-shield.");
				} else if (c.dialogueAction == 90) {
					c.teleAction = -1;
					c.playerLevel[0] = 99;
					c.playerXP[0] = c.getPA().getXPForLevel(100);
					c.playerLevel[1] = 99;
					c.playerXP[1] = c.getPA().getXPForLevel(100);
					c.playerLevel[2] = 99;
					c.playerXP[2] = c.getPA().getXPForLevel(100);
					c.playerLevel[3] = 99;
					c.playerXP[3] = c.getPA().getXPForLevel(100);
					c.playerLevel[4] = 99;
					c.playerXP[4] = c.getPA().getXPForLevel(100);
					c.playerLevel[5] = 99;
					c.playerXP[5] = c.getPA().getXPForLevel(100);
					c.playerLevel[6] = 99;
					c.playerXP[6] = c.getPA().getXPForLevel(100);
					c.getPA().refreshSkill(0);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(2);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
					c.getPA().refreshSkill(5);
					c.getPA().refreshSkill(6);
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 91) {
					c.teleAction = -1;
					c.altarPrayed = 0;
					c.setSidebarInterface(5, 5608);
					c.getPA().removeAllWindows();
					c.startAnimation(645);
					c.sendMessage("Your strength is back to normal.");
					c.getCurse().resetCurse();
					c.dialogueAction = -1;
				} else if (c.teleAction == 2) {
					//duel arena
					if(c.hasNpc)
					return;
					c.getPA().spellTeleport(3358, 3270, 0);
					c.sendMessage("If you find a dupe/bug report it to the owner.");
					c.getItems().deleteItem(15016,c.getItems().getItemSlot(15016),999999999);
					c.getItems().deleteItem(15015,c.getItems().getItemSlot(15015),999999999);
				} else if (c.teleAction == 3) {
					//chaos elemental
					c.getPA().spellTeleport(3295, 3921, 0);
				} else if (c.teleAction == 4) {
					//Fala
				c.getPA().spellTeleport(3086, 3516, 0);

				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(2724,3484,0);
					c.sendMessage("For magic logs, try north of the duel arena.");
				}
				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2660, 4839, 0);
					c.dialogueAction = -1;
				} else if (c.teleAction == 8) {
					c.getPA().spellTeleport(2847,9642,0);
					c.sendMessage("Goodluck!");
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2527, 4833, 0); //astrals here
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2464, 4834, 0); //bloods here
					c.dialogueAction = -1;
				
				} else if (c.teleAction == 20) {
					c.getPA().spellTeleport(2964,3378,0);
				}
				break;
			case 9194:
			if (c.dialogueAction == 106) {
				c.getDH().sendDialogues(107, 0);
				break;
				} else if (c.dialogueAction == 107) {
				c.getDH().sendDialogues(106, 0);
				break;
				}
				if (c.teleAction == 1) {
					//island
					c.getPA().spellTeleport(2905,9730,0);
				} else if (c.teleAction == 2) {
					c.getPA().spellTeleport(2852,3547,0);
				} else if (c.teleAction == 3) {
					c.getPA().spellTeleport(3299,9380,4);
					c.getPA().closeAllWindows();
				} else if (c.teleAction == 4) {
					int random = Misc.random(2);

        				switch (random) {
           			case 0:
                			c.getPA().startTeleport(3245, 3796, 0, "modern");
					c.sendMessage("WATCH OUT, THIS IS MULTI");
					break;
            			case 1:
                			c.getPA().startTeleport(3245, 3796, 0, "modern");
					c.sendMessage("WATCH OUT, THIS IS MULTI");
               		 		break;
				case 2:
                			c.getPA().startTeleport(3245, 3796, 0, "modern");
					c.sendMessage("WATCH OUT, THIS IS MULTI");
               		 		break;
				case 3:
                			c.getPA().startTeleport(3245, 3796, 0, "modern");
					c.sendMessage("WATCH OUT, THIS IS MULTI");
               		 		break;
				}
				} else if (c.teleAction == 8) {
					c.getPA().spellTeleport(2971,9518,1);
					c.sendMessage("BarrelsChest Drops Anchor!");
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(2812,3463,0);
				}
				if (c.dialogueAction == 10 || c.dialogueAction == 11) {
					c.dialogueId++;
					c.getDH().sendDialogues(c.dialogueId, 0);
				} else if (c.dialogueAction == 12) {
					c.dialogueId = 17;
					c.getDH().sendDialogues(c.dialogueId, 0);
				
				} else if (c.teleAction == 20) {
					c.getPA().spellTeleport(3506,3496,0);
				} else if (c.dialogueAction == 25) {
					c.getDH().sendOption5("Dice (10 sides)", "Dice (12 sides)", "Dice (20 sides)", "Dice (up to 100)", "Previous");
				} else if (c.dialogueAction == 90) {
					c.teleAction = -1;
					c.getDH().sendOption5("Normal Magics", "Ancient Magics", "Lunar Magics", "Normal Prayers", "Next Page");
					c.dialogueAction = 91;
				} else if (c.dialogueAction == 91) {
					c.teleAction = -1;
					c.getDH().sendOption5("Curse Prayers", "Coming Soon", "Coming Soon", "Coming Soon", "Close");
					c.dialogueAction = 92;
				}
				break;
			
			/*case 71074:
				if (c.clanId >= 0 && Server.clanChat.clans[c.clanId].owner.equalsIgnoreCase(c.playerName)) {
					if (c.CSLS == 0) {
		if(System.currentTimeMillis() - c.lastEmote >= 1500) {
						Server.clanChat.clans[c.clanId].CS = 1;
						Server.clanChat.sendLootShareMessage(c.clanId, "LootShare has been toggled to " + (!Server.clanChat.clans[c.clanId].lootshare ? "ON" : "OFF") + " by the clan leader.");
						Server.clanChat.clans[c.clanId].lootshare = !Server.clanChat.clans[c.clanId].lootshare;
						c.CSLS = 1;
						Server.clanChat.updateClanChat(c.clanId);
			c.lastEmote = System.currentTimeMillis();
						return;
				}	
				}	
					if (c.CSLS == 1) {
		if(System.currentTimeMillis() - c.lastEmote >= 1500) {
						c.CSLS = 2;
						Server.clanChat.clans[c.clanId].CS = 2;
						Server.clanChat.updateClanChat(c.clanId);
						Server.clanChat.sendLootShareMessage(c.clanId, "LootShare has been toggled to " + (!Server.clanChat.clans[c.clanId].lootshare ? "ON" : "OFF") + " by the clan leader.");
						Server.clanChat.clans[c.clanId].lootshare = !Server.clanChat.clans[c.clanId].lootshare;
			c.lastEmote = System.currentTimeMillis();
						return;

				}	
				}	
					if (c.CSLS == 2) {
		if(System.currentTimeMillis() - c.lastEmote >= 1500) {
						if(Server.clanChat.clans[c.clanId].playerz == 1) {
						c.sendMessage("There must be atleast 2 members in the clan chat to toggle Coinshare ON.");
						c.CSLS = 0;
						Server.clanChat.clans[c.clanId].CS = 0;
						Server.clanChat.updateClanChat(c.clanId);
			c.lastEmote = System.currentTimeMillis();
						return;
						}
						c.CSLS = 3;
						Server.clanChat.clans[c.clanId].CS = 3;
						Server.clanChat.updateClanChat(c.clanId);
						Server.clanChat.sendCoinShareMessage(c.clanId, "CoinShare has been toggled to " + (!Server.clanChat.clans[c.clanId].coinshare ? "ON" : "OFF") + " by the clan leader.");
						Server.clanChat.clans[c.clanId].coinshare = !Server.clanChat.clans[c.clanId].coinshare;
						return;

				}	
				}	
					if (c.CSLS == 3) {
		if(System.currentTimeMillis() - c.lastEmote >= 1500) {
						c.CSLS = 0;
						Server.clanChat.clans[c.clanId].CS = 0;
						Server.clanChat.updateClanChat(c.clanId);
						Server.clanChat.sendCoinShareMessage(c.clanId, "CoinShare has been toggled to " + (!Server.clanChat.clans[c.clanId].coinshare ? "ON" : "OFF") + " by the clan leader.");
						Server.clanChat.clans[c.clanId].coinshare = !Server.clanChat.clans[c.clanId].coinshare;
			c.lastEmote = System.currentTimeMillis();
						return;
				}	
				}	
					} else {
						c.sendMessage("Only the owner of the clan has the power to do that.");
				}	
			break;*/
			case 34185: case 34184: case 34183: case 34182: case 34189: case 34188: case 34187: case 34186: case 34193: case 34192: case 34191: case 34190:
				if (c.craftingLeather)
					c.getCrafting().handleCraftingClick(actionButtonId);
				if (c.getFletching().fletching)
					c.getFletching().handleFletchingClick(actionButtonId);
			break;
			
			case 15147:
				if (c.smeltInterface) {
					c.smeltType = 2349;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 15151:
				if (c.smeltInterface) {
					c.smeltType = 2351;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			
			case 15159:
				if (c.smeltInterface) {
					c.smeltType = 2353;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			
			case 29017:
				if (c.smeltInterface) {
					c.smeltType = 2359;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 29022:
				if (c.smeltInterface) {
					c.smeltType = 2361;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 29026:
				if (c.smeltInterface) {
					c.smeltType = 2363;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			case 108005:
			c.getPA().showInterface(19148);
			break;
			
			case 59004:
			c.getPA().removeAllWindows();
			break;
			
			case 70212:
				if (c.clanId > -1)
					Server.clanChat.leaveClan(c.playerId, c.clanId);
				else
					c.sendMessage("You are not in a clan.");
			break;
			case 62137:
				if (c.clanId >= 0) {
					c.sendMessage("You are already in a clan.");
					break;
				}
				if (c.getOutStream() != null) {
					c.getOutStream().createFrame(187);
					c.flushOutStream();
				}	
			break;
			
			case 9178:
				/*if (c.usingGlory)
					c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, "modern");*/
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(3428, 3538, 0, "modern");
				if (c.dialogueAction == 3)		
					c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, "modern");
				if (c.dialogueAction == 4)
					c.getPA().startTeleport(3565, 3314, 0, "modern");
				if (c.dialogueAction == 20) {
					c.getPA().startTeleport(2897, 3618, 4, "modern");
				}
				if(c.dialogueAction == 100) {
					c.getDH().sendDialogues(25, 946);
				}
				if(c.dialogueAction == 45) {
					c.getPA().startTeleport(3339, 3067, 0, "modern");
				}
			if(c.dialogueAction == 34)          // get rid of summoned npc			
				break;
			
			case 9179:
				/*if (c.usingGlory)
					c.getPA().startTeleport(Config.AL_KHARID_X, Config.AL_KHARID_Y, 0, "modern");*/
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(2884, 3395, 0, "modern");
				if (c.dialogueAction == 3)
					c.getPA().startTeleport(3243, 3513, 0, "modern");
				if (c.dialogueAction == 4)
					c.getPA().startTeleport(2444, 5170, 0, "modern");
				if (c.dialogueAction == 20) {
					c.getPA().startTeleport(2897, 3618, 12, "modern");
				}
				if(c.dialogueAction == 101) {
					c.getDH().sendDialogues(21, 946);
				}
				if(c.dialogueAction == 45) {
					c.getPA().startTeleport(3052, 9578, 0, "modern");
				}	
			break;
			
			case 9180:
				/*if (c.usingGlory)
					c.getPA().startTeleport(Config.KARAMJA_X, Config.KARAMJA_Y, 0, "modern");*/
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(2471,10137, 0, "modern");	
				if (c.dialogueAction == 3)
					c.getPA().startTeleport(3363, 3676, 0, "modern");
				if (c.dialogueAction == 4)
					c.getPA().startTeleport(2659, 2676, 0, "modern");
				if (c.dialogueAction == 20) {
					c.getPA().startTeleport(2897, 3618, 8, "modern");
				}
				if(c.dialogueAction == 101) {
					c.getDH().sendDialogues(23, 946);
				}
				if(c.dialogueAction == 45) {
					c.getPA().startTeleport(2785 ,2701, 0, "modern");
				}
			break;
			
			case 9181:
				/*if (c.usingGlory)
					c.getPA().startTeleport(Config.MAGEBANK_X, Config.MAGEBANK_Y, 0, "modern");*/
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(2669,3714, 0, "modern");
				if (c.dialogueAction == 3)	
					c.getPA().startTeleport(2540, 4716, 0, "modern");
				if (c.dialogueAction == 4) {
					c.sendMessage("Currently disabled due to dupers");
				}
				if (c.dialogueAction == 20) {
					//c.getPA().startTeleport(3366, 3266, 0, "modern");
					//c.killCount = 0;
					c.sendMessage("This will be added shortly");
				} else if (c.dialogueAction == 10 || c.dialogueAction == 101) {
					c.dialogueAction = 0;
					c.getPA().removeAllWindows();
				} else {
					c.getPA().removeAllWindows();
				}
				c.dialogueAction = 0;
				break;
			
			case 1093:
			case 1094:
			case 1097:
				if (c.autocastId > 0) {
					c.getPA().resetAutocast();
				} else {
					if (c.playerMagicBook == 1) {
						if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040)
							c.setSidebarInterface(0, 1689);
						else
							c.sendMessage("You can't autocast ancients without an ancient staff/Chaotic staff.");
					} else if (c.playerMagicBook == 0) {
						if (c.playerEquipment[c.playerWeapon] == 4170 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
							c.setSidebarInterface(0, 12050);
						} else {
							c.setSidebarInterface(0, 1829);
						}	
					}
						
				}		
			break;
			case 9157://barrows tele to tunnels
				if(c.dialogueAction == 1) {
					int r = 4;
					//int r = Misc.random(3);
					switch(r) {
						case 0:
							c.getPA().movePlayer(3534, 9677, 0);
							break;
						
						case 1:
							c.getPA().movePlayer(3534, 9712, 0);
							break;
						
						case 2:
							c.getPA().movePlayer(3568, 9712, 0);
							break;
						
						case 3:
							c.getPA().movePlayer(3568, 9677, 0);
							break;
						case 4:
							c.getPA().movePlayer(3551, 9694, 0);
							break;
					}
				} else if (c.dialogueAction == 2) {
					c.getPA().movePlayer(2507, 4717, 0);		
				} else if (c.dialogueAction == 5) {
					c.getSlayer().giveTask();
				} else if (c.dialogueAction == 6) {
					c.getSlayer().giveTask2();
				} else if (c.dialogueAction == 7) {
					c.getPA().startTeleport(3088,3933,0,"modern");
					c.sendMessage("NOTE: You are now in the wilderness...");
				} else if (c.dialogueAction == 50) {
					c.sendMessage("This is ");
				} else if (c.dialogueAction == 34) {
					c.sendMessage("Coming soon ");
				} else if (c.dialogueAction == 8) {
					c.getPA().resetBarrows();
					c.sendMessage("Your barrows have been reset.");
				} else if (c.nextChat == 1337) {
					if (c.rxPoints > 0) {
						c.specAmount = 10;
						c.rxPoints -= 1;
					} else {
						c.sendMessage("You don't have enough Pk Points.");
					}
				} else if(c.nextChat == 999) {
					if(c.playerRights == 2) {
					if(c.lastsummon > 0) {
						for(int i = 0; i < c.bobItems.length; i += 1) {
							if (c.bobItems[i] > 0) {
								//Server.itemHandler.createGroundItem(c, c.bobItems[i], Server.npcHandler.npcs[c.summoningnpcid].absX, Server.npcHandler.npcs[c.summoningnpcid].absY, 1, c.playerId);
								c.bobItems[i] = -1;
								}
							}
						}
						c.lastsummon = -1;
						c.npcId = -1;
						c.summoningnpcid = 0;
						c.summonTime = -1;
						c.getPA().sendFrame126("", 17017);
						c.getPA().sendFrame126("", 17021);
						c.sendMessage("Your BoB items have dropped on the floor");
					} else if(c.playerRights == 0 || c.playerRights >= 3 || c.playerRights == 1) {
					if(c.lastsummon > 0) {
						for(int i = 0; i < c.bobItems.length; i += 1) {
							if (c.bobItems[i] > 0) {
								Server.itemHandler.createGroundItem(c, c.bobItems[i], NPCHandler.npcs[c.summoningnpcid].absX, NPCHandler.npcs[c.summoningnpcid].absY, 1, c.playerId);
								c.bobItems[i] = -1;
								}
							}
						}
						c.lastsummon = -1;
						c.npcId = -1;
						c.summoningnpcid = 0;
						c.summonTime = -1;
						c.getPA().sendFrame126("", 17017);
						c.getPA().sendFrame126("", 17021);
						c.sendMessage("Your BoB items have drop on the floor");
					} else {
						c.sendMessage("You do not have a npc currently spawned");
						return;
						}
				 } else if (c.dialogueAction == 27) {
					c.getPA().movePlayer(3086, 3493, 0);
					c.monkeyk0ed = 0;
					c.Jail = false;
					c.forcedText = "I swear to god that i will never break the rules anymore!";
					c.forcedChatUpdateRequired = true;
					c.updateRequired = true;
				}
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
				break;
			
			case 9158:
				if (c.dialogueAction == 8) {
					c.getPA().fixAllBarrows();
				} else {
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
				}
			case 107243:
				c.setSidebarInterface(4, 1644);
				break;

			case 107215:
				c.setSidebarInterface(11, 904);
				break;
			
			/**Specials**/
			case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E C I A L  A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

			case 66127:
				if (c.lastsummon > 0) {
					c.getDH().Kill();
				} else {
					c.sendMessage("You don't have a summon to dismiss");
				}
			break;

			case 66126: //call summon
				if (c.cannotSummon()) {
					c.sendMessage("You can't call your familiar in this area.");
					return;
				}
				if (c.lastsummon > 0 && c.summoningnpcid > 0) {
					NPCHandler.npcs[c.summoningnpcid].isDead = true;
					NPCHandler.npcs[c.summoningnpcid].applyDead = true;
					c.Summoning.SummonNewNPC(c.lastsummon, c.Summoning().getNpcDataIndex(c.lastsummon));
					NPCHandler.npcs[c.summoningnpcid].gfx0(1315);
					NPCHandler.npcs[c.summoningnpcid].underAttackBy2 = -1;	
					NPCHandler.npcs[c.summoningnpcid].updateRequired = true;
					NPCHandler.npcs[c.summoningnpcid].dirUpdateRequired = true;
					NPCHandler.npcs[c.summoningnpcid].getNextWalkingDirection();
				} else {
					c.sendMessage("<col=8345667>You don't have a familiar to call!");
				}
			break;
			
			case 66142: //renew summon
				if (c.summonTime > 120) {
					c.sendMessage("You have more than 60 seconds left on your familiar, so you can't renew it.");
					return;
				}
				if (c.lastsummon < 0) {
					c.sendMessage("You don't have a summon to renew");
					return;
				}
			break;

			case 66122:
			/*if (c.lastsummon != c.getPA().bobNpcSummon()) {
				c.sendMessage("This summon can't hold any items.");
				return;
			}*/
			switch(c.lastsummon) {
				case 6806: //thorny snail
				case 6807:
				case 6994: //spirit kalphite
				case 6995:
				case 6867: //bull ant
				case 6868:
				case 6794: //spirit terrorbird
				case 6795:
				case 6815: //war tortoise
				case 6816:
				case 6873: //pack yak
				case 6874:
					for (int i = 0; i < c.bobSlotCount; i++) {
						if (c.bobItems[i] > 0) {
							c.getPA().takeFromBoB(i, 1);
							c.startAnimation(827);
							c.stopMovement();
						} else {
							c.sendMessage("Your BoB doesn't have any items stored.");
						}
					}
				break;
			}
			break;

			
			case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29038:
			if(c.playerEquipment[c.playerWeapon] == 13902) {
			c.specBarId = 7486;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			} else {
			c.specBarId = 7486;
			/*if (c.specAmount >= 5) {
				c.attackTimer = 0;
				c.getCombat().attackPlayer(c.playerIndex);
				c.usingSpecial = true;
				c.specAmount -= 5;
			}*/
			c.getCombat().handleGmaulPlayer();
			c.getItems().updateSpecialBar();
			}
			break;
			
			case 29063:
			if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]) + (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;
			
			case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

			case 30108:
			c.specBarId = 7812;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			case 76053:
			c.getPA().showInterface(25900);
			break;
			case 76052:
							c.sendMessage("THANK YOU FOR READING RULES.");
				c.sendMessage("<col=255>1. Do not ask staff for items/ranks.");
				c.sendMessage("<col=255>2. Pking HAS NO RULES! CEPT NO PKP FARMING");
				c.sendMessage("<col=255> - Giveback fights are at your own risk! NO REFUND!");
				c.sendMessage("<col=255>3. Do not use offensive language.");
				c.sendMessage("<col=255>4. Do not scam items/accounts.");
				c.sendMessage("<col=255>5. Auto clickers ARE NOT ALOWED.");
				c.sendMessage("<col=255>6. Auto typers ARE ONLY ALOWED IF YOU PUT SECONDS AT 5+");
				c.sendMessage("<col=255>7. Trading RS related things such as Items, RSGP, RSAcc's are at your own risk!");
				c.sendMessage("<col=255>-  If you get scammed, you will NOT get items back.");
				c.sendMessage("<col=255>8. Do not disrespect staff!");
				break;
						case 86004:
			c.getPA().showInterface(15150);
			break;
			case 86008:
			c.getPA().showInterface(5292);
			break;
			case 29138:
			if(c.playerEquipment[c.playerWeapon] == 15486) {
				if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
					c.gfx0(1958);
					c.SolProtect = 120;
					c.startAnimation(10518);
					c.getItems().updateSpecialBar();
					c.usingSpecial = !c.usingSpecial;
					c.sendMessage("All damage will be split into half for 1 minute.");
					c.getPA().sendFrame126("@bla@S P E C I A L  A T T A C K", 7562);
				} else {
					c.sendMessage("You don't have the required special energy to use this attack.");
				}	
			}			
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			/**Dueling**/			
			case 26065: // no forfeit
			case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;
			
			case 26066: // no movement
			case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;
			
			case 26069: // no range
			case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;
			
			case 26070: // no melee
			case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;				
			
			case 26071: // no mage
			case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;
				
			case 26072: // no drinks
			case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;
			
			case 26073: // no food
			case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;
			
			case 26074: // no prayer
			case 26047:	
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;
			
			case 26076: // obsticals
			case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;
			
			case 2158: // fun weapons
			case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;
			
			case 30136: // sp attack
			case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;	

			case 53245: //no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;
			
			case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;
			
			case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;
			
			case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;
			
			case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;
			
			case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;
			
			case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;
			
			case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;
			
			case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;
			
			case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;
			
			case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;
			
			case 26018:	
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if(o == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}
			
			if(c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if(c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if(o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			
			if(c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getTradeAndDuel().confirmDuel();
				o.getTradeAndDuel().confirmDuel();
			}
			break;
			
			case 25120:
			if(c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) PlayerHandler.players[c.duelingWith];
			if(o1 == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			c.duelStatus = 4;
			if(o1.duelStatus == 4 && c.duelStatus == 4) {				
				c.getTradeAndDuel().startDuel();
				o1.getTradeAndDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
			break;
	
			
			case 4169: // god spell charge
			c.usingMagic = true;
			if(!c.getCombat().checkMagicReqs(48)) {
				break;
			}
				
			if(System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay	= System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
	        break;
			
			
			case 28164: // item kept on death 
			break;
			
			

case 152:
if(c.lastsummon == 6873 || c.lastsummon == 6874 || c.lastsummon == 6794 || c.lastsummon == 6795 || c.lastsummon == 6815 || c.lastsummon == 6816 || c.lastsummon == 6867 || c.lastsummon == 6868 || c.lastsummon == 6994 || c.lastsummon == 6995 || c.lastsummon == 6806 || c.lastsummon == 6807 ) {
c.sendMessage("You are now storing items inside your BoB");
	c.Summoning().store();
			} else {
			c.sendMessage("You do not have a npc currently spawned");
		return;
		}
break;
	
			
			case 153:
							if (c.lastsummon > 0) {
					c.getDH().Kill();
				} else {
					c.sendMessage("You don't have a summon to dismiss");
				}
			break;
			

	
			
						/*case 32195://1
			case 32196:
				c.getAgil().AgilityTicketCounter(c, "1", 2996, 1, 100);
				break;
			case 32203://10
			case 32197:
				c.getAgil().AgilityTicketCounter(c, "10", 2996, 10, 300);
				break;
			case 32204://25
			case 32198:
				c.getAgil().AgilityTicketCounter(c, "25", 2996, 25, 450);
				break;
			case 32199://100
			case 32205:
				c.getAgil().AgilityTicketCounter(c, "100", 2996, 100, 750);
				break;
			case 32200://1000
			case 32206:
				c.getAgil().AgilityTicketCounter(c, "1000", 2996, 1000, 1000);
				break;*/
			case 32192://toadflex
			case 32190:
			case 32202://snapdragon
			case 32201:
			case 32193://piratehook
			case 32189:
				c.sendMessage("Not Available!");
				break;
			
			case 9154:
			c.logout();
			break;
			
			case 82016:
			c.takeAsNote = !c.takeAsNote;
			break;
			
	
			//home teleports
			case 4171://normal
			case 50056://ancient
			case 117048://lunar
			//String type[] = {"modern", "ancient", "lunar"};
			//String type = type[c.playerMagicBook];
			String type = c.playerMagicBook == 0 ? "modern" : "ancient";
			c.getPA().startTeleport(2606, 3093, 0, type);
			c.getPA().resetFishing();
			break;
			
			case 50235:
			case 4140:
			case 117112:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Rock Crabs", "Taverly Dungeon", "Slayer Tower", "Brimhaven Dungeon", "Hill Giants");

			c.teleAction = 1;
			break;

			
			case 4143:
			case 50245:
			case 117123:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Barrows", "Pest Control", "TzHaar Cave", "Duel Arena", "Warrior Guild - Dragon Defender");
			c.teleAction = 2;
			break;
			
			case 50253:
			case 117131:
			case 4146:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Godwars", "King Black Dragon (Wild)", "Dagannoth Kings", "Chaos Elemental (Wild)", "Corporeal Beast");
			c.teleAction = 3;
			break;
			

			case 51005:
			case 117154:
			case 4150:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Mage Bank", "Green Dragons(13 Wild)", "East Dragons (18 Wild)", "Edgeville", "Level 35 Wild (MULTI)");
			c.teleAction = 4;
			break;			
			
			case 51013:
			case 6004:	
			case 117162:
			c.getPA().resetFishing();	
			c.getDH().sendOption5("Mining", "Smithing", "Fishing/Cooking", "Woodcutting", "Farming");
			c.teleAction = 5;
			break; 
			
			
			case 51023:
			case 6005:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Lumbridge", "Varrock", "Camelot", "Falador", "Canifis");
			c.teleAction = 20;
			break; 
			
				

			case 72038:
			case 51039:
			case 117186:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Desert Strykeworm", "Jungle Strykeworm", "Ice Strykeworm", "Nomad", "Barrelschest Boss");
			c.teleAction = 8;
			break;

			case 29031:
			case 51031:
			c.getPA().resetFishing();
			c.getDH().sendOption5("Agility", "Hunter", "Coming soon!", "Coming soon!", "Coming soon!");
			c.teleAction = 21;
			break;
			
      			case 9125: //Accurate
			case 6221: // range accurate
			//case 22230: //kick (unarmed)
			case 48010: //flick (whip)
			case 21200: //spike (pickaxe)
			case 1080: //bash (staff)
			case 6168: //chop (axe)
			case 6236: //accurate (long bow)
			case 17102: //accurate (darts)
			case 8234: //stab (dagger)

			case 30088: //claws
			case 1177: //hammer
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			case 9126: //Defensive
			case 48008: //deflect (whip)
			//case 22228: //punch (unarmed)
			case 21201: //block (pickaxe)
			case 1078: //focus - block (staff)
			case 6169: //block (axe)
			case 33019: //fend (hally)
			case 18078: //block (spear)
			case 8235: //block (dagger)
			case 1175: //accurate (darts)
			case 30089: //stab (dagger)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			case 9127: // Controlled
			case 48009: //lash (whip)
			case 33018: //jab (hally)
			case 6234: //longrange (long bow)
			case 6219: //longrange
			case 18077: //lunge (spear)
			case 18080: //swipe (spear)
			case 18079: //pound (spear)
			case 17100: //longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			case 9128: //Aggressive
			case 6220: // range rapid
			//case 22229: //block (unarmed)
			case 21203: //impale (pickaxe)
			case 21202: //smash (pickaxe)
			case 1079: //pound (staff)
			case 6171: //hack (axe)
			case 6170: //smash (axe)
			case 33020: //swipe (hally)
			case 6235: //rapid (long bow)
			case 17101: //repid (darts)
			case 8237: //lunge (dagger)
			case 30091: //claws
			case 1176: //stat hammer
			case 8236: //slash (dagger)

			case 30090: //claws
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			/**CURSE Prayers**/
              		case 87231: // protect Item
			if(c.trade11 > 1) {
			for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows 
				c.prayerActive[p] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);	
			}
			c.sendMessage("You must wait 15 minutes before using this!");
			return;
			}
	        if (c.playerLevel[3] == 0);
					else
			c.getCurse().activateCurse(0);
			break;	
			case 87233: // sap warrior
			c.getCurse().activateCurse(1);
                
			break;	
			case 87235: // sap ranger
                        c.getCurse().activateCurse(2);
 
			break;	
			case 87237: // sap mage
			c.getCurse().activateCurse(3);
			break;
			case 87239: // sap spirit
			c.getCurse().activateCurse(4);
			
			break;
			case 87241: // berserker
			c.startAnimation(12589);
			c.gfx0(2266);
			c.getCurse().activateCurse(5);
			break;
			case 87243: // deflect summoning
			c.getCurse().activateCurse(6);
			break;
			case 87245: // deflect mage
			c.getCurse().activateCurse(7);
			break;
			case 87247: //deflect range
			c.getCurse().activateCurse(8);
			break;
			case 87249: // deflect meele
			c.getCurse().activateCurse(9);
			break;
			case 87251: // Leech attack
			c.getCurse().activateCurse(10);
                   
			break;			
			case 87253: // leech range
			c.getCurse().activateCurse(11);
              	
			break;
			case 87255: // leech mage
			c.getCurse().activateCurse(12);
			break;	
			case 88001: // leech def
			c.getCurse().activateCurse(13);
		
			break;
			case 88003: // leech str
			c.getCurse().activateCurse(14);	
         	
			break;
			case 88005: // leech run
			c.getCurse().activateCurse(15);
			c.sendMessage("Everyone has unlimited energy. There is no point doing this!");
             c.forcedText = "Im an idiot cuz i tried to leech run energy! Everyone has unlimited energy!";
			break;	
			case 88007: // leech spec
			c.getCurse().activateCurse(16);
			
			break;					
			case 88009: // Wrath
			c.getCurse().activateCurse(17);
			break;
			case 88011: // SS
			c.getCurse().activateCurse(18);
			break;
			case 88013: // turmoil
					c.getCurse().activateCurse(19);
			break;	
			/**End of curse prayers**/
			
			
			case 89219:
				c.getPA().removeAllWindows();
			break;
	/*
	* @author Jilic-Matt
	* @scroll pouch creation 
	*/
			case 151045:
				c.getPA().removeAllWindows();
				c.getPA().sendFrame126("Summoning Pouch Creation", 39707);
				c.getPA().showInterface(39700);
			break;
			case 155026:
				if (c.playerRights >= 3) {
					c.getPA().removeAllWindows();
					c.getPA().sendFrame126("Summoning Scroll Creation", 39707);
					c.getPA().showInterface(38700);
				} else {
					c.sendMessage("This feature is currently unavailable");
				}
			break;
			
			case 155031:
			case 155034:
			case 155037:
			case 155040:
			case 155043:
			case 155046:
			case 155049:
			case 155052: // Spirit scorpion
			case 155055: // spirit tz-kih
			case 155058: // albino rat
			case 155061: // spirit kalphite
			case 155064: // compost mound
			case 155067: // giant chinchompa
			case 155070: // vampire bat
			case 155073: // honey badger
			case 155076: // beaver
			case 155079: // void ravager
			case 155082: // void spinner
			case 155085: // void torcher
			case 155088: // void shifter
			case 155091: // bronze minotaur
			case 155094: // bull ant
			case 155097: // macaw
			case 155100: // evil turnip
			case 155103: // Spirit Cockatrice
			case 155106: // Spirit Guthatrice
			case 155109: // Spirit Saratrice
			case 155112: // Spirit Zamatrice
			case 155115: // Spirit Pengatrice
			case 155118: // Spirit Coraxatrice
			case 155121: // Spirit Vulatrice
			case 155124: // Iron Minotaur
			case 155127: // pyrelord
			case 155130: // magpie
			case 155133: // bloated leech
			case 155136: // spirit terrorbird
			case 155139: // abyssal parasite
			case 155142: // spirit jelly
			case 155145: // steel minotaur
			case 155148: // ibis
			case 155151: // spirit kyatt
			case 155154: // spirit larupia
			case 155157: // spirit graahk
			case 155160: // karamthulhu overlord
			case 155163: // smoke devil
			case 155166: // abyssal lurker
			case 155169: // spirit cobra
			case 155172: // stranger plant
			case 155175: // mithril minotaur
			case 155178: // barker toad
			case 155181: // war tortoise
			case 155184: // bunyip
			case 155187: // fruit bat
			case 155190: // ravenous locust
			case 155193: // arctic bear
			case 155196: // phoenix
			case 155199: // obby golem
			case 155202: // granite crab
			case 155205: // praying mantis
			case 155208: // forge regent		
			case 155211: // addy minotaur
			case 155214: // talon beast
			case 155217: // giant ent	
			case 155220: // fire titan
			case 155223: // moss titan
			case 155226: // ice titan
			case 155229: // hydra
			case 155232: // spirit dagannoth
			case 155235: // lava titan
			case 155238: // swamp titan
			case 155241: // rune minotaur
			case 155244: // unicorn stallion
			case 155247: // geyser titan
			case 155250: // wolpertinger
			case 155253: // abyssal titan
			case 156000: // iron titan	
			case 156003: // pack yak
			case 156006: // steel titan
				c.Summoning().makeSummoningPouch(c, actionButtonId);
			break;
		
		/*
		*@ Options Tab
		*/
			case 156068: // Graphics options
				c.getPA().showInterface(40030);
			break;			
			case 156069: // Sound options
				c.getPA().showInterface(40010);
			break;
		/*
		*@ Options Tab
		*/
			
		
			/**Prayers**/
			case 97168: // thick skin
			c.getCombat().activatePrayer(0);
			break;	
			case 97170: // burst of str
			c.getCombat().activatePrayer(1);
			break;	
			case 97172: // charity of thought
			c.getCombat().activatePrayer(2);
			break;	
			case 97174: // range
			c.getCombat().activatePrayer(3);
			break;
			case 97176: // mage
			c.getCombat().activatePrayer(4);
			break;
			case 97178: // rockskin
			c.getCombat().activatePrayer(5);
			break;
			case 97180: // super human
			c.getCombat().activatePrayer(6);
			break;
			case 97182:	// improved reflexes
			c.getCombat().activatePrayer(7);
			break;
			case 97184: //hawk eye
			c.getCombat().activatePrayer(8);
			break;
			case 97186:
			c.getCombat().activatePrayer(9);
			break;
			case 97188: // protect Item
			if(c.trade11 > 1) {
			for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows 
				c.prayerActive[p] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);	
			}
			c.sendMessage("You must wait 15 minutes before using this!");
			return;
			}
			c.getCombat().activatePrayer(10);
			break;			
			case 97190: // 26 range
			c.getCombat().activatePrayer(11);
			break;
			case 97192: // 27 mage
			c.getCombat().activatePrayer(12);
			break;	
			case 97194: // steel skin
			c.getCombat().activatePrayer(13);
			break;
			case 97196: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
			case 97198: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;	
			case 97200: // protect from magic
			c.getCombat().activatePrayer(16);
			break;					
			case 97202: // protect from range
			c.getCombat().activatePrayer(17);
			break;
			case 97204: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
			case 97206: // 44 range
			c.getCombat().activatePrayer(19);
			break;	
			case 97208: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;				
			case 97210: // retrui
			c.getCombat().activatePrayer(21);
			break;					
			case 97212: // redem
			c.getCombat().activatePrayer(22);
			break;					
			case 97214: // smite
			c.getCombat().activatePrayer(23);
			break;
			case 97216: // chiv
			c.getCombat().activatePrayer(24);
			break;
			case 97218: // piety
			c.getCombat().activatePrayer(25);
			break;
			//END OF PRAYER
			case 34158:
			case 33210: //Agility
				SkillGuides.agilityInterface(c);
				break;
			case 34161:
			case 33213: //Herblore
				SkillGuides.herbloreInterface(c);
				break;
			case 59199:
			case 33216: //Theiving
				SkillGuides.thievingInterface(c);
				break;	
			case 59202:
			case 33219: //craft
				SkillGuides.craftingInterface(c);
				break;	
			case 33222: //Fletching
				SkillGuides.fletchingInterface(c);
				break;	
			case 59205: 
			case 33208: //Mining
				SkillGuides.miningInterface(c);
				break;	
			case 33211: //Smithing
				SkillGuides.smithingInterface(c);
				break;	
			case 33214: //Fishing
				SkillGuides.fishingInterface(c);
				break;	
			case 33217: //Cooking
				SkillGuides.cookingInterface(c);
				break;	
			case 33220: //Firemaking
				SkillGuides.firemakingInterface(c);
				break;	
			case 33223: //Woodcutting
				SkillGuides.woodcuttingInterface(c);
				break;	
			case 54104: //Farming
				SkillGuides.farmingInterface(c);
				break;
			
			case 13092:
                        if (System.currentTimeMillis() - c.lastButton < 400) {

					c.lastButton = System.currentTimeMillis();

					break;

				} else {

					c.lastButton = System.currentTimeMillis();

				}
			Client ot = (Client) PlayerHandler.players[c.tradeWith];
			if(ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);	
			c.goodTrade= true;
			ot.goodTrade= true;
			
			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if(ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems.size()) {					
						c.sendMessage(ot.playerName +" only has "+ot.getItems().freeSlots()+" free slots, please remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						ot.sendMessage(c.playerName +" has to remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items or you could offer them "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						c.goodTrade= false;
						ot.goodTrade= false;
						c.getPA().sendFrame126("Not enough inventory space...", 3431);
						ot.getPA().sendFrame126("Not enough inventory space...", 3431);
							break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...", 3431);				
						ot.getPA().sendFrame126("Other player has accepted", 3431);
						c.goodTrade= true;
						ot.goodTrade= true;
						}
					}	
				}	
				if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
					c.tradeConfirmed = true;
					if(ot.tradeConfirmed) {
						c.getTradeAndDuel().confirmScreen();
						ot.getTradeAndDuel().confirmScreen();
						break;
					}
							  
				}

		
			break;
					
			case 13218:
                         if (System.currentTimeMillis() - c.lastButton < 400) {

					c.lastButton = System.currentTimeMillis();

					break;

				} else {

					c.lastButton = System.currentTimeMillis();

				}
			c.tradeAccepted = true;
			Client ot1 = (Client) PlayerHandler.players[c.tradeWith];
				if (ot1 == null) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}
				
				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed && !c.tradeConfirmed2) {
					c.tradeConfirmed2 = true;
					if(ot1.tradeConfirmed2) {	
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.getTradeAndDuel().giveItems();
						ot1.getTradeAndDuel().giveItems();
						c.sendMessage("Trade accepted.");
						c.SaveGame();
						ot1.SaveGame();
						ot1.sendMessage("Trade accepted.");
						break;
					}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
				}
				
			break;			
			/* Rules Interface Buttons */
			case 125011: //Click agree
				if(!c.ruleAgreeButton) {
					c.ruleAgreeButton = true;
					c.getPA().sendFrame36(701, 1);
				} else {
					c.ruleAgreeButton = false;
					c.getPA().sendFrame36(701, 0);
				}
				break;
			case 125003://Accept
				if(c.ruleAgreeButton) {
					c.getPA().showInterface(3559);
					c.newPlayer = false;
				} else if(!c.ruleAgreeButton) {
					c.sendMessage("You need to click on you agree before you can continue on.");
				}
				break;
			case 125006://Decline
				c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
				break;
			/* End Rules Interface Buttons */
			/* Player Options */
			case 74176:
				if(!c.mouseButton) {
					c.mouseButton = true;
					c.getPA().sendFrame36(500, 1);
					c.getPA().sendFrame36(170,1);
				} else if(c.mouseButton) {
					c.mouseButton = false;
					c.getPA().sendFrame36(500, 0);
					c.getPA().sendFrame36(170,0);					
				}
				break;
			case 74184:
				if(!c.splitChat) {
					c.splitChat = true;
					c.getPA().sendFrame36(502, 1);
					c.getPA().sendFrame36(287, 1);
				} else {
					c.splitChat = false;
					c.getPA().sendFrame36(502, 0);
					c.getPA().sendFrame36(287, 0);
				}
				break;
			case 100231:
				if(!c.chatEffects) {
					c.chatEffects = true;
					c.getPA().sendFrame36(501, 1);
					c.getPA().sendFrame36(171, 0);
				} else {
					c.chatEffects = false;
					c.getPA().sendFrame36(501, 0);
					c.getPA().sendFrame36(171, 1);
				}
				break;
			case 100237:
				if(!c.acceptAid) {
					c.acceptAid = true;
					c.getPA().sendFrame36(503, 1);
					c.getPA().sendFrame36(427, 1);
				} else {
					c.acceptAid = false;
					c.getPA().sendFrame36(503, 0);
					c.getPA().sendFrame36(427, 0);
				}
				break;
			case 74201://brightness1
				c.getPA().sendFrame36(505, 1);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166, 1);
				break;
			case 74203://brightness2
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 1);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,2);
				break;

			case 74204://brightness3
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 1);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,3);
				break;

			case 74205://brightness4
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 1);
				c.getPA().sendFrame36(166,4);
				break;
			case 74206://area1
				c.getPA().sendFrame36(509, 1);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74207://area2
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 1);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74208://area3
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 1);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74209://area4
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 1);
				break;
			case 168:
                c.startAnimation(855);		c.stopMovement();
            break;
            case 169:
                c.startAnimation(856);		c.stopMovement();
            break;
            case 162:
                c.startAnimation(857);		c.stopMovement();
            break;
            case 164:
                c.startAnimation(858);		c.stopMovement();
            break;
            case 165:
                c.startAnimation(859);		c.stopMovement();
            break;
            case 161:
                c.startAnimation(860);		c.stopMovement();
            break;
            case 170:
                c.startAnimation(861);		c.stopMovement();
            break;
            case 171:
                c.startAnimation(862);		c.stopMovement();
            break;
            case 163:
                c.startAnimation(863);		c.stopMovement();
            break;
            case 167:
                c.startAnimation(864);		c.stopMovement();
            break;
            case 172:
                c.startAnimation(865);		c.stopMovement();
            break;
            case 166:
                c.startAnimation(866);		c.stopMovement();
            break;
            case 52050:
                c.startAnimation(2105);		c.stopMovement();
            break;
            case 52051:
                c.startAnimation(2106);		c.stopMovement();
            break;
            case 52052:
                c.startAnimation(2107);		c.stopMovement();
            break;
            case 52053:
                c.startAnimation(2108);		c.stopMovement();
            break;
            case 52054:
                c.startAnimation(2109);		c.stopMovement();
            break;
            case 52055:
                c.startAnimation(2110);		c.stopMovement();
            break;
            case 52056:
                c.startAnimation(2111);		c.stopMovement();
            break;
            case 52057:
                c.startAnimation(2112);		c.stopMovement();
            break;
            case 52058:
                c.startAnimation(2113);		c.stopMovement();
            break;
            case 43092:
                c.startAnimation(1374);		c.stopMovement();
				c.gfx0(574);
            break;
            case 2155:
                c.startAnimation(11044);		c.stopMovement();
				c.gfx0(1973);
            break;
            case 25103:
                c.startAnimation(10530);		c.stopMovement();
				c.gfx0(1864);
            break;
            case 25106:
				c.startAnimation(8770);
				c.gfx0(1553);		c.stopMovement();
            break;
            case 2154:
                c.startAnimation(7531);		c.stopMovement();
            break;
            case 52071:
                c.startAnimation(0x84F);		c.stopMovement();
            break;
            case 52072:
                c.startAnimation(0x850);		c.stopMovement();
            break;
            case 73003:
		c.startAnimation(6111);	c.stopMovement();
            break;
            case 73001:
                c.startAnimation(3544);		c.stopMovement();
            break;
            case 73000:
			if(System.currentTimeMillis() - c.logoutDelay < 8000) {
c.sendMessage("You cannot do skillcape emotes in combat!");
return;
}
                c.startAnimation(3543);		c.stopMovement();
            break;
			case 72032:
				c.startAnimation(9990);		c.stopMovement();
				c.gfx0(1734);
            break;
			case 72033:
				c.startAnimation(4278);		c.stopMovement();
            break;
			case 59062:
				c.startAnimation(4280);		c.stopMovement();
            break;
			case 72254:
				c.startAnimation(4275);		c.stopMovement();
            break;
			case 73004:
				c.startAnimation(7272);		c.stopMovement();
				c.gfx0(1244);
            break;
			case 72255:
			if(System.currentTimeMillis() - c.logoutDelay < 8000) {
c.sendMessage("You cannot do skillcape emotes in combat!");		c.stopMovement();
return;
}
				c.startAnimation(2414);
				c.gfx0(1537);
			break;
			/* END OF EMOTES */
			case 28166:
				
				break;
case 118098:
c.getPA().castVeng();
break; 
			

				case 77035:
		c.forcedText = "[QC] My Hunter level is  " + c.getPA().getLevelForXP(c.playerXP[21]) + ".";
		c.forcedChatUpdateRequired = true;
		c.updateRequired = true;
			break;
			case 47130:
				c.forcedText = "I must slay another " + c.taskAmount + " " + Server.npcHandler.getNpcListName(c.slayerTask) + ".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			
			case 24017:
				c.getPA().resetAutocast();
				//c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				//c.setSidebarInterface(0, 328);
				//c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 : c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

}
