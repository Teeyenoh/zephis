package uk.co.quarklike.zephis.src.map.entity;

import uk.co.quarklike.zephis.src.map.Map;

public class Entity {
	private long _entityID;
	private Map _map;

	private Body _body;
	private Mind _mind;

	public Entity(Map map) {
		_map = map;
		_entityID = map.registerEntity(this);
		_body = new Body(this);
		_mind = new Mind(this);
	}

	public void update() {
		if (_body.update() || _mind.update()) {
			remove();
		}
	}

	public Body getBody() {
		return _body;
	}

	public Mind getMind() {
		return _mind;
	}

	public int getPositionID() {
		return (_body.getY() * _map.getWidth()) + _body.getX();
	}

	public long getEntityID() {
		return _entityID;
	}

	public String getName() {
		return "Quarkbean";
	}

	public int getLevel() {
		return 1;
	}

	public String getRace() {
		return "Plains Elf";
	}

	public Map getMap() {
		return _map;
	}

	public void setMap(short map, byte x, byte y) {
		_map.removeEntity(this._entityID);
		_map = _map.getMapManager().getMap(map);
		_entityID = _map.registerEntity(this);
		_body.setPosition(x, y);
	}

	public void remove() {
		_map.removeEntity(_entityID);
	}
}
