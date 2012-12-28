package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.CoinBag;


/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();
		boolean torvaChanged = false;
		
		if (c.playerIndex > 0 || c.npcIndex > 0)
			c.getCombat().resetPlayerAttack();
		if (c.wearId >= 5509 && c.wearId <= 5515) {
			int pouch = -1;
			int a = c.wearId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().emptyPouch(pouch);
			return;
		}
		if(c.wearId == 6465) {
		if(c.playerRights == 0 ||  c.playerRights == 1 ||  c.playerRights == 2 ||  c.playerRights == 5 ||  c.playerRights ==  6) {
		c.sendMessage("This ring is not for you!");
		return;
		} else 
		if(c.playerRights == 3 ||  c.playerRights == 7) {
		c.sendMessage("Do not abuse this ring!");
		}
		}
		if(c.wearId == 19669) {
		if(c.playerRights == 0 ||  c.playerRights == 1 ||  c.playerRights == 2) {
		c.sendMessage("This ring is not for you!");
		return;
		} else 
		if(c.playerRights == 3 ||  c.playerRights == 4 || c.playerRights == 5 || c.playerRights == 6) {
		c.sendMessage("Do not abuse this ring!");
		}
		}
		if (c.wearId == 10521) {
			CoinBag.emptyCoinBag(c, c.wearId);
			return;
		}
		if(c.wearId == 9748) {
		if(c.playerLevel[0] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9754) {
		if(c.playerLevel[1] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9751) {
		if(c.playerLevel[2] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9769) {
		if(c.playerLevel[3] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9757) {
		if(c.playerLevel[4] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9760) {
		if(c.playerLevel[5] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9763) {
		if(c.playerLevel[6] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9802) {
		if(c.playerLevel[7] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9808) {
		if(c.playerLevel[8] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9784) {
		if(c.playerLevel[9] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9799) {
		if(c.playerLevel[10] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9805) {
		if(c.playerLevel[11] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9781) {
		if(c.playerLevel[12] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
				if(c.wearId == 9796) {
		if(c.playerLevel[13] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
				if(c.wearId == 9793) {
		if(c.playerLevel[14] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
				if(c.wearId == 9775) {
		if(c.playerLevel[15] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9772) {
		if(c.playerLevel[16] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId == 9778) {
		if(c.playerLevel[17] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
				if(c.wearId == 9787) {
		if(c.playerLevel[18] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
				if(c.wearId == 9811) {
		if(c.playerLevel[19] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
				if(c.wearId == 9766) {
		if(c.playerLevel[20] < 99) {
		c.sendMessage("You cannot wear this , you must have 99 in the skill");
		return;
		}
		}
		if(c.wearId >= 15039 && c.wearId <= 15044) {
		if(c.playerRights <= 1) {
		c.sendMessage("You cannot wear this");
		return;
		} else
		if(c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 7) {
		c.earningPotential = 100;
		}
		}
		if (c.wearSlot == 0 || c.wearSlot == 4 || c.wearSlot == 7) {
			if (c.playerEquipment[c.wearSlot] == 20143 || c.playerEquipment[c.wearSlot] == 20139 || c.playerEquipment[c.wearSlot] == 20135)
				torvaChanged = true;
		}
		if(c.wearId == 5733) {
		if(c.playerRights <= 1) {
		c.sendMessage("You cannot wear that!");
		return;
		} else
		if(c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 7) {
		c.earningPotential = 100;
		c.playerLevel[3] = 99;
		c.getPA().refreshSkill(3);
		return;
		}
		}
		if(c.wearId == 4565) {
				c.basket = true;
				} else {
				c.basket = false;
				}
			//c.attackTimer = oldCombatTimer;
		c.getItems().wearItem(c.wearId, c.wearSlot, c.getItems().getItemName(c.wearId).toLowerCase());
		if (torvaChanged && c.playerLevel[3] > c.calculateMaxLifePoints()) {
			c.playerLevel[3] = c.calculateMaxLifePoints();
			c.getPA().refreshSkill(3);
		}

if(c.playerRights == 3)
{
c.sendMessage(" ID: "+c.wearId+" SLOT: "+c.wearSlot+" "+c.getItems().getItemName(c.wearId).toLowerCase()+"");
}
	}

}
