package uk.co.quarklike.prototype.engine;

import uk.co.quarklike.prototype.map.Entity;
import uk.co.quarklike.prototype.map.Map;

public class ContentHub {
	private ResourceManager resources;
	private Map mapToDraw;
	private Entity camera;
	private int windowWidth, windowHeight;

	// Temp
	public int slot;
	public int texture;

	public ResourceManager getResources() {
		return resources;
	}

	public Map getMapToDraw() {
		return mapToDraw;
	}

	public Entity getCamera() {
		return camera;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setResources(ResourceManager m_resource) {
		this.resources = m_resource;
	}

	public void setMapToDraw(Map map) {
		this.mapToDraw = map;
	}

	public void setCamera(Entity camera) {
		this.camera = camera;
	}

	public void setWindowWidth(int width) {
		this.windowWidth = width;
	}

	public void setWindowHeight(int height) {
		this.windowHeight = height;
	}
}
