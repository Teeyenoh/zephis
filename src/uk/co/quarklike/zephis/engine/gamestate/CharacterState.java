package uk.co.quarklike.zephis.engine.gamestate;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.zephis.engine.ContentHub;
import uk.co.quarklike.zephis.engine.gui.windows.GUIStats;
import uk.co.quarklike.zephis.map.Map;
import uk.co.quarklike.zephis.map.entity.EntityLiving;

public class CharacterState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;
	private GUIStats stats;

	public CharacterState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(false);
		this.stats = new GUIStats(contentHub, player);
	}

	private void closeMenu() {
		contentHub.setNewState(new PlayingState(map, player));
	}

	@Override
	public void update() {
		contentHub.addGUI(stats);

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_C) {
					closeMenu();
				}
			}
		}

		stats.refresh();
	}

	@Override
	public void deinit() {

	}
}
