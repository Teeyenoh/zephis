package uk.co.quarklike.prototype.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.util.ResourceLoader;

import uk.co.quarklike.prototype.Log;

public class MapData {
	public static final int LAYERS = 24;

	private int mapID;
	private int rawWidth, rawHeight;
	private int width, height;
	private int[][][] values;

	public MapData(int id, int width, int height) {
		this.mapID = id;
		this.width = width;
		this.height = height;
		this.values = new int[width][height][LAYERS];
	}

	public int getValue(int x, int y, int layer) {
		if (isOutOfBounds(x, y, layer))
			return 0;
		return values[x][y][layer];
	}

	public void setValue(int x, int y, int layer, int value) {
		if (isOutOfBounds(x, y, layer))
			return;
		values[x][y][layer] = value;
	}

	private boolean isOutOfBounds(int x, int y, int layer) {
		return x < 0 || x >= width || y < 0 || y >= height || layer < 0 || layer >= LAYERS;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private static final int HEADER = 24;
	private static final int TILE = 24;

	public static MapData fromFile(String fileName) {
		MapData output = null;

		try {
			InputStream stream = ResourceLoader.getResourceAsStream("res/maps/" + fileName);

			byte[] data = new byte[stream.available()];
			stream.read(data);

			int width = (int) Math.pow(2, data[1]);
			int height = (int) Math.pow(2, data[2]);
			output = new MapData(data[0], width, height);
			output.rawWidth = data[1];
			output.rawHeight = data[2];

			int tiles = (data.length - HEADER) / TILE;
			for (int i = 0; i < tiles; i++) {
				int tileX = i % width;
				int tileY = i / width;

				for (int j = 0; j < TILE; j++) {
					output.setValue(tileX, tileY, j, data[HEADER + (i * TILE) + j]);
				}
			}
		} catch (IOException e) {
			Log.err("Failed to read map file: " + fileName, e);
		}

		return output;
	}

	public void saveToFile(String fileName) {
		try {
			File f = new File("res/maps/" + fileName);
			if (f.exists()) {
				f.delete();
			} else {
				f.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(f);

			out.write(mapID);
			out.write(rawWidth);
			out.write(rawHeight);

			for (int i = 3; i < HEADER; i++) {
				out.write(0);
			}

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					for (int k = 0; k < LAYERS; k++) {
						out.write(values[i][j][k]);
					}
				}
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getMapID() {
		return mapID;
	}
}
