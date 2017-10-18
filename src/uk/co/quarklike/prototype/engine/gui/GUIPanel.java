package uk.co.quarklike.prototype.engine.gui;

import uk.co.quarklike.prototype.engine.RenderEngine;

public class GUIPanel {
	private int x, y, width, height;

	public GUIPanel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void draw(RenderEngine renderEngine) {
		renderEngine.drawQuad(x, y, width, height, 0, 0, 1, 0, 1, 1, 0, 1, 0);
	}
}
