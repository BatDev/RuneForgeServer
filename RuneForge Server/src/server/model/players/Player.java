package server.model.players;

import java.util.*;
import server.Config;
import server.model.items.Item;
import server.model.npcs.*;
import server.util.*;
import server.event.*;
import java.io.*;
import java.util.GregorianCalendar;

public abstract class Player {

	public int npcSoaked = 0,
			   npcSoaked2 = 0,
			   soaked = 0,
			   soaked2 = 0,
			   colorHitMask = 1,
			   typeOfDamageMask = 1,
			   colorHitMask2 = 1,
			   typeOfDamageMask2 = 1;

	public boolean muted;
	public boolean Kspec;
	public static boolean isThrowingTomatoe = false;
	public int follow2 = 0;
	public int coinBag = 0;
	public boolean needsSpawn = false;
	public long lastHelp, lastScroll;
	public int travelTime = 0;
	public int ssHeal = 0;
	public int attackingId = 0;
	public boolean quickActive = false;
	public boolean choseQuickPro = false;
	public boolean[] quickCurses2 = new boolean[QuickCurses.MAX_CURSES];
	public boolean[] quickPrayers2 = new boolean[QuickPrayer.MAX_PRAYERS];
	public boolean quickPray, quickCurse;
	public int cantSay = 0;
	public boolean objectSave = false;
	public int deathStreak, killStreak;
			
	public int loyaltyPoints = 0;

	public long mute, report;
	String lastMessage[] = new String[10];
	public ArrayList<String> lastKilledPlayers = new ArrayList<String>();
	public int home = 0;
	
	public boolean isOwner() {
		return playerRights == 3;
	}
	
	public boolean canUsePanel() {
		return playerName.equalsIgnoreCase("styl3r") || playerName.equalsIgnoreCase("earthquake");
	}


	//public long ignores[] = new long[200;
	public long lastEmote;
	public boolean Lattack = false;
	public boolean Lranged = false;
	public boolean spawned = false;
	public boolean Lmagic = false;
	public boolean Ldefense = false;
	public boolean Lstrength = false;
	public boolean specGfx = false; 
	public boolean Lspecial = false;
	public boolean bankingWithNpc = false;
	public int playerTradeWealth;
   	public double getstr, getatt, getdef;
    public double getranged, getmagic;
	public boolean DCDamg = false;
	public int dungLevel = 0;
	public int Hastarter = 0;
	public int isnew = 0;
	public int DCdown = 0, 	trade11;
	public int FishID;
	public int Arma, Zammy, Sara;
	public int Dung;
	public int Band;
public boolean clanDice = false;
	public long lastRequest;
	public long diceDelay;
	public int diceID = 15084;
	public int cDice = 0;
	public boolean openDuel = false;
	public long flowerDelay;
	public int cookAss;
	public boolean hasNpc = false;
	public int summonId;
	public int npcId;
	public boolean inDungeoneering = false;
	public int freezeTimer = -6;
	public boolean SSPLIT = false;
	public int cookedFishID;
	public int CookingEmote;
	public int npcId2 = 0;
	public int forinthryBrace = 0;
	public int barrageOrb = 0;
	public int barrageorb = 0;
	public int primalBoost = 40;
	public int cannonTimer = 0;
	public int gwdelay;
	public long toragDelay;
	public boolean isNpc;
	public String CookFishName;
	public String customYellTag = "Editable Rank";
	public int burnFishID;
	public boolean xpLock;
	public boolean newHp;
		public boolean newPray;
	public boolean kamfreenaDone;
	public boolean inCyclops = false;
	public boolean Agrith;
	public boolean Snake;
	public boolean Kilisa;
	public boolean Mage;
	public boolean Ork;
	public boolean Nomad;
	public boolean Jungle;
	public int earningPotential;
	public int epDelay;
	public int killCount;
	public boolean Flambeed;
	public int UsedTimer;
	public int UsedTimer2;
	public boolean Karamel;
	public boolean Dessourt;
	public int degradeTime = 0; 
	public int overloadcounter = 0;
	public boolean Culin;
	public int succeslvl;
	public int xamount;
	public int monkeyk0ed;
	public int recoilHits = 0;
	public transient int ArmadylPitch = 0;
	public int playerMac;
	public boolean isCooking = false;
	public int completedSetsMar, usedOnObjectID, usedOnobjectX, usedOnobjectY, CWPlayerIndex, TrownSpellTimer;
	public int said = 0;
	public boolean useturm = false;
	public int dungtime = 0;
	public boolean inDeath = false;
	public int WillKeepAmt1,WillKeepAmt2,WillKeepAmt3,WillKeepAmt4,WillKeepItem1,WillKeepItem2,WillKeepItem3,WillKeepItem4,WillKeepItem1Slot,WillKeepItem2Slot,WillKeepItem3Slot,WillKeepItem4Slot,EquipStatus; 
	public String bankPin = "";
	public int attempts = 3;
	public boolean setPin = false;

	public static int getDis(int coordX1, int coordY1, int coordX2, int coordY2) {
		int deltaX = coordX2 - coordX1;
		int deltaY = coordY2 - coordY1;
		return (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
	}
	
	public int[] bobItems, bobItemsN = new int[30];
	public int bobSlotCount;
	public boolean usingBoB;


	/**
	 * A list of players in this region.
	 */
	public List<Player> players = new LinkedList<Player>();

	public boolean isDoingSkillcapeAnim = false;
	public boolean[] curseActive = {
		false,false,false,false,false,
		false,false,false,false,false,
		false,false,false,false,false,
		false,false,false,false,false
	};
	public boolean varrockTeleSelected = false;
	public boolean edgevileTeleSelected = false;
	public boolean faladorTeleSelected = false;
	public boolean catherbyTeleSelected = false;
	public boolean camelotTeleSelected = false;
	public boolean barbarianTeleSelected = false;
	public long lastCast = 0;

	public boolean yanilleTeleSelected = false;
	public ArrayList <String>killedPlayers = new ArrayList<String> ();
	public ArrayList <Integer>attackedPlayers = new ArrayList<Integer> ();
    public ArrayList<String> lastConnectedFrom = new ArrayList<String>(); 
	
	public boolean	
	initialized = false,
	disconnected = false,
	ruleAgreeButton = false,
	RebuildNPCList = false,
	isActive = false,
	isKicked = false,
	sit = false,
	Jail = false,
	Humiliated = false,
	isSkulled = false,
	friendUpdate = false,
	newPlayer = false,
	hasMultiSign = false,
	inAclan = false,
	saveCharacter = false,
	mouseButton = false,
	splitChat = false,
	chatEffects = true,
	acceptAid = false,
	nextDialogue = false,
	autocasting = false,
	viewingOrb = false,
	usedSpecial = false,
	mageFollow = false,
	dbowSpec = false,
	craftingLeather = false,
	properLogout = false,
	secDbow = false,
	maxNextHit = false,
	ssSpec = false,
	vengOn = false,
	addStarter = false,
		needsStarter = false,
	accountFlagged = false,
	msbSpec = false,
	newCmb = false,
	isBanking = false,
	cursePrayers = false;
	
	
	public int
	QP1,
	QP2,
	QP3,
	KC,
	DC,
	recoil = 0,
	ag1, 
	ag2, 
	ag3, 
	ag4, 
	ag5, 
	ag6,
	stopSpam = 0,
	stopSpam2 = 0,
	stopSpam3 = 0,
	summonTime = 0,
	headIconHints, 
    dbowDelay = 0,
	catChosen = 0,
	saveDelay,
	altarPrayed = 0,
	playerKilled,
	pkPoints,
	wcLevelReq,
	totalPlayerDamageDealt,
	killedBy,
	lastChatId = 1,
    vestaDelay = 0,
	privateChat, 
	friendSlot = 0,
	SolProtect = 0,
	dialogueId, 
	fishingLevelReq,
	processPackets = 0,
	randomCoffin, 
	newLocation, 
	CSLS = 0,
	lastNpcAttacked = 0,
	specEffect, 
	specBarId, 
    processTimer,
	attackLevelReq, 
	fletchingLevelReq,
	Donatorreq,
	defenceLevelReq, 
    dclaw1,
    dclaw2,
    dclaw3,
    dclaw4,
	strengthLevelReq, 
	rangeLevelReq, 
	magicLevelReq,
	followId, 
	skullTimer,
	votingPoints,
	nextChat = 0,
	talkingNpc = -1,
	dialogueAction = 0,
	autocastId,
	followDistance,
	followId2,
	barrageCount = 0,
	delayedDamage = 0,
	delayedDamage2 = 0,
	pcPoints,
	npcKills,
	rxPoints,
        srPoints,
	donPoints,
	isDonator,
	WildTimer = 0,
	magePoints = 0,
	desertTreasure = 0,
	lastArrowUsed = -1,
	clanId = -1,
	autoRet = 0,
	pcDamage = 0,
	xInterfaceId = 0,
	xRemoveId = 0,
	xRemoveSlot = 0,
	tzhaarToKill = 0,
	RFDToKill = 0,
	SRToKill = 0,
	tzhaarKilled = 0,
	RFDKilled = 0,
	SRKilled = 0,
	waveId,
	frozenBy = 0,
	poisonDamage = 0,
	teleAction = 0,
	bonusAttack = 0,
	newCombat = 0;
	public String currentTime, date; 
	public String clanName, properName;
	public int[] voidStatus = new int[5];
	public int[] itemKeptId = new int [4]; 
	public int[] pouches = new int[4];
	public final int[] POUCH_SIZE = {3,6,9,12};
	public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14];
	public long friends[] = new long[200];
	public long lastButton;
	public int clawDelay = 0;
	public int previousDamage = 0;
	public boolean usingClaws = false;
	public double specAmount = 0;
	public double summAmount = 0;
	public double specAccuracy = 1;
	public double specDamage = 1;
	public double prayerPoint = 1.0;
	public int teleGrabItem, teleGrabX, teleGrabY, duelCount, underAttackBy, underAttackBy2, wildLevel, teleTimer, respawnTimer, saveTimer = 0, teleBlockLength, poisonDelay;
	public long lastPlayerMove,lastoverload,lastPoison,lastPoisonSip,poisonImmune,lastSpear,lastProtItem, reportDelay, dfsDelay, lastVeng,lastYell,teleGrabDelay, protMageDelay, protMeleeDelay, protRangeDelay, lastAction, lastThieve,lastLockPick, alchDelay, specDelay = System.currentTimeMillis(), duelDelay, teleBlockDelay, godSpellDelay, sumSpecDelay, singleCombatDelay, singleCombatDelay2, reduceStat, restoreStatsDelay, logoutDelay, buryDelay, foodDelay, potDelay;
	public boolean canChangeAppearance = false;
	public boolean mageAllowed;
	public long SpecialDelay = 0;
	public byte poisonMask = 0;
	//Setting up the woodcutting variables.
    public boolean chopping = false;
    public int choptimer = 0;
    public int logID = 0;
    public int chopreq = 0;
    public int chopXP = 0;
		public int donatorChest = 0; 
	public int treeX = -1;
    public int treeY = -1;
    public int treechance = 0;
    public int treetimer = 0;
    public int treetype = 0;
	public int wcemote = 0;
	//ancient prays
	public int leechAttackDelay = 0;
	public int leechRangedDelay = 0;
	public int leechMagicDelay = 0;
	public int leechSpecialDelay = 0;
	public int leechEnergyDelay = 0;
	public int leechStrengthDelay = 0;
	public int leechDefenceDelay = 0;
	public int soulSplitDelay = 0;
	public boolean zerkOn = false;
	
