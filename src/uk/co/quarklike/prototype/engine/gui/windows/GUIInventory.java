package uk.co.quarklike.prototype.engine.gui.windows;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.GraphicsManager;
import uk.co.quarklike.prototype.engine.gui.GUIImage;
import uk.co.quarklike.prototype.engine.gui.GUIItem;
import uk.co.quarklike.prototype.engine.gui.GUIText;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.ItemStack;

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

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
		comps.add(new GUIText(x, y - (height / 2) + 32, GraphicsManager.titleFont, "Inventory", GUIText.ALIGN_CENTRE));
		comps.add(selection = new GUIImage(x, y, width, 64, "gui/selection.png"));

		int yOffs = y - (height / 2) + 32 + 48;
		int xOffs = x - (width / 2) + 48;
		int farX = x + (width / 2) - 48;

		for (ItemStack i : player.getInventory().getItems()) {
			comps.add(new GUIItem(xOffs, yOffs, 32, 32, i.getItemID()));
			comps.add(new GUIText(xOffs + 48, yOffs, GraphicsManager.defaultFont, Item.getItem(i.getItemID()).getName(), GUIText.ALIGN_LEFT));
			comps.add(new GUIText(farX, yOffs, GraphicsManager.defaultFont, "x " + i.getQuantity(), GUIText.ALIGN_RIGHT));
			yOffs += 48;
		}

		setItem(item);
	}

	public void setItem(int item) {
		this.item = item;
		selection.setY((y - (height / 2) + 32 + 48) + (item * 48));
	}
}
