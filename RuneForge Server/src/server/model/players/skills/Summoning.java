package server.model.players.skills;

import server.model.npcs.NPCHandler;
import server.model.players.*;
import server.Server;

/**
* @Author I'm A Jerk
* @Author Kataang
*/

public class Summoning {
	
	Client c; 

	public Summoning(Client c) {
		this.c = c;
	}
	
	
	//pouchId, nonWildNpcId, wildNpcId, SummonName, SummonTime, maxhit, attack, defence, pouchreq, bobslotcount, summonPoints
	public final static Object[][] SUMMONING_NPC_DATA = {
		{12047, 6829, 6830, "Spirit wolf", 720, 4, 10, 80, 1, 0, 1},
		{12043, 6825, 6826, "Dreadfowl", 480, 6, 10, 80, 4, 0, 1},
		{12059, 6841, 6842, "Spirit spider", 1800, 6, 10, 80, 10, 0, 2},
		{12019, 6806, 6807, "Thorny snail", 1920, 5, 20, 80, 13, 3, 2},
		{12009, 6796, 6797, "Granite crab", 2160, 8, 20, 80, 16, 0, 2},
		{12778, 7331, 7332, "Spirit mosquito", 1440, 8, 20, 80, 17, 0, 4},
		{12049, 6831, 6832, "Desert wyrm", 2280, 8, 20, 80, 18, 0, 1},
		{12055, 6837, 3838, "Spirit scorpion", 2040, 8, 20, 80, 19, 0, 2},
		{12808, 7361, 7362, "Spirit Tz-Kih", 2160, 8, 20, 80, 22, 0, 3},
		{12067, 6847, 6848, "Albino rat", 2640, 8, 20, 80, 23, 0, 3},
		{12063, 6994, 6995, "Spirit kalphite", 2640, 10, 20, 80, 25, 6, 3},
		{12091, 6871, 6872, "Compost mound", 2880, 10, 20, 80, 28, 0, 6, 6},
		{12802, 7353, 7354, "Giant chinchompa", 3720, 11, 20, 80, 29, 0, 1},
		{12053, 6835, 6836, "Vampire bat", 3960, 12, 20, 80, 31, 0, 4},
		{12065, 6845, 6846, "Honey badger", 3000, 14, 40, 80, 32, 0, 4},
		{12021, 6808, 6808, "Beaver", 3240, 12, 40, 80, 33, 0, 4},
		{12818, 7370, 7371, "Void ravager", 3240, 11, 40, 80, 34, 0, 4},
		{12814, 7367, 7368, "Void shifter", 11280, 11, 40, 80, 34, 0, 4},
		{12798, 7351, 7352, "Void torcher", 11280, 11, 40, 80, 34, 0, 4},
		{12780, 7333, 7334, "Void spinner", 3240, 11, 40, 80, 34, 0, 4},
		{12073, 6853, 6854, "Bronze minotaur", 3600, 7, 40, 80, 36, 0, 9},
		{12087, 6867, 6868, "Bull ant", 3600, 9, 40, 80, 40, 9, 5},
		{12071, 6851, 6851, "Macaw", 3720, 8, 40, 80, 41, 0, 5},
		{12051, 6833, 6834, "Evil turnip", 3600, 7, 40, 80, 42, 0, 5},
		{12075, 6855, 6856, "Iron minotaur", 4440, 8, 40, 80, 46, 0, 9},
		{12816, 7377, 7378, "Pyrelord", 3840, 8, 40, 80, 46, 0, 4},
		{12041, 6824, 6824, "Magpie", 4080, 13, 40, 80, 47, 0, 5},
		{12061, 6843, 6844, "Bloated leech", 4080, 9, 40, 80, 49, 0, 5},
		{12007, 6794, 6795, "Spirit terrorbird", 3600, 11, 60, 80, 52, 12, 6},
		{12035, 6818, 6819, "Abyssal parasite", 3600, 13, 60, 80, 54, 0, 6},
		{12027, 6992, 6993, "Spirit jelly", 3600, 15, 60, 80, 55, 0, 6},
		{12077, 6857, 6858, "Steel minotaur", 3600, 11, 60, 80, 56, 0, 9},
		{12531, 6991, 6991, "Ibis", 3600, 11, 60, 80, 56, 0, 6},
		{12810, 7363, 7364, "Spirit graahk", 3600, 20, 60, 80, 57, 0, 6},
		{12812, 7365, 7366, "Spirit kyatt", 3600, 20, 60, 80, 57, 0, 6},
		{12784, 7337, 7338, "Spirit larupia", 3600, 20, 60, 80, 57, 0, 6},
		{12023, 6809, 6810, "Karamthulhu overlord", 3600, 11, 60, 80, 58, 0, 6},
		{12085, 6865, 6866, "Smoke devil", 3600, 11, 60, 80, 61, 0, 7},
		{12037, 6820, 6821, "Abyssal lurker", 3600, 11, 60, 80, 62, 0, 7},
		{12015, 6802, 6803, "Spirit cobra", 3600, 14, 60, 80, 63, 0, 7},
		{12045, 6827, 6828, "Stranger plant", 3600, 18, 60, 80, 64, 0, 7},
		{12079, 6859, 6860, "Mithril minotaur", 3600, 20, 60, 80, 66, 0, 7},
		{12123, 6889, 6890, "Barker toad", 3600, 20, 60, 80, 66, 0, 7},
		{12031, 6815, 6816, "War tortoise", 3600, 21, 60, 80, 67, 18, 7},
		{12029, 6813, 6814, "Bunyip", 3600, 17, 60, 80, 68, 0, 7},
		{12033, 6817, 6817, "Fruit bat", 3600, 11, 60, 80, 69, 0, 7},
		{12820, 7372, 7373, "Ravenous locust", 3600, 11, 60, 80, 70, 0, 6},
		{12057, 6839, 6840, "Arctic bear", 3600, 15, 60, 80, 71, 0, 8},
		{14623, 8575, 8576, "Phoenix", 3600, 13, 60, 80, 72, 0, 8},
		{12792, 7345, 7346, "Obsidian golem", 3600, 25, 80, 80, 73, 0, 8},
		{12069, 6849, 6850, "Granite lobster", 3600, 11, 60, 80, 74, 0, 8},
		{12011, 6798, 6799, "Praying mantis", 3600, 11, 60, 80, 75, 0, 8},
		{12081, 6861, 6862, "Adamant minotaur", 3600, 22, 60, 80, 76, 0, 8},
		{12782, 7335, 7336, "Forge regent", 3600, 24, 60, 80, 76, 0, 8},
		{12794, 7347, 7348, "Talon beast", 3600, 24, 60, 80, 77, 0, 8},
		{12013, 6800, 6801, "Giant ent", 3600, 11, 60, 80, 78, 0, 8},
		{12802, 7355, 7356, "Fire titan", 3600, 26, 60, 80, 79, 0, 9},
		{12804, 7357, 7358, "Moss titan", 3600, 26, 60, 80, 79, 0, 9},
		{12806, 7359, 7360, "Ice titan", 3600, 26, 60, 80, 79, 0, 9},
		{12025, 6811, 6812, "Hydra", 3600, 28, 60, 80, 80, 0, 9},
		{12017, 6804, 6805, "Spirit daggannoth", 3600, 30, 60, 80, 83, 0, 9},
		{12788, 7341, 7342, "Lava titan", 3600, 30, 60, 80, 83, 0, 9},
		{12776, 7329, 7330, "Swamp titan", 3600, 31, 60, 80, 85, 0, 9},
		{12083, 6863, 6864, "Rune minotaur", 3600, 32, 60, 80, 86, 0, 9},
		{12039, 6822, 6823, "Unicorn stallion", 3600, 33, 60, 80, 88, 0, 9},
		{12786, 7339, 7340, "Geyser titan", 3600, 34, 60, 80, 89, 0, 9},
		{12089, 6869, 6870, "Wolpertinger", 3600, 35, 60, 80, 92, 0, 10},
		{12796, 7349, 7350, "Abyssal titan", 3600, 36, 60, 80, 93, 0, 10},
		{12822, 7375, 7376, "Iron titan", 3600, 37, 60, 80, 95, 0, 10},
		{12093, 6873, 6874, "Pack Yack", 3600, 38, 60, 80, 96, 30, 10},
		{12790, 7343, 7344, "Steel titan", 3600, 39, 90, 80, 99, 0, 10},
		};

