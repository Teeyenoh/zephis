package uk.co.quarklike.prototype.engine.gamestate;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.windows.GUIInventory;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.EntityLiving;

public class MenuState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;

	private GUIInventory inventory;

	public MenuState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(false);
		inventory = new GUIInventory(contentHub);
	}

	@Override
	public void update() {
		contentHub.addGUI(inventory);

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					contentHub.setNewState(new PlayingState(map, player));
				}
			}
		}
	}

	@Override
	public void deinit() {

	}
}
