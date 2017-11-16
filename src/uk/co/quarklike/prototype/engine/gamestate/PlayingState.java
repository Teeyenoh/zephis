package uk.co.quarklike.prototype.engine.gamestate;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.windows.GUIBars;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.Entity;
import uk.co.quarklike.prototype.map.entity.EntityLiving;

public class PlayingState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;

	private GUIBars stats;

	public PlayingState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(true);
		contentHub.setMapToDraw(map);
		contentHub.setCamera(player);

		stats = new GUIBars(contentHub, player);
	}

	@Override
	public void update() {
		contentHub.addGUI(stats);

		map.requestTextures(contentHub.getResources());

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			player.move(Map.NORTH);

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			player.move(Map.EAST);

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			player.move(Map.SOUTH);

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			player.move(Map.WEST);

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			player.startSprinting();
		else
			player.stopSprinting();

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					contentHub.setNewState(new InventoryState(map, player));
				} else if (Keyboard.getEventKey() == Keyboard.KEY_G) {
					contentHub.setNewState(new InventoryState(map, player, map.getItem(player.getX(), player.getY()), "GUI_WINDOW_CONTAINER_TITLE_GROUND"));
				} else if (Keyboard.getEventKey() == Keyboard.KEY_C) {
					contentHub.setNewState(new CharacterState(map, player));
				}

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

		for (Entity e : map.getEntities())
			e.update();

		map.update();
	}

	@Override
	public void deinit() {

	}
}
