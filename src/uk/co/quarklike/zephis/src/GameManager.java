package uk.co.quarklike.zephis.src;

public class GameManager {
	private RenderEngine _render;

	private Map _currentMap;
	private short _mapToLoad = 0;

	public void initGame() {
		this._render = new RenderEngine();

		this._render.initGL();
		Map.loadMapList("maps_rotbm.properties");

		this._currentMap = Map.loadMap(this._mapToLoad);
	}

	public void updateGame() {
		this._render.render(this._currentMap);
	}

	public void destroyGame() {

	}
}
