package uk.co.quarklike.zephis.src.graphics;

public class MenuPositionGroup {
	private int _index;
	private int _max;
	private int _visible;
	private int _buffer;

	private int _top;

	public MenuPositionGroup(int max, int visible, int buffer) {
		_max = max;
		_visible = visible;
		_buffer = buffer;
	}

	public int getTop() {
		return _top;
	}

	public int getBottom() {
		return _top + _visible;
	}

	public boolean moveUp() {
		if (_index == 0) {
			return true;
		} else {
			_index = Math.max(0, _index - 1);
			if (_index - _top < _buffer) {
				_top = Math.max(0, _top - 1);
			}
			return false;
		}
	}

	public boolean moveDown() {
		if (_index == _max - 1) {
			return true;
		} else {
			_index = Math.min(_max - 1, _index + 1);
			if (getBottom() - _index <= _buffer) {
				_top = Math.min(_max - _visible, _top + 1);
			}
			return false;
		}
	}

	public int getIndex() {
		return _index;
	}

	public void setTop() {
		_index = 0;
	}

	public void setBottom() {
		_index = _max - 1;
	}
	
	public int getMax() {
		return _max;
	}
}
