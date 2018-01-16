package uk.co.quarklike.zephis.old.engine.gui;

import uk.co.quarklike.zephis.old.engine.RenderEngine;

public abstract class GUIComponent {
	protected int x, y, width, height;

	public GUIComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void draw(RenderEngine renderEngine);
}