		private static final String[][] summoningPouchData = {
			// Pouch id, pouch charm, item1, Shardamount, LVL, Spec scroll
			{ "Spirit wolf pouch", "Gold Charm", "Wolf bones", "7", "1", "Howl scroll" },
			{ "Dreadfowl pouch", "Gold Charm", "Raw chicken", "8", "4", "Dreadfowl strike scroll" },
			{ "Spirit spider pouch", "Gold Charm", "Spider carcass", "8", "10", "Egg spawn scroll" },
			{ "Thorny Snail pouch", "Gold Charm", "Thin snail", "9", "13", "Slime spray scroll" },
			{ "Granite Crab pouch", "Gold Charm", "Iron ore", "7", "16", "Stony shell scroll" },
			{ "Mosquito pouch", "Gold Charm", "Proboscis", "1", "17", "Pester scroll" },
			{ "Desert wyrm pouch", "Green Charm", "Bucket of sand", "45", "18", "Electric lash scroll" },
			{ "Spirit Scorpion pouch", "Crimson Charm", "Bronze claws", "57", "19", "Venom shot scroll" },
			{ "Spirit tz-kih pouch", "crimson charm", "Obsidian charm", "64", "22", "Fireball assault scroll" },
			{ "Albino rat pouch", "Blue Charm", "Raw rat meat", "75", "23", "Cheese feast scroll" },
			{ "Spirit kalphite pouch", "blue Charm", "potato cactus", "51", "25", "Sandstorm scroll" },
			{ "Compost mound pouch", "Green charm", "compost", "47", "28", "Generate compost scroll" },
			{ "Giant chinchompa pouch", "Blue Charm", "Chinchompa", "84", "29", "Explode scroll" },
			{ "Vampire bat pouch", "Crimson Charm", "Vampire dust", "81", "31", "Vampire touch scroll" },
			{ "Honey badger pouch", "Crimson Charm", "Honeycomb", "84", "32", "Insane ferocity scroll" },
			{ "Beaver pouch", "Green Charm", "Willow logs", "72", "33", "Multichop scroll" },
			{ "Void ravager pouch", "green Charm", "Ravager Charm", "74", "34", "Call to arms scroll" },
			{ "Void shifter pouch", "blue charm", "Shifter charm", "74", "34", "Call to arms scroll" },
			{ "void spinner pouch", "blue Charm", "spinner Charm", "74", "34", "Call to arms scroll" },
			{ "Void Torcher pouch", "blue Charm", "Torcher Charm", "74", "34", "Call to arms scroll" },
			{ "Bronze minotaur pouch", "Blue Charm", "Bronze bar", "102", "36", "Bronze bull rush scroll" },
			{ "Bull ant pouch", "gold Charm", "Marigolds", "11", "40", "Unburden scroll" },
			{ "Macaw pouch", "green Charm", "Clean guam", "78", "41", "Herbcall scroll" },
			{ "Evil turnip pouch", "crimson Charm", "Carved turnip", "104", "42", "Evil flames scroll" },
			{ "Iron minotaur pouch", "Blue Charm", "Iron bar", "125", "46", "Iron bull rush scroll" },
			{ "Pyrelord pouch", "Crimson Charm", "Tinderbox", "111", "46", "Immense heat scroll" },
			{ "Magpie pouch", "green Charm", "Gold ring", "88", "47", "Thieving fingers scroll" },
			{ "Bloated leech pouch", "Crimson Charm", "Raw beef", "117", "49", "Blood drain scroll" },
			{ "Spirit terrorbird pouch", "Gold Charm", "Raw bird meat", "12", "52", "Tireless run scroll" },
			{ "Abyssal parasite pouch", "green Charm", "Abyssal charm", "106", "54", "Abyssal drain scroll" },
			{ "Spirit jelly pouch", "blue Charm", "Jug of water", "151", "55", "Dissolve scroll" },
			{ "Steel minotaur pouch", "blue Charm", "steel bar", "141", "56", "Fish rain scroll" },
			{ "Ibis pouch", "green Charm", "Harpoon", "109", "56", "Steel bull rush scroll" },
			{ "Spirit Graahk pouch", "blue Charm", "graahk fur", "154", "57", "Ambush scroll" },
			{ "Spirit Kyatt pouch", "blue Charm", "Kyatt fur", "153", "57", "Rending scroll" },
			{ "Spirit larupia pouch", "blue Charm", "larupia fur", "155", "57", "Goad scroll" },
			{ "Karamthulhu overlord pouch", "blue Charm", "Empty fishbowl", "144", "58", "Doomsphere scroll" },
			{ "Smoke devil pouch", "Crimson Charm", "Goat horn dust", "141", "61", "Dust cloud scroll" },
			{ "Abyssal lurker", "green Charm", "Abyssal charm", "119", "62", "Abyssal stealth scroll" },
			{ "Spirit cobra pouch", "Crimson Charm", "Snake hide", "116", "63", "Ophidian incubation scroll" },
			{ "Stranger plant pouch", "Crimson Charm", "Bagged plant", "128", "64", "Poisonous blast scroll" },
			{ "Mithril minotaur pouch", "Blue Charm", "Mithril bar", "152", "66", "Mithril bull rush scroll" },
			{ "Barker toad pouch", "Gold Charm", "Swamp toad", "11", "66", "Toad bark scroll" },
			{ "War tortoise pouch", "Gold Charm", "Tortoise shell", "1", "67", "Testudo scroll" },
			{ "Bunyip pouch", "Green Charm", "Raw shark", "110", "68", "Swallow whole scroll" },
			{ "Fruit bat pouch", "Green Charm", "Banana", "130", "69", "Fruitfall scroll" },
			{ "Ravenous Locust pouch", "Crimson Charm", "Pot of Flour", "79", "70", "Famine scroll" },
			{ "Arctic bear pouch", "Gold Charm", "Polar kebbit fur", "14", "71", "Arctic blast scroll" },
			{ "Phoenix pouch", "Crimson Charm", "Phoenix Quill", "165", "72", "" },
			{ "Obsidian Golem pouch", "Blue Charm", "Obsidian Charm", "195", "73", "Volcanic strength scroll" },
			{ "Granite lobster pouch", "Crimson Charm", "Granite (500g)", "166", "74", "Crushing claw scroll" },
			{ "Praying mantis pouch", "Crimson Charm", "Flowers", "168", "75", "Mantis strike scroll" },
			{ "Adamant minotaur pouch", "Blue Charm", "Adamant Bar", "144", "76", "Inferno scroll" },
			{ "Forge Regent pouch", "Green Charm", "Ruby harvest", "141", "76", "Adamant bull rush scroll" },
			{ "Talon Beast pouch", "Crimson Charm", "Talon Beast charm", "174", "77", "Deadly claw scroll" },
			{ "Giant ent pouch", "Green Charm", "Willow branch", "124", "78", "Acorn missile scroll" },
			{ "Fire titan pouch", "Blue Charm", "Fire talisman", "198", "79", "Titan's constitution scroll" },
			{ "Ice titan pouch", "Blue Charm", "Water talisman", "198", "79", "Titan's constitution scroll" },
			{ "Moss titan pouch", "Blue Charm", "Earth talisman", "202", "79", "Titan's constitution scroll" },
			{ "Hydra pouch", "Green Charm", "Water orb", "128", "80", "Regrowth scroll" },
			{ "Spirit dagannoth", "Crimson Charm", "Dagannoth hide", "1", "83", "Spike shot scroll" },
			{ "Lava titan pouch", "Blue Charm", "Obsidian Charm", "219", "83", "Ebon thunder scroll" },
			{ "Swamp titan pouch", "Swamp lizard", "Swamp lizard", "150", "85", "Swamp plague scroll" },
			{ "Rune minotaur pouch", "Blue Charm", "Rune bar", "1", "86", "Rune bull rush scroll" },
			{ "Unicorn stallion pouch", "green Charm", "Unicorn Horn", "140", "88", "Healing aura scroll" },
			{ "Geyser titan pouch", "blue Charm", "Water talisman", "222", "89", "Boil scroll" },
			{ "Wolpertinger pouch", "crimson Charm", "Raw rabbit", "203", "92", "Magic focus scroll" },
			{ "Abyssal titan pouch", "green Charm", "Abyssal charm", "113", "93", "Essence shipment scroll" },
			{ "Iron titna pouch", "crimson Charm", "Iron platebody", "198", "95", "Iron within scroll" },
			{ "Pack yak pouch", "Crimson Charm", "Yak hide", "211", "96", "Winter storage scroll" },
			{ "Steel titan pouch", "Blue Charm", "Steel platebody", "178", "99", "Steel of legends scroll" },

	};
	
