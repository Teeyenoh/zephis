package uk.co.quarklike.prototype.engine.gamestate;

import uk.co.quarklike.prototype.engine.ContentHub;

public interface GameState {
	public void init(ContentHub contentHub);

	public void update();

	public void deinit();
}
