package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Bank X Items
 **/

public class BankX2 implements PacketType {
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int Xamount = c.getInStream().readDWord();
		
        if(c.attackSkill) {
            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 0;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.attackSkill = false;
            } catch (Exception e){}
}
if(c.defenceSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 1;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.defenceSkill = false;
            } catch (Exception e){}
}
            if(c.strengthSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 2;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.strengthSkill = false;
            } catch (Exception e){}
}
            if(c.healthSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 3;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.healthSkill = false;
            } catch (Exception e){}
}
            if(c.rangeSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 4;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.rangeSkill = false;
            } catch (Exception e){}
}
            if(c.prayerSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 5;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.prayerSkill = false;
            } catch (Exception e){}
}
            if(c.mageSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 6;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.mageSkill = false;
            } catch (Exception e){}
}
            if(c.summonSkill) {
                            if (c.inWild())
                    return;
            for (int j = 0; j < c.playerEquipment.length; j++) {
                    if (c.playerEquipment[j] > 0) {
                            c.sendMessage("Please remove all your equipment before using this.");
                            return;
                    }
            }
            try {       
            int skill = 24;
            int level = Xamount;
            if (level > 99)
                    level = 99;
            else if (level < 0)
                    level = 1;
            c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
            c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
            c.getPA().refreshSkill(skill);
			c.getPA().refreshSkill(3);
            c.sendMessage("Please relog if your combat level hasn't changed!");
            c.summonSkill = false;
            } catch (Exception e){}
}
		switch (c.xInterfaceId) {
			case 5064:
			if (!c.usingBoB) {
				if(c.inTrade) {
					c.sendMessage("You can't store items while trading!");
					return;
				}
				c.getItems().bankItem(c.playerItems[c.xRemoveSlot] , c.xRemoveSlot, Xamount);
			} else {
				c.getPA().addToBoB(c.xRemoveSlot, Xamount);
			}
			break;
			
			case 7423:
			if (c.usingBoB) {
				c.getPA().takeFromBoB(c.xRemoveSlot, Xamount); 
			}
			break;
				
			case 5382:
			c.getItems().fromBank(c.bankItems[c.xRemoveSlot] , c.xRemoveSlot, Xamount);
			break;

			case 3322:
			if(!c.getItems().playerHasItem(c.xRemoveId, Xamount))
		return;
			if(c.duelStatus <= 0) {
            	c.getTradeAndDuel().tradeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
            } else {				
				c.getTradeAndDuel().stakeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
			}  
			break;
				
			case 3415: 
				if(!c.getItems().playerHasItem(c.xRemoveId, Xamount))
		return;
			if(c.duelStatus <= 0) { 
            	c.getTradeAndDuel().fromTrade(c.xRemoveId, c.xRemoveSlot, Xamount);
			} 
			break;
				
			case 6669:
			if(!c.getItems().playerHasItem(c.xRemoveId, Xamount))
		return;
			c.getTradeAndDuel().fromDuel(c.xRemoveId, c.xRemoveSlot, Xamount);
			break;			
		}
	}
}