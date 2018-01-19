package uk.co.quarklike.zephis.src;

import java.nio.ByteBuffer;

public class Entity {
	private static final int DATA_ID = 0; // long
	private static final int DATA_X = 8; // short
	private static final int DATA_Y = 10; // short
	private static final int DATA_SUB_X = 12; // byte
	private static final int DATA_SUB_Y = 13; // byte
	private static final int DATA_MOVING = 14; // byte
	private static final int DATA_DIRECTION = 15; // byte
	private static final int DATA_QUEUED_MOVE = 16; // byte

	public static final int DATA_LENGTH = 17;

	private ByteBuffer _data;
	private Map _map;

	private byte _speed = 2; //temp

	public Entity(Map map) {
		_data = ByteBuffer.allocate(DATA_LENGTH);
		_map = map;
		setID(_map.getNewID());
		_map.addEntity(this);
	}

	public Entity(ByteBuffer data, Map map) {
		_data = data;
		_map = map;
		_map.addEntity(this);
	}

	public void update() {
		move();
	}

	private void move() {
		if (isMoving()) {
			setSubX((byte) (getSubX() + Map.getDirectionalX(getDirection()) * _speed));
			setSubY((byte) (getSubY() + Map.getDirectionalY(getDirection()) * _speed));

			if (getSubX() > 16) {
				setSubX((byte) (-16 + _speed));
				setX((short) (getX() + 1));
			}

			if (getSubX() < -15) {
				setSubX((byte) (16 - _speed));
				setX((short) (getX() - 1));
			}

			if (getSubY() > 16) {
				setSubY((byte) (-16 + _speed));
				setY((short) (getY() + 1));
			}

			if (getSubY() < -15) {
				setSubY((byte) (16 - _speed));
				setY((short) (getY() - 1));
			}

			if (getSubX() == 0 && getSubY() == 0) {
				setMoving(false);
			}
		}

		if (!isMoving() && getQueuedMove() != -1) {
			queueMove(getQueuedMove());
			setQueuedMove((byte) -1);
		}
	}

	public void queueMove(byte direction) {
		if (isMoving()) {
			if (direction != getDirection()) {
				setQueuedMove(direction);
			}
			return;
		}

		setDirection(direction);
		setMoving(true);
	}

	public long getID() {
		return _data.getLong(DATA_ID);
	}

	public void setID(long id) {
		_data.putLong(DATA_ID, id);
	}

	public short getX() {
		return _data.getShort(DATA_X);
	}

	public void setX(short x) {
		_data.putShort(DATA_X, x);
	}

	public short getY() {
		return _data.getShort(DATA_Y);
	}

	public void setY(short y) {
		_data.putShort(DATA_Y, y);
	}

	public byte getSubX() {
		return _data.get(DATA_SUB_X);
	}

	public void setSubX(byte subX) {
		_data.put(DATA_SUB_X, subX);
	}

	public byte getSubY() {
		return _data.get(DATA_SUB_Y);
	}

	public void setSubY(byte subY) {
		_data.put(DATA_SUB_Y, subY);
	}

	public boolean isMoving() {
		return _data.get(DATA_MOVING) == 1;
	}

	public void setMoving(boolean moving) {
		_data.put(DATA_MOVING, moving ? (byte) 1 : 0);
	}

	public byte getDirection() {
		return _data.get(DATA_DIRECTION);
	}

	public void setDirection(byte direction) {
		_data.put(DATA_DIRECTION, direction);
	}

	private byte getQueuedMove() {
		return _data.get(DATA_QUEUED_MOVE);
	}

	private void setQueuedMove(byte direction) {
		_data.put(DATA_QUEUED_MOVE, direction);
	}
}