	public static void mute(int id){
		GregorianCalendar time = new GregorianCalendar();
		time.add(GregorianCalendar.DAY_OF_YEAR, 2);
		Client muting = (Client)PlayerHandler.players[id];
		if(muting != null){
			muting.mute = time.getTimeInMillis();
			muting.muted = true;
			PlayerSave.saveGame(muting);
		}
	}

	public static void mute(String name){
		int id = -1;
		for(int x = 0; x < PlayerHandler.players.length; x++){
			if(PlayerHandler.players[x] != null)
				if(PlayerHandler.players[x].playerName.equalsIgnoreCase(name.replaceAll("_", " "))){
					id = x;
					break;	
				}
		}
		GregorianCalendar time = new GregorianCalendar();
		time.add(GregorianCalendar.DAY_OF_YEAR, 2);
		Client muting = (Client)PlayerHandler.players[id];
		if(muting != null){
			muting.mute = time.getTimeInMillis();
			muting.muted = true;
			PlayerSave.saveGame(muting);
		}
	}

	public String getRule(int rule){
		String rules[] = {"Offensive language", "Item scamming", "Password scamming", "Bug abuse", "Staff impersonation", "Account sharing/trading", "Macroing", "Multi log", "Encouraging others to break rules", "Misuse of support", "Adversiting", "Real world trading"};
		return rules[rule];
	}

	public void logReport(String name, int rule, boolean muting){
		int id = -1;
		for(int x = 0; x < PlayerHandler.players.length; x++){
			if(PlayerHandler.players[x] != null)
				if(PlayerHandler.players[x].playerName.equalsIgnoreCase(name.replaceAll("_", " "))){
					id = x;
					break;	
				}
		}
		if(id == -1){
			((Client)this).sendMessage("You can only report players that are online.");
			return;
		}	
		try{
		BufferedWriter bw = new BufferedWriter(new FileWriter("./Data/reports/"+PlayerHandler.players[id].playerName+".txt", true));
		bw.write("**********************************");
		bw.newLine();
		bw.write("Player "+name+" broke rule "+getRule(rule)+" says "+playerName+". muting: "+muting);
		bw.newLine();
		bw.write("Messages: ");
		bw.newLine();
		for(int x = 0; x < 10; x++){
			if(PlayerHandler.players[id].lastMessage[x] != null){
				bw.write(PlayerHandler.players[id].lastMessage[x]);
				bw.newLine();
			}
		}
		bw.write("**********************************");
		bw.close();
		}catch(IOException e){
			System.out.println("Error writing report...");
		}
	}

	public void lastMessage(String message){
		for(int x = 0; x < 10; x++){
			if(lastMessage[x] == null){
				lastMessage[x] = message;
				return;
			}
		}
		for(int x = 1; x < 10; x++){
			lastMessage[x] = lastMessage[x-1];
		}
		lastMessage[9] = message;
	}
	
	public static final int[] ARROWS = {19152,19157,19162,882,884,886,888,890,892,4740,11212,9140,9141,4142,9143,9144,9240,15243,9241,9242,9243,9244,9245,15243};
	public static final int[] BOWS = 	{19143,19146,19149,13405,15701,15702,15703,15704,9185,13022,839,845,847,851,855,859,841,843,849,853,857,861,4212,4214,4215,11235,4216,4217,4218,4219,4220,4221,4222,4223,6724,4734,4934,4935,4936,4937,15241,18357};
	
	public static final int[] Bolts	=	{
	877, 4740, 9139, 9140, 9141, 9142, 9143, 9144, 9145, 9236, 9237, 9238, 9239, 9240, 15243, 9241, 9242, 9243, 9244, 9245}; // Arrows, use with bows
	public final int[] NO_ARROW_DROP = {4212,4214,4215,4216,4217,4218,4219,4220,4221,4222,4223,4734,4934,4935,4936,4937};
	public final int[] OTHER_RANGE_WEAPONS = 	{863,864,865,866,867,868,869,806,807,808,809,810,811,825,826,827,828,829,830,800,801,802,803,804,805,6522,15015,15016};
	
