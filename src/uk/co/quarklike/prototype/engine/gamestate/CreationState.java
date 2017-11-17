package uk.co.quarklike.prototype.engine.gamestate;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import uk.co.quarklike.prototype.Util;
import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.windows.GUICharCreation;
import uk.co.quarklike.prototype.map.Map;
import uk.co.quarklike.prototype.map.entity.CharClass;
import uk.co.quarklike.prototype.map.entity.EntityLiving;
import uk.co.quarklike.prototype.map.entity.Race;

public class CreationState implements GameState {
	private static HashMap<Byte, Integer> costs = new HashMap<Byte, Integer>();
	{
		costs.put((byte) 8, 0);
		costs.put((byte) 9, 1);
		costs.put((byte) 10, 2);
		costs.put((byte) 11, 3);
		costs.put((byte) 12, 4);
		costs.put((byte) 13, 5);
		costs.put((byte) 14, 7);
		costs.put((byte) 15, 9);
		costs.put((byte) 16, 10000);
	}

	private ContentHub contentHub;
	private Map map;
	private GUICharCreation gui;

	private int field;

	private String name = "";
	private int nameIndex = 0;

	private int gender = 0;
	private int race = 1;
	private int charClass = 1;

	private int points;
	private byte[] stats;

	public CreationState(Map map) {
		this.map = map;
	}

	@Override
	public void init(ContentHub contentHub) {
		this.contentHub = contentHub;

		contentHub.setDrawMap(false);
		this.gui = new GUICharCreation(contentHub);

		points = 27;
		stats = new byte[6];
		for (int i = 0; i < 6; i++) {
			stats[i] = 8;
		}
	}

	private void closeMenu() {
		EntityLiving player = new EntityLiving(name, "tiles/grass.png");
		player.setStats(stats);
		player.register(map);
		contentHub.setNewState(new PlayingState(map, player));
	}

	private static final int FIELD_NAME = 0;
	private static final int FIELD_GENDER = 1;
	private static final int FIELD_RACE = 2;
	private static final int FIELD_CLASS = 3;
	private static final int FIELD_AB_START = 4;
	private static final int FIELD_AB_END = 9;
	private static final int FIELD_FINISH = 10;

	@Override
	public void update() {
		contentHub.addGUI(gui);

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (field == FIELD_NAME) {
					if (TextInput.isChar(Keyboard.getEventCharacter())) {
						name = TextInput.addChar(name, nameIndex, Keyboard.getEventCharacter());
						nameIndex = Util.clamp(nameIndex + 1, 0, 32);
					} else if (TextInput.isBack(Keyboard.getEventCharacter())) {
						name = TextInput.addChar(name, nameIndex, Keyboard.getEventCharacter());
						nameIndex = Util.clamp(nameIndex - 1, 0, 32);
					}

					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						nameIndex = Util.clamp(nameIndex - 1, 0, name.length());
					} else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						nameIndex = Util.clamp(nameIndex + 1, 0, name.length());
					}
				} else if (field == FIELD_GENDER) {
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						gender = Util.wrap(gender - 1, 0, 2);
					} else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						gender = Util.wrap(gender + 1, 0, 2);
					}
				} else if (field == FIELD_RACE) {
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						race = Util.wrap(race - 1, 1, Race.getRaceCount());
					} else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						race = Util.wrap(race + 1, 1, Race.getRaceCount());
					}
				} else if (field == FIELD_CLASS) {
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						charClass = Util.wrap(charClass - 1, 1, CharClass.getClassCount());
					} else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						charClass = Util.wrap(charClass + 1, 1, CharClass.getClassCount());
					}
				} else if (field == FIELD_FINISH) {
					if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
						this.closeMenu();
					}
				} else if (field >= FIELD_AB_START && field <= FIELD_AB_END) {
					int stat = field - FIELD_AB_START;
					points += getCost(stats[stat]);
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						stats[stat] = (byte) Util.clamp(stats[stat] - 1, 8, 15);
					} else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						if (getCost(stats[stat] + 1) <= points)
							stats[stat] = (byte) Util.clamp(stats[stat] + 1, 8, 15);
					}
					points -= getCost(stats[stat]);
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					field = Util.wrap(field - 1, 0, 10);
				} else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					field = Util.wrap(field + 1, 0, 10);
				}
			}
		}

		name = name.substring(0, name.length() < 32 ? name.length() : 32);

		gui.setIndex(field);
		gui.setName(name);
		gui.setGender(gender);
		gui.setRace(Race.getRace(race).getName());
		gui.setClass(CharClass.getCharClass(charClass).getName());
		gui.setPoints(points);

		for (int i = 0; i < 6; i++) {
			gui.setStat(i, stats[i]);
		}

		gui.refresh();
	}

	private int getCost(int value) {
		return costs.get((byte) value);
	}

	@Override
	public void deinit() {

	}
}
