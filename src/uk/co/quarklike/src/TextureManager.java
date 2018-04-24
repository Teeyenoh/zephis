package uk.co.quarklike.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

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
		ByteBuffer buf = null;
		int width = 0;
		int height = 0;

		int textureID = 0;

		try {
			InputStream in = new FileInputStream("res/textures/" + path + ".png");

			try {

				PNGDecoder decoder = new PNGDecoder(in);

				width = decoder.getWidth();
				height = decoder.getHeight();

				buf = ByteBuffer.allocateDirect(width * height * 4);
				decoder.decode(buf, width * 4, Format.RGBA);
				buf.flip();
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		_textures.put(path, textureID);

		return textureID;
	}
}
