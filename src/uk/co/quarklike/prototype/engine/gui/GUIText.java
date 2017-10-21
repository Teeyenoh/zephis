package uk.co.quarklike.prototype.engine.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import uk.co.quarklike.prototype.engine.RenderEngine;

public class GUIText extends GUIComponent {
	private UnicodeFont font;
	private String text;
	private int alignment;

	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_CENTRE = 2;
	public static final int ALIGN_RIGHT = 3;

	public GUIText(int x, int y, UnicodeFont font, String text, int alignment) {
		super(x, y, 0, 0);
		this.font = font;
		this.text = text;
		this.alignment = alignment;
	}

	@Override
	public void draw(RenderEngine renderEngine) {
		int x = this.x;
		int y = this.y - (font.getLineHeight() / 2);

		switch (this.alignment) {
		case ALIGN_LEFT:
			break;
		case ALIGN_CENTRE:
			x -= font.getWidth(text) / 2;
			break;
		case ALIGN_RIGHT:
			x -= font.getWidth(text);
			break;
		}

		renderEngine.drawText(x, y, font, text, Color.white);
	}
}
