package uk.co.quarklike.prototype.map.entity;

import uk.co.quarklike.prototype.map.Map;

public class EntityLiving extends Entity {
	protected int speed = 2;

	protected boolean moving;
	protected byte direction;
	protected byte queued = -1;

	public EntityLiving(String name, String texture) {
		super(name, texture);
	}

	@Override
	public void update() {
		handleMovement();
		if (!moving)
			if (queued != -1) {
				move(queued);
				queued = -1;
			}
	}

	private void handleMovement() {
		if (!moving)
			return;

		subX += speed * Map.getDirectionalX(direction);
		subY += speed * Map.getDirectionalY(direction);

		if (Map.getDirectionalX(direction) != 0)
			if (subX == 0)
				moving = false;
			else if (subX == 16 * (Map.getDirectionalX(direction) / Math.abs(Map.getDirectionalX(direction)))) {
				x += Map.getDirectionalX(direction);
				subX = (short) (16 * -(Map.getDirectionalX(direction) / Math.abs(Map.getDirectionalX(direction))));
			}

		if (Map.getDirectionalY(direction) != 0)
			if (subY == 0)
				moving = false;
			else if (subY == 16 * (Map.getDirectionalY(direction) / Math.abs(Map.getDirectionalY(direction)))) {
				y += Map.getDirectionalY(direction);
				subY = (short) (16 * -(Map.getDirectionalY(direction) / Math.abs(Map.getDirectionalY(direction))));
			}
	}

	public void move(byte direction) {
		if (moving)
			if (direction != this.direction) {
				queued = direction;
				return;
			}

		this.direction = direction;
		if (!map.isBlocked(x, y, direction))
			moving = true;
	}
}
