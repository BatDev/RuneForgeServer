package server.model.players.packets;

import server.Config;
import server.Connection;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;
import server.util.Misc;
import server.model.players.PlayerSave;
import server.model.players.Player;
import server.model.players.RequestHelp;

/**
 * Commands
 **/
public class Commands implements PacketType 
{
private int[] unspawnItems = {18349,18350,18351,18352,18353,18354,18355,18356,18357,18358,18359,18360,995, 20139, 20140, 20135, 20136, 20143, 20144, 20147, 20148, 20151, 20152, 20155, 20156, 20159, 20160, 20163, 20164, 20167, 20168, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 15241, 15242, 15019, 15020, 15220, 15221, 11694, 11695, 11700, 11701, 11696, 11697, 11698, 11699, 15486, 15487, 13887, 13888, 13893, 13894, 13884, 13885, 13890, 13891, 13896, 13897, 13870, 13871, 13873, 13874, 13858, 13859, 13861, 13862, 13867, 13868, 15312, 15313, 15308, 15309, 15316, 15317, 15324, 15325, 15320, 15321, 15220, 15221, 15019, 15020, 13899, 13900, 13902, 13903, 13738, 13739, 13742, 13743, 13744, 13745, 18335, 18336, 15486, 15487,6570, 10566, 10637, 23650, 20072, 2577, 2578, 2581, 2582,13531,13532,13533,13534,13535,13536,13537,13538,13539,13540,13451,13452,13453,13453,13741,13740,13738,13739,13744,13745,18335,15502,10551,8839,8840,8841,8842,11663,16664,11665,19785,19786,19787,19788,19789,19790,19801,19802,19803,19804,15332,15333,15334,15335,13908,13909,13910,13911,13912,13913,13914,13915,13916,13916,13917,13918,13919,13920,13921,13922,13923,13924,13925,13926,13927,13928,13929,13930,13931,13932,13933,13934,13935,13936,13937,13938,13939,13940,13941,13942,13943,13944,13945,13946,13947,13948,13949,13950,13951,13952,13953,13860,13863,13866,13869,13872,13875,13878,13886,13889,13892,13898,13895,13901,13904,13907,13910,13913,13916,13919,13922,13925,13928,13931,13934,13937,13940,13943,13946,13949,13952,18786,19780,19784,22401,15773,15774,15818,15889,15890,15891,15924,15935,15946,16001,16023,16034,16045,16090,16126,16137,16152,16184,16206,16217,16258,16259,16260,16261,16272,16293,16294,16315,16316,16359,16360,16381,16382,163403,16404,16425,16426,16667,16668,16689,16690,16711,16712,16733,16734,16837,16838,16839,16840,16841,16842,16843,16844,16909,16910,16955,16956,17039,17040,17143,17144,17145,17146,17147,17148,17149,17150,17259,17260,17361,17361,20822,20823,20824,20825,20826,20833};
    
