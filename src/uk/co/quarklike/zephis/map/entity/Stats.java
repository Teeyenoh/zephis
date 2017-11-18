package uk.co.quarklike.zephis.map.entity;

import org.lwjgl.Sys;

import uk.co.quarklike.zephis.Main;
import uk.co.quarklike.zephis.Util;

public class Stats {
	private EntityLiving parent;
	private short currentHealth, currentMana, currentStamina;
	private short healthRegen, manaRegen, staminaRegen;
	private byte hunger, tiredness, warmth;
	private long lastTime;

	private short level;
	private byte strength, dexterity, constitution, intelligence, wisdom, charisma;

	public Stats(EntityLiving parent, short level, byte st_str, byte st_dex, byte st_con, byte st_int, byte st_wis, byte st_cha, short health, short mana, short stamina, byte hunger, byte tiredness, byte warmth) {
		this.parent = parent;
		this.level = level;
		this.strength = st_str;
		this.dexterity = st_dex;
		this.constitution = st_con;
		this.intelligence = st_int;
		this.wisdom = st_wis;
		this.charisma = st_cha;
		this.currentHealth = health;
		this.hunger = hunger;
		this.currentMana = mana;
		this.tiredness = tiredness;
		this.currentStamina = stamina;
		this.warmth = warmth;
		this.healthRegen = this.manaRegen = this.staminaRegen = 1;
		lastTime = Sys.getTime();
	}

	public void generateStats() {
		strength = rollStat();
		dexterity = rollStat();
		constitution = rollStat();
		intelligence = rollStat();
		wisdom = rollStat();
		charisma = rollStat();
	}

	private byte rollStat() {
		byte stat = 0;
		byte lowest = (byte) (Main.instance.getRand().nextInt(6) + 1);
		stat += lowest;

		for (int i = 1; i < 4; i++) {
			byte roll = (byte) (Main.instance.getRand().nextInt(6) + 1);
			if (roll < lowest)
				lowest = roll;
			stat += roll;
		}

		return (byte) (stat - lowest);
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
		return (short) (4 + getConMod());
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
		return (short) (4 + getIntMod());
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
		return (short) (4 + getDexMod());
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

	public short getLevel() {
		return level;
	}

	public byte getStr() {
		return strength;
	}

	public byte getStrMod() {
		return (byte) (Math.floor((float) (strength - 10) / 2) + Math.floor(level / 2));
	}

	public byte getDex() {
		return dexterity;
	}

	public byte getDexMod() {
		return (byte) (Math.floor((float) (dexterity - 10) / 2) + Math.floor(level / 2));
	}

	public byte getCon() {
		return constitution;
	}

	public byte getConMod() {
		return (byte) (Math.floor((float) (constitution - 10) / 2) + Math.floor(level / 2));
	}

	public byte getInt() {
		return intelligence;
	}

	public byte getIntMod() {
		return (byte) (Math.floor((float) (intelligence - 10) / 2) + Math.floor(level / 2));
	}

	public byte getWis() {
		return wisdom;
	}

	public byte getWisMod() {
		return (byte) (Math.floor((float) (wisdom - 10) / 2) + Math.floor(level / 2));
	}

	public byte getCha() {
		return charisma;
	}

	public byte getChaMod() {
		return (byte) (Math.floor((float) (charisma - 10) / 2) + Math.floor(level / 2));
	}
}
