package uk.co.quarklike.zephis.src;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class RenderEngine {
	private HashMap<Byte, String> _terrainTextures = new HashMap<Byte, String>();

	private HashMap<String, Integer> _textureIDs = new HashMap<String, Integer>();

	private UnicodeFont _fontDefault;

	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-Main.WINDOW_WIDTH / 2, Main.WINDOW_WIDTH / 2, Main.WINDOW_HEIGHT / 2, -Main.WINDOW_HEIGHT / 2, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		try {
			_fontDefault = new UnicodeFont(new Font("res/fonts/default.ttf", Font.PLAIN, 16));
			_fontDefault.addAsciiGlyphs();
			_fontDefault.getEffects().add(new ColorEffect(Color.WHITE));
			_fontDefault.loadGlyphs();
			drawText(0, 0, "Loading...", _fontDefault, ALIGN_CENTRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(Map map, long camera, boolean highlight) {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		Entity camEntity = map.getEntity(camera);

		glTranslatef(-(camEntity.getX() * 32) - camEntity.getSubX(), -(camEntity.getY() * 32) - camEntity.getSubY(), 0);

		for (short i = 0; i < map.getWidth(); i++) {
			for (short j = 0; j < map.getHeight(); j++) {
				for (byte k = 0; k < 3; k++) {
					drawTile(i, j, map.getTileTexture(i, j, k), map.getTileIndex(i, j, k));
				}
			}
		}

		for (Entity e : map.getEntities()) {
			drawEntity(e, highlight);
		}

		glTranslatef((camEntity.getX() * 32) + camEntity.getSubX(), (camEntity.getY() * 32) + camEntity.getSubY(), 0);
	}

	public void clearMapTextures() {
		for (String texture : _terrainTextures.values()) {
			if (_textureIDs.containsKey(texture)) {
				glDeleteTextures(_textureIDs.get(texture));
				_textureIDs.remove(texture);
			}
		}
	}

	public void loadMapTextures(Map map) {
		for (short i = 0; i < map.getWidth(); i++) {
			for (short j = 0; j < map.getHeight(); j++) {
				for (byte k = 0; k < 3; k++) {
					this.loadTerrainTexture(map.getTileTexture(i, j, k));
				}
			}
		}
	}

	public void drawTile(short x, short y, byte tileTexture, byte tileIndex) {
		if (tileTexture == 0)
			return;
		drawQuad(x * 32, y * 32, 32, 32, getTerrainID(tileTexture), 512, 512, tileIndex);
	}

	public void drawEntity(Entity e, boolean highlight) {
		if (highlight)
			drawQuad((e.getX() * 32), (e.getY() * 32), 32, 32, getTextureID("player_select"), 32, 32, 0);

		drawQuad((e.getX() * 32) + e.getSubX(), (e.getY() * 32) + e.getSubY() - 12, 32, 32, getTextureID("terrain_grass"), 512, 512, 0);

		if (highlight) {
			glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			drawQuad((e.getX() * 32), (e.getY() * 32), 32, 32, getTextureID("player_select"), 32, 32, 0);
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	private static final int MENU_ACTION_WIDTH = 192;
	private static final int MENU_ACTION_HEIGHT = 256;

	public void drawActionMenu(byte index) {
		int leftX = (-Main.WINDOW_WIDTH / 2) + 4;
		int centreX = (-Main.WINDOW_WIDTH / 2) + (MENU_ACTION_WIDTH / 2);
		int topY = (Main.WINDOW_HEIGHT / 2) - (MENU_ACTION_HEIGHT) + 4;
		int centreY = (Main.WINDOW_HEIGHT / 2) - (MENU_ACTION_HEIGHT / 2);

		drawQuad(centreX, centreY, MENU_ACTION_WIDTH, MENU_ACTION_HEIGHT, getTextureID("menu_action"), 256, 256, 0);

		drawText(centreX, topY, "Actions", _fontDefault, ALIGN_CENTRE);
	}

	public void drawQuad(int x, int y, int width, int height, int textureID, int textureWidth, int textureHeight, int textureSlot) {
		float xOffs = (float) width / textureWidth;
		float yOffs = (float) height / textureHeight;

		int texturesWide = textureWidth / width;

		int slotX = textureSlot % texturesWide;
		int slotY = textureSlot / texturesWide;

		float tx = xOffs * slotX;
		float ty = yOffs * slotY;

		glBindTexture(GL_TEXTURE_2D, textureID);

		glTranslatef(x, y, 0);

		glBegin(GL_QUADS);
		glTexCoord2f(tx, ty);
		glVertex2f(-width / 2, -height / 2);
		glTexCoord2f(tx + xOffs, ty);
		glVertex2f(width / 2, -height / 2);
		glTexCoord2f(tx + xOffs, ty + yOffs);
		glVertex2f(width / 2, height / 2);
		glTexCoord2f(tx, ty + yOffs);
		glVertex2f(-width / 2, height / 2);
		glEnd();

		glTranslatef(-x, -y, 0);
	}

	private static final int ALIGN_LEFT = 0;
	private static final int ALIGN_CENTRE = 1;
	private static final int ALIGN_RIGHT = 2;

	private void drawText(int x, int y, String text, UnicodeFont font, int alignment) {
		switch (alignment) {
		case ALIGN_LEFT:
			break;
		case ALIGN_CENTRE:
			x -= (font.getWidth(text) / 2);
			break;
		case ALIGN_RIGHT:
			x -= font.getWidth(text);
			break;
		}

		font.drawString(x, y, text);
	}

	private void loadTerrainTexture(byte id) {
		loadTexture(_terrainTextures.get(id));
	}

	private int getTerrainID(byte id) {
		return _textureIDs.get(_terrainTextures.get(id));
	}

	private int getTextureID(String path) {
		if (!_textureIDs.containsKey(path)) {
			loadTexture(path);
		}

		return _textureIDs.get(path);
	}

	private void loadTexture(String path) {
		if (_textureIDs.containsKey(path))
			return;

		try {
			_textureIDs.put(path, TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/textures/" + path + ".png"), GL_NEAREST).getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load texture: " + path);
		}
	}

	public void loadTextureList(String path) {
		Properties list = new Properties();

		try {
			list.load(ResourceLoader.getResourceAsStream("res/textures/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object o : list.keySet()) {
			String s = (String) o;
			_terrainTextures.put(Byte.parseByte(s), list.getProperty(s));
		}
	}
}
