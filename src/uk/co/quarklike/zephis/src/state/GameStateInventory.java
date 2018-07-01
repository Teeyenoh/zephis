package uk.co.quarklike.zephis.src.state;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.zephis.src.Zephis;
import uk.co.quarklike.zephis.src.graphics.MenuPosition;
import uk.co.quarklike.zephis.src.graphics.MenuPositionGroup;
import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.map.entity.Entity;

public class GameStateInventory implements GameState {
	private Zephis _instance;

	private Entity _player;
	private MenuPosition _inventoryPosition;

	public void init(Zephis instance, Entity player) {
		_instance = instance;
		_player = player;
		_player.getBody().getInventory().sortByAlphabet();
		_inventoryPosition = new MenuPosition(new MenuPositionGroup[] { new MenuPositionGroup(_player.getBody().getInventory().getItems().length, 27, 3) });
	}

	public void update(RenderEngine renderEngine) {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					_inventoryPosition.moveUp();
				} else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					_inventoryPosition.moveDown();
				} else if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
					_instance.changeState(new GameStatePlaying());
				} else if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					_instance.changeState(new GameStatePlaying());
				}
			}
		}

		renderEngine.renderInventory(_player, _inventoryPosition);
	}

	public void deinit() {

	}

	public int getStateID() {
		return STATE_INVENTORY;
	}
}
