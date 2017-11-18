package uk.co.quarklike.zephis.engine.gui.windows;

import org.newdawn.slick.Color;

import uk.co.quarklike.zephis.engine.ContentHub;
import uk.co.quarklike.zephis.engine.GraphicsManager;
import uk.co.quarklike.zephis.engine.gui.GUIImage;
import uk.co.quarklike.zephis.engine.gui.GUIText;

public class GUICharCreation extends GUIWindow {
	private GUIText name;
	private GUIText gender;
	private GUIText race;
	private GUIText charClass;
	private GUIText points;

	private GUIText[] tags;
	private GUIText[] scores, mods;

	private static final int FIELD_WIDTH = 96;

	public GUICharCreation(ContentHub contentHub) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;
		tags = new GUIText[11];
		scores = new GUIText[6];
		mods = new GUIText[6];

		init();
	}

	@Override
	public void init() {
		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
		comps.add(new GUIText(x, nearY, GraphicsManager.titleFont, Color.white, "GUI_WINDOW_CREATION_TITLE", GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));

		int offs = GraphicsManager.defaultFont.getLineHeight() + 6;
		int fieldX = nearX + 64;

		comps.add(tags[0] = new GUIText(nearX, nearY + offs, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CREATION_FIELD_NAME", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(name = new GUIText(fieldX, nearY + offs, GraphicsManager.defaultFont, Color.white, "", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));

		comps.add(tags[1] = new GUIText(nearX, nearY + (offs * 2), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CREATION_FIELD_GENDER", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(fieldX, nearY + (offs * 2), GraphicsManager.defaultFont, Color.white, "<--", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(gender = new GUIText(fieldX + FIELD_WIDTH, nearY + (offs * 2), GraphicsManager.defaultFont, Color.white, "", GUIText.ALIGN_CENTRE, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(fieldX + (FIELD_WIDTH * 2), nearY + (offs * 2), GraphicsManager.defaultFont, Color.white, "-->", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));

		comps.add(tags[2] = new GUIText(nearX, nearY + (offs * 3), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CREATION_FIELD_RACE", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(fieldX, nearY + (offs * 3), GraphicsManager.defaultFont, Color.white, "<--", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(race = new GUIText(fieldX + FIELD_WIDTH, nearY + (offs * 3), GraphicsManager.defaultFont, Color.white, "", GUIText.ALIGN_CENTRE, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(fieldX + (FIELD_WIDTH * 2), nearY + (offs * 3), GraphicsManager.defaultFont, Color.white, "-->", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));

		comps.add(tags[3] = new GUIText(nearX, nearY + (offs * 4), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CREATION_FIELD_CLASS", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(fieldX, nearY + (offs * 4), GraphicsManager.defaultFont, Color.white, "<--", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(charClass = new GUIText(fieldX + FIELD_WIDTH, nearY + (offs * 4), GraphicsManager.defaultFont, Color.white, "", GUIText.ALIGN_CENTRE, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(fieldX + (FIELD_WIDTH * 2), nearY + (offs * 4), GraphicsManager.defaultFont, Color.white, "-->", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));

		comps.add(new GUIText(nearX, nearY + (offs * 6), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CREATION_POINTS", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(points = new GUIText(nearX + 128, nearY + (offs * 6), GraphicsManager.defaultFont, Color.white, "" + 27, GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));

		comps.add(tags[4] = new GUIText(nearX, nearY + (offs * 7), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_STRENGTH", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(tags[5] = new GUIText(nearX, nearY + (offs * 8), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_DEXTERITY", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(tags[6] = new GUIText(nearX, nearY + (offs * 9), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_CONSTITUTION", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(tags[7] = new GUIText(nearX, nearY + (offs * 10), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_INTELLIGENCE", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(tags[8] = new GUIText(nearX, nearY + (offs * 11), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_WISDOM", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(tags[9] = new GUIText(nearX, nearY + (offs * 12), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_CHARISMA", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));

		comps.add(scores[0] = new GUIText(nearX + 192, nearY + (offs * 7), GraphicsManager.defaultFont, Color.white, "8", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(scores[1] = new GUIText(nearX + 192, nearY + (offs * 8), GraphicsManager.defaultFont, Color.white, "8", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(scores[2] = new GUIText(nearX + 192, nearY + (offs * 9), GraphicsManager.defaultFont, Color.white, "8", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(scores[3] = new GUIText(nearX + 192, nearY + (offs * 10), GraphicsManager.defaultFont, Color.white, "8", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(scores[4] = new GUIText(nearX + 192, nearY + (offs * 11), GraphicsManager.defaultFont, Color.white, "8", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
		comps.add(scores[5] = new GUIText(nearX + 192, nearY + (offs * 12), GraphicsManager.defaultFont, Color.white, "8", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));

		comps.add(mods[0] = new GUIText(nearX + 256, nearY + (offs * 7), GraphicsManager.defaultFont, Color.white, "-1", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(mods[1] = new GUIText(nearX + 256, nearY + (offs * 8), GraphicsManager.defaultFont, Color.white, "-1", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(mods[2] = new GUIText(nearX + 256, nearY + (offs * 9), GraphicsManager.defaultFont, Color.white, "-1", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(mods[3] = new GUIText(nearX + 256, nearY + (offs * 10), GraphicsManager.defaultFont, Color.white, "-1", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(mods[4] = new GUIText(nearX + 256, nearY + (offs * 11), GraphicsManager.defaultFont, Color.white, "-1", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(mods[5] = new GUIText(nearX + 256, nearY + (offs * 12), GraphicsManager.defaultFont, Color.white, "-1", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));

		comps.add(tags[10] = new GUIText(nearX, nearY + (offs * 14), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CREATION_FIELD_FINISH", GUIText.ALIGN_LEFT, GUIText.CASE_DEFAULT));
	}

	@Override
	public void refresh() {

	}

	public void setName(String name) {
		this.name.setText(name);
	}

	public void setGender(int gender) {
		this.gender.setText(gender == 0 ? "GUI_WINDOW_CREATION_GENDER_NEUTRAL" : gender == 1 ? "GUI_WINDOW_CREATION_GENDER_MALE" : gender == 2 ? "GUI_WINDOW_CREATION_GENDER_FEMALE" : "ERROR");
	}

	public void setRace(String race) {
		this.race.setText(race);
	}

	public void setClass(String charClass) {
		this.charClass.setText(charClass);
	}

	public void setPoints(int points) {
		this.points.setText("" + points);
	}

	public void setStat(int stat, int value) {
		scores[stat].setText("" + value);
		mods[stat].setText(getModString(value));
	}

	private String getModString(int value) {
		byte mod = (byte) (Math.floor((float) (value - 10) / 2));

		if (mod < 0) {
			return "-" + Math.abs(mod);
		} else {
			return "+" + Math.abs(mod);
		}
	}

	public void setIndex(int i) {
		int nearX = x - (width / 2) + 48;

		for (GUIText t : tags) {
			if (t != null)
				t.setX(nearX);
		}

		tags[i].setX(nearX + 8);
	}
}
