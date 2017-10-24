package uk.co.quarklike.prototype.map.item;

public class ItemType {
	protected String useText;
	protected boolean useable;

	public ItemType() {
		this.useText = "GUI_WINDOW_INVENTORY_COMMAND_USE";
		this.useable = false;
	}

	public String getUseText() {
		return useText;
	}

	public boolean isUseable() {
		return useable;
	}
}
