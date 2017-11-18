package uk.co.quarklike.zephis.engine.gui.windows;

import org.newdawn.slick.Color;

import uk.co.quarklike.zephis.engine.ContentHub;
import uk.co.quarklike.zephis.engine.GraphicsManager;
import uk.co.quarklike.zephis.engine.gui.GUIImage;
import uk.co.quarklike.zephis.engine.gui.GUIText;
import uk.co.quarklike.zephis.map.entity.EntityLiving;

public class GUIStats extends GUIWindow {
	private EntityLiving player;

	public GUIStats(ContentHub contentHub, EntityLiving player) {
		this.x = 0;
		this.y = 0;
		this.width = 512;
		this.height = 512;
		this.player = player;

		init();
	}

	@Override
	public void init() {
		int nearX = x - (width / 2) + 48;
		int nearY = y - (height / 2) + 32;
		int farX = x + (width / 2) - 48;
		int farY = y + (height / 2) - 48;

		comps.add(new GUIImage(x, y, width, height, "gui/background.png"));
		comps.add(new GUIText(x, nearY, GraphicsManager.titleFont, Color.white, player.getName(), GUIText.ALIGN_CENTRE, GUIText.CASE_TITLE));

		int offs = GraphicsManager.defaultFont.getLineHeight() + 6;

		comps.add(new GUIText(nearX, nearY + 32 + offs, GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_STRENGTH", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, nearY + 32 + offs, GraphicsManager.defaultFont, Color.white, player.getStats().getStr() + " ("+ getModString(player.getStats().getStrMod())+")", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(nearX, nearY + 32 + (2 * offs), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_DEXTERITY", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, nearY + 32 + (2 * offs), GraphicsManager.defaultFont, Color.white, player.getStats().getDex() + " ("+ getModString(player.getStats().getDexMod())+")", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(nearX, nearY + 32 + (3 * offs), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_CONSTITUTION", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, nearY + 32 + (3 * offs), GraphicsManager.defaultFont, Color.white, player.getStats().getCon() + " ("+ getModString(player.getStats().getConMod())+")", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(nearX, nearY + 32 + (4 * offs), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_INTELLIGENCE", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, nearY + 32 + (4 * offs), GraphicsManager.defaultFont, Color.white, player.getStats().getInt() + " ("+ getModString(player.getStats().getIntMod())+")", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(nearX, nearY + 32 + (5 * offs), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_WISDOM", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, nearY + 32 + (5 * offs), GraphicsManager.defaultFont, Color.white, player.getStats().getWis() + " ("+ getModString(player.getStats().getWisMod())+")", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
		comps.add(new GUIText(nearX, nearY + 32 + (6 * offs), GraphicsManager.defaultFont, Color.white, "GUI_WINDOW_CHARACTER_ABILITY_CHARISMA", GUIText.ALIGN_LEFT, GUIText.CASE_TITLE));
		comps.add(new GUIText(farX, nearY + 32 + (6 * offs), GraphicsManager.defaultFont, Color.white, player.getStats().getCha() + " ("+ getModString(player.getStats().getChaMod())+")", GUIText.ALIGN_RIGHT, GUIText.CASE_DEFAULT));
	}

	private String getModString(byte mod) {
		if (mod < 0) {
			return "-" + Math.abs(mod);
		} else {
			return "+" + Math.abs(mod);
		}
	}

	@Override
	public void refresh() {

	}
}
