package uk.co.quarklike.src;

public class Entity {
	public static final byte DIR_NONE = 0x11;
	public static final byte DIR_FORWARD = 0x12;
	public static final byte DIR_RIGHT = 0x21;
	public static final byte DIR_BACKWARD = 0x10;
	public static final byte DIR_LEFT = 0x01;

	public static final int getXDirection(byte dir) {
		byte comp = (byte) (dir >> 4);
		return comp == 0x0 ? -1 : comp == 0x2 ? 1 : 0;
	}

	public static final int getYDirection(byte dir) {
		byte comp = (byte) (dir - ((int) (dir >> 4) << 4));
		return comp == 0x0 ? -1 : comp == 0x2 ? 1 : 0;
	}

	private long _entityID;
	private Map _map;
	private int _x, _y;
	private byte _subX, _subY;
	private boolean _moving;
	private byte _direction = DIR_NONE;
	private byte _queuedMove = DIR_NONE;

	private int _speed = 2;
	private int _hp = 10;

	public Entity(Map map) {
		_map = map;
		_entityID = map.registerEntity(this);
	}

	public void update() {
		if (_hp <= 0) {
			_map.removeEntity(_entityID);
			return;
		}

		if (_moving) {
			_subX += _speed * getXDirection(_direction);
			_subY += _speed * getYDirection(_direction);

			if (_subX >= 16) {
				_subX -= 32;
				_x += 1;
			} else if (_subX <= -16) {
				_subX += 32;
				_x -= 1;
			} else if (_subX == 0 && getXDirection(_direction) != 0) {
				_moving = false;
			}

			if (_subY >= 16) {
				_subY -= 32;
				_y += 1;
			} else if (_subY <= -16) {
				_subY += 32;
				_y -= 1;
			} else if (_subY == 0 && getYDirection(_direction) != 0) {
				_moving = false;
			}
		}

		if (!_moving) {
			move(_queuedMove);
			_queuedMove = DIR_NONE;
		}
	}

	public void move(byte direction) {
		if (direction == DIR_NONE)
			return;

		if (!_moving) {
			if (checkMove(direction))
				_moving = true;
			_direction = direction;
		} else if (direction != _direction) {
			_queuedMove = direction;
		}
	}

	public void attack() {
		for (int i = -1; i <= 1; i++) {
			Entity e = null;
			int x = _x + getXDirection(_direction) + (i * getYDirection(_direction));
			int y = _y + getYDirection(_direction) + (i * getXDirection(_direction));

			if ((e = _map.getEntity(x, y)) != null) {
				e.takeDamage(5);
			}
		}
	}

	public void takeDamage(int damage) {
		_hp -= damage;
	}

	private boolean checkMove(byte direction) {
		if (_map.isBlocked(_x + getXDirection(direction), _y + getYDirection(direction))) {
			return false;
		}

		return true;
	}

	public void setX(int x) {
		_x = x;
	}

	public int getX() {
		return _x;
	}

	public void setY(int y) {
		_y = y;
	}

	public int getY() {
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

	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setPosition(int x, int y, byte subX, byte subY) {
		setPosition(x, y);
		setSubX(subX);
		setSubY(subY);
	}

	public int getPositionID() {
		return (_y * _map.getWidth()) + _x;
	}

	public long getEntityID() {
		return _entityID;
	}
}
