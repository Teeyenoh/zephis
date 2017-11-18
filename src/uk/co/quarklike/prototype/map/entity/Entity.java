package uk.co.quarklike.prototype.map.entity;

import uk.co.quarklike.prototype.engine.SaveManager;
import uk.co.quarklike.prototype.map.Map;

public class Entity {
	// Entity type
	protected String entityName;
	protected String texture;

	// Entity instance
	protected long entityID;
	protected Map map;
	protected int x, y;
	protected byte subX, subY;
	protected byte textureSlot;

	public Entity(String name, String texture) {
		this.entityName = name;
		this.texture = texture;
	}

	public void loadEntity(String name, int x, int y, byte subX, byte subY) {
		this.entityName = name.trim();
		this.x = x;
		this.y = y;
		this.subX = subX;
		this.subY = subY;
	}

	public void register(Map map) {
		this.map = map;
		this.entityID = map.addEntity(this);
	}
	
	public void register(Map map, long id) {
		this.map = map;
		this.entityID = map.addEntity(this, id);
	}

	public void update() {

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public byte getSubX() {
		return subX;
	}

	public byte getSubY() {
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

	public long getEntityID() {
		return entityID;
	}

	public int getMapID() {
		return map.getMapID();
	}
	
	public byte getType() {
		return SaveManager.DEFAULT;
	}
}
