package server.model.players.quests;

import server.model.players.Client;

/**
 * @author Artesia
 **/

public class Forgotten {
	
	
	private Client c;
	public Forgotten (Client c) {
		this.c = c;	
	}
	
	public void ForgottenQuest() {
		for(int i = 8144; i < 8245; i++) {
			c.getPA().sendFrame126("", i);
		}
		if (c.QP3 == 0) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("This quest is not yet working.", 8148);
			c.getPA().showInterface(8134);
		} else
		if (c.QP3 == 2) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("@str@To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("@str@In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("Elizabeth told me that she", 8148);
			c.getPA().sendFrame126("took her brother to Camelot", 8149);
			c.getPA().sendFrame126("yesterday and she couldn't find", 8150);
			c.getPA().sendFrame126("him. She also said he likes bees", 8151); // 2761 3435
			c.getPA().showInterface(8134);
		} else
		if (c.QP3 == 3) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("@str@To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("@str@In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("@str@Elizabeth told me that she", 8148);
			c.getPA().sendFrame126("@str@took her brother to Camelot", 8149);
			c.getPA().sendFrame126("@str@yesterday and she couldn't find", 8150);
			c.getPA().sendFrame126("@str@him. She also said he likes bees", 8151); // 2761 3435
			c.getPA().sendFrame126("Billy said he wont leave until", 8152);
			c.getPA().sendFrame126("I get a bee from the hive in a jar", 8153); // 12156  13573
			c.getPA().sendFrame126("so that he can take it home.", 8154);
			c.getPA().showInterface(8134);
		} else
		if (c.QP3 == 4) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("@str@To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("@str@In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("@str@Elizabeth told me that she", 8148);
			c.getPA().sendFrame126("@str@took her brother to Camelot", 8149);
			c.getPA().sendFrame126("@str@yesterday and she couldn't find", 8150);
			c.getPA().sendFrame126("@str@him. She also said he likes bees", 8151); // 2761 3435
			c.getPA().sendFrame126("@str@Billy said he wont leave until", 8152);
			c.getPA().sendFrame126("@str@I get a bee from the hive in a jar", 8153); // 12156  13573
			c.getPA().sendFrame126("@str@so that he can take it home.", 8154);
			c.getPA().sendFrame126("You must run back and tell Elizabeth", 8155);
			c.getPA().sendFrame126("that you found her brother.", 8156);
			c.getPA().showInterface(8134);
		} else
		if (c.QP3 == 5) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("@str@To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("@str@In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("@str@Elizabeth told me that she", 8148);
			c.getPA().sendFrame126("@str@took her brother to Camelot", 8149);
			c.getPA().sendFrame126("@str@yesterday and she couldn't find", 8150);
			c.getPA().sendFrame126("@str@him. She also said he likes bees", 8151); // 2761 3435
			c.getPA().sendFrame126("@str@Billy said he wont leave until", 8152);
			c.getPA().sendFrame126("@str@I get a bee from the hive in a jar", 8153); // 12156  13573
			c.getPA().sendFrame126("@str@so that he can take it home.", 8154);
			c.getPA().sendFrame126("@str@You must run back and tell Elizabeth", 8155);
			c.getPA().sendFrame126("@str@that you found her brother.", 8156);
			c.getPA().sendFrame126("She told me to lead her to her", 8157);
			c.getPA().sendFrame126("brother in camelot.", 8158);
			c.getPA().showInterface(8134);
		} else
		if (c.QP3 == 6) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("@str@To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("@str@In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("@str@Elizabeth told me that she", 8148);
			c.getPA().sendFrame126("@str@took her brother to Camelot", 8149);
			c.getPA().sendFrame126("@str@yesterday and she couldn't find", 8150);
			c.getPA().sendFrame126("@str@him. She also said he likes bees", 8151); // 2761 3435
			c.getPA().sendFrame126("@str@Billy said he wont leave until", 8152);
			c.getPA().sendFrame126("@str@I get a bee from the hive in a jar", 8153); // 12156  13573
			c.getPA().sendFrame126("@str@so that he can take it home.", 8154);
			c.getPA().sendFrame126("@str@You must run back and tell Elizabeth", 8155);
			c.getPA().sendFrame126("@str@that you found her brother.", 8156);
			c.getPA().sendFrame126("@str@She told me to lead her to her", 8157);
			c.getPA().sendFrame126("@str@brother in camelot.", 8158);
			c.getPA().sendFrame126("Elizabeth was making me slow", 8159);
			c.getPA().sendFrame126("so I had to leave her behind and", 8160);
			c.getPA().sendFrame126("go grab her brother. Her brother said", 8161);
			c.getPA().sendFrame126("he will go with you once he gets the", 8162);
			c.getPA().sendFrame126("honey and or bee in a jar.", 8163);
			c.getPA().showInterface(8134);
		} else 
		if (c.QP3 == 7) {
			c.getPA().sendFrame126("Lost Not Forgotten", 8144);
			c.getPA().sendFrame126("@str@To start this quest talk to Elizabeth", 8146); // 3489 3469
			c.getPA().sendFrame126("@str@In the Pub at Canafis.", 8147);
			c.getPA().sendFrame126("@str@Elizabeth told me that she", 8148);
			c.getPA().sendFrame126("@str@took her brother to Camelot", 8149);
			c.getPA().sendFrame126("@str@yesterday and she couldn't find", 8150);
			c.getPA().sendFrame126("@str@him. She also said he likes bees", 8151); // 2761 3435
			c.getPA().sendFrame126("@str@Billy said he wont leave until", 8152);
			c.getPA().sendFrame126("@str@I get a bee from the hive in a jar", 8153); // 12156  13573
			c.getPA().sendFrame126("@str@so that he can take it home.", 8154);
			c.getPA().sendFrame126("@str@You must run back and tell Elizabeth", 8155);
			c.getPA().sendFrame126("@str@that you found her brother.", 8156);
			c.getPA().sendFrame126("@str@She told me to lead her to her", 8157);
			c.getPA().sendFrame126("@str@brother in camelot.", 8158);
			c.getPA().sendFrame126("@str@Elizabeth was making me slow", 8159);
			c.getPA().sendFrame126("@str@so I had to leave her behind and", 8160);
			c.getPA().sendFrame126("@str@go grab her brother. Her brother said", 8161);
			c.getPA().sendFrame126("@str@he will go with you once he gets the", 8162);
			c.getPA().sendFrame126("@str@honey and or bee in a jar.", 8163);
			c.getPA().sendFrame126("Now that you got the honey for him", 8164);
			c.getPA().sendFrame126("you need to go tell Elizabeth that", 8165);
			c.getPA().sendFrame126("he was just trying to get a bee to", 8166);
			c.getPA().sendFrame126("bring home and he will be home shortly.", 8167);
			c.getPA().showInterface(8134);
		}
	}
	
	public void forgottenComplete() {
		if (c.QP3 == 8) {
			c.getPA().sendFrame126("You have recieved 10 RuneForge Points.", 4911); //3
			c.getPA().sendFrame126("@red@ Exit", 4912); //1
			c.getPA().sendFrame126("@or2@Congratulations You Finished The Quest!", 4913); //2
			c.getPA().sendFrame126("You gained 5 RuneForge Points.", 4914);//4
			c.getPA().sendFrame126("You gained 15 PC Points.", 4915); //5
			c.getPA().sendFrame126("Congratulations!", 4916); //7
			c.getPA().sendFrame126("Gained 100K exp in Fishing.", 4918); //6 10
			c.getPA().showInterface(4909);
		}
	}
	public void forgottenCompleted() {
		if (c.QP3 == 9) {
			c.getPA().sendFrame126(" ", 4911); //3
			c.getPA().sendFrame126("@red@ Exit", 4912); //1
			c.getPA().sendFrame126("@or2@Congratulations You Finished The Quest!", 4913); //2
			c.getPA().sendFrame126(" ", 4914);//4
			c.getPA().sendFrame126(" ", 4915); //5
			c.getPA().sendFrame126("Congratulations!", 4916); //7
			c.getPA().sendFrame126(" ", 4918); //6 10
			c.getPA().showInterface(4909);
			}
		}
	}