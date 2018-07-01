package uk.co.quarklike.zephis.src.map;

import java.nio.ByteBuffer;

public class MapData {
	private static final int SIZE_BYTE = Byte.SIZE / Byte.SIZE;
	private static final int SIZE_SHORT = Short.SIZE / Byte.SIZE;
	private static final int SIZE_INT = Integer.SIZE / Byte.SIZE;
	private static final int SIZE_LONG = Long.SIZE / Byte.SIZE;
	private static final int SIZE_FLOAT = Float.SIZE / Byte.SIZE;
	private static final int SIZE_DOUBLE = Double.SIZE / Byte.SIZE;
	private static final int SIZE_BOOL = Byte.SIZE / Byte.SIZE;

	public static int MAP_ID;
	public static int MAP_IS_CELL;
	public static int MAP_CELL_N;
	public static int MAP_CELL_W;
	public static int MAP_CELL_E;
	public static int MAP_CELL_S;
	public static int MAP_WIDTH;
	public static int MAP_HEIGHT;

	public static int[] MAP_TP_ON = new int[16];
	public static int[] MAP_TP_FX = new int[16];
	public static int[] MAP_TP_FY = new int[16];
	public static int[] MAP_TP_TX = new int[16];
	public static int[] MAP_TP_TY = new int[16];
	public static int[] MAP_TP_TID = new int[16];

	public static int MAP_TERRAIN_START;
	public static int MAP_TERRAIN_SIZE = SIZE_SHORT;

	public static int DATA_SIZE;

	private static int offs;

	public static void initMapData() {
		MAP_ID = addData(SIZE_SHORT);
		MAP_IS_CELL = addData(SIZE_BOOL);
		MAP_CELL_N = addData(SIZE_SHORT);
		MAP_CELL_W = addData(SIZE_SHORT);
		MAP_CELL_E = addData(SIZE_SHORT);
		MAP_CELL_S = addData(SIZE_SHORT);
		MAP_WIDTH = addData(SIZE_BYTE);
		MAP_HEIGHT = addData(SIZE_BYTE);

		for (int i = 0; i < 16; i++) {
			MAP_TP_ON[i] = addData(SIZE_BOOL);
			MAP_TP_FX[i] = addData(SIZE_BYTE);
			MAP_TP_FY[i] = addData(SIZE_BYTE);
			MAP_TP_TX[i] = addData(SIZE_BYTE);
			MAP_TP_TY[i] = addData(SIZE_BYTE);
			MAP_TP_TID[i] = addData(SIZE_SHORT);
		}

		MAP_TERRAIN_START = addData(SIZE_SHORT);

		DATA_SIZE = offs;
	}

	private static int addData(int size) {
		int out = offs;
		offs += size;
		return out;
	}

	private ByteBuffer _data;

	public MapData(ByteBuffer data) {
		_data = data;
	}

	public short getMapID() {
		return getShort(MAP_ID);
	}

	public boolean isCell() {
		return getBool(MAP_IS_CELL);
	}

	public short getCellN() {
		return getShort(MAP_CELL_N);
	}

	public short getCellW() {
		return getShort(MAP_CELL_W);
	}

	public short getCellE() {
		return getShort(MAP_CELL_E);
	}

	public short getCellS() {
		return getShort(MAP_CELL_S);
	}

	public byte getWidth() {
		return getByte(MAP_WIDTH);
	}

	public byte getHeight() {
		return getByte(MAP_HEIGHT);
	}

	public Teleporter getTP(int index) {
		if (getBool(MAP_TP_ON[index])) {
			return null;
		}

		byte fromX = getByte(MAP_TP_FX[index]);
		byte fromY = getByte(MAP_TP_FY[index]);
		byte toX = getByte(MAP_TP_TX[index]);
		byte toY = getByte(MAP_TP_TY[index]);
		short toID = getShort(MAP_TP_TID[index]);

		return new Teleporter(toID, fromX, fromY, toX, toY);
	}

	public short getTerrain(int x, int y) {
		int i = (y * getWidth()) + x;
		return getShort(MAP_TERRAIN_START + (i * MAP_TERRAIN_SIZE));
	}

	private byte getByte(int offs) {
		return _data.get(offs);
	}

	private boolean getBool(int offs) {
		return getByte(offs) == 0x01;
	}

	private short getShort(int offs) {
		return _data.getShort(offs);
	}

	private int getInt(int offs) {
		return _data.getInt(offs);
	}

	private long getLong(int offs) {
		return _data.getLong(offs);
	}
}
