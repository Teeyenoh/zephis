package uk.co.quarklike.prototype.engine.gui;

import uk.co.quarklike.prototype.engine.RenderEngine;

public class GUIImage extends GUIComponent {
	private String texture;

	public GUIImage(int x, int y, int width, int height, String texture) {
		super(x, y, width, height);
		this.texture = texture;
	}

	@Override
	public void draw(RenderEngine renderEngine) {
		renderEngine.drawQuad(x, y, width, height, texture);
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setTexture(String t) {
		this.texture = t;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
