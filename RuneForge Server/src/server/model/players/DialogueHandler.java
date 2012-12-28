package server.model.players;

public class DialogueHandler {

	private Client c;
	
	public DialogueHandler(Client client) {
		this.c = client;
	}
	
	/**
	 * Handles all talking
	 * @param dialogue The dialogue you want to use
	 * @param npcId The npc id that the chat will focus on during the chat
	 */
public void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch(dialogue) {
		case 106:
				sendOption5("One 6-sided die", "Two 6-sided dice", "One 4-sided die", "One 8-sided die", "More...");
				c.dialogueAction = 106;
				c.teleAction = 0;
				c.nextChat = 0;
				break;

			case 107:
				sendOption5("One 10-sided die", "One 12-sided die", "One 20-sided die", "Two 10-sided dice for 1-100", "Back...");
				c.dialogueAction = 107;
				c.teleAction = 0;
				c.nextChat = 0;
				break;
		
		/**
		*Home Changer
		**/
			case 2500:
            sendNpcChat4("Welcome to RuneForge!", "Click on your combat skill and change it,", "to your desired level!", "Please keep voting for the server!", c.talkingNpc, "RuneForge Guide");
			break;	
		case 70:
			sendNpcChat2("You have not set a custom home.", "Would you like to set one now?", c.talkingNpc, "Home Security");
			c.nextChat = 74;
		break;
		case 71:
			sendNpcChat2("Your current home is Varrok.", "Would you like to change it?", c.talkingNpc, "Home Security");
			c.nextChat = 75;
			c.home = 1;
		break;
		case 72:
			sendNpcChat2("Your current home is Edgeville.", "Would you like to change it?", c.talkingNpc, "Home Security");
			c.nextChat = 76;
			c.home = 2;
		break;
		case 73:
			sendNpcChat2("Your current home is Falador.", "Would you like to change it?", c.talkingNpc, "Home Security");
			c.nextChat = 77;
			c.home = 3;
		break;
		case 74:
			sendOption3("Varrok", "Edgeville", "Falador");
			c.nextChat = 0;
		break;
		case 75:
			sendOption3("Edgeville", "Falador", "Stay");
			c.nextChat = 0;
		break;
		case 76:
			sendOption3("Varrok", "Falador", "Stay");
			c.nextChat = 0;
		break;
		case 77:
			sendOption3("Varrok", "Edgeville", "Stay");
			c.nextChat = 0;
		break;
		/**
		*End of Home Changer
		**/
		//Start of Quest1
		case 350:
			sendNpcChat4("Hello "+c.playerName+" welcome to","the start of a Challenging Mission.","I need you to talk to the King","in Camelot for me. Will you?", c.talkingNpc, "Wizard");
			c.nextChat = 351;
		break;
		case 351:
			sendPlayerChat2("Yes I can do that for you.","I'll be back in a few!");
			c.nextChat = 0;
		break;
		case 352:
			sendNpcChat4("Oh dear! Thank you so much","for coming here!","I need you to go to the man","in lumbridge", c.talkingNpc, "King");
			c.nextChat = 353;
		break;
		case 353:
			sendPlayerChat2("Sure thing! Ill talk to the Man","right away!");
			c.nextChat = 0;
		break;
		case 354:
			sendNpcChat4("A goblin I see!","The insane creature of the dead!","Sha... Shant... Shanty...","Help me! Help me!", c.talkingNpc, "Fallen Man");
			c.nextChat = 355;
		break;
		case 355:
			sendPlayerChat2("I don't understand what you mean?","I guess I could go look?");
			c.nextChat = 0;
		break;
		case 356:
			sendNpcChat4("Hello young one. I have a favour","i need you to kill the insane in me","gear up and talk to me again when","nvm... your to weak just talk again...", c.talkingNpc, "Insane Man");
			c.nextChat = 357;
		break;
		case 357:
			sendPlayerChat2("I accept your challenge!","I will go gear up!");
			c.nextChat = 0;
		break;
		case 358:
			sendNpcChat2("Good Luck!"," ", c.talkingNpc, "Insane Man");
			c.nextChat = 0;
		break;
		case 359:
			sendNpcChat2("I lied your a noob!","Click the quest name for Reward.", c.talkingNpc, "Man");
			c.nextChat = 360;
		break;
		case 360:
			sendPlayerChat2("This is amazing!","Thank you!");
			c.nextChat = 0;
		break;
		//End Quest1
		//Quest 2
		case 370: //Section1
			sendPlayerChat2("Excuse me sir are you there?"," ");
			c.nextChat = 371;
		break;
		case 371:
			sendNpcChat2("Oh hello there " +c.playerName+"!","Would you be willing to do me a favor?",c.talkingNpc, "Guide");
			c.nextChat = 372;
		break;
		case 372:
			sendPlayerChat2("Well that depends... Im kinda busy today so...","Well what do you need done?");
			c.nextChat = 373;
		break;
		case 373:
			sendNpcChat2("Well it's not a bad favor its quite simple...","I need you to get my guiding gear from a demon.",c.talkingNpc, "Guide");
			c.nextChat = 374;
		break;
		case 374:
			sendPlayerChat2("I guess I can do that I'll go talk to him."," ");
			c.nextChat = 0;
			c.QP2 = 1;
		break;
		case 375: // Section2
			sendNpcChat2("Alright so I.... Didn't see you there...","What do you want you squirmy little human!",c.talkingNpc, "Demon");
			c.nextChat = 376;
		break;
		case 376:
			sendPlayerChat2("Woah man! Back off I just need somthing...","Can I get the Falador's Guides items back?");
			c.nextChat = 377;
			c.QP2 = 2;
		break;
		case 377:
			sendNpcChat2("Well thinking back to that man...","Yah if you get me some wheat and milk.",c.talkingNpc, "Demon");
			c.nextChat = 378;
		break;
		case 378:
			sendPlayerChat2("Sounds easy to me!","Ill go get that right now!");
			c.nextChat = 379;
		break;
		case 379:
			if(c.getItems().playerHasItem(1947)) {
			c.getItems().playerHasItem(1927);
			c.getItems().deleteItem(1947,1);
			c.getItems().deleteItem(1927,1);
			sendNpcChat2("Thank you!","Heres the Guides item",c.talkingNpc, "Demon");
			c.getItems().addItem(3103,1);
			c.QP2 = 3;
			c.nextChat = 380;
			} else
			sendNpcChat2("You do not have the items I need!","Go back and get them!",c.talkingNpc, "Demon");
			c.nextChat = 0;
		break;
		case 380:
			sendPlayerChat2("Thank you!"," ");
			c.nextChat = 0;
		break;
		case 381:
			sendPlayerChat2("Hey Falador Guide!","I got your item from the Demon!");
			c.nextChat = 382;
		break;
		case 382:
			sendNpcChat2("Thank you " +c.playerName+" but this is not mine.","Read this...",c.talkingNpc, "Guide");
			c.nextChat = 383;
		break;
		case 383:
			sendPlayerChat2("Oh... an IOU...","Im really sorry about this I'll go talk to him!");
			c.nextChat = 0;
			c.QP2 = 4;
		break;
		case 384:
			sendPlayerChat2("Hey big guy with the army crap on!","Hey! Im talking to you!");
			c.nextChat = 385;
		break;
		case 385:
			sendNpcChat2("What in the hell do you want?","I already gave you his items now get out!",c.talkingNpc, "Demon");
			c.nextChat = 386;
		break;
		case 386:
			sendPlayerChat2("No the item you gave me was an IOU.","Now give me his damn items!");
			c.nextChat = 387;
		break;
		case 387:
			sendNpcChat2("If you want his real items than you have to fight me!","Forget that my minion im lazy!",c.talkingNpc, "Demon");
			c.nextChat = 388;
		break;
		case 388:
			sendPlayerChat2("Alright you grumpy old hag I'll brb!","Im gonna go talk to the Guide first.");
			c.QP2 = 5;
			c.nextChat = 0;
		break;
		case 389:
			sendPlayerChat2("Sir!, I have a problem!","The Demon wants me to fight his minion.");
			c.nextChat = 390;
		break;
		case 390:
			sendNpcChat2("Well then go fight him!","I really need my items back please go!",c.talkingNpc, "Guide");
			c.nextChat = 391;
		break;
		case 391:
			sendPlayerChat2("Fine grumpster I will!","My reward for this better be good...");
			c.nextChat = 0;
		break;
		case 392:
			sendNpcChat2("Goodluck you stupid rat!","Nobody can defeat Evil shitzu!",c.talkingNpc, "Demon");
			c.nextChat = 0;
		break;
		case 393:
			sendPlayerChat2("I thought nobody could huh?","He didn't even attack me! HAHA!");
			c.nextChat = 394;
		break;
		case 394:
			sendNpcChat2("... Im gonna have a talk with him!"," Heres the Guides real items...", c.talkingNpc, "Demon");
			c.getItems().addItem(2574,1);
			c.getItems().addItem(2575,1);
			c.getItems().addItem(2576,1);
			c.nextChat = 0;
		break;
		case 395:
			sendPlayerChat2("Hey I got your items!"," ");
			c.nextChat = 396;
		break;
		case 396:
			if(c.getItems().playerHasItem(2574)) {
			c.getItems().playerHasItem(2575);
			c.getItems().playerHasItem(2576);
			c.getItems().deleteItem(2574,1);
			c.getItems().deleteItem(2575,1);
			c.getItems().deleteItem(2576,1);
			sendNpcChat2("Thank you!","Just click your quest now for the reward!",c.talkingNpc, "Guide");
			c.QP2 = 8;
			c.nextChat = 0;
			} else
			sendNpcChat2("You do not have the items I need!","Go back and get them!",c.talkingNpc, "Guide");
			c.nextChat = 0;
		break;
		//End Quest2
		case 200:
			sendNpcChat4("Hello there "+c.playerName+"!"," I have the ability to reset your combat stats for free!","But remember, this is irreversable!","What would you like me to do?", c.talkingNpc, "Xp Reseter");
			c.nextChat = 210;
		break;
		case 210:
			sendOption4("Reset Strength", "Reset Prayer", "Reset Attack", "Reset All Combat Stats");
			c.dialogueAction = 42;
		break;
		case 230:
			sendNpcChat2("Congratulations!", "Your Strength has been completely reset!",c.talkingNpc, "Xp Reseter");
			int skilld = 2;
					int leveld = 1;
					c.playerXP[skilld] = c.getPA().getXPForLevel(leveld)+2;
					c.playerLevel[skilld] = c.getPA().getLevelForXP(c.playerXP[skilld]);
					c.getPA().refreshSkill(skilld);
			
			c.nextChat = 0;
		break;
		case 240:
			sendNpcChat2("Congratulations!", "Your attack has been completely reset!",c.talkingNpc, "Xp Reseter");
			int skill = 0;
					int levela = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(levela)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
			c.nextChat = 0;
		break;
		case 250:
			sendNpcChat2("Congratulations!", "Your combat stats have been completely reset!",c.talkingNpc, "Xp Reseter");
			int skill1 = 0;
					int level = 1;
					c.playerXP[skill1] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill1] = c.getPA().getLevelForXP(c.playerXP[skill1]);
					c.getPA().refreshSkill(skill1);
					int skill2 = 1;
				//	int level = 1;
					c.playerXP[skill2] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill2] = c.getPA().getLevelForXP(c.playerXP[skill2]);
					c.getPA().refreshSkill(skill2);
					int skill3 = 2;
				//	int level = 1;
					c.playerXP[skill3] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill3] = c.getPA().getLevelForXP(c.playerXP[skill3]);
					c.getPA().refreshSkill(skill3);
					int skill4 = 3;
					level = 10;
					c.playerXP[skill4] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill4] = c.getPA().getLevelForXP(c.playerXP[skill4]);
					c.getPA().refreshSkill(skill4);
					int skill5 = 4;
					level = 1;
					c.playerXP[skill5] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill5] = c.getPA().getLevelForXP(c.playerXP[skill5]);
					c.getPA().refreshSkill(skill5);
					int skill6 = 5;
				//	int level = 1;
					c.playerXP[skill6] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill6] = c.getPA().getLevelForXP(c.playerXP[skill6]);
					c.getPA().refreshSkill(skill6);
					int skill7 = 6;
				//	int level = 1;
					c.playerXP[skill7] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill7] = c.getPA().getLevelForXP(c.playerXP[skill7]);
					c.getPA().refreshSkill(skill7);
			c.nextChat = 0;
		break;
		case 260:
			sendNpcChat2("Congratulations!","Your prayer have been completely reset!",c.talkingNpc, "Xp Reseter");
			int skillp = 5;
					int levelp = 1;
					c.playerXP[skillp] = c.getPA().getXPForLevel(levelp)+5;
					c.playerLevel[skillp] = c.getPA().getLevelForXP(c.playerXP[skillp]);
					c.getPA().refreshSkill(skillp);
			c.nextChat = 0;
		break;
		case 30:
			sendNpcChat4("Congratulations!","You have killed 20 monkeys hope you learned something..", "would you like to escape?","Do not break anymore rules!", c.talkingNpc, "Mosol Rei");
			c.dialogueAction = 26;
			c.nextChat = 31;
			break;
		case 31:
			sendOption2("Yes get me out of this fucking hell hole!",  "Hell no! I love it here, I'm nuts for these monkeys!");
			c.dialogueAction = 27;
			c.nextChat = 0;	
			break;
		case 50:
			sendOption2("Ardougne - 1vs1",  "Fally - Multi");
			c.dialogueAction = 50;
			break;
		case 32:
			sendNpcChat4("You cannot Escape yet!","You've killed "+c.monkeyk0ed+" out of 20 monkeys!","Come back when you have killed 20","Kthxbai", c.talkingNpc, "Mosol Rei");
			c.dialogueAction = 30;
			c.nextChat = 0;
			break;
		case 100:
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
			c.sendMessage("To visit strykeworms please go in your spellbook and find Strykeworms teleport (Ancients)!");
		break;
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 201:
			sendOption4("Information", "Black Jack","Five", "Maybe later...");
			c.dialogueAction = 100;
			break;

		case 25:
			sendOption4("","Black Jack", "Five","");
			c.dialogueAction = 101;
			break;

		case 21:
			sendNpcChat4("The way we play this game is simple. The way you win is", 
					"You need to get a higher number than me and you win the", 
					"500,000 coins. You need to bet 250,000 coins per round.",
					"If you get over 22 you bust and you lose.", 
					c.talkingNpc, "~ Black Jack ~");
					c.nextChat = 22;
					break;

		case 22:
			sendNpcChat4("", 
					"If i get 22+ I bust and I lose. If you get 21 then you have black", 
					"jack and you win double of what you bet.",
					"", 
					c.talkingNpc, "~ Black Jack ~");
					c.nextChat = 0;
					break;

		case 23:
			sendNpcChat4("This is my own game which I made. It's pretty simple", 
					"and resembles poker but it's a lot different. The aim of this", 
					"game is to get the same number like the random number",
					"You got 2 numbers if both hit the same you win.", 
					c.talkingNpc, "~ Five ~");
					c.nextChat = 24;
					break;
		case 24:
			sendNpcChat4("", 
					"To play this game you need to bet 1,000,000 coins. You", 
					"can win a lot of good items but also lose a lot of cash.",
					"", 
					c.talkingNpc, "~ Five ~");
					c.nextChat = 0;
					break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 45:
			sendNpcChat2("Since you haven't shown me a defender to", "prove your prowess as a warrior.", 4289, "Kamfreena");
			c.nextChat = 46;
			break;
		case 46:
			sendNpcChat3("I'll release some Cyclopes which might drop bronze", "defenders for you to start off with, unless you show me", "another. Have fun in there.", 4289, "Kamfreena");
			c.nextChat = -1;
			break;
		case 47:
			sendNpcChat2("The cyclops will now drop:", "" + c.getWarriorsGuild().getCyclopsDrop126(c) + " defenders.", 4289, "Kamfreena");
			c.nextChat = -1;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!",  "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat4("Hello!", "My name is Vannaka and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Vannaka");
			c.nextChat = 4;
		break;
		case 5:
			sendNpcChat4("Hello adventurer...", "My name is Kolodion, the master of this mage bank.", "Would you like to play a minigame in order ", 
						"to earn points towards recieving magic related prizes?", c.talkingNpc, "Kolodion");
			c.nextChat = 6;
		break;
		case 6:
			sendNpcChat4("The way the game works is as follows...", "You will be teleported to the wilderness,", 
			"You must kill mages to recieve points,","redeem points with the chamber guardian.", c.talkingNpc, "Kolodion");
			c.nextChat = 15;
		break;
		case 11:
			sendNpcChat4("Hello!", "My name is Vannaka and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Vannaka");
			c.nextChat = 12;
		break;
		case 12:
			sendOption2("Yes I would like a slayer task.", "No I would not like a slayer task.");
			c.dialogueAction = 5;
		break;
		case 13:
			sendNpcChat4("Hello!", "My name is Vannaka and I am a master of the slayer skill.", "I see I have already assigned you a task to complete.", 
			"Would you like me to give you an easier task?", c.talkingNpc, "Vannaka");
			c.nextChat = 14;
		break;
		case 14:
			sendOption2("Yes I would like an easier task.", "No I would like to keep my task.");
			c.dialogueAction = 6;
		break;
		case 15:
			sendOption2("Yes I would like to play", "No, sounds too dangerous for me.");
			c.dialogueAction = 7;
		break;
		case 16:
			sendOption2("I would like to reset my barrows brothers.", "I would like to fix all my barrows");
			c.dialogueAction = 8;
		break;
		case 17:
			sendOption5("Air", "Mind", "Water", "Earth", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
		break;
		case 18:
			sendOption5("Fire", "Body", "Cosmic", "Astral", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
		break;
		case 19:
			sendOption5("Nature", "Law", "Death", "Blood", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
		break;
		case 150:
			sendOption5("Dice (6 sides)", "Dice (2, 6 sides)", "Dice (4 sides)", "Dice (8 sides)", "More");
			c.dialogueId = 150;
			c.dialogueAction = 25;
			c.nextChat = 151;
		break;
		}
	}
	
	/*
	 * Information Box
	 */
	
	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}
	
	/*
	 * Options
	 */
	
	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	private void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}
	
	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}
	
	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}
	
	public void Kill() {
		if (c.cannotSummon()) {
			c.sendMessage("You cannot dismiss a summon in this area.");
			return;
		}
		c.nextChat = 999;
		c.getPA().sendFrame126("Dismiss your monster", 2460);
		c.getPA().sendFrame126("Yes", 2461);
		c.getPA().sendFrame126("No", 2462);
		c.getPA().sendFrame164(2459);
	}
	
	/*
	 * Statements
	 */
	
	public void sendStatement(String s) { // 1 line click here to continue chat box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}
	
	/*
	 * Npc Chatting
	 */
	
	private void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Player Chating Back
	 */
	
	private void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}

	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}
	
	public void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}
	
	public void talk(int face, String line1, String line2, String line3, String line4, int npcID) {
		c.getPA().sendFrame200(4901, face);
		c.getPA().sendFrame126(c.getPA().GetNpcName(npcID).replaceAll("_", " "), 4902);
		c.getPA().sendFrame126(""+line1, 4903);
		c.getPA().sendFrame126(""+line2, 4904);
		c.getPA().sendFrame126(""+line3, 4905);
		c.getPA().sendFrame126(""+line4, 4906);
		c.getPA().sendFrame126("Click here to continue", 4907);
		c.getPA().sendFrame75(npcID, 4901);
		c.getPA().sendFrame164(4900);
	}
}
