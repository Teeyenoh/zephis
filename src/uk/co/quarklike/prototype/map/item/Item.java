package uk.co.quarklike.prototype.map.item;

import uk.co.quarklike.prototype.Log;

public class Item {
	private static final Item[] items = new Item[256];

	private int itemID;
	private String itemName;
	private String itemTexture;
	private int textureSlot;

	public Item(int id, String name, String texture, int slot) {
		this.itemID = id;
		this.itemName = name;
		this.itemTexture = texture;
		this.textureSlot = slot;
		if (items[id - 1] != null)
			Log.warn("Attempting to add item to slot: " + id + ", which is filled by item: " + items[id - 1].getName());
		else
			items[id - 1] = this;
	}

	public int getID() {
		return itemID;
	}

	public String getName() {
		return itemName;
	}

	public String getTexture() {
		return itemTexture;
	}

	public int getTextureSlot() {
		return textureSlot;
	}

	public static Item getItem(int itemID) {
		return items[itemID - 1];
	}
}
