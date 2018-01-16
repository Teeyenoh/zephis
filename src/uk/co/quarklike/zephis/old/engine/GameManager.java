package uk.co.quarklike.zephis.old.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import uk.co.quarklike.zephis.old.Language;
import uk.co.quarklike.zephis.old.Log;
import uk.co.quarklike.zephis.old.Main;
import uk.co.quarklike.zephis.old.Util;
import uk.co.quarklike.zephis.old.engine.gamestate.GameState;
import uk.co.quarklike.zephis.old.engine.gamestate.PlayingState;
import uk.co.quarklike.zephis.old.map.Map;
import uk.co.quarklike.zephis.old.map.MapData;
import uk.co.quarklike.zephis.old.map.entity.EntityLiving;

public class GameManager implements Manager {
	private ContentHub contentHub;
	private Map currentMap;
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
		Log.info("Loading database...");
		DatabaseParser.parseDatabse("zephis.accdb");
	}

	@Override
	public void init() {
		Language.initLanguage("en_GB");
		Log.info("Loading map...");
		currentMap = new Map(MapData.fromFile("test.qm1"));
	}

	@Override
	public void postInit() {
		Log.info("Loading save file...");
		if (SaveManager.readFile(contentHub, currentMap, "testsave.qs1")) {
			switchState(new PlayingState(currentMap, contentHub.getPlayer()));
		}
	}

	@Override
	public void update() {
		if (currentState != null)
			currentState.update();

		if (contentHub.getNewState() != null) {
			this.switchState(contentHub.getNewState());
			contentHub.setNewState(null);
		}

		if (Main.DEBUG) {
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
			int tileX = ((mouseX + contentHub.getCamera().getSubX()) / 32) + contentHub.getCamera().getX();
			int tileY = -(((mouseY + contentHub.getCamera().getSubY()) / 32) - contentHub.getCamera().getY());

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

			Display.setTitle(Main.TITLE + " Mode: " + mode + " Tile: " + tile + " Texture: " + texture + " Layer: " + layer);

			if (mode == COLLISION) {
				if (mouseWheel < 0)
					collision = (byte) Util.wrap(collision - 1, 0, 3);
				if (mouseWheel > 0)
					collision = (byte) Util.wrap(collision + 1, 0, 3);
			}
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
		SaveManager.saveFile(currentMap, contentHub.getPlayer(), "testsave.qs1");
	}

	@Override
	public String getName() {
		return "Game Manager";
	}

}
