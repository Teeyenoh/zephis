package uk.co.quarklike.prototype.engine.gui.windows;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.GUIImage;

public class GUIInventory extends GUIWindow {
	public GUIInventory(ContentHub contentHub) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
	}
}
