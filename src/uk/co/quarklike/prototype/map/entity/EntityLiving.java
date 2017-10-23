package uk.co.quarklike.prototype.map.entity;

import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.item.Inventory;
import uk.co.quarklike.prototype.map.item.ItemStack;

public class EntityLiving extends Entity {
	protected int speed = 2;
	protected int nextSpeed;

	protected boolean moving;
	protected byte direction;
	protected byte queued = -1;

	public Inventory body;

	public EntityLiving(String name, String texture) {
		super(name, texture);
		body = new Inventory(10);
	}

	public void loadPlayer(String name, int x, int y, byte subX, byte subY, byte direction, boolean moving) {
		this.entityName = name;
		this.x = x;
		this.y = y;
		this.subX = subX;
		this.subY = subY;
		this.direction = direction;
		this.moving = moving;
	}

	@Override
	public void update() {
		handleMovement();
		if (!moving) {
			speed = nextSpeed;
			if (queued != -1) {
				move(queued);
				queued = -1;
			}
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
				subX = (byte) (16 * -(Map.getDirectionalX(direction) / Math.abs(Map.getDirectionalX(direction))));
			}

		if (Map.getDirectionalY(direction) != 0)
			if (subY == 0)
				moving = false;
			else if (subY == 16 * (Map.getDirectionalY(direction) / Math.abs(Map.getDirectionalY(direction)))) {
				y += Map.getDirectionalY(direction);
				subY = (byte) (16 * -(Map.getDirectionalY(direction) / Math.abs(Map.getDirectionalY(direction))));
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

	public boolean addItem(ItemStack i) {
		return body.addItem(i.getItemID(), i.getQuantity());
	}

	public void pickUpItem() {
		int item = map.getItem(getX(), getY());
		if (item != 0) {
			if (body.addItem(item, (byte) 1)) {
				map.setItem(getX(), getY(), 0);
			}
		}
	}

	public void dropItem(ItemStack i) {
		if (body.removeItem(i.getItemID(), i.getQuantity())) {
			map.setItem(this.getX(), this.getY(), i.getItemID());
		}
	}

	public void throwItem(ItemStack i) {
		if (body.removeItem(i.getItemID(), 1)) {
			if (map.getCollision(x, y, direction)) {
				dropItem(i);
			} else {
				Entity proj = new EntityProjectileItem(this.direction, i.getItemID());
				proj.setPosition(x + Map.getDirectionalX(direction), y + Map.getDirectionalY(direction));
				proj.register(map);
			}
		}
	}

	public Inventory getInventory() {
		return body;
	}

	public void setSpeed(int speed) {
		this.nextSpeed = speed;
	}

	public boolean isMoving() {
		return moving;
	}

	public byte getDirection() {
		return direction;
	}
}
