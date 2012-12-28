package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
import server.model.players.skills.*;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 * Proper Streams
 */

		public class ItemClick3 implements PacketType {

		public void summon(Client c, int npcid, int index) {
			if(c.lastsummon < 1) {
				c.Summoning().SummonNewNPC(npcid, index);
			} else {
				c.sendMessage("You already have a NPC summoned");
				c.sendMessage("To dismiss it you need to click on the summoning Stat icon");
			}
		}
		
		public boolean handlePouches(int itemId) {
			for(int i = 0; i < Summoning.SUMMONING_NPC_DATA.length; i++) {
				Object[] data = Summoning.SUMMONING_NPC_DATA[i];
				if(c.getItems().playerHasItem(itemId) && (Integer) data[0] == itemId) {
					c.sendMessage("You have summoned " + (String) data[3] + ".");
					summon(c, (!c.inWild()) ? (Integer) data[1] : (Integer) data[2], i);
					return true;
				}
			}
			return false;
		}
		
		private Client c;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		
		c.s = itemId;   
		this.c = c;
		if(handlePouches(itemId))
			return;
			
		switch (itemId) {

		case 1712:
			c.getPA().handleGlory(itemId);
		break;
		case 5733:
		if(c.playerRights <= 1) {
		c.sendMessage("This item is not for you.");
		} else
		if(c.playerRights >= 2) {
		c.sendMessage("This item is under construction.");
		}
		break;
		
			
		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName+ " - Item3rdOption: "+itemId+" : "+itemId11+" : "+itemId1);
			break;
		}

	}

}
