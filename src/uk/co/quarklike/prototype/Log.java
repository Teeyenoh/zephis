package uk.co.quarklike.prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Calendar;

public class Log {
	private static Calendar c;
	private static NumberFormat nf;

	private static PrintStream log;

	public static void initLog() {
		File logsF = new File("logs/");
		logsF.mkdir();

		try {
			log = new PrintStream("logs/log-" + getDateTimeSimple() + ".log");
		} catch (FileNotFoundException e) {
			System.exit(0);
		}
	}

	public static void deinitLog() {
		log.close();
	}

	public static void info(Object x) {
		String out = getDateTime() + " [Thread: " + Thread.currentThread().getName() + "] [" + getManagerName() + "] INFO: " + x;

		System.out.println(out);
		log.println(out);
	}

	public static void warn(Object x) {
		String out = getDateTime() + " [Thread: " + Thread.currentThread().getName() + "] [" + getManagerName() + "] WARN: " + x;

		System.out.println(out);
		log.println(out);
	}

	public static void warn(Object x, Exception e) {
		String out = getDateTime() + " [Thread: " + Thread.currentThread().getName() + "] [" + getManagerName() + "] WARN: " + x;

		System.out.println(out);
		log.println(out);

		if (Main.DEBUG)
			e.printStackTrace(log);
	}

	public static void err(Object x) {
		String out = getDateTime() + " [Thread: " + Thread.currentThread().getName() + "] [" + getManagerName() + "] ERR: " + x;

		System.out.println(out);
		log.println(out);

		Main.instance.stop();
	}

	public static void err(Object x, Exception e) {
		String out = getDateTime() + " [Thread: " + Thread.currentThread().getName() + "] [" + getManagerName() + "] ERR: " + x;

		System.out.println(out);
		log.println(out);

		if (Main.DEBUG)
			e.printStackTrace();
		e.printStackTrace(log);

		Main.instance.stop();
	}

	public static void debug(Object x) {
		String out = getDateTime() + " [Thread: " + Thread.currentThread().getName() + "] [" + getManagerName() + "] DEBUG: " + x;

		System.out.println(out);

		if (Main.DEBUG)
			log.println(out);
	}

	private static String getManagerName() {
		return Main.instance.getCurrentManager() == null ? "None" : Main.instance.getCurrentManager().getName();
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

	private static String getDateTimeSimple() {
		if (nf == null) {
			nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMinimumIntegerDigits(2);
		}
		c = Calendar.getInstance();

		String dateTime = "";

		dateTime += nf.format(c.get(Calendar.YEAR));
		dateTime += nf.format(c.get(Calendar.MONTH) + 1);
		dateTime += nf.format(c.get(Calendar.DAY_OF_MONTH));
		dateTime += nf.format(c.get(Calendar.HOUR_OF_DAY));
		dateTime += nf.format(c.get(Calendar.MINUTE));
		dateTime += nf.format(c.get(Calendar.SECOND));

		return dateTime;
	}
}
