package uk.co.quarklike.prototype.engine.gamestate;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.windows.GUIInventory;
import uk.co.quarklike.prototype.engine.gui.windows.GUIStats;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.type.ItemType;

public class MenuState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;

	private int menuDelay;
	private int item;

	private GUIInventory inventory;
	private GUIStats stats;

	public MenuState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(false);
		inventory = new GUIInventory(contentHub, player);
		stats = new GUIStats(contentHub, player);
	}

	private void closeMenu() {
		contentHub.setNewState(new PlayingState(map, player));
	}

	@Override
	public void update() {
		contentHub.addGUI(inventory);
		contentHub.addGUI(stats);

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
					ItemType i = Item.getItem(player.getInventory().getItems().get(item).getItemID()).getItemType();
					i.use(map, player);
					if (i.isUsed())
						player.getInventory().removeItem(item);
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && menuDelay == 0) {
			item = Util.wrap(item - 1, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && menuDelay == 0) {
			item = Util.wrap(item - 3, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && menuDelay == 0) {
			item = Util.wrap(item + 1, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && menuDelay == 0) {
			item = Util.wrap(item + 3, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			menuDelay = 0;
		}

		menuDelay = Util.clamp(menuDelay - 1, 0, 16);

		player.getInventory().update();
		inventory.refresh();
		inventory.setItem(item);
	}

	@Override
	public void deinit() {

	}
}
