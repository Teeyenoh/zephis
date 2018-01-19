package uk.co.quarklike.zephis.src;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.newdawn.slick.util.ResourceLoader;

public class SaveManager {
	private static final int DATA_CURRENT_MAP_ID = 0; // short
	private static final int DATA_LAST_MAP_ID = 2; // short
	private static final int DATA_CURRENT_MAP_ENTITY_COUNT = 4; // short
	private static final int DATA_LAST_MAP_ENTITY_COUNT = 6; // short
	private static final int DATA_ENTITY_START = 8;

	public static Map[] loadSave(String path) {
		ByteBuffer b = null;

		Map map = null;
		Map lastMap = null;

		try {
			InputStream stream = ResourceLoader.getResourceAsStream("save/" + path + ".qsav");
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			b = ByteBuffer.wrap(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load save: " + path);
		}

		short mapID = b.getShort(DATA_CURRENT_MAP_ID);
		short lastMapID = b.getShort(DATA_LAST_MAP_ID);
		short entityCount = b.getShort(DATA_CURRENT_MAP_ENTITY_COUNT);
		short lastEntityCount = b.getShort(DATA_LAST_MAP_ENTITY_COUNT);

		map = Map.loadMap(mapID);
		lastMap = lastMapID == -1 ? null : Map.loadMap(lastMapID);

		for (short i = 0; i < entityCount; i++) {
			byte[] bytes = new byte[Entity.DATA_LENGTH];
			b.position(DATA_ENTITY_START + (i * Entity.DATA_LENGTH));
			b.get(bytes, 0, Entity.DATA_LENGTH);
			ByteBuffer data = ByteBuffer.wrap(bytes);
			new Entity(data, map);
		}

		for (short i = 0; i < lastEntityCount; i++) {
			byte[] bytes = new byte[Entity.DATA_LENGTH];
			b.position(DATA_ENTITY_START + (entityCount * Entity.DATA_LENGTH) + (i * Entity.DATA_LENGTH));
			b.get(bytes, 0, Entity.DATA_LENGTH);
			ByteBuffer data = ByteBuffer.wrap(bytes);
			new Entity(data, lastMap);
		}

		return new Map[] { map, lastMap };
	}
}
