package uk.co.quarklike.zephis.src.graphics.particle;

import uk.co.quarklike.zephis.src.Zephis;

public class ParticleRising extends Particle {
	private int _ySpeed;
	private int _xSpread;

	public ParticleRising(int x, int y, int width, int height, int life, String texturePath, int ySpeed, int xSpread) {
		super(x, y, width, height, life, texturePath);
		_ySpeed = ySpeed;
		_xSpread = xSpread;
	}

	public void update() {
		_y += _ySpeed;
		_x += Zephis.instance.getRandom().nextInt((2 * _xSpread) + 1) - _xSpread;
	}
}
