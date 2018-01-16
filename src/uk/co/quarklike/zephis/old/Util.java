package uk.co.quarklike.zephis.old;

public class Util {
	public static int clamp(int n, int min, int max) {
		if (max < min)
			return n;
		
		return n < min ? min : n > max ? max : n;
	}

	public static int wrap(int n, int min, int max) {
		if (max < min)
			return n;
		
		while (n < min) {
			n += (max - min + 1);
		}

		while (n > max) {
			n -= (max - min + 1);
		}

		return n;
	}

	public static int wrapIf(int n, int min, int max, boolean b, int def) {
		if (!b)
			return def;
		return wrap(n, min, max);
	}
}
