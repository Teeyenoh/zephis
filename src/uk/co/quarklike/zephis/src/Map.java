package uk.co.quarklike.zephis.src;

import java.nio.ByteBuffer;

public class Map {
	private static final int DATA_ID = 0;
	private static final int DATA_WIDTH = 2;
	private static final int DATA_HEIGHT = 4;
	private static final int DATA_TILES = 6;

	private static final int DATA_TILE_TEXTURE_1 = 0;
	private static final int DATA_TILE_INDEX_1 = 1;
	private static final int DATA_TILE_TEXTURE_2 = 2;
	private static final int DATA_TILE_INDEX_2 = 3;
	private static final int DATA_TILE_TEXTURE_3 = 4;
	private static final int DATA_TILE_INDEX_3 = 5;
	private static final int DATA_TILE_COLLISION = 6;

	private static final int DATA_TILE_LENGTH = 7;

	private ByteBuffer _data;

	public Map(ByteBuffer data) {
		this._data = data;
	}

	public byte getTileTexture(short x, short y, byte layer) {
		return _data.get(((DATA_TILE_LENGTH * (y * this.getWidth())) + x) + (layer == 0 ? DATA_TILE_TEXTURE_1 : layer == 1 ? DATA_TILE_TEXTURE_2 : DATA_TILE_TEXTURE_3));
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
}
