package server.model.shops;

import server.Config;
import server.Server;
import server.model.items.Item;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.world.ShopHandler;

public class ShopAssistant {

	private Client c;
	
	public ShopAssistant(Client client) {
		this.c = client;
	}
	
	/**
	*Shops
	**/
	
	public void openShop(int ShopID){		
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
	}

	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if(itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}
	
	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true && PlayerHandler.players[i].myShopId == c.myShopId && i != c.playerId) {
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}
	
	
	public void updateshop(int i){
		resetShop(i);
	}
	
	public void resetShop(int ShopID) {
		synchronized(c) {
			int TotalItems = 0;
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
 			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0 || i <= ShopHandler.ShopItemsStandard[ShopID]) {
					if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
						c.getOutStream().writeByte(255); 					
						c.getOutStream().writeDWord_v2(ShopHandler.ShopItemsN[ShopID][i]);	
					} else {
						c.getOutStream().writeByte(ShopHandler.ShopItemsN[ShopID][i]);
					}
					if (ShopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT || ShopHandler.ShopItems[ShopID][i] < 0) {
						ShopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
					}
					c.getOutStream().writeWordBigEndianA(ShopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();	
		}
	}
	
	
	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double TotPrice = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					ShopValue = Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		
		TotPrice = ShopValue;

		if (ShopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1; 
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1; 
			}
		} else if (Type == 1) {
			TotPrice *= 1; 
		}
		return TotPrice;
	}
	
	public int getItemShopValue(int itemId) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == itemId) {
					return (int)Server.itemHandler.ItemList[i].ShopValue;
				}
			}	
		}
		return 0;
	}
	
	
	
	/**
	*buy item from shop (Shop Price)
	**/
	
	public void buyFromShopPrice(int removeId, int removeSlot){
		int ShopValue = (int)Math.floor(getItemShopValue(removeId, 0, removeSlot));
		ShopValue *= 1;
		String ShopAdd = "";
		if (c.myShopId == 20) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Pest Control.");
			return;
		}
		if (c.myShopId == 29) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Tokkul.");
			return;
		}
		if (c.myShopId == 83) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " RuneForge Points");
			return;
		}
				if (c.myShopId == 87) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Donator Points");
			return;
		}
				if (c.myShopId == 17) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Survival Points");
			return;
		}
		if (c.myShopId == 18) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Pest Control.");
			return;
		}
		if (c.myShopId == 21) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Agility Points.");
			return;
		}
		if (c.myShopId == 22) {
			c.sendMessage(c.getItems().getItemName(removeId)+": currently costs " + getSpecialItemValue(removeId) + " Voteing Points.");
			return;
		}
		if (c.myShopId == 15) {
			c.sendMessage("This item current costs " + c.getItems().getUntradePrice(removeId) + " coins.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId)+": currently costs "+ShopValue+" coins"+ShopAdd);
	}
	

	
	
	public int getSpecialItemValue(int id) {
		switch (id) {
			//Tokkul Start
			case 6571:
			return 15000;
			case 11128:
			return 6000;
			
			case 11694:
			return 600;
			case 11696:
			return 400;
			case 13876:
			case 13864:
			return 100;

			
			
			
			
			case 6522:
			return 2;
			case 6523:
			case 6525:
			case 6526:
			return 750;
			case 6527:
			return 2000;
			case 6528:
			return 3000;
			//tokkul end
			case 1767:
			case 1765:	
			case 1771:
			case 5559:
			return 30;
			case 6916:
			case 6918:
			case 6920:
			case 6922:
			case 6924:
			return 70;
			case 11663:
			case 11664:
			case 11665:
			case 8842:
			return 40;
			case 17020:
			return 70;
			case 17004:
			case 17003:
			case 17005:
			case 17002:
			return 50;
			case 15308:
			case 15312:
			case 15316:
			case 15320:
			case 15324:
			return 4;
			case 8839:
			case 8840:
			return 95;
			case 10499:
			return 30;
			case 13887:
			case 13893:
			return 400;
			case 18017:
			case 18018:
			case 18019:
			case 18020:
			return 3;
			case 14508:
			return 250;
			case 8845:
			return 10;
			case 8846:
			return 10;
			case 8847:
			return 15;
			case 8848:
			return 20;
			case 8849:
			case 8850:
			return 25;
			case 10551:
			return 50;
			case 6570:
			return 65;
                        case 10887:
                        return 250;
			case 11851:
			case 12210:
			case 12213:
			case 12216:
			case 12219:
			case 12222:
			return 100;
			case 11730:
			return 180;
			case 18335:
			return 400;
			case 6585:
			return 15;
			case 16713:
			return 400;
                        case 15017:
                        case 15018:
                        return 150;
			case 15055:
			return 5;
                        case 15031:
                        return 600;
                        case 15007:
                        case 15006:
                        return 700;
                        case 15005:
                        case 15004:
                        return 850;
                        case 15003:
                        case 15002:
			case 15001:				
       			case 15000:
                        return 300;
			case 16689:
			return 1000;
			case 17259:
			return 1000;
		        case 17361: 
			return 1000;
			case 16711:
			return 900;
			case 11821:
			return 1000;
			case 11822:
			return 1000;
			case 11820:
			return 800;
			case 15022:
			case 15026:
                        return 750;
			case 15021:
			case 15023:
			return 850;
			case 13350:
			return 2200;
			case 15332:
			return 7;
			case 15220:
			return 250;
			case 15019:
			return 150;
			case 13351:
			return 2200;
                        case 15025:
			return 230;
                        case 15024:
                        return 170;
                        case 15043:
			case 15042:
                        return 790;
                      	case 15041:
                        case 15040:
			return 650;
                        case 15051:
                        return 500;
                        case 15050:
                        return 700;
						//SURVIVAL
						case 11726:
						case 11724:
						case 11722:
						case 11720:
						case 11718:
						return 900;
						case 11728:
						return 750;
						//DONOR
                                                case 19669:
                                                return 225;
                                                case 15241:
                                                return 750;

						case 1050:
						return 1500;
						case 1038:
						case 1040:
						case 1042:
						case 1044:
						case 1046:
			            case 1048:
						return 2500;
						case 1055:
						case 1057:
						case 1053:
						return 2000;
						case 20139:
						case 20135:
						case 20143:
						case 20147:
						case 20151:
						case 20155:
						case 20159:
						case 20163:
						case 20167:
						return 1000;
						//END DONOR				
			case 18355:
			return 700;
			case 19785:
			return 160;
			case 19786:
			return 140;
			case 13858:
			case 13861:
			return 400;
			case 1555:
			case 7584:
			case 1556:
			case 1557:
			case 1558:
			return 150;
			case 13738:
			return 400;
case 19780:
return 500;

			case 13742:
			return 550;
			case 13744:
			return 450;
			case 13740:
			return 900;
			case 14484:
			return 800;
			case 13870:
			case 13873:
			return 300;
			case 18351:
                        case 18353:
                        case 18349:
                        return 800;
			case 13890:
			case 13884:
			return 500;
case 13899:
case 13902:
return 350;
			case 13867:
			return 400;
			case 13896:
			return 250;
case 15486:
			return 150;
			case 15505:
			return 45;
			case 15507:
			return 45;
			case 15509:
			case 15511:
			return 35;
			case 15073:
			return 55;
			case 15074:
			return 55;
			case 17025:
			return 35;
			case 17018:
			return 90;
			case 17019:
			return 80;
			case 15054:
			return 200;
			case 14936:
			return 300;
			case 14938:
			return 250;
			case 15090:
			case 15091:
			case 15092:
			case 15093:
			case 15094:
			case 15095:
			case 15096:
			case 15097:
			case 15098:
			return 350;
			case 15085:
			return 1300;
			case 15081:
			return 1150;
			case 15080:
			return 1150;
 			case 15083:
			return 600;
			case 13352:
			case 13353:
			case 13354:
			return 700;
			case 16222:
			return 1100;
                        case 14497:
                        case 14501:
                        return 150;
                        case 14499:
                        return 60;
                        case 15060:
                        case 15062:
                        return 150;
                        case 15061:
                        return 70;
                        case 15020:
                        return 150;
                        case 2577:
                        case 2581:
                        return 40;
                        case 6914:
                        case 6890:
                        return 45;

		}
		return 0;
	}
	
	
	
	/**
	*Sell item to shop (Shop Price)
	**/
	public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEM_SELLABLE) {

			
			if (i == removeId) {
				c.sendMessage("You can't sell "+c.getItems().getItemName(removeId).toLowerCase()+".");
				return;
			} 

		}
		boolean IsIn = false;
		if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= ShopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (ShopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "+c.getItems().getItemName(removeId).toLowerCase()+" to this store.");
		} else {
			int ShopValue = (int)Math.floor(getItemShopValue(removeId, 1, removeSlot));
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			c.sendMessage(c.getItems().getItemName(removeId)+": shop will buy for "+ShopValue+" coins"+ShopAdd);
		}
	}
	
	
	
	public boolean sellItem(int itemID, int fromSlot, int amount) {
				if(c.myShopId == 83){
			for (int i : Config.UNDROPPABLE_ITEMS)  {
				if(i == itemID) {
					c.sendMessage("You can't sell this item.");
					return false;
				}		
			}
			}
							if(c.myShopId == 1){
			for (int i : Config.UNDROPPABLE_ITEMS)  {
				if(i == itemID) {
					c.sendMessage("You can't sell this item.");
					return false;
				}		
			}
			}
			if(c.inTrade) {
            		c.sendMessage("You cant sell items to the shop while your in trade!");
           		return false;
            		}
		if (c.myShopId == 14)
			return false;
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemID) {
				c.sendMessage("You can't sell "+c.getItems().getItemName(itemID).toLowerCase()+".");
				return false;
}
}
		
		
		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= ShopHandler.ShopItemsStandard[c.myShopId]; i++) {
					if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage("You can't sell "+c.getItems().getItemName(itemID).toLowerCase()+" to this store.");
					return false;
				}
			}

			if (amount > c.playerItemsN[fromSlot] && (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID) && Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false && Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
				amount = c.getItems().getItemAmount(itemID);
			}
			//double ShopValue;
			//double TotPrice;
			int TotPrice2 = 0;
			//int Overstock;
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int)Math.floor(getItemShopValue(itemID, 1, fromSlot));
				if (c.getItems().freeSlots() > 0 || c.getItems().playerHasItem(995)) {
					if (Item.itemIsNote[itemID] == false) {
						c.getItems().deleteItem(itemID, c.getItems().getItemSlot(itemID), 1);
					} else {
						c.getItems().deleteItem(itemID, fromSlot, 1);
					}
					c.getItems().addItem(995, TotPrice2);
					addShopItem(itemID, 1);
				} else {
					c.sendMessage("You don't have enough space in your inventory.");
					break;
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}
	
