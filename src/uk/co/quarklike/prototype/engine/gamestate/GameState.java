package uk.co.quarklike.prototype.engine.gamestate;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.map.Map;

public interface GameState {
	public void init();

	public void update(ContentHub contentHub, Map map);

	public void deinit();
}