	public boolean NEED(Client c, int i) {
		return c.getItems().playerHasItem(POUCH) && c.getItems().playerHasItem(SHARD, Integer.parseInt(summoningPouchData[i][3])) && c.getItems().playerHasItem(c.getItems().getItemId(summoningPouchData[i][1])) && c.getItems().playerHasItem(c.getItems().getItemId(summoningPouchData[i][2]));
	}

	public void makeSummoningPouch(Client c, int buttonId) {
		int i = (buttonId - 155031)/3;
		if (NEED(c, i)) {
			if(c.playerLevel[24] >= Integer.parseInt(summoningPouchData[i][4])) {
				c.getItems().deleteItem(POUCH, 1);
				c.getItems().deleteItem(SHARD, Integer.parseInt(summoningPouchData[i][3]));
				c.getItems().deleteItem(c.getItems().getItemId(summoningPouchData[i][1]), 1);
				c.getItems().deleteItem(c.getItems().getItemId(summoningPouchData[i][2]), 1);
				c.getItems().addItem(c.getItems().getItemId(summoningPouchData[i][0]), 1);
			} else {
				c.sendMessage("You do not have the required level to make this pouch");
			}
		} else {
			c.sendMessage("You do not have the required items to make this pouch");
		}
	}

	public void makeSummoningScroll(Client c, int pouchUsed) {
		for (int i = 0; i < summoningPouchData.length; i++) {
			if (pouchUsed == c.getItems().getItemId(summoningPouchData[i][0])) {
				if (c.getItems().playerHasItem(c.getItems().getItemId(summoningPouchData[i][0]), 1) && c.playerLevel[21] >= Integer.parseInt(summoningPouchData[i][4])) {
					c.getItems().deleteItem(c.getItems().getItemId(summoningPouchData[i][0]), 1);
					c.getItems().addItem(c.getItems().getItemId(summoningPouchData[i][5]), 1);
				} else {
					c.sendMessage("You need a higher summoning level to make this scroll");
				}
			}
		}
	}
	
