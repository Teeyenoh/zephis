package uk.co.quarklike.zephis.src.graphics.particle;

import uk.co.quarklike.zephis.src.Zephis;

public abstract class Particle {
	protected int _x, _y;
	private int _life;
	private int _width, _height;
	private int _textureID;

	public Particle(int x, int y, int width, int height, int life, String texturePath) {
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_life = life;
		_textureID = Zephis.instance.getTextureManager().getTexture(texturePath);
	}

	public abstract void update();

	public int getTextureID() {
		return _textureID;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public boolean isDead() {
		return (_life -= 1) == 0;
	}
}
