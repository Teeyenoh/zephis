package uk.co.quarklike.zephis.old.engine.gui.windows;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import uk.co.quarklike.zephis.old.Util;
import uk.co.quarklike.zephis.old.engine.ContentHub;
import uk.co.quarklike.zephis.old.engine.GraphicsManager;
import uk.co.quarklike.zephis.old.engine.gui.GUIImage;
import uk.co.quarklike.zephis.old.engine.gui.GUIItem;
import uk.co.quarklike.zephis.old.engine.gui.GUIText;
import uk.co.quarklike.zephis.old.map.entity.EntityLiving;
import uk.co.quarklike.zephis.old.map.item.Inventory;
import uk.co.quarklike.zephis.old.map.item.Item;
import uk.co.quarklike.zephis.old.map.item.ItemStack;
import uk.co.quarklike.zephis.old.map.item.type.ItemType;

public class GUIInventory extends GUIWindow {
	private EntityLiving player;
	private int item;
	private GUIText title;
	private GUIImage selection;
	private GUIText number;
	private GUIImage upArrow;
	private GUIImage downArrow;
	private ArrayList<GUIItem> itemImages;
	private ArrayList<GUIText> itemNames;
	private ArrayList<GUIText> itemCounts;
	private GUIText leftText;
	private GUIText centreText;
	private GUIText rightText;

	private boolean isContainer;
	private Inventory container;
	private String name;
	private int screen;

	public GUIInventory(ContentHub contentHub, EntityLiving player) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;
		this.player = player;

