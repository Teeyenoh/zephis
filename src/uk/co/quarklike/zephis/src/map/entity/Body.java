package uk.co.quarklike.zephis.src.map.entity;

import uk.co.quarklike.zephis.src.Const;
import uk.co.quarklike.zephis.src.Util;
import uk.co.quarklike.zephis.src.graphics.TextureCell;
import uk.co.quarklike.zephis.src.map.Teleporter;
import uk.co.quarklike.zephis.src.map.item.Inventory;

public class Body {
	private Entity _parent;

	private byte _x, _y;
	private byte _subX, _subY;
	private boolean _moving;
	private byte _direction = Const.DIR_BACKWARD;
	private byte _queuedMove = Const.DIR_NONE;
	private AnimationManager _anim;

	private int _speed = 2;
	private int _hp = 10;
	private int _attackCooldown = 0;
	private Inventory _inventory;

	public Body(Entity parent) {
		_parent = parent;

		_anim = new AnimationManager("sausageBlob");

		_inventory = new Inventory();
	}

	public boolean update() {
		_attackCooldown = Util.max(_attackCooldown - 1, 0);

		if (_moving)
			doMovement();

		if (!_moving)
			doQueuedMove();

		_anim.update();

		// If dead, tell entity to die
		return isDead();
	}

	private void doMovement() {
		if (_moving) {
			_subX += _speed * Util.getXDirection(_direction);
			_subY += _speed * Util.getYDirection(_direction);

			if (_subX >= 16) {
				_subX -= 32;
				_x += 1;
			} else if (_subX <= -16) {
				_subX += 32;
				_x -= 1;
			} else if (_subX == 0 && Util.getXDirection(_direction) != 0) {
				_moving = false;
				checkTeleport();
			}

			if (_subY >= 16) {
				_subY -= 32;
				_y += 1;
			} else if (_subY <= -16) {
				_subY += 32;
				_y -= 1;
			} else if (_subY == 0 && Util.getYDirection(_direction) != 0) {
				_moving = false;
				checkTeleport();
			}
		}
	}

	private void doQueuedMove() {
		move_simple(_queuedMove);
		clearQueuedMove();
	}

	private void clearQueuedMove() {
		_queuedMove = Const.DIR_NONE;
	}

	public void move_simple(byte direction) {
		if (direction == Const.DIR_NONE || _attackCooldown > 0)
			return;

		if (!_moving) {
			if (checkMove(direction))
				_moving = true;
			_direction = direction;
		}
	}

	public void move_input(byte direction) {
		move_simple(direction);

		if (_moving && direction != _direction) {
			_queuedMove = direction;
		}
	}

	private boolean checkMove(byte direction) {
		if (_parent.getMap().isBlocked((byte) (_x + Util.getXDirection(direction)), (byte) (_y + Util.getYDirection(direction)))) {
			return false;
		}

		return true;
	}

	public void attack() {
		if (_attackCooldown > 0 || _moving)
			return;

		Entity e = null;
		byte x = (byte) (_x + Util.getXDirection(_direction));
		byte y = (byte) (_y + Util.getYDirection(_direction));

		if ((e = _parent.getMap().getEntity(x, y)) != null) {
			e.getBody().takeDamage(5);
		}

		_anim.setCurrentAnim("attack_" + Util.getDirectionName(_direction));
		_anim.queueAnim("idle_" + Util.getDirectionName(_direction));
		_attackCooldown = 30;
	}

	public void takeDamage(int damage) {
		_hp -= damage;
	}

	public float getHealthPercent() {
		return (float) _hp / 10;
	}

	public void checkTeleport() {
		Teleporter t = null;
		if ((t = _parent.getMap().getTeleporter(getX(), getY())) != null) {
			t.teleport(_parent);
			clearQueuedMove();
		}
	}

	public void setX(byte x) {
		_x = x;
	}

	public byte getX() {
		return _x;
	}

	public void setY(byte y) {
		_y = y;
	}

	public byte getY() {
		return _y;
	}

	public void setSubX(byte subX) {
		_subX = subX;
	}

	public byte getSubX() {
		return _subX;
	}

	public void setSubY(byte subY) {
		_subY = subY;
	}

	public byte getSubY() {
		return _subY;
	}

	public void setPosition(byte x, byte y) {
		setX(x);
		setY(y);
	}

	public void setPosition(byte x, byte y, byte subX, byte subY) {
		setPosition(x, y);
		setSubX(subX);
		setSubY(subY);
	}

	public byte getDirection() {
		return _direction;
	}

	public Inventory getInventory() {
		return _inventory;
	}

	public TextureCell getTexture() {
		return _anim.getCurrentAnim().getTexture(_direction);
	}

	private boolean isDead() {
		return _hp <= 0;
	}
}