	private final int POUCH = 12155;
	private final int SHARD = 12183;
		
	/**
	 * BoB Storing
	 */
	public void store()	{
		c.getPA().sendFrame126("Summoning BoB", 7421);
		for (int k = 0; k < 29; k++) {
			if(c.bobItems[k] > 0) {
				c.getPA().Frame34(7423, c.bobItems[k], k, c.bobItemsN[k]);
			}
			if(c.bobItems[k] <= 0) {
				c.getPA().Frame34(7423, -1, k, c.bobItemsN[k]);
			}
		}
		c.getPA().refreshBoB();
		c.isBanking = false;
		c.usingBoB = true;
		c.getItems().resetItems(5064);
		c.getItems().resetTempItems();
		c.getOutStream().createFrame(248);
		c.getOutStream().writeWordA(4465);
		c.getOutStream().writeWord(5063);
		c.getPA().sendFrame87(286, 0);
		c.flushOutStream();
		c.ResetKeepItems();
	}
	
	/**
	 * Summoning New Familiars
	 */
	public void SummonNewNPC(int npcID, int index) {
		int maxhit = (Integer) SUMMONING_NPC_DATA[index][5];
		int attack = (Integer) SUMMONING_NPC_DATA[index][6];
		int defence = (Integer) SUMMONING_NPC_DATA[index][7];
		String summonName = (String) SUMMONING_NPC_DATA[index][3];
		c.bobSlotCount = (Integer) SUMMONING_NPC_DATA[index][9];
		pouchreq = (Integer) SUMMONING_NPC_DATA[index][8];
		if (c.summonTime <= 0 && (Integer) SUMMONING_NPC_DATA[index][4] > 0)
			c.summonTime = (Integer) SUMMONING_NPC_DATA[index][4];
		c.getPA().sendFrame75(npcID, 17027);
		
		if(c.playerLevel[24] >= pouchreq) {
			Server.npcHandler.Summon(c, npcID, summonName, c.absX, c.absY-1, c.heightLevel, 0, 100, maxhit, false, attack, defence);
			c.getItems().deleteItem(c.s, 1);
			for (int i = 0; i < NPCHandler.maxNPCs; i++) {
				if (NPCHandler.npcs[i] != null) {
					c.npcslot = NPCHandler.npcs[i].npcId;
				}
			}
		} else {
			c.sendMessage("You need "+pouchreq+" Summoning to summon this monster");
		}
	}
	