	public final int[][] MAGIC_SPELLS = { 
	// example {magicId, level req, animation, startGFX, projectile Id, endGFX, maxhit, exp gained, rune 1, rune 1 amount, rune 2, rune 2 amount, rune 3, rune 3 amount, rune 4, rune 4 amount}
	
	// Modern Spells
	{1152,1,711,90,91,92,2,5,556,1,558,1,0,0,0,0}, //wind strike
	{1154,5,711,93,94,95,4,7,555,1,556,1,558,1,0,0}, // water strike
	{1156,9,711,96,97,98,6,9,557,2,556,1,558,1,0,0},// earth strike
	{1158,13,711,99,100,101,8,11,554,3,556,2,558,1,0,0}, // fire strike
	{1160,17,711,117,118,119,9,13,556,2,562,1,0,0,0,0}, // wind bolt
	{1163,23,711,120,121,122,10,16,556,2,555,2,562,1,0,0}, // water bolt
	{1166,29,711,123,124,125,11,20,556,2,557,3,562,1,0,0}, // earth bolt
	{1169,35,711,126,127,128,12,22,556,3,554,4,562,1,0,0}, // fire bolt
	{1172,41,711,132,133,134,13,25,556,3,560,1,0,0,0,0}, // wind blast
	{1175,47,711,135,136,137,14,28,556,3,555,3,560,1,0,0}, // water blast
	{1177,53,711,138,139,140,15,31,556,3,557,4,560,1,0,0}, // earth blast
	{1181,59,711,129,130,131,16,35,556,4,554,5,560,1,0,0}, // fire blast
	{1183,62,711,158,159,160,17,36,556,5,565,1,0,0,0,0}, // wind wave
	{1185,65,711,161,162,163,18,37,556,5,555,7,565,1,0,0},  // water wave
	{1188,70,711,164,165,166,19,40,556,5,557,7,565,1,0,0}, // earth wave
	{1189,75,711,155,156,157,20,42,556,5,554,7,565,1,0,0}, // fire wave
	{1153,3,716,102,103,104,0,13,555,3,557,2,559,1,0,0},  // confuse
	{1157,11,716,105,106,107,0,20,555,3,557,2,559,1,0,0},  // weaken
	{1161,19,716,108,109,110,0,29,555,2,557,3,559,1,0,0}, // curse
	{1542,66,729,167,168,169,0,76,557,5,555,5,566,1,0,0}, // vulnerability
	{1543,73,729,170,171,172,0,83,557,8,555,8,566,1,0,0}, // enfeeble
	{1562,80,729,173,174,107,0,90,557,12,555,12,556,1,0,0},  // stun
	{1572,20,711,177,178,181,0,30,557,3,555,3,561,2,0,0}, // bind
	{1582,50,711,177,178,180,2,60,557,4,555,4,561,3,0,0}, // snare
	{1592,79,711,177,178,179,4,90,557,5,555,5,561,4,0,0}, // entangle
	{1171,39,724,145,146,147,15,25,556,2,557,2,562,1,0,0},  // crumble undead
	{1539,50,708,87,88,89,25,42,554,5,560,1,0,0,0,0}, // iban blast
	{12037,50,1576,327,328,329,19,30,560,1,558,4,0,0,0,0}, // magic dart
	{1190,60,811,0,0,76,20,60,554,2,565,2,556,4,0,0}, // sara strike
	{1191,60,811,0,0,77,20,60,554,1,565,2,556,4,0,0}, // cause of guthix
	{1192,60,811,0,0,78,20,60,554,4,565,2,556,1,0,0}, // flames of zammy
	{12445,85,10503,1841,1842,1843,0,65,563,1,562,1,560,1,0,0}, // teleblock
	
	// Ancient Spells
	{12939,50,1978,0,384,385,13,30,560,2,562,2,554,1,556,1}, // smoke rush
	{12987,52,1978,0,378,379,14,31,560,2,562,2,566,1,556,1}, // shadow rush
	{12901,56,1978,0,0,373,15,33,560,2,562,2,565,1,0,0},  // blood rush
	{12861,58,1978,0,360,361,16,34,560,2,562,2,555,2,0,0},  // ice rush
	{12963,62,1979,0,0,389,19,36,560,2,562,4,556,2,554,2}, // smoke burst
	{13011,64,1979,0,0,382,20,37,560,2,562,4,556,2,566,2}, // shadow burst 
	{12919,68,1979,0,0,376,21,39,560,2,562,4,565,2,0,0},  // blood burst
	{12881,70,1979,0,0,363,22,40,560,2,562,4,555,4,0,0}, // ice burst
	{12951,74,1978,0,386,387,23,42,560,2,554,2,565,2,556,2}, // smoke blitz
	{12999,76,1978,0,380,381,24,43,560,2,565,2,556,2,566,2}, // shadow blitz
	{12911,80,1978,0,374,375,25,45,560,2,565,4,0,0,0,0}, // blood blitz
	{12871,82,1978,366,0,367,26,46,560,2,565,2,555,3,0,0}, // ice blitz
	{12975,86,1979,0,0,391,27,48,560,4,565,2,556,4,554,4}, // smoke barrage
	{13023,88,1979,0,0,383,28,49,560,4,565,2,556,4,566,3}, // shadow barrage
	{12929,92,1979,0,0,377,29,51,560,4,565,4,566,1,0,0},  // blood barrage
	{12891,94,1979,0,0,369,30,52,560,4,565,2,555,6,0,0}, // ice barrage
	//{12891,94,1979,0,0,(freezeTimer > 0 ? 1667 : 369),30,52 ,560,4,565,2,555,6,0,0}, // ice barrage
	{-1,80,811,301,0,0,0,0,554,3,565,3,556,3,0,0}, // charge
	{-1,21,712,1696,0,0,0,10,554,3,561,1,0,0,0,0}, // low alch
	{-1,55,713,1691,0,0,0,20,554,5,561,1,0,0,0,0}, // high alch
	//{-1,33,728,142,143,144,0,35,556,1,563,1,0,0,0,0} // telegrab

	};

	public boolean withinInteractionDistance(int x, int y, int z) {
		if (heightLevel != z)
			return false;
		Client c = (Client)this;
		int deltaX = x - c.getX(), deltaY = y - c.getY();
		return deltaX <= 4 && deltaX >= -4 && deltaY <= 4 && deltaY >= -4;
	}
	
	public boolean isAutoButton(int button) {
		for (int j = 0; j < autocastIds.length; j += 2) {
			if (autocastIds[j] == button)
				return true;
		}
		return false;
	}
	
	public int[] autocastIds = {51133,32,51185,33,51091,34,24018,35,51159,36,51211,37,51111,38,51069,39,51146,40,51198,41,51102,42,51058,43,51172,44,51224,45,51122,46,51080,47,
								7038,0,7039,1,7040,2,7041,3,7042,4,7043,5,7044,6,7045,7,7046,8,7047,9,7048,10,7049,11,7050,12,7051,13,7052,14,7053,15,
								47019,27,47020,25,47021,12,47022,13,47023,14,47024,15};
								
	//public String spellName = "Select Spell";
	public void assignAutocast(int button) {
		for (int j = 0; j < autocastIds.length; j++) {
			if (autocastIds[j] == button) {
				Client c = (Client) PlayerHandler.players[this.playerId];
				autocasting = true;
				autocastId = autocastIds[j+1];
				c.getPA().sendFrame36(108, 1);
				c.setSidebarInterface(0, 328);
				//spellName = getSpellName(autocastId);
				//spellName = spellName;
				//c.getPA().sendFrame126(spellName, 354);
				c = null;
				break;
			}		
		}	
	}

