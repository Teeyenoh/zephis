package uk.co.quarklike.zephis.src.graphics;

import java.awt.Color;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontManager {
	private HashMap<String, UnicodeFont> _fonts;

	public FontManager() {
		_fonts = new HashMap<String, UnicodeFont>();
	}

	public UnicodeFont getFont(String path) {
		if (_fonts.containsKey(path))
			return _fonts.get(path);
		return loadFont(path);
	}

	public UnicodeFont loadFont(String path) {
		UnicodeFont font = null;

		try {
			font = new UnicodeFont("res/fonts/" + path + ".ttf", 16, false, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect(Color.WHITE));
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		_fonts.put(path, font);

		return font;
	}
}
