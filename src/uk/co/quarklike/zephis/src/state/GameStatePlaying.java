package uk.co.quarklike.zephis.src.state;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.zephis.src.Zephis;
import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.map.Map;
import uk.co.quarklike.zephis.src.map.entity.Entity;

public class GameStatePlaying implements GameState {
	private Zephis _instance;

	private Entity _player;
	private Map _map;

	public void init(Zephis instance, Entity player, Map map) {
		_instance = instance;
		_player = player;
		_map = map;
	}

	public void update(RenderEngine renderEngine) {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			_player.move(Entity.DIR_FORWARD);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			_player.move(Entity.DIR_RIGHT);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			_player.move(Entity.DIR_BACKWARD);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			_player.move(Entity.DIR_LEFT);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
					_player.attack();
				} else if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
					_instance.changeState(new GameStateStatMenu());
				} else if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					_instance.changeState(new GameStateInventory());
				}
			}
		}

		_map.update();

		for (Entity e : _map.getEntities()) {
			e.update();
		}

		renderEngine.renderPlaying(_map, _player);
	}

	public void deinit() {

	}

	public int getStateID() {
		return STATE_PLAYING;
	}

}
