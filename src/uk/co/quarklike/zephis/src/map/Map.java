package uk.co.quarklike.zephis.src.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import uk.co.quarklike.zephis.src.Zephis;
import uk.co.quarklike.zephis.src.map.entity.Entity;

public class Map {
	private static Comparator<Entity> _drawSort = new Comparator<Entity>() {
		@Override
		public int compare(Entity e1, Entity e2) {
			return e2.getPositionID() - e1.getPositionID();
		}
	};

	private MapManager _manager;
	private MapData _data;

	private HashMap<Long, Entity> _entities;
	private ArrayList<Long> _toRemove;

	public Map(MapManager manager, MapData data) {
		_manager = manager;
		_data = data;
		_entities = new HashMap<Long, Entity>();
		_toRemove = new ArrayList<Long>();
	}

	public void update() {
		for (long l : _toRemove) {
			_entities.remove(l);
		}

		_toRemove.clear();
	}

	public long registerEntity(Entity e) {
		long id = 0;
		while (_entities.containsKey(id)) {
			id = Zephis.instance.getRandom().nextLong();
		}
		_entities.put(id, e);
		return id;
	}

	public void removeEntity(long id) {
		_toRemove.add(id);
	}

	public short getTerrain(int x, int y) {
		if (isOutOfBounds(x, y)) {
			if (isCell()) {
				if (x < 0) {
					return getCellW().getTerrain(x - getWidth(), y);
				}

				if (x >= getWidth()) {
					return getCellE().getTerrain(x + getWidth(), y);
				}

				if (y < 0) {
					return getCellS().getTerrain(x, y - getHeight());
				}

				if (y >= getHeight()) {
					return getCellN().getTerrain(x, y + getHeight());
				}
			} else {
				return 0;
			}
		}

		return _data.getTerrain(x, y);
	}

	private boolean isCell() {
		return _data.isCell();
	}

	private Map getCellN() {
		return _manager.getMap(_data.getCellN());
	}

	private Map getCellW() {
		return _manager.getMap(_data.getCellW());
	}

	private Map getCellE() {
		return _manager.getMap(_data.getCellE());
	}

	private Map getCellS() {
		return _manager.getMap(_data.getCellS());
	}

	public boolean isBlocked(int x, int y) {
		return isOutOfBounds(x, y) || getEntity(x, y) != null || getTerrain(x, y) == 2;
	}

	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> output = new ArrayList<Entity>(_entities.values());
		output.sort(_drawSort);
		return output;
	}

	public Entity getEntity(long id) {
		return _entities.get(id);
	}

	public Entity getEntity(int x, int y) {
		for (Entity e : getEntities()) {
			if (e.getBody().getX() == x && e.getBody().getY() == y)
				return e;
		}

		return null;
	}

	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || x >= getWidth() || y < 0 || y >= getHeight();
	}

	public byte getWidth() {
		return _data.getWidth();
	}

	public byte getHeight() {
		return _data.getHeight();
	}

	public MapManager getMapManager() {
		return _manager;
	}

	public short getMapID() {
		return _data.getMapID();
	}

	public Teleporter getTeleporter(byte x, byte y) {
		for (int i = 0; i < 16; i++) {
			Teleporter tp = _data.getTP(i);
			if (tp != null) {
				if (tp.isAt(x, y)) {
					return tp;
				}
			}
		}

		return null;
	}
}
