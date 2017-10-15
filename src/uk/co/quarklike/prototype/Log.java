package uk.co.quarklike.prototype;

import java.text.NumberFormat;
import java.util.Calendar;

public class Log {
	private static Calendar c;
	private static NumberFormat nf;

	public static void info(Object x) {
		System.out.println(getDateTime() + " INFO: " + x);
	}

	public static void warning(Object x, Exception e) {
		System.out.println(getDateTime() + " WARNING: " + Main.instance.getCurrentManager().getName() + ": " + x);
		e.printStackTrace();
	}

	public static void error(Object x, Exception e) {
		System.out.println(getDateTime() + " ERROR: " + Main.instance.getCurrentManager().getName() + ": " + x);
		e.printStackTrace();
		Main.instance.stop();
	}

	public static void debug(Object x) {
		if (Main.DEBUG)
			System.out.println(getDateTime() + " DEBUG: " + x);
	}

	private static String getDateTime() {
		if (nf == null) {
			nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMinimumIntegerDigits(2);
		}
		c = Calendar.getInstance();

		String dateTime = "[";

		dateTime += nf.format(c.get(Calendar.YEAR)) + "/";
		dateTime += nf.format(c.get(Calendar.MONTH) + 1) + "/";
		dateTime += nf.format(c.get(Calendar.DAY_OF_MONTH)) + " ";
		dateTime += nf.format(c.get(Calendar.HOUR_OF_DAY)) + ":";
		dateTime += nf.format(c.get(Calendar.MINUTE)) + ":";
		dateTime += nf.format(c.get(Calendar.SECOND)) + "]";

		return dateTime;
	}
}
