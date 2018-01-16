package uk.co.quarklike.zephis.old.map.item;

import java.util.ArrayList;
import java.util.Comparator;

import uk.co.quarklike.zephis.old.Log;

public class Inventory {
	public static final Comparator<ItemStack> sortByID = new Comparator<ItemStack>() {
		@Override
		public int compare(ItemStack a, ItemStack b) {
			return a.getItemID() - b.getItemID();
		}
	};

	public static final Comparator<ItemStack> sortByQuantity = new Comparator<ItemStack>() {
		@Override
		public int compare(ItemStack a, ItemStack b) {
			return a.getQuantity() - b.getQuantity();
		}
	};

	private int maxSize;
	private ArrayList<ItemStack> items;

	public Inventory(int maxSize) {
		this.maxSize = maxSize;
		items = new ArrayList<ItemStack>();
	}

	public void update() {
		ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();

		for (ItemStack i : items) {
			if (i.getQuantity() <= 0) {
				toRemove.add(i);
			}
		}

		items.removeAll(toRemove);
	}

	public boolean addItem(int itemID, byte quantity) {
		ItemStack i = null;
		if ((i = containsItem(itemID)) == null) {
			if (items.size() < maxSize) {
				items.add(new ItemStack(itemID, quantity));
				return true;
			}
		} else {
			i.addQuantity(quantity);
			return true;
		}

		return false;
	}

	public boolean removeItem(int itemID, int quantity) {
		ItemStack i = null;
		if ((i = containsItem(itemID)) == null) {
			return false;
		} else {
			if (i.getQuantity() >= quantity) {
				i.addQuantity(-quantity);
				return true;
			}
		}

		return false;
	}

	public boolean removeItem(int slot) {
		return removeItem(items.get(slot).getItemID(), 1);
	}

	private ItemStack containsItem(int itemID) {
		for (ItemStack i : items) {
			if (i.getItemID() == itemID)
				return i;
		}
		return null;
	}

	public void printInventory() {
		for (ItemStack i : items) {
			Log.info(Item.getItem(i.getItemID()).getName() + " x " + i.getQuantity());
		}
	}

	public static final int SORT_ID = 1;
	public static final int SORT_QUANT = 2;

	public void sort(int type, boolean ascending) {
		switch (type) {
		case SORT_ID:
			items.sort(sortByID);
			break;
		case SORT_QUANT:
			items.sort(sortByQuantity);
			break;
		}
	}

	public ArrayList<ItemStack> getItems() {
		return items;
	}
}
