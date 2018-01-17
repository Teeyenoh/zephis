package uk.co.quarklike.zephis.src;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Properties;

import org.newdawn.slick.util.ResourceLoader;

public class Map {
	public static HashMap<Short, String> maps = new HashMap<Short, String>();

	private static final int DATA_ID = 0;
	private static final int DATA_WIDTH = 2;
	private static final int DATA_HEIGHT = 4;
	private static final int DATA_TILES = 6;

	private static final byte DATA_TILE_TEXTURE_1 = 0;
	private static final byte DATA_TILE_INDEX_1 = 1;
	private static final byte DATA_TILE_TEXTURE_2 = 2;
	private static final byte DATA_TILE_INDEX_2 = 3;
	private static final byte DATA_TILE_TEXTURE_3 = 4;
	private static final byte DATA_TILE_INDEX_3 = 5;
	private static final byte DATA_TILE_COLLISION = 6;

	private static final int DATA_TILE_LENGTH = 7;

	private ByteBuffer _data;

	public Map(ByteBuffer data) {
		this._data = data;
	}

	private byte getTileData(short x, short y, byte offset) {
		return _data.get(DATA_TILES + ((DATA_TILE_LENGTH * (y * this.getWidth())) + x) + offset);
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

	public short getMapID() {
		return this._data.getShort(DATA_ID);
	}

	public short getWidth() {
		return this._data.getShort(DATA_WIDTH);
	}

	public short getHeight() {
		return this._data.getShort(DATA_HEIGHT);
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
			System.out.println("Failed to load map: " + mapID);
		}

		return map;
	}
}
