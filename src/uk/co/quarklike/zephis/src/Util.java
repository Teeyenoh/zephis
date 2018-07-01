package uk.co.quarklike.zephis.src;

import static uk.co.quarklike.zephis.src.Const.*;

public class Util {
	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static int max(int a, int b) {
		return a < b ? b : a;
	}

	public static int clamp(int a, int min, int max) {
		return max(min(a, max), min);
	}

	public static int wrap(int a, int min, int max) {
		return ((a - min) % (max - min)) + min;
	}

	public static final int getXDirection(byte dir) {
		byte comp = (byte) (dir >> 4);
		return (byte) (comp == 0x0 ? -1 : comp == 0x2 ? 1 : 0);
	}

	public static final int getYDirection(byte dir) {
		byte comp = (byte) (dir - ((dir >> 4) << 4));
		return (byte) (comp == 0x0 ? -1 : comp == 0x2 ? 1 : 0);
	}

	public static final String getDirectionName(byte dir) {
		switch (dir) {
		case DIR_NONE:
			return "none";
		case DIR_FORWARD:
			return "up";
		case DIR_RIGHT:
			return "right";
		case DIR_BACKWARD:
			return "down";
		case DIR_LEFT:
			return "left";
		default:
			return "none";
		}
	}
}
