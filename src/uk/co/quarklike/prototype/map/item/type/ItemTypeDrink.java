package uk.co.quarklike.prototype.map.item.type;

public class ItemTypeDrink extends ItemTypeConsumable {
	public ItemTypeDrink(int mana, int stamina, int warmth) {
		super("GUI_WINDOW_INVENTORY_COMMAND_DRINK", 0, mana, stamina, warmth);
	}
}
