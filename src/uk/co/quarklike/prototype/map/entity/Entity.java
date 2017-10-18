package uk.co.quarklike.prototype.map.entity;

import uk.co.quarklike.prototype.map.Map;

public class Entity {
	// Entity type
	protected String entityName;
	protected String texture;

	// Entity instance
	protected long entityID;
	protected Map map;
	protected int x, y;
	protected short subX, subY;
	protected short textureSlot = 1;

	public Entity(String name, String texture) {
		this.entityName = name;
		this.texture = texture;
	}

	public void register(Map map) {
		this.map = map;
		this.entityID = map.addEntity(this);
	}

	public void update() {

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public short getSubX() {
		return subX;
	}

	public short getSubY() {
		return subY;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.subX = 0;
		this.subY = 0;
	}

	public String getName() {
		return entityName;
	}

	public String getTexture() {
		return texture;
	}

	public short getTextureSlot() {
		return textureSlot;
	}

	public long getID() {
		return entityID;
	}
}
