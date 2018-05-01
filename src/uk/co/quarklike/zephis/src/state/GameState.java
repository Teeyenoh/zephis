package uk.co.quarklike.zephis.src.state;

import uk.co.quarklike.zephis.src.Zephis;
import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.map.Map;
import uk.co.quarklike.zephis.src.map.entity.Entity;

public interface GameState {
	public static final int STATE_PLAYING = 0;
	public static final int STATE_STAT_MENU = 1;
	public static final int STATE_INVENTORY = 2;

	public void init(Zephis instance, Entity player, Map map);

	public void update(RenderEngine renderEngine);

	public void deinit();

	public int getStateID();
}
