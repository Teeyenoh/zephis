package uk.co.quarklike.prototype.engine.gamestate;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.map.Map;

public class MainMenuState implements GameState {
	@Override
	public void init() {

	}

	@Override
	public void update(ContentHub contentHub, Map map) {
		contentHub.setDrawMap(false);
		
	}

	@Override
	public void deinit() {

	}
}
