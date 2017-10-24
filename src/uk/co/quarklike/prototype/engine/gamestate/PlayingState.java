package uk.co.quarklike.prototype.engine.gamestate;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.Entity;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.item.Item;
import uk.co.quarklike.prototype.map.item.ItemStack;

public class PlayingState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;

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
	}

	@Override
	public void update() {
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
			player.setSpeed(4);
		else
			player.setSpeed(2);

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_T) {
					player.throwItem(new ItemStack(Item.getItem("Copper Ingot").getID(), (byte) 1));
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_P) {
					player.pickUpItem();
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_I) {
					contentHub.setNewState(new MenuState(map, player));
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
