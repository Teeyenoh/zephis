package uk.co.quarklike.prototype.map.entity;

import org.lwjgl.Sys;

import uk.co.quarklike.prototype.Util;

public class Stats {
	private EntityLiving parent;
	private short hardMaxHealth, hardMaxMana, hardMaxStamina;
	private short currentHealth, currentMana, currentStamina;
	private short healthRegen, manaRegen, staminaRegen;
	private byte hunger, tiredness, warmth;
	private long lastTime;

	public Stats(EntityLiving parent, short maxHealth, short health, short maxMana, short mana, short maxStamina, short stamina, byte hunger, byte tiredness, byte warmth) {
		this.parent = parent;
		this.hardMaxHealth = maxHealth;
		this.currentHealth = health;
		this.hunger = hunger;
		this.hardMaxMana = maxMana;
		this.currentMana = mana;
		this.tiredness = tiredness;
		this.hardMaxStamina = maxStamina;
		this.currentStamina = stamina;
		this.warmth = warmth;
		this.healthRegen = this.manaRegen = this.staminaRegen = 1;
		lastTime = Sys.getTime();
	}

	public void update() {
		if (Sys.getTime() - lastTime >= 1000) {
			addHealth(healthRegen);
			addMana(manaRegen);
			addStamina(staminaRegen);
			addHunger((byte) -1);
			addTiredness((byte) -1);
			lastTime = Sys.getTime();
		}

		this.currentHealth = (short) Util.clamp(this.currentHealth, 0, getSoftMaxHealth());
		this.currentMana = (short) Util.clamp(this.currentMana, 0, getSoftMaxMana());
		this.currentStamina = (short) Util.clamp(this.currentStamina, 0, getSoftMaxStamina());
	}

	public short getHardMaxHealth() {
		return hardMaxHealth;
	}

	public short getSoftMaxHealth() {
		return (short) (getHardMaxHealth() * ((float) hunger / 100));
	}

	public short getHealth() {
		return currentHealth;
	}

	public void addHealth(short health) {
		this.currentHealth = (short) Util.clamp(this.currentHealth + health, 0, getSoftMaxHealth());
	}

	public short getHardMaxMana() {
		return hardMaxMana;
	}

	public short getSoftMaxMana() {
		return (short) (getHardMaxMana() * ((float) tiredness / 100));
	}

	public short getMana() {
		return currentMana;
	}

	public void addMana(short mana) {
		this.currentMana = (short) Util.clamp(this.currentMana + mana, 0, getSoftMaxMana());
	}

	public short getHardMaxStamina() {
		return hardMaxStamina;
	}

	public short getSoftMaxStamina() {
		return (short) (getHardMaxStamina() * ((float) warmth / 100));
	}

	public short getStamina() {
		return currentStamina;
	}

	public void addStamina(short stamina) {
		this.currentStamina = (short) Util.clamp(this.currentStamina + stamina, 0, getSoftMaxStamina());
	}

	public byte getHunger() {
		return this.hunger;
	}

	public void addHunger(byte hunger) {
		this.hunger = (byte) Util.clamp(this.hunger + hunger, 0, 100);
	}

	public byte getTiredness() {
		return this.tiredness;
	}

	public void addTiredness(byte tiredness) {
		this.tiredness = (byte) Util.clamp(this.tiredness + tiredness, 0, 100);
	}

	public byte getWarmth() {
		return this.warmth;
	}

	public void addWarmth(byte warmth) {
		this.warmth = (byte) Util.clamp(this.warmth + warmth, 0, 100);
	}
}
