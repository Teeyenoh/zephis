package uk.co.quarklike.prototype.engine.gui;

import org.newdawn.slick.Color;

import uk.co.quarklike.prototype.engine.GraphicsManager;
import uk.co.quarklike.prototype.engine.RenderEngine;

public class GUIText extends GUI {
	public GUIText(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(RenderEngine renderEngine) {
		renderEngine.drawText(x + 1, y + 1, GraphicsManager.defaultFont, "Test", Color.black);
		renderEngine.drawText(x, y, GraphicsManager.defaultFont, "Test", Color.white);
	}
}