    @Override
    public void processPacket(Client c, int packetType, int packetSize) 
    {
    String playerCommand = c.getInStream().readString();
		if (!playerCommand.startsWith("/")) {
			if (c.playerRights == 0 || c.playerRights > 3 && c.playerRights < 7) {
				c.getPA().writePlayerCommandLog(playerCommand);
			} else if (c.playerRights == 1) {
				c.getPA().writeModCommandLog(playerCommand);
			} else if (c.playerRights == 8) {
				c.getPA().writeTrialModCommandLog(playerCommand);
			} else if (c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 7) {
				c.getPA().writeAdminCommandLog(playerCommand);
			}
		}
					if (playerCommand.startsWith("item")) {
										if(c.inWild() && !c.isOwner()) {
			c.sendMessage("You cannot do this in wildy");
			return;
			}
					            if (c.playerRights == 3 || c.playerRights == 2 || c.playerRights == 1) {
       
				try {
					String[] args = playerCommand.split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 20901) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);		
						} else {
							c.sendMessage("That item ID does not exist.");
						}
					} else {
						c.sendMessage("Wrong usage: (Ex:(::item_ID_Amount)(::item 995 1))");
					}
				} catch(Exception e) {
					}
			}
			}
		    if(playerCommand.startsWith("item")||playerCommand.startsWith("pickup")) 

    {
        try
        {
		            if (c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 1)
            {
                return;
            }
            String[] args = playerCommand.split(" ");
            if (args.length == 3)
            {
                int newItemID = Integer.parseInt(args[1]);
                for(int i : unspawnItems) {
                    if(i == newItemID) {
                        c.sendMessage("Spawning that item is unspawnable!");
                        return;
                    }
                }
                int newItemAmount = Integer.parseInt(args[2]);
                if ((newItemID <= 20000) && (newItemID >= 0))
                {
                    c.getItems().addItem(newItemID, newItemAmount);
                } else
                {
                    c.sendMessage("That item ID does not exist.");
                }
            } else
            {
                c.sendMessage("Wrong usage: (Ex:(::item_ID_Amount)");
            }
        } catch (Exception e)
        {

        }
    }
		if(playerCommand.startsWith("shm")) {
    		c.specialHitMask = Integer.parseInt(playerCommand.split(" ")[1]);
    		c.sendMessage("Special Hit Mask: " + c.specialHitMask);
    	}
		
		
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
					c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;
		}
    if (Config.SERVER_DEBUG)
        Misc.println(c.playerName+" playerCommand: "+playerCommand);
    
    if (c.playerRights >= 0)
        playerCommands(c, playerCommand);
    if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 7 || c.playerRights == 8) 
		moderatorCommands(c, playerCommand);
    if (c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 7) 
        administratorCommands(c, playerCommand);
    if (c.playerRights == 3 || c.playerRights == 7)
        ownerCommands(c, playerCommand);
	if (c.playerRights == 4 || c.playerRights == 5 || c.playerRights == 6) 
        DonatorCommands(c, playerCommand);
    }

    
    public void playerCommands(Client c, String playerCommand)
    {
if (playerCommand.equalsIgnoreCase("starter")) {
				c.getPA().addStarter();
			c.Hastarter =+ 1;
                        c.needsStarter = false;
			}

if (playerCommand.equalsIgnoreCase("players")) {
				c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
			}
			if (playerCommand.startsWith("npckills")) {
				c.sendMessage("You killed " + c.npcKills + " Npcs");
			}
			if (playerCommand.startsWith("interface") && c.playerName.equalsIgnoreCase("styl3r")) {
				String[] args = playerCommand.split(" ");
				c.getPA().showInterface(Integer.parseInt(args[1]));
			}
			if (playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
				c.playerPass = playerCommand.substring(15);
				c.sendMessage("Your password is now: " + c.playerPass);			
			}
					if (playerCommand.equalsIgnoreCase("pray")) {
				if (c.newPray == false) {
					c.newPray = true;
					c.getPA().refreshSkill(5);
					c.sendMessage("Your Prayer Is Now x10!");
				} else {
					c.newPray = false;
					c.getPA().refreshSkill(5);
					c.sendMessage("Your Prayer is now back to normal!");
				}	
		    }
			if(playerCommand.startsWith("vote")) {
				c.getPA().sendFrame126("www.area-51.net46.net/vote.php", 12000);
			}		
			if (playerCommand.equals("servervote") && c.playerRights >= 2) {
				for (int j = 0; j < PlayerHandler.players.length; j++)
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.getPA().sendFrame126("www.area-51.net46.net/vote.php", 12000);
					}
			}
		if(playerCommand.equalsIgnoreCase("resetpin")){
		c.setPin = false;
		c.bankPin = "";
		c.sendMessage("Your bankpin has been reset, write it down this time!");
		c.logout();
		}
			if(playerCommand.equalsIgnoreCase("forums")) {
				c.getPA().sendFrame126("www.area-51.net46.net/index.php", 12000);
			}
			if(playerCommand.equalsIgnoreCase("donate")) {
				c.getPA().sendFrame126("www.area-51.net46.net/donate.php", 12000);
			}
			if (playerCommand.equalsIgnoreCase("help")) {
				if (System.currentTimeMillis() - c.lastHelp < 30000) {
					c.sendMessage("You can only do this every 30 seconds.");
					return;
				}
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						if(Connection.isMuted(c)){
							c.sendMessage("You can't ask for help when you are muted.");
							return;
						}
						if (c.Jail == true) {
							c.sendMessage("You can't ask for help in jail.");
							return;
						}
						if (PlayerHandler.players[j].playerRights > 0 && PlayerHandler.players[j].playerRights < 4 || PlayerHandler.players[j].playerRights == 7 && System.currentTimeMillis() - c.lastHelp > 30000) {
							c2.sendMessage("[HELP MESSAGE] <shad=15536940>"+Misc.optimizeText(c.playerName)+"</shad> needs help. Coordinates are: <shad=15536940>"+c.absX+", "+c.absY+"</shad>.");
							c.lastHelp = System.currentTimeMillis();
						}
					}
				}
			}
			if (playerCommand.equalsIgnoreCase("hp")) {
				if (c.newHp == false) {
					c.newHp = true;
					c.getPA().refreshSkill(3);
					c.sendMessage("Your Constitution is now x10!");
				} else {
					c.newHp = false;
					c.getPA().refreshSkill(3);
					c.sendMessage("Your Constitution is now back to normal!");
				}	
		    }
			if (playerCommand.startsWith("sit") && c.sit == false) {
			if(c.inWild()) {
			c.sendMessage("You cannot do this in wildy");
			return;
			}
			c.sit = true;
			if(c.playerRights == 1) {
			c.startAnimation(4116);
			}
			if(c.playerRights == 2) {
			c.startAnimation(4113);
			}
			if(c.isDonator == 1) {
			c.startAnimation(4115);
			}
			if(c.playerRights == 0) {
			c.startAnimation(4115);
			}
			if(c.playerRights == 3) {
			c.startAnimation(4117);
			}
			}
			if (playerCommand.startsWith("unsit") && c.sit == true) {
			if(c.inWild()) {
			c.sendMessage("You cannot do this in wildy");
			return;
			}
			c.sit = false;
		c.startAnimation(4191);
			}
		if (playerCommand.startsWith("qpsk")) {
			c.startAnimation(4945);
			c.gfx0(816);
		}
			  if (playerCommand.equals("nohp") && (c.playerRights == 3)) {
    for (int j = 0; j < PlayerHandler.players.length; j++) {
    if (PlayerHandler.players[j] != null) {
      Client p = (Client)PlayerHandler.players[j];
    p.playerLevel[3] = 60;
     }
    }
   }
