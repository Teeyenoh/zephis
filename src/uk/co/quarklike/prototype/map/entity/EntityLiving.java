package uk.co.quarklike.prototype.map.entity;

import uk.co.quarklike.prototype.Log;
import uk.co.quarklike.prototype.SaveManager;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.item.Inventory;
import uk.co.quarklike.prototype.map.item.ItemStack;

public class EntityLiving extends Entity {
	protected final int baseSpeed = 2;
	protected int speed = 2;
	protected boolean sprinting;

	protected boolean moving;
	protected byte direction;
	protected byte queued = -1;

	protected Stats stats;
	protected Inventory inventory;

	public EntityLiving(String name, String texture) {
		super(name, texture);
		this.stats = new Stats(this, (short) 100, (short) 100, (short) 100, (short) 100, (short) 100, (short) 100, (byte) 100, (byte) 100, (byte) 100);
		this.inventory = new Inventory(10);
	}

	public void loadEntity(String name, int x, int y, byte subX, byte subY, byte direction, boolean moving, short maxHealth, short health, short maxMana, short mana, short maxStamina, short stamina, byte hunger, byte tiredness, byte warmth) {
		super.loadEntity(name, x, y, subX, subY);
		this.direction = direction;
		this.moving = moving;
		this.stats = new Stats(this, maxHealth, health, maxMana, mana, maxStamina, stamina, hunger, tiredness, warmth);
	}

	@Deprecated
	@Override
	public void loadEntity(String name, int x, int y, byte subX, byte subY) {

	}

	@Override
	public void update() {
		handleMovement();

		stats.update();

		if (stats.getStamina() <= 0) {
			stats.addStamina((byte) -stats.getStamina());
			stopSprinting();
		}

		if (!moving) {
			if (queued != -1) {
				move(queued);
				queued = -1;
			}
		}

		inventory.update();
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
		if (moving) {
			if (direction != this.direction) {
				queued = direction;
			}
			return;
		}

		this.direction = direction;
		if (!map.isBlocked(x, y, direction)) {
			moving = true;

			if (sprinting) {
				stats.addStamina((byte) -2);
				speed = baseSpeed * 2;
				Log.info("Check!");
			} else
				speed = baseSpeed;
		}
	}

	public boolean addItem(ItemStack i) {
		return inventory.addItem(i.getItemID(), i.getQuantity());
	}

	public void pickUpItem() {
		// int item = map.getItem(getX(), getY());
		// if (item != 0) {
		// if (inventory.addItem(item, (byte) 1)) {
		// map.setItem(getX(), getY(), 0);
		// }
		// }
	}

	public void dropItem(ItemStack i) {
		if (inventory.removeItem(i.getItemID(), 1)) {
			map.addItem(this.getX(), this.getY(), i.getItemID(), (byte) 1);
		}
	}

	public void dropItem(int item) {
		dropItem(inventory.getItems().get(item));
	}

	public void throwItem(ItemStack i) {
		if (inventory.removeItem(i.getItemID(), 1)) {
			if (map.getCollision(x, y, direction)) {
				dropItem(i);
			} else {
				Entity proj = new EntityProjectileItem(this.direction, i.getItemID());
				proj.setPosition(x + Map.getDirectionalX(direction), y + Map.getDirectionalY(direction));
				proj.register(map);
			}
		}
	}

	public void throwItem(int item) {
		throwItem(inventory.getItems().get(item));
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void startSprinting() {
		sprinting = true;
	}

	public void stopSprinting() {
		sprinting = false;
	}

	public boolean isMoving() {
		return moving;
	}

	public byte getDirection() {
		return direction;
	}

	public Stats getStats() {
		return this.stats;
	}

	@Override
	public byte getType() {
		return SaveManager.LIVING;
	}
}