	public int shards = 18016;//its also in npc, npchandler
	public int charm = 1;
	public int item = 1;
	public int amountofshard = 1;
	public int pouchreq;
	
	
	public boolean hasitem() {
		if(c.getItems().playerHasItem(charm, 1) && c.getItems().playerHasItem(item, 1) && c.getItems().playerHasItem(18016, amountofshard) && c.getItems().playerHasItem(12155, 1) && c.playerLevel[24] >= req) {
			c.getItems().deleteItem(charm, 1);
			c.getItems().deleteItem(item, 1);
			c.getItems().deleteItem(shards, amountofshard);
			c.getItems().deleteItem(12155, 1);
			return true;
		} else {
			c.sendMessage("You need the following items: 1x "+c.getItems().getItemName(charm)+" ");
			c.sendMessage("1x "+c.getItems().getItemName(item)+" ");
			c.sendMessage(""+amountofshard+"x "+c.getItems().getItemName(shards)+" ");
			c.sendMessage("You also need a summoning Level of "+req+" to make this pouch ");
			return false;
		}
	}
	
 
	public int gold = 12158;
	public int green = 12159;
	public int crim = 12160;
	public int blue = 12163;
	
	public int pouch = 12155;
	public int req;
	public void ItemonItem(int itemUsed, int useWith) {
	//variables
	//charm = charm id, item = itemmatirial, amountofshard = shard amount
		switch(itemUsed) {
			case 2138:
			useWith = pouch;
			charm = gold;
			req = 1;
			item = 2138;
			amountofshard = 8;
			if(hasitem())
			{
			c.getItems().addItem(12043, 1);
			c.getPA().addSkillXP(300, 24); //AmtExp is different so its defined in the method
			}

			break;


			case 2859:
			req = 1;
			useWith = pouch;
			charm = gold;
			item = 2859;
			amountofshard = 7;
			if(hasitem())
			{
			c.getItems().addItem(12047, 1);
			c.getPA().addSkillXP(500, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 6291:
			useWith = pouch;
			charm = gold;
			item = 6291;
			amountofshard = 8;
			req = 10;
			if(hasitem())
			{
			c.getItems().addItem(12059, 1);
			c.getPA().addSkillXP(800, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 3369:
			req = 13;
			useWith = pouch;
			charm = gold;
			item = 3369;
			amountofshard = 9;
			if(hasitem())
			{
			c.getItems().addItem(12019, 1);
			c.getPA().addSkillXP(1000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 440:
			req = 16;
			useWith = pouch;
			charm = gold;
			item = 440;
			amountofshard = 7;
			if(hasitem())
			{
			c.getItems().addItem(12009, 1);
			c.getPA().addSkillXP(1500, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 6319:
			req = 17;
			useWith = pouch;
			charm = gold;
			item = 6319;
			amountofshard = 1;
			if(hasitem())
			{
			c.getItems().addItem(12778, 1);
			c.getPA().addSkillXP(1600, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1783:
			req = 18;
			useWith = pouch;
			charm = green;
			item = 1783;
			amountofshard = 45;
			if(hasitem())
			{
			c.getItems().addItem(12049, 1);
			c.getPA().addSkillXP(2000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 3095:
			req = 19;
			useWith = pouch;
			charm = green;
			item = 3095;
			amountofshard = 57;
			if(hasitem())
			{
			c.getItems().addItem(12055, 1);
			c.getPA().addSkillXP(2100, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 12168:
			req = 22;
			useWith = pouch;
			charm = crim;
			item = 3095;
			amountofshard = 64;
			if(hasitem())
			{
			c.getItems().addItem(12808, 1);
			c.getPA().addSkillXP(2400, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 2134:
			req = 23;
			useWith = pouch;
			charm = blue;
			item = 2134;
			amountofshard = 75;
			if(hasitem())
			{
			c.getItems().addItem(12067, 1);
			c.getPA().addSkillXP(2800, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 3138:
			req = 25;
			useWith = pouch;
			charm = blue;
			item = 3138;
			amountofshard = 51;
			if(hasitem())
			{
			c.getItems().addItem(12063, 1);
			c.getPA().addSkillXP(3000, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 6032:
			req = 28;
			useWith = pouch;
			charm = green;
			item = 6032;
			amountofshard = 47;
			if(hasitem())
			{
			c.getItems().addItem(12091, 1);
			c.getPA().addSkillXP(4000, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 9976:
			req = 29;
			useWith = pouch;
			charm = green;
			item = 9976;
			amountofshard = 84;
			if(hasitem())
			{
			c.getItems().addItem(12800, 1);
			c.getPA().addSkillXP(4500, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 3325:
			req = 31;
			useWith = pouch;
			charm = crim;
			item = 3325;
			amountofshard = 81;
			if(hasitem())
			{
			c.getItems().addItem(12053, 1);
			c.getPA().addSkillXP(5000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 12156:
			req = 32;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 84;
			if(hasitem())
			{
			c.getItems().addItem(12065, 1);
			c.getPA().addSkillXP(5400, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 1519:
			req = 33;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 72;
			if(hasitem())
			{
			c.getItems().addItem(12021, 1);
			c.getPA().addSkillXP(5300, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 12164:
			req = 34;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 74;
			if(hasitem())
			{
			c.getItems().addItem(12818, 1);
			c.getPA().addSkillXP(5200, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 12165:
			req = 34;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 74;
			if(hasitem())
			{
			c.getItems().addItem(12814, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 12167:
			req = 34;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 74;
			if(hasitem())
			{
			c.getItems().addItem(12798, 1);
			c.getPA().addSkillXP(6000, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 2349:
			req = 36;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 102;
			if(hasitem())
			{
			c.getItems().addItem(12073, 1);
			c.getPA().addSkillXP(6100, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 6010:
			req = 40;
			useWith = pouch;
			charm = gold;
			item = itemUsed;
			amountofshard = 11;
			if(hasitem())
			{
			c.getItems().addItem(12087, 1);
			c.getPA().addSkillXP(6200, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 249:
			req = 41;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 78;
			if(hasitem())
			{
			c.getItems().addItem(12071, 1);
			c.getPA().addSkillXP(6300, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 12153:
			req = 42;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 104;
			if(hasitem())
			{
			c.getItems().addItem(12051, 1);
			c.getPA().addSkillXP(6400, 24); //AmtExp is different so its defined in the method
			}
			break;



			case 2351:
			req = 46;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 125;
			if(hasitem())
			{
			c.getItems().addItem(12075, 1);
			c.getPA().addSkillXP(6500, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 13403:
			req = 46;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 111;
			if(hasitem())
			{
			c.getItems().addItem(12816, 1);
			c.getPA().addSkillXP(6600, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1635:
			req = 47;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 88;
			if(hasitem())
			{
			c.getItems().addItem(12041, 1);
			c.getPA().addSkillXP(6700, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 2132:
			req = 49;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 117;
			if(hasitem())
			{
			c.getItems().addItem(12061, 1);
			c.getPA().addSkillXP(6800, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 9978:
			req = 52;
			useWith = pouch;
			charm = gold;
			item = itemUsed;
			amountofshard = 12;
			if(hasitem())
			{
			c.getItems().addItem(12007, 1);
			c.getPA().addSkillXP(6900, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 12161:
			req = 54;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 106;
			if(hasitem())
			{
			c.getItems().addItem(12036, 1);
			c.getPA().addSkillXP(7000, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 1937:
			req = 55;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 151;
			if(hasitem())
			{
			c.getItems().addItem(12027, 1);
			c.getPA().addSkillXP(7100, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 2353:
			req = 56;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 141;
			if(hasitem())
			{
			c.getItems().addItem(12077, 1);
			c.getPA().addSkillXP(7200, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 311:
			req = 56;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 109;
			if(hasitem())
			{
			c.getItems().addItem(12531, 1);
			c.getPA().addSkillXP(7300, 24); //AmtExp is different so its defined in the method
			}

			break;


			case 10099:
			req = 57;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 154;
			if(hasitem())
			{
			c.getItems().addItem(12810, 1);
			c.getPA().addSkillXP(7400, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 10103:
			req = 57;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 153;
			if(hasitem())
			{
			c.getItems().addItem(12812, 1);
			c.getPA().addSkillXP(7500, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 10095:
			req = 57;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 155;
			if(hasitem())
			{
			c.getItems().addItem(12784, 1);
			c.getPA().addSkillXP(7600, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 9736:
			req = 58;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 141;
			if(hasitem())
			{
			c.getItems().addItem(12805, 1);
			c.getPA().addSkillXP(7700, 24); //AmtExp is different so its defined in the method
			}
			break;

			//case 12161:
			//useWith = pouch;
			//charm = green;
			//item = itemUsed;
			//amountofshard = 141;
			//if(hasitem())
			//{
			//c.getItems().addItem(12037, 1);

			//}
			//break;

			case 7801:
			req = 63;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 116;
			if(hasitem())
			{
			c.getItems().addItem(12015, 1);
			c.getPA().addSkillXP(7800, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 8431://stranger plant
			req = 64;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 128;
			if(hasitem())
			{
			c.getItems().addItem(12045, 1);
			c.getPA().addSkillXP(7900, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 2359://stranger plant
			req = 66;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 152;
			if(hasitem())
			{
			c.getItems().addItem(12079, 1);
			c.getPA().addSkillXP(8000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 2150://stranger plant
			req = 66;
			useWith = pouch;
			charm = gold;
			item = itemUsed;
			amountofshard = 11;
			if(hasitem())
			{
			c.getItems().addItem(12123, 1);
			c.getPA().addSkillXP(8100, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 7939://stranger plant
			req = 67;
			useWith = pouch;
			charm = gold;
			item = itemUsed;
			amountofshard = 1;
			if(hasitem())
			{
			c.getItems().addItem(12031, 1);
			c.getPA().addSkillXP(8200, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 383://stranger plant
			req = 68;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 110;
			if(hasitem())
			{
			c.getItems().addItem(12029, 1);
			c.getPA().addSkillXP(8300, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1963://stranger plant
			req = 69;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 130;
			if(hasitem())
			{
			c.getItems().addItem(12033, 1);
			c.getPA().addSkillXP(8400, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1933://stranger plant
			req = 70;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 79;
			if(hasitem())
			{
			c.getItems().addItem(12820, 1);
			c.getPA().addSkillXP(8500, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 10117://stranger plant
			req = 71;
			useWith = pouch;
			charm = gold;
			item = itemUsed;
			amountofshard = 14;
			if(hasitem())
			{
			c.getItems().addItem(12057, 1);
			c.getPA().addSkillXP(8600, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 14616://stranger plant
			req = 72;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 165;
			if(hasitem())
			{
			c.getItems().addItem(14623, 1);
			c.getPA().addSkillXP(8700, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 4188://changed
			req = 73;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 195; 
			if(hasitem())
			{
			c.getItems().addItem(12792, 1);
			c.getPA().addSkillXP(8800, 24); //AmtExp is different so its defined in the method
			}
			break;



			case 6979://changed
			req = 74;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 166; 
			if(hasitem())
			{
			c.getItems().addItem(12069, 1);
			c.getPA().addSkillXP(8900, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 2460://changed
			req = 75;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 168; 
			if(hasitem())
			{
			c.getItems().addItem(12011, 1);
			c.getPA().addSkillXP(9000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 2361://changed
			req = 75;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 144; 
			if(hasitem())
			{
			c.getItems().addItem(12081, 1);
			c.getPA().addSkillXP(9100, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 10020://changed
			req = 76;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 141; 
			if(hasitem())
			{
			c.getItems().addItem(12782, 1);
			c.getPA().addSkillXP(9200, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 12162: //changed
			req = 77;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 174; 
			if(hasitem())
			{
			c.getItems().addItem(12794, 1);
			c.getPA().addSkillXP(9300, 24); //AmtExp is different so its defined in the method
			}
			break;



			case 5933: //changed
			req = 78;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 124; 
			if(hasitem())
			{
			c.getItems().addItem(12013, 1);
			c.getPA().addSkillXP(9400, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1442: //changed
			req = 79;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 198; 
			if(hasitem())
			{
			c.getItems().addItem(12802, 1);
			c.getPA().addSkillXP(9500, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 1438: //changed
			req = 79;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 198; 
			if(hasitem())
			{
			c.getItems().addItem(12806, 1);
			c.getPA().addSkillXP(9600, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1440: //changed
			req = 79;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 202; 
			if(hasitem())
			{
			c.getItems().addItem(12804, 1);
			c.getPA().addSkillXP(9700, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 571: //changed
			req = 80;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 128;
			if(hasitem())
			{
			c.getItems().addItem(12025, 1);
			c.getPA().addSkillXP(9900, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 6155: //changed
			req = 83;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 1;
			if(hasitem())
			{
			c.getItems().addItem(12017, 1);
			c.getPA().addSkillXP(10000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 4699: //changed lava rune
			req = 85;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 219;
			if(hasitem())
			{
			c.getItems().addItem(12788, 1);
			c.getPA().addSkillXP(11000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 10149: //changed
			req = 86;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 150;
			if(hasitem())
			{
			c.getItems().addItem(12776, 1);
			c.getPA().addSkillXP(12000, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 2363: //changed
			req = 88;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 1;
			if(hasitem())
			{
			c.getItems().addItem(12083, 1);
			c.getPA().addSkillXP(13800, 24); //AmtExp is different so its defined in the method
			}
			break;



			case 1486: //changed
			req = 89;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 140;
			if(hasitem())
			{
			c.getItems().addItem(12039, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1444: //changed
			req = 92;
			useWith = pouch;
			charm = blue;
			item = itemUsed;
			amountofshard = 222;
			if(hasitem())
			{
			c.getItems().addItem(12786, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 3228: //changed
			req = 93;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 203;
			if(hasitem())
			{
			c.getItems().addItem(12089, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 7979: //changed abyss head
			req = 93;
			useWith = pouch;
			charm = green;
			item = itemUsed;
			amountofshard = 113;
			if(hasitem())
			{
			c.getItems().addItem(12796, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1115: //changed
			req = 95;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 198;
			if(hasitem())
			{
			c.getItems().addItem(12822, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;

			case 10818: //changed
			req = 96;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 211;
			if(hasitem())
			{
			c.getItems().addItem(12093, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;


			case 1119: //changed
			req = 99;
			useWith = pouch;
			charm = crim;
			item = itemUsed;
			amountofshard = 178;
			if(hasitem())
			{
			c.getItems().addItem(12790, 1);
			c.getPA().addSkillXP(5800, 24); //AmtExp is different so its defined in the method
			}
			break;


}



}
		
	public int getNpcDataIndex(int npcId) {
		for(int i = 0; i < SUMMONING_NPC_DATA.length; i++) {
			Object[] data = SUMMONING_NPC_DATA[i];
			if((Integer) data[1] == npcId || (Integer) data[2] == npcId) {
				return i;
			}
		}
		return 0;
	}

}