package uk.co.quarklike.prototype.map.entity;

public class Race {
	private static final Race[] races = new Race[26];

	private int raceID;
	private String name;

	public Race(int id, String name) {
		this.raceID = id;
		this.name = name;
		races[id - 1] = this;
	}

	public int getID() {
		return raceID;
	}

	public String getName() {
		return name;
	}

	public static Race getRace(int race) {
		return races[race - 1];
	}

	public static int getRaceCount() {
		return races.length;
	}
}
