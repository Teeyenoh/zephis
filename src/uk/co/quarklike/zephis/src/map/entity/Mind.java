package uk.co.quarklike.zephis.src.map.entity;

public class Mind {
	private Entity _parent;

	private Skills _skills;

	public Mind(Entity parent) {
		_parent = parent;

		_skills = new Skills(new int[] { 10, 10, 10, 10, 10, 10 }, new int[] { 32, 16, 44, 27, 11, 31, 73, 19, 19, 80, 43, 3, 64, 9, 80, 16, 35, 18, 22, 2, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 39, 41, 43, 47, 53, 57, 59, 61, 67, 71, 73, 79, 83, 87, 89 });
	}

	public boolean update() {
		return false;
	}

	public Skills getSkills() {
		return _skills;
	}
}
