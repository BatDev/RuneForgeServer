package server.model.players.packets;

/**
 * @author Ryan / Lmctruck30
 */

import server.model.items.UseItem;
import server.model.players.Client;
import server.model.players.PacketType;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/*
		 * a = ?
		 * b = ?
		 */
		
		c.getInStream().readUnsignedWord();
		int objectId = c.getInStream().readSignedWordBigEndian();
		int objectY = c.getInStream().readSignedWordBigEndianA();
		c.getInStream().readUnsignedWord();
		int objectX = c.getInStream().readSignedWordBigEndianA();
		int itemId = c.getInStream().readUnsignedWord();
		UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);
		if (itemId == 1925 && objectId == 8689) {
			c.getItems().deleteItem(1925,1);
			c.getItems().addItem(1927,1);
			}
		if (itemId == 1438 && objectId == 2452) { // Air Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2841, 4829, 0);
			}

			if (itemId == 1440 && objectId == 2455) { // Earth Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2655, 4830, 0);
			} 

			if (itemId == 1442 && objectId == 3312) { // Fire Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2574, 4848, 0);
			} 
			if (itemId == 1444 && objectId == 3184) { // Water Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2727, 4833, 0);
			}
			if (itemId == 1446 && objectId == 3052) { // Body Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2522, 4825, 0);
			}
			if (itemId == 1448 && objectId == 2981) { // Mind Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2792, 4827, 0);
		}
		
	}

}
