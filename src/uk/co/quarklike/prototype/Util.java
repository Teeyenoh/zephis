package uk.co.quarklike.prototype;

public class Util {
	public static int clamp(int n, int min, int max) {
		return n < min ? min : n > max ? max : n;
	}

	public static int wrap(int n, int min, int max) {
		while (n < min) {
			n += (max - min + 1);
		}

		while (n > max) {
			n -= (max - min + 1);
		}

		return n;
	}
}
