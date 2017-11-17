package uk.co.quarklike.prototype.map.entity;

public class CharClass {
	private static final CharClass[] classes = new CharClass[5];

	private int classID;
	private String className;

	private int hitDie, manaDie, stamDie;

	public CharClass(int id, String name) {
		this.classID = id;
		this.className = name;
		classes[id - 1] = this;
	}

	public int getID() {
		return classID;
	}

	public String getName() {
		return className;
	}

	public static CharClass getCharClass(int charClass) {
		return classes[charClass - 1];
	}

	public static int getClassCount() {
		return classes.length;
	}
}
