package uk.co.quarklike.zephis.src;

import org.lwjgl.input.Keyboard;

public class GameManager {
	private RenderEngine _render;

	private Map _currentMap;
	private Map _lastMap;

	private static final byte STATE_PLAYING = 0;
	private static final byte STATE_ACTION = 1;

	private byte _gameState;

	private static final byte ACTION_ATTACK = 0;
	private static final byte ACTION_SWAG = 1;

	private byte _actionIndex;

	public void initGame() {
		_render = new RenderEngine();

		_render.initGL();
		_render.loadTextureList("terrain_rotbm.properties");
		Map.loadMapList("maps_rotbm.properties");

		loadMaps(SaveManager.loadSave("testSave"));

		_gameState = STATE_PLAYING;
	}

	public void updateGame() {
		if (_gameState == STATE_PLAYING) {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				_currentMap.getPlayer().queueMove(Map.DIRECTION_UP);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				_currentMap.getPlayer().queueMove(Map.DIRECTION_LEFT);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				_currentMap.getPlayer().queueMove(Map.DIRECTION_DOWN);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				_currentMap.getPlayer().queueMove(Map.DIRECTION_RIGHT);
			}

			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_X) {
						_gameState = STATE_ACTION;
					}
				}
			}

			// Update the previous map's entities
			if (_lastMap != null) {
				for (Entity e : _lastMap.getEntities()) {
					e.update();
				}
			}

			// Update the current map
			for (Entity e : _currentMap.getEntities()) {
				e.update();
			}

			_render.render(_currentMap, Map.PLAYER_ID, false);
		} else if (_gameState == STATE_ACTION) {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				_actionIndex = (byte) ((_actionIndex + 1) % 2);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				_actionIndex = (byte) ((_actionIndex - 1) % 2);
			}

			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_X) {
						_gameState = STATE_PLAYING;
					}
				}
			}

			_render.render(_currentMap, Map.PLAYER_ID, true);
			_render.drawActionMenu(_actionIndex);
		}
	}

	private void loadMaps(Map[] maps) {
		_lastMap = maps[1];
		_render.clearMapTextures();
		_render.loadMapTextures(maps[0]);
		_currentMap = maps[0];
	}

	private void changeMap(short newMapID) {
		Map newMap = Map.loadMap(newMapID);
		_render.clearMapTextures();
		_render.loadMapTextures(newMap);
		_currentMap = newMap;
	}

	public void destroyGame() {

	}
}
