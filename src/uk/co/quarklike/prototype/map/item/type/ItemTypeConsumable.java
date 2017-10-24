package uk.co.quarklike.prototype.map.item.type;

import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.EntityLiving;

public class ItemTypeConsumable extends ItemType {
	private int health, mana, stamina, warmth;

	public ItemTypeConsumable(String text, int health, int mana, int stamina, int warmth) {
		this.useText = text;
		this.useable = true;
		this.health = health;
		this.mana = mana;
		this.stamina = stamina;
		this.warmth = warmth;
	}

	@Override
	public void use(Map map, EntityLiving user) {
		user.addHealth(health);	
	}
}
