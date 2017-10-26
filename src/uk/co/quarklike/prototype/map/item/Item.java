package uk.co.quarklike.prototype.map.item;

import java.util.HashMap;

import uk.co.quarklike.prototype.Log;
import uk.co.quarklike.prototype.map.item.type.ItemType;

public class Item {
	private static final Item[] items = new Item[256];
	private static HashMap<String, Integer> itemMap = new HashMap<String, Integer>();

	private int itemID;
	private String itemName;
	private ItemType itemType;
	private String itemTexture;
	private short textureSlot;

	public Item(int id, String name, ItemType itemType, String texture, short slot) {
		this.itemID = id;
		this.itemName = name;
		this.itemType = itemType;
		this.itemTexture = texture;
		this.textureSlot = slot;
		if (items[id - 1] != null)
			Log.warn("Attempting to add item to slot: " + id + ", which is filled by item: " + items[id - 1].getName());
		else {
			items[id - 1] = this;
			itemMap.put(name, id);
		}
	}

	public int getID() {
		return itemID;
	}

	public String getName() {
		return itemName;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public String getTexture() {
		return itemTexture;
	}

	public short getTextureSlot() {
		return textureSlot;
	}

	public static Item getItem(int itemID) {
		return items[itemID - 1];
	}

	public static Item getItem(String itemName) {
		return getItem(itemMap.get(itemName));
	}
}
