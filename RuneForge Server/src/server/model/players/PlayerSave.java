package server.model.players;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.util.Misc;

public class PlayerSave
{

	
	
	/**
	*Loading
	**/
	public static int loadGame(Client p, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;
		
		try {
			characterfile = new BufferedReader(new FileReader("./Data/characters/"+playerName+".txt"));
			File1 = true;
		} catch(FileNotFoundException fileex1) {
		}
		
		if (File1) {
			//new File ("./Data/characters/"+playerName+".txt");
		} else {
			Misc.println(playerName+": character file not found.");
			p.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(playerName+": Error loading file!");
			return 3;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
				case 1:
					 if (token.equals("character-password")) {
						if (playerPass.equalsIgnoreCase(token2) || Misc.basicEncrypt(playerPass).equals(token2)) {
							playerPass = token2;
						} else {
							return 3;
						}
					}
					break;
				case 2:
					if (token.equals("character-height")) {
						p.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("character-posx")) {
						p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
					} else if (token.equals("character-posy")) {
						p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
					} else if (token.equals("coinBag")) { 
						p.coinBag = Integer.parseInt(token2);
					} else if (token.equals("character-rights")) {
						p.playerRights = Integer.parseInt(token2);
					} else if (token.equals("character-title")) {
						p.playerTitle = Integer.parseInt(token2);
					} else if (token.equals("character-yellTag")) {
						p.customYellTag = token2;
                    } else if(token.equals("connected-from")) {
                        			p.lastConnectedFrom.add(token2); 
					} else if (token.equals("tutorial-progress")) {
						p.tutorial = Integer.parseInt(token2);	
					} else if (token.equals("crystal-bow-shots")) {
						p.crystalBowArrowCount = Integer.parseInt(token2);
					} else if (token.equals("skull-timer")) {
						p.skullTimer = Integer.parseInt(token2);
			        } else if (token.equals("ag1")) {
						p.ag1 = Integer.parseInt(token2);
					} else if (token.equals("ag2")) {
						p.ag2 = Integer.parseInt(token2);
					} else if (token.equals("ag3")) {
						p.ag3 = Integer.parseInt(token2);
					} else if (token.equals("ag4")) {
						p.ag4 = Integer.parseInt(token2);
					} else if (token.equals("ag5")) {
						p.ag5 = Integer.parseInt(token2);
					} else if (token.equals("ag6")) {
						p.ag6 = Integer.parseInt(token2);
					} else if (line.startsWith("KC")) {
						p.KC = Integer.parseInt(token2);
					} else if (line.startsWith("totalstored")) {
						p.totalstored = Integer.parseInt(token2);
					} else if (line.startsWith("DC")) {
						p.DC = Integer.parseInt(token2);
					} else if (line.startsWith("home")) {
						p.home = Integer.parseInt(token2);
					} else if (token.equals("mute")) {
						p.mute = Long.parseLong(token2);
					} else if (token.equals("catChosen")) {
						p.catChosen = Integer.parseInt(token2);
	 if (token.equals("bankPin")) {
		p.bankPin = token2;
	 }
	 if (token.equals("setPin")) {
		p.setPin = Boolean.parseBoolean(token2);
	}
					} else if (token.equals("has-npc")) {
						p.hasNpc = Boolean.parseBoolean(token2);
					} else if (token.equals("summonId")) {
						p.summonId = Integer.parseInt(token2);
					} else if (token.equals("EP")) {
						p.earningPotential = Integer.parseInt(token2);
					} else if (token.equals("magic-book")) {
						p.playerMagicBook = Integer.parseInt(token2);
					} else if (token.equals("xpLock")) {
						p.xpLock = Boolean.parseBoolean(token2);
					} else if (token.equals("cursePrayers")) {
						p.cursePrayers = Boolean.parseBoolean(token2);
					} else if (token.equals("don-points")) {
						p.donPoints = Integer.parseInt(token2);
										} else if (token.equals("sr-points")) {
						p.srPoints = Integer.parseInt(token2);
					} else if (token.equals("newHp")) {
						p.newHp = Boolean.parseBoolean(token2);
					} else if (token.equals("needsStarter")) {
						p.needsStarter = Boolean.parseBoolean(token2);
				    } else if (token.equals("newPray")) {
						p.newPray = Boolean.parseBoolean(token2);
					} else if (token.equals("Jailed")) {
						p.Jail = Boolean.parseBoolean(token2);
					} else if (token.equals("Agrith")) {
						p.Agrith = Boolean.parseBoolean(token2);
					} else if (token.equals("vls-hits")) {
					        p.degradeTime = Integer.parseInt(token2);
					} else if (token.equals("objectSave")) { //save construction object true ? false
						p.objectSave = Boolean.parseBoolean(token2);
					} else if (token.equals("Flambeed")) {
						p.Flambeed = Boolean.parseBoolean(token2);
					} else if (token.equals("Karamel")) {
						p.Karamel = Boolean.parseBoolean(token2);
					} else if (token.equals("Dessourt")) {
						p.Dessourt = Boolean.parseBoolean(token2);
					} else if (token.equals("culin")) {
						p.Culin = Boolean.parseBoolean(token2);
					} else if (token.equals("Monkey-Kc")) {
						p.monkeyk0ed = Integer.parseInt(token2);
					} else if (token.equals("loyaltyPoints")) {
						p.loyaltyPoints = Integer.parseInt(token2);
					} else if (token.equals("brother-info")) {
						p.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
					} else if (token.equals("special-amount")) {
						p.specAmount = Double.parseDouble(token2);
					} else if (token.equals("sumspec-amount")) {
						p.summAmount = Double.parseDouble(token2);						
					} else if (token.equals("selected-coffin")) {
						p.randomCoffin = Integer.parseInt(token2);	
					} else if (token.equals("barrows-killcount")) {
						p.pkPoints = Integer.parseInt(token2);							
					} else if (token.equals("teleblock-length")) {
						p.teleBlockDelay = System.currentTimeMillis();
						p.teleBlockLength = Integer.parseInt(token2);							
					} else if (token.equals("pc-points")) {
						p.pcPoints = Integer.parseInt(token2);
					} else if (token.equals("npc-kills")) {
						p.npcKills = Integer.parseInt(token2);
					} else if (token.equals("rx-points")) {
						p.rxPoints = Integer.parseInt(token2);	
				    } else if (token.equals("killStreak")) {
						p.killStreak = Integer.parseInt(token2);
					} else if (token.equals("deathStreak")) {
						p.deathStreak = Integer.parseInt(token2);
					} else if (token.equals("gwdelay")) {
						p.gwdelay = Integer.parseInt(token2);
					} else if (token.equals("Altar")) {
						p.altarPrayed = Integer.parseInt(token2);
					} else if (token.equals("Dung-KC")) {
						p.Dung = Integer.parseInt(token2);
					} else if (token.equals("Arma-KC")) {
						p.Arma = Integer.parseInt(token2);	
					} else if (token.equals("Band-KC")) {
						p.Band = Integer.parseInt(token2);	
					} else if (token.equals("Zammy-KC")) {
						p.Zammy = Integer.parseInt(token2);	
					} else if (token.equals("Sara-KC")) {
						p.Sara = Integer.parseInt(token2);	
					} else if (token.equals("pk-points")) {
						p.pkPoints = Integer.parseInt(token2);	
					} else if (token.equals("isDonator")) {
						p.isDonator = Integer.parseInt(token2);
											} else if (token.equals("donatorChest")) {
						p.donatorChest = Integer.parseInt(token2);	
					} else if (token.equals("dungLevel")) {
						p.dungLevel = Integer.parseInt(token2);
					} else if (token.equals("slayerTask")) {
						p.slayerTask = Integer.parseInt(token2);
					} else if (token.equals("Hastarter")) {
						p.Hastarter = Integer.parseInt(token2);		
					} else if (token.equals("isnew")) {
						p.isnew = Integer.parseInt(token2);							
					} else if (token.equals("taskAmount")) {
						p.taskAmount = Integer.parseInt(token2);					
					} else if (token.equals("magePoints")) {
						p.magePoints = Integer.parseInt(token2);					
					} else if (token.equals("autoRet")) {
						p.autoRet = Integer.parseInt(token2);
					} else if (token.equals("quickCurses2")) {
						for (int j = 0; j < token3.length; j++) {
							p.quickCurses2[j] = Boolean.parseBoolean(token3[j]);						
						}
					} else if (token.equals("quickPrayers2")) {
						for (int j = 0; j < token3.length; j++) {
							p.quickPrayers2[j] = Boolean.parseBoolean(token3[j]);						
						}
					} else if (token.equals("trade11")) {
						p.trade11 = Integer.parseInt(token2);
					} else if (token.equals("SpeDelay")) {
						p.SpecialDelay = Integer.parseInt(token2);
					} else if (token.equals("barrowskillcount")) {
						p.barrowsKillCount = Integer.parseInt(token2);
					} else if (token.equals("flagged")) {
						p.accountFlagged = Boolean.parseBoolean(token2);
					} else if (token.equals("wave")) {
						p.waveId = Integer.parseInt(token2);
					} else if (token.equals("void")) {
						for (int j = 0; j < token3.length; j++) {
							p.voidStatus[j] = Integer.parseInt(token3[j]);						
						}
					} else if (token.equals("fightMode")) {
						p.fightMode = Integer.parseInt(token2);
					} else if (token.equals("lastsummon")) {
						p.lastsummon = Integer.parseInt(token2);
					} else if (token.equals("summonTime")) {
						p.summonTime = Integer.parseInt(token2);
					} else if (line.startsWith("QP1")) {
						p.QP1 = Integer.parseInt(token2);
					} else if (line.startsWith("QP2")) {
						p.QP2 = Integer.parseInt(token2);
					} else if (line.startsWith("QP3")) {
						p.QP3 = Integer.parseInt(token2);
					} else if (token.equals("forinthryBrace")) {
						p.forinthryBrace = Integer.parseInt(token2);

					} else if (token.equals("summoningnpcid")) {
						p.summoningnpcid = Integer.parseInt(token2);
					}
					break;
				case 3:
					if (token.equals("character-equip")) {
						p.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("character-look")) {
						p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					} 
					break;
				case 5:
					if (token.equals("character-skill")) {
						p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("character-item")) {
						p.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("character-bank")) {
						p.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 8:
					 if (token.equals("character-friend")) {
						p.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} 
					break;
				case 9:
					/* if (token.equals("character-ignore")) {
						ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} */
					break;

				case 20:
					if (token.equals("stored")) {
						p.bobItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					}
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {		ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {	ReadMode = 2;
                                 } else if (line.equals("[AGILITY]")) {	ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {	ReadMode = 3;
				} else if (line.equals("[LOOK]")) {		ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {		ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {		ReadMode = 6;
				} else if (line.equals("[BANK]")) {		ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {		ReadMode = 8;
				} else if (line.equals("[IGNORES]")) {		ReadMode = 9;
} else if (line.equals("[STORED]")) {		ReadMode = 20;
} else if (line.equals("[OCCUPY]")) {		ReadMode = 21;
} else if (line.equals("[SHOP]")) { ReadMode = 10;

				} else if (line.equals("[EOF]")) {		try { characterfile.close(); } catch(IOException ioexception) { } return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return 13;
	}
	
	
	
	
	/**
	*Saving
	**/
	public static boolean saveGame(Client p) {
		if(!p.saveFile || p.newPlayer || !p.saveCharacter) {
			//System.out.println("first");
			return false;
		}
		if(p.playerName == null || PlayerHandler.players[p.playerId] == null) {
			//System.out.println("second");
			return false;
		}
		p.playerName = p.playerName2;
		int tbTime = (int)(p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
		if(tbTime > 500000 || tbTime < 0){
			tbTime = 0;
		}
		
		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter("./Data/characters/"+p.playerName+".txt"));
			
			/*ACCOUNT*/
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(p.playerName, 0, p.playerName.length());
			characterfile.newLine();
			characterfile.write("character-password = ", 0, 21);
			characterfile.write(p.playerPass, 0, p.playerPass.length());
			characterfile.newLine();
			characterfile.newLine();

			/*CHARACTER*/
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
			characterfile.newLine();
			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
			characterfile.newLine();
			characterfile.write("coinBag = ", 0, 10);
			characterfile.write(Integer.toString(p.coinBag), 0, Integer.toString(p.coinBag).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
			characterfile.newLine();
			characterfile.write("character-title = ", 0, 18);
			characterfile.write(Integer.toString(p.playerTitle), 0, Integer.toString(p.playerTitle).length());
			characterfile.newLine();
			characterfile.write("character-yellTag = ", 0, 20);
			characterfile.write(p.customYellTag, 0, p.customYellTag.length());
			characterfile.newLine();
            for (int i = 0; i < p.lastConnectedFrom.size(); i++) {
                characterfile.write("connected-from = ", 0, 17);
                characterfile.write(p.lastConnectedFrom.get(i), 0, p.lastConnectedFrom.get(i).length());
                characterfile.newLine();
            } 
			characterfile.write("crystal-bow-shots = ", 0, 20);
			characterfile.write(Integer.toString(p.crystalBowArrowCount), 0, Integer.toString(p.crystalBowArrowCount).length());
			characterfile.newLine();
			characterfile.write("VLS-hits = ", 0, 11);
			characterfile.write(Integer.toString(p.degradeTime), 0, Integer.toString(p.degradeTime).length());
			characterfile.newLine(); 
			characterfile.write("objectSave = ", 0, 13);
			characterfile.write(Boolean.toString(p.objectSave), 0, Boolean.toString(p.objectSave).length());
			characterfile.newLine();
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(p.skullTimer), 0, Integer.toString(p.skullTimer).length());
			characterfile.newLine();
			characterfile.write("[AGILITY]", 0, 9);
			characterfile.newLine();
			characterfile.write("ag1 = ", 0, 6);
			characterfile.write(Integer.toString(p.ag1), 0, Integer.toString(p.ag1).length());
			characterfile.newLine();
			characterfile.write("ag2 = ", 0, 6);
			characterfile.write(Integer.toString(p.ag2), 0, Integer.toString(p.ag2).length());
			characterfile.newLine();
			characterfile.write("ag3 = ", 0, 6);
			characterfile.write(Integer.toString(p.ag3), 0, Integer.toString(p.ag3).length());
			characterfile.newLine();
			characterfile.write("ag4 = ", 0, 6);
			characterfile.write(Integer.toString(p.ag4), 0, Integer.toString(p.ag4).length());
			characterfile.newLine();
			characterfile.write("ag5 = ", 0, 6);
			characterfile.write(Integer.toString(p.ag5), 0, Integer.toString(p.ag5).length());
			characterfile.newLine();
			characterfile.write("ag6 = ", 0, 6);
			characterfile.write(Integer.toString(p.ag6), 0, Integer.toString(p.ag6).length());
			characterfile.newLine();
			characterfile.write("setPin = ", 0, 9);
			characterfile.write(Boolean.toString(p.setPin), 0, Boolean.toString(p.setPin).length());
			characterfile.newLine();
			characterfile.write("bankPin = ", 0, 10);
			characterfile.write(p.bankPin, 0, p.bankPin.length());
			characterfile.newLine();
			characterfile.write("KC = ", 0, 5);
			characterfile.write(Integer.toString(p.KC), 0, Integer.toString(p.KC).length());
			characterfile.newLine();
			characterfile.write("totalstored = ", 0, 14);
			characterfile.write(Integer.toString(p.totalstored), 0, Integer.toString(p.totalstored).length());
			characterfile.newLine();
			characterfile.write("DC = ", 0, 5);
			characterfile.write(Integer.toString(p.DC), 0, Integer.toString(p.DC).length());
			characterfile.newLine();
			characterfile.write("home = ", 0, 7);
			characterfile.write(Integer.toString(p.home), 0, Integer.toString(p.home).length());
			characterfile.newLine();
			characterfile.write("catChosen = ", 0, 12);
			characterfile.write(Integer.toString(p.catChosen), 0, Integer.toString(p.catChosen).length());
			characterfile.newLine();
			characterfile.write("has-npc = ", 0, 10);
			characterfile.write(Boolean.toString(p.hasNpc), 0, Boolean.toString(p.hasNpc).length());
			characterfile.newLine();
			characterfile.write("summonId = ", 0, 11);
			characterfile.write(Integer.toString(p.summonId), 0, Integer.toString(p.summonId).length());
			characterfile.newLine();
			characterfile.write("EP = ", 0, 5);
			characterfile.write(Integer.toString(p.earningPotential), 0, Integer.toString(p.earningPotential).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer.toString(p.playerMagicBook).length());
			characterfile.newLine();
			for (int b = 0; b < p.barrowsNpcs.length; b++) {
				characterfile.write("brother-info = ", 0, 15);
				characterfile.write(Integer.toString(b), 0, Integer.toString(b).length());
				characterfile.write("	", 0, 1);
				characterfile.write(p.barrowsNpcs[b][1] <= 1 ? Integer.toString(0) : Integer.toString(p.barrowsNpcs[b][1]), 0, Integer.toString(p.barrowsNpcs[b][1]).length());
				characterfile.newLine();
			}	
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(p.specAmount), 0, Double.toString(p.specAmount).length());
			characterfile.newLine();
			characterfile.write("sumspec-amount = ", 0, 17);
			characterfile.write(Double.toString(p.summAmount), 0, Double.toString(p.summAmount).length());
			characterfile.newLine();
			characterfile.write("selected-coffin = ", 0, 18);
			characterfile.write(Integer.toString(p.randomCoffin), 0, Integer.toString(p.randomCoffin).length());
			characterfile.newLine();
			characterfile.write("barrows-killcount = ", 0, 20);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("pc-points = ", 0, 12);
			characterfile.write(Integer.toString(p.pcPoints), 0, Integer.toString(p.pcPoints).length());
			characterfile.newLine();
			characterfile.write("npc-kills = ", 0, 12);
			characterfile.write(Integer.toString(p.npcKills), 0, Integer.toString(p.npcKills).length());
			characterfile.newLine();
			characterfile.write("rx-points = ", 0, 12);
			characterfile.write(Integer.toString(p.rxPoints), 0, Integer.toString(p.rxPoints).length());
			characterfile.newLine();
						characterfile.write("killStreak = ", 0, 13);
			characterfile.write(Integer.toString(p.killStreak), 0, Integer.toString(p.killStreak).length());
			characterfile.newLine();
									characterfile.write("deathStreak = ", 0, 14);
			characterfile.write(Integer.toString(p.deathStreak), 0, Integer.toString(p.deathStreak).length());
			characterfile.newLine();
			characterfile.write("gwdelay = ", 0, 10);
			characterfile.write(Integer.toString(p.gwdelay), 0, Integer.toString(p.gwdelay).length());
			characterfile.newLine();
			characterfile.write("Altar = ", 0, 8);
			characterfile.write(Integer.toString(p.altarPrayed), 0, Integer.toString(p.altarPrayed).length());
			characterfile.newLine();
			characterfile.write("Hastarter = ", 0, 12);
			characterfile.write(Integer.toString(p.Hastarter), 0, Integer.toString(p.Hastarter).length());
			characterfile.newLine();
			characterfile.write("isnew = ", 0, 8);
			characterfile.write(Integer.toString(p.isnew), 0, Integer.toString(p.isnew).length());
			characterfile.newLine();
			characterfile.write("Dung-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Dung), 0, Integer.toString(p.Dung).length());
			characterfile.newLine();
			characterfile.write("Arma-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Arma), 0, Integer.toString(p.Arma).length());
			characterfile.newLine();
			characterfile.write("Band-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Band), 0, Integer.toString(p.Band).length());
			characterfile.newLine();
			characterfile.write("Zammy-KC = ", 0, 11);
			characterfile.write(Integer.toString(p.Zammy), 0, Integer.toString(p.Zammy).length());
			characterfile.newLine();
			characterfile.write("Sara-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Sara), 0, Integer.toString(p.Sara).length());
			characterfile.newLine();
			characterfile.write("pk-points = ", 0, 12);
			characterfile.write(Integer.toString(p.pkPoints), 0, Integer.toString(p.pkPoints).length());
			characterfile.newLine();
			characterfile.write("isDonator = ", 0, 12);
			characterfile.write(Integer.toString(p.isDonator), 0, Integer.toString(p.isDonator).length());
			characterfile.newLine();
			characterfile.write("don-points = ", 0, 13);
			characterfile.write(Integer.toString(p.donPoints), 0, Integer.toString(p.donPoints).length());
			characterfile.newLine();
						characterfile.write("sr-points = ", 0, 12);
			characterfile.write(Integer.toString(p.srPoints), 0, Integer.toString(p.srPoints).length());
			characterfile.newLine();
						characterfile.write("donatorChest = ", 0, 15);
			characterfile.write(Integer.toString(p.donatorChest), 0, Integer.toString(p.donatorChest).length());
			characterfile.newLine();
			characterfile.write("dungLevel = ", 0, 12);
			characterfile.write(Integer.toString(p.dungLevel), 0, Integer.toString(p.dungLevel).length());
			characterfile.newLine();
			characterfile.write("slayerTask = ", 0, 13);
			characterfile.write(Integer.toString(p.slayerTask), 0, Integer.toString(p.slayerTask).length());
			characterfile.newLine();
			characterfile.write("xpLock = ", 0, 9);
			characterfile.write(Boolean.toString(p.xpLock), 0, Boolean.toString(p.xpLock).length());
			characterfile.newLine();
			characterfile.write("cursePrayers = ", 0, 15);
			characterfile.write(Boolean.toString(p.cursePrayers), 0, Boolean.toString(p.cursePrayers).length());
			characterfile.newLine();
			characterfile.write("newHp = ", 0, 8);
			characterfile.write(Boolean.toString(p.newHp), 0, Boolean.toString(p.newHp).length());
			characterfile.newLine();
						characterfile.write("needsStarter = ", 0, 15);
			characterfile.write(Boolean.toString(p.needsStarter), 0, Boolean.toString(p.needsStarter).length());
			characterfile.newLine();
						characterfile.write("newPray = ", 0, 8);
			characterfile.write(Boolean.toString(p.newPray), 0, Boolean.toString(p.newPray).length());
			characterfile.newLine();
			characterfile.write("Agrith = ", 0, 9);
			characterfile.write(Boolean.toString(p.Agrith), 0, Boolean.toString(p.Agrith).length());
			characterfile.newLine();
			characterfile.write("Flambeed = ", 0, 11);
			characterfile.write(Boolean.toString(p.Flambeed), 0, Boolean.toString(p.Flambeed).length());
			characterfile.newLine();
			characterfile.write("Karamel = ", 0, 10);
			characterfile.write(Boolean.toString(p.Karamel), 0, Boolean.toString(p.Karamel).length());
			characterfile.newLine();
			characterfile.write("Dessourt = ", 0, 11);
			characterfile.write(Boolean.toString(p.Dessourt), 0, Boolean.toString(p.Dessourt).length());
			characterfile.newLine();
			characterfile.write("culin = ", 0, 8);
			characterfile.write(Boolean.toString(p.Culin), 0, Boolean.toString(p.Culin).length());
			characterfile.newLine();
			characterfile.write("taskAmount = ", 0, 13);
			characterfile.write(Integer.toString(p.taskAmount), 0, Integer.toString(p.taskAmount).length());
			characterfile.newLine();
			characterfile.write("magePoints = ", 0, 13);
			characterfile.write(Integer.toString(p.magePoints), 0, Integer.toString(p.magePoints).length());
			characterfile.newLine();
			characterfile.write("mute = ", 0, 7);
			characterfile.write(Long.toString(p.mute), 0, Long.toString(p.mute).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(p.autoRet), 0, Integer.toString(p.autoRet).length());
			characterfile.newLine();
			characterfile.write("trade11 = ", 0, 10);
			characterfile.write(Integer.toString(p.trade11), 0, Integer.toString(p.trade11).length());
			characterfile.newLine();
			characterfile.write("SpeDelay = ", 0, 11);
			characterfile.write(Long.toString(p.SpecialDelay), 0, Long.toString(p.SpecialDelay).length());
			characterfile.newLine();
			characterfile.write("barrowskillcount = ", 0, 19);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean.toString(p.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("Jailed = ", 0, 9);
			characterfile.write(Boolean.toString(p.Jail), 0, Boolean.toString(p.Jail).length());
			characterfile.newLine();
			characterfile.write("wave = ", 0, 7);
			characterfile.write(Integer.toString(p.waveId), 0, Integer.toString(p.waveId).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(p.fightMode), 0, Integer.toString(p.fightMode).length());
			characterfile.newLine();
			characterfile.write("lastsummon = ", 0, 13);
			characterfile.write(Integer.toString(p.lastsummon), 0, Integer.toString(p.lastsummon).length());
			characterfile.newLine();
			characterfile.write("summonTime = ", 0, 13);
			characterfile.write(Integer.toString(p.summonTime), 0, Integer.toString(p.summonTime).length());
			characterfile.newLine();
			characterfile.write("QP1 = ", 0, 5);
			characterfile.write(Integer.toString(p.QP1), 0, Integer.toString(p.QP1).length());
			characterfile.newLine();
			characterfile.write("QP2 = ", 0, 5);
			characterfile.write(Integer.toString(p.QP2), 0, Integer.toString(p.QP2).length());
			characterfile.newLine();
			characterfile.write("QP3 = ", 0, 5);
			characterfile.write(Integer.toString(p.QP3), 0, Integer.toString(p.QP3).length());
			characterfile.newLine();
			characterfile.write("forinthryBrace = ", 0, 17);
			characterfile.write(Integer.toString(p.forinthryBrace), 0, Integer.toString(p.forinthryBrace).length());
			characterfile.newLine();
			characterfile.write("summoningnpcid = ", 0, 17);
			characterfile.write(Integer.toString(p.summoningnpcid), 0, Integer.toString(p.summoningnpcid).length());
			characterfile.newLine();
			characterfile.write("Monkey-Kc = ", 0, 12);
			characterfile.write(Integer.toString(p.monkeyk0ed), 0, Integer.toString(p.monkeyk0ed).length());
			characterfile.newLine();
			characterfile.write("loyaltyPoints = ", 0, 16);
			characterfile.write(Integer.toString(p.loyaltyPoints), 0, Integer.toString(p.loyaltyPoints).length());
			characterfile.newLine();
			characterfile.write("void = ", 0, 7);
			String toWrite = p.voidStatus[0] + "\t" + p.voidStatus[1] + "\t" + p.voidStatus[2] + "\t" + p.voidStatus[3] + "\t" + p.voidStatus[4];
			characterfile.write(toWrite);
			characterfile.newLine();
			characterfile.newLine();
			
			/*QUICK-PRAYERS*/
			characterfile.write("[QUICKS]", 0, 8);
			characterfile.newLine();
			characterfile.write("quickCurses2 = ", 0, 15);
			String toWrite1 = "";
			for(int i1 = 0; i1 < p.quickCurses2.length; i1++) {
				toWrite1 += p.quickCurses2[i1] +"\t";
			}
			characterfile.write(toWrite1);
			characterfile.newLine();
			characterfile.write("quickPrayers2 = ", 0, 16);
			String toWrite2 = "";
			for(int i1 = 0; i1 < p.quickPrayers2.length; i1++) {
				toWrite2 += p.quickPrayers2[i1] +"\t";
			}
			characterfile.write(toWrite2);
			characterfile.newLine();
			characterfile.newLine();
			
			/*EQUIPMENT*/
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < p.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipment[i]), 0, Integer.toString(p.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0, Integer.toString(p.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*LOOK*/
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*SKILLS*/
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*ITEMS*/
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < p.playerItems.length; i++) {
				if (p.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItems[i]), 0, Integer.toString(p.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItemsN[i]), 0, Integer.toString(p.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/*BANK*/
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.bankItems.length; i++) {
				if (p.bankItems[i] > 0) {
					characterfile.write("character-bank = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItems[i]), 0, Integer.toString(p.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItemsN[i]), 0, Integer.toString(p.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/*FRIENDS*/
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.friends.length; i++) {
				if (p.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

		/*Storeditems*/
			characterfile.write("[STORED]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.bobItems.length; i++) {
				characterfile.write("stored = ", 0, 9);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.bobItems[i]), 0, Integer.toString(p.bobItems[i]).length());
				characterfile.newLine();
			}
	characterfile.newLine();
			
		/*IGNORES*/
			/*characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < ignores.length; i++) {
				if (ignores[i] > 0) {
					characterfile.write("character-ignore = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Long.toString(ignores[i]), 0, Long.toString(ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();*/
		/*EOF*/
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch(IOException ioexception) {
			Misc.println(p.playerName+": error writing file.");
			return false;
		}
		return true;
	}	
	

}