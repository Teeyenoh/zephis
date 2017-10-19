package uk.co.quarklike.prototype.engine.gamestate;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.GUIText;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.Entity;

public class PlayingState implements GameState {
	@Override
	public void init() {

	}

	@Override
	public void update(ContentHub contentHub, Map map) {
		contentHub.setMapToDraw(map);
		contentHub.setDrawMap(true);
		map.requestTextures(contentHub.getResources());

		for (Entity e : map.getEntities())
			e.update();

		map.update();
	}

	@Override
	public void deinit() {

	}
}
