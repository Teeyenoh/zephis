package uk.co.quarklike.prototype.engine.gamestate;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.EntityLiving;

public class CombatState implements GameState {
	private ContentHub contentHub;
	private Map map;
	private EntityLiving player;

	public CombatState(Map map, EntityLiving player) {
		this.map = map;
		this.player = player;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;
	}

	@Override
	public void update() {

	}

	@Override
	public void deinit() {

	}

}
