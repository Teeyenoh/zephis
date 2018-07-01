package uk.co.quarklike.zephis.src.map;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class MapManager {
	private HashMap<Integer, Map> _maps;

	public MapManager() {
		_maps = new HashMap<Integer, Map>();
	}

	public void addMap(int id, Map map) {
		_maps.put(id, map);
	}

	public Map getMap(int map) {
		return _maps.get(map);
	}

	public MapData loadMap(String path) {
		MapData data = null;

		try {
			InputStream in = new FileInputStream("res/map/" + path + ".qmap");
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			data = new MapData(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}