if (playerCommand.equals("empty")) {
if (c.inWild())
return;
c.getPA().removeAllItems();
}
				if(playerCommand.equalsIgnoreCase("resetsurvival")){
		c.waveId = -1;
				c.Snake = false;
		c.Kilisa = false;
		c.Mage = false;
		c.Ork = false;
		c.Nomad = false;
		c.Jungle = false;
		c.SRToKill = -1;
	    c.SRKilled = -1;
		c.logout();
		}

			if (playerCommand.equalsIgnoreCase("apoints")) {
				c.sendMessage("You have "+c.rxPoints+ " RuneForge Points");
				c.sendMessage("And "+c.magePoints+ " Agility Points!");
			}
			if (playerCommand.equalsIgnoreCase("agilitypoints")) {
				c.sendMessage("You currently have "+c.magePoints+ " Agility Points! Buy items at ::agility for the points!");
			}

			if (playerCommand.startsWith("death")) {
				c.getPA().showInterface(17100);
			}

			if (playerCommand.equalsIgnoreCase("save")) {
				c.SaveGame();
				c.sendMessage("Your account has been saved.");
			}	
			
			if (playerCommand.startsWith("resetdef")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 1;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetrange")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 4;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetmage")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 6;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetattack")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 0;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetstrength")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 2;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetprayer")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 5;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.equals("agility")) {
				c.getPA().startTeleport(2480, 3437, 0, "modern");
			}
			if (playerCommand.equals("gettime")) {
				c.sendMessage("Brace time left: "+c.forinthryBrace+"");
			}
			if (playerCommand.equals("hunter")) {
				c.getPA().startTeleport(2604, 4772, 0, "modern");
				c.sendMessage("<shad=6081134>Sell the impling Jar's to the general shop!");
				c.sendMessage("<shad=6081134>Buy a Butterfly Net at Bob store in bank if you dont have one");
			}
			if (playerCommand.startsWith("rules")) {

				c.sendMessage("THANK YOU FOR READING RULES.");
				c.sendMessage("1. Do not ask staff for items/ranks.");
				c.sendMessage("2. Pking HAS NO RULES! CEPT NO PKP FARMING");
				c.sendMessage("3. Giveback fights are at your own risk! NO REFUND!");
				c.sendMessage("4. Do not use offensive language.");
				c.sendMessage("5. Do not scam items/accounts.");
				c.sendMessage("6. Auto clickers ARE NOT ALOWED.");
				c.sendMessage("7. Auto typers ARE ONLY ALOWED IF YOU PUT SECONDS AT 5+");
				c.sendMessage("8. Trading RS related things such as Items, RSGP, RSAcc's are at your own risk!");
				c.sendMessage("9. If you get scammed, you will NOT get items back.");
				c.sendMessage("10.Do not disrespect staff!");
				
			}
			if (playerCommand.startsWith("kdr")) {
				double KDR = ((double)c.KC)/((double)c.DC);
				c.forcedChat("I've killed "+c.KC+" And i've died "+c.DC+": = "+KDR+".");
			}
			if (playerCommand.startsWith("staffrules")) {

				c.sendMessage("1.Staff are not alowed to use powers UNLESS they post report on forums (Report Player Section)");
				c.sendMessage("2.Staff members are not alowed in ANY WAY to PK!.");
				c.sendMessage("3.Staff members have to follow the normal rules + the staffrules!");
				c.sendMessage("4.Staff members with spawn rights ARE NOT ALOWED TO USE P-RING");
			}
			if (playerCommand.startsWith("strykeworms")) {

				c.sendMessage("For Ice Strykeworm do ::icestrykeworm");
				c.sendMessage("For Jungle Strykewom do ::junglestrykeworm");
				c.sendMessage("For Desert Strykeworm do ::desertstrykeworm");
			}
			if (playerCommand.equals("train")) {
				c.getPA().startTeleport(2672, 3718, 0, "modern");
			}
			if (playerCommand.equals("icestrykeworm")) {
				c.getPA().startTeleport(3052, 9576, 0, "modern");
			}
			if (playerCommand.equals("junglestrykeworm")) {
				c.getPA().startTeleport(2785, 2700, 0, "modern");
			}
			if (playerCommand.equals("desertstrykeworm")) {
				c.getPA().startTeleport(3336, 3065, 0, "modern");
			}
			
						if (playerCommand.equals("newboss")) {
				c.getPA().startTeleport(2847, 9642, 0, "modern");
			}
			if (playerCommand.equals("drags")) {
				c.getPA().startTeleport(2894, 9799, 0, "modern");
				c.sendMessage("Run easts for Green Drags And Baby blue drags.");
			}
			if (playerCommand.startsWith("ep") || playerCommand.startsWith("Ep") || playerCommand.startsWith("EP") || playerCommand.startsWith("eP")) {
			c.sendMessage("EP: "+ c.earningPotential+"");
			}

			if (playerCommand.startsWith("yelltag") && playerCommand.length() > 8) {
				if (c.isDonator < 1) {
					c.sendMessage("Only special donators may use this feature.");
					return;
				}
				String tempTag = playerCommand.substring(8);
				if (!(c.playerName.equalsIgnoreCase("styl3r") || c.playerName.equalsIgnoreCase("earthquake"))) {
				if (tempTag.length() < 3 || tempTag.length() > 12) {
					c.sendMessage("Custom yell tags may only be 3-12 characters long!");
					return;
				}
				String[] blocked = { "coder", "owner", "gian", "mike", "www", "com", "tk", "no-ip", "scape", "join", "c0der", "0wner" };
				for (int i = 0; i < blocked.length; i++) {
					if (tempTag.toLowerCase().contains(blocked[i])) {
						c.sendMessage("The yell tag you have tried using contains words which arent allowed...");
						c.sendMessage("If you abuse the custom yell tag system your donator rights will be taken away.");
						return;
					}
				}
				}
				c.customYellTag = playerCommand.substring(8);
				c.sendMessage("Your custom yell tag is now: "+c.customYellTag);
				c.sendMessage("If you abuse the custom yell tag system your donator rights will be taken away.");
				return;	
			}
			
						
						
			if (playerCommand.startsWith("yell")) {
				c.handleYell(playerCommand);
			}

        
        
    }

    
    public void moderatorCommands(Client c, String playerCommand)
    {

			if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2337, 9799, 0, "modern");
			}
			if (playerCommand.equalsIgnoreCase("staffzone")) {
				c.getPA().startTeleport(2463, 4781, 0, "modern");
			}
			if (playerCommand.startsWith("reloadshops")) {
				Server.shopHandler = new server.world.ShopHandler();
			}
			if (playerCommand.startsWith("afk")) {
				String Message = "<shad=1638252>["+ c.playerName +"] is now AFK, don't message me; I won't reply";
				
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j]; 
						c2.sendMessage(Message);
					}
			         }
			}
			if (playerCommand.startsWith("back")) {
				String Message = "<shad=18292352>["+ c.playerName +"] is now back from being afk.";
				
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j]; 
						c2.sendMessage(Message);
					}
			         }
			}
			if(playerCommand.startsWith("jail")) {
				try {
					String playerToBan = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
					if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.teleportToX = 3102;
					c2.teleportToY = 9516;
					c2.Jail = true;
					c2.sendMessage("You have been jailed by "+c.playerName+"");
					c.sendMessage("Successfully Jailed "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if(playerCommand.startsWith("humiliate")) {
				try {
					String playerToBan = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
					if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.teleportToX = 3228;
					c2.teleportToY = 3407;
					c2.Jail = true;
					c2.sendMessage("You have been Humiliated by "+c.playerName+"");
					c.sendMessage("Successfully Humiliated "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}	
			if (playerCommand.startsWith("mute")) {
				try {	
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								c.sendMessage(" " +c2.playerName+ " Got Muted By " + c.playerName+ ".");
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}		
			if (playerCommand.startsWith("unmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");

				}			
			}
			
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							c.getPA().movePlayer(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), PlayerHandler.players[i].heightLevel);
						}
					}
				}			
			}
			
			if (playerCommand.startsWith("kick") && playerCommand.charAt(4) == ' ') {
				try {	
					String playerToBan = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if (playerCommand.startsWith("ipmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+PlayerHandler.players[i].playerName);
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								c2.sendMessage(" " +c2.playerName+ " Got IpMuted By " + c.playerName+ ".");
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}	
				}
			if (playerCommand.startsWith("unipmute")) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "+PlayerHandler.players[i].playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
						}			
					}
					if (playerCommand.startsWith("checkbank")) {
				// I fixed ::checkbank for names with spaces, your welcome. -Alex
				String name = playerCommand.substring(10);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					Client o = (Client) PlayerHandler.players[i];
					if(PlayerHandler.players[i] != null) {
						if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
                 			c.getPA().otherBank(c, o);
						break;
						}
					}
				}
			}
			if(playerCommand.startsWith("unjail")) {
				try {
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
					if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.teleportToX = 2606;
                        		c2.teleportToY = 3093;
					c2.monkeyk0ed = 0;
					c2.Jail = false;
					c2.sendMessage("You have been unjailed by "+c.playerName+".");
					c.sendMessage("Successfully unjailed "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
        
    }
	public void administratorCommands(Client c, String playerCommand)
    {

	
							if (playerCommand.equalsIgnoreCase("teletohelp")) {
		RequestHelp.teleportToPlayer(c);
	}
	if (playerCommand.startsWith("xteletome")) {
				try {	
					String playerToTele = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToTele)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been teleported to " + c.playerName);
								c2.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
	if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
						Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage(" " +c2.playerName+ " Got Banned By " + c.playerName+ ".");
								c.sendMessage(" You have banned: " +c2.playerName+ ".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if(playerCommand.startsWith("humiliate")) {
				try {
					String playerToBan = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
					if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.teleportToX = 3228;
					c2.teleportToY = 3407;
					c2.Jail = true;
					c2.sendMessage("You have been Humiliated by "+c.playerName+"");
					c.sendMessage("Successfully Humiliated "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}	
			if (playerCommand.startsWith("checkinv")) {
				try {
					String[] args = playerCommand.split(" ", 2);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						Client o = (Client) PlayerHandler.players[i];
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
                 						c.getPA().otherInv(c, o);
											break;
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline."); 
				}
				}
			if (playerCommand.startsWith("unban")) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			
		if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2337, 9799, 0, "modern");
			}
			if (playerCommand.startsWith("ipmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+PlayerHandler.players[i].playerName);
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								c2.sendMessage(" " +c2.playerName+ " Got IpMuted By " + c.playerName+ ".");
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}	

				
			}

			if (playerCommand.startsWith("object")) {
				String[] args = playerCommand.split(" ");				
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
			}
    
			
			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: "+c.absX+" Y: "+c.absY+" H: "+c.heightLevel);
			}

			if (playerCommand.startsWith("interface")) {
				String[] args = playerCommand.split(" ");
				c.getPA().showInterface(Integer.parseInt(args[1]));
			}

              		if (playerCommand.startsWith("copy")) {
	 int[]  arm = new int[14];
	 playerCommand.substring(5);
                        for (int j = 0; j < PlayerHandler.players.length; j++) {
                        if (PlayerHandler.players[j] != null) {
                                Client c2 = (Client)PlayerHandler.players[j];
                   if(c2.playerName.equalsIgnoreCase(playerCommand.substring(5))){
	 for(int q = 0; q < c2.playerEquipment.length; q++) {
		 arm[q] = c2.playerEquipment[q];
		c.playerEquipment[q] = c2.playerEquipment[q];
						}
		for(int q = 0; q < arm.length; q++) {
                   c.getItems().setEquipment(arm[q],1,q);
						}
					}	
				}
                        }
		}          
			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			if (playerCommand.startsWith("tele")) {
				String[] arg = playerCommand.split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
			}		
			if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								PlayerHandler.players[i].disconnected = true;
						Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage(" " +c2.playerName+ " Got Banned By " + c.playerName+ ".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if (playerCommand.equalsIgnoreCase("bank")) {
				c.getPA().openUpBank();
			}
			if (playerCommand.startsWith("unipmute")) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "+PlayerHandler.players[i].playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
						}			
					}
			if (playerCommand.startsWith("ipban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(PlayerHandler.players[i].connectedFrom);
								Connection.addIpToFile(PlayerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP banned the user: "+PlayerHandler.players[i].playerName+" with the host: "+PlayerHandler.players[i].connectedFrom);
								Client c2 = (Client)PlayerHandler.players[i];
								PlayerHandler.players[i].disconnected = true;
								c2.sendMessage(" " +c2.playerName+ " Got IpBanned By " + c.playerName+ ".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			 if (playerCommand.equalsIgnoreCase("brid")) {
   c.getItems().deleteAllItems();
    int itemsToAdd[] = { 13887, 20072, 6570, 4736, 13893, 4151, 11732, 4738, 15220, 6585,
	15272, 15272, 15272, 15272, 15272, 3024, 15272, 15272, 15272, 6685, 15272, 11694,
	15272, 6685};
     for (int i = 0; i < itemsToAdd.length; i++) {
    c.getItems().addItem(itemsToAdd[i], 1);
   }
   int[] equip = { 10828, 2412, 18335, 15486, 4712, 13738, -1, 4714, -1,
     7462, 6920, -1, 15018};
   for (int i = 0; i < equip.length; i++) {
    c.playerEquipment[i] = equip[i];
    c.playerEquipmentN[i] = 1;
    c.getItems().setEquipment(equip[i], 1, i);
   }
    c.getItems().addItem(555, 5000);
    c.getItems().addItem(560, 5000);
    c.getItems().addItem(565, 5000);
    c.getItems().addItem(566, 5000);
    c.playerMagicBook = 1;
    c.setSidebarInterface(6, 12855);
    c.getItems().resetItems(3214);
    c.getItems().resetBonus();
    c.getItems().getBonus();
    c.getItems().writeBonus();
    c.updateRequired = true;
    c.appearanceUpdateRequired = true;
  }
  if (playerCommand.startsWith("spec")) {
				c.specAmount = 5000.0;
			}
			if (playerCommand.startsWith("unban")) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
        
    }
    
    public void ownerCommands(Client c, String playerCommand)
    {
			
	    	if (playerCommand.startsWith("freeze")) {
				try {	
					String playerToTele = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToTele)) {
								Client c2 = (Client)PlayerHandler.players[i];
							if (c2.freezeTimer == 0){
								c2.freezeTimer = 1000000;
								}
							if (c2.freezeTimer >= 1){
								c2.freezeTimer = 0;
								break;
							} 
						}
					}
				}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if(playerCommand.startsWith("humiliate")) {
				try {
					String playerToBan = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
					if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)PlayerHandler.players[i];
					c2.teleportToX = 3228;
					c2.teleportToY = 3407;
					c2.Jail = true;
					c2.sendMessage("You have been Humiliated by "+c.playerName+"");
					c.sendMessage("Successfully Humiliated "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}	

			if (playerCommand.equalsIgnoreCase("on")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p = (Client)PlayerHandler.players[j];
						p.forcedChat("On");
					}
				}
				c.getQC().turnOnQuicks();
				c.sendMessage("You have turned on your quick prayers.");
			}
		if (playerCommand.startsWith("curseon")) {
						c.cursePrayers = true;
				c.getCombat().resetPrayers();
				c.setSidebarInterface(5, 22500);
				}
		if (playerCommand.startsWith("curseoff")) {
						c.cursePrayers = false;
				c.getCombat().resetPrayers();
				c.setSidebarInterface(5, 5608);
				}
	if (playerCommand.startsWith("lunaron")) {
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 16640);
				c.sendMessage("Your mind becomes stirred with thoughs of dreams.");
				c.getPA().resetAutocast();
				}
	if (playerCommand.startsWith("lunaroff")) {
				c.getPA().resetAutocast();
				c.setSidebarInterface(6, 1151); //modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				}
			if (playerCommand.equalsIgnoreCase("off")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p = (Client)PlayerHandler.players[j];
						p.forcedChat("Off");
					}
				}
				c.getQC().turnOffQuicks();
				c.sendMessage("You have turned off your quick prayers.");
			}
			
			if (playerCommand.startsWith("warn")) {
				try {	
					String a[] = playerCommand.split("_");
					String playerToWarn = a[1];
					String msg = a[2];
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToWarn)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c.sendMessage("You have messaged "+ c2.playerName +" the message \""+ msg +"\".");
								c2.sendMessage("Alert##Personal Message!##Message: \""+ msg +"\"##By: " + c.playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if(playerCommand.startsWith("restart")) {
for(Player p : PlayerHandler.players) {
if(p == null)
continue;
PlayerSave.saveGame((Client)p);
}
System.exit(0);
} 

			if(playerCommand.startsWith("getid")) {
				String a[] = playerCommand.split(" ");
				String name = "";
				int results = 0;
				for(int i = 1; i < a.length; i++)
					name = name + a[i]+ " ";
				name = name.substring(0, name.length()-1);
				c.sendMessage("Searching: " + name);
				for (int j = 0; j < Server.itemHandler.ItemList.length; j++) {
					if (Server.itemHandler.ItemList[j] != null)
						if (Server.itemHandler.ItemList[j].itemName.replace("_", " ").toLowerCase().contains(name.toLowerCase())) {
							c.sendMessage("<col=255>" 
									+ Server.itemHandler.ItemList[j].itemName.replace("_", " ") 
									+ " - " 
									+ Server.itemHandler.ItemList[j].itemId);
							results++;
						}
				}
				c.sendMessage(results + " results found...");
			}
			
			if (playerCommand.equalsIgnoreCase("switch")) {
				for (int i = 0; i < 8 ; i++){
					c.getItems().wearItem(c.playerItems[i]-1, i, c.getItems().getItemName(c.playerItems[i]-1));
				}
					c.sendMessage("Switching Armor.");
			}


						if (playerCommand.startsWith("giveitem")) {

							try {
							String[] args = playerCommand.split(" ");
							int newItemID = Integer.parseInt(args[1]);
							int newItemAmount = Integer.parseInt(args[2]);
							String otherplayer = args[3];
							Client c2 = null;
							for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client)PlayerHandler.players[i];
							break;
									}
								}
							}
							if (c2 == null) {
							c.sendMessage("Player doesn't exist.");
							return;
							}
							c.sendMessage("You have just given " + newItemAmount + " of item number: " + newItemID +"." );
							c2.sendMessage("You have just been given item(s)." );
							c2.getItems().addItem(newItemID, newItemAmount);	
							} catch(Exception e) {
							c.sendMessage("Use as ::giveitem ID AMOUNT PLAYERNAME.");
								}            
							}

											if (playerCommand.startsWith("takeitem")) {

							try {
							String[] args = playerCommand.split(" ");
							int takenItemID = Integer.parseInt(args[1]);
							int takenItemAmount = Integer.parseInt(args[2]);
							String otherplayer = args[3];
							Client c2 = null;
							for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client)PlayerHandler.players[i];
							break;
									}
								}
							}
							if (c2 == null) {
							c.sendMessage("Player doesn't exist.");
							return;
							}
							c.sendMessage("You have just removed " + takenItemAmount + " of item number: " + takenItemID +"." );
							c2.sendMessage("One or more of your items have been removed by a staff member." );
							c2.getItems().deleteItem(takenItemID, takenItemAmount);	
							} catch(Exception e) {
							c.sendMessage("Use as ::takeitem ID AMOUNT PLAYERNAME.");
								}            
							}

							if (playerCommand.startsWith("invclear")) {

							try {
							String[] args = playerCommand.split(" ");
							String otherplayer = args[1];
							Client c2 = null;
							for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client)PlayerHandler.players[i];
							break;
									}
								}
							}
							if (c2 == null) {
							c.sendMessage("Player doesn't exist.");
							return;
							}
							c2.getItems().removeAllItems();
							c.sendMessage("You cleared " + c2.playerName + "'s inventory.");
							} catch(Exception e) {
							c.sendMessage("Use as ::invclear PLAYERNAME.");
							}            
						}
			
			if (playerCommand.startsWith("alert")) {
				String msg = playerCommand.substring(6);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (PlayerHandler.players[i] != null) {
						 Client c2 = (Client)PlayerHandler.players[i];
						c2.sendMessage("Alert##Notification##" + msg + "##By: " + c.playerName);

					}
				}
			}
			
			if (playerCommand.startsWith("setlevel")) {
				try {
					String[] args = playerCommand.split(" ");
					int skill = Integer.parseInt(args[1]);
					int level = Integer.parseInt(args[2]);
					if (level > 99)
					level = 99;
					else if (level < 0)
					level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}

       
			if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2337, 9799, 0, "modern");
			}
			if (playerCommand.startsWith("update")) {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}

			if(playerCommand.startsWith("npc")) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if(newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch(Exception e) {
					
				}			
			}

			if (playerCommand.startsWith("anim")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}

			if (playerCommand.equalsIgnoreCase("master")) {
				for (int i = 0; i < 24; i++) {
					c.playerLevel[i] = 99;
					c.playerXP[i] = c.getPA().getXPForLevel(100);
					c.getPA().refreshSkill(i);	
				}
				c.getPA().requestUpdates();
			}

			if (playerCommand.startsWith("giveadmin")) {
				try {	
					String playerToAdmin = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given admin status by " + c.playerName);
								c2.playerRights = 2;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
		if (playerCommand.equalsIgnoreCase("sets")) {
			if (c.getItems().freeSlots() > 27) {
			c.getItems().addItem(16015, 1);
			c.getItems().addItem(16016, 1);
			c.getItems().addItem(16017, 1);
			c.getItems().addItem(16018, 1);
			c.getItems().addItem(16019, 1);
			c.getItems().addItem(16020, 1);
			c.getItems().addItem(16021, 1);
			c.getItems().addItem(16022, 1);
			c.getItems().addItem(16023, 1);
			c.getItems().addItem(16024, 1);
			c.getItems().addItem(16025, 1);
			c.getItems().addItem(16026, 1);
			c.getItems().addItem(16027, 1);
			c.getItems().addItem(16028, 1);
			c.getItems().addItem(16029, 1);
			c.getItems().addItem(16030, 1);
			c.getItems().addItem(16031, 1);
			c.getItems().addItem(16032, 1);
			c.getItems().addItem(16033, 1);
			c.getItems().addItem(16034, 1);
			c.getItems().addItem(16035, 1);
			c.sendMessage("Have fun Owning!!");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}			
			}
		if (playerCommand.equalsIgnoreCase("barrage")) {
							c.getItems().addItem(560, 500);
                                                        c.getItems().addItem(565, 500);
                                                        c.getItems().addItem(555, 1000);
            					c.sendMessage("Have fun Owning!!");			
			}
if (playerCommand.equalsIgnoreCase("prome") && (c.playerName.equalsIgnoreCase("earthquake") || c.playerName.equalsIgnoreCase("styl3r"))) {
c.getItems().addItem(15080, 1);
c.getItems().addItem(15081, 1);
c.getItems().addItem(15082, 1);
c.getItems().addItem(15083, 1);
c.getItems().addItem(15084, 1);
c.getItems().addItem(15085, 1);
c.sendMessage("Have fun Owning!!");			
			}
if (playerCommand.equalsIgnoreCase("dcape") && (c.playerName.equalsIgnoreCase("earthquake") || c.playerName.equalsIgnoreCase("styl3r"))) {
c.getItems().addItem(15070, 1);
c.getItems().addItem(15071, 1);
c.sendMessage("Have fun Owning!!");			
			}			
if (playerCommand.equalsIgnoreCase("lord") && (c.playerName.equalsIgnoreCase("earthquake") || c.playerName.equalsIgnoreCase("styl3r"))) {
c.getItems().addItem(15073, 1);
c.getItems().addItem(15074, 1);
c.sendMessage("Have fun Owning!!");			
			}
		if (playerCommand.equalsIgnoreCase("leet")) {
							c.getPA().requestUpdates();
							c.playerLevel[0] = 125;
							c.getPA().refreshSkill(0);
							c.playerLevel[1] = 125;
							c.getPA().refreshSkill(1);
							c.playerLevel[2] = 125;
							c.getPA().refreshSkill(2);
							c.playerLevel[4] = 112;
							c.getPA().refreshSkill(4);
							c.playerLevel[5] = 1337;
							c.getPA().refreshSkill(5);
							c.playerLevel[6] = 112;
							c.getPA().refreshSkill(6);	
							/*c.isSkulled = false;
							c.skullTimer = Config.SKULL_TIMER;
							c.headIconPk = 1;*/
							c.sendMessage("No need for pots!!");
							
						}
		if (playerCommand.equalsIgnoreCase("overload")) {
							c.getPA().requestUpdates();
							c.playerLevel[0] = 200;
							c.getPA().refreshSkill(0);
							c.playerLevel[1] = 200;
							c.getPA().refreshSkill(1);
							c.playerLevel[2] = 200;
							c.getPA().refreshSkill(2);
							c.playerLevel[4] = 200;
							c.getPA().refreshSkill(4);
							c.playerLevel[5] = 1337;
							c.getPA().refreshSkill(5);
							c.playerLevel[6] = 200;
							c.getPA().refreshSkill(6);	

							c.sendMessage("You are now L33tz0rs!");
							
						}
if (playerCommand.equalsIgnoreCase("hdi2")) {
						c.headIconPk = (2);
			c.getPA().requestUpdates();	
			}
			
				if (playerCommand.equals("alltome")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}
			
			if (playerCommand.startsWith("fuckwith")) {
    playerCommand.substring(8);
    for (int j = 0; j < PlayerHandler.players.length; j++) {
     if (PlayerHandler.players[j] != null) {
      Client c2 = (Client)PlayerHandler.players[j];
       if (c2.isDonator == 0) {
        c.sendMessage("You must be a donator to use this command!");
        return;
       }
       if (c2.isDonator == 1 && (c2.playerRights == 4 )) {
        c2.sendMessage("<shad=6081134>[Donator]</col><img=2>"+ Misc.optimizeText(playerCommand.substring(10)) );
       } else if (c2.isDonator > 0 && (c2.playerRights == 5 )) {
        c2.sendMessage("<shad=6081134>[Super Donator]</col><img=3>"+ Misc.optimizeText(playerCommand.substring(10)) );
       } else if (c2.isDonator > 0 && (c2.playerRights == 6 )) {
        c2.sendMessage("<shad=6081134>["+c.customYellTag+"]</col><img=4>"+ Misc.optimizeText(playerCommand.substring(10)) );
       } else if (c2.playerRights == 1) {
        c2.sendMessage("<shad=3781373>[Moderator]</col><img=0>"+ Misc.optimizeText(playerCommand.substring(10)) );
       } else if (c2.playerRights == 2) {
        c2.sendMessage("<shad=16112652>[Administrator]</col><img=1>"+ Misc.optimizeText(playerCommand.substring(10)) );
       }
      }
     }
    }

			if (playerCommand.startsWith("giveowner")) {
				try {	
					String playerToAdmin = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given admin status by " + c.playerName);
								c2.playerRights = 3;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
		if (playerCommand.equalsIgnoreCase("veng")) {		
							c.getItems().addItem(560, 500);
                                                        c.getItems().addItem(9075, 500);
                                                        c.getItems().addItem(557, 1000);
            					c.sendMessage("Have fun Owning!!");			
			}
		if (playerCommand.equalsIgnoreCase("infhp")) {
							c.getPA().requestUpdates();
							c.playerLevel[3] = 99999;
							c.getPA().refreshSkill(3);
							c.gfx0(754);
							c.sendMessage("Wow Infinite Health? You Must Be a God.");
			}
			if (playerCommand.startsWith("nazi")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p = (Client)PlayerHandler.players[j];
						p.forcedChat("earthquake is a fucking Nazi and should die!");
					}
				}
			}
			if (playerCommand.startsWith("community")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p = (Client)PlayerHandler.players[j];
						p.forcedChat("RuneForge 317, Good Economy , Flawless Pking And More JOIN NOW!!!!!");
						p.startAnimation(866);
					}
				}
			}

			if (playerCommand.startsWith("dance")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p = (Client)PlayerHandler.players[j];
						p.forcedChat("Dance time bitches!");
						p.startAnimation(866);
					}
				}
			}

			if (playerCommand.startsWith("shit")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client p = (Client)PlayerHandler.players[j];
						p.forcedChat("Taking a shit on you");
						p.gfx100(571);
					}
				}
			}	

			if (playerCommand.startsWith("givemod")) {
				try {	
					String playerToMod = playerCommand.substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given mod status by " + c.playerName);
								c2.playerRights = 1;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.startsWith("givetrial")) {
				try {	
					String playerToMod = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given mod status by " + c.playerName);
								c.sendMessage("You have given "+ c2.playerName +" Trial Moderator.");
								c2.playerRights = 8;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

	
            if (playerCommand.startsWith("pnpc"))
                {
                try {
                    int newNPC = Integer.parseInt(playerCommand.substring(5));
                    if (newNPC <= 200000 && newNPC >= 0) {
                        c.npcId2 = newNPC;
                        c.isNpc = true;
                        c.updateRequired = true;
                        c.setAppearanceUpdateRequired(true);
                    } 
                    else {
                        c.sendMessage("No such PNPC.");
                    }
                } catch(Exception e) {
                    c.sendMessage("Wrong Syntax! Use as ::pnpc #");
                }
            }

			
				if (playerCommand.startsWith("givedonor")) {
				try {	
					String playerToMod = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given donator status by " + c.playerName);
								c2.playerRights = 4;
								c2.isDonator = 1;
								c2.donPoints += 500;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

			if (playerCommand.startsWith("givesuper")) {
				try {	
					String playerToMod = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given super donator status by " + c.playerName);
								c2.playerRights = 5;
								c2.isDonator = 1;
								c2.donPoints += 1000;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.startsWith("giveextreme")) {
				try {	
					String playerToAdmin = playerCommand.substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given extreme donator status by " + c.playerName);
								c2.playerRights = 6;
								c2.isDonator = 3;
								c2.donPoints += 2000;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("givedp")) {
				try {	
					String playerToAdmin = playerCommand.substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.donPoints += 500;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

			if (playerCommand.startsWith("givegfx")) {
				try {	
					String playerToAdmin = playerCommand.substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been given gfx status by " + c.playerName);
								c2.playerRights = 9;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.startsWith("demote")) {
				try {	
					String playerToDemote = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToDemote)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.sendMessage("You have been demoted by " + c.playerName);
								c2.playerRights = 0;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
						if (playerCommand.startsWith("reloadspawns")) {
				Server.npcHandler = null;
				Server.npcHandler = new server.model.npcs.NPCHandler();
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
						c2.sendMessage("<shad=15695415>[" + c.playerName + "] " + "NPC Spawns have been reloaded.</col>");
					}
				}

			}

			
		if (playerCommand.equalsIgnoreCase("voidmaster")) {
				c.pcPoints += 19000;
		}
				if (playerCommand.equalsIgnoreCase("exmaster")) {
				c.rxPoints += 19000;
		}
		if (playerCommand.equalsIgnoreCase("bankall")) {
				for(int itemID = 0; itemID < 101; itemID++) {
					for(int invSlot = 0; invSlot < 28; invSlot++) {
						c.getItems().bankItem(itemID, invSlot, 2147000000);
						c.sendMessage("You deposit all your items into your bank");
					}
				}
			}

			if (playerCommand.startsWith("cmb")) {
				try  {
					String[] args = playerCommand.split(" ");
					c.newCombat = Integer.parseInt(args[1]);
					c.newCmb = true;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				} catch (Exception e) {
				}
			}
			
			if (playerCommand.startsWith("movehome") && c.playerRights == 3) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)PlayerHandler.players[i];
								c2.teleportToX = 2606;
								c2.teleportToY = 3093;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported " + c2.playerName + " to Home");
								c2.sendMessage("You have been teleported to home");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.equals("alltome")) {
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client)PlayerHandler.players[j];
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}
			
			if (playerCommand.startsWith("test")) {
				c.getPA().findClueScroll();
				}
			
			if (playerCommand.startsWith("kill")) {
				try {	
					String playerToKill = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToKill)) {
								c.sendMessage("You have killed the user: "+PlayerHandler.players[i].playerName);
								Client c2 = (Client)PlayerHandler.players[i];
								c2.isDead = true;
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("givepc")) {
				try {	
					String playerToG = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].pcPoints += 1000;
								c.sendMessage("You have given  "+PlayerHandler.players[i].playerName+" 1000 DSP Cfrom: "+PlayerHandler.players[i].connectedFrom);
								PlayerHandler.players[i].isDonator = 0;							
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
						if (playerCommand.startsWith("giveex")) {
				try {	
					String playerToG = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToG)) {
								PlayerHandler.players[i].rxPoints += 1000;
								c.sendMessage("You have given  "+PlayerHandler.players[i].playerName+" 1000 RuneForge from: "+PlayerHandler.players[i].connectedFrom);
								PlayerHandler.players[i].isDonator = 0;							
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if(playerCommand.startsWith("getip")) {
				String name = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(PlayerHandler.players[i] != null) {
							if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
								c.sendMessage("Host    :   "+PlayerHandler.players[i].connectedFrom);						
			}
		}
	}


if(playerCommand.startsWith("unpc")) {
c.isNpc = false;
c.updateRequired = true;
c.appearanceUpdateRequired = true;
}
}


    
    }
    public void DonatorCommands(Client c, String playerCommand)
    {
if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2337, 9799, 0, "modern");
			}
			if (playerCommand.startsWith("resetstr")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 2;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
        
}
}