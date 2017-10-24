package uk.co.quarklike.prototype.engine.gui.windows;

import org.newdawn.slick.Color;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.GraphicsManager;
import uk.co.quarklike.prototype.engine.gui.GUIImage;
import uk.co.quarklike.prototype.engine.gui.GUIItem;
import uk.co.quarklike.prototype.engine.gui.GUIText;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.ItemStack;
import uk.co.quarklike.prototype.map.item.ItemType;

public class GUIInventory extends GUIWindow {
	private EntityLiving player;
	private int item;
	private GUIImage selection;

	public GUIInventory(ContentHub contentHub, EntityLiving player) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;
		this.player = player;
		refresh();
	}

	public void refresh() {
		comps.clear();

		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int xOffs = nearX;
		int yOffs = nearY;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
		comps.add(new GUIText(x, nearY, GraphicsManager.titleFont, Color.white, "GUI_WINDOW_INVENTORY_TITLE", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));
		comps.add(selection = new GUIImage(x, y, width, 64, "gui/selection.png"));

		for (ItemStack i : player.getInventory().getItems()) {
			yOffs += 48;
			comps.add(new GUIItem(xOffs, yOffs, 32, 32, i.getItemID()));
			comps.add(new GUIText(xOffs + 48, yOffs, GraphicsManager.defaultFont, Color.white, Item.getItem(i.getItemID()).getName(), GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
			comps.add(new GUIText(farX, yOffs, GraphicsManager.defaultFont, Color.white, "x " + i.getQuantity(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		}

		ItemType itemType = Item.getItem(player.getInventory().getItems().get(item).getItemID()).getItemType();
		boolean useable = itemType.isUseable();

		comps.add(new GUIText(nearX, farY, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_INVENTORY_COMMAND_DROP", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(x, farY, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_INVENTORY_COMMAND_THROW", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, farY, GraphicsManager.defaultFont, useable ? Color.white : Color.gray, itemType.getUseText(), GUIText.ALIGN_RIGHT, GUIText.CASE_TITLE));

		setItem(item);
	}

	public void setItem(int item) {
		this.item = item;
		selection.setY((y - (height / 2) + 32 + 48) + (item * 48));
	}
}
