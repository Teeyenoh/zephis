package uk.co.quarklike.zephis.src.map;

import uk.co.quarklike.zephis.src.map.entity.Entity;

public class Teleporter {
	private short _mapID;
	private byte _fromX, _fromY;
	private byte _toX, _toY;

	public Teleporter(short map, byte fromX, byte fromY, byte toX, byte toY) {
		_mapID = map;
		_fromX = fromX;
		_fromY = fromY;
		_toX = toX;
		_toY = toY;
	}

	public void teleport(Entity e) {
		e.setMap(_mapID, _toX, _toY);
	}

	public boolean isAt(int x, int y) {
		return x == _fromX && y == _fromY;
	}
}
