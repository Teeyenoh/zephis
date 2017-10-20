package uk.co.quarklike.prototype.engine.gamestate;

import uk.co.quarklike.prototype.engine.ContentHub;

public class MainMenuState implements GameState {
	private ContentHub contentHub;

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;
	}

	@Override
	public void update() {
		contentHub.setDrawMap(false);
	}

	@Override
	public void deinit() {

	}
}
