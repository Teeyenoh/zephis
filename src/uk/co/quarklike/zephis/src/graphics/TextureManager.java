package uk.co.quarklike.zephis.src.graphics;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureManager {
	private HashMap<String, Integer> _textures;

	public TextureManager() {
		_textures = new HashMap<String, Integer>();
	}

	public int getTexture(String path) {
		if (_textures.containsKey(path))
			return _textures.get(path);
		return loadTexture(path);
	}

	public int loadTexture(String path) {
		int textureID = 0;

		try {
			textureID = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/textures/" + path + ".png"), GL11.GL_NEAREST).getTextureID();
		} catch (IOException e) {
			e.printStackTrace();
		}

		_textures.put(path, textureID);

		return textureID;
	}
}
