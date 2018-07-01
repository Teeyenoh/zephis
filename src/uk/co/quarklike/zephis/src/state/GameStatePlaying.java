package uk.co.quarklike.zephis.src.state;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.zephis.src.Const;
import uk.co.quarklike.zephis.src.Zephis;
import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.map.entity.Entity;

public class GameStatePlaying implements GameState {
	private Zephis _instance;

	private Entity _player;

	public void init(Zephis instance, Entity player) {
		_instance = instance;
		_player = player;
	}

	public void update(RenderEngine renderEngine) {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			_player.getBody().move_input(Const.DIR_FORWARD);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			_player.getBody().move_input(Const.DIR_RIGHT);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			_player.getBody().move_input(Const.DIR_BACKWARD);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			_player.getBody().move_input(Const.DIR_LEFT);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
					_player.getBody().attack();
				} else if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
					_instance.changeState(new GameStateStatMenu());
				} else if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					_instance.changeState(new GameStateInventory());
				}
			}
		}

		_player.getMap().update();

		for (Entity e : _player.getMap().getEntities()) {
			e.update();
		}

		renderEngine.renderPlaying(_player.getMap(), _player);
	}

	public void deinit() {

	}

	public int getStateID() {
		return STATE_PLAYING;
	}

}
