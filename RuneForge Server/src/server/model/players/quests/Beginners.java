package server.model.players.quests;

import server.model.players.Client;

/**
 * @author Artesia
 **/

public class Beginners {
	
	
	private Client c;
	public Beginners (Client c) {
		this.c = c;	
	}
	
	public void firstQuest() {
		for(int i = 8144; i < 8245; i++) {
			c.getPA().sendFrame126("", i);
		}
		if (c.QP1 == 0) {
			c.getPA().sendFrame126("Beginners Mission", 8144);
			c.getPA().sendFrame126("Talk to the dark wizard around varrok.", 8145);
			c.getPA().sendFrame126("Talk to the dark wizard around varrok.", 8146);
			c.getPA().sendFrame126("@str@Message Inplace", 8147);
			c.getPA().showInterface(8134);
			} else
		if (c.QP1 == 1) {
			c.getPA().sendFrame126("@str@Talk to the dark wizard around varrok.", 8145);
			c.getPA().sendFrame126("@str@to the man near the dark wizard in varrok.", 8146);
			c.getPA().sendFrame126("@str@Message Inplace", 8147);
			c.getPA().sendFrame126("You have now started the quest.", 8148);
			c.getPA().sendFrame126("To get your next objective  you should", 8149);
			c.getPA().sendFrame126("find the King in the castle and talk to him.", 8150);
			c.getPA().sendFrame126("He will tell you where to go next.", 8151);
			c.getPA().showInterface(8134);
			} else
		if (c.QP1 == 2) {
			c.getPA().sendFrame126("@str@Talk to the dark wizard around varrok.", 8145);
			c.getPA().sendFrame126("@str@to the man near the dark wizard in varrok.", 8146);
			c.getPA().sendFrame126("@str@Message Inplace", 8147);
			c.getPA().sendFrame126("@str@You have now started the quest.", 8148);
			c.getPA().sendFrame126("@str@To get your next objective you should", 8149);
			c.getPA().sendFrame126("@str@find the King in the castle and talk to him.", 8150);
			c.getPA().sendFrame126("@str@He will tell you where to go next.", 8151);
			c.getPA().sendFrame126("The king said somthing about a", 8152);
			c.getPA().sendFrame126("insane Man near the desert, I should", 8153);
			c.getPA().sendFrame126("talk to the Fallen Man in lumbridge.", 8154);
			c.getPA().showInterface(8134);
			} else
		if (c.QP1 == 3) {
			c.getPA().sendFrame126("@str@Talk to the dark wizard around varrok.", 8145);
			c.getPA().sendFrame126("@str@to the man near the dark wizard in varrok.", 8146);
			c.getPA().sendFrame126("@str@Message Inplace", 8147);
			c.getPA().sendFrame126("@str@You have now started the quest.", 8148);
			c.getPA().sendFrame126("@str@To get your next objective you should", 8149);
			c.getPA().sendFrame126("@str@find the King in the castle and talk to him.", 8150);
			c.getPA().sendFrame126("@str@He will tell you where to go next.", 8151);
			c.getPA().sendFrame126("@str@The king said somthing about a", 8152);
			c.getPA().sendFrame126("@str@insane Man near the desert, I should", 8153);
			c.getPA().sendFrame126("@str@talk to the Fallen Man in lumbridge.", 8154);
			c.getPA().sendFrame126("The Fallen Man said I can find the Man", 8155);
			c.getPA().sendFrame126("near the Shanty entrance.", 8156);
			c.getPA().showInterface(8134);
			} else
		if (c.QP1 == 4) {
			c.getPA().sendFrame126("@str@Talk to the dark wizard around varrok.", 8145);
			c.getPA().sendFrame126("@str@to the man near the dark wizard in varrok.", 8146);
			c.getPA().sendFrame126("@str@Message Inplace", 8147);
			c.getPA().sendFrame126("@str@You have now started the quest.", 8148);
			c.getPA().sendFrame126("@str@To get your next objective you should", 8149);
			c.getPA().sendFrame126("@str@find the King in the castle and talk to him.", 8150);
			c.getPA().sendFrame126("@str@He will tell you where to go next.", 8151);
			c.getPA().sendFrame126("@str@The king said somthing about a", 8152);
			c.getPA().sendFrame126("@str@insane Man near the desert, I should", 8153);
			c.getPA().sendFrame126("@str@talk to the Fallen Man in lumbridge.", 8154);
			c.getPA().sendFrame126("@str@The Fallen Man said I can find the Man", 8155);
			c.getPA().sendFrame126("@str@near the Shanty entrance.", 8156);
			c.getPA().sendFrame126("The Man told me to gear up", 8157);
			c.getPA().sendFrame126("and talk to him again when im ready.", 8158); 
			c.getPA().showInterface(8134);
			} else
		if (c.QP1 == 5) {
			c.getPA().sendFrame126("@str@Talk to the dark wizard around varrok.", 8145);
			c.getPA().sendFrame126("@str@to the man near the dark wizard in varrok.", 8146);
			c.getPA().sendFrame126("@str@Message Inplace", 8147);
			c.getPA().sendFrame126("@str@You have now started the quest.", 8148);
			c.getPA().sendFrame126("@str@To get your next objective you should", 8149);
			c.getPA().sendFrame126("@str@find the King in the castle and talk to him.", 8150);
			c.getPA().sendFrame126("@str@He will tell you where to go next.", 8151);
			c.getPA().sendFrame126("@str@The king said somthing about a", 8152);
			c.getPA().sendFrame126("@str@insane Man near the desert, I should", 8153);
			c.getPA().sendFrame126("@str@talk to the Fallen Man in lumbridge.", 8154);
			c.getPA().sendFrame126("@str@The Fallen Man said I can find the Man", 8155);
			c.getPA().sendFrame126("@str@near the Shanty entrance.", 8156);
			c.getPA().sendFrame126("@str@The Man told me to gear up", 8157);
			c.getPA().sendFrame126("@str@and talk to him again when im ready.", 8158); 
			c.getPA().sendFrame126("Ater killing the beast talk to the", 8159); 
			c.getPA().sendFrame126("Man for your reward.", 8160); 
			c.getPA().showInterface(8134);
		}
	}
	public void resetInfo() {
	for(int i = 8144; i < 8245; i++) {
			c.getPA().sendFrame126(" ", i);
		}
	}
	public void questsCompletedd() {
		if (c.QP1 == 7) {
			c.getPA().sendFrame126("@red@ Exit", 4912);
			c.getPA().sendFrame126("Congratulations! You have completed Beginners Mission!", 4913);
			c.getPA().sendFrame126(" ", 4914);
			c.getPA().sendFrame126(" ", 4915);
			c.getPA().sendFrame126("Congratulations", 4916);
			c.getPA().sendFrame126("@red@ Exit", 4917);
			c.getPA().showInterface(4909);
		}
	}
	public void questsCompleted() {
		if (c.QP1 == 6) {
		if (c.getItems().freeSlots() > 0) {
			c.getItems().addItem(995,15000000);
			c.rxPoints += 5;
			c.getPA().sendFrame126("@red@ Exit", 4912);
			c.getPA().sendFrame126("Congratulations! You have completed Beginners Mission!", 4913);
			c.getPA().sendFrame126("You have gained 15 Million Gold Coins!", 4914);
			c.getPA().sendFrame126("You have gained 5 RuneForge Points!", 4915);
			c.getPA().sendFrame126("Congratulations", 4916);
			c.getPA().sendFrame126("@red@ Exit", 4917);
			c.getPA().showInterface(4909);
			}
		}
	}
}