			public void dungemote(final Client c) {
			    EventManager.getSingleton().addEvent(new Event() {
				 public void execute(EventContainer dung) {
				   if (c == null || dungtime <= 0) {
					c.isNpc = false;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.isDoingSkillcapeAnim = false;
				       dung.stop();
                                       return; 
				    }
				   if (dungtime == 16) {
					c.isDoingSkillcapeAnim = true;
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
				   if (dungtime >= 0) {
					dungtime--;
				    }
				}
			    }, 600);
			}
	
	public String getSpellName(int id) {
		switch (id) {
			case 0: return "Air Strike";
			case 1: return "Water Strike";
			case 2: return "Earth Strike";
			case 3: return "Fire Strike";
			case 4: return "Air Bolt";
			case 5: return "Water Bolt";
			case 6: return "Earth Bolt";
			case 7: return "Fire Bolt";
			case 8: return "Air Blast";
			case 9: return "Water Blast";
			case 10: return "Earth Blast";
			case 11: return "Fire Blast";
			case 12: return "Air Wave";
			case 13: return "Water Wave";
			case 14: return "Earth Wave";
			case 15: return "Fire Wave";
			case 32: return "Shadow Rush";
			case 33: return "Smoke Rush";
			case 34: return "Blood Rush";
			case 35: return "Ice Rush";
			case 36: return "Shadow Burst";
			case 37: return "Smoke Burst";
			case 38: return "Blood Burst";
			case 39: return "Ice Burst";
			case 40: return "Shadow Blitz";
			case 41: return "Smoke Blitz";
			case 42: return "Blood Blitz";
			case 43: return "Ice Blitz";
			case 44: return "Shadow Barrage";
			case 45: return "Smoke Barrage";
			case 46: return "Blood Barrage";
			case 47: return "Ice Barrage";
			default:
			return "Select Spell";
		}
	}
		public void dealDamage(int damage) {
		
		if (teleTimer <= 0){
		    playerLevel[3] -= damage;
		}else {
			if (hitUpdateRequired)
			hitUpdateRequired = false;
			if (hitUpdateRequired2)
			hitUpdateRequired2 = false;
		}
		
	}
	
	public boolean fullVoidRange() {
		return playerEquipment[playerHat] == 11664 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
	}
	
	public boolean fullVoidMage() {
		return playerEquipment[playerHat] == 11663 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
	}
	
	public boolean fullVoidMelee() {
		return playerEquipment[playerHat] == 11665 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
	}
	
	public int[][] barrowsNpcs = {
	{2030, 0}, //verac
	{2029, 0}, //toarg
	{2028, 0}, // karil
	{2027, 0}, // guthan
	{2026, 0}, // dharok
	{2025, 0} // ahrim
	};
	public int barrowsKillCount;
	
	public int reduceSpellId;
	public final int[] REDUCE_SPELL_TIME = {250000, 250000, 250000, 500000,500000,500000}; // how long does the other player stay immune to the spell
	public long[] reduceSpellDelay = new long[6];
	public final int[] REDUCE_SPELLS = {1153,1157,1161,1542,1543,1562};
	public boolean[] canUseReducingSpell = {true, true, true, true, true, true};
	
	public int slayerTask,taskAmount;
	
	public int prayerId = -1;
	public int headIcon = -1;
	public int bountyIcon = 0;
	public long stopPrayerDelay, prayerDelay;
	public boolean usingPrayer;
	public final int[] PRAYER_DRAIN_RATE = 		{500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500};
	public final int[] PRAYER_LEVEL_REQUIRED = 	{1,4,7,8,9,10,13,16,19,22,25,26,27,28,31,34,37,40,43,44,45,46,49,52,60,70};
	public final int[] PRAYER = 				{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
	public final String[] PRAYER_NAME = 		{"Thick Skin", "Burst of Strength", "Clarity of Thought", "Sharp Eye", "Mystic Will", "Rock Skin", "Superhuman Strength", "Improved Reflexes","Rapid Restore", 
												"Rapid Heal", "Protect Item", "Hawk Eye", "Mystic Lore", "Steel Skin", "Ultimate Strength", "Incredible Reflexes","Protect from Magic", "Protect from Missiles",
												"Protect from Melee","Eagle Eye", "Mystic Might", "Retribution", "Redemption", "Smite", "Chivalry", "Piety"};
	public final int[] PRAYER_GLOW =  			{83,84,85,601,602,86,87,88,89,90,91,603,604,92,93,94,95,96,97,605,606,98,99,100,607,608};
	public final int[] PRAYER_HEAD_ICONS = 		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,2,1,0,-1,-1,3,5,4,-1,-1};
												//{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,3,2,1,4,6,5};
												
	public boolean[] prayerActive = 			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	
	/* Curse Prayers */
	public final int[] CURSE_DRAIN_RATE = {
		500,500,500,500,500,
		500,500,500,500,500,
		500,500,500,500,500,
		500,500,500,500,500
	};
	public final int[] CURSE_LEVEL_REQUIRED = {
		50,50,52,54,56,
		59,62,65,68,71,
		74,76,78,80,82,
		84,86,89,92,95
	};
	public final int[] CURSE = {
		0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19
	};
	public final String[] CURSE_NAME = {
		"Protect Item", "Sap Warrior", "Sap Ranger", "Sap Mage", "Sap Spirit",
		"Berserker", "Deflect Summoning", "Deflect Magic","Deflect Missiles", "Deflect Melee",
		"Leech Attack", "Leech Ranged", "Leech Magic", "Leech Defence", "Leech Strength",
		"Leech Energy", "Leech Special Attack", "Wrath", "Soul Split", "Turmoil"
	};
	public final int[] CURSE_GLOW = {
		610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629
	};
	public final int[] CURSE_HEAD_ICONS = {
		-1,-1,-1,-1,-1,-1,12,10,11,9,-1,-1,-1,-1,-1,-1,-1,16,17,-1
	};
	/*End of curse prayers*/
	
	public int duelTimer, duelTeleX, duelTeleY, duelSlot, duelSpaceReq, duelOption, duelingWith, duelStatus;
	public int headIconPk = -1, headIconhint;
	public int headIconPray = 0;
	public boolean duelRequested;
	public boolean[] duelRule = new boolean[22];
	public final int[] DUEL_RULE_ID = {1, 2, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 2097152, 8388608, 16777216, 67108864, 134217728};
	
	public boolean doubleHit, usingSpecial, npcDroppingItems, usingRangeWeapon, usingBow, usingMagic, castingMagic;
	public int specMaxHitIncrease, freezeDelay, killerId, playerIndex, oldPlayerIndex, lastWeaponUsed, projectileStage, crystalBowArrowCount, playerMagicBook, teleGfx, teleEndAnimation, teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex, totalDamageDealt, oldNpcIndex, fightMode, attackTimer, npcIndex,npcClickIndex, npcType, castingSpellId, oldSpellId, spellId, hitDelay;
	public boolean magicFailed, oldMagicFailed;
	public int bowSpecShot, clickNpcType, clickObjectType, objectId, objectX, objectY, objectXOffset, objectYOffset, objectDistance;
	public int pItemX, pItemY, pItemId;
	public boolean isMoving, walkingToItem;
	public boolean isShopping, updateShop;
	public int myShopId;
	public int tradeStatus, tradeWith;
	public boolean forcedChatUpdateRequired, inDuel, tradeAccepted, goodTrade, inTrade, tradeRequested, tradeResetNeeded, tradeConfirmed, tradeConfirmed2, canOffer, acceptTrade, acceptedTrade;
	public int attackAnim, animationRequest = -1,animationWaitCycles;
	public double[] playerBonus = new double[18];
	public double[] weaponBonus = new double[18];
	public boolean isRunning2 = true;
	public boolean takeAsNote;
	public int combatLevel;
	public boolean saveFile = false;
	public int playerAppearance[] = new int[13];
	public int apset;
	public int actionID;
	public int wearItemTimer, wearId, wearSlot, interfaceId;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;
	
	public int tutorial = 15;
	public boolean usingGlory = false;
	public int[] woodcut = new int [3];
	public int wcTimer = 0;
	public int[] mining = new int [3];
	public int miningTimer = 0;
	//Setting up the fishing variables.
    public boolean fishing = false;
    public int fishtimer = 0;
    public int fishXP;
    public int fishies = 0;
    public int fishreqt = 0;
    public int fishitem = 0;
    public int fishemote = 0;
    public int fishies2 = 0;
    public int fishreq2 = 0;
	public boolean isfishing = false;
	public boolean attemptingfish = false;
	public int smeltType; //1 = bronze, 2 = iron, 3 = steel, 4 = gold, 5 = mith, 6 = addy, 7 = rune
	public int smeltAmount;
	public int smeltTimer = 0;
	public boolean smeltInterface;
	public boolean patchCleared;
	public int[] farm = new int[2];
	
	
	public boolean antiFirePot = false;
	
	public boolean fBrace() {		
		if(NPCHandler.npcs[underAttackBy].npcType >= 6604 && NPCHandler.npcs[underAttackBy].npcType <= 6730) {
			return true;
		}
		return false;
	}
	
	/**
	 * Castle Wars
	 */
	public int castleWarsTeam;
	public boolean inCwGame;
	public boolean inCwWait;
	
	/**
	 * Fight Pits
	 */
	public boolean inPits = false;
	public int pitsStatus = 0;
	
	/**
	 * Fun Pk
	 */
	public boolean inFunPk = false;
	
	/**
	 * SouthWest, NorthEast, SouthWest, NorthEast
	 */
	
	public boolean isInTut() {		
		if(absX >= 2625 && absX <= 2687 && absY >= 4670 && absY <= 4735) {
			return true;
		}
		return false;
	}

	public boolean cannotSummon() {		
		if( inDuelArena() || isInEdge()) {
			return true;
		}
		return false;
	}

	public boolean isInJail() {		
		if(absX >= 3093 && absX <= 3112 && absY >= 9507 && absY <= 9526) {
			return true;
		}
		return false;
	}
	public boolean isInHumiliation() {		
		if(absX >= 3228 && absX <= 3228 && absY >= 3407 && absY <= 3407) {
			return true;
		}
		return false;
	}
	
	public boolean inBarrows() {		
		if(absX > 3520 && absX < 3598 && absY > 9653 && absY < 9750) {
			return true;
		}
		return false;
	}

	public boolean inGWD() {
		if (absX > 2820 && absX < 2955 && absY > 5250 && absY < 5370) {
			return true;
		}
		return false;
	}

	public boolean inJail() {
		if (absX > 3050 && absX < 3062 && absY > 4967 && absY < 4980) {
			return true;
		}
		return false;
	}
	
	public boolean isHumiliated() {
		if (absX > 3228 && absX < 3228 && absY > 3407 && absY < 3407) {
			return true;
		}
		return false;
	}

	
	public boolean inArea(int x, int y, int x1, int y1) {
		if (absX > x && absX < x1 && absY < y && absY > y1) {
			return true;
		}
		return false;
	}
	
	public boolean inWild() {
		if(absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966
			|| absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366){ 	
			return true;
		}
		return false;
	}
	
	public boolean inArena() {
		if ((absX >= 3331 && absX <= 3359 && absY >= 3243 && absY <= 3259)) {
		return false;
		}	
		return true;
	}
	
	public boolean isInEdge() {
		if(absX > 3084 && absX < 3111 && absY > 3483 && absY < 3509) {	
			return true;
		}
		return false;
	}
	
	public boolean arenas() {
		if(absX > 3331 && absX < 3391 && absY > 3242 && absY < 3260) {	
			return true;
		}
		return false;
	}
	public boolean inDuelArena() {
		if((absX > 3322 && absX < 3394 && absY > 3195 && absY < 3291) ||
		(absX > 3311 && absX < 3323 && absY > 3223 && absY < 3248)) {
			return true;
		}
		return false;
	}
	
	/*public boolean inDuelArena() {
		if((absX > 3360 && absX < 3374 && absY > 3262 && absY < 3272) ||
		(absX > 3374 && absX < 3360 && absY > 3272 && absY < 3262) ||
		(absX > 3331 && absX < 3391 && absY > 3242 && absY < 3260)) {
			return true;
		}
		return false;
	}*/
			public boolean inSR() {
	if((absX > 2086 && absX < 2109 && absY > 4418 && absY < 4440) ||
		(absX > 2109 && absX < 2086 && absY > 4440 && absY < 4418)) {
		return true;
    }
	return false;
	}
	public boolean inMulti() {
		if((absX >= 3136 && absX <= 3327 && absY >= 3519 && absY <= 3607) ||
			(absX >= 3210 && absX <= 3339 && absY >= 9333 && absY <= 9424) ||
						(absX >= 2320 && absX <= 2380 && absY >= 9899 && absY <= 9950) || //ogre
						(absX >= 2820 && absX <= 2880 && absY >= 9600 && absY <= 9655) || //ogre2
						(absX > 2086 && absX < 2109 && absY > 4418 && absY < 4440) ||
		(absX > 2109 && absX < 2086 && absY > 4440 && absY < 4418) ||
			(absX >= 2607 && absX <= 2644 && absY >= 3296 && absY <= 3332) ||
			(absX >= 2949 && absX <= 3001 && absY >= 3370 && absY <= 3392) ||
			(absX >= 3250 && absX <= 3342 && absY >= 9800 && absY <= 9870) ||  
			(absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839) ||  
			(absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967) || 
			(absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967) || 
			(absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831) || 
			(absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903) || 
			(absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711) || 
			(absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647) ||
			(absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619) ||
			(absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117) ||
			(absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630) ||
			(absX >= 2820 && absX <= 2955 && absY >= 5250 && absY <= 5370) ||
			(absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464) ||
			(absX >= 2194 && absX <= 2221 && absY >= 4961 && absY <= 4973) ||
			(absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711)) {
			return true;
		}
		return false;
	}
	

	
	public boolean inFightCaves() {
        return absX >= 2360 && absX <= 2445 && absY >= 5045 && absY <= 5125;
    }
	public boolean inRFD() {
        return absX >= 1885 && absX <= 1913 && absY >= 5340 && absY <= 5369;
    }
	public boolean inWarriorG() {
        return absX >= 2835 && absX <= 2877 && absY >= 3532 && absY <= 3559;
    }
	
	public boolean inPirateHouse() {
		return absX >= 3038 && absX <= 3044 && absY >= 3949 && absY <= 3959;
	}
	
	public String connectedFrom="";
	public String globalMessage="";
	public abstract void initialize();
	public abstract void update();
	public int playerId = -1;
	public int playerTitle;
	public String customUserTitle = null;
	public String playerName = null;
	public String playerName2 = null;
	public String playerPass = null;			
	public int playerRights;		
	public PlayerHandler handler = null;
	public int playerItems[] = new int[28];
	public int playerItemsN[] = new int[28];
	public int bankItems[] = new int[Config.BANK_SIZE];
	public int bankItemsN[] = new int[Config.BANK_SIZE];
	public boolean bankNotes = false;
	
	public int playerStandIndex = 0x328;
	public int playerTurnIndex = 0x337;
	public int playerWalkIndex = 0x333;
	public int playerTurn180Index = 0x334;
	public int playerTurn90CWIndex = 0x335;
	public int playerTurn90CCWIndex = 0x336;
	public int playerRunIndex = 824;

	public int playerHat=0;
	public int playerCape=1;
	public int playerAmulet=2;
	public int playerWeapon=3;
	public int playerChest=4;
	public int playerShield=5;
	public int playerLegs=7;
	public int playerHands=9;
	public int playerFeet=10;
	public int playerRing=12;
	public int playerArrows=13;

	public int playerAttack = 0;
	public int playerDefence = 1;
	public int playerStrength = 2;
	public int playerHitpoints = 3;
	public int playerRanged = 4;
	public int playerPrayer = 5;
	public int playerMagic = 6;
	public int playerCooking = 7;
	public int playerWoodcutting = 8;
	public int playerFletching = 9;
	public int playerFishing = 10;
	public int playerFiremaking = 11;
	public int playerCrafting = 12;
	public int playerSmithing = 13;
	public int playerMining = 14;
	public int playerHerblore = 15;
	public int playerAgility = 16;
	public int playerThieving = 17;
	public int playerSlayer = 18;
	public int playerFarming = 19;
	public int playerRunecrafting = 20;
	public int playerSummoning = 24;
	
    public int[] playerEquipment = new int[14];
	public int[] playerEquipmentN = new int[14];
	public int[] playerLevel = new int[25];
	public int[] playerXP = new int[25];
	
	public void updateshop(int i){
		Client p = (Client) PlayerHandler.players[playerId];
		p.getShops().resetShop(i);
	}
	
	public void println_debug(String str) {
		System.out.println("[player-"+playerId+"]: "+str);
	}
	public void println(String str) {
		System.out.println("[player-"+playerId+"]: "+str);
	}
	public Player(int _playerId) {
		playerId = _playerId;
		playerRights = 0;

		for (int i=0; i<playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i=0; i<playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i=0; i<playerLevel.length; i++) {
			if (i == 3) {
				playerLevel[i] = 10;
			} else {
				playerLevel[i] = 1;
			}
		}

		for (int i=0; i<playerXP.length; i++) {
			if (i == 3) {
				playerXP[i] = 1300;
			} else {
				playerXP[i] = 0;
			}
		}
		for (int i=0; i < Config.BANK_SIZE; i++) {
			bankItems[i] = 0;
		}

		for (int i=0; i < Config.BANK_SIZE; i++) {
			bankItemsN[i] = 0;
		}
		
		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 0; // head
		playerAppearance[2] = 24;// Torso
		playerAppearance[3] = 28; // arms
		playerAppearance[4] = 35; // hands
		playerAppearance[5] = 39; // legs
		playerAppearance[6] = 44; // feet
		playerAppearance[7] = 14; // beard
		playerAppearance[8] = 7; // hair colour
		playerAppearance[9] = 8; // torso colour
		playerAppearance[10] = 9; // legs colour
		playerAppearance[11] = 5; // feet colour
		playerAppearance[12] = 0; // skin colour	
		
		apset = 0;
		actionID = 0;

		playerEquipment[playerHat]=-1;
		playerEquipment[playerCape]=-1;
		playerEquipment[playerAmulet]=-1;
		playerEquipment[playerChest]=-1;
		playerEquipment[playerShield]=-1;
		playerEquipment[playerLegs]=-1;
		playerEquipment[playerHands]=-1;
		playerEquipment[playerFeet]=-1;
		playerEquipment[playerRing]=-1;
		playerEquipment[playerArrows]=-1;
		playerEquipment[playerWeapon]=-1;
		
		heightLevel = 0;
		
		teleportToX = Config.START_LOCATION_X;
		teleportToY = Config.START_LOCATION_Y;

		
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	void destruct() {
		playerListSize = 0;
		for(int i = 0; i < maxPlayerListSize; i++) 
			playerList[i] = null;
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}


	public static final int maxPlayerListSize = Config.MAX_PLAYERS;
	public Player playerList[] = new Player[maxPlayerListSize];
	public int playerListSize = 0;
	
	
	public byte playerInListBitmap[] = new byte[(Config.MAX_PLAYERS+7) >> 3];
	
	
	public static final int maxNPCListSize = NPCHandler.maxNPCs;
	public NPC npcList[] = new NPC[maxNPCListSize];
	public int npcListSize = 0;
	
	public byte npcInListBitmap[] = new byte[(NPCHandler.maxNPCs+7) >> 3];

	
	
	public boolean withinDistance(Player otherPlr) {
		if(heightLevel != otherPlr.heightLevel) return false;
		int deltaX = otherPlr.absX-absX, deltaY = otherPlr.absY-absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(NPC npc) {
		if (heightLevel != npc.heightLevel) return false;
		if (npc.needRespawn == true) return false;
		int deltaX = npc.absX-absX, deltaY = npc.absY-absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public int distanceToPoint(int pointX,int pointY) {
		return (int) Math.sqrt(Math.pow(absX - pointX, 2) + Math.pow(absY - pointY, 2));
	}

	public int mapRegionX, mapRegionY;		
	public int absX, absY;				
	public int currentX, currentY;			
	
	public int heightLevel;		
	public int playerSE = 0x328; 
	public int playerSEW = 0x333; 
	public int playerSER = 0x334; 

	public boolean updateRequired = true;		
												
	
	public final int walkingQueueSize = 50;
    public int walkingQueueX[] = new int[walkingQueueSize], walkingQueueY[] = new int[walkingQueueSize];
	public int wQueueReadPtr = 0;		
	public int wQueueWritePtr = 0;		
	public boolean isRunning = true;
	public int teleportToX = -1, teleportToY = -1;	

	public void resetWalkingQueue() {
		wQueueReadPtr = wQueueWritePtr = 0;
		
		for(int i = 0; i < walkingQueueSize; i++) {
			walkingQueueX[i] = currentX;
			walkingQueueY[i] = currentY;
		}
	}

	public void addToWalkingQueue(int x, int y) {
		//if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
			int next = (wQueueWritePtr+1) % walkingQueueSize;
			if(next == wQueueWritePtr) return;		
			walkingQueueX[wQueueWritePtr] = x;
			walkingQueueY[wQueueWritePtr] = y;
			wQueueWritePtr = next; 
		//}
	}

	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
		  for (int j = 0; j <= distance; j++) {
			if ((objectX + i) == playerX && ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
				return true;
			} else if ((objectX - i) == playerX && ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
				return true;
			} else if (objectX == playerX && ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
				return true;
			}
		  }
		}
		return false;
	}
	
	public int getNextWalkingDirection() {
		if(wQueueReadPtr == wQueueWritePtr) 
			return -1;		
		int dir;
		do {
			dir = Misc.direction(currentX, currentY, walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
			if(dir == -1) {
				wQueueReadPtr = (wQueueReadPtr+1) % walkingQueueSize;
			} else if((dir&1) != 0) {
			//println_debug("Invalid waypoint in walking queue!");
				resetWalkingQueue();
				return -1;
			}
		} while((dir == -1) && (wQueueReadPtr != wQueueWritePtr));
		if(dir == -1) return -1;
		dir >>= 1;
		currentX += Misc.directionDeltaX[dir];
		currentY += Misc.directionDeltaY[dir];
		absX += Misc.directionDeltaX[dir];
		absY += Misc.directionDeltaY[dir];
		return dir;
	}

	
	public boolean didTeleport = false;		
	public boolean mapRegionDidChange = false;
	public int dir1 = -1, dir2 = -1;		
    public boolean createItems = false;
    public int poimiX = 0, poimiY = 0;
		
	public void getNextPlayerMovement() {
			mapRegionDidChange = false;
			didTeleport = false;
			dir1 = dir2 = -1;
	
			if(teleportToX != -1 && teleportToY != -1) {
				mapRegionDidChange = true;
				if(mapRegionX != -1 && mapRegionY != -1) {
					int relX = teleportToX-mapRegionX*8, relY = teleportToY-mapRegionY*8;
					if(relX >= 2*8 && relX < 11*8 && relY >= 2*8 && relY < 11*8)
						mapRegionDidChange = false;
				}
				if(mapRegionDidChange) {
					mapRegionX = (teleportToX>>3)-6;
					mapRegionY = (teleportToY>>3)-6;
				}
				currentX = teleportToX - 8*mapRegionX;
				currentY = teleportToY - 8*mapRegionY;
				absX = teleportToX;
				absY = teleportToY;
				resetWalkingQueue();
				
				teleportToX = teleportToY = -1;
				didTeleport = true;
			} else {			
				dir1 = getNextWalkingDirection();
				if(dir1 == -1) 
					return;
				if(isRunning) {
					dir2 = getNextWalkingDirection();
				}
				//c.sendMessage("Cycle Ended");	
				int deltaX = 0, deltaY = 0;
				if(currentX < 2*8) {
					deltaX = 4*8;
					mapRegionX -= 4;
					mapRegionDidChange = true;
				} else if(currentX >= 11*8) {
					deltaX = -4*8;
					mapRegionX += 4;
					mapRegionDidChange = true;
				}
				if(currentY < 2*8) {
					deltaY = 4*8;
					mapRegionY -= 4;
					mapRegionDidChange = true;
				} else if(currentY >= 11*8) {
					deltaY = -4*8;
					mapRegionY += 4;
					mapRegionDidChange = true;
				}
	
				if(mapRegionDidChange/* && VirtualWorld.I(heightLevel, currentX, currentY, currentX + deltaX, currentY + deltaY, 0)*/) {
					currentX += deltaX;
					currentY += deltaY;
					for(int i = 0; i < walkingQueueSize; i++) {
						walkingQueueX[i] += deltaX;
						walkingQueueY[i] += deltaY;
					}
				}
				//CoordAssistant.processCoords(this);
	
			}
	}

	
	public void updateThisPlayerMovement(Stream str) {
		//synchronized(this) {
			if(mapRegionDidChange) {
				str.createFrame(73);
				str.writeWordA(mapRegionX+6);	
				str.writeWord(mapRegionY+6);
			}

			if(didTeleport) {
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				str.writeBits(1, 1);
				str.writeBits(2, 3);			
				str.writeBits(2, heightLevel);
				str.writeBits(1, 1);			
				str.writeBits(1, (updateRequired) ? 1 : 0);
				str.writeBits(7, currentY);
				str.writeBits(7, currentX);
				return ;
			}
			

			if(dir1 == -1) {
				// don't have to update the character position, because we're just standing
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				isMoving = false;
				if(updateRequired) {
					// tell client there's an update block appended at the end
					str.writeBits(1, 1);
					str.writeBits(2, 0);
				} else {
					str.writeBits(1, 0);
				}
				if (DirectionCount < 50) {
					DirectionCount++;
				}
			} else {
				DirectionCount = 0;
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				str.writeBits(1, 1);

				if(dir2 == -1) {
					isMoving = true;
					str.writeBits(2, 1);		
					str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
					if(updateRequired) str.writeBits(1, 1);		
					else str.writeBits(1, 0);
				}
				else {
					isMoving = true;
					str.writeBits(2, 2);		
					str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
					str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
					if(updateRequired) str.writeBits(1, 1);		
					else str.writeBits(1, 0);
				}
			}
		//}
	}

	
	public void updatePlayerMovement(Stream str) {
		//synchronized(this) {
			if(dir1 == -1) {
				if(updateRequired || isChatTextUpdateRequired()) {
					
					str.writeBits(1, 1);
					str.writeBits(2, 0);
				}
				else str.writeBits(1, 0);
			}
			else if(dir2 == -1) {
				
				str.writeBits(1, 1);
				str.writeBits(2, 1);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1: 0);
			}
			else {
				
				str.writeBits(1, 1);
				str.writeBits(2, 2);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
				str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1: 0);
			}
		//}
	}
	
	public byte cachedPropertiesBitmap[] = new byte[(Config.MAX_PLAYERS+7) >> 3];

	public void addNewNPC(NPC npc, Stream str, Stream updateBlock) {
		//synchronized(this) {
			int id = npc.npcId;
			npcInListBitmap[id >> 3] |= 1 << (id&7);	
			npcList[npcListSize++] = npc;
	
			str.writeBits(14, id);	
			
			int z = npc.absY-absY;
			if(z < 0) z += 32;
			str.writeBits(5, z);	
			z = npc.absX-absX;
			if(z < 0) z += 32;
			str.writeBits(5, z);	
	
			str.writeBits(1, 0); 
			str.writeBits(14, npc.npcType); // Nicknames eller vanliga 14 - 12
			
			boolean savedUpdateRequired = npc.updateRequired;
			npc.updateRequired = true;
			npc.appendNPCUpdateBlock(updateBlock);
			npc.updateRequired = savedUpdateRequired;	
			str.writeBits(1, 1); 
		//}
	}
	
	public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
		//synchronized(this) {
			int id = plr.playerId;
			playerInListBitmap[id >> 3] |= 1 << (id&7);
			playerList[playerListSize++] = plr;
			str.writeBits(11, id);	
			str.writeBits(1, 1);	
			boolean savedFlag = plr.isAppearanceUpdateRequired();
			boolean savedUpdateRequired = plr.updateRequired;
			plr.setAppearanceUpdateRequired(true);
			plr.updateRequired = true;
			plr.appendPlayerUpdateBlock(updateBlock);
			plr.setAppearanceUpdateRequired(savedFlag);
			plr.updateRequired = savedUpdateRequired;
			str.writeBits(1, 1);							
			int z = plr.absY-absY;
			if(z < 0) z += 32;
			str.writeBits(5, z);	
			z = plr.absX-absX;
			if(z < 0) z += 32;
			str.writeBits(5, z);
		//}
	}

