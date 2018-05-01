package uk.co.quarklike.zephis.src.graphics;

public class MenuPosition {
	private MenuPositionGroup[] _groups;
	private int _group;

	public MenuPosition(MenuPositionGroup[] groups) {
		_groups = groups;
	}

	public void moveUp() {
		if (_groups[_group].moveUp() && _group > 0) {
			_group -= 1;
			_groups[_group].setBottom();
		}
	}

	public void moveDown() {
		if (_groups[_group].moveDown() && _group < _groups.length - 1) {
			_group += 1;
			_groups[_group].setTop();
		}
	}

	public int getGroupIndex() {
		return _group;
	}

	public MenuPositionGroup getGroup(int group) {
		return _groups[group];
	}

	public MenuPositionGroup getGroup() {
		return getGroup(_group);
	}
}
