package uk.co.quarklike.zephis.src;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translation {
	private static Locale _locale;
	private static ResourceBundle _translations;

	public static void loadLocal(String language, String country, String path) {
		_locale = new Locale(language, country);
		_translations = ResourceBundle.getBundle("uk.co.quarklike.zephis.lang." + path, _locale);
	}

	public static String translate(String text) {
		return _translations.getString(text);
	}
}
