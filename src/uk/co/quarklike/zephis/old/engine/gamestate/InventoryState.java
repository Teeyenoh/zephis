package uk.co.quarklike.zephis.old.engine.gamestate;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.zephis.old.Util;
import uk.co.quarklike.zephis.old.engine.ContentHub;
import uk.co.quarklike.zephis.old.engine.gui.windows.GUIBars;
import uk.co.quarklike.zephis.old.engine.gui.windows.GUIInventory;
import uk.co.quarklike.zephis.old.map.Map;
import uk.co.quarklike.zephis.old.map.entity.EntityLiving;
import uk.co.quarklike.zephis.old.map.item.Inventory;
import uk.co.quarklike.zephis.old.map.item.Item;
import uk.co.quarklike.zephis.old.map.item.type.ItemType;

public class InventoryState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;
	private Inventory container;
	private boolean containerOpen;
	private String name;
	private int screen;

	private int menuDelay;
	private int item_inventory;
	private int item_container;

	private GUIInventory inventory;
	private GUIBars stats;

	public InventoryState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	public InventoryState(Map map, EntityLiving player, Inventory container, String name) {
		this.map = map;
		this.player = player;
		this.container = container;
		this.containerOpen = true;
		this.name = name;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(false);
		inventory = !containerOpen ? new GUIInventory(contentHub, player) : new GUIInventory(contentHub, player, container, name);
		inventory.setScreen(screen = Util.wrapIf(screen + 1, 0, 1, containerOpen, screen));
		stats = new GUIBars(contentHub, player);
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
					if (!containerOpen)
						player.dropItem(item_inventory);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_T) {
					switch (screen) {
					case 0:
						if (containerOpen) {
							container.addItem(player.getInventory().getItems().get(item_inventory).getItemID(), (byte) 1);
							player.getInventory().removeItem(item_inventory);
						} else {
							player.throwItem(item_inventory);
							closeMenu();
						}
						break;
					case 1:
						player.getInventory().addItem(container.getItems().get(item_container).getItemID(), (byte) 1);
						container.removeItem(item_container);
						break;
					}
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
					switch (screen) {
					case 0:
						ItemType i = Item.getItem(player.getInventory().getItems().get(item_inventory).getItemID()).getItemType();
						i.use(map, player);
						if (i.isUsed())
							player.getInventory().removeItem(item_inventory);
						break;
					case 1:
						ItemType j = Item.getItem(container.getItems().get(item_container).getItemID()).getItemType();
						j.use(map, player);
						if (j.isUsed())
							container.removeItem(item_container);
						break;
					}
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
					inventory.setScreen(screen = Util.wrapIf(screen + 1, 0, 1, containerOpen, screen));
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && menuDelay == 0) {
			item_inventory = Util.wrap(item_inventory - 1, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && menuDelay == 0) {
			item_inventory = Util.wrap(item_inventory - 3, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && menuDelay == 0) {
			item_inventory = Util.wrap(item_inventory + 1, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && menuDelay == 0) {
			item_inventory = Util.wrap(item_inventory + 3, 0, player.getInventory().getItems().size() - 1);
			menuDelay = 16;
		}

		if (!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			menuDelay = 0;
		}

		menuDelay = Util.clamp(menuDelay - 1, 0, 16);

		player.getInventory().update();

		switch (screen) {
		case 0:
			inventory.setItem(item_inventory = Util.wrap(item_inventory, 0, player.getInventory().getItems().size() - 1));
			break;
		case 1:
			inventory.setItem(item_container = Util.wrap(item_container, 0, container.getItems().size() - 1));
			break;
		}
		if (container != null)
			container.update();
		inventory.refresh();
		inventory.setItem(item_inventory);
	}

	@Override
	public void deinit() {

	}
}
