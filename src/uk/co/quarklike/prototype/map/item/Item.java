package uk.co.quarklike.prototype.map.item;

import uk.co.quarklike.prototype.Log;

public class Item {
	private static final Item[] items = new Item[256];

	private int itemID;
	private String itemName;
	private String itemTexture;
	private int textureSlot;

	public static final Item testItem = new Item(0, "Test Item", "items/test.png", 0);

	public Item(int id, String name, String texture, int slot) {
		this.itemID = id;
		this.itemName = name;
		this.itemTexture = texture;
		this.textureSlot = slot;
		if (items[id] != null) {
			Log.warn("Attempting to add item to slot: " + id + ", which is filled by item: " + items[id].getName());
		}
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
		return items[itemID];
	}
}
