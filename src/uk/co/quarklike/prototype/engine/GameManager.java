package uk.co.quarklike.prototype.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import uk.co.quarklike.prototype.Log;
import uk.co.quarklike.prototype.Main;
import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.map.Entity;
import uk.co.quarklike.prototype.map.EntityLiving;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.MapData;

public class GameManager implements Manager {
	private ContentHub contentHub;
	private Map currentMap;
	private EntityLiving player;

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
	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {
		currentMap = new Map(MapData.fromFile("test.qm1"));
		(player = new EntityLiving("Player", "tiles/grass.png")).register(currentMap);
		player.setPosition(127, 127);
	}

	@Override
	public void update() {
		Log.info("Game");
		contentHub.setMapToDraw(currentMap);
		contentHub.setCamera(player);
		currentMap.requestTextures(contentHub.getResources());

		for (Entity e : currentMap.getEntities())
			e.update();

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			player.move(Map.NORTH);

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			player.move(Map.EAST);

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			player.move(Map.SOUTH);

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			player.move(Map.WEST);

		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			layer = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			layer = 1;

		if (Keyboard.isKeyDown(Keyboard.KEY_3))
			layer = 2;

		if (Keyboard.isKeyDown(Keyboard.KEY_4))
			layer = 3;

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

	@Override
	public void deinit() {
		currentMap.save("test.qm1");
	}

	@Override
	public String getName() {
		return "Game Manager";
	}

}