		init();
	}

	public GUIInventory(ContentHub contentHub, EntityLiving player, Inventory container, String name) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;
		this.player = player;
		this.container = container;
		this.name = name;
		isContainer = true;
		screen = 1;

		init();
	}

	@Override
	public void init() {
		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
		comps.add(title = new GUIText(x, nearY, GraphicsManager.titleFont, Color.white, "GUI_WINDOW_INVENTORY_TITLE", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));
		comps.add(selection = new GUIImage(x, y, width, 64, "gui/selection.png"));

		comps.add(number = new GUIText(farX, nearY, GraphicsManager.defaultFont, Color.white, (item + 1) + " / " + player.getInventory().getItems().size(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(upArrow = new GUIImage(nearX, nearY - 10, 16, 16, "gui/arrow_up_on.png"));
		comps.add(downArrow = new GUIImage(nearX, nearY + 10, 16, 16, "gui/arrow_down_on.png"));

		this.itemImages = new ArrayList<GUIItem>();
		this.itemNames = new ArrayList<GUIText>();
		this.itemCounts = new ArrayList<GUIText>();

		if (player.getInventory().getItems().size() == 0)
			return;

		int startItem = Util.clamp(item - 3, 0, item);
		int endItem = Util.clamp(startItem + 7, item, player.getInventory().getItems().size());

		for (int i = startItem; i < endItem; i++) {
			ItemStack itemStack = player.getInventory().getItems().get(i);
			int yOffs = nearY + 64 + ((i - startItem) * 48);
			itemImages.add(new GUIItem(nearX, yOffs, 32, 32, itemStack.getItemID()));
			itemNames.add(new GUIText(nearX + 48, yOffs, GraphicsManager.defaultFont, Color.white, Item.getItem(itemStack.getItemID()).getName(), GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
			itemCounts.add(new GUIText(farX, yOffs, GraphicsManager.defaultFont, Color.white, "x " + itemStack.getQuantity(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		}

		comps.addAll(itemImages);
		comps.addAll(itemNames);
		comps.addAll(itemCounts);

		ItemType itemType = Item.getItem(player.getInventory().getItems().get(item).getItemID()).getItemType();
		boolean useable = itemType.isUseable();

		comps.add(leftText = new GUIText(nearX, farY, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_INVENTORY_COMMAND_DROP", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(centreText = new GUIText(x, farY, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_INVENTORY_COMMAND_THROW", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));
		comps.add(rightText = new GUIText(farX, farY, GraphicsManager.defaultFont, useable ? Color.white : Color.gray, itemType.getUseText(), GUIText.ALIGN_RIGHT, GUIText.CASE_TITLE));

		setItem(item);
	}

	@Override
	public void refresh() {
		switch (screen) {
		case 0:
			screen0();
			break;
		case 1:
			screen1();
			break;
		}
	}

	private void screen0() {
		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		int startItem = Util.clamp(item - 3, 0, item);
		int endItem = Util.clamp(startItem + 7, item, player.getInventory().getItems().size());

		title.setText("GUI_WINDOW_INVENTORY_TITLE");
		selection.setY((y - (height / 2) + 96) + ((item - startItem) * 48));

		if (startItem == 0)
			upArrow.setTexture("gui/arrow_up_off.png");
		else
			upArrow.setTexture("gui/arrow_up_on.png");

		if (endItem >= player.getInventory().getItems().size() - 1)
			downArrow.setTexture("gui/arrow_down_off.png");
		else
			downArrow.setTexture("gui/arrow_down_on.png");

		number.setText((item + 1) + " / " + player.getInventory().getItems().size());

		comps.removeAll(itemImages);
		itemImages.clear();
		comps.removeAll(itemNames);
		itemNames.clear();
		comps.removeAll(itemCounts);
		itemCounts.clear();

		if (player.getInventory().getItems().size() == 0) {
			number.setText("0 / 0");
			return;
		}

		ItemType itemType = Item.getItem(player.getInventory().getItems().get(item).getItemID()).getItemType();
		boolean useable = itemType.isUseable();
		if (isContainer) {
			leftText.setText("");
			centreText.setText("GUI_WINDOW_INVENTORY_COMMAND_PLACE");
		} else {
			leftText.setText("GUI_WINDOW_INVENTORY_COMMAND_DROP");
			centreText.setText("GUI_WINDOW_INVENTORY_COMMAND_THROW");
		}
		rightText.setText(itemType.getUseText());
		rightText.setColour(useable ? Color.white : Color.gray);

		for (int i = startItem; i < endItem; i++) {
			ItemStack itemStack = player.getInventory().getItems().get(i);
			int yOffs = nearY + 64 + ((i - startItem) * 48);
			itemImages.add(new GUIItem(nearX, yOffs, 32, 32, itemStack.getItemID()));
			itemNames.add(new GUIText(nearX + 48, yOffs, GraphicsManager.defaultFont, Color.white, Item.getItem(itemStack.getItemID()).getName(), GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
			itemCounts.add(new GUIText(farX, yOffs, GraphicsManager.defaultFont, Color.white, "x " + itemStack.getQuantity(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		}

		comps.addAll(itemImages);
		comps.addAll(itemNames);
		comps.addAll(itemCounts);
	}

	private void screen1() {
		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		int startItem = Util.clamp(item - 3, 0, item);
		int endItem = Util.clamp(startItem + 7, item, container.getItems().size());

		title.setText(name);
		selection.setY((y - (height / 2) + 96) + ((item - startItem) * 48));

		if (startItem == 0)
			upArrow.setTexture("gui/arrow_up_off.png");
		else
			upArrow.setTexture("gui/arrow_up_on.png");

		if (endItem >= container.getItems().size() - 1)
			downArrow.setTexture("gui/arrow_down_off.png");
		else
			downArrow.setTexture("gui/arrow_down_on.png");

		number.setText((item + 1) + " / " + container.getItems().size());

		comps.removeAll(itemImages);
		itemImages.clear();
		comps.removeAll(itemNames);
		itemNames.clear();
		comps.removeAll(itemCounts);
		itemCounts.clear();

		if (container.getItems().size() == 0) {
			number.setText("0 / 0");
			return;
		}

		ItemType itemType = Item.getItem(container.getItems().get(item).getItemID()).getItemType();
		boolean useable = itemType.isUseable();
		leftText.setText("");
		centreText.setText("GUI_WINDOW_INVENTORY_COMMAND_TAKE");
		rightText.setText(itemType.getUseText());
		rightText.setColour(useable ? Color.white : Color.gray);

		for (int i = startItem; i < endItem; i++) {
			ItemStack itemStack = container.getItems().get(i);
			int yOffs = nearY + 64 + ((i - startItem) * 48);
			itemImages.add(new GUIItem(nearX, yOffs, 32, 32, itemStack.getItemID()));
			itemNames.add(new GUIText(nearX + 48, yOffs, GraphicsManager.defaultFont, Color.white, Item.getItem(itemStack.getItemID()).getName(), GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
			itemCounts.add(new GUIText(farX, yOffs, GraphicsManager.defaultFont, Color.white, "x " + itemStack.getQuantity(), GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		}

		comps.addAll(itemImages);
		comps.addAll(itemNames);
		comps.addAll(itemCounts);
	}

	public void setScreen(int screen) {
		this.screen = screen;
	}

	public void setItem(int item) {
		this.item = item;
	}
}
