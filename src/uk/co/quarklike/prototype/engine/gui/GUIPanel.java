package uk.co.quarklike.prototype.engine.gui;

import uk.co.quarklike.prototype.engine.RenderEngine;

public class GUIPanel extends GUI {
	private int x, y, width, height;

	public GUIPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(RenderEngine renderEngine) {
		renderEngine.drawQuad(x, y, width, height, 0, 0, 1, 0, 1, 1, 0, 1, 0);
	}
}
