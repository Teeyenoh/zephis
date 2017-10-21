package uk.co.quarklike.prototype.map.item;

public class ItemStack {
	private int itemID;
	private int quantity;

	public ItemStack(int itemID, int stackSize) {
		this.itemID = itemID;
		this.quantity = stackSize;
	}

	public int getItemID() {
		return itemID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}
}
