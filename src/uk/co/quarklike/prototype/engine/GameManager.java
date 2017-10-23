package uk.co.quarklike.prototype.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import uk.co.quarklike.prototype.DatabaseParser;
import uk.co.quarklike.prototype.Main;
import uk.co.quarklike.prototype.SaveManager;
import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.gamestate.GameState;
import uk.co.quarklike.prototype.engine.gamestate.PlayingState;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.MapData;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.ItemStack;

public class GameManager implements Manager {
	private ContentHub contentHub;
	private Map currentMap;
	private EntityLiving player;
	private GameState currentState;

	// Temp
	private int tile;
	private int texture;
	private byte collision;
	private byte layer;
	private byte mode;

	private static final int TILE = 0;
	private static final int COLLISION = 1;

	@Override
	public void preInit(ContentHub contentHub) {
		this.contentHub = contentHub;
		DatabaseParser.parseDatabse("zephis.accdb");
	}

	@Override
	public void init() {
		currentMap = new Map(MapData.fromFile("test.qm1"));
		player = new EntityLiving("Player", "tiles/grass.png");
	}

	@Override
	public void postInit() {
		SaveManager.readFile(currentMap, player, "testsave.qs1");
		switchState(new PlayingState(currentMap, player));
	}

	@Override
	public void update() {
		if (currentState != null)
			currentState.update();

		if (contentHub.getNewState() != null) {
			this.switchState(contentHub.getNewState());
			contentHub.setNewState(null);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			layer = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			layer = 1;

		if (Keyboard.isKeyDown(Keyboard.KEY_3))
			layer = 2;

		if (Keyboard.isKeyDown(Keyboard.KEY_4))
			layer = 3;

		if (Keyboard.isKeyDown(Keyboard.KEY_5))
			layer = 4;

		if (Keyboard.isKeyDown(Keyboard.KEY_6))
			layer = 5;

		if (Keyboard.isKeyDown(Keyboard.KEY_7))
			layer = 6;

		if (Keyboard.isKeyDown(Keyboard.KEY_8))
			layer = 7;

		if (Keyboard.isKeyDown(Keyboard.KEY_9))
			layer = 8;

		int mouseX = Mouse.getX() - (contentHub.getWindowWidth() / 2);
		int mouseY = Mouse.getY() - (contentHub.getWindowHeight() / 2);
		mouseX += (mouseX < 0 ? -16 : 16);
		mouseY += (mouseY < 0 ? -16 : 16);
		int mouseWheel = Mouse.getDWheel();
		int tileX = ((mouseX + player.getSubX()) / 32) + player.getX();
		int tileY = -(((mouseY + player.getSubY()) / 32) - player.getY());

		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				if (Mouse.getEventButton() == 0) {
					switch (mode) {
					case TILE:
						currentMap.setTile(tileX, tileY, layer, contentHub.slot + 1);
						currentMap.setTexture(tileX, tileY, layer, contentHub.texture);
						break;
					case COLLISION:
						currentMap.setCollision(tileX, tileY, collision, !currentMap.getCollision(tileX, tileY, collision));
						break;
					}
				} else if (Mouse.getEventButton() == 1) {
					switch (mode) {
					case TILE:
						currentMap.setTile(tileX, tileY, layer, 0);
						currentMap.setTexture(tileX, tileY, layer, 0);
						break;
					}
				} else if (Mouse.getEventButton() == 2) {
					mode = (byte) Util.wrap(mode + 1, 0, 1);
				}
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_W) {
					contentHub.texture = Util.clamp(contentHub.texture + 1, 0, 256);
				} else if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					contentHub.slot = Util.clamp(contentHub.slot - 1, 0, 256);
				} else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					contentHub.texture = Util.clamp(contentHub.texture - 1, 0, 256);
				} else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					contentHub.slot = Util.clamp(contentHub.slot + 1, 0, 256);
				}
			}
		}

		Display.setTitle(Main.TITLE + " Mode: " + mode + " Tile: " + tile + " Texture: " + texture + " Layer: " + layer);

		if (mode == COLLISION) {
			if (mouseWheel < 0)
				collision = (byte) Util.wrap(collision - 1, 0, 3);
			if (mouseWheel > 0)
				collision = (byte) Util.wrap(collision + 1, 0, 3);
		}
	}

	public void switchState(GameState newState) {
		if (currentState != null)
			currentState.deinit();
		currentState = newState;
		currentState.init(contentHub);
	}

	@Override
	public void deinit() {
		if (Main.DEBUG)
			currentMap.save("test.qm1");
		SaveManager.saveFile(currentMap, player, "testsave.qs1");
	}

	@Override
	public String getName() {
		return "Game Manager";
	}

}
