package uk.co.quarklike.zephis.src.map.item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import uk.co.quarklike.zephis.src.Translation;

public class Inventory {
	private HashMap<Integer, Integer> _items;

	public static final int SORT_QUANTITY = 0;
	public static final int SORT_ALPHABETICAL = 1;
	public static final int SORT_ID = 2;

	private int _sorting = SORT_QUANTITY;

	public Inventory() {
		_items = new HashMap<Integer, Integer>();
	}

	public void addItem(int itemID, int quantity) {
		if (_items.containsKey(itemID)) {
			_items.put(itemID, _items.get(itemID) + quantity);
		} else {
			_items.put(itemID, quantity);
		}
	}

	public void removeItem(int itemID, int quantity) {
		_items.put(itemID, _items.get(itemID) - quantity);
	}

	public int getItemCount(int itemID) {
		return _items.get(itemID);
	}

	public boolean containsItem(int itemID, int quantity) {
		return _items.get(itemID) >= quantity;
	}

	private Comparator<Integer> _sortQuantity = new Comparator<Integer>() {
		public int compare(Integer a, Integer b) {
			return getItemCount(b) - getItemCount(a);
		}
	};

	private Comparator<Integer> _sortAlphabet = new Comparator<Integer>() {
		public int compare(Integer a, Integer b) {
			int i = 0;
			String nameA = Item.getItem(a).getTranslatedName();
			String nameB = Item.getItem(b).getTranslatedName();
			while (nameB.charAt(i) - nameA.charAt(i) == 0) {
				if (i == nameA.length() || i == nameB.length()) {
					return (nameA.length() - nameB.length()) / (Math.abs(nameA.length() - nameB.length()));
				}
				i++;
			}
			return nameA.charAt(i) - nameB.charAt(i);
		}
	};

	public int[] getItems() {
		ArrayList<Integer> list = new ArrayList<Integer>(_items.keySet());

		switch (_sorting) {
		case SORT_QUANTITY:
			list.sort(_sortQuantity);
			break;
		case SORT_ALPHABETICAL:
			list.sort(_sortAlphabet);
			break;
		case SORT_ID:
			list.sort(null);
			break;
		}

		return list.stream().mapToInt(i -> i).toArray();
	}

	public void sortByQuantity() {
		_sorting = SORT_QUANTITY;
	}

	public void sortByAlphabet() {
		_sorting = SORT_ALPHABETICAL;
	}

	public void sortByID() {
		_sorting = SORT_ID;
	}
}