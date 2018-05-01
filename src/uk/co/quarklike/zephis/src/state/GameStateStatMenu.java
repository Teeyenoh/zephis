package uk.co.quarklike.zephis.src.state;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.zephis.src.Zephis;
import uk.co.quarklike.zephis.src.graphics.MenuPosition;
import uk.co.quarklike.zephis.src.graphics.MenuPositionGroup;
import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.map.Map;
import uk.co.quarklike.zephis.src.map.entity.Entity;
import uk.co.quarklike.zephis.src.map.entity.Skills;

public class GameStateStatMenu implements GameState {
	private Zephis _instance;
	
	private Map _map;
	private Entity _player;
	private MenuPosition _statPosition;

	public void init(Zephis instance, Entity player, Map map) {
		_instance = instance;
		_player = player;
		_map = map;
		_statPosition = new MenuPosition(new MenuPositionGroup[] { new MenuPositionGroup(6, 6, 0), new MenuPositionGroup(Skills.SKILL_COUNT, 19, 3) });
	}

	public void update(RenderEngine renderEngine) {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					_statPosition.moveUp();
				} else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					_statPosition.moveDown();
				} else if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
					_instance.changeState(new GameStatePlaying());
				}
			}
		}
		
		renderEngine.renderStats(_map, _player, _statPosition);
	}

	public void deinit() {

	}

	public int getStateID() {
		return STATE_STAT_MENU;
	}
}
