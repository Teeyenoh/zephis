package uk.co.quarklike.zephis.src;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import org.newdawn.slick.util.ResourceLoader;

public class Map {
	private static HashMap<Short, String> maps = new HashMap<Short, String>();

	private static final int DATA_ID = 0; // short
	private static final int DATA_WIDTH = 2; // short
	private static final int DATA_HEIGHT = 4; // short
	private static final int DATA_TILES = 6; // int

	private static final byte DATA_TILE_TEXTURE_1 = 0; // byte
	private static final byte DATA_TILE_INDEX_1 = 1; // byte
	private static final byte DATA_TILE_TEXTURE_2 = 2; // byte
	private static final byte DATA_TILE_INDEX_2 = 3; // byte
	private static final byte DATA_TILE_TEXTURE_3 = 4; // byte
	private static final byte DATA_TILE_INDEX_3 = 5; // byte
	private static final byte DATA_TILE_COLLISION = 6; // byte

	private static final int DATA_TILE_LENGTH = 7;

	public static final byte DIRECTION_UP = 0;
	public static final byte DIRECTION_LEFT = 1;
	public static final byte DIRECTION_DOWN = 2;
	public static final byte DIRECTION_RIGHT = 3;

	public static final long PLAYER_ID = 1;

	private ByteBuffer _data;
	private HashMap<Long, Entity> _entities;

	public Map(ByteBuffer data) {
		_data = data;
		_entities = new HashMap<Long, Entity>();
	}

	private byte getTileData(short x, short y, byte offset) {
		return _data.get(DATA_TILES + ((DATA_TILE_LENGTH * ((y * this.getWidth()) + x) + offset)));
	}

	public byte getTileTexture(short x, short y, byte layer) {
		return getTileData(x, y, layer == 0 ? DATA_TILE_TEXTURE_1 : layer == 1 ? DATA_TILE_TEXTURE_2 : DATA_TILE_TEXTURE_3);
	}

	public byte getTileIndex(short x, short y, byte layer) {
		return getTileData(x, y, layer == 0 ? DATA_TILE_INDEX_1 : layer == 1 ? DATA_TILE_INDEX_2 : DATA_TILE_INDEX_3);
	}

	public boolean getCollision(short x, short y, byte direction) {
		String asString = Integer.toBinaryString(getTileData(x, y, DATA_TILE_COLLISION)).substring(4);
		return asString.toCharArray()[direction] == '1';
	}

	public void addEntity(Entity e) {
		_entities.put(e.getID(), e);
	}

	public long getNewID() {
		long id = Main.rand.nextLong();

		while (_entities.containsKey(id)) {
			id = Main.rand.nextLong();
		}

		return id;
	}

	public Entity getEntity(long id) {
		return _entities.get(id);
	}

	public Entity getPlayer() {
		return getEntity(PLAYER_ID);
	}

	public Collection<Entity> getEntities() {
		return _entities.values();
	}

	public short getMapID() {
		return _data.getShort(DATA_ID);
	}

	public short getWidth() {
		return _data.getShort(DATA_WIDTH);
	}

	public short getHeight() {
		return _data.getShort(DATA_HEIGHT);
	}

	public static void loadMapList(String path) {
		Properties list = new Properties();

		try {
			list.load(ResourceLoader.getResourceAsStream("res/maps/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object o : list.keySet()) {
			String s = (String) o;
			maps.put(Short.parseShort(s), list.getProperty(s));
		}
	}

	public static Map loadMap(short mapID) {
		Map map = null;

		try {
			InputStream stream = ResourceLoader.getResourceAsStream("res/maps/" + maps.get(mapID) + ".qmap");
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			ByteBuffer b = ByteBuffer.wrap(bytes);
			map = new Map(b);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load map: " + mapID + " - " + maps.get(mapID));
		}

		return map;
	}

	public static byte getDirectionalX(byte direction) {
		switch (direction) {
		case DIRECTION_LEFT:
			return -1;
		case DIRECTION_RIGHT:
			return 1;
		default:
			return 0;
		}
	}

	public static byte getDirectionalY(byte direction) {
		switch (direction) {
		case DIRECTION_UP:
			return -1;
		case DIRECTION_DOWN:
			return 1;
		default:
			return 0;
		}
	}
}
