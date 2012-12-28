package server.model.items;

public class ItemList {
	public int itemId;
	public String itemName;
	public String itemDescription;
	public double ShopValue;
	public double LowAlch;
	public double HighAlch;
	public double[] Bonuses = new double[100];

	public ItemList(int _itemId) {
		itemId = _itemId;
	}
}
