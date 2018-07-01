package uk.co.quarklike.zephis.src.map;

import uk.co.quarklike.zephis.src.map.entity.Entity;

public class TeleporterDoor extends Teleporter {
	public TeleporterDoor(short map, byte fromX, byte fromY, byte toX, byte toY) {
		super(map, fromX, fromY, toX, toY);
	}

	@Override
	public void teleport(Entity e) {
		super.teleport(e);
		e.getBody().move_simple(e.getBody().getDirection());
	}
}