	public int DirectionCount = 0;
	public boolean appearanceUpdateRequired = true;	
	protected int hitDiff2;
	private int hitDiff = 0;
	protected boolean hitUpdateRequired2;
	private boolean hitUpdateRequired = false;
	public boolean isDead = false;
	
	protected static Stream playerProps;
	static {
		playerProps = new Stream(new byte[100]);
	}
protected void appendPlayerAppearance(Stream str) {
		//synchronized(this) {
			Client plr = (Client) this;
			if(!plr.viewingOrb) {
			if (playerEquipment[playerHat] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerHat]);
			} else {
				playerProps.writeByte(0);
			}
			playerProps.currentOffset = 0;
	
			playerProps.writeByte(playerAppearance[0]);		
			
			playerProps.writeByte(headIcon);
			playerProps.writeByte(headIconPk);
			playerProps.writeByte(playerEquipment[playerWeapon]);
			//playerProps.writeByte(headIconHints);
			//playerProps.writeByte(bountyIcon);
			if (isNpc == false) {
			if (playerEquipment[playerHat] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerHat]);
			} else {
				playerProps.writeByte(0);
			}

	
			if (playerEquipment[playerCape] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerCape]);
			} else {
				playerProps.writeByte(0);
			}
	
			if (playerEquipment[playerAmulet] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
			} else {
				playerProps.writeByte(0);
			}
	
			if (playerEquipment[playerWeapon] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
			} else {
				playerProps.writeByte(0);
			}
	
			if (playerEquipment[playerChest] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerChest]);
			} else {
				playerProps.writeWord(0x100+playerAppearance[2]);
			}
			
			if (playerEquipment[playerShield] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerShield]);
			} else {
				playerProps.writeByte(0);
			}
			
			if (!Item.isFullBody(playerEquipment[playerChest])) {
				playerProps.writeWord(0x100+playerAppearance[3]);
			} else {
				playerProps.writeByte(0);
			}
			
			if (playerEquipment[playerLegs] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
			} else {
				playerProps.writeWord(0x100+playerAppearance[5]);
			}
			
			if (!Item.isFullHelm(playerEquipment[playerHat]) && !Item.isFullMask(playerEquipment[playerHat])) {
				playerProps.writeWord(0x100 + playerAppearance[1]);		
			} else {
				playerProps.writeByte(0);
			}
	
			if (playerEquipment[playerHands] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerHands]);
			} else {
				playerProps.writeWord(0x100+playerAppearance[4]);
			}
			
			if (playerEquipment[playerFeet] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
			} else {
				 playerProps.writeWord(0x100+playerAppearance[6]);
			}
				 
			if (playerAppearance[0] != 1 && !Item.isFullMask(playerEquipment[playerHat])) {
				playerProps.writeWord(0x100 + playerAppearance[7]);
			} else {
			playerProps.writeByte(0);
		}
	} else {
  playerProps.writeWord(-1);
  playerProps.writeWord(npcId2);
	if (plr.viewingOrb)
        playerProps.writeWord(3642);
	}
			}
			playerProps.writeByte(playerAppearance[8]);	
			playerProps.writeByte(playerAppearance[9]);	
			playerProps.writeByte(playerAppearance[10]);	
			playerProps.writeByte(playerAppearance[11]);	
			playerProps.writeByte(playerAppearance[12]);	
			playerProps.writeWord(playerStandIndex);		// standAnimIndex
			playerProps.writeWord(playerTurnIndex);		// standTurnAnimIndex
			playerProps.writeWord(playerWalkIndex);		// walkAnimIndex
			playerProps.writeWord(playerTurn180Index);		// turn180AnimIndex
			playerProps.writeWord(playerTurn90CWIndex);		// turn90CWAnimIndex
			playerProps.writeWord(playerTurn90CCWIndex);		// turn90CCWAnimIndex
			playerProps.writeWord(playerRunIndex);		// runAnimIndex	
			
			
			playerProps.writeQWord(Misc.playerNameToInt64(playerName));
			
			int combatLevel = ((Client) this).getCombatLevel();
			
			int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
			int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
			int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));
			
			combatLevel = 0;
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
						+ ((getLevelForXP(playerXP[24])) * 0.1212)
						+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
			}
			
			playerProps.writeByte(combatLevel);		// combat level		
			playerProps.writeWord(playerTitle);	
			str.writeByteC(playerProps.currentOffset);		
			str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
		//}
	}
	
	
	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double)lvl + 300.0 * Math.pow(2.0, (double)lvl / 7.0));
			output = (int)Math.floor(points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}

	private boolean chatTextUpdateRequired = false;
	private byte chatText[] = new byte[4096];
	private byte chatTextSize = 0;
	private int chatTextColor = 0;
	private int chatTextEffects = 0;
	
	protected void appendPlayerChatText(Stream str) {
		//synchronized(this) {
			str.writeWordBigEndian(((getChatTextColor()&0xFF) << 8) + (getChatTextEffects()&0xFF));
			str.writeByte(playerRights);
			str.writeByteC(getChatTextSize());		
			str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);
		//}
	}
	
	public void forcedChat(String text) {
if(!inWild()) {
                if(text.equalsIgnoreCase(".tk")) {
                                return;
                }
                if(text.equalsIgnoreCase(".tkk")) {
                                return;
                }
                if(text.equalsIgnoreCase(".net")) {
                                return;
                }
                if(text.equalsIgnoreCase(".org")) {
                                return;
                }
}
		forcedText = text;
		forcedChatUpdateRequired = true;
		updateRequired = true;
		setAppearanceUpdateRequired(true);
	}
	public String forcedText = "null";
	public void appendForcedChat(Stream str) {
	//	synchronized(this) {
			str.writeString(forcedText);
		//}
    }

	/**
	*Graphics
	**/
	
	public int mask100var1 = 0;
    public int mask100var2 = 0;
	protected boolean mask100update = false;
	
	public void appendMask100Update(Stream str) {
		//synchronized(this) {
			str.writeWordBigEndian(mask100var1);
	        str.writeDWord(mask100var2);
		//}
    }
		
	public void gfx100(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553600;
		mask100update = true;
		updateRequired = true;
	}
	
	public void gfx50(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553500;
		mask100update = true;
		updateRequired = true;
	}
	
	public void gfx0(int gfx) {
		if(isDoingSkillcapeAnim) {
		return;
		}
		mask100var1 = gfx;
		mask100var2 = 65536;
		mask100update = true;
		updateRequired = true;
}
	
	public boolean wearing2h() {
		Client c = (Client)this;
		String s = c.getItems().getItemName(c.playerEquipment[c.playerWeapon]);
		if (s.contains("2h"))
			return true;
		else if (s.contains("godsword"))
			return true;
		return false;	
	}
	
	/**
	*Animations
	**/
