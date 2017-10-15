package uk.co.quarklike.prototype.map;

import java.util.Collection;
import java.util.HashMap;

import uk.co.quarklike.prototype.Main;
import uk.co.quarklike.prototype.engine.ResourceManager;

public class Map {
	public static final int TILE_LAYERS = 4;

	public static final byte NORTH = 0;
	public static final byte EAST = 1;
	public static final byte SOUTH = 2;
	public static final byte WEST = 3;

	private MapData data;
	private HashMap<Long, Entity> entities;

	public Map(MapData data) {
		this.data = data;
		entities = new HashMap<Long, Entity>();
	}

	public int getTile(int x, int y, int layer) {
		return data.getValue(x, y, layer);
	}

	public int getTexture(int x, int y, int layer) {
		return data.getValue(x, y, layer + TILE_LAYERS);
	}

	public boolean getCollision(int x, int y, byte direction) {
		String value = Integer.toBinaryString(data.getValue(x, y, TILE_LAYERS * 2));

		int length = value.length();

		for (int i = 0; i < 4 - length; i++) {
			value = "0" + value;
		}

		switch (direction) {
		case NORTH:
			return Integer.parseInt("" + value.charAt(value.length() - 1)) == 1;
		case EAST:
			return Integer.parseInt("" + value.charAt(value.length() - 2)) == 1;
		case SOUTH:
			return Integer.parseInt("" + value.charAt(value.length() - 3)) == 1;
		case WEST:
			return Integer.parseInt("" + value.charAt(value.length() - 4)) == 1;
		default:
			return false;
		}
	}

	public void setTile(int x, int y, int layer, int tile) {
		data.setValue(x, y, layer, tile);
	}

	public void setTexture(int x, int y, int layer, int texture) {
		data.setValue(x, y, layer + TILE_LAYERS, texture);
	}

	public void setCollision(int x, int y, byte direction, boolean blocked) {
		int v = data.getValue(x, y, TILE_LAYERS * 2);
		switch (direction) {
		case NORTH:
			v += blocked ? 1 : getCollision(x, y, NORTH) ? -1 : 0;
			break;
		case EAST:
			v += blocked ? 2 : getCollision(x, y, EAST) ? -2 : 0;
			break;
		case SOUTH:
			v += blocked ? 4 : getCollision(x, y, SOUTH) ? -4 : 0;
			break;
		case WEST:
			v += blocked ? 8 : getCollision(x, y, WEST) ? -8 : 0;
			break;
		}

		data.setValue(x, y, TILE_LAYERS * 2, v);
	}

	public boolean isBlocked(int x, int y, byte direction) {
		if (isOutOfBounds(x + getDirectionalX(direction), y + getDirectionalY(direction), 0))
			return true;
		return getCollision(x, y, direction);
	}

	public Collection<Entity> getEntities() {
		return entities.values();
	}

	public long addEntity(Entity e) {
		if (entities.containsValue(e))
			return e.getID();
		long id = 0;
		while (id == 0 || entities.containsKey(id)) {
			id = Main.instance.getRand().nextLong();
		}
		entities.put(id, e);
		return id;
	}

	private boolean isOutOfBounds(int x, int y, int layer) {
		return x < 0 || x >= getWidth() || y < 0 || y >= getHeight() || layer < 0 || layer >= MapData.LAYERS;
	}

	public void requestTextures(ResourceManager resources) {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				for (int k = 0; k < TILE_LAYERS; k++) {
					resources.requestTexture(getTexture(i, j, k));
				}
			}
		}
	}

	public int getWidth() {
		return data.getWidth();
	}

	public int getHeight() {
		return data.getHeight();
	}

	public static byte getDirectionalX(byte direction) {
		switch (direction) {
		case NORTH:
			return 0;
		case EAST:
			return 1;
		case SOUTH:
			return 0;
		case WEST:
			return -1;
		default:
			return 0;
		}
	}

	public static byte getDirectionalY(byte direction) {
		switch (direction) {
		case NORTH:
			return -1;
		case EAST:
			return 0;
		case SOUTH:
			return 1;
		case WEST:
			return 0;
		default:
			return 0;
		}
	}
	
	public void save(String fileName) {
		data.saveToFile(fileName);
	}
}
