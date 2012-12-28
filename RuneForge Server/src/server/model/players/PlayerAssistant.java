package server.model.players;

import server.Config;
import server.Server;
import server.model.npcs.NPCHandler;
import server.util.Misc;
import java.util.Properties;
import server.model.players.PlayerSave;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import server.event.EventContainer;
import server.event.Event;
import server.event.EventManager;
import server.Connection;
import server.model.players.Client;
import server.model.players.PlayerHandler;


public class PlayerAssistant {

	private Client c;

	public PlayerAssistant(Client Client) {
		this.c = Client;
	}
	
	                      public void compemote(final Client c) {
			    EventManager.getSingleton().addEvent(new Event() {
				int comptime = 28;
				 public void execute(EventContainer comp) {
				   if (comptime == 28) {
					c.startAnimation(13190);
				    }
				   if (comptime == 27) {
					c.npcId2 = 8596;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(11197);
				c.playerStandIndex = 11195;
				    }
				   if (comptime == 23) {
					c.npcId2 = 8597;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(11202);
				c.playerStandIndex = 11200;
				    }
				   if (comptime == 20) {
					c.npcId2 = 8591;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				c.playerStandIndex = 9724;
				    }
				 if (comptime == 17) {
					c.npcId2 = 8281;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				  c.startAnimation(13192);
				c.startAnimation(10680);
				c.startAnimation(10681);
				c.playerStandIndex = 10665;
				    }
				 
				 if (comptime == 13) {
					c.npcId2 = 10224;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13157);
				c.playerStandIndex = 13156;
				    }
				   if (comptime == 11) {
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13152);
					c.gfx100(2465);
				c.playerStandIndex = 13156;
				    }
				   if (comptime == 7) {
					c.npcId2 = 10770;
					 c.isNpc = true;
     				 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13156);
				c.playerStandIndex = 13156;
				    }
				   if (comptime == 0) {
					c.isNpc = false;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				c.playerStandIndex = 0x328;
					c.startAnimation(12567);
				    }
				   if (c == null || comptime <= 0) {
				       comp.stop();
                                                                         return; 
				    }
				   if (comptime >= 0) {
					comptime--;
				    }
				}
			    }, 600);
			}
				public void moveAction(final int x, final int y, final int h, final String type) {
		if (type.equals("up")) {
			c.startAnimation(828);
		} else if (type.equals("down")) {
			c.startAnimation(827);
		}
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer p) {
				c.resetWalkingQueue();
				c.teleportToX = x;
				c.teleportToY = y;
				c.heightLevel = h;
				c.getPA().requestUpdates();
				p.stop();
			}
		}, 600);
	}
	                  	public void moveThroughDoor(final Client c, final int obI, boolean n) {
	                		final int[] coords = new int[2];
	                		final int[] obFace = new int[2];
	                		if(n) {
	                			if(c.absX > c.objectX) {
	                				coords[0] = -1;
	                			} else {
	                				coords[0] = 1;
	                			}
	                			coords[1] = 0;
	                		} else {
	                			if(c.absY > c.objectY || c.absY == c.objectY) {
	                				coords[1] = -1;
	                			} else {
	                				coords[1] = 1;
	                			}
	                			coords[0] = 0;
	                		}
	                		obFace[0] = n ? -1 : -4;
	                		obFace[1] = n ? -2 : -1;
	                		object(obI, c.objectX, c.objectY, obFace[0], 0);
	                		walkTo(coords[0], coords[1]);
	                		EventManager.getSingleton().addEvent(new Event() {
	                			public void execute(EventContainer p) {
	                				object(obI, c.objectX, c.objectY, obFace[1], 0);
	                				p.stop();
	                			}
	                		}, 750);
	                	}
			public void addStarter() {
		if (!Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
                	c.getItems().addItem(560,100000);
                	c.getItems().addItem(557,100000);
                	c.getItems().addItem(9075,100000);
                	c.getItems().addItem(555,100000);
                	c.getItems().addItem(565,100000);
			         c.getItems().addItem(561,100000);
                	c.getItems().addItem(562,100000);
                	c.getItems().addItem(563,100000);
                	c.getItems().addItem(556,100000);
                	c.getItems().addItem(554,100000);
                	c.getItems().addItem(566,100000);
                	c.getItems().addItem(2441,500);
                	c.getItems().addItem(2437,500);
                	c.getItems().addItem(2443,500);
                	c.getItems().addItem(3025,500);
               	    c.getItems().addItem(6686,500);
               	    c.getItems().addItem(3041,500);
               	    c.getItems().addItem(2445,500);
               	    c.getItems().addItem(2447,500);
               	    c.getItems().addItem(386,1000);
               	    c.getItems().addItem(392,1000);
               	    c.getItems().addItem(15273,500);
			Connection.addIpToStarterList1(PlayerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter1(PlayerHandler.players[c.playerId].connectedFrom);
			c.sendMessage("You have recieved 1 out of 2 starter packages on this IP address.");
		} else if (Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom) && !Connection.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
                	c.getItems().addItem(560,50000);
                	c.getItems().addItem(557,50000);
                	c.getItems().addItem(9075,50000);
                	c.getItems().addItem(555,50000);
                	c.getItems().addItem(565,50000);
			         c.getItems().addItem(561,50000);
                	c.getItems().addItem(562,50000);
                	c.getItems().addItem(563,50000);
                	c.getItems().addItem(556,50000);
                	c.getItems().addItem(554,50000);
                	c.getItems().addItem(566,50000);
                	c.getItems().addItem(2441,250);
                	c.getItems().addItem(2437,250);
                	c.getItems().addItem(2443,250);
                	c.getItems().addItem(3025,250);
               	    c.getItems().addItem(6686,250);
               	    c.getItems().addItem(3041,250);
               	    c.getItems().addItem(2445,250);
               	    c.getItems().addItem(2447,250);
               	    c.getItems().addItem(386,500);
               	    c.getItems().addItem(392,500);
               	    c.getItems().addItem(15273,250);
			c.sendMessage("You have recieved 2 out of 2 starter packages on this IP address.");
			Connection.addIpToStarterList2(PlayerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter2(PlayerHandler.players[c.playerId].connectedFrom);
		} else if (Connection.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom) && Connection.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
			c.sendMessage("You have already recieved 2 starters!");
		}
	}
			public void ThrowTomatoes(final Client c) {
			EventManager.getSingleton().addEvent(new Event() {
				int tomatoeTime = 4;
				 public void execute(EventContainer Tomatoes) {
				   if (tomatoeTime == 4) {
					Player.isThrowingTomatoe = true;
					c.startAnimation(30);
				    }
				   if (tomatoeTime == 3) {
					Player.isThrowingTomatoe = true;
					c.startAnimation(29);
				    }
				   if (tomatoeTime == 2) {
					Player.isThrowingTomatoe = true;
					c.startAnimation(31);
				    }
				   if (tomatoeTime == 0) {
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.playerStandIndex = 0x328;
					Player.isThrowingTomatoe = false;
				    }
				   if (c == null || tomatoeTime <= 0) {
				       Tomatoes.stop();
                    return; 
				    }
				   if (tomatoeTime >= 0) {
					tomatoeTime--;
				    }
				}
			    }, 600);
			}
				                      public void maxemote(final Client c) {
			    EventManager.getSingleton().addEvent(new Event() {
				int comptime = 28;
				 public void execute(EventContainer comp) {
				   if (comptime == 28) {
					c.startAnimation(13194);
				    }
				   if (comptime == 27) {
					c.npcId2 = 2745;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(11199);
				c.playerStandIndex = 11195;
				    }
				   if (comptime == 23) {
					c.npcId2 = 5666;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(11202);
				c.playerStandIndex = 11200;
				    }
				   if (comptime == 20) {
					c.npcId2 = 8349;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				c.playerStandIndex = 9724;
				    }
				 if (comptime == 17) {
					c.npcId2 = 9467;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				  c.startAnimation(13192);
				c.startAnimation(10680);
				c.startAnimation(10681);
				c.playerStandIndex = 10665;
				    }
				 
				 if (comptime == 13) {
					c.npcId2 = 10210;
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13157);
				c.playerStandIndex = 13156;
				    }
				   if (comptime == 11) {
					 c.isNpc = true;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13152);
					c.gfx100(2465);
				c.playerStandIndex = 13156;
				    }
				   if (comptime == 7) {
					c.npcId2 = 10770;
					 c.isNpc = true;
     				 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13156);
				c.playerStandIndex = 13156;
				    }
				   if (comptime == 0) {
					c.isNpc = false;
					 c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				c.playerStandIndex = 0x328;
					c.startAnimation(12567);
				    }
				   if (c == null || comptime <= 0) {
				       comp.stop();
                                                                         return; 
				    }
				   if (comptime >= 0) {
					comptime--;
				    }
				}
			    }, 600);
			}
	
	public static int BoBSummons[] = {6806, 6807, 6994, 6995, 6867, 6868, 6794, 6795, 6815, 6816, 6873, 6874};
	

	public void dropBoBItems() {
		if(c.lastsummon > 0) {
			for(int i = 0; i < c.bobItems.length; i += 1) {
				if (c.bobItems[i] > 0) {
					Server.itemHandler.createGroundItem(c, c.bobItems[i], NPCHandler.npcs[c.summoningnpcid].absX, NPCHandler.npcs[c.summoningnpcid].absY, 1, c.playerId);
					c.bobItems[i] = -1;
				}
			}
			c.lastsummon = -1;
			c.npcId = -1;
			c.summoningnpcid = 0;
			c.summonTime = -1;
			sendFrame126("", 17017);
			sendFrame126("", 17021);
			c.sendMessage("Your BoB items have drop on the floor");
		} else {
			c.sendMessage("You do not have a npc currently spawned");
		return;
		}
	}
	
	/**
	 * @author I'm A Jerk
	 * Start of BoB Storing
	 */
	
	public void refreshBoB() {
		for (int k = 0; k < 29; k++) {
			if (c.bobItems[k] > 0)
				c.bobItemsN[k] = 1;
			if(c.bobItems[k] > 0) {
				Frame34(7423, c.bobItems[k], k, c.bobItemsN[k]);
			}
			if(c.bobItems[k] <= 0) {
				Frame34(7423, -1, k, c.bobItemsN[k]);
			}
		}
		c.getItems().resetItems(5064);
	}
	
	public void takeFromBoB(int slot, int amount) {
		int itemId = c.bobItems[slot];
		int invSlotCount = c.getItems().freeSlots();
		if (amount > invSlotCount) 
			amount = invSlotCount;
		if (invSlotCount < 1) {
			c.sendMessage("Not enough inventory space.");
			return;
		}
		bobWithdrawLoop(itemId, amount);
		refreshBoB();
	}
	
	private void bobWithdrawLoop(int itemId, int amount) {
		int inventoryAmount = 0;
		for (int i = 0; i < c.bobItems.length; i++) {
			if (c.bobItems[i] == itemId) {
				inventoryAmount++;
				if (inventoryAmount <= amount) {
					c.getItems().addItem(itemId, c.bobItemsN[i]);
					c.bobItems[i] = -1;
					c.bobItemsN[i] = 0; 
				}
			}
		}
	}
	
	public void addToBoB(int slot, int amount) {
		int itemId = c.playerItems[slot] - 1;
		int invItemCount = c.getItems().getItemCount(itemId);

		if (amount > openSlots()) 
			amount = openSlots();
		if (amount > invItemCount) 
			amount = invItemCount;
		//c.sendMessage(openSlots() + "  " + invItemCount);
		if (!bobSlotsAvailable()) {
			c.sendMessage("Your familiar's load is too heavy to add more items!");
			return;
		}
		//c.sendMessage(itemId + " " + amount);
		bobDepositLoop(itemId, amount);
		refreshBoB();
	}
	
	private void bobDepositLoop(int itemId, int amount) {
		int inventoryAmount = 0;
		//c.sendMessage("Attemping to store: " + itemId + " " + amount + " with " + c.bobSlotCount);
		for (int i = 0; i < c.bobSlotCount; i++) { 
			if (c.bobItemsN[i] <= 0) {
				inventoryAmount++;
				if (inventoryAmount <= amount) {
					c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
					c.bobItems[i] = itemId;
					c.bobItemsN[i] = 1;
				}
			}
		}
	}
	
	public boolean bobSlotsAvailable() {
		for (int i = 0; i < c.bobSlotCount; i++) {
			if (c.bobItemsN[i] <= 0)
				return true;
		}
		return false;
	}
	
	public int openSlots() {
		int slotCount = 0;
		for (int i = 0; i < c.bobSlotCount; i++) {
			if (c.bobItemsN[i] <= 0)
				slotCount++;
		}
		return slotCount;
	}

	/**
	 * End of BoB Storing
	 */

	 
	 	public void writeCommandLog(String command)
	{
		checkDateAndTime();	
		String filePath = "./Data/Commands2.txt";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " 
				+ "[" + c.playerName + "]: " + "[" + c.connectedFrom + "] "
				 +  "::" + command);
			bw.newLine();
			bw.flush();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}
	public void writePlayerCommandLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Commands/PlayerCommands.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
					+ checkTimeOfDay() + "]: " + "[" + c.playerName + " - "+ c.getX() +", "+ c.getY() +"]: "
					+ "[" + c.connectedFrom + "] " + "::" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}
	
	public void writeModCommandLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Commands/ModCommands.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
					+ checkTimeOfDay() + "]: " + "[" + c.playerName + " - "+ c.getX() +", "+ c.getY() +"]: "
					+ "[" + c.connectedFrom + "] " + "::" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}
	
			public void findClueScroll() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().getClueScroll.length; i++) {
				if (c.playerItems[j] - 1 == c.getItems().getClueScroll[i][1]) {
					if (totalCost + 5000000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 5000000;
					}
					if (c.getItems().freeSlots() == 0){
						Server.itemHandler.createGroundItem(c, c.getItems().getClueScroll[i][1], c.getX(), c.getY(), 1, c.getId());
					} else {
						c.playerItems[i] = c.getItems().getClueScroll[i][1] + 1;
					}
				}
			}
			if (breakOut)
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
					totalCost);
	}
	
	public void writeTrialModCommandLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Commands/TrialModCommands.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
					+ checkTimeOfDay() + "]: " + "[" + c.playerName + " - "+ c.getX() +", "+ c.getY() +"]: "
					+ "[" + c.connectedFrom + "] " + "::" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}
	
	public void writeAdminCommandLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Commands/AdminCommands.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
					+ checkTimeOfDay() + "]: " + "[" + c.playerName + " - "+ c.getX() +", "+ c.getY() +"]: "
					+ "[" + c.connectedFrom + "] " + "::" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public int getWearingAmount2() {
		int totalCash = 0;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] > 0) {
				totalCash += getItemValue(c.playerEquipment[i]);
			}
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] > 0) {
				totalCash += getItemValue(c.playerItems[i]);
			}
		}
		return totalCash;
	}

	public int getItemValue(int ItemID) {
		int shopValue = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					shopValue = (int) Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return shopValue;
	}

	public int backupInvItems[] = new int[28];
	public int backupInvItemsN[] = new int[28];

	public void otherInv(Client c, Client o) {
		if (o == c || o == null || c == null) {
			return;
		}

		for (int i = 0; i < o.playerItems.length; i++) {
			backupInvItems[i] = c.playerItems[i];
			c.playerItemsN[i] = c.playerItemsN[i];
			c.playerItemsN[i] = o.playerItemsN[i];
			c.playerItems[i] = o.playerItems[i];
		}

		c.getItems().updateInventory();

		for (int i = 0; i < o.playerItems.length; i++) {
			c.playerItemsN[i] = backupInvItemsN[i];
			c.playerItems[i] = backupInvItems[i];
		}
	}

	public void sendFrame34a(int frame, int item, int slot, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	public int backupItems[] = new int[Config.BANK_SIZE];
	public int backupItemsN[] = new int[Config.BANK_SIZE];

	public void otherBank(Client c, Client o) {
		if (o == c || o == null || c == null) {
			return;
		}

		for (int i = 0; i < o.bankItems.length; i++) {
			backupItems[i] = c.bankItems[i];
			backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i];
			c.bankItems[i] = o.bankItems[i];
		}
		openUpBank();

		for (int i = 0; i < o.bankItems.length; i++) {
			c.bankItemsN[i] = backupItemsN[i];
			c.bankItems[i] = backupItems[i];
		}
	}

	public void displayItemOnInterface(int frame, int item, int slot, int amount) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}
	}

	// }

	public void writeChatLog(String data) {
		checkDateAndTime();
		String filePath = "./Data/ChatLogs/" + c.playerName + ".txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
					+ checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: "
					+ "" + data + " ");
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void appendVengeanceNPC(int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		if (c.npcIndex > 0 && NPCHandler.npcs[c.npcIndex] != null) {
			c.forcedText = "Taste Vengeance!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			c.vengOn = false;
			if ((NPCHandler.npcs[c.npcIndex].HP - damage) > 0) {
				damage = (int) (damage * 0.75);
				if (damage > NPCHandler.npcs[c.npcIndex].HP) {
					damage = NPCHandler.npcs[c.npcIndex].HP;
				}
				NPCHandler.npcs[c.npcIndex].HP -= damage;
				NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
				NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
				NPCHandler.npcs[c.npcIndex].updateRequired = true;
			}
		}
		c.updateRequired = true;
	}

	int tmpNWCY[] = new int[50];
	int tmpNWCX[] = new int[50];

	public void fmwalkto(int i, int j) {
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.absX + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = tmpNWCX[0] = tmpNWCY[0] = 0;
		int l = c.absY + j;
		l -= c.mapRegionY * 8;
		c.isRunning2 = false;
		c.isRunning = false;
		c.getNewWalkCmdX()[0] += k;
		c.getNewWalkCmdY()[0] += l;
		c.poimiY = l;
		c.poimiX = k;
	}

	public void humidify() {
		if (c.playerLevel[6] < 68) {
			c.sendMessage("You need a magic level of 68 to cast this spell.");
			return;
		}
		if (c.getItems().playerHasItem(229, 1)) {
			if (c.getItems().playerHasItem(555, 3)
					&& c.getItems().playerHasItem(9075, 1)
					&& c.getItems().playerHasItem(554, 1)) {
				c.startAnimation(722);
				c.gfx100(1061);
				addSkillXP(c.MAGIC_SPELLS[51][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
				c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
				c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
				c.getItems()
						.deleteItem(9075, c.getItems().getItemSlot(9075), 4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell.");
			}
		} else {
			c.sendMessage("You dont have anything to fill.");
		}
		int vialAmountFilled = 0;
		int bucketAmountFilled = 0;
		int jugAmountFilled = 0;
		boolean filledJugs = false;
		boolean filledVials = false;
		boolean filledBuckets = false;
		for (int i = 0; i < 28; i++) {
			if (c.getItems().playerHasItem(229, 1)) {
				filledVials = true;
				c.getItems().deleteItem(229, c.getItems().getItemSlot(229), 1);
				c.getItems().addItem(227, 1);
				vialAmountFilled += 1;
			}
			if (c.getItems().playerHasItem(1925, 1)) {
				filledBuckets = true;
				c.getItems()
						.deleteItem(1925, c.getItems().getItemSlot(1925), 1);
				c.getItems().addItem(1929, 1);
				bucketAmountFilled += 1;
			}
			if (c.getItems().playerHasItem(1935, 1)) {
				filledJugs = true;
				c.getItems()
						.deleteItem(1935, c.getItems().getItemSlot(1935), 1);
				c.getItems().addItem(1937, 1);
				jugAmountFilled += 1;
			}
		}
		if (filledVials)
			c.sendMessage("You filled " + vialAmountFilled
					+ " vials with water.");
		if (filledBuckets)
			c.sendMessage("You filled " + bucketAmountFilled
					+ " buckets of water.");
		if (filledJugs)
			c.sendMessage("You filled " + jugAmountFilled + " jugs of water.");
	}

	public String GetNpcName(int NpcID) {
		for (int i = 0; i < NPCHandler.maxListedNPCs; i++) {
			if (NPCHandler.NpcList[i] != null) {
				if (NPCHandler.NpcList[i].npcId == NpcID) {
					return NPCHandler.NpcList[i].npcName;
				}
			}
		}
		return "NPC Not Listed" + NpcID;
	}

	public void sendQuest(String s, int id) {
		try {
			c.outStream.createFrameVarSizeWord(126);
			c.outStream.writeString(s);
			c.outStream.writeWordA(id);
			c.outStream.endFrameVarSizeWord();
		} catch (Exception e) {
		}
	}

	public String checkTimeOfDay() {
		Calendar cal = new GregorianCalendar();
		int TIME_OF_DAY = cal.get(Calendar.AM_PM);
		if (TIME_OF_DAY > 0)
			return "PM";
		else
			return "AM";
	}

	public void GWKC() {
		walkableInterface(16210);
		sendFrame126("" + c.Arma + "", 16216);
		sendFrame126("" + c.Band + "", 16217);
		sendFrame126("" + c.Sara + "", 16218);
		sendFrame126("" + c.Zammy + "", 16219);
	}

	public void resetFishing() {
		c.fishtimer = -1;
		c.fishing = false;
	}

	public void ResetGWKC() {
		if (c.inGWD()) {
			c.Arma = 0;
			c.Band = 0;
			c.Sara = 0;
			c.Zammy = 0;
			c.sendMessage("A magical force reseted your kill count!");
		}
	}

	public void checkDateAndTime() {
		Calendar cal = new GregorianCalendar();

		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DAY = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = cal.get(Calendar.HOUR_OF_DAY);
		int MIN = cal.get(Calendar.MINUTE);
		int SECOND = cal.get(Calendar.SECOND);

		String day = "";
		String month = "";
		String hour = "";
		String minute = "";
		String second = "";

		if (DAY < 10)
			day = "0" + DAY;
		else
			day = "" + DAY;
		if (MONTH < 10)
			month = "0" + MONTH;
		else
			month = "" + MONTH;
		if (HOUR < 10)
			hour = "0" + HOUR;
		else
			hour = "" + HOUR;
		if (MIN < 10)
			minute = "0" + MIN;
		else
			minute = "" + MIN;
		if (SECOND < 10)
			second = "0" + SECOND;
		else
			second = "" + SECOND;

		c.date = day + "/" + month + "/" + YEAR;
		c.currentTime = hour + ":" + minute + ":" + second;
	}

	Properties p = new Properties();

	/*public void loadAnnouncements() {
		try {
			loadIni();
			if (p.getProperty("announcement1").length() > 0) {
				c.sendMessage(p.getProperty("announcement1"));
			}
			if (p.getProperty("announcement2").length() > 0) {
				c.sendMessage(p.getProperty("announcement2"));
			}
			if (p.getProperty("announcement3").length() > 0) {
				c.sendMessage(p.getProperty("announcement3"));
			}
			printIt();
		} catch (Exception e) {
		}
	}

	public void printIt() {
		messageOfTheDay();
	}

	public void messageOfTheDay() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			// Client c2 = (Client)Server.playerHandler.players[j];
			int tipID = 0 + Misc.random(15);
			if (tipID == 0) {
				c.sendMessage("Did you know?: There is AGILITY! ::agility to train.");
				return;
			}
			if (tipID == 1) {
				c.sendMessage("Did you know?: Donators can reset all combat stats and yell!!");
				return;
			}
			if (tipID == 2) {
				c.sendMessage("Did you know?: You can donate and get Rewarded! Do ::donate");
				return;
			}
			if (tipID == 3) {
				c.sendMessage("Did you know?: Type in '::vote' to help the server (atleast each 24 hours)!");
				return;
			}
			if (tipID == 4) {
				c.sendMessage("Did you know?: RuneForge has working HIGHSCORES! ::highscores");
				return;
			}
			if (tipID == 5) {
				c.sendMessage("Did you know?: Find our website at 'www.RuneForge.com'. Please register.");
				return;
			}
			if (tipID == 6) {
				c.sendMessage("Did you know?: You get random messages when you login.");
				return;
			}
			if (tipID == 7) {
				c.sendMessage("Did you know?: RuneForge is brand new and looking for Staff!");
				return;
			}
			if (tipID == 8) {
				c.sendMessage("Did you know?: This server is amazing.");
				return;
			}
			if (tipID == 9) {
				c.sendMessage("Did you know?: The owners are Jason and Matt.");
				return;
			}
			if (tipID == 10) {
				c.sendMessage("Did you know?: Follow our rules and stay active to earn a rank.");
				return;
			}
		}
	}*/

	public int CraftInt, Dcolor, FletchInt;

	/**
	 * MulitCombat icon
	 * 
	 * @param i1
	 *            0 = off 1 = on
	 */
	public void multiWay(int i1) {
		// synchronized(c) {
		c.outStream.createFrame(61);
		c.outStream.writeByte(i1);
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	// }

	public void clearClanChat() {
		c.clanId = -1;
		c.inAclan = false;
		c.CSLS = 0;
		c.getPA().sendFrame126("Talking in: ", 18139);
		c.getPA().sendFrame126("Owner: ", 18140);
		for (int j = 18144; j < 18244; j++)
			c.getPA().sendFrame126("", j);
	}

	public void resetAutocast() {
		c.autocastId = 0;
		c.autocasting = false;
		c.getPA().sendFrame36(108, 0);
	}

	public void sendFrame126(String s, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	// }

	public void sendLink(String s) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(187);
			c.getOutStream().writeString(s);
		}
	}

	// }

	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(134);
			c.getOutStream().writeByte(skillNum);
			c.getOutStream().writeDWord_v1(XP);
			c.getOutStream().writeByte(currentLevel);
			c.flushOutStream();
		}
	}

	// }
	public void totallevelsupdate() {
		int totalLevel = (getLevelForXP(c.playerXP[0])
				+ getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4])
				+ getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6])
				+ getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10])
				+ getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12])
				+ getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16])
				+ getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18])
				+ getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20]));
		sendFrame126("Levels: " + totalLevel, 13983);
	}

	public void sendFrame106(int sideIcon) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(106);
			c.getOutStream().writeByteC(sideIcon);
			c.flushOutStream();
			requestUpdates();
		}
	}

	// }

	public void sendFrame107() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(107);
			c.flushOutStream();
		}
	}

	// }
	public void sendFrame36(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(36);
			c.getOutStream().writeWordBigEndian(id);
			c.getOutStream().writeByte(state);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame185(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(185);
			c.getOutStream().writeWordBigEndianA(Frame);
		}
	}

	// }

	public void showInterface(int interfaceid) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(97);
			c.getOutStream().writeWord(interfaceid);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame248(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.getOutStream().writeWord(SubFrame2);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame171(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(171);
			c.getOutStream().writeByte(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame200(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(200);
			c.getOutStream().writeWord(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame70(int i, int o, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(70);
			c.getOutStream().writeWord(i);
			c.getOutStream().writeWordBigEndian(o);
			c.getOutStream().writeWordBigEndian(id);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame75(int npcid, int intId) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(75);
			c.getOutStream().writeWordBigEndianA(npcid);
			c.getOutStream().writeWordBigEndianA(intId);
			c.flushOutStream();
		}
		// }
	}

	public void sendFrame164(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(164);
			c.getOutStream().writeWordBigEndian_dup(Frame);
			c.flushOutStream();
		}
	}

	// }
	

	public void setPrivateMessaging(int i) { // friends and ignore list status
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(221);
			c.getOutStream().writeByte(i);
			c.flushOutStream();
		}
	}

	// }

	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(206);
			c.getOutStream().writeByte(publicChat);
			c.getOutStream().writeByte(privateChat);
			c.getOutStream().writeByte(tradeBlock);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame87(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(87);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.getOutStream().writeDWord_v1(state);
			c.flushOutStream();
		}
	}

	// }

	public void sendPM(long name, int rights, byte[] chatmessage, int messagesize) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSize(196);
			c.getOutStream().writeQWord(name);
			c.getOutStream().writeDWord(c.lastChatId++);
			c.getOutStream().writeByte(rights);
			c.getOutStream().writeBytes(chatmessage, messagesize, 0);
			c.getOutStream().endFrameVarSize();
			c.flushOutStream();
			Misc.textUnpack(chatmessage, messagesize);
			Misc.longToPlayerName(name);
		}
	}

	// }

	public void createPlayerHints(int type, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(type);
			c.getOutStream().writeWord(id);
			c.getOutStream().write3Byte(0);
			c.flushOutStream();
		}
	}

	// }

	public void createObjectHints(int x, int y, int height, int pos) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(pos);
			c.getOutStream().writeWord(x);
			c.getOutStream().writeWord(y);
			c.getOutStream().writeByte(height);
			c.flushOutStream();
		}
	}

	// }

	public void loadPM(long playerName, int world) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (world != 0) {
				world += 9;
			} else if (!Config.WORLD_LIST_FIX) {
				world += 1;
			}
			c.getOutStream().createFrame(50);
			c.getOutStream().writeQWord(playerName);
			c.getOutStream().writeByte(world);
			c.flushOutStream();
		}
		// }
	}

	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		c.getItems().resetItems(3214);
	}

	public void removeAllWindows() {
		c.usingBoB = false;
		if (c.getOutStream() != null && c != null) {
			c.getPA().resetVariables();
			c.getOutStream().createFrame(219);
			c.flushOutStream();
		}
	}

	public void closeAllWindows() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(219);
			c.flushOutStream();
			c.isBanking = false;
			c.getTradeAndDuel().declineTrade();
		}
		// }
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34); // init item to smith screen
			c.outStream.writeWord(column); // Column Across Smith Screen
			c.outStream.writeByte(4); // Total Rows?
			c.outStream.writeDWord(slot); // Row Down The Smith Screen
			c.outStream.writeWord(id + 1); // item
			c.outStream.writeByte(amount); // how many there are?
			c.outStream.endFrameVarSizeWord();
		}
	}

	// }

	public void Frame34(int frame, int item, int slot, int amount) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void walkableInterface(int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(208);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.flushOutStream();
		}
	}

	// }

	public String getTotalAmount(Client c, int j) {
		if (j >= 10000 && j < 10000000) {
			return j / 1000 + "K";
		} else if (j >= 10000000 && j <= 2147483647) {
			return j / 1000000 + "M";
		} else {
			return "" + j + " gp";
		}
	}

	public int mapStatus = 0;

	public void sendFrame99(int state) { // used for disabling map
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (mapStatus != state) {
				mapStatus = state;
				c.getOutStream().createFrame(99);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	// }

	public void sendCrashFrame() { // used for crashing cheat clients
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(123);
			c.flushOutStream();
		}
	}

	// }

	/**
	 * Reseting animations for everyone
	 **/

	public void frame1() {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Client person = (Client) PlayerHandler.players[i];
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPA().requestUpdates();
						}
					}
				}
			}
		}
	}

	// }

	/**
	 * Creating projectile
	 **/
	public void createProjectile(int x, int y, int offX, int offY, int angle,
			int speed, int gfxMoving, int startHeight, int endHeight,
			int lockon, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(16);
			c.getOutStream().writeByte(64);
			c.flushOutStream();
		}
	}

	// }

	public void createProjectile2(int x, int y, int offX, int offY, int angle,
			int speed, int gfxMoving, int startHeight, int endHeight,
			int lockon, int time, int slope) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(slope);
			c.getOutStream().writeByte(64);
			c.flushOutStream();
		}
	}

	// }

	// projectiles for everyone within 25 squares
	public void createPlayersProjectile(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							if (p.heightLevel == c.heightLevel)
								person.getPA().createProjectile(x, y, offX,
										offY, angle, speed, gfxMoving,
										startHeight, endHeight, lockon, time);
						}
					}
				}
			}
		}
	}

	// }

	public void createPlayersProjectile2(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time, int slope) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().createProjectile2(x, y, offX, offY,
									angle, speed, gfxMoving, startHeight,
									endHeight, lockon, time, slope);
						}
					}
				}
			}
		}
	}

	// }

	/**
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(4);
			c.getOutStream().writeByte(0);
			c.getOutStream().writeWord(id);
			c.getOutStream().writeByte(height);
			c.getOutStream().writeWord(time);
			c.flushOutStream();
		}
	}

	// }

	// creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().stillGfx(id, x, y, height, time);
						}
					}
				}
			}
		}
	}

	// }

	/**
	 * Objects, add and remove
	 **/
	public void object(int objectId, int objectX, int objectY, int face,
			int objectType) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}
	}

	// }

	public void checkObjectSpawn(int objectId, int objectX, int objectY,
			int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}
	}

	// }

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public void showOption(int i, int l, String s, int a) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (!optionType.equalsIgnoreCase(s)) {
				optionType = s;
				c.getOutStream().createFrameVarSize(104);
				c.getOutStream().writeByteC(i);
				c.getOutStream().writeByteA(l);
				c.getOutStream().writeString(s);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
			}
		}
	}

	// }

	/**
	 * Open bank
	 **/
	public void openUpBank(){
			if(c.getBankPin().getFullPin().equalsIgnoreCase("")){
				c.getBankPin().open();
			        return;
			}
			if(c.inTrade || c.tradeStatus == 1) {
				Client o = (Client) Server.playerHandler.players[c.tradeWith];
				if(o != null) {
					o.getTradeAndDuel().declineTrade();
				}
			}
			if(c.duelStatus == 1) {
				Client o = (Client) Server.playerHandler.players[c.duelingWith];
				if(o != null) {
					o.getTradeAndDuel().resetDuel();
				}
			}
			if(c.getOutStream() != null && c != null) {
				c.getItems().resetItems(5064);
				c.getItems().rearrangeBank();
				c.getItems().resetBank();
				c.getItems().resetTempItems();
				c.getOutStream().createFrame(248);
				c.getOutStream().writeWordA(5292);
				c.getOutStream().writeWord(5063);
				c.flushOutStream();
				c.isBanking = true;
			}
		
	}


	// }

	/**
	 * Private Messaging
	 **/
	public void logIntoPM() {
		setPrivateMessaging(2);
		for(int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
			Player p = PlayerHandler.players[i1];
			if(p != null && p.isActive) {
				Client o = (Client)p;
				if(o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for(int i = 0; i < c.friends.length; i++) {
			if(c.friends[i] != 0)  {
				for(int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
					Player p = PlayerHandler.players[i2];
					if (p != null && p.isActive && Misc.playerNameToInt64(p.playerName) == c.friends[i])  {
						Client o = (Client)p;
						if(o != null) {
							if (c.playerRights >= 2 || p.privateChat == 0 || (p.privateChat == 1 && o.getPA().isInPM(Misc.playerNameToInt64(c.playerName)))) {
			 		 			loadPM(c.friends[i], 1);
			 		 			pmLoaded = true;
							}
							break;
						}
					}
				}
				if(!pmLoaded) {	
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for(int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
				Player p = PlayerHandler.players[i1];
    			if(p != null && p.isActive) {
					Client o = (Client)p;
					if(o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}
	public void writeQuestTab(){
		c.getPA().sendFrame126("Players Online: "+PlayerHandler.getPlayerCount()+ " ", 29155); //quest journal title
		//c.getPA().sendFrame126("www.ReactionX.org", 29164); //quest title
		c.getPA().sendFrame126("Combat Level: " +c.combatLevel+ " ", 29165);
		c.getPA().sendFrame126("@gre@RXP (RuneInsanity Points):", 29166); //quest title
		c.getPA().sendFrame126("@yel@"+c.rxPoints+" ", 29167);
		c.getPA().sendFrame126("@gre@Agility Points: ", 29168);
		c.getPA().sendFrame126("@yel@"+c.magePoints+" ", 29169);
		c.getPA().sendFrame126("- - Summoning SKILL - -", 29170);
		c.getPA().sendFrame126("@lre@Summoning lVl: " + c.playerLevel[24] + "/"+ getLevelForXP(c.playerXP[24]), 29171);
		c.getPA().sendFrame126("@lre@Current Exp: " + c.playerXP[24] + "", 29172);
		c.getPA().sendFrame126("@lre@Next level At : " + c.getPA().getXPForLevel(getLevelForXP(c.playerXP[24]) + 1) + "", 29173);
		c.getPA().sendFrame126("@lre@Remaining : " + (c.getPA().getXPForLevel(getLevelForXP(c.playerXP[24]) + 1) - c.playerXP[24]) + "", 29174);
	}
	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = PlayerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		long l = Misc
				.playerNameToInt64(PlayerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						if (o.getPA().isInPM(
								Misc.playerNameToInt64(c.playerName))) {
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i] && c.playerRights < 2) {
						loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				if (l == c.friends[i]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId,
			int healType) {
		c.attackTimer = c.getCombat().getAttackDelay(
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon])
						.toLowerCase());
		if (c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if (!c.isDead && System.currentTimeMillis() - c.foodDelay > 2000) {
			if (c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the "
						+ c.getItems().getItemName(itemId).toLowerCase() + ".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}
	
	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {

		switch (spellId) {
		case 1162: // low alch
			if (System.currentTimeMillis() - c.alchDelay > 1000) {
				if (!c.getCombat().checkMagicReqs(49)) {
					break;
				}
				if (itemId == 995) {
					c.sendMessage("You can't alch coins");
					break;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995,
						c.getShops().getItemShopValue(itemId) / 3);
				c.startAnimation(c.MAGIC_SPELLS[49][2]);
				c.gfx100(c.MAGIC_SPELLS[49][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[49][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;

		case 1178: // high alch
			if (System.currentTimeMillis() - c.alchDelay > 2000) {
				if (!c.getCombat().checkMagicReqs(50)) {
					break;
				}
				if (itemId == 995) {
					c.sendMessage("You can't alch coins");
					break;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995,
						(int) (c.getShops().getItemShopValue(itemId) * .75));
				c.startAnimation(c.MAGIC_SPELLS[50][2]);
				c.gfx100(c.MAGIC_SPELLS[50][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[50][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;
		case 8349:
			handleAlt(itemId);
			break;
		}
	}

	/**
	 * Dieing
	 **/

	public void applyDead() {
		c.respawnTimer = 15;
		c.isDead = false;

		if (c.duelStatus != 6) {
			c.killerId = findKiller();
			Client o = (Client) PlayerHandler.players[c.killerId];
			if (o != null) {
			if(!(c.npcIndex > 0) && c.inPits == false){
				if (c.killerId != c.playerId && !c.inDuelArena() && !c.inFightCaves()) {
					if (o.playerEquipment[o.playerRing] == 19669) {
						o.rxPoints += 5;
						o.KC += 1;
						o.killStreak++;
						o.deathStreak = 0;
						o.sendMessage("You have killed <col=255>"+c.playerName+"</col> so you recieved 5 points, you now have <col=255>"+o.rxPoints+"</col> points");
					} else {
						o.rxPoints += 3;
						o.KC += 1;
						c.DC += 1;
						o.killStreak++;
						o.deathStreak = 0;
						o.sendMessage("You have killed <col=255>"+c.playerName+"</col> so you recieved 3 points, you now have <col=255>"+o.rxPoints+"</col> points");
					}
				}
				c.playerKilled = c.playerId;
				if (o.duelStatus == 5) {
					o.duelStatus++;
				}
			}
		}
		}
		if (c.duelStatus <= 4) {
			c.sendMessage("Oh dear you are dead!");
		c.deathStreak++;
		c.killStreak = 0;
		} else if (c.duelStatus != 6) {
			c.sendMessage("You have lost the duel!");
		}
		resetDamageDone();
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;

		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.tradeResetNeeded = true;
	}

	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[c.playerId] = 0;
			}
		}
	}

	/*
	 * Vengeance
	 */
	public void castVeng() {
		if (c.playerLevel[6] < 94) {
			c.sendMessage("You need a magic level of 94 to cast this spell.");
			return;
		}
		if (c.playerLevel[1] < 40) {
			c.sendMessage("You need a defence level of 40 to cast this spell.");
			return;
		}
		if (c.inDuelArena()) {
			c.sendMessage("Vengeance has been disabled in duels.");
			return;
		}
		if (!c.getItems().playerHasItem(9075, 4) || !c.getItems().playerHasItem(557, 10) || !c.getItems().playerHasItem(560, 2)) {
			c.sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		if (System.currentTimeMillis() - c.lastCast < 30000) {
			c.sendMessage("You can only cast vengeance every 30 seconds.");
			return;
		}
		if (c.vengOn) {
			c.sendMessage("You already have vengeance casted.");
			return;
		}
		c.startAnimation(4410);
		c.gfx100(726);
		c.getItems().deleteItem2(9075, 4);
		c.getItems().deleteItem2(557, 10);
		c.getItems().deleteItem2(560, 2);
		addSkillXP(2000, 6);
		c.stopMovement();
		refreshSkill(6);
		c.vengOn = true;
		c.lastCast = System.currentTimeMillis();
	}

	public void vengOther() {
		if (c.playerIndex > 0) {
			Player q = PlayerHandler.players[c.playerIndex];
			final int oX = q.getX();
			final int oY = q.getY();
			if (c.playerLevel[6] < 93) {
				c.sendMessage("You need a magic level of 94 to cast this spell.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (c.inDuelArena() || q.inDuelArena()) {
				c.sendMessage("Vengeance has been disabled in duels.");
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (q.underAttackBy > 0 && !c.inMulti()) {
				c.sendMessage("You cannot veng this person in a fight.");
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (!q.acceptAid) {
				c.sendMessage("This player has their accept Aid off, therefore you cannot veng them!");
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.playerLevel[1] < 40) {
				c.sendMessage("You need a defence level of 40 to cast this spell.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (!c.getItems().playerHasItem(9075, 3) || !c.getItems().playerHasItem(557, 10) || !c.getItems().playerHasItem(560, 2)) {
				c.sendMessage("You don't have the required runes to cast this spell.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (System.currentTimeMillis() - c.lastCast < 30000) {
				c.sendMessage("You can only cast vengeance every 30 seconds.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (q.vengOn) {
				c.sendMessage("That player already have vengeance casted.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			c.startAnimation(4411);
			q.gfx100(725);
			c.getItems().deleteItem2(9075, 3);
			c.getItems().deleteItem2(557, 10);
			c.getItems().deleteItem2(560, 2);
			q.vengOn = true;
			addSkillXP(2000, 6);
			c.turnPlayerTo(oX, oY);
			refreshSkill(6);
			c.getCombat().resetPlayerAttack();
			c.stopMovement();
			c.lastCast = System.currentTimeMillis();
		}
	}

	public boolean wearingCape(int cape) {
		int capes[] = { 12169, 12170, 9747, 9748, 9750, 9751, 9753, 9754, 9756, 9757, 9759,
				9760, 9762, 9763, 9765, 9766, 9768, 9769, 9771, 9772, 9774,
				9775, 9777, 9778, 9780, 9781, 9783, 9784, 9786, 9787, 9789,
				9790, 9792, 9793, 9795, 9796, 9798, 9799, 9801, 9802, 9804,
				9805, 9807, 9808, 9810, 9811, 9813, 9948, 9949 };
		for (int i = 0; i < capes.length; i++) {
			if (capes[i] == cape) {
				return true;
			}
		}
		return false;
	}

	public boolean wearingdungCape(int cape) {
		int capes[] = { 18509, 2, };
		for (int i = 0; i < capes.length; i++) {
			if (capes[i] == cape) {
				return true;
			}
		}
		return false;
	}

	public void vengMe() {
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557, 10)
					&& c.getItems().playerHasItem(9075, 4)
					&& c.getItems().playerHasItem(560, 2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(726);
				c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
				c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
				c.getItems()
						.deleteItem(9075, c.getItems().getItemSlot(9075), 4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

	public int skillcapeGfx(int cape) {
		int capeGfx[][] = { { 9747, 823 }, { 9748, 823 }, { 9750, 828 },
				{ 9751, 828 }, { 9753, 824 }, { 9754, 824 }, { 9756, 832 },
				{ 9757, 832 }, { 9759, 829 }, { 9760, 829 }, { 9762, 813 },
				{ 9763, 813 }, { 9765, 817 }, { 9766, 817 }, { 9768, 833 },
				{ 9769, 833 }, { 9771, 830 }, { 9772, 830 }, { 9774, 835 },
				{ 9775, 835 }, { 9777, 826 }, { 9778, 826 }, { 9780, 818 },
				{ 9781, 818 }, { 9783, 812 }, { 9784, 812 }, { 9786, 827 },
				{ 9787, 827 }, { 9789, 820 }, { 9790, 820 }, { 9792, 814 },
				{ 9793, 814 }, { 9795, 815 }, { 9796, 815 }, { 9798, 819 },
				{ 9799, 819 }, { 9801, 821 }, { 9802, 821 }, { 9804, 831 },
				{ 9805, 831 }, { 9807, 822 }, { 9808, 822 }, { 9810, 825 },
				{ 9811, 825 }, { 9948, 907 }, { 9949, 907 }, { 9813, 816 }, { 12169, 1515 }, { 12170, 1515 } };
		for (int i = 0; i < capeGfx.length; i++) {
			if (capeGfx[i][0] == cape) {
				return capeGfx[i][1];
			}
		}
		return -1;
	}

	public int skillcapeEmote(int cape) {
		int capeEmote[][] = { { 9747, 4959 }, { 9748, 4959 }, { 9750, 4981 },
				{ 9751, 4981 }, { 9753, 4961 }, { 9754, 4961 }, { 9756, 4973 },
				{ 9757, 4973 }, { 9759, 4979 }, { 9760, 4979 }, { 9762, 4939 },
				{ 9763, 4939 }, { 9765, 4947 }, { 9766, 4947 }, { 9768, 4971 },
				{ 9769, 4971 }, { 9771, 4977 }, { 9772, 4977 }, { 9774, 4969 },
				{ 9775, 4969 }, { 9777, 4965 }, { 9778, 4965 }, { 9780, 4949 },
				{ 9781, 4949 }, { 9783, 4937 }, { 9784, 4937 }, { 9786, 4967 },
				{ 9787, 4967 }, { 9789, 4953 }, { 9790, 4953 }, { 9792, 4941 },
				{ 9793, 4941 }, { 9795, 4943 }, { 9796, 4943 }, { 9798, 4951 },
				{ 9799, 4951 }, { 9801, 4955 }, { 9802, 4955 }, { 9804, 4975 },
				{ 9805, 4975 }, { 9807, 4957 }, { 9808, 4957 }, { 9810, 4963 },
				{ 9811, 4963 }, { 9948, 5158 }, { 9949, 5158 }, { 9813, 4945 }, { 12169, 8525 }, { 12170, 8525 }, };
		for (int i = 0; i < capeEmote.length; i++) {
			if (capeEmote[i][0] == cape) {
				return capeEmote[i][1];
			}
		}
		return -1;
	}

	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;
	}

	public void resetFollowers() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].followId == c.playerId) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPA().resetFollow();
				}
			}
		}
	}
	

	public void giveLife() {
		c.isDead = false;
		c.faceUpdate(-1);
		c.freezeTimer = 0;
		c.lastsummon = -1;
		if (c.duelStatus <= 4 && !c.getPA().inPitsWait()) {
			if (!c.inPits && !c.inFightCaves() && !c.isOwner()) {
				c.getItems().resetKeepItems();
				if ((c.playerRights == 2 && Config.ADMIN_DROP_ITEMS) || c.playerRights != 2) {
		if (!c.isSkulled && !c.isOwner()) {	// what items to keep
						c.getItems().keepItem(0, true);
						c.getItems().keepItem(1, true);	
						c.getItems().keepItem(2, true);
					}	
					if(c.prayerActive[10] || c.curseActive[0] && System.currentTimeMillis() - c.lastProtItem > 700) {
						c.getItems().keepItem(3, true);
					}
                                        c.getItems().dropAllItemsPVP();

					c.getItems().dropAllItems(); // drop all items
					c.getItems().deleteAllItems(); // delete all items

				        if(c.inRFD()) {
					c.getItems().deleteAllItems(); // delete all items
				        }
					c.getPA().ResetGWKC();

					
					if(!c.isSkulled) { // add the kept items once we finish deleting and dropping them	
						for (int i1 = 0; i1 < 3; i1++) {
							if(c.itemKeptId[i1] > 0) {
								c.getItems().addItem(c.itemKeptId[i1], 1);
							}
						}
					}	
					if(c.prayerActive[10] || c.curseActive[0]) { // if we have protect items 
						if(c.itemKeptId[3] > 0) {
							c.getItems().addItem(c.itemKeptId[3], 1);
						}
					}
				}
				c.getItems().resetKeepItems();
			} else if (c.inPits) {
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.pitsStatus = 1;
				c.isSkulled = false;
			}
		}
		c.getCombat().resetPrayers();
		for (int i = 0; i < 20; i++) {
			c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
			c.getPA().refreshSkill(i);
		}
		if (c.pitsStatus == 1) {
			movePlayer(2399, 5173, 0);
		} else if (c.duelStatus <= 4) { // if we are not in a duel repawn to
										// wildy
			movePlayer(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
			c.isSkulled = false;
			c.skullTimer = 0;
			c.attackedPlayers.clear();
		} else if (c.inFightCaves()) {
			c.getPA().resetTzhaar();
		} else { // we are in a duel, respawn outside of arena
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o != null) {
				o.getPA().createPlayerHints(10, -1);
				if (o.duelStatus == 6) {
					o.getTradeAndDuel().duelVictory();
				}
			}
			c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			o.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			if (c.duelStatus != 6) { // if we have won but have died, don't
										// reset the duel status.
				c.getTradeAndDuel().resetDuel();
			}
		}
		//PlayerSaving.getSingleton().requestSave(c.playerId);
		PlayerSave.saveGame(c);
		c.getCombat().resetPlayerAttack();
		resetAnimation();
		c.startAnimation(65535);
		frame1();
		resetTb();
		c.isSkulled = false;
		c.attackedPlayers.clear();
		c.headIconPk = -1;
		c.skullTimer = -1;
		c.damageTaken = new int[Config.MAX_PLAYERS];
		c.getPA().requestUpdates();
		removeAllWindows();
		c.tradeResetNeeded = true;
	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (c.newLocation) {
		case 1:
			movePlayer(3578, 9706, -1);
			break;
		case 2:
			movePlayer(3568, 9683, -1);
			break;
		case 3:
			movePlayer(3557, 9703, -1);
			break;
		case 4:
			movePlayer(3556, 9718, -1);
			break;
		case 5:
			movePlayer(3534, 9704, -1);
			break;
		case 6:
			movePlayer(3546, 9684, -1);
			break;
		}
		c.newLocation = 0;
	}

	/**
	 * Teleporting
	 **/
	public void spellTeleport(int x, int y, int height) {
		c.getPA().startTeleport(x, y, height,
				c.playerMagicBook == 1 ? "ancient" : "modern");
	}

	public void startMovement(int x, int y, int height) {
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inRFD()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.inWarriorG() && c.heightLevel == 2) {
			c.sendMessage("You can't teleport out of Warrior Guild!");
			return;
		}
		if (c.inGWD()) {
			ResetGWKC();
		}
		if (c.inFightCaves()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			EarningPotential.checkTeleport(c);
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;

		}

	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inPits || c.viewingOrb || inPitsWait()) {
			c.sendMessage("You can't teleport in here!");
			return;
		}
		if (c.inGWD()) {
			ResetGWKC();
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.inWarriorG() && c.heightLevel == 2) {
			c.sendMessage("You can't teleport out of Warrior Guild!");
			return;
		}
		if (c.inRFD()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.inFightCaves()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			EarningPotential.checkTeleport(c);
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(8939);
				c.teleTimer = 9;
				c.gfx0(1576);
				c.teleEndAnimation = 8941;
			}

			if (teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(9599);
				c.teleGfx = 0;
				c.teleTimer = 11;
				c.teleEndAnimation = 8941;
				c.gfx0(1681);
			}
			
			if (teleportType.equalsIgnoreCase("dung")) {
				c.startAnimation(13288);
				//c.teleGfx = 2516;
				c.teleTimer = 6;
				c.teleEndAnimation = 13285;
				c.gfx0(2517);
			}

		}
	}

	public void startTeleport2(int x, int y, int height) {
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.inGWD()) {
			ResetGWKC();
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0) {
			c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(714);
			c.teleTimer = 11;
			c.teleGfx = 308;
			c.teleEndAnimation = 715;

		}
	}

	public void processTeleport() {
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if (c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}
	}

	public void movePlayer(int x, int y, int h) {
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		c.heightLevel = h;
		requestUpdates();
	}

	/**
	 * Following
	**/
	
	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
	}
	
	public void followPlayer(int i) {
		if(PlayerHandler.players[c.followId] == null || PlayerHandler.players[c.followId].isDead) {
			c.followId = 0;
			return;
		}		
				if (c.freezeTimer > 0) {
			return;
		}
		if (c.inWG()) {
			c.followId = 0;
			return;
		}
		if (c.inDuelArena()) {
			c.followId = 0;
			return;
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;
		if(c.freezeTimer > 0 || c.isDead || c.playerLevel[3] <= 0)
			return;
		
		int otherX = PlayerHandler.players[c.followId].getX();
		int otherY = PlayerHandler.players[c.followId].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			return;
		}
		if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);//This is what causes Clipping through walls needa rewrite this
				return;
			}
		}
		
		if((c.usingBow || c.mageFollow || (c.playerIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if(c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}
		
		
		c.faceUpdate(c.followId+32768);
		c.getX();
		c.getY();
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
				case 0:
					walkTo(0,-1);
				break;
				case 1:
					walkTo(0,1);
				break;
				case 2:
					walkTo(1,0);
				break;
				case 3:
					walkTo(-1,0);
				break;			
			}		
		} else if(c.isRunning2 && !withinDistance) {
			if(otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if(otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if(otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if(otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if(otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if(otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if(otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if(otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if(otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if(otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if(otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if(otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			} 
		}
		c.faceUpdate(c.followId+32768);
	}
	
	public void followNpc() { //old one
		if (NPCHandler.npcs[c.followId2] == null
				|| NPCHandler.npcs[c.followId2].isDead) {
			c.followId2 = 0;
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		if (c.inWG()) {
			c.followId = 0;
			return;
		}
		if (c.inDuelArena()) {
			c.followId = 0;
			return;
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = NPCHandler.npcs[c.followId2].getX();
		int otherY = NPCHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);
		c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0))
				&& bowDistance && !sameSpot) {
			return;
		}

		if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		c.faceUpdate(c.followId2);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			if (otherY > c.getY() && otherX == c.getX()) {
				walkTo(0,
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				walkTo(0,
						getMove(c.getY(), otherY + 1)
								+ getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1)
						+ getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1)
						+ getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1)
						+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1)
								+ getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1)
						+ getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1)
						+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1)
						+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY + 1));
			}
		}
		c.faceUpdate(c.followId2);
	}


	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else
			return j - i;
	}

	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}

	public void walkTo(int i, int j) {
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*
		 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

		int k = c.getX() + xMove;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + yMove;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}

	}

	public void walkToCheck(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
			return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753
				&& c.playerEquipment[c.playerChest] == 4757
				&& c.playerEquipment[c.playerLegs] == 4759
				&& c.playerEquipment[c.playerWeapon] == 4755;
	}

	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724
				&& c.playerEquipment[c.playerChest] == 4728
				&& c.playerEquipment[c.playerLegs] == 4730
				&& c.playerEquipment[c.playerWeapon] == 4726;
	}

	/**
	 * reseting animation
	 **/
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon])
						.toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void handleAlt(int id) {
		if (!c.getItems().playerHasItem(id)) {
			c.getItems().addItem(id, 1);
		}
	}

	//capeId, hoodId
	/*public final static ADD_SKILLCAPESint [][] {
		{9748, 9749}, {9748, 9749}
		};*/
	
public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(c.playerXP[0])
				+ getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4])
				+ getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6])
				+ getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10])
				+ getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12])
				+ getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16])
				+ getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18])
				+ getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20])
				+ getLevelForXP(c.playerXP[21]) + getLevelForXP(c.playerXP[22]));
		sendFrame126("Levels: " + totalLevel, 13983);


	
		switch (skill) {
		case 0:
			sendFrame126("Congratulations, you just advanced an attack level!", 6248);
			sendFrame126("Your attack level is now " + getLevelForXP(c.playerXP[skill]) + ".", 6249);
			c.sendMessage("Congratulations, you just advanced an attack level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			sendFrame164(6247);
			/*if (c.getItems().freeSlots > 1) {
				c.getItems().addItem();
			} else {
				c.getItems().bankItem();
			}*/
			break;

		case 1:
			sendFrame126("Congratulations, you just advanced a defence level!",
					6254);
			sendFrame126("Your defence level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6255);
			c.sendMessage("Congratulations, you just advanced a defence level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "",
					3983);
			sendFrame164(6253);
			break;

		case 2:
			sendFrame126(
					"Congratulations, you just advanced a strength level!",
					6207);
			sendFrame126("Your strength level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6208);
			c.sendMessage("Congratulations, you just advanced a strength level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "",
					3983);
			sendFrame164(6206);
			break;

		case 3:
			sendFrame126(
					"Congratulations, you just advanced a hitpoints level!",
					6217);
			sendFrame126("Your hitpoints level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6218);
			c.sendMessage("Congratulations, you just advanced a hitpoints level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "",
					3983);
			sendFrame164(6216);
			break;

		case 4:
			sendFrame126("Congratulations, you just advanced a ranged level!",
					5453);
			sendFrame126("Your ranged level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6114);
			c.sendMessage("Congratulations, you just advanced a ranging level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "",
					3983);
			sendFrame164(4443);
			break;

		case 5:
			sendFrame126("Congratulations, you just advanced a prayer level!",
					6243);
			sendFrame126("Your prayer level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6244);
			c.sendMessage("Congratulations, you just advanced a prayer level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "",
					3983);
			sendFrame164(6242);
			break;

		case 6:
			sendFrame126("Congratulations, you just advanced a magic level!",
					6212);
			sendFrame126("Your magic level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6213);
			c.sendMessage("Congratulations, you just advanced a magic level.");
			c.getPA().sendFrame126("Combat Level: " + c.getCombatLevel() + "",
					3983);
			sendFrame164(6211);
			break;

		case 7:
			sendFrame126("Congratulations, you just advanced a cooking level!",
					6227);
			sendFrame126("Your cooking level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6228);
			c.sendMessage("Congratulations, you just advanced a cooking level.");
			sendFrame164(6226);
			break;

		case 8:
			sendFrame126(
					"Congratulations, you just advanced a woodcutting level!",
					4273);
			sendFrame126("Your woodcutting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4274);
			c.sendMessage("Congratulations, you just advanced a woodcutting level.");
			sendFrame164(4272);
			break;

		case 9:
			sendFrame126(
					"Congratulations, you just advanced a fletching level!",
					6232);
			sendFrame126("Your fletching level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6233);
			c.sendMessage("Congratulations, you just advanced a fletching level.");
			sendFrame164(6231);
			break;

		case 10:
			sendFrame126("Congratulations, you just advanced a fishing level!",
					6259);
			sendFrame126("Your fishing level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6260);
			c.sendMessage("Congratulations, you just advanced a fishing level.");
			sendFrame164(6258);
			break;

		case 11:
			sendFrame126(
					"Congratulations, you just advanced a fire making level!",
					4283);
			sendFrame126("Your firemaking level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4284);
			c.sendMessage("Congratulations, you just advanced a fire making level.");
			sendFrame164(4282);
			break;

		case 12:
			sendFrame126(
					"Congratulations, you just advanced a crafting level!",
					6264);
			sendFrame126("Your crafting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6265);
			c.sendMessage("Congratulations, you just advanced a crafting level.");
			sendFrame164(6263);
			break;

		case 13:
			sendFrame126(
					"Congratulations, you just advanced a smithing level!",
					6222);
			sendFrame126("Your smithing level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6223);
			c.sendMessage("Congratulations, you just advanced a smithing level.");
			sendFrame164(6221);
			break;

		case 14:
			sendFrame126("Congratulations, you just advanced a mining level!",
					4417);
			sendFrame126("Your mining level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4438);
			c.sendMessage("Congratulations, you just advanced a mining level.");
			sendFrame164(4416);
			break;

		case 15:
			sendFrame126(
					"Congratulations, you just advanced a herblore level!",
					6238);
			sendFrame126("Your herblore level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6239);
			c.sendMessage("Congratulations, you just advanced a herblore level.");
			sendFrame164(6237);
			break;

		case 16:
			sendFrame126("Congratulations, you just advanced a agility level!",
					4278);
			sendFrame126("Your agility level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4279);
			c.sendMessage("Congratulations, you just advanced an agility level.");
			sendFrame164(4277);
			break;

		case 17:
			sendFrame126(
					"Congratulations, you just advanced a thieving level!",
					4263);
			sendFrame126("Your theiving level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4264);
			c.sendMessage("Congratulations, you just advanced a thieving level.");
			sendFrame164(4261);
			break;

		case 18:
			sendFrame126("Congratulations, you just advanced a slayer level!",
					12123);
			sendFrame126("Your slayer level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 12124);
			c.sendMessage("Congratulations, you just advanced a slayer level.");
			sendFrame164(12122);
			break;

		case 20:
			sendFrame126(
					"Congratulations, you just advanced a runecrafting level!",
					4268);
			sendFrame126("Your runecrafting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations, you just advanced a runecrafting level.");
			sendFrame164(4267);
			break;

		case 21:
			c.sendMessage("Congratulations, you just advanced a hunter level!");
			c.sendMessage("Your hunter level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".");
			break;

		case 24:
			c.sendMessage("Congratulations, you just advanced a summoning level!");
			c.sendMessage("Your summoning level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".");
			break;
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
	}

	public void refreshSkill(int i) {
		switch (i) {
		case 0:
			sendFrame126("" + c.playerLevel[0] + "", 4004);
			sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 4005);
			sendFrame126("" + c.playerXP[0] + "", 4044);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1)
					+ "", 4045);
			break;

		case 1:
			sendFrame126("" + c.playerLevel[1] + "", 4008);
			sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 4009);
			sendFrame126("" + c.playerXP[1] + "", 4056);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1)
					+ "", 4057);
			break;

		case 2:
			sendFrame126("" + c.playerLevel[2] + "", 4006);
			sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 4007);
			sendFrame126("" + c.playerXP[2] + "", 4050);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1)
					+ "", 4051);
			break;

		case 3:
			if (c.newHp == false) {
				sendFrame126("" + c.playerLevel[3] + "", 4016);
				sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
				sendFrame126("" + c.playerXP[3] + "", 4080);
				sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3]) + 1)
						+ "", 4081);
			} else {
				sendFrame126("" + c.playerLevel[3] * 10 + "", 4016);
				sendFrame126("" + getLevelForXP(c.playerXP[3]) * 10 + "", 4017);
				sendFrame126("" + c.playerXP[3] + "", 4080);
				sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3]) + 1)
						+ "", 4081);
			}
			break;

		case 4:
			sendFrame126("" + c.playerLevel[4] + "", 4010);
			sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 4011);
			sendFrame126("" + c.playerXP[4] + "", 4062);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1)
					+ "", 4063);
			break;

		case 5:
		   if (c.newPray == false) {
			sendFrame126("" + c.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			sendFrame126("" + c.playerXP[5] + "", 4068);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1)
					+ "", 4069);
			sendFrame126("" + c.playerLevel[5] + "/"
					+ getLevelForXP(c.playerXP[5]) + "", 687);// Prayer frame
			} else {
			 sendFrame126("" + c.playerLevel[5] * 10 + "", 4012);
			 sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			 sendFrame126("" + c.playerXP[5] + "", 4068);
			 sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1)
			          + "", 4069);
			sendFrame126("" + c.playerLevel[5] + "/"
			         + getLevelForXP(c.playerXP[5]) + "", 687);
			}		 
			break;

		case 6:
			sendFrame126("" + c.playerLevel[6] + "", 4014);
			sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 4015);
			sendFrame126("" + c.playerXP[6] + "", 4074);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1)
					+ "", 4075);
			break;

		case 7:
			sendFrame126("" + c.playerLevel[7] + "", 4034);
			sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 4035);
			sendFrame126("" + c.playerXP[7] + "", 4134);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[7]) + 1)
					+ "", 4135);
			break;

		case 8:
			sendFrame126("" + c.playerLevel[8] + "", 4038);
			sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 4039);
			sendFrame126("" + c.playerXP[8] + "", 4146);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1)
					+ "", 4147);
			break;

		case 9:
			sendFrame126("" + c.playerLevel[9] + "", 4026);
			sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 4027);
			sendFrame126("" + c.playerXP[9] + "", 4110);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1)
					+ "", 4111);
			break;

		case 10:
			sendFrame126("" + c.playerLevel[10] + "", 4032);
			sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 4033);
			sendFrame126("" + c.playerXP[10] + "", 4128);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1)
					+ "", 4129);
			break;

		case 11:
			sendFrame126("" + c.playerLevel[11] + "", 4036);
			sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 4037);
			sendFrame126("" + c.playerXP[11] + "", 4140);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1)
					+ "", 4141);
			break;

		case 12:
			sendFrame126("" + c.playerLevel[12] + "", 4024);
			sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 4025);
			sendFrame126("" + c.playerXP[12] + "", 4104);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1)
					+ "", 4105);
			break;

		case 13:
			sendFrame126("" + c.playerLevel[13] + "", 4030);
			sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 4031);
			sendFrame126("" + c.playerXP[13] + "", 4122);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1)
					+ "", 4123);
			break;

		case 14:
			sendFrame126("" + c.playerLevel[14] + "", 4028);
			sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 4029);
			sendFrame126("" + c.playerXP[14] + "", 4116);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1)
					+ "", 4117);
			break;

		case 15:
			sendFrame126("" + c.playerLevel[15] + "", 4020);
			sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 4021);
			sendFrame126("" + c.playerXP[15] + "", 4092);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1)
					+ "", 4093);
			break;

		case 16:
			sendFrame126("" + c.playerLevel[16] + "", 4018);
			sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 4019);
			sendFrame126("" + c.playerXP[16] + "", 4086);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1)
					+ "", 4087);
			break;

		case 17:
			sendFrame126("" + c.playerLevel[17] + "", 4022);
			sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 4023);
			sendFrame126("" + c.playerXP[17] + "", 4098);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1)
					+ "", 4099);
			break;

		case 18:
			sendFrame126("" + c.playerLevel[18] + "", 12166);
			sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 12167);
			sendFrame126("" + c.playerXP[18] + "", 12171);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1)
					+ "", 12172);
			break;

		case 19:
			sendFrame126("" + c.playerLevel[19] + "", 13926);
			sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 13927);
			sendFrame126("" + c.playerXP[19] + "", 13921);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1)
					+ "", 13922);
			break;

		case 20:
			sendFrame126("" + c.playerLevel[20] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 4153);
			sendFrame126("" + c.playerXP[20] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1)
					+ "", 4158);
			break;

		case 21:
			sendFrame126("" + c.playerLevel[21] + "", 29800); // hunter
			break;

		case 24:	
			sendFrame126("" + c.playerLevel[24] + "", 29801);
			/*sendFrame126("" + c.playerLevel[24] + "", 29799);
			sendFrame126("" + c.playerXP[24] + "", 29803);*/

			break;
		}
	}


	
	
	
	
	
	
	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double) lvl + 300.0
					* Math.pow(2.0, (double) lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double) lvl + 300.0
					* Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public boolean addSkillXP(int amount, int skill) {
		if (c.xpLock == true || c.Jail == true) {
			return false;
		}
		if (amount + c.playerXP[skill] < 0 || c.playerXP[skill] > 200000000) {
			if (c.playerXP[skill] > 200000000) {
				c.playerXP[skill] = 200000000;
			}
			return false;
		}
		if (c.playerEquipment[c.playerRing] == 15017) //double xp ring
		{ 
			amount *= Config.SERVER_EXP_BONUS * 2;
		} else {
			amount *= Config.SERVER_EXP_BONUS;
		}
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill])
					&& skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public void resetBarrows() {
		c.barrowsNpcs[0][1] = 0;
		c.barrowsNpcs[1][1] = 0;
		c.barrowsNpcs[2][1] = 0;
		c.barrowsNpcs[3][1] = 0;
		c.barrowsNpcs[4][1] = 0;
		c.barrowsNpcs[5][1] = 0;
		c.barrowsKillCount = 0;
		c.randomCoffin = Misc.random(3) + 1;
	}

	public static int Barrows[] = { 4708, 4710, 4712, 4714, 4716, 4718, 4720,
			4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747,
			4749, 4751, 4753, 4755, 4757, 4759 };
	public static int Crystal[] = {11732,
			14499, 11235, 11128, 10939, 10940, 10941, 10862, 10828, 10828,
			10825, 10802, 10796, 10772, 10770, 10768, 10766, 10764, 10762,
			10760, 10758, 10756, 10754, 10752, 10750, 10748, 10742, 10732,
			10728, 10727, 10726, 10725, 10724, 10722, 10721, 10718, 10717,
			10716, 10715, 10714, 10713, 10712, 10710, 10708, 10707, 10705,
			10703, 10702, 10700, 10696, 10694, 10693, 10689, 10688, 10679,
			10678, 10677, 10676, 10675, 10674, 10673, 10672, 10671, 10670,
			10669, 10668, 10667, 10666, 10665, 10664, 10663, 10654, 10635,
			10633, 10632, 10631, 10630, 10629, 10628, 10627, 10626, 10625,
			10624, 10623, 10622, 10621, 10612, 10604, 10400, 10402, 10404,
			10406, 10408, 10410, 10412, 10414, 10416, 10418, 10420, 10422,
			10424, 10426, 10428, 10430, 10432, 10434, 10436, 10438, 10077,
			10079, 10081, 10083, 10085, 10067, 10063, 10059, 10055, 10047,
			10045, 10041, 10039, 10035, 10023, 9790, 9789, 9678, 9676, 9674,
			9672, 9629, 9097, 9098, 9099, 9100, 9084, 9044, 8971, 8970, 8969,
			8968, 8967, 8966, 8965, 8964, 8963, 8962, 8961, 8960, 8959, 8958,
			8957, 8956, 8955, 8954, 8953, 8952, 8950, 8856, 8650, 8652, 8654,
			8656, 8658, 8660, 8662, 8664, 8666, 8668, 8670, 8672, 8674, 8676,
			8678, 8680, 7809, 7808, 7807, 7806, 7804, 7803, 7668, 6733 };
	public static int EasyClue[] = {4740};
	public static int MediumClue[] = {4740};
	public static int HardClue[] = {4740};
	public static int EliteClue[] = {4740};
	public static int Runes[] = { 4708, 4710, 4712, 4714, 4716, 4718, 4720,
			4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747,
			4749, 4751, 4753, 4755, 4757, 4759};
	public static int Pots[] = {};
	public static int arti[] = { 14876, 14877, 14878, 14879, 14880, 14881,
			14882, 14883, 14884, 14885, 14886, 14887, 14888, 14889, 14890,
			14891, 14892 };
	public static int[] LvlStacks1 ={995};
	public static int[] LvlUnStacks1 = {4151};
	public static int[] AllLvlCash = {995};
	public int randomLvlStacks1() {
			Misc.random(2500);
		return LvlStacks1[(int) (Math.random() * LvlStacks1.length)];
	}
	public int randomBarrows() {
		return Barrows[(int) (Math.random() * Barrows.length)];
	}
	public int randomCrystal() {
		return Crystal[(int) (Math.random() * Crystal.length)];
	}

	public int randomEasyClue() {
		return EasyClue[(int) (Math.random() * EasyClue.length)];
	}

	public int randomMediumClue() {
		return MediumClue[(int) (Math.random() * MediumClue.length)];
	}

	public int randomHardClue() {
		return HardClue[(int) (Math.random() * HardClue.length)];
	}

	public int randomEliteClue() {
		return EliteClue[(int) (Math.random() * EliteClue.length)];
	}

	public int randomRunes() {
		return Runes[(int) (Math.random() * Runes.length)];
	}

	public int randomPots() {
		return Pots[(int) (Math.random() * Pots.length)];
	}

	/**
	 * Show an arrow icon on the selected player.
	 * 
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		// synchronized(c) {
		c.outStream.createFrame(254);
		c.outStream.writeByte(i);

		if (i == 1 || i == 10) {
			c.outStream.writeWord(j);
			c.outStream.writeWord(k);
			c.outStream.writeByte(l);
		} else {
			c.outStream.writeWord(k);
			c.outStream.writeWord(l);
			c.outStream.writeByte(j);
		}
	}

	// }

	public int getNpcId(int id) {
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}

	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove2(2638, 4688);
		objectToRemove2(3253, 3267);
		objectToRemove2(3107, 3274);
		objectToRemove(2638, 4688);
		objectToRemove(2844, 3440);
		objectToRemove(2846, 3437);
		objectToRemove(2840, 3439);
		objectToRemove(2841, 3443);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
		objectToRemove2(2219, 4956);
		objectToRemove2(2220, 4957);
		
	}

	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
		c.usingGlory = true;
	}
	
	public void handleWearingForinthryBrace(int itemId) {
		switch (itemId) {
			case 11095:
				if (c.forinthryBrace < 0) {
					c.getItems().wearItem(11097, 1, 9);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11097:
				if (c.forinthryBrace < 0) {
					c.getItems().wearItem(11099, 1, 9);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11099:
				if (c.forinthryBrace < 0) {
					c.getItems().wearItem(11101, 1, 9);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11101:
				if (c.forinthryBrace < 0) {
					c.getItems().wearItem(11103, 1, 9);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11103:
				if (c.forinthryBrace < 0) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().removeItem(11103, c.playerHands);
						c.getItems().deleteItem(11103, c.getItems().getItemSlot(11103), 1);
						c.forinthryBrace = 120;
						c.sendMessage("You are immune against revenants for one minute.");
					} else {
						c.sendMessage("You need at least one free inventory space to do this.");
					}
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
		}
	}
	
	public void handleForinthryBrace(int itemId) {
		switch (itemId) {
			case 11095:
				if (c.forinthryBrace < 0) {
					c.getItems().deleteItem(11095, c.getItems().getItemSlot(11095), 1);
					c.getItems().addItem(11097, 1);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11097:
				if (c.forinthryBrace < 0) {
					c.getItems().deleteItem(11097, c.getItems().getItemSlot(11097), 1);
					c.getItems().addItem(11099, 1);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11099:
				if (c.forinthryBrace < 0) {
					c.getItems().deleteItem(11099, c.getItems().getItemSlot(11099), 1);
					c.getItems().addItem(11101, 1);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11101:
				if (c.forinthryBrace < 0) {
					c.getItems().deleteItem(11101, c.getItems().getItemSlot(11101), 1);
					c.getItems().addItem(11103, 1);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
			case 11103:
				if (c.forinthryBrace < 0) {
					c.getItems().deleteItem(11103, c.getItems().getItemSlot(11103), 1);
					c.forinthryBrace = 120;
					c.sendMessage("You are immune against revenants for one minute.");
				} else {
					c.sendMessage("You still have time left against revenants.");
				}
			break;
		}
	}

	public void resetVariables() {
		c.getCrafting().resetCrafting();
		c.usingGlory = false;
		c.smeltInterface = false;
		c.smeltType = 0;
		c.smeltAmount = 0;
		c.woodcut[0] = c.woodcut[1] = c.woodcut[2] = 0;
		c.mining[0] = c.mining[1] = c.mining[2] = 0;
	}

	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175
				&& c.getY() >= 5169;
	}

	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}

	public void removeFromCW() {
		if (c.castleWarsTeam == 1) {
			if (c.inCwWait) {
				Server.castleWars.saradominWait
						.remove(Server.castleWars.saradominWait
								.indexOf(c.playerId));
			} else {
				Server.castleWars.saradomin.remove(Server.castleWars.saradomin
						.indexOf(c.playerId));
			}
		} else if (c.castleWarsTeam == 2) {
			if (c.inCwWait) {
				Server.castleWars.zamorakWait
						.remove(Server.castleWars.zamorakWait
								.indexOf(c.playerId));
			} else {
				Server.castleWars.zamorak.remove(Server.castleWars.zamorak
						.indexOf(c.playerId));
			}
		}
	}

	public int antiFire() {
		int toReturn = 0;
		if (c.antiFirePot)
			toReturn++;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12]
				|| c.playerEquipment[c.playerShield] == 11284
				|| c.playerEquipment[c.playerShield] == 11283)
			toReturn++;
		return toReturn;
	}

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 },
				{ 2402, 5 }, { 746, 5 }, { 4151, 150 }, { 565, 100000 },
				{ 560, 100000 }, { 555, 300000 }, { 11235, 10 } };
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < c.getItems().getTotalCount(
					itemsToCheck[j][0]))
				return true;
		}
		return false;
	}

	public void teleTabTeleport(int x, int y, int height, String teleportType) {
		if (c.inPits) {
			c.sendMessage("You can't teleport during Fight Pits.");
			return;
		}
		if (c.getPA().inPitsWait()) {
			c.sendMessage("You can't teleport during Fight Pits.");
			return;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("teleTab")) {
				c.startAnimation(4731);
				c.teleEndAnimation = 0;
				c.teleTimer = 8;
				c.gfx0(678);
			}
		}
	}

	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0)
				count++;
		}
		return count;
	}

	public void useOperate(int itemId) {
		switch (itemId) {
		case 1712:
		case 1710:
		case 1708:
		case 1706:
			handleGlory(itemId);
		break;
		case 11095:
			handleWearingForinthryBrace(itemId);
		break;
		case 11097:
			handleWearingForinthryBrace(itemId);
		break;
		case 11099:
			handleWearingForinthryBrace(itemId);
		break;
		case 11101:
			handleWearingForinthryBrace(itemId);
		break;
		case 11103:
			handleWearingForinthryBrace(itemId);
		break;
		case 11283:
		case 11284:
			if (c.playerIndex > 0) {
				c.getCombat().handleDfs();
			} else if (c.npcIndex > 0) {
				c.getCombat().handleDfsNPC();
			}
			/*
			 * case 6731: c.getCombat().handleSeers(); break; case 3842:
			 * c.getCombat().Zammybook(); break;
			 */
		}
	}

	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		moveCheck(x, y);
		c.lastSpear = System.currentTimeMillis();
	}

	public void moveCheck(int xMove, int yMove) {
		movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
	}

	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < Config.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX,
					PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY + 9400,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}

	public void resetTzhaar() {
		c.waveId = -1;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		c.getPA().movePlayer(2438, 5168, 0);
	}

	public void resetRFD() {
		c.waveId = -1;
		c.RFDToKill = -1;
		c.RFDKilled = -1;
		c.getPA().movePlayer(3274, 3166, 0);
	}
	public void enterRFD() {
		if (c.Culin == true) {
			c.sendMessage("You have already finished this minigame!");
			return;
		}
		if (c.Agrith == true && c.Flambeed == false) {
			c.waveId = 1;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Flambeed == true && c.Karamel == false) {
			c.waveId = 2;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Karamel == true && c.Dessourt == false) {
			c.waveId = 3;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Dessourt == true && c.Culin == false) {
			c.waveId = 4;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Agrith == false) {
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			c.waveId = 0;
			c.RFDToKill = -1;
			c.RFDKilled = -1;
			Server.rfd.spawnNextWave(c);
		}
	}



	public void enterCaves() {
		c.getPA().movePlayer(2413, 5117, c.playerId * 4);
		c.waveId = 0;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		Server.fightCaves.spawnNextWave(c);
		c.jadSpawn();
	}

	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned.");
			c.poisonDamage = damage;
		}
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}
		}
		return false;
	}

	public void checkPouch(int i) {
		if (i < 0)
			return;
		c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");
	}

	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > c.getItems().getItemAmount(1436)) {
			toAdd = c.getItems().getItemAmount(1436);
		}
		if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
			toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > 0) {
			c.getItems().deleteItem(1436, toAdd);
			c.pouches[i] += toAdd;
		}
	}

	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.pouches[i];
		if (toAdd > c.getItems().freeSlots()) {
			toAdd = c.getItems().freeSlots();
		}
		if (toAdd > 0) {
			c.getItems().addItem(1436, toAdd);
			c.pouches[i] -= toAdd;
		}
	}

	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j] - 1 == c.getItems().brokenBarrows[i][1]) {
					if (totalCost + 80000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 80000;
					}
					c.playerItems[j] = c.getItems().brokenBarrows[i][0] + 1;
				}
			}
			if (breakOut)
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
					totalCost);
	}

	public void handleLoginText() {
		c.getPA().sendFrame126("Monster Teleport", 13037);
		c.getPA().sendFrame126("Minigame Teleport", 13047);
		c.getPA().sendFrame126("Boss Teleport", 13055);
		c.getPA().sendFrame126("Pking Teleport", 13063);
		c.getPA().sendFrame126("Skill Teleport", 13071);
		c.getPA().sendFrame126("Monster Teleport", 1300);
		c.getPA().sendFrame126("Minigame Teleport", 1325);
		c.getPA().sendFrame126("Boss Teleport", 1350);
		c.getPA().sendFrame126("Pking Teleport", 1382);
		c.getPA().sendFrame126("Skill Teleport", 1415);
		c.getPA().sendFrame126("City Teleport", 1454);
		c.getPA().sendFrame126("Skill Teleport 2", 7457);
		c.getPA().sendFrame126("Boss Teleport 2", 13097);
		c.getPA().sendFrame126("Skill Teleport 2", 13089);
		c.getPA().sendFrame126("City Teleport", 13081);
		c.getPA().sendFrame126("Ta Quir Priw", 809);
		c.getPA().sendFrame126("Lemanto Andra", 810);
		c.getPA().sendFrame126("Sindarpos", 811);
		c.getPA().sendFrame126("Gandius", 812);
		c.getPA().sendFrame126("Kar-Hewo", 813);
		c.getPA().sendFrame126("Lemantolly", 12338);
		c.getPA().sendFrame126("Undri", 12339);
	}

	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}
	
	public void forceAttack(int id) {
			c.playerIndex = id;
			if(PlayerHandler.players[c.playerIndex] == null){
				return;
			}
			
			if(c.respawnTimer > 0) {
				return;
			}

			if(c.trade11 > 0) {
				return;
			}
			
			if (c.autocastId > 0)
				c.autocasting = true;
			
			if (!c.autocasting && c.spellId > 0) {
				c.spellId = 0;
			}
			c.mageFollow = false;
			c.spellId = 0;
			c.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185;
			for (int bowId : Player.BOWS) {
				if(c.playerEquipment[c.playerWeapon] == bowId) {
					usingBow = true;
					for (int arrowId : Player.ARROWS) {
						if(c.playerEquipment[c.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
				if(c.playerEquipment[c.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}
			if(c.duelStatus == 5) {	
				if(c.duelCount > 0) {
					c.sendMessage("The duel hasn't started yet!");
					c.playerIndex = 0;
					return;
				}
				if(c.duelRule[9]){
					boolean canUseWeapon = false;
					for(int funWeapon: Config.FUN_WEAPONS) {
						if(c.playerEquipment[c.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if(!canUseWeapon) {
						c.sendMessage("You can only use fun weapons in this duel!");
						return;
					}
				}
				
				if(c.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
					c.sendMessage("Range has been disabled in this duel!");
					return;
				}
				if(c.duelRule[3] && (!usingBow && !usingOtherRangeWeapons)) {
					c.sendMessage("Melee has been disabled in this duel!");
					return;
				}
			}
			
			if((usingBow || c.autocasting) && c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(), PlayerHandler.players[c.playerIndex].getY(), 6)) {
				c.usingBow = true;
				c.stopMovement();
			}
			
			if(usingOtherRangeWeapons && c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(), PlayerHandler.players[c.playerIndex].getY(), 3)) {
				c.usingRangeWeapon = true;
				c.stopMovement();
			}
			if (!usingBow)
				c.usingBow = false;
			if (!usingOtherRangeWeapons)
				c.usingRangeWeapon = false;

			if(!usingCross && !usingArrows && usingBow && c.playerEquipment[c.playerWeapon] < 4212 && c.playerEquipment[c.playerWeapon] > 4223) {
				c.sendMessage("You have run out of arrows!");
				return;
			} 
			if(c.getCombat().correctBowAndArrows() < c.playerEquipment[c.playerArrows] && Config.CORRECT_ARROWS && usingBow && !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185) {
					c.sendMessage("You can't use "+c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase()+"s with a "+c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()+".");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return;
			}
			if (c.playerEquipment[c.playerWeapon] == 9185 && !c.getCombat().properBolts()) {
					c.sendMessage("You must use bolts with a crossbow.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return;				
			}
			if (c.getCombat().checkReqs()) {
				c.followId = c.playerIndex;
				if (!c.usingMagic && !usingBow && !usingOtherRangeWeapons) {
					c.followDistance = 1;
					c.getPA().followPlayer(c.playerIndex);
				}	
				if (c.attackTimer <= 0) {
					//c.sendMessage("Tried to attack...");
					//c.getCombat().attackPlayer(c.playerIndex);
					//c.attackTimer++;
				}	
			}
	}

}