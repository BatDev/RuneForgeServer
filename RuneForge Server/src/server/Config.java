package server;


public class Config {

	public static final boolean SERVER_DEBUG = false;//needs to be false for Real world to work
	
	public static final String SERVER_NAME = "RuneForge";
	public static final String WELCOME_MESSAGE = "Welcome to RuneForge.";
	public static final String FORUMS = "";
	
	public static final int CLIENT_VERSION = 1;
	
	public static int MESSAGE_DELAY = 6000;
	public static final int ITEM_LIMIT = 30000; // item id limit, different clients have more items like silab which goes past 15000
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;
	public static final int BANK_SIZE = 352;
	public static final int MAX_PLAYERS = 1024;

	public static final int CONNECTION_DELAY = 100; // how long one ip can keep connecting
	public static final int IPS_ALLOWED = 2; // how many ips are allowed
		
	public static final boolean WORLD_LIST_FIX = false; // change to true if you want to stop that world--8 thing, but it can cause the screen to freeze on silabsoft client
	
	public static final int[] ITEM_SELLABLE 		=	{18786,19784,22401,18355,19780,11694,11696,15486,13876,13864,13867,1038,1040,1042,1044,1046,1048,1050,1052,1053,1055,1057,15241,20139,20135,20143,20147,20151,20155,20153,20163,20167,2577,2581,10551,8839,8840,8842,1164,1163,19786,19785,11665}; // what items can't be sold in any store
	public static final int[] ITEM_TRADEABLE 		= 	{19669,15039,15040,15041,15042,15043,15044,9948,9949,9950,8850,10551,8839,8840,8842,11663,11664,11665,3842,3844,3840,8844,8845,8846,8847,8848,8849,8850,10551,6570,7462,7461,7460,7459,7458,7457,7456,7455,7454,7453,8839,8840,8842,11663,11664,11665,10499,
														9748,9754,9751,9769,9757,9760,9763,9802,9808,9784,9799,9805,9781,9796,9793,9775,9772,9778,9787,9811,9766,
														9749,9755,9752,9770,9758,9761,9764,9803,9809,9785,9800,9806,9782,9797,9794,9776,9773,9779,9788,9812,9767,
														9747,9753,9750,9768,9756,9759,9762,9801,9807,9783,9798,9804,9780,9795,9792,9774,9771,9777,9786,9810,9765,4202,6465,12158,12159,12160,12161,12157,12156,12162,12163,12164,12165,12166,12167,19785,19786,12169,12170,12171,20072}; // what items can't be traded or staked
	public static final int[] UNDROPPABLE_ITEMS 	= 	{15039,15040,15041,15042,15043,15044,18349,18351,18353,14484,13740,13742,13744,13738,18335,13873,13870,13861,13858,13902,13899,13893,13887,13890,13884,13896,15220,15019,15018,15020}; // what items can't be dropped
	
	public static final int[] FUN_WEAPONS	=	{2460,2461,2462,2463,2464,2465,2466,2467,2468,2469,2470,2471,2471,2473,2474,2475,2476,2477,}; // fun weapons for dueling

	 public static final int[] WEBS_CANNOT = { 9185, 839, 845, 847, 851, 855,
			859, 841, 843, 849, 853, 857, 4212, 4214, 4215, 11235, 4216,
			4217, 4218, 4219, 4220, 4221, 4222, 4223, 6724, 4734, 4934, 4935,
			4936, 4937, 1379, 1381, 1383, 1385, 1387, 1389 };
	
	public static final boolean ADMIN_CAN_TRADE = false; //can admins trade?
	public static final boolean ADMIN_CAN_SPAWN = true; //can admins spawn?
	public static final boolean ADMIN_CAN_SELL_ITEMS = false; // can admins sell items?
	public static final boolean ADMIN_DROP_ITEMS = false; // can admin drop items?

	public static final int[] CAT_ITEMS 	= 	{1555,1556,1557,1558,1559,1560,1561,1562,1563,1564,1565,7585,7584,1157,3589,}; // TO MAKE CATS NOT BE ABLE TO DROP WHEN UR HAVEING ONE SUMMONED
	
	public static final int START_LOCATION_X = 3182; // start here
	public static final int START_LOCATION_Y = 3422;
	public static final int RESPAWN_X = 2606; // when dead respawn here
	public static final int RESPAWN_Y = 3093;
	public static final int DUELING_RESPAWN_X = 3362; // when dead in duel area spawn here
	public static final int DUELING_RESPAWN_Y = 3263;
	public static final int RANDOM_DUELING_RESPAWN = 5; // random coords
	
	public static final int NO_TELEPORT_WILD_LEVEL = 20; // level you can't tele on and above
	public static final int SKULL_TIMER = 1200; // how long does the skull last? seconds x 2
	public static final int TELEBLOCK_DELAY = 2000; // how long does teleblock last for.
	public static final boolean SINGLE_AND_MULTI_ZONES = true; // multi and single zones?
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true; // wildy levels and combat level differences matters
	
	public static final boolean itemRequirements = true; // attack, def, str, range or magic levels required to wield weapons or wear items?
		
	public static final int MELEE_EXP_RATE = 1500; // damage * exp rate
	public static final int RANGE_EXP_RATE = 1500;
	public static final int MAGIC_EXP_RATE = 1500;
	public static double SERVER_EXP_BONUS = 1;
	
