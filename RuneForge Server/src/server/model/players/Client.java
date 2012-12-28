package server.model.players;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

import org.apache.mina.common.IoSession;
import server.Config;
import server.model.players.quests.*;
import server.Server;
import server.Database;
import java.sql.Statement;
import java.sql.ResultSet;
import server.model.items.ItemAssistant;
import server.model.shops.ShopAssistant;
import server.net.*;
import server.util.*;
import server.model.players.skills.*;
import server.event.*;
import server.model.minigames.*;
import server.model.npcs.NPCHandler;
import server.Connection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import server.model.players.PlayerAssistant;


public class Client extends Player {



	public StaffControls staffControls = new StaffControls(this);
	private QuickPrayer quickPrayer = new QuickPrayer(this);
	private QuickCurses quickCurses = new QuickCurses(this);
	
	private DonatorControlPanel donatorControlPanel = new DonatorControlPanel(this);
	
	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	public static PlayerSave save;
	public boolean summon;
	public Beginners quest1Handler = new Beginners(this);
	public Travelers quest2Handler = new Travelers(this);
	public Forgotten quest3Handler = new Forgotten(this);
	public static Client cliento2;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Potions potion = new Potions(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private WarriorsGuild warriorsGuild = new WarriorsGuild();
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private TradeLog tradeLog = new TradeLog(this);
	private Dicing dicing = new Dicing(this);
		private Pins pins = new Pins(this);
	private SummonAddOn summonAddOn = new SummonAddOn(this);
	private Construction construction = new Construction(this);
	public int s;
	public boolean needsToSpawn;
		public int getLocalX() {
		return getX() - 8 * getMapRegionX();
	}
	
	public int getLocalY() {
		return getY() - 8 * getMapRegionY();
	}
	
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	//private Runecrafting runecrafting = new Runecrafting(this);
	public Summoning Summoning = new Summoning(this);
	private Woodcutting woodcutting = new Woodcutting(this);
	private Mining mine = new Mining(this);
	public Agility ag = new Agility(this);
	private Cooking cooking = new Cooking(this);
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private Prayer prayer = new Prayer(this);
	private Curse curse = new Curse(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Firemaking firemaking = new Firemaking(this);
	private Herblore herblore = new Herblore(this);
	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;	
	public int returnCode = 2; 
	public int clawDamage;
	public int clawIndex;
	public int startDel = 0;
	public int clawType = 0;
	public int protectItem = 0;
	public int totalstored;
	public int npcslot;
	public int given;
	public int summoningnpcid;
	private Future<?> currentTask;
	public boolean officialClient = true;
	public boolean basket = false;
	public boolean WildernessWarning = false;
	public boolean attackSkill = false;
	public boolean strengthSkill = false;
	public boolean defenceSkill = false;
	public boolean mageSkill = false;
	public boolean rangeSkill = false;
	public boolean prayerSkill = false;
	public boolean healthSkill = false;
	public boolean summonSkill = false;
	
	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		//synchronized(this) {
			outStream = new Stream(new byte[Config.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		//}
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void frame1() // cancels all player and npc emotes within area!
    {
        for (Player p : PlayerHandler.players) {
            if (p != null) {
                Client c = (Client) p;
                c.outStream.createFrame(1);
            }
        }
        updateRequired = true;
        appearanceUpdateRequired = true;
  	}

	public int attackingplayer;
	public int lastsummon;

	public void loginMessage() {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
								switch (playerRights) {
				case 1: // moderator
					c2.sendMessage("<shad=3781373>[Moderator]</shad>" + Misc.optimizeText(playerName) + " has just logged in, feel free to ask help.");
					break;
				case 2: // adminstrator
					c2.sendMessage("<shad=16112652>[Administrator]</shad>"+ Misc.optimizeText(playerName) +" has just logged in, feel free to ask help.");
					break;
				}
			}
		}
       }
	public void wildyWarning() {
	getPA().sendFrame126("WARNING!", 6940);
        //Edit the below if you want to change the text, and delete the slashes.
	getPA().sendFrame126("Proceed with caution. If you go much further north you will enter the\nwilderness. This is a very dangerous area where other players can attack you!", 6939);
	getPA().sendFrame126("The further north you go the more dangerous it becomes, but there is more\ntreasure to be found.", 6941);
	getPA().sendFrame126("In the wilderness an indicator at the bottom-right of the screen\nwill show the current level of danger.", 6942);
	getPA().showInterface(1908);
	}
	
	public void stafftab() {
	String staffNames[] = { "BatDev", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty" };
			
		//Staff Spot 1 Online/Offline
		for (int i=0, i2=28000; i < staffNames.length; i++, i2+=2) {
			getPA().sendFrame126(staffNames[i], i2);
			getPA().sendFrame126( ((PlayerHandler.isPlayerOn(staffNames[i]))? "@gre@Online" : "@red@Offline") , i2 + 1);
		}
		}
	
	public boolean picking = false;
		public boolean slayerHelmetEffect;
	public boolean usingBoB;
	public int[] bobItems = new int[30];
	public int[] bobItemsN = new int[30];

	public void jadSpawn() {
		// getPA().movePlayer(absX, absY, playerId * 4);
		getDH().sendDialogues(41, 2618);
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				Server.fightCaves.spawnNextWave((Client) PlayerHandler.players[playerId]);
				c.stop();
			}
		}, 10000);
	}
	
	public void handCannonDestory() {
		cannonTimer = 0;
		int chance = playerLevel[playerFiremaking] * 5 + 25;
		if(specGfx)
			chance/=2;
		if(Misc.random(chance) == 1)
			EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
			if(cannonTimer <= 0) {
				gfx0(2140);
    			playerEquipment[playerWeapon] = -1;
    			sendMessage("Your hand cannon explodes LMFAO!");
    			int damage = Misc.random(15) + 1;
				setHitDiff(damage);
				setHitUpdateRequired(true);
    			dealDamage(Misc.random(15) + 1);
    			updateRequired = true;
				getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
    			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
    			getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getPA().requestUpdates();getOutStream().createFrame(34);
				getOutStream().writeWord(6);
				getOutStream().writeWord(1688);
				getOutStream().writeByte(playerWeapon);
				getOutStream().writeWord(0);
				getOutStream().writeByte(0);
				updateRequired = true; 
				setAppearanceUpdateRequired(true);
				c.stop();
				} else {
					cannonTimer--;
				}
			}
		}, 500);
	}
	public boolean specGfx = false;
	public int cannonTimer = 0;
	public void handCannonSpec() {
		cannonTimer = 0;
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				cannonTimer--;
				if(cannonTimer == 0) {
					gfx0(2141);
					specGfx = true;
				}
				if(cannonTimer == 1) {
					if (playerIndex > 0)
						getCombat().fireProjectilePlayer();
					else if (npcIndex > 0)
						getCombat().fireProjectileNpc();	
					c.stop();
				}
			}
		}, 25);
	}
	public static int Flower[] = {2980,2981,2982,2983,2984,2985,2986,2980,2981,2982,2983,2984,2985,2986,2980,2981,2982,2983,2984,2985,2986,2987,2988,2980,2981,2982,2983,2984,2985,2986,2980,2981,2982,2983,2984,2985,2986,2980,2981,2982,2983,2984,2985,2986};


		public static int randomFlower()

		{

		return Flower[(int)(Math.random()*Flower.length)];

		}
		public int getCombatLevel() {
        int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));
			if (ran > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[4])) * 0.4875)
						+ ((getLevelForXP(playerXP[22])) * 0.125));
			} else if (mag > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[6])) * 0.4875)
						+ ((getLevelForXP(playerXP[22])) * 0.125));
			} else {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[0])) * 0.325) 
						+ ((getLevelForXP(playerXP[2])) * 0.325)
						+ ((getLevelForXP(playerXP[22])) * 0.125));
			}
		return combatLevel;
	}
