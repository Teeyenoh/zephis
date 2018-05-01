package uk.co.quarklike.zephis.src.map;

import uk.co.quarklike.zephis.src.Zephis;

public class Terrain {
	private static Terrain[] terrain = new Terrain[256];

	private int _terrainID;

	private String _name;
	private int _textureID;

	public static final Terrain grass = new Terrain(1, "grass", "grass");
	public static final Terrain dirt = new Terrain(2, "dirt", "dirt");

	public Terrain(int terrainID, String name, String texturePath) {
		_terrainID = terrainID;
		terrain[terrainID] = this;
		_name = name;
		_textureID = Zephis.instance.getTextureManager().getTexture(texturePath);
	}

	public int getTextureID() {
		return _textureID;
	}

	public static Terrain getTerrain(int terrainID) {
		return terrain[terrainID];
	}
}
