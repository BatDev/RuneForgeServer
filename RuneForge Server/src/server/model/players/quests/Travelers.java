package server.model.players.quests;

import server.model.players.Client;

/**
 * @author Artesia
 **/

public class Travelers {
	
	
	private Client c;
	public Travelers (Client c) {
		this.c = c;	
	}
	
public void travelersQuest() { //2790
		for(int i = 8144; i < 8245; i++) {
			c.getPA().sendFrame126("", i);
		}	
		if (c.QP2 == 0) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("To start this quest you should", 8146);
			c.getPA().sendFrame126("talk to the guide in Falador.", 8147);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 1) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("inside the Draynor Castle.", 8150);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 2) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("@str@The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("@str@his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("@str@inside the Draynor Castle.", 8150);
			c.getPA().sendFrame126("The Demon said to gather up", 8151);
			c.getPA().sendFrame126("some wheat and milk if I want", 8152);
			c.getPA().sendFrame126("the Falador Guides items back.", 8153);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 3) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("@str@The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("@str@his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("@str@inside the Draynor Castle.", 8150);
			c.getPA().sendFrame126("@str@The Demon said to gather up", 8151);
			c.getPA().sendFrame126("@str@some wheat and milk if I want", 8152);
			c.getPA().sendFrame126("@str@he Falador Guides items back.", 8153);
			c.getPA().sendFrame126("The demon exchanged his items for", 8154);
			c.getPA().sendFrame126("the Guides. I should take these", 8155);
			c.getPA().sendFrame126("items back to the guide now.", 8156);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 4) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("@str@The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("@str@his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("@str@inside the Draynor Castle.", 8150);
			c.getPA().sendFrame126("@str@The Demon said to gather up", 8151);
			c.getPA().sendFrame126("@str@some wheat and milk if I want", 8152);
			c.getPA().sendFrame126("@str@he Falador Guides items back.", 8153);
			c.getPA().sendFrame126("@str@The demon exchanged his items for", 8154);
			c.getPA().sendFrame126("@str@the Guides. I should take these", 8155);
			c.getPA().sendFrame126("@str@items back to the guide now.", 8156);
			c.getPA().sendFrame126("The Guide told me that the items I", 8157);
			c.getPA().sendFrame126("have gotten are broken and should", 8158);
			c.getPA().sendFrame126("force the Demon to buy him new ones", 8159);
			c.getPA().sendFrame126("if I want my reward.", 8160);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 5) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("@str@The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("@str@his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("@str@inside the Draynor Castle.", 8150);
			c.getPA().sendFrame126("@str@The Demon said to gather up", 8151);
			c.getPA().sendFrame126("@str@some wheat and milk if I want", 8152);
			c.getPA().sendFrame126("@str@he Falador Guides items back.", 8153);
			c.getPA().sendFrame126("@str@The demon exchanged his items for", 8154);
			c.getPA().sendFrame126("@str@the Guides. I should take these", 8155);
			c.getPA().sendFrame126("@str@items back to the guide now.", 8156);
			c.getPA().sendFrame126("@str@The Guide told me that the items I", 8157);
			c.getPA().sendFrame126("@str@have gotten are broken and should", 8158);
			c.getPA().sendFrame126("@str@force the Demon to buy him new ones", 8159);
			c.getPA().sendFrame126("@str@if I want my reward.", 8160);
			c.getPA().sendFrame126("The demon said that I have to fight him.", 8161);
			c.getPA().sendFrame126("I should report back to the Guide and", 8162);
			c.getPA().sendFrame126("tell him what I need to do for this", 8163);
			c.getPA().sendFrame126("and then report back to the Demon", 8164);
			c.getPA().sendFrame126("for my fight against him.", 8164);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 6) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("@str@The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("@str@his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("@str@inside the Draynor Castle.", 8150);
			c.getPA().sendFrame126("@str@The Demon said to gather up", 8151);
			c.getPA().sendFrame126("@str@some wheat and milk if I want", 8152);
			c.getPA().sendFrame126("@str@he Falador Guides items back.", 8153);
			c.getPA().sendFrame126("@str@The demon exchanged his items for", 8154);
			c.getPA().sendFrame126("@str@the Guides. I should take these", 8155);
			c.getPA().sendFrame126("@str@items back to the guide now.", 8156);
			c.getPA().sendFrame126("@str@The Guide told me that the items I", 8157);
			c.getPA().sendFrame126("@str@have gotten are broken and should", 8158);
			c.getPA().sendFrame126("@str@force the Demon to buy him new ones", 8159);
			c.getPA().sendFrame126("@str@if I want my reward.", 8160);
			c.getPA().sendFrame126("@str@The demon said that I have to fight him.", 8161);
			c.getPA().sendFrame126("@str@I should report back to the Guide and", 8162);
			c.getPA().sendFrame126("@str@tell him what I need to do for this", 8163);
			c.getPA().sendFrame126("@str@and then report back to the Demon", 8164);
			c.getPA().sendFrame126("@str@for my fight against him.", 8165);
			c.getPA().sendFrame126("You have defeated the Evil Demon.", 3166);
			c.getPA().sendFrame126("I should talk to the Demon to retrieve", 8167);
			c.getPA().sendFrame126("the materials for the Guide.", 8168);
			c.getPA().showInterface(8134);
			} else
		if (c.QP2 == 7) {
			c.getPA().sendFrame126("Travelers Guide", 8144);
			c.getPA().sendFrame126("@str@To start this quest you should", 8146);
			c.getPA().sendFrame126("@str@talk to the Guide in Falador.", 8147);
			c.getPA().sendFrame126("@str@The Guide told you to retrieve", 8148);
			c.getPA().sendFrame126("@str@his items back from the Evil Demon", 8149);
			c.getPA().sendFrame126("@str@inside the Draynor Castle.", 8150);
			c.getPA().sendFrame126("@str@The Demon said to gather up", 8151);
			c.getPA().sendFrame126("@str@some wheat and milk if I want", 8152);
			c.getPA().sendFrame126("@str@he Falador Guides items back.", 8153);
			c.getPA().sendFrame126("@str@The demon exchanged his items for", 8154);
			c.getPA().sendFrame126("@str@the Guides. I should take these", 8155);
			c.getPA().sendFrame126("@str@items back to the guide now.", 8156);
			c.getPA().sendFrame126("@str@The Guide told me that the items I", 8157);
			c.getPA().sendFrame126("@str@have gotten are broken and should", 8158);
			c.getPA().sendFrame126("@str@force the Demon to buy him new ones", 8159);
			c.getPA().sendFrame126("@str@if I want my reward.", 8160);
			c.getPA().sendFrame126("@str@The demon said that I have to fight him.", 8161);
			c.getPA().sendFrame126("@str@I should report back to the Guide and", 8162);
			c.getPA().sendFrame126("@str@tell him what I need to do for this", 8163);
			c.getPA().sendFrame126("@str@and then report back to the Demon", 8164);
			c.getPA().sendFrame126("@str@for my fight against him.", 8165);
			c.getPA().sendFrame126("@str@You have defeated the Evil Demon.", 3166);
			c.getPA().sendFrame126("@str@I should talk to the Demon to retrieve", 8167);
			c.getPA().sendFrame126("@str@the materials for the Guide.", 8168);
			c.getPA().sendFrame126("The guide appreciates the work I have", 8169);
			c.getPA().sendFrame126("done for him and has awarded me after I", 8170);
			c.getPA().sendFrame126("click on the quest name again.", 8171);
			c.getPA().showInterface(8134);
				}
			}
		public void travelersComplete() {
			if(c.QP2 == 8) {
			c.getItems().addItem(995, 25000000);
			c.rxPoints += 25;
			c.getPA().sendFrame126("@red@ Exit", 4912);
			c.getPA().sendFrame126("You have completed Travelers Guide!", 4913);
			c.getPA().sendFrame126("You have recieved 25 Million Gp", 4914);
			c.getPA().sendFrame126("You can now wear Dragon Platebody!", 4915);
			c.getPA().sendFrame126("You have recieved 25 RuneForge Points.", 4916);
			c.getPA().sendFrame126("Congratulations!", 4918);
			c.getPA().sendFrame126("   Congratulations!", 4917);
			c.getPA().showInterface(4909);
				}
			}
		public void travelersCompleted() {
			if (c.QP2 == 9) {
			c.getPA().sendFrame126("@red@ Exit", 4912); // 1
			c.getPA().sendFrame126("You have already completed this quest.", 4913); // 2
			c.getPA().sendFrame126(".", 4914);
			c.getPA().sendFrame126(".", 4915);
			c.getPA().sendFrame126(".", 4916);
			c.getPA().sendFrame126("   Congratulations!", 4917);
			c.getPA().showInterface(4909);
				}
			}
}