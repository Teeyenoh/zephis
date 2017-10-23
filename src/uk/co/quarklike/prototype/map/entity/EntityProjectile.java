package uk.co.quarklike.prototype.map.entity;

public class EntityProjectile extends EntityLiving {
	private byte direction;
	private int startX = -1, startY = -1;
	private int range;

	public EntityProjectile(String name, String texture, byte direction, int range) {
		super(name, texture);
		this.direction = direction;
		this.speed = 4;
		this.range = range;
	}

	public void update() {
		if (startX == -1 && startY == -1) {
			startX = getX();
			startY = getY();
		}

		if (Math.abs(startX - getX()) >= range || Math.abs(startY - getY()) >= range) {
			finish();
		}

		move(direction);
		if (!moving)
			finish();
		super.update();
	}

	protected void finish() {
		map.removeEntity(this.getEntityID());
	}
}
