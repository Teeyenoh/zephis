package uk.co.quarklike.zephis.old.engine.gamestate;

import uk.co.quarklike.zephis.old.engine.ContentHub;

public interface GameState {
	public void init(ContentHub contentHub);

	public void update();

	public void deinit();
}
