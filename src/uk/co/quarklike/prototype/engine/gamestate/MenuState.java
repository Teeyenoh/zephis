package uk.co.quarklike.prototype.engine.gamestate;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.windows.GUIInventory;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.EntityLiving;

public class MenuState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;

	private int menuDelay;
	private int item;

	private GUIInventory inventory;

	public MenuState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(false);
		inventory = new GUIInventory(contentHub, player);
	}
	
	private void closeMenu() {
		contentHub.setNewState(new PlayingState(map, player));
	}

	@Override
	public void update() {
		contentHub.addGUI(inventory);

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					closeMenu();
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					player.dropItem(item);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_T) {
					player.throwItem(item);
					closeMenu();
					
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
					
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && menuDelay == 0) {
			item = Util.wrap(item - 1, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && menuDelay == 0) {
			item = Util.wrap(item + 1, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			menuDelay = 0;
		}

		menuDelay = Util.clamp(menuDelay - 1, 0, 16);

		inventory.setItem(item);

		inventory.refresh();
	}

	@Override
	public void deinit() {

	}
}