/*	public int getCombatLevel() {
        int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));
			if (ran > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
			} else if (mag > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
			} else {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
			}
		return combatLevel;
	}*/
	
	public void HighAndLow(){
		if (combatLevel < 15){
			int Low = 3;
			int High = combatLevel + 12;
				getPA().sendFrame126("@gre@"+Low+"@yel@ - @red@"+High+"", 199);
		}
		if (combatLevel > 15 && combatLevel < 114){
			int Low = combatLevel - 12;
			int High = combatLevel + 12;
				getPA().sendFrame126("@gre@"+Low+"@yel@ - @red@"+High+"", 199);
		}
		if (combatLevel > 114){
			int Low = combatLevel - 12;
			int High = 126;
				getPA().sendFrame126("@gre@"+Low+"@yel@ - @red@"+High+"", 199);
		}
	}




	public void flushOutStream() {	
		if(disconnected || outStream.currentOffset == 0) return;
		//synchronized(this) {	
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset]; 
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		//}
	}
	   
	public void StartBestItemScan(){
		if(isSkulled && !prayerActive[10]){
			ItemKeptInfo(0);
			return;
		}
		FindItemKeptInfo();	
		ResetKeepItems();
		BestItem1();
	}
	public void BestItem1(){
		int BestValue = 0;
		int NextValue = 0;
		int ItemsContained = 0;
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		for(int ITEM = 0; ITEM < 28; ITEM++){
			if(playerItems[ITEM] > 0){
			ItemsContained += 1;
				NextValue = (int) Math.floor(getShops().getItemShopValue(playerItems[ITEM]-1));
				if(NextValue > BestValue){
					BestValue = NextValue;
					WillKeepItem1 = playerItems[ITEM]-1;
					WillKeepItem1Slot = ITEM;
					if(playerItemsN[ITEM] > 2 && !prayerActive[10]){
					WillKeepAmt1 = 3;
					} else if(playerItemsN[ITEM] > 3 && prayerActive[10]){
					WillKeepAmt1 = 4;
					} else {
					WillKeepAmt1 = playerItemsN[ITEM]; 
					}
				}
			}
		}
		for(int EQUIP = 0; EQUIP < 14; EQUIP++){
			if(playerEquipment[EQUIP] > 0){
			ItemsContained += 1;
			NextValue = (int) Math.floor(getShops().getItemShopValue(playerEquipment[EQUIP]));
				if(NextValue > BestValue){
					BestValue = NextValue;
					WillKeepItem1 = playerEquipment[EQUIP];
					WillKeepItem1Slot = EQUIP+28;
					if(playerEquipmentN[EQUIP] > 2 && !prayerActive[10]){
					WillKeepAmt1 = 3;
					} else if(playerEquipmentN[EQUIP] > 3 && prayerActive[10]){
					WillKeepAmt1 = 4;
					} else {
					WillKeepAmt1 = playerEquipmentN[EQUIP]; 
					}
				}
			}
		}
		if(!isSkulled && ItemsContained > 1 && (WillKeepAmt1 < 3 || (prayerActive[10] && WillKeepAmt1 < 4))){
		BestItem2(ItemsContained);
		}
	}
	public void BestItem2(int ItemsContained){
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		for(int ITEM = 0; ITEM < 28; ITEM++){
			if(playerItems[ITEM] > 0){
				NextValue = (int) Math.floor(getShops().getItemShopValue(playerItems[ITEM]-1));
				if(NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM]-1 == WillKeepItem1)){
					BestValue = NextValue;
					WillKeepItem2 = playerItems[ITEM]-1;
					WillKeepItem2Slot = ITEM;
					if(playerItemsN[ITEM] > 2-WillKeepAmt1 && !prayerActive[10]){
					WillKeepAmt2 = 3-WillKeepAmt1;
					} else if(playerItemsN[ITEM] > 3-WillKeepAmt1 && prayerActive[10]){
					WillKeepAmt2 = 4-WillKeepAmt1;
					} else {
					WillKeepAmt2 = playerItemsN[ITEM]; 
					}
				}
			}
		}
		for(int EQUIP = 0; EQUIP < 14; EQUIP++){
			if(playerEquipment[EQUIP] > 0){
			NextValue = (int) Math.floor(getShops().getItemShopValue(playerEquipment[EQUIP]));
				if(NextValue > BestValue && !(EQUIP+28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)){
					BestValue = NextValue;
					WillKeepItem2 = playerEquipment[EQUIP];
					WillKeepItem2Slot = EQUIP+28;
					if(playerEquipmentN[EQUIP] > 2-WillKeepAmt1 && !prayerActive[10]){
					WillKeepAmt2 = 3-WillKeepAmt1;
					} else if(playerEquipmentN[EQUIP] > 3-WillKeepAmt1 && prayerActive[10]){
					WillKeepAmt2 = 4-WillKeepAmt1;
					} else {
					WillKeepAmt2 = playerEquipmentN[EQUIP]; 
					}
				}
			}
		}
		if(!isSkulled && ItemsContained > 2 && (WillKeepAmt1+WillKeepAmt2 < 3 || (prayerActive[10] && WillKeepAmt1+WillKeepAmt2 < 4))){
		BestItem3(ItemsContained);
		}
	}
	public void BestItem3(int ItemsContained){
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		for(int ITEM = 0; ITEM < 28; ITEM++){
			if(playerItems[ITEM] > 0){
				NextValue = (int) Math.floor(getShops().getItemShopValue(playerItems[ITEM]-1));
				if(NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM]-1 == WillKeepItem1) && !(ITEM == WillKeepItem2Slot && playerItems[ITEM]-1 == WillKeepItem2)){
					BestValue = NextValue;
					WillKeepItem3 = playerItems[ITEM]-1;
					WillKeepItem3Slot = ITEM;
					if(playerItemsN[ITEM] > 2-(WillKeepAmt1+WillKeepAmt2) && !prayerActive[10]){
					WillKeepAmt3 = 3-(WillKeepAmt1+WillKeepAmt2);
					} else if(playerItemsN[ITEM] > 3-(WillKeepAmt1+WillKeepAmt2) && prayerActive[10]){
					WillKeepAmt3 = 4-(WillKeepAmt1+WillKeepAmt2);
					} else {
					WillKeepAmt3 = playerItemsN[ITEM]; 
					}
				}
			}
		}
		for(int EQUIP = 0; EQUIP < 14; EQUIP++){
			if(playerEquipment[EQUIP] > 0){
			NextValue = (int) Math.floor(getShops().getItemShopValue(playerEquipment[EQUIP]));
				if(NextValue > BestValue && !(EQUIP+28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1) && !(EQUIP+28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)){
					BestValue = NextValue;
					WillKeepItem3 = playerEquipment[EQUIP];
					WillKeepItem3Slot = EQUIP+28;
					if(playerEquipmentN[EQUIP] > 2-(WillKeepAmt1+WillKeepAmt2) && !prayerActive[10]){
					WillKeepAmt3 = 3-(WillKeepAmt1+WillKeepAmt2);
					} else if(playerEquipmentN[EQUIP] > 3-WillKeepAmt1 && prayerActive[10]){
					WillKeepAmt3 = 4-(WillKeepAmt1+WillKeepAmt2);
					} else {
					WillKeepAmt3 = playerEquipmentN[EQUIP]; 
					}
				}
			}
		}
		if(!isSkulled && ItemsContained > 3 && prayerActive[10] && ((WillKeepAmt1+WillKeepAmt2+WillKeepAmt3) < 4)){
		BestItem4();
		}
	}
	public void BestItem4(){
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		for(int ITEM = 0; ITEM < 28; ITEM++){
			if(playerItems[ITEM] > 0){
				NextValue = (int) Math.floor(getShops().getItemShopValue(playerItems[ITEM]-1));
				if(NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM]-1 == WillKeepItem1) && !(ITEM == WillKeepItem2Slot && playerItems[ITEM]-1 == WillKeepItem2) && !(ITEM == WillKeepItem3Slot && playerItems[ITEM]-1 == WillKeepItem3)){
					BestValue = NextValue;
					WillKeepItem4 = playerItems[ITEM]-1;
					WillKeepItem4Slot = ITEM;
				}
			}
		}
		for(int EQUIP = 0; EQUIP < 14; EQUIP++){
			if(playerEquipment[EQUIP] > 0){
			NextValue = (int) Math.floor(getShops().getItemShopValue(playerEquipment[EQUIP]));
				if(NextValue > BestValue && !(EQUIP+28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1) && !(EQUIP+28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2) && !(EQUIP+28 == WillKeepItem3Slot && playerEquipment[EQUIP] == WillKeepItem3)){
					BestValue = NextValue;
					WillKeepItem4 = playerEquipment[EQUIP];
					WillKeepItem4Slot = EQUIP+28;
				}
			}
		}
	}
	
	public void ItemKeptInfo(int Lose){
		for(int i = 17109; i < 17131; i++){
			getPA().sendFrame126("",i);
		}
		getPA().sendFrame126("Items you will keep on death:",17104);
		getPA().sendFrame126("Items you will lose on death:",17105);
		getPA().sendFrame126("Server",17106);
		getPA().sendFrame126("Max items kept on death:",17107);
		getPA().sendFrame126("~ "+Lose+" ~",17108);
		getPA().sendFrame126("The normal amount of",17111);
		getPA().sendFrame126("items kept is three.",17112);
	switch (Lose){
		case 0:
		default:
			getPA().sendFrame126("Items you will keep on death:",17104);
			getPA().sendFrame126("Items you will lose on death:",17105);
			getPA().sendFrame126("You're marked with a",17111);
			getPA().sendFrame126("@red@skull. @lre@This reduces the",17112);
			getPA().sendFrame126("items you keep from",17113);
			getPA().sendFrame126("three to zero!",17114);		
			getPA().sendFrame126("@red@Carried Wealth: ",17122);
			getPA().sendFrame126(""+getItems().getCarriedWealth()+" GP",17123);
		break;	
		case 1:
				getPA().sendFrame126("Items you will keep on death:",17104);
			getPA().sendFrame126("Items you will lose on death:",17105);
			getPA().sendFrame126("You're marked with a",17111);
			getPA().sendFrame126("@red@Skull. @lre@This reduces the",17112);
			getPA().sendFrame126("items you keep from",17113);
			getPA().sendFrame126("three to zero!",17114);
			getPA().sendFrame126("However, you also have",17115);
			getPA().sendFrame126("@red@Protect @lre@Item prayer",17118);
			getPA().sendFrame126("active, which saves",17119);
			getPA().sendFrame126("you one extra item!",17120);
			getPA().sendFrame126("@red@Carried Wealth: ",17122);
			getPA().sendFrame126(""+getItems().getCarriedWealth()+" GP",17123);
		break;
		case 3:
				getPA().sendFrame126("Items you will keep on death(if not skulled):",17104);
			getPA().sendFrame126("Items you will lose on death(if not skulled):",17105);
			getPA().sendFrame126("You have no factors",17111);
			getPA().sendFrame126("affecting the items",17112);
			getPA().sendFrame126("you keep.",17113);
			getPA().sendFrame126("@red@Carried Wealth: ",17122);
			getPA().sendFrame126(""+getItems().getCarriedWealth()+" GP",17123);
		break;
		case 4:
					getPA().sendFrame126("Items you will keep on death(if not skulled):",17104);
			getPA().sendFrame126("Items you will lose on death(if not skulled):",17105);
			getPA().sendFrame126("You have the @red@Protect",17111);
			getPA().sendFrame126("@red@Item @lre@prayer active,",17112);
			getPA().sendFrame126("which saves you one",17113);
			getPA().sendFrame126("extra item!",17114);
			getPA().sendFrame126("@red@Carried Wealth: ",17122);
			getPA().sendFrame126(""+getItems().getCarriedWealth()+" GP",17123);
		break;
		case 5:
					getPA().sendFrame126("Items you will keep on death(if not skulled):",17104);
			getPA().sendFrame126("Items you will lose on death(if not skulled):",17105);
			getPA().sendFrame126("@red@You are in a @red@Dangerous",17111);
			getPA().sendFrame126("@red@Zone, and will lose all",17112);
			getPA().sendFrame126("@red@if you die.",17113);
			getPA().sendFrame126("",17114);
			getPA().sendFrame126("@red@Carried Wealth: ",17122);
			getPA().sendFrame126(""+getItems().getCarriedWealth()+" GP",17123);
		break;
		}
	}
	public void ResetKeepItems(){
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		WillKeepAmt1 = 0;
		WillKeepAmt2 = 0;
		WillKeepAmt3 = 0;
		}
	public void FindItemKeptInfo(){
		if(isSkulled && prayerActive[10])
			ItemKeptInfo(1);
		else if(!isSkulled && !prayerActive[10])
			ItemKeptInfo(3);
		else if(!isSkulled && prayerActive[10])
			ItemKeptInfo(4);
		else if (inPits || inFightCaves()) {
			ItemKeptInfo(5);
			}
		}	   
	
	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}
	
	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 4, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 8, 8, 12, //50
		8, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};

	public void destruct() {
		//synchronized (this) {
               PlayerSave.saveGame(this);
               PlayerSave.saveGame(this);
        if(disconnected == true) { 
            saveCharacter = true;
        }
		if(disconnected == true){
				 getTradeAndDuel().declineTrade();
		 }
		if(session == null) 
			return;
		PlayerSave.saveGame(this);
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		getPA().removeFromCW();
		if (inPits) {
		Server.fightPits.removePlayerFromPits(playerId);
		}
		Misc.println("[DEREGISTERED]: "+playerName+"");
		PlayerSave.saveGame(this);
                saveCharacter = true;
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	//}
}
	
	
	public void sendMessage(String s) {
		//synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			//}
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		//synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			//}
		}
	}	
