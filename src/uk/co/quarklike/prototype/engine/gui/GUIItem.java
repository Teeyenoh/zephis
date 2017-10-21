package uk.co.quarklike.prototype.engine.gui;

import uk.co.quarklike.prototype.engine.RenderEngine;
import uk.co.quarklike.prototype.map.item.Item;

public class GUIItem extends GUIComponent {
	private int item;

	public GUIItem(int x, int y, int width, int height, int item) {
		super(x, y, width, height);
		this.item = item;
	}

	@Override
	public void draw(RenderEngine renderEngine) {
		renderEngine.drawQuad(x, y, width, height, Item.getItem(item).getTextureSlot(), Item.getItem(item).getTexture());
	}
}
