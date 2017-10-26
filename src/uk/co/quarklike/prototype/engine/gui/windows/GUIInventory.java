package uk.co.quarklike.prototype.engine.gui.windows;

import org.newdawn.slick.Color;

import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.GraphicsManager;
import uk.co.quarklike.prototype.engine.gui.GUIImage;
import uk.co.quarklike.prototype.engine.gui.GUIItem;
import uk.co.quarklike.prototype.engine.gui.GUIText;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.ItemStack;
import uk.co.quarklike.prototype.map.item.type.ItemType;

public class GUIInventory extends GUIWindow {
	private EntityLiving player;
	private int item;
	private GUIImage selection;
	private GUIText number;
	private GUIImage upArrow;
	private GUIImage downArrow;

	public GUIInventory(ContentHub contentHub, EntityLiving player) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;
		this.player = player;

		init();
	}

	@Override
	public void init() {
		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
		comps.add(new GUIText(x, nearY, GraphicsManager.titleFont, Color.white, "GUI_WINDOW_INVENTORY_TITLE", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));
		comps.add(selection = new GUIImage(x, y, width, 64, "gui/selection.png"));

		comps.add(number = new GUIText(farX, nearY, GraphicsManager.defaultFont, Color.white, (item + 1) + " / " + player.getInventory().getItems().size(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(upArrow = new GUIImage(nearX, nearY - 10, 16, 16, "gui/arrow_up_on.png"));
		comps.add(downArrow = new GUIImage(nearX, nearY + 10, 16, 16, "gui/arrow_down_on.png"));

		int startItem = Util.clamp(item - 3, 0, item);
		int endItem = Util.clamp(startItem + 7, item, player.getInventory().getItems().size());

		for (int i = startItem; i < endItem; i++) {
			ItemStack itemStack = player.getInventory().getItems().get(i);
			int yOffs = nearY + 64 + ((i - startItem) * 48);
			comps.add(new GUIItem(nearX, yOffs, 32, 32, itemStack.getItemID()));
			comps.add(new GUIText(nearX + 48, yOffs, GraphicsManager.defaultFont, Color.white, Item.getItem(itemStack.getItemID()).getName(), GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
			comps.add(new GUIText(farX, yOffs, GraphicsManager.defaultFont, Color.white, "x " + itemStack.getQuantity(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		}

		ItemType itemType = Item.getItem(player.getInventory().getItems().get(item).getItemID()).getItemType();
		boolean useable = itemType.isUseable();

		comps.add(new GUIText(nearX, farY, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_INVENTORY_COMMAND_DROP", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(x, farY, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_INVENTORY_COMMAND_THROW", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, farY, GraphicsManager.defaultFont, useable ? Color.white : Color.gray, itemType.getUseText(), GUIText.ALIGN_RIGHT, GUIText.CASE_TITLE));

		setItem(item);
	}

	@Override
	public void refresh() {
		int startItem = Util.clamp(item - 3, 0, item);
		int endItem = Util.clamp(startItem + 7, item, player.getInventory().getItems().size());

		selection.setY((y - (height / 2) + 96) + ((item - startItem) * 48));

		if (item == 0)
			upArrow.setTexture("gui/arrow_up_off.png");
		else
			upArrow.setTexture("gui/arrow_up_on.png");

		if (item == player.getInventory().getItems().size() - 1)
			downArrow.setTexture("gui/arrow_down_off.png");
		else
			downArrow.setTexture("gui/arrow_down_on.png");

		number.setText((item + 1) + " / " + player.getInventory().getItems().size());
	}

	public void setItem(int item) {
		this.item = item;
	}
}