	public static final int INCREASE_SPECIAL_AMOUNT = 17500; // how fast your special bar refills
	public static final int INCREASE_SPECIAL_AMOUNT_WITH_RING = 15000; // how fast your special bar refills with ring
	public static final int INCREASE_SUMMON_AMOUNT = 60000; //30sec // how fast your summon special refills
	public static final boolean PRAYER_POINTS_REQUIRED = true; // you need prayer points to use prayer
	public static final boolean PRAYER_LEVEL_REQUIRED = true; // need prayer level to use different prayers
	public static final boolean MAGIC_LEVEL_REQUIRED = true; // need magic level to cast spell
	public static final int GOD_SPELL_CHARGE = 300000; // how long does god spell charge last?
	public static final boolean RUNES_REQUIRED = true; // magic rune required?
	public static final boolean CORRECT_ARROWS = true; // correct arrows for bows?
	public static final boolean CRYSTAL_BOW_DEGRADES = false; // magic rune required?
	public static boolean doubleEXPWeekend = false;
	public static final int SAVE_TIMER = 30; // save every 1 minute
	public static final int NPC_RANDOM_WALK_DISTANCE = 5; // the square created , 3x3 so npc can't move out of that box when randomly walking
	public static final int NPC_FOLLOW_DISTANCE = 10; // how far can the npc follow you from it's spawn point, 													
	public static final int[] UNDEAD_NPCS = {90,91,92,93,94,103,104,73,74,75,76,77}; // undead npcs

	/**
	 * Barrows Reward
	 */
	
	
	/**
	 * Glory
	 */
	public static final int EDGEVILLE_X = 3087;
	public static final int EDGEVILLE_Y = 3492;
	public static final String EDGEVILLE = "";
	public static final int AL_KHARID_X = 3293;
	public static final int AL_KHARID_Y = 3174;
	public static final String AL_KHARID = "";
	public static final int KARAMJA_X = 3087;
	public static final int KARAMJA_Y = 3500;
	public static final String KARAMJA = "";
	public static final int MAGEBANK_X = 2538;
	public static final int MAGEBANK_Y = 4716;
	public static final String MAGEBANK = "";
	
	/**
	* Teleport Spells
	**/
	// modern
	public static final int VARROCK_X = 3087;
	public static final int VARROCK_Y = 3500;
	public static final String VARROCK = "";
	public static final int LUMBY_X = 3222;
	public static final int LUMBY_Y = 3218;
	public static final String LUMBY = "";
	public static final int FALADOR_X = 2964;
	public static final int FALADOR_Y = 3378;
	public static final String FALADOR = "";
	public static final int CAMELOT_X = 2757;
	public static final int CAMELOT_Y = 3477;
	public static final String CAMELOT = "";
	public static final int ARDOUGNE_X = 2662;
	public static final int ARDOUGNE_Y = 3305;
	public static final String ARDOUGNE = "";
	public static final int WATCHTOWER_X = 3087;
	public static final int WATCHTOWER_Y = 3500;
	public static final String WATCHTOWER = "";
	public static final int TROLLHEIM_X = 3243;
	public static final int TROLLHEIM_Y = 3513;
	public static final String TROLLHEIM = "";
	
	// ancient
	
	public static final int PADDEWWA_X = 3098;
	public static final int PADDEWWA_Y = 9884;
	
	public static final int SENNTISTEN_X = 3322;
	public static final int SENNTISTEN_Y = 3336;

    public static final int KHARYRLL_X = 3492;
	public static final int KHARYRLL_Y = 3471;

	public static final int LASSAR_X = 3006;
	public static final int LASSAR_Y = 3471;
	
	public static final int DAREEYAK_X = 3161;
	public static final int DAREEYAK_Y = 3671;
	
	public static final int CARRALLANGAR_X = 3156;
	public static final int CARRALLANGAR_Y = 3666;
	
	public static final int ANNAKARL_X = 3288;
	public static final int ANNAKARL_Y = 3886;
	
	public static final int GHORROCK_X = 2977;
	public static final int GHORROCK_Y = 3873;
 
	public static final int TIMEOUT = 20;
	public static final int CYCLE_TIME = 600;
	public static final int BUFFER_SIZE = 10000;
	public static final int MAX_PROCESS_PACKETS = 10;
	
	/**
	 * Slayer Variables
	 */
	public static final int[][] SLAYER_TASKS = {{1,87,90,4,5}, //low tasks
												{6,7,8,9,10}, //med tasks
												{11,12,13,14,15}, //high tasks
												{1,1,15,20,25}, //low reqs
												{30,35,40,45,50}, //med reqs
												{60,75,80,85,90}}; //high reqs
	
	/**
	* Skill Experience Multipliers
	*/	
	public static final int WOODCUTTING_EXPERIENCE = 50;
	public static final int MINING_EXPERIENCE = 50;
	public static final int SMITHING_EXPERIENCE = 50;
	public static final int FARMING_EXPERIENCE = 50;
	public static final int FIREMAKING_EXPERIENCE = 50;
	public static final int HERBLORE_EXPERIENCE = 50;
	public static final int FISHING_EXPERIENCE = 110;
	public static final int AGILITY_EXPERIENCE = 50;
	public static final int PRAYER_EXPERIENCE = 150;
	public static final int RUNECRAFTING_EXPERIENCE = 50;
	public static final int CRAFTING_EXPERIENCE = 50;
	public static final int THIEVING_EXPERIENCE = 80;
	public static final int SLAYER_EXPERIENCE = 100;
	public static final int COOKING_EXPERIENCE = 50;
	public static final int FLETCHING_EXPERIENCE = 50;
}