public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				ShopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
					ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					ShopHandler.ShopItemsN[c.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}
	
	public long buyDelay;
	public boolean buyItem(int itemID, int fromSlot, int amount) {
		if(System.currentTimeMillis() - buyDelay < 1500) {
			return false;

		/*} else if (c.myShopId == 15) {
			buyVoid(itemID);
			return false;*/		
		
		} else if (c.myShopId == 1) {
			buyVoid(itemID);
			return false;
                }
		if(!shopSellsItem(itemID))
			return false;

		if (amount > 0) {
			if (amount > ShopHandler.ShopItemsN[c.myShopId][fromSlot]) {
				amount = ShopHandler.ShopItemsN[c.myShopId][fromSlot];
			}
			//double ShopValue;
			//double TotPrice;
			int TotPrice2 = 0;
			//int Overstock;
			int Slot = 0;
			int Slot1 = 0;//Tokkul
			int Slot3 = 0;//Donator Gold

			if (c.myShopId == 18) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 21) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 22) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 20) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 83) {
				handleOtherShop(itemID);
				return false;
			}
						if (c.myShopId == 87) {
				handleOtherShop(itemID);
				return false;
			}
						if (c.myShopId == 17) {
				handleOtherShop(itemID);
				return false;
			}	
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int)Math.floor(getItemShopValue(itemID, 0, fromSlot));
				Slot = c.getItems().getItemSlot(995);
				Slot1 = c.getItems().getItemSlot(6529);
				Slot3 = c.getItems().getItemSlot(5555);
				if (Slot == -1 && c.myShopId != 11 && c.myShopId != 29 && c.myShopId != 30 && c.myShopId != 31 && c.myShopId != 47) {
					c.sendMessage("You don't have enough coins.");
					break;
				}
				if(Slot1 == -1 && c.myShopId == 29 || c.myShopId == 30 || c.myShopId == 31) {
					c.sendMessage("You don't have enough tokkul.");
					break;
				}
				if(Slot3 == -1 && c.myShopId == 11) {
					c.sendMessage("You don't have enough donator gold.");
					break;
				}
			
                if(TotPrice2 <= 1) {
                	TotPrice2 = (int)Math.floor(getItemShopValue(itemID, 0, fromSlot));
                	TotPrice2 *= 1.66;
                }
                if(c.myShopId == 29 || c.myShopId == 30 || c.myShopId == 31) {
                	if (c.playerItemsN[Slot1] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(6529, c.getItems().getItemSlot(6529), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough tokkul.");
						break;
					}
                }
                else if(c.myShopId == 47) {
                	if (c.pkPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.pkPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Pk Points.");
						break;
					}
                }
 		else if(c.myShopId == 83) {
                	if (c.rxPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.rxPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough RuneForge Points.");
						break;
					}
                }
		 		else if(c.myShopId == 87) {
                	if (c.donPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.donPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Donator Points.");
						break;
					}
                }
		 		else if(c.myShopId == 17) {
                	if (c.srPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.donPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Survival Points.");
						break;
					}
                }
                else if(c.myShopId == 11) {
                	if (c.playerItemsN[Slot3] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(5555, c.getItems().getItemSlot(5555), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough donator gold.");
						break;
					}
                }
                else if(c.myShopId != 11 && c.myShopId != 29 || c.myShopId != 30 || c.myShopId != 31 || c.myShopId != 47) {
					if (c.playerItemsN[Slot] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(995, c.getItems().getItemSlot(995), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough coins.");
						break;
					}
                }
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}	
	
		public void handleOtherShop(int itemID) {
			if (c.myShopId == 20) {
				if (c.pcPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pcPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Pest Control points to buy this item.");			
				}
			} else if (c.myShopId == 18) {
				if (c.pcPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pcPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Pest Control points to buy this item.");			
				}
			} else if (c.myShopId == 83) {
				if (c.rxPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.rxPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough RuneForge points to buy this item.");			
				}
			} else if (c.myShopId == 87) {
				if (c.donPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.donPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Donator Points to buy this item.");			
				}
			} else if (c.myShopId == 17) {
				if (c.srPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.donPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Survival Points to buy this item.");			
				}
			} else if (c.myShopId == 22) {
				if (c.pkPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pkPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Voteing points to buy this item.");			
				}
			} else if (c.myShopId == 21) {
				if (c.magePoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.magePoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Agility Points to buy this item.");			
				}

			}
		}		
		public void openVoid() {
			/*synchronized(c) {
				c.getItems().resetItems(3823);
				c.isShopping = true;
				c.myShopId = 15;
				c.getPA().sendFrame248(3824, 3822);
				c.getPA().sendFrame126("Void Recovery", 3901);
				
				int TotalItems = 5;
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(3900);
				c.getOutStream().writeWord(TotalItems);
				for (int i = 0; i < c.voidStatus.length; i++) {
					c.getOutStream().writeByte(c.voidStatus[i]);
					c.getOutStream().writeWordBigEndianA(2519 + i * 2);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();	
			}*/		
		}

		public void buyVoid(int item) {
			/*if (item > 2527 || item < 2518)
				return;
			//c.sendMessage("" + item);
			if (c.voidStatus[(item-2518)/2] > 0) {
				if (c.getItems().freeSlots() >= 1) {
					if (c.getItems().playerHasItem(995,c.getItems().getUntradePrice(item))) {
						c.voidStatus[(item-2518)/2]--;
						c.getItems().deleteItem(995,c.getItems().getItemSlot(995), c.getItems().getUntradePrice(item));
						c.getItems().addItem(item,1);
						openVoid();
					} else {
						c.sendMessage("This item costs " + c.getItems().getUntradePrice(item) + " coins to rebuy.");				
					}
				} else {
					c.sendMessage("I should have a free inventory space.");
				}
			} else {
				c.sendMessage("I don't need to recover this item from the void knights.");
			}*/
		}


}

