package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
import server.model.players.skills.RuneCraft;

/**
 * Item Click 2 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 * Proper Streams
 */

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		
		if (!c.getItems().playerHasItem(itemId,1))
			return;

		switch (itemId) {
			case 5733:
				if(c.playerRights <= 1) {
				c.sendMessage("This item is not for you.");
				} else
				if(c.playerRights >= 2) {
				c.getDH().sendOption5("100 Ep", "1000 Pts", "Lvl 1 Combat", "Lvl 126 Combat", "Next Page");
				c.dialogueAction = 90;
				}
				break;
			case 1438:// Air Talisman
				RuneCraft.locate(c, 2985, 3292);
				break;
			case 1440:// Earth Talisman
				RuneCraft.locate(c, 3306, 3474);
				break;
			case 1442:// Fire Talisman
				RuneCraft.locate(c, 3313, 3255);
				break;
			case 1444:// Water Talisman
				RuneCraft.locate(c, 3185, 3165);
				break;
			case 1446:// Body Talisman
				RuneCraft.locate(c, 3053, 3445);
				break;
			case 1448:// Mind Talisman
				RuneCraft.locate(c, 2982, 3514);
				break;
			case 11095:
				c.getPA().handleForinthryBrace(itemId);
			break;
			case 11097:
				c.getPA().handleForinthryBrace(itemId);
			break;
			case 11099:
				c.getPA().handleForinthryBrace(itemId);
			break;
			case 11101:
				c.getPA().handleForinthryBrace(itemId);
			break;
			case 11103:
				c.getPA().handleForinthryBrace(itemId);
			break;
			
			case 15098:
				c.getDicing().handleDiceBagInCc(itemId);
			break;
			
			case 11694:

				c.sendMessage("Dismantling has been disabled due to duping");
			break;
			case 11696:
				c.sendMessage("Dismantling has been disabled due to duping");
			break;
			case 11698:
				c.sendMessage("Dismantling has been disabled due to duping");
			break;
			case 11700:
				c.sendMessage("Dismantling has been disabled due to duping");
			break;
			
		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName+ " - Item3rdOption: "+itemId);
			break;
		}

	}

}
