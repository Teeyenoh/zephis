package uk.co.quarklike.prototype.engine.gui;

import org.apache.commons.lang.WordUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import uk.co.quarklike.prototype.Language;
import uk.co.quarklike.prototype.engine.RenderEngine;

public class GUIText extends GUIComponent {
	private UnicodeFont font;
	private Color color;
	private String text;
	private int alignment;
	private int textCase;

	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_CENTRE = 2;
	public static final int ALIGN_RIGHT = 3;

	public static final int CASE_UPPER = 1;
	public static final int CASE_TITLE = 2;
	public static final int CASE_LOWER = 3;
	public static final int CASE_DEFAULT = 4;

	public GUIText(int x, int y, UnicodeFont font, Color color, String text, int alignment, int textCase) {
		super(x, y, 0, 0);
		this.font = font;
		this.color = color;
		this.text = text;
		this.alignment = alignment;
		this.textCase = textCase;
	}

	@Override
	public void draw(RenderEngine renderEngine) {
		String text = Language.get(this.text);
		switch (this.textCase) {
		case CASE_UPPER:
			text = WordUtils.capitalizeFully(text);
			break;
		case CASE_TITLE:
			text = WordUtils.capitalize(text);
			break;
		case CASE_LOWER:
			text = WordUtils.uncapitalize(text);
			break;
		case CASE_DEFAULT:
			break;
		}

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

		renderEngine.drawText(x, y, font, text, color);
	}
}