public void CatchimpNpc(String npcName, int Net, int npcId, int itemId, int AmtExp, int Req, int playerId) {
npcName = Server.npcHandler.getNpcListName(npcId);
	if (System.currentTimeMillis() - foodDelay >= 1500) { //anti spamm
		if (playerLevel[21] >= Req) { //first we check if he's high enough to catch
			if (playerEquipment[playerWeapon] == 10010 || playerEquipment[playerWeapon] == 11259) { //player got net?
				if (playerLevel[21] + Misc.random(10) >= Misc.random(20) + Req) { //catch chance
				if (Misc.random(1000) == 1) {
				sendMessage("You catched a GIGANTIC Impling and gained triple Experience!"); //looks like player got a net
				getItems().addItem(722, 1); //itemid is different so its defined in the method
				startAnimation(6999); //this always stays 6999, no need to change this
				getPA().addSkillXP(AmtExp*3, 21); //AmtExp is different so its defined in the method
				} else {
				sendMessage("You Catched an Impling!"); //looks like player got a net
				getItems().addItem(itemId, 1); //itemid is different so its defined in the method
				startAnimation(6999); //this always stays 6999, no need to change this
				getPA().addSkillXP(AmtExp, 21); //AmtExp is different so its defined in the method
				}
				} else {
				sendMessage("You Failed To Catch The Impling");
				startAnimation(6999);
				}
			} else { //player got net?
			sendMessage("You need to wear a butterfly net!"); //looks like he doesn't
			return;
			}	
		} else {
		sendMessage("You need atleast "+ Req +" Hunter To catch that Impling!");
		return;
		}
		foodDelay = System.currentTimeMillis();// we use food timer but it really doesn't mather, this is just used for anti-spamm :)
	}
}			
public void CatchHunterNpc(String npcName, int Net, int npcId, int itemId, int AmtExp, int Req, int playerId) {
npcName = Server.npcHandler.getNpcListName(npcId);
	if (System.currentTimeMillis() - foodDelay >= 1500) { //anti spamm
		if (playerLevel[21] >= Req) { //first we check if he's high enough to catch
			if (playerEquipment[playerWeapon] == 10010 || playerEquipment[playerWeapon] == 11259) { //player got net?
				if (playerLevel[21] + Misc.random(10) >= Misc.random(20) + Req) { //catch chance
				if (Misc.random(1000) == 1) {
				sendMessage("You catched a GIGANTIC butterfly and gained triple Experience!"); //looks like player got a net
				getItems().addItem(722, 1); //itemid is different so its defined in the method
				startAnimation(6999); //this always stays 6999, no need to change this
				getPA().addSkillXP(AmtExp*3, 21); //AmtExp is different so its defined in the method
				} else {
				sendMessage("You Catched a Butterfly!"); //looks like player got a net
				getItems().addItem(itemId, 1); //itemid is different so its defined in the method
				startAnimation(6999); //this always stays 6999, no need to change this
				getPA().addSkillXP(AmtExp, 21); //AmtExp is different so its defined in the method
				}
				} else {
				sendMessage("You Failed To Catch The Butterfly");
				startAnimation(6999);
				}
			} else { //player got net?
			sendMessage("You need to wear a butterfly net!"); //looks like he doesn't
			return;
			}	
		} else {
		sendMessage("You need atleast "+ Req +" Hunter To catch that Butterfly!");
		return;
		}
		foodDelay = System.currentTimeMillis();// we use food timer but it really doesn't mather, this is just used for anti-spamm :)
	}
}
	public void initialize() {
	                        if(checkVotes(playerName)) {
								rxPoints += 51;
                                sendMessage("Thanks for voting!");
                        }
        logininterface();
	    welcomescreen();
		mymessage();
		loginMessage();
		//plimusdonation();
		// synchronized (this) {
		outStream.createFrame(249);
		outStream.writeByteA(1); // 1 for members, zero for free
		outStream.writeWordBigEndianA(playerId);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName))
					disconnected = true;
				}
			}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		for(int p = 0; p < PRAYER.length; p++) { // reset prayer glows 
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);	
			}
			for(int p = 0; p < CURSE.length; p++) { // reset prayer glows 
				curseActive[p] = false;
				getPA().sendFrame36(CURSE_GLOW[p], 0);	
			}
						if(hasNpc == true) {
				if (summonId > 0) {
					Server.npcHandler.spawnNpc3(this, summonId, absX, absY-1, heightLevel, 0, 120, 25, 200, 200, true, false, true);
					
				}
			}
			getPA().sendCrashFrame();
			getPA().handleWeaponStyle();
			getPA().handleLoginText();
			accountFlagged = getPA().checkForFlags();
			//getPA().sendFrame36(43, fightMode-1);
			getPA().sendFrame36(505, 0);
			getPA().sendFrame36(506, 0);
			getPA().sendFrame36(507, 0);
			getPA().sendFrame36(508, 1);
			getPA().sendFrame36(166, 4);
			getPA().sendFrame36(108, 0);//resets autocast button
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107(); // reset screen
			getPA().setChatOptions(0, 0, 0); // reset private messaging options
			setSidebarInterface(1, 3917);
			setSidebarInterface(2, 638);
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 5608);
			getPA().totallevelsupdate();
			if(playerMagicBook == 0) {
				setSidebarInterface(6, 1151); //modern
			}
			if(playerMagicBook == 1){
				setSidebarInterface(6, 12855); // ancient
			}
			if(playerMagicBook == 2){
				setSidebarInterface(6, 16640);
			}
			if(altarPrayed == 0) {
				setSidebarInterface(5, 5608);
			} else {
				setSidebarInterface(5, 22500);
			}
			correctCoordinates();
			setSidebarInterface(7, 18128);		
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			//setSidebarInterface(11, 4445); // wrench tab
			setSidebarInterface(11, 904); // wrench tab
			setSidebarInterface(12, 147); // run tab
			setSidebarInterface(13, -1); //music tab 6299 for lowdetail. 962 for highdetail
			setSidebarInterface(15, 6299); //blank
			setSidebarInterface(14, 24999); //blank
			setSidebarInterface(16, 17011); //summoning
			sendMessage("Welcome to RuneForge.");
			if (lastsummon > 0) {
				Summoning().SummonNewNPC(lastsummon, Summoning().getNpcDataIndex(lastsummon));
			}
			if (inWild()) {
				sendMessage("Alert##Wildy Notification## You are currently risking: "+ getItems().getCarriedWealth() +" GP##Watch Out!");
			}
			/*if (xpLock == true) {
				sendMessage("Your XP is <shad=3781373>LOCKED</shad>, go to equipment tab to unlock it");
			} else {
				sendMessage("Your XP is currently <shad=3781373>UNLOCKED</shad>, go to equipment tab to lock it");
			}*/
			if (inWarriorG() && heightLevel == 2) {
				getPA().movePlayer(2846, 3540, 2);
			}
			if (Config.doubleEXPWeekend == true) {
				sendMessage("Enjoy Double EXP Weekend!");
			}
			//getPA().loadAnnouncements();
			getPA().showOption(4, 0,"Trade With", 3);
			getPA().showOption(5, 0,"Follow", 4);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getPA().sendFrame126("Combat Level: "+getCombatLevel()+"", 3983);
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat],1,playerHat);
			getItems().setEquipment(playerEquipment[playerCape],1,playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet],1,playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],playerEquipmentN[playerArrows],playerArrows);
			getItems().setEquipment(playerEquipment[playerChest],1,playerChest);
			getItems().setEquipment(playerEquipment[playerShield],1,playerShield);
			getItems().setEquipment(playerEquipment[playerLegs],1,playerLegs);
			getItems().setEquipment(playerEquipment[playerHands],1,playerHands);
			getItems().setEquipment(playerEquipment[playerFeet],1,playerFeet);
			getItems().setEquipment(playerEquipment[playerRing],1,playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],playerEquipmentN[playerWeapon],playerWeapon);
			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPA().logIntoPM();
			getPA().sendFrame126("Summoning Special: "+(int)summAmount+"/60", 17040);
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: "+playerName+"");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().clearClanChat();
			getPA().resetFollow();
			if (isnew == 0) {
			getDH().sendDialogues(2500, 0);
			isnew =+ 1;
			} else if (isnew == 1)
			return;
			if (Hastarter == 1) {
			return;
			} else if (addStarter)
				getPA().addStarter();
			Hastarter =+ 1;
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
		//}
        if (acceptAid) {
        acceptAid = false;
        getPA().sendFrame36(503, 0);
        getPA().sendFrame36(427, 0);

        } else
        
        acceptAid = true;
        getPA().sendFrame36(503, 1);
        getPA().sendFrame36(427, 1);
    }
	

	


	public void update() {
		//synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		//}
	}
	
	public void mymessage() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				int r3 = 0;
				r3 = Misc.random(5);
			       if (r3 == 0) {           
					sendMessage("<col=255>[SERVER] Only Donate to Styl3r or Earthquake");
					saveCharacter = true;
				} else if (r3 == 1) { 
					sendMessage("<col=255>[SERVER] Use ::resetpin if you forgot your pin!");
					saveCharacter = true;
				} else if (r3 == 2) {
					sendMessage("<col=255>[SERVER] Server is still in progress");
					saveCharacter = true;
				} else if (r3 == 3) {
					sendMessage("<col=255>[SERVER] Almost everything is spawnable!");
					saveCharacter = true;
				}
			}
		}, 300000); //milisecondsservermessage
	};
	public void welcomescreen() {
	
		    getPA().sendFrame200(21304, 861);
		    getPA().sendFrame75(1, 21304);
			getPA().sendFrame126("Your currently have a", 21321);
			getPA().sendFrame126("Bank-PIN set. Talk to a banker", 21322);
			getPA().sendFrame126("If you wish to remove it.", 21324);
			getPA().sendFrame126("You are a donator! Thank-you for", 21314);
			getPA().sendFrame126("donating, "+playerName+".", 21315);
			getPA().sendFrame126("", 21316);
			getPA().sendFrame126("You have never logged on this account before. Welcome!", 21313);
			}
	public void logininterface() {
			if(inWild()) {
			return;
			}
	getPA().showInterface(16300); 
}

	public void handleQuestTab() {
				donorControlPanel();
				getPA().sendFrame126("@lre@Players Online: "+PlayerHandler.getPlayerCount()+ " ", 29155); //quest journal title
				getPA().sendFrame126("Your Statistics:", 663);
				getPA().sendFrame126("@lre@RuneForge Player Info:", 29165);
			getPA().sendFrame126("@lre@Combat Level: @gre@" +combatLevel+ " ", 29166);
			if (playerRights == 4)
			getPA().sendFrame126("@lre@Rank: @gre@Donator", 29167);
			else if (playerRights == 3)
			getPA().sendFrame126("@lre@Rank: @gre@Owner", 29167);
			else if (playerRights == 2)
			getPA().sendFrame126("@lre@Rank: @gre@Admin", 29167);
			else if (playerRights == 1)
			getPA().sendFrame126("@lre@Rank: @gre@Mod", 29167);
			else if(playerRights == 0)
			getPA().sendFrame126("@lre@Rank: @gre@Normal Player", 29167);
			else if(playerRights == 4)
			getPA().sendFrame126("@lre@Rank: @gre@Donator", 29167);
			else if(playerRights == 5)
			getPA().sendFrame126("@lre@Rank: @gre@Super Donator", 29167);
			else if(playerRights == 6)
			getPA().sendFrame126("@lre@Rank: @gre@Extreme Donator", 29167);
                        else if(playerRights == 9)
                        getPA().sendFrame126("@lre@Rank: @gre@Gfx Designer", 29167);
                        else if(playerRights == 8)
                        getPA().sendFrame126("@lre@Rank: @gre@Trial Moderator", 29167);
				getPA().sendFrame126("@lre@RuneForge Points: @gre@" +rxPoints+ " ", 29168); //quest title
				getPA().sendFrame126("@lre@Agility Points: @gre@" +magePoints+ " ", 29169);
				getPA().sendFrame126("@lre@Donator Points: @gre@" +donPoints+ " ", 29170);
				getPA().sendFrame126("@lre@EP Amount: @gre@" +earningPotential+ " ", 29171);
				getPA().sendFrame126("@lre@Exp Lock Status: @gre@"+xpLock+" ", 29172);
				getPA().sendFrame126("@lre@Slayer Task: @gre@"+Server.npcHandler.getNpcListName(slayerTask)+" ", 29173);
			    getPA().sendFrame126("@lre@Amount: @gre@"+taskAmount+" ", 29174);
			getPA().sendFrame126("@lre@Kill Count: @gre@"+barrowsKillCount, 29175);
  			getPA().sendFrame126("@gre@"+Server.days+" @lre@Days,@gre@ "+Server.hours+" @lre@Hours, @gre@ "+Server.minutes+" @lre@Min", 29176);
			getPA().sendFrame126("@lre@Title: @gre@"+KillStreak.sendKillStreak(this), 29177);
		getPA().sendFrame126("@lre@Killstreak: @gre@"+killStreak+"", 29178);
		getPA().sendFrame126("@lre@Deathstreak: @gre@"+deathStreak+"", 29179);
				getPA().sendFrame126("", 29180);					
				getPA().sendFrame126("", 29181);
				}
				
											/*getPA().sendFrame126("RuneForge: "+PlayerHandler.getPlayerCount()+" Online", 29155); //Tab Title
									getPA().sendFrame126("Your Rape Status:", 663);	
			getPA().sendFrame126("@gre@Items kept on death ", 29165);
			getPA().sendFrame126("@gre@Save Game ", 29166);
			getPA().sendFrame126("@lre@Playername: @gre@"+ Misc.optimizeText(playerName) +" " , 29167);
			if (playerRights == 4)
			getPA().sendFrame126("@lre@Rank: @gre@Donator", 29168);
			else if (playerRights == 3)
			getPA().sendFrame126("@lre@Rank: @gre@Owner", 29168);
			else if (playerRights == 2)
			getPA().sendFrame126("@lre@Rank: @gre@Admin", 29168);
			else if (playerRights == 1)
			getPA().sendFrame126("@lre@Rank: @gre@Mod", 29168);
			getPA().sendFrame126("@lre@Combat Level: @gre@" +combatLevel+ " ", 29169);
			getPA().sendFrame126("@lre@Torvapkz Points: @gre@"+rxPoints+"  ", 29170);
			getPA().sendFrame126("@lre@Exp Lock Status: @gre@"+xpLock+" ", 29171);
			getPA().sendFrame126("", 29173);
			getPA().sendFrame126("@lre@Slayer Task: @gre@"+Server.npcHandler.getNpcListName(slayerTask)+" ", 29174);
			getPA().sendFrame126("     Amount: @gre@"+taskAmount+" ", 29175);
			getPA().sendFrame126(" ", 29176);
			}*/
				//getPA().sendFrame126("@or1@         ¬Server Quests¬", 29175);
				//getPA().sendFrame126("", 29176);
				//getPA().sendFrame126("-Challenging Mission", 29177); //113249
				//getPA().sendFrame126("", 29178);
				//getPA().sendFrame126("Lost Not Forgotten", 29179);
				//getPA().sendFrame126("Chaos Miners", 29180);
				//if (QP3 == 0) { //113250
					//getPA().sendFrame126("@red@Lost Not Forgotten", 29179);
				//} else if (QP3 >= 1) {
					//getPA().sendFrame126("@yel@Lost Not Forgotten", 29179);
				//}
				//if (QP2 == 0) { //113250
					//getPA().sendFrame126("@red@Travelers Guide", 29178);
				//} else if (QP2 == 1 || QP2 == 2|| QP2 == 3 || QP2 == 4 || QP2 == 5 || QP2 == 6 || QP2 == 7){
					//getPA().sendFrame126("@yel@Travelers Guide", 29178);
				//} else if (QP2 == 8 || QP2 == 9) {
					//getPA().sendFrame126("@gre@Travelers Guide", 29178);
				//}
				//if (QP1 == 0) {
					//getPA().sendFrame126("@red@Beginners Mission", 29177);
				//} else if (QP1 == 1 || QP1 == 2|| QP1 == 3 || QP1 == 4 || QP1 == 5 || QP1 == 6){
					//getPA().sendFrame126("@yel@Beginners Mission", 29177);
				//} else if (QP1 == 7) {
					//getPA().sendFrame126("@gre@Beginners Mission", 29177);
			

	
			public void dungemote(final Client c) {
			    EventManager.getSingleton().addEvent(new Event() {
				 public void execute(EventContainer dung) {
				   if (dungtime == 16) {
				       c.gfx0(2442);
					c.startAnimation(13190);
				    }
				   if (dungtime == 15) {
					c.npcId2 = 11228;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13192);
				    }
				   if (dungtime == 10) {
					c.npcId2 = 11227;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13193);
				    }
				   if (dungtime == 6) {
				       c.gfx0(2442);
				    }
				   if (dungtime == 5) {
					c.npcId2 = 11229;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13194);
				    }
				   if (dungtime == 0) {
					c.isNpc = false;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				    }
				   if (c == null || dungtime <= 0) {
				       dung.stop();
                                                                         return; 
				    }
				   if (dungtime >= 0) {
					dungtime--;
				    }
				}
			    }, 600);
			}
        public boolean checkVotes(String playerName) {
                try {
                        String urlString = "http://area-51.net46.net/vote.php?type=checkvote&username="+playerName;
                        urlString = urlString.replaceAll(" ", "%20");
                        URL url = new URL(urlString);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String results = reader.readLine();
                        if(results.length() > 0) {
                                if(results.equals("user needs reward..."))
                                        return true;
                                else
                                        return false;
                        }
                } catch (MalformedURLException e) {
                        System.out.println("Malformed URL Exception in checkVotes(String playerName)");
                } catch (IOException e) {
                        System.out.println("IO Exception in checkVotes(String playerName)");
                }
                return false;
        }

	public void logout() {
		//synchronized (this) {
			if (duelStatus > 0) {
				sendMessage("You can not log out in Duel Arena.");
				return;
			}
			if(System.currentTimeMillis() - logoutDelay > 10000) {
			outStream.createFrame(109);
				properLogout = true;
				PlayerSave.saveGame(this);
			if (lastsummon > 0) {
				for (int i = 0; i < NPCHandler.maxNPCs; i++) {
					if (NPCHandler.npcs[i] != null) {
						if (NPCHandler.npcs[i].summon == true) {
							if (NPCHandler.npcs[i].spawnedBy == getId()) {
								NPCHandler.npcs[i].isDead = true;
								NPCHandler.npcs[i].applyDead = true;
								NPCHandler.npcs[i].summon = false;
							}
						}
					}
				}
			}
				saveCharacter = true;
			} else {
				sendMessage("You must wait a few seconds from being out of combat before you can do this.");
			}
		//}
	}
	public void SaveGame() {
		//synchronized (this) {
				PlayerSave.saveGame(this);
		//}
	}
	public static int[] cats = {3506, 766, 3507};
	
	public int packetSize = 0, packetType = -1;
	public long saveGameDelay;
	
	public boolean validClient(int index) {
Client p = (Client) PlayerHandler.players[index];
if ((p != null) && !p.disconnected) {
return true;
}
return false;
}
public Client getClient(int index) {
return ((Client) PlayerHandler.players[index]);
} 
	public void rankOnServer() {
		if (playerRights == 0) {
			getPA().sendFrame126("Your Rank: Normal Player", 29162);
		} else if (playerRights == 1) {
			getPA().sendFrame126("Your Rank: Moderator", 29162);
		} else if (playerRights == 2) {
			getPA().sendFrame126("Your Rank: Administrator", 29162);
		} else if (playerRights == 3) {
			getPA().sendFrame126("Your Rank: Owner", 29162);
		} else if (playerRights == 4) {
			getPA().sendFrame126("Your Rank: Normal Donator", 29162);
		} else if (playerRights == 5) {
			getPA().sendFrame126("Your Rank: Super Donator", 29162);
		} else if (playerRights == 6) {
			getPA().sendFrame126("Your Rank: Extreme Donator", 29162);
		} else if (playerRights == 7) {
			getPA().sendFrame126("Your Rank: Server Developer", 29162);
		} else if (playerRights == 8) {
			getPA().sendFrame126("Your Rank: Trial Moderator", 29162);
		}
	}
	
	public void FetchDice()
	{
		int rnd;
		String Message = "";
		if (cDice == 0 || (System.currentTimeMillis() - diceDelay <= 1000)) {
			return;
		}
		switch (cDice) {
		//Dice
			case 15096: rnd = Misc.random(19)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on a twenty-sided die."); break;
			case 15094: rnd = Misc.random(11)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on a twelve-sided die."); break;
			case 15092: rnd = Misc.random(9)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on a ten-sided die."); break;
			case 15090: rnd = Misc.random(7)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on an eight-sided die."); break;
			case 15100: rnd = Misc.random(3)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on a four-sided die."); break;
			case 15086: rnd = Misc.random(5)+1;	Message = ("rolled <col=16711680>"+ rnd +"</col> on a six-sided die."); break;
			case 15088: rnd = Misc.random(11)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on two six-sided dice."); break;
			case 15098: rnd = Misc.random(99)+1; Message = ("rolled <col=16711680>"+ rnd +"</col> on the percentile dice."); break;
		}
		sendMessage("You " + Message);
			if (clanDice){
				if (clanId >= 0) {
					Server.clanChat.messageToClan("Clan Chat channel-mate <col=16711680>"+playerName+"</col> "+Message, clanId);
				}
			}
		cDice = 0;
	}

	public void useDice(int itemId, boolean clan){
			if (System.currentTimeMillis() - diceDelay >= 3000) {
				sendMessage("Rolling...");
				startAnimation(11900);
				diceDelay = System.currentTimeMillis();
				cDice = itemId;
				clanDice = clan;
			switch (itemId) {
				//Gfx's
				case 15086: gfx0(2072); break;
				case 15088: gfx0(2074); break;
				case 15090: gfx0(2071); break;
				case 15092: gfx0(2070); break;
				case 15094: gfx0(2073); break;
				case 15096: gfx0(2068); break;
				case 15098: gfx0(2075); break;
				case 15100: gfx0(2069); break;
			}
		}

	}
	
	public void donorControlPanel() {
		if (isDonator == 1) {
			getPA().sendFrame126("Refill Special Bar", 29161);
			getPA().sendFrame126("@lre@100% Special Bar", 29162);
		} else {
			getPA().sendFrame126("Refill Special Bar", 29161);
			getPA().sendFrame126("@lre@You must be a Donators", 29162);
		}
	}
	
	public void process() {		
handleQuestTab();
FetchDice();
stafftab();
		/* Sets Interfaces for Wild, PC, etc. */
		setAllInterfaces();
			if (inWild() == true && WildernessWarning == false) {
			resetWalkingQueue();
			wildyWarning();
			WildernessWarning = true;
	}		
		if (System.currentTimeMillis() - lastoverload > 1000) {
			if (overloadcounter > 0) {
				startAnimation(2383);
				dealDamage(10);
				handleHitMask(10);
				overloadcounter -= 1;
				getPA().refreshSkill(3);
				lastoverload = System.currentTimeMillis();	
			}
		}
		if(basket == true) {
			playerStandIndex = 1836;
			playerWalkIndex = 1836;
			playerRunIndex = 1836;
		}
		
		

		if (getItems().updateInventory) {
			getItems().updateInventory();
		}
		if(needsToSpawn && !cannotSummon()) {
			Summoning.SummonNewNPC(lastsummon, Summoning().getNpcDataIndex(lastsummon));
			needsToSpawn = false;
		}
        if(vestaDelay > 0) {
			vestaDelay--;
        }
		if(SolProtect > 0) {
			if(playerEquipment[playerWeapon] != 15486) {
				sendMessage("You are no longer protected as you unequipped Staff of light.");
				SolProtect = 0;
				return;
			}		
			SolProtect--;
			if (SolProtect == 1) {
				sendMessage("Your lightness protection slowly leaves your soul...");
			}			
		}
		if (forinthryBrace > 0) {
			if (NPCHandler.npcs[npcIndex] != null && NPCHandler.npcs[npcIndex].npcType >= 6604 && NPCHandler.npcs[npcIndex].npcType <= 6730) {
				forinthryBrace--;
			}
		}
		if (forinthryBrace == 60) {
			sendMessage("<shad=15733302>You have immunity against npcs for 30 more seconds!");
		}
		if(forinthryBrace == 0) {
			forinthryBrace = -1;
		}
		
		/**
		 * Start Of Summoning
		 */
		/* Summon Timer */
		String time = (summonTime > 0) ? ("" + (summonTime/120 - ((summonTime%120  <= 60) ? 1 : 0)) + "." + ((summonTime%120  <= 60) ? "30" : "00")) : "00.00";
		getPA().sendFrame126(time, 17021);
		getPA().sendFrame126("Unlimited", 17025);
		/* Summon Warning Messages */
		if (summonTime <= 60 && summonTime >= 55 && stopSpam3 == 0) {
			sendMessage("<shad=15733302>Your summon has 60 seconds left before it vanishes.");
			stopSpam3 = 1;
		}
		if (summonTime <= 30 && summonTime >= 25 && stopSpam2 == 0) {
			sendMessage("<shad=15733302>Your summon has 30 seconds left before it vanishes.");
			stopSpam2 = 1;
		}
		if (summonTime > 0) {
			if (!cannotSummon()) {
				summonTime--;
				stopSpam = 1;
			}
		}
		/* Drops BoB Items */
		if (summonTime == 0 && stopSpam == 1) {
			getPA().dropBoBItems();
		}
		/**
		 * End Of Summoning
		 */
		
		if(gwdelay > 0) {
			gwdelay--;
		}
		if(clawDelay > 0) {
			clawDelay--;
		}		
		if(clawDelay == 1) {
		    delayedDamage = clawDamage/4;
		    delayedDamage2 = (clawDamage/4)+1;
			if(clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 1, clawDamage/4);
			}
			if(clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 1, clawDamage/4);
			}
			if(clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 2, (clawDamage/4) + 1);
			}
			if(clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 2, (clawDamage/4) + 1);
			}
			clawDelay = 0;
			specEffect = 0;
			previousDamage = 0;
			usingClaws = false;
			clawType = 0;
		}
		
		/* Woodcutting */
		if (wcTimer > 0) {
			wcTimer--;
		} else if (wcTimer == 0 && woodcut[0] > 0) {
			getWoodcutting().cutWood();
		} else if (miningTimer > 0 && mining[0] > 0) {
			miningTimer--;
		} else if (miningTimer == 0 && mining[0] > 0) {
			getMining().mineOre();
		} else  if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
			getSmithing().smelt(smeltType);
		}


		if(System.currentTimeMillis() - saveGameDelay > Config.SAVE_TIMER && !disconnected) {
			saveCharacter = true; 
			saveGameDelay = System.currentTimeMillis();
		}
		
		
		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage/2;
			if (damage > 0) {
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
					typeOfDamageMask = 0;
					colorHitMask = 5;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
					typeOfDamageMask2 = 0;
					colorHitMask2 = 5;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}	
		}
		
		
		if(System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if(duelCount != 1) {
				forcedChat(""+(--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}
	
		if(System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT_WITH_RING && playerEquipment[playerRing] == 19969) {
			specDelay = System.currentTimeMillis();
				if(specAmount < 10) {
					specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		} else if(System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
				if(specAmount < 10) {
					specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}
		
		if(System.currentTimeMillis() - sumSpecDelay > Config.INCREASE_SUMMON_AMOUNT) {
			sumSpecDelay = System.currentTimeMillis();
			if(summAmount < 60) {
				summAmount += 10;
				getPA().sendFrame126("Summoning Special: "+(int)summAmount+"/60", 17040);
			if (summAmount > 60)
				summAmount = 60;
				getPA().sendFrame126("Summoning Special: "+(int)summAmount+"/60", 17040);
			}
		}
		
		if(clickObjectType > 0 && goodDistance(objectX + objectXOffset, objectY + objectYOffset, getX(), getY(), objectDistance)) {
			if(clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}
		
		if((clickNpcType > 0) && NPCHandler.npcs[npcClickIndex] != null) {			
			if(goodDistance(getX(), getY(), NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY(), 1)) {
				if(clickNpcType == 1) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType);
				}
				if(clickNpcType == 2) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if(clickNpcType == 3) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(), NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}
		
		if(walkingToItem) {
			if(getX() == pItemX && getY() == pItemY || goodDistance(getX(), getY(), pItemX, pItemY,1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX, pItemY, true);
			}
		}
		
		if(followId > 0) {
			getPA().followPlayer(playerIndex);
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		getFishing().FishingProcess();
		getCombat().handlePrayerDrain();
		
		if(System.currentTimeMillis() - singleCombatDelay >  8500) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 8500) {
			underAttackBy2 = 0;
		}
		
		if(System.currentTimeMillis() - restoreStatsDelay >  60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++)  {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if(level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if(System.currentTimeMillis() - teleGrabDelay >  1550 && usingMagic) {
			usingMagic = false;
			if(Server.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
			}
		}
		
		if(!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}
		
		if(hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if(skullTimer > 0) {
			skullTimer--;
			if(skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}	
		}
		
		if(isDead && respawnTimer == -6) {
			getPA().applyDead();
		}
		
		if(respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if(respawnTimer == 12) {
			respawnTimer--;
			startAnimation(836);
			poisonDamage = -1;
		}	
		
		if(respawnTimer > -6) {
			respawnTimer--;
		}
		if(freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, PlayerHandler.players[frozenBy].absX, PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}
		
		if(hitDelay > 0) {
			hitDelay--;
		}
		
		if(teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if(teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if(teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if(teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}	

		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);				
			}		
		}
		
		if(attackTimer > 0) {
			attackTimer--;
		}
		
		if(attackTimer == 1){
			if(npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if(playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
		
		if(timeOutCounter > Config.TIMEOUT) {
			//disconnected = true;
		}
		
		timeOutCounter++;
		
		if(inTrade && tradeResetNeeded){
			Client o = (Client) PlayerHandler.players[tradeWith];
			if(o != null){
				if(o.tradeResetNeeded){
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}
	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

		public void WalkTo(int x, int y) {
		newWalkCmdSteps = (Math.abs((x+y)));
		if (newWalkCmdSteps % 1 != 0) newWalkCmdSteps /= 1;
		if (++newWalkCmdSteps > walkingQueueSize) {
			println("Warning: WalkTo command contains too many steps (" + newWalkCmdSteps + ").");
			newWalkCmdSteps = 0;
		}
		int firstStepX = absX;
		firstStepX -= mapRegionX*8;

		for (int i = 1; i < newWalkCmdSteps; i++) {
			newWalkCmdX[i] = x;
			newWalkCmdY[i] = y;
		}
		newWalkCmdX[0] = newWalkCmdY[0];
		int firstStepY = absY;
		firstStepY -= mapRegionY*8;
		newWalkCmdIsRunning = ((inStream.readSignedByteC() == 1));
		for (int q = 0; q < newWalkCmdSteps; q++) {
			newWalkCmdX[q] += firstStepX;
			newWalkCmdY[q] += firstStepY;
		}
	}
	public void fmwalkto(int i, int j) {
        newWalkCmdSteps = 0;
        if(++newWalkCmdSteps > 50)
            newWalkCmdSteps = 0;
        int k = absX + i;
        k -= mapRegionX * 8;
        newWalkCmdX[0] = newWalkCmdY[0] = tmpNWCX[0] = tmpNWCY[0] = 0;
        int l = absY + j;
        l -= mapRegionY * 8;
		isRunning2 = false;
		isRunning = false;
        //for(this.i = 0; this.i < newWalkCmdSteps; this.i++)
       //{
            newWalkCmdX[0] += k;
            newWalkCmdY[0] += l;
        //}
	//lastWalk = System.currentTimeMillis();
	//walkDelay = 1;
        poimiY = l;
        poimiX = k;
    }
	public int tmpNWCY[] = new int[walkingQueueSize];
	public int tmpNWCX[] = new int[walkingQueueSize];
	
	public synchronized Stream getInStream() {
		return inStream;
	}
	
	public synchronized int getPacketType() {
		return packetType;
	}
	
	public synchronized int getPacketSize() {
		return packetSize;
	}
	
	public synchronized Stream getOutStream() {
		return outStream;
	}
	
	public ItemAssistant getItems() {
		return itemAssistant;
	}
		
	public PlayerAssistant getPA() {
		return playerAssistant;
	}
	
	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public TradeLog getTradeLog() {
		return tradeLog;
	}

	public WarriorsGuild getWarriorsGuild() {
		return warriorsGuild;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public Crafting getCrafting() {
		return crafting;
	}
	
	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}
	
	public CombatAssistant getCombat() {
		return combatAssistant;
	}
	
	public ActionHandler getActions() {
		return actionHandler;
	}
	
	public Dicing getDicing() {
		return dicing;
	}
  
	public PlayerKilling getKill() {
		return playerKilling;
	}
	
	public IoSession getSession() {
		return session;
	}
	
	public Potions getPotions() {
		return potion;
	}
	
	public PotionMixing getPotMixing() {
		return potionMixing;
	}
	
	public Food getFood() {
		return food;
	}
	
	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}
	
	/*public Runecrafting getRunecrafting() {
		return runecrafting;
	}*/
	public Woodcutting getWoodcutting() {
		return woodcutting;
	}
	
	public Mining getMining() {
		return mine;
	}
		public Pins getBankPin() {
		return pins;
	}
	
	public Cooking getCooking() {
		return cooking;
	}
	
	public Agility getAgil() {
		return ag;
	}
	
	
	public Fishing getFishing() {
		return fish;
	}
	
	public Smithing getSmithing() {
		return smith;
	}
	
	public Farming getFarming() {
		return farming;
	}
	
	public Thieving getThieving() {
		return thieving;
	}
	
	
	public Herblore getHerblore() {
		return herblore;
	}

	public Summoning Summoning() {
		return Summoning;
	}
	
	public Firemaking getFiremaking() {
		return firemaking;
	}
	
	public SmithingInterface getSmithingInt() {
		return smithInt;
	}
	
	public Prayer getPrayer() { 
		return prayer;
	}
	
	public Construction getConstruction() {
		return construction;
	}
	
	public SummonAddOn getSummonAddOn() {
		return summonAddOn;
	}
	
	public DonatorControlPanel getDCP() {
		return donatorControlPanel;
	}
	
	public QuickPrayer getQP() {
		return quickPrayer;
	}

	public QuickCurses getQC() {
		return quickCurses;
	}

	public Curse getCurse() { 
		return curse;
	}

	public Fletching getFletching() { 
		return fletching;
	}

/**
* Gets the prospecting class.
* @return The prospecting class.
*/
public Prospecting getProspecting() {
		return prospecting;
	}
	
	/**
	 * End of Skill Constructors
	 */

	 /**
	 * Second skill instances.
	 */
	private Prospecting prospecting = new Prospecting();
	
	public void queueMessage(Packet arg1) {
		synchronized(queuedPackets) {
			//if (arg1.getId() != 41)
				queuedPackets.add(arg1);
			//else
				//processPacket(arg1);
		}
	}
	
	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized(queuedPackets) {
			p = queuedPackets.poll();
		}
		if(p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if(packetType > 0) {
			//sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
			processPackets++;
		}
		timeOutCounter = 0;
		if(processPackets > Config.MAX_PROCESS_PACKETS) {
			return false;
		}
		return true;
	}
	
	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if(p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if(packetType > 0) {
				//sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}



	
	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client)PlayerHandler.players[playerId]);
					c.stop();
				}
			}, 10000);
		
		}
		
		
		if (inRFD()) {
			getPA().movePlayer(1899,5363, playerId * 4+2);
			sendMessage("Your wave will start in 10 seconds Good Luck.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.rfd.spawnNextWave((Client)PlayerHandler.players[playerId]);
					c.stop();
				}
			}, 10000);
		
		}
	
	}
	
	
	
	public void handleYell(String playerCommand) {
		/**
		* @ G Wishart
		* This is the sensor for the yell command
		*/
		String text = playerCommand.substring(5);
		String[] bad = {"<img=1>", "<img=2>", "<img=0>"};
		for(int i = 0; i < bad.length; i++){
			if(text.indexOf(bad[i]) >= 0){
				sendMessage("You cannot use one of the words you just said in yell!");
				return;
			}
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client)PlayerHandler.players[j];
				if(Connection.isMuted(this)){
					sendMessage("You are muted and cannot yell");
					return;
				}
				/*if (isDonator == 0) {
					sendMessage("You must be a donator to use this command!");
					return;
				}*/
                                
				if (isDonator == 1 && (playerRights == 4 )) {
					c2.sendMessage("<shad=6081134>[Donator]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (isDonator > 0 && (playerRights == 5 )) {
					c2.sendMessage("<shad=6081134>[Super Donator]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (isDonator > 0 && (playerRights == 6 )) {
					c2.sendMessage("<shad=6081134>[Extreme Donator]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerRights == 1) {
					c2.sendMessage("<shad=3781373>[Moderator]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerRights == 2) {
					c2.sendMessage("<shad=16112652>[Administrator]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerRights == 9) {
					c2.sendMessage("<shad=750301>[GFX Designer]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerRights == 8) {
					c2.sendMessage("<shad=750301>[Trial Moderator]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
                                } else if (playerRights == 0) {
					c2.sendMessage("<shad=255>[RuneForge]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				/*Custom Yelltags*/
				} else if (playerName.equalsIgnoreCase("Jay")) {
									c2.sendMessage("<shad=750301>[GFX Designer]</col></shad>"+ Misc.optimizeText(playerName) +": "
									+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=16711780>[Legit Dicer]</shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=15572459>[Captain Dicer]</shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<col=149157262>[Legit Dicer]"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=13872524>[Server Developer]</col></shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=750301>["+customYellTag+"]</col></shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=12852414>["+customYellTag+"]</col></shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=750301>["+customYellTag+"]</col></shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("Earthquake")) {
						c2.sendMessage("<shad=12595455>[Co-Owner]</col></shad>" + Misc.optimizeText(playerName) + ": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				} else if (playerName.equalsIgnoreCase("BatDev")) {
						c2.sendMessage("<shad=12595455>[Owner]</col></shad>"+ Misc.optimizeText(playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
				}
			}
		}
	}
	
	public void setAllInterfaces() {
		if (inWild()) {
			int modY = absY > 6400 ?  absY - 6400 : absY;
					wildLevel = (((modY - 3520) / 8) + 1);
					EarningPotential.checkPotential(this);
					getPA().walkableInterface(197);
					if(Config.SINGLE_AND_MULTI_ZONES) {
						if(inMulti()) {
							getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
						} else {
							getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
						}
					} else {
						getPA().multiWay(-1);
						getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
					}
					getPA().showOption(3, 0, "Attack", 1);
				} else if(inPcBoat()) {
					getPA().walkableInterface(21119);
				} else if(inPcGame()) {
					getPA().walkableInterface(21100);
				} else if (inDuelArena()) {
					getPA().walkableInterface(201);
					if(duelStatus == 5) {
						getPA().showOption(3, 0, "Attack", 1);
					} else {
						getPA().showOption(3, 0, "Challenge", 1);
					}
				} else if(inBarrows()){
					getPA().sendFrame126("Kill Count: "+barrowsKillCount, 4536);
					getPA().walkableInterface(4535);
				} else if(inGWD()){
						getPA().GWKC();
				} else if (inCwGame || inPits) {
					getPA().showOption(3, 0, "Attack", 1);
				} else if (!inDuelArena()) {			getPA().showOption(5, 0,"Follow", 4);			getPA().showOption(3, 0, "null", 1);			getPA().walkableInterface(-1);
				} else if (getPA().inPitsWait()) {
					getPA().showOption(3, 0, "Null", 1);
				} else if (!inCwWait) {
					getPA().sendFrame99(0);
					getPA().walkableInterface(-1);
					getPA().showOption(3, 0, "Null", 1);
				}
			}
	
	
	
}