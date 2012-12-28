package server.model.minigames;

import server.model.players.Client;
import server.Server;
import server.util.Misc;

/**
 * @author I'm A Jerk
 */
public class Dicing {
	
	private Client c;
	public Dicing(Client c) {
		this.c = c;
	}
	
	public void chooseDiceBag(int itemId) {
		switch(itemId) {
			case 15084:
			if (c.getItems().playerHasItem(15084, 1)) {
				c.getItems().deleteItem(15084, c.getItems().getItemSlot(15084), 1);
				//c.getDH().sendDialogues(150, 4289);
				c.getDH().sendOption5("Dice (6 sides)", "Dice (2, 6 sides)", "Dice (4 sides)", "Dice (8 sides)", "More");
			}
			break;
		}
	}
	
	public void handleDialogues(int dialogue) {
		switch(dialogue) {
			case 9194:
			if (c.getItems().playerHasItem(15084, 1)) {
				c.getItems().deleteItem(15084, c.getItems().getItemSlot(15084), 1);
				//c.getDH().sendDialogues(150, 4289);
				c.getDH().sendOption5("Dice (6 sides)", "Dice (2, 6 sides)", "Dice (4 sides)", "Dice (8 sides)", "More");
			}
			break;
		}
	}
	
	public void handleDiceBagInCc(int itemId) {
		switch(itemId) {
			case 15098:
			if (c.clanId >= 0) {
				Server.clanChat.playerMessageToClan(c.playerId, "I have rolled a "+ Misc.random(100) +" on the percentile dice", c.clanId);
				c.startAnimation(11900);
				c.gfx0(2075);
			} else {
				if (c.clanId != -1)
				c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			break;
		}
	}
	
	public void handleDiceBagInPublic(int itemId) {
		switch(itemId) {
			case 15098:
			if (c.clanId >= 0) {
				Server.clanChat.playerMessageToClan(c.playerId, "I have rolled a "+ Misc.random(100) +" on the percentile dice", c.clanId);
				c.startAnimation(11900);
				c.gfx0(2075);
			} else {
				if (c.clanId != -1)
				c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			break;
		}
	}
	
}