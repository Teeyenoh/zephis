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

	private int _width, _height;
	private int[][] _terrain;
	private HashMap<Long, Entity> _entities;
	private ArrayList<Long> _toRemove;

	public Map(int width, int height) {
		_width = width;
		_height = height;
		_terrain = new int[width][height];
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

	public int getTerrain(int x, int y) {
		if (isOutOfBounds(x, y))
			return 0;
		return 1;//_terrain[x][y];
	}

	public boolean isBlocked(int x, int y) {
		return isOutOfBounds(x, y) || getEntity(x, y) != null;
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
			if (e.getX() == x && e.getY() == y)
				return e;
		}

		return null;
	}

	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || x >= _width || y < 0 || y >= _height;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}
}
