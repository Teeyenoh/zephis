package uk.co.quarklike.zephis.old.map.item.type;

public class ItemTypeFood extends ItemTypeConsumable {
	public ItemTypeFood(short health, short stamina, byte hunger, byte warmth) {
		super("GUI_WINDOW_INVENTORY_COMMAND_EAT", health, (short) 0, stamina, hunger, (byte) 0, warmth);
	}
}
