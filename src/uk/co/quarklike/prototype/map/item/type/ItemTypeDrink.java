package uk.co.quarklike.prototype.map.item.type;

public class ItemTypeDrink extends ItemTypeConsumable {
	public ItemTypeDrink(short mana, short stamina, byte hunger, byte warmth) {
		super("GUI_WINDOW_INVENTORY_COMMAND_DRINK", (short) 0, mana, stamina, hunger, (byte) 0, warmth);
	}
}
