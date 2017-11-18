package uk.co.quarklike.zephis.map.item.type;

import uk.co.quarklike.zephis.map.Map;
import uk.co.quarklike.zephis.map.entity.EntityLiving;

public class ItemTypeConsumable extends ItemType {
	private short health, mana, stamina;
	private byte hunger, tiredness, warmth;

	public ItemTypeConsumable(String text, short health, short mana, short stamina, byte hunger, byte tiredness, byte warmth) {
		this.useText = text;
		this.useable = true;
		this.health = health;
		this.mana = mana;
		this.stamina = stamina;
		this.hunger = hunger;
		this.tiredness = tiredness;
		this.warmth = warmth;
		this.isUsed = true;
	}

	@Override
	public void use(Map map, EntityLiving user) {
		user.getStats().addHealth(health);
		user.getStats().addMana(mana);
		user.getStats().addStamina(stamina);
		user.getStats().addHunger(hunger);
		user.getStats().addTiredness(tiredness);
		user.getStats().addWarmth(warmth);
	}
}
