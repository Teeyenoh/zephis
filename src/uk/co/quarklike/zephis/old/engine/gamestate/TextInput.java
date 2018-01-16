package uk.co.quarklike.zephis.old.engine.gamestate;

import uk.co.quarklike.zephis.old.Log;

public class TextInput {
	public static String addChar(String s, int index, char c) {

		if (Character.isAlphabetic(c)) {
			return s.substring(0, index) + c + s.substring(index);
		} else if (Character.isSpaceChar(c)) {
			return s.substring(0, index) + ' ' + s.substring(index);
		} else if (c == '\b') {
			if (index != 0)
				return s.substring(0, index - 1) + s.substring(index);
		}

		return s;
	}

	public static boolean isChar(char c) {
		return Character.isAlphabetic(c) || Character.isSpaceChar(c);
	}

	public static boolean isBack(char c) {
		Log.info(c == '\b');
		return c == '\b';
	}
}