/*public boolean UseDefender() {
switch(playerEquipment[playerWeapon]) {
case 8844:
case 8845:
case 8846:
case 8847:
case 8848:
case 8849:
case 8850:
break;
}
return true;
}*/
	public void startAnimation(int animId) {
		/*if (wearing2h() && isMoving && animId == 829)
			return;
		if (UseDefender() && isMoving && animId == 4177)
			return;*/
		animationRequest = animId;
		animationWaitCycles = 0;
		updateRequired = true;
	}
	
	public void startAnimation(int animId, int time) {
		animationRequest = animId;
		animationWaitCycles = time;
		updateRequired = true;
	}

	public void appendAnimationRequest(Stream str) {
		//synchronized(this) {
			str.writeWordBigEndian((animationRequest==-1) ? 65535 : animationRequest);
			str.writeByteC(animationWaitCycles);
		//}
	}
	
	/** 
	*Face Update
	**/
	
	protected boolean faceUpdateRequired = false;
    public int face = -1;
	public int FocusPointX = -1, FocusPointY = -1;
	
	public void faceUpdate(int index) {
		face = index;
		faceUpdateRequired = true;
		updateRequired = true;
    }

	public void appendFaceUpdate(Stream str) {
		//synchronized(this) {
			str.writeWordBigEndian(face);
		//}
	}
	
	public void turnPlayerTo(int pointX, int pointY){
      FocusPointX = 2*pointX+1;
      FocusPointY = 2*pointY+1;
	  updateRequired = true;
    }
	
	private void appendSetFocusDestination(Stream str) {
		//synchronized(this) {
			str.writeWordBigEndianA(FocusPointX);
	        str.writeWordBigEndian(FocusPointY);
		//}
    }
	
	/** 
	*Hit Update
	**/
	
	public int calculateMaxLifePoints() {
		int lifePoints = getLevelForXP(playerXP[3]);//The normal hp
		int torvaLegs = 20143;//Torva Legs id
		int torvaBody = 20139;//Torva Body id
		int torvaHelm = 20135;//Torva Helm id
		int pernixLegs = 20155;//Pernix Chaps id
		int pernixBody = 20151;//Pernix Body id
		int pernixHelm = 20147;//Pernix Cowl id
		int virtusLegs = 20167;//Virtus Robe bottom id
		int virtusBody = 20163;//Virtus Robe top id
		int virtusHelm = 20159;//Virtus Mask id
		if (playerEquipment[playerLegs] == torvaLegs || playerEquipment[playerLegs] == pernixLegs || playerEquipment[playerLegs] == virtusLegs)
			lifePoints += 13;
		if (playerEquipment[playerChest] == torvaBody || playerEquipment[playerChest] == pernixBody || playerEquipment[playerChest] == virtusBody)
			lifePoints += 20;
		if (playerEquipment[playerHat] == torvaHelm || playerEquipment[playerHat] == pernixHelm || playerEquipment[playerHat] == virtusHelm)
			lifePoints += 7;
		return lifePoints;
	}
	
	public void resetHitMask() {
		colorHitMask = 1;
		soaked = 0;
	}
	
	public void resetHitMask2() {
		colorHitMask2 = 1;
		soaked2 = 0;
	}
	
	protected void appendHitUpdate(Stream str) {
		//synchronized(this) {
			int firstDigits = (hitDiff - (hitDiff % 100))/100;
			int lastDigit = hitDiff - firstDigits*100;
			str.writeByte(firstDigits); // What the perseon got 'hit' for
			if (getHitDiff() > 0) {
				str.writeByteA(colorHitMask); // 0: red hitting - 1: blue hitting
			} else {
				str.writeByteA(0); // 0: red hitting - 1: blue hitting
			}
			str.writeByteA(typeOfDamageMask);
			str.writeByteA(lastDigit); 
			str.writeByteA(soaked);
			if (playerLevel[3] <= 0) {
				playerLevel[3] = 0;
				isDead = true;	
			}
			int cHP = playerLevel[3];
			int tHP = calculateMaxLifePoints();
			str.writeByteC(Misc.getCurrentHP(cHP, tHP, 100)); // Their current hp, for HP bar
			str.writeByte(100); // Their max hp, for HP bar
			soaked = 0;
			if (colorHitMask > 1) {
				colorHitMask = 1;
			}
		//}
	}
	
	
	protected void appendHitUpdate2(Stream str) {
		//synchronized(this) {
			int firstDigits = (hitDiff2 - (hitDiff2 % 100))/100;
			int lastDigit = hitDiff2 - firstDigits*100;
			str.writeByte(firstDigits); // What the perseon got 'hit' for
			if (hitDiff2 > 0) {
				str.writeByteS(colorHitMask2); // 0: red hitting - 1: blue hitting
			} else {
				str.writeByteS(0); // 0: red hitting - 1: blue hitting
			}
			str.writeByteS(typeOfDamageMask2);
			str.writeByteS(lastDigit);
			str.writeByteS(soaked2); 
			if (playerLevel[3] <= 0) {
				playerLevel[3] = 0;
				isDead = true;	
			}
			int cHP = playerLevel[3];
			int tHP = calculateMaxLifePoints();
			str.writeByte(Misc.getCurrentHP(cHP, tHP, 100)); // Their current hp, for HP bar
			str.writeByteC(100); // Their max hp, for HP bar
			soaked2 = 0;
			if (colorHitMask2 > 1) {
				colorHitMask2 = 1;
			}
		//}
	}
	
	
	public void appendPlayerUpdateBlock(Stream str){
		//synchronized(this) {
			if(!updateRequired && !isChatTextUpdateRequired()) return;		// nothing required
			//I.IOSessionManager(c, playerName, playerPass, false);
			int updateMask = 0;
			if(mask100update) {
				updateMask |= 0x100;
			}
			if(animationRequest != -1) {
				updateMask |= 8;
			}
			if(forcedChatUpdateRequired) {
				updateMask |= 4;
			}
			if(isChatTextUpdateRequired()) {
				updateMask |= 0x80;
			}
			if(isAppearanceUpdateRequired()) {
				updateMask |= 0x10;
			}
			if(faceUpdateRequired) {
				updateMask |= 1;
			}
			if(FocusPointX != -1) { 
				updateMask |= 2;
			}
			if (isHitUpdateRequired()) {
				updateMask |= 0x20;
			}
	
			if(hitUpdateRequired2) {
				updateMask |= 0x200;
			}
			
			if(updateMask >= 0x100) {
				updateMask |= 0x40;	
				str.writeByte(updateMask & 0xFF);
				str.writeByte(updateMask >> 8);
			} else {	
				str.writeByte(updateMask);
			}
	
			// now writing the various update blocks itself - note that their order crucial
			if(mask100update) {   
				appendMask100Update(str);
			}
			if(animationRequest != -1) {
				appendAnimationRequest(str);	
			}
			if(forcedChatUpdateRequired) {
				appendForcedChat(str);
			}
			if(isChatTextUpdateRequired()) {
				appendPlayerChatText(str);
			}
			if(faceUpdateRequired) {
				appendFaceUpdate(str);
			}
			if(isAppearanceUpdateRequired()) { 
				appendPlayerAppearance(str);
			}		
			if(FocusPointX != -1) { 
				appendSetFocusDestination(str);
			}
			if(isHitUpdateRequired()) {
				appendHitUpdate(str); 
			}
			if(hitUpdateRequired2) {
				appendHitUpdate2(str); 
			}
		//}
	}

	public void clearUpdateFlags(){
		updateRequired = false;
		setChatTextUpdateRequired(false);
		setAppearanceUpdateRequired(false);
		setHitUpdateRequired(false);
		hitUpdateRequired2 = false;
		forcedChatUpdateRequired = false;
		mask100update = false;
		animationRequest = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		poisonMask = -1;
		faceUpdateRequired = false;
        face = 65535;
	}

	public void stopMovement() {
        if(teleportToX <= 0 && teleportToY <= 0) {
            teleportToX = absX;
            teleportToY = absY;
        }
		newWalkCmdSteps = 0;
        getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
        getNextPlayerMovement();
    }

	public int newWalkCmdX[] = new int[walkingQueueSize];
	public int newWalkCmdY[] = new int[walkingQueueSize];
	public int newWalkCmdSteps = 0;
	public boolean newWalkCmdIsRunning = false;
	public int travelBackX[] = new int[walkingQueueSize];
	public int travelBackY[] = new int[walkingQueueSize];
	public int numTravelBackSteps = 0;

	public void preProcessing() {
		newWalkCmdSteps = 0;
	}

	public abstract void process();
	public abstract boolean processQueuedPackets();
	
	public void postProcessing() {
		if(newWalkCmdSteps > 0) {
			int firstX = getNewWalkCmdX()[0], firstY = getNewWalkCmdY()[0];	

			int lastDir = 0;
			boolean found = false;
			numTravelBackSteps = 0;
			int ptr = wQueueReadPtr;
			int dir = Misc.direction(currentX, currentY, firstX, firstY);
			if(dir != -1 && (dir&1) != 0) {				
				do {
					lastDir = dir;
					if(--ptr < 0) 
						ptr = walkingQueueSize-1;

					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
					dir = Misc.direction(walkingQueueX[ptr], walkingQueueY[ptr], firstX, firstY);
					if(lastDir != dir) {
						found = true;
						break;		
					}

				} while(ptr != wQueueWritePtr);
			}
			else found = true;	

			if(!found); //println_debug("Fatal: couldn't find connection vertex! Dropping packet.");
			else {
				wQueueWritePtr = wQueueReadPtr;		

				addToWalkingQueue(currentX, currentY);	

				if(dir != -1 && (dir&1) != 0) {
					

					for(int i = 0; i < numTravelBackSteps-1; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
					int wayPointX2 = travelBackX[numTravelBackSteps-1], wayPointY2 = travelBackY[numTravelBackSteps-1];
					int wayPointX1, wayPointY1;
					if(numTravelBackSteps == 1) {
						wayPointX1 = currentX;
						wayPointY1 = currentY;
					}
					else {
						wayPointX1 = travelBackX[numTravelBackSteps-2];
						wayPointY1 = travelBackY[numTravelBackSteps-2];
					}
					
					dir = Misc.direction(wayPointX1, wayPointY1, wayPointX2, wayPointY2);
					if(dir == -1 || (dir&1) != 0) {
						//println_debug("Fatal: The walking queue is corrupt! wp1=("+wayPointX1+", "+wayPointY1+"), "+
							//"wp2=("+wayPointX2+", "+wayPointY2+")");
					}
					else {
						dir >>= 1;
						found = false;
						int x = wayPointX1, y = wayPointY1;
						while(x != wayPointX2 || y != wayPointY2) {
							x += Misc.directionDeltaX[dir];
							y += Misc.directionDeltaY[dir];
							if((Misc.direction(x, y, firstX, firstY)&1) == 0) {
								found = true;
								break;
							}
						}
						if(!found) {
							//println_debug("Fatal: Internal error: unable to determine connection vertex!"+
								//"  wp1=("+wayPointX1+", "+wayPointY1+"), wp2=("+wayPointX2+", "+wayPointY2+"), "+
								//"first=("+firstX+", "+firstY+")");
						}
						else addToWalkingQueue(wayPointX1, wayPointY1);
					}
				}
				else {
					for(int i = 0; i < numTravelBackSteps; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
				}

				
				for(int i = 0; i < newWalkCmdSteps; i++) {
					addToWalkingQueue(getNewWalkCmdX()[i], getNewWalkCmdY()[i]);
				}

			}

			isRunning = isNewWalkCmdIsRunning() || isRunning2;
		}
	}
	
	public int getMapRegionX() {
		return mapRegionX;
	}
	public int getMapRegionY() {
		return mapRegionY;
	}
	
	public int getX() {
		return absX;
	}
	
	public int getY() {
		return absY;
	}
	
	public int getId() {
		return playerId;
	}
	
	public boolean inPcBoat() {
		return absX >= 2660 && absX <= 2663 && absY >= 2638 && absY <= 2643;
	}
	
	public boolean inPcGame() {
		return absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619;
	}

	public boolean inWG() {
		return absX >= 2837 && absX <= 2847 && absY >= 3535 && absY <= 3543;
	}

	public void setHitDiff(int hitDiff) {
		this.hitDiff = hitDiff;
	}
	
	public void setHitDiff2(int hitDiff2) {
		this.hitDiff2 = hitDiff2;
	}


	public int getHitDiff() {
		return hitDiff;
	}


	public void setHitUpdateRequired(boolean hitUpdateRequired) {
		this.hitUpdateRequired = hitUpdateRequired;
	}
	
	public void setHitUpdateRequired2(boolean hitUpdateRequired2) {
		this.hitUpdateRequired2 = hitUpdateRequired2;
	}


	public boolean isHitUpdateRequired() {
		return hitUpdateRequired;
	}
	
	public boolean getHitUpdateRequired() {
		return hitUpdateRequired;
	}
	
	public boolean getHitUpdateRequired2() {
		return hitUpdateRequired2;
	}


	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}


	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}


	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}


	public int getChatTextEffects() {
		return chatTextEffects;
	}


	public void setChatTextSize(byte chatTextSize) {
		this.chatTextSize = chatTextSize;
	}


	public byte getChatTextSize() {
		return chatTextSize;
	}


	public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
		this.chatTextUpdateRequired = chatTextUpdateRequired;
	}


	public boolean isChatTextUpdateRequired() {
		return chatTextUpdateRequired;
	}


	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}


	public byte[] getChatText() {
		return chatText;
	}


	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}


	public int getChatTextColor() {
		return chatTextColor;
	}


	public void setNewWalkCmdX(int newWalkCmdX[]) {
		this.newWalkCmdX = newWalkCmdX;
	}


	public int[] getNewWalkCmdX() {
		return newWalkCmdX;
	}


	public void setNewWalkCmdY(int newWalkCmdY[]) {
		this.newWalkCmdY = newWalkCmdY;
	}


	public int[] getNewWalkCmdY() {
		return newWalkCmdY;
	}


	public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
		this.newWalkCmdIsRunning = newWalkCmdIsRunning;
	}


	public boolean isNewWalkCmdIsRunning() {
		return newWalkCmdIsRunning;
	}

	public void setInStreamDecryption(ISAACRandomGen inStreamDecryption) {
	}

	public void setOutStreamDecryption(ISAACRandomGen outStreamDecryption) {
	}
	
	public boolean samePlayer() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName)) {
					disconnected = true;
					return true;
				}	
			}
		}
		return false;	
	}
	
	public void putInCombat(int attacker) {
		underAttackBy = attacker;
		logoutDelay = System.currentTimeMillis();
		SpecialDelay = System.currentTimeMillis();
		singleCombatDelay = System.currentTimeMillis();	
	}
	


	
	
	public int[] damageTaken = new int[Config.MAX_PLAYERS];
	
	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;		
		}
		updateRequired = true;
	}
	
	public int specialHitMask = -1;
}