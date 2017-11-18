package uk.co.quarklike.zephis.map.item;

public class ItemStack {
	private int itemID;
	private byte quantity;

	public ItemStack(int itemID, byte stackSize) {
		this.itemID = itemID;
		this.quantity = stackSize;
	}

	public int getItemID() {
		return itemID;
	}

	public byte getQuantity() {
		return quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}
}
