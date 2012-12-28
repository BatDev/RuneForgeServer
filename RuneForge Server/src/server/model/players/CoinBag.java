package server.model.players;

/**
* A coinbag will pickup every coin drop that has been dropped by a npc automaticly.
* CoinBag class.
* @author Aintaro.
*/
public class CoinBag {
	
	/**
	* Itemid of the coinbag
	*/
	private static final int COIN_BAG = 10521;
	/**
	* Takes out the coins from the coinbag
	*/
	public static void emptyCoinBag(Client c, int itemId) {
		if (c.getItems().freeSlots() > 0 && c.coinBag > 0) {
			c.getItems().addItem(995, c.coinBag);
			c.coinBag = 0;
		} else if (c.coinBag == 0) {
			c.sendMessage("Your bag contains no coins.");
		} else {
			c.sendMessage("Not enough space in your inventory.");
		}
	}
	
	/**
	* Adding Coins to Coin Bag @@matt
	**/
		public static void addCoinsToBag(Client c) {{
			int itemAmount = c.getItems().getItemAmount(995);
			int slot = c.getItems().getItemSlot(995);
			if (c.getItems().playerHasItem(995, itemAmount, slot))
				c.getItems().deleteItem(995, slot, itemAmount);
				c.coinBag += itemAmount;
			}
		}
	
	/**
	* Checks how many coins the coinbag has left.
	*/
	public static void lookInCoingBag(Client c, int itemId) {
		if (itemId == COIN_BAG) {
			if(c.coinBag >= 10000 && c.coinBag < 10000000) {
				c.sendMessage("Your coinbag contains " + c.coinBag / 1000  + "K gp.");
				return;
			}
			if(c.coinBag >= 10000000 && c.coinBag < 2147000000) {
				c.sendMessage("Your coinbag contains " + c.coinBag / 1000000  + "M gp.");
				return;
			}
			c.sendMessage("Your coinbag contains " + c.coinBag + " Coins.");
			return;
		}
	}
	
}