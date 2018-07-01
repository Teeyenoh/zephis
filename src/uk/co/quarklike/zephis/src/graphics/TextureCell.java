package uk.co.quarklike.zephis.src.graphics;

import org.lwjgl.opengl.GL11;

public class TextureCell {
	private String _texturePath;
	private int _textureWidth;
	private int _textureHeight;
	private int _cellWidth;
	private int _cellHeight;
	private int _cellX;
	private int _cellY;

	public TextureCell(String path, int width, int height, int cellWidth, int cellHeight, int cellX, int cellY) {
		_texturePath = path;
		_textureWidth = width;
		_textureHeight = height;
		_cellWidth = cellWidth;
		_cellHeight = cellHeight;
		_cellX = cellX;
		_cellY = cellY;
	}

	public String getTexture() {
		return _texturePath;
	}

	public float getXCoord1() {
		return _cellX * ((float) _cellWidth / _textureWidth);
	}

	public float getXCoord2() {
		return (_cellX + 1) * ((float) _cellWidth / _textureWidth);
	}

	public float getYCoord1() {
		return _cellY * ((float) _cellHeight / _textureHeight);
	}

	public float getYCoord2() {
		return (_cellY + 1) * ((float) _cellHeight / _textureHeight);
	}

	public void bind(TextureManager manager) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, manager.getTexture(_texturePath));
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
