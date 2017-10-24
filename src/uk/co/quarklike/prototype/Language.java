package uk.co.quarklike.prototype;

import java.io.IOException;
import java.util.Properties;

import org.newdawn.slick.util.ResourceLoader;

public class Language {
	private static Properties lang;

	public static void initLanguage(String language) {
		try {
			lang = new Properties();
			lang.load(ResourceLoader.getResourceAsStream("res/lang/" + language + ".properties"));
		} catch (IOException e) {
			Log.err("Failed to load language file", e);
		}
	}

	public static String get(String t) {
		return (String) lang.getOrDefault(t, t);
	}
}
