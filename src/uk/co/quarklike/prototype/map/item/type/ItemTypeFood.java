package uk.co.quarklike.prototype.map.item.type;

public class ItemTypeFood extends ItemTypeConsumable {
	public ItemTypeFood(int health, int stamina, int warmth) {
		super("GUI_WINDOW_INVENTORY_COMMAND_EAT", health, 0, stamina, warmth);
	}
}
