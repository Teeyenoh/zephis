package uk.co.quarklike.zephis.src.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

import uk.co.quarklike.zephis.src.Translation;
import uk.co.quarklike.zephis.src.graphics.particle.Particle;
import uk.co.quarklike.zephis.src.map.Map;
import uk.co.quarklike.zephis.src.map.Terrain;
import uk.co.quarklike.zephis.src.map.entity.Entity;
import uk.co.quarklike.zephis.src.map.entity.Skills;
import uk.co.quarklike.zephis.src.map.item.Item;

public class RenderEngine {
	public static final int STATE_GAME = 0;
	public static final int STATE_STATS = 1;

	private static final int _TILE_SIZE = 32;

	private int _windowWidth, _windowHeight;
	private int _windowWidth32, _windowHeight32;
	private String _windowTitle;
	private boolean _fullscreen;

	private ArrayList<Particle> _particles, _toRemove;

	private TextureManager _textureManager;
	private FontManager _fontManager;

	private UnicodeFont _font;

	public RenderEngine(int width, int height, String title, boolean fullscreen) {
		_windowWidth = width;
		_windowHeight = height;

		_windowWidth32 = 32 * (int) (width / 32);
		_windowHeight32 = 32 * (int) (height / 32);

		_windowTitle = title;
		_fullscreen = fullscreen;

		_particles = new ArrayList<Particle>();
		_toRemove = new ArrayList<Particle>();

		_textureManager = new TextureManager();
		_fontManager = new FontManager();
	}

	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(_windowWidth, _windowHeight));
			Display.setTitle(_windowTitle);
			Display.setFullscreen(_fullscreen);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-_windowWidth / 2, _windowWidth / 2, _windowHeight / 2, -_windowHeight / 2, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		_font = _fontManager.loadFont("Zephis");
		_font.drawString(0, 0, "Loading...");
	}

	public void renderPlaying(Map map, Entity camera) {
		prepareRender();
		drawGame(map, camera);
		endRender();
	}

	public void renderStats(Entity camera, MenuPosition position) {
		prepareRender();
		drawStats(camera, position);
		endRender();
	}

	public void renderInventory(Entity camera, MenuPosition position) {
		prepareRender();
		drawInventory(camera, position);
		endRender();
	}

	private void prepareRender() {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}

	private void endRender() {
		Display.update();
		Display.sync(60);
	}

	private void drawGame(Map map, Entity camera) {
		int cameraX = camera.getBody().getX();
		byte cameraSubX = camera.getBody().getSubX();
		int cameraY = camera.getBody().getY();
		byte cameraSubY = camera.getBody().getSubY();

		glTranslatef((-cameraX * _TILE_SIZE) - cameraSubX, (cameraY * _TILE_SIZE) + cameraSubY, 0);

		for (int i = cameraX - getCameraWidth(); i < cameraX + getCameraWidth(); i++) {
			for (int j = cameraY - getCameraHeight(); j < cameraY + getCameraHeight(); j++) {
				if (map.getTerrain(i, j) != 0) {
					drawTile(map, i, j);
				}
			}
		}

		for (Entity e : map.getEntities()) {
			if (isOnScreen(cameraX, cameraY, e.getBody().getX(), e.getBody().getY())) {
				drawEntity(e);
			}
		}

		for (Entity e : map.getEntities()) {
			if (isOnScreen(cameraX, cameraY, e.getBody().getX(), e.getBody().getY()) && !e.equals(camera)) {
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("bar_back"));
				drawQuad((e.getBody().getX() * 32) + e.getBody().getSubX(), (e.getBody().getY() * 32) + e.getBody().getSubY() + 40, 32, 4);
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("health"));
				drawQuad((e.getBody().getX() * 32) + e.getBody().getSubX(), (e.getBody().getY() * 32) + e.getBody().getSubY() + 40, (int) (30 * (e.getBody().getHealthPercent())), 2);
				glBindTexture(GL_TEXTURE_2D, 0);
			}
		}

		for (Particle p : _particles) {
			p.update();
			drawParticle(p);

			if (p.isDead())
				_toRemove.add(p);
		}

		_particles.removeAll(_toRemove);
		_toRemove.clear();

		glTranslatef((cameraX * _TILE_SIZE) + cameraSubX, (-cameraY * _TILE_SIZE) - cameraSubY, 0);
	}

	private void drawStats(Entity camera, MenuPosition position) {
		drawStatMenu(camera, position);
		drawHealthMenu(camera);
		drawRepMenu(camera);
	}

	private void drawInventory(Entity camera, MenuPosition position) {
		drawInventoryMenu(camera, position);
		drawItemMenu(camera, position);
	}

	private void drawInventoryMenu(Entity camera, MenuPosition position) {
		int centreX = -_windowWidth32 / 4;
		int centreY = 0;
		int width = _windowWidth32 / 2;
		int height = _windowHeight32;

		int leftX = centreX - (width / 2) + 16;
		int rightX = centreX + (width / 2) - 16;
		int bottomY = centreY - (height / 2) + 16;
		int topY = centreY + (height / 2) - 16;

		drawMenu(camera, centreX, centreY, width, height);

		drawText(_font, Color.black, "<MENU_INVENTORY>", centreX, topY, ALIGN_CENTRE);

		{
			int topItem = position.getGroup().getTop();
			int itemPos = position.getGroup().getIndex();

			if (topItem > 0) {
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("arrow_up_on"));
				drawQuad(centreX, topY - (18 * 1) - 9, 16, 16);
				glBindTexture(GL_TEXTURE_2D, 0);
			}

			if (position.getGroup().getBottom() < position.getGroup().getMax()) {
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("arrow_down_on"));
				drawQuad(centreX, topY - (18 * 29) - 9, 16, 16);
				glBindTexture(GL_TEXTURE_2D, 0);
			}

			for (int i = topItem; i < Math.min(position.getGroup().getBottom(), position.getGroup().getMax()); i++) {
				int modPos = i - topItem;
				int textY = topY - (18 * 2) - (18 * modPos);

				if (i == itemPos) {
					drawHighlight(centreX, textY, width);
				}

				int itemID = (int) camera.getBody().getInventory().getItems()[i];

				drawText(_font, Color.black, Item.getItem(itemID).getName(), leftX, textY, ALIGN_LEFT);
				drawText(_font, Color.black, "" + camera.getBody().getInventory().getItemCount(itemID), rightX, textY, ALIGN_RIGHT);
			}
		}
	}

	private void drawItemMenu(Entity camera, MenuPosition position) {
		int centreX = _windowWidth32 / 4;
		int centreY = 0;
		int width = _windowWidth32 / 2;
		int height = _windowHeight32;

		int leftX = centreX - (width / 2) + 16;
		int rightX = centreX + (width / 2) - 16;
		int bottomY = centreY - (height / 2) + 16;
		int topY = centreY + (height / 2) - 16;

		drawMenu(camera, centreX, centreY, width, height);

		int itemID = (int) camera.getBody().getInventory().getItems()[position.getGroup().getIndex()];
		Item item = Item.getItem(itemID);

		{
			drawText(_font, Color.black, item.getName(), leftX, topY - 18, ALIGN_LEFT);

			int slot = item.getTextureSlot();
			float slotX = (float) (slot % 16) / 16;
			float slotY = (float) (slot / 16) / 16;

			float offs = 1f / 16;

			glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture(item.getTexture()));
			drawQuadWithTextureRegion(rightX - 64, topY - 64, 128, 128, slotX, slotY, slotX + offs, slotY + offs);
			glBindTexture(GL_TEXTURE_2D, 0);
		}
	}

	private void drawStatMenu(Entity camera, MenuPosition position) {
		int centreX = -_windowWidth32 / 4;
		int centreY = 0;
		int width = _windowWidth32 / 2;
		int height = _windowHeight32;

		int leftX = centreX - (width / 2) + 16;
		int rightX = centreX + (width / 2) - 16;
		int bottomY = centreY - (height / 2) + 16;
		int topY = centreY + (height / 2) - 16;

		drawMenu(camera, centreX, centreY, width, height);

		// Character Details
		{
			drawText(_font, Color.black, camera.getName(), centreX, topY, ALIGN_CENTRE);
			drawText(_font, Color.black, "<MENU_LEVEL> " + camera.getLevel() + " " + camera.getRace(), centreX, topY - (18 * 1), ALIGN_CENTRE);
		}

		// Abilities
		{
			if (position.getGroupIndex() == 0) {
				drawHighlight(centreX, topY - (18 * (3 + position.getGroup().getIndex())), width);
			}

			drawText(_font, Color.black, "<ABILITY_STRENGTH>", leftX, topY - (18 * 3), ALIGN_LEFT);
			drawText(_font, Color.black, "<ABILITY_AGILITY>", leftX, topY - (18 * 4), ALIGN_LEFT);
			drawText(_font, Color.black, "<ABILITY_ENDURANCE>", leftX, topY - (18 * 5), ALIGN_LEFT);
			drawText(_font, Color.black, "<ABILITY_INTELLIGENCE>", leftX, topY - (18 * 6), ALIGN_LEFT);
			drawText(_font, Color.black, "<ABILITY_FAITH>", leftX, topY - (18 * 7), ALIGN_LEFT);
			drawText(_font, Color.black, "<ABILITY_PERSONALITY>", leftX, topY - (18 * 8), ALIGN_LEFT);

			drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getAbility(0)), rightX, topY - (18 * 3), ALIGN_RIGHT);
			drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getAbility(1)), rightX, topY - (18 * 4), ALIGN_RIGHT);
			drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getAbility(2)), rightX, topY - (18 * 5), ALIGN_RIGHT);
			drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getAbility(3)), rightX, topY - (18 * 6), ALIGN_RIGHT);
			drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getAbility(4)), rightX, topY - (18 * 7), ALIGN_RIGHT);
			drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getAbility(5)), rightX, topY - (18 * 8), ALIGN_RIGHT);

			int topSkill = position.getGroup(1).getTop();
			int skillPos = position.getGroup(1).getIndex();

			if (topSkill > 0) {
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("arrow_up_on"));
				drawQuad(centreX, topY - (18 * 9) - 9, 16, 16);
				glBindTexture(GL_TEXTURE_2D, 0);
			}

			if (position.getGroup(1).getBottom() < position.getGroup(1).getMax()) {
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("arrow_down_on"));
				drawQuad(centreX, topY - (18 * 29) - 9, 16, 16);
				glBindTexture(GL_TEXTURE_2D, 0);
			}

			int[] skillOrder = camera.getMind().getSkills().getSkillOrder();

			for (int i = topSkill; i < Math.min(position.getGroup(1).getBottom(), Skills.SKILL_COUNT); i++) {
				int modPos = i - topSkill;
				int textY = topY - (18 * 10) - (18 * modPos);

				if (i == skillPos && position.getGroupIndex() == 1) {
					drawHighlight(centreX, textY, width);
				}

				int skill = skillOrder[i];

				drawText(_font, Color.black, Skills.getSkillName(skill), leftX, textY, ALIGN_LEFT);
				drawText(_font, Color.black, String.valueOf(camera.getMind().getSkills().getSkill(skill)), rightX, textY, ALIGN_RIGHT);
			}
		}
	}

	private void drawHighlight(int x, int y, int width) {
		float tBottom = 24f / 32;
		float tX1 = 16f / 64;
		float tX2 = 32f / 64;
		float tX3 = 48f / 64;

		glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("gui_highlight"));
		glColor4f(1, 1, 1, 0.3f);
		drawQuadWithTextureRegion(x, y - 8, width - 48, 24, tX1, 0, tX2, tBottom); // Middle
		drawQuadWithTextureRegion(x - (width / 2) + 16, y - 8, 16, 24, 0, 0, tX1, tBottom); // Left
		drawQuadWithTextureRegion(x + (width / 2) - 16, y - 8, 16, 24, tX2, 0, tX3, tBottom); // Right
		glColor4f(1, 1, 1, 1);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private void drawHealthMenu(Entity camera) {
		int centreX = _windowWidth32 / 4;
		int centreY = _windowHeight32 / 4;
		int width = _windowWidth32 / 2;
		int height = _windowHeight32 / 2;

		drawMenu(camera, centreX, centreY, width, height);
	}

	private void drawRepMenu(Entity camera) {
		int centreX = _windowWidth32 / 4;
		int centreY = -_windowHeight32 / 4;
		int width = _windowWidth32 / 2;
		int height = _windowHeight32 / 2;

		drawMenu(camera, centreX, centreY, width, height);
	}

	private void drawMenu(Entity camera, int x, int y, int width, int height) {
		glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("guiskin1"));

		int middleWidth = width - 64;
		int middleHeight = height - 64;
		int leftX = x - (width / 2) + 16;
		int rightX = x + (width / 2) - 16;
		int bottomY = y - (height / 2) + 16;
		int topY = y + (height / 2) - 16;

		repeatQuad(x, topY, middleWidth, 32, 0.25f, 0, 0.50f, 0.25f, 32, 32); // Top
		repeatQuad(leftX, y, 32, middleHeight, 0, 0.25f, 0.25f, 0.50f, 32, 32); // Left
		repeatQuad(rightX, y, 32, middleHeight, 0.50f, 0.25f, 0.75f, 0.50f, 32, 32); // Right
		repeatQuad(x, bottomY, middleWidth, 32, 0.25f, 0.50f, 0.50f, 0.75f, 32, 32); // Bottom

		drawQuadWithTextureRegion(leftX, topY, 32, 32, 0, 0, 0.25f, 0.25f); // Top Left
		drawQuadWithTextureRegion(rightX, topY, 32, 32, 0.50f, 0, 0.75f, 0.25f); // Top Right
		drawQuadWithTextureRegion(leftX, bottomY, 32, 32, 0, 0.50f, 0.25f, 0.75f); // Bottom Left
		drawQuadWithTextureRegion(rightX, bottomY, 32, 32, 0.50f, 0.50f, 0.75f, 0.75f); // Bottom Right

		drawQuadWithTextureRegion(x, y, middleWidth, middleHeight, 0.25f, 0.25f, 0.50f, 0.50f); // Middle
	}

	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTRE = 1;
	public static final int ALIGN_RIGHT = 2;

	private void drawText(UnicodeFont font, Color c, String s, int x, int y, int align) {
		String translated = "";
		boolean done = false;
		while (!done) {
			int index = s.indexOf('<');
			if (index == -1) {
				translated += s;
				done = true;
			} else {
				translated = s.substring(0, index);
				s = s.substring(index);
				int endIndex = s.indexOf('>');
				translated += Translation.translate(s.substring(1, endIndex));
				s = s.substring(endIndex + 1);
			}
		}

		s = translated;

		y *= -1;

		switch (align) {
		case ALIGN_LEFT:
			break;
		case ALIGN_CENTRE:
			x -= font.getWidth(s) / 2;
			break;
		case ALIGN_RIGHT:
			x -= font.getWidth(s);
			break;
		}

		font.drawString(x + 1, y + 1, s, Color.black);
		font.drawString(x, y, s, c);
		Color.white.bind();
	}

	private void drawTile(Map map, int x, int y) {
		glBindTexture(GL_TEXTURE_2D, Terrain.getTerrain(map.getTerrain(x, y)).getTextureID());
		drawQuad(x * _TILE_SIZE, y * _TILE_SIZE, _TILE_SIZE, _TILE_SIZE);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private void drawEntity(Entity e) {
		TextureCell texture = e.getBody().getTexture();

		texture.bind(_textureManager);
		drawQuadWithTextureCell(e.getBody().getX() * _TILE_SIZE + e.getBody().getSubX(), e.getBody().getY() * _TILE_SIZE + e.getBody().getSubY() + 20, _TILE_SIZE, _TILE_SIZE * 2, texture);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private void drawParticle(Particle p) {
		glBindTexture(GL_TEXTURE_2D, p.getTextureID());
		drawQuad(p.getX(), p.getY(), p.getWidth(), p.getHeight());
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private void drawRotatedQuad(int x, int y, int width, int height, int rotate) {
		glTranslatef(x, -y, 0);

		switch (rotate) {
		case 0:
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(-width / 2, -height / 2);
			glTexCoord2f(1, 0);
			glVertex2f(width / 2, -height / 2);
			glTexCoord2f(1, 1);
			glVertex2f(width / 2, height / 2);
			glTexCoord2f(0, 1);
			glVertex2f(-width / 2, height / 2);
			glEnd();
			break;
		case 1:
			glBegin(GL_QUADS);
			glTexCoord2f(0, 1);
			glVertex2f(-width / 2, -height / 2);
			glTexCoord2f(0, 0);
			glVertex2f(width / 2, -height / 2);
			glTexCoord2f(1, 0);
			glVertex2f(width / 2, height / 2);
			glTexCoord2f(1, 1);
			glVertex2f(-width / 2, height / 2);
			glEnd();
			break;
		case 2:
			glBegin(GL_QUADS);
			glTexCoord2f(1, 1);
			glVertex2f(-width / 2, -height / 2);
			glTexCoord2f(0, 1);
			glVertex2f(width / 2, -height / 2);
			glTexCoord2f(0, 0);
			glVertex2f(width / 2, height / 2);
			glTexCoord2f(1, 0);
			glVertex2f(-width / 2, height / 2);
			glEnd();
			break;
		case 3:
			glBegin(GL_QUADS);
			glTexCoord2f(1, 0);
			glVertex2f(-width / 2, -height / 2);
			glTexCoord2f(1, 1);
			glVertex2f(width / 2, -height / 2);
			glTexCoord2f(0, 1);
			glVertex2f(width / 2, height / 2);
			glTexCoord2f(0, 0);
			glVertex2f(-width / 2, height / 2);
			glEnd();
			break;
		}

		glTranslatef(-x, y, 0);
	}

	private void drawQuad(int x, int y, int width, int height) {
		drawQuadWithTextureRegion(x, y, width, height, 0, 0, 1, 1);
	}

	private void repeatQuad(int x, int y, int width, int height, float x1, float y1, float x2, float y2, int repeatWidth, int repeatHeight) {
		int repeatX = (int) Math.ceil((float) width / repeatWidth);
		int repeatY = (int) Math.ceil((float) height / repeatHeight);
		int startX = x - (((repeatWidth * repeatX) / 2) + (repeatWidth / 2)) + repeatWidth;
		int startY = y - (((repeatHeight * repeatY) / 2) + (repeatHeight / 2)) + repeatHeight;

		for (int i = 0; i < repeatX; i++) {
			for (int j = 0; j < repeatY; j++) {
				drawQuadWithTextureRegion(startX + (i * repeatWidth), startY + (j * repeatHeight), repeatWidth, repeatHeight, x1, y1, x2, y2);
			}
		}
	}

	private void drawQuadWithTextureCell(int x, int y, int width, int height, TextureCell cell) {
		drawQuadWithTextureRegion(x, y, width, height, cell.getXCoord1(), cell.getYCoord1(), cell.getXCoord2(), cell.getYCoord2());
	}

	private void drawQuadWithTextureRegion(int x, int y, int width, int height, float x1, float y1, float x2, float y2) {
		glTranslatef(x, -y, 0);

		glBegin(GL_QUADS);
		glTexCoord2f(x1, y1);
		glVertex2f(-width / 2, -height / 2);
		glTexCoord2f(x2, y1);
		glVertex2f(width / 2, -height / 2);
		glTexCoord2f(x2, y2);
		glVertex2f(width / 2, height / 2);
		glTexCoord2f(x1, y2);
		glVertex2f(-width / 2, height / 2);
		glEnd();

		glTranslatef(-x, y, 0);
	}

	private boolean isOnScreen(int camX, int camY, int x, int y) {
		return x >= camX - getCameraWidth() && x <= camX + getCameraWidth() && y >= camY - getCameraHeight() && y <= camY + getCameraHeight();
	}

	private int getCameraWidth() {
		return ((_windowWidth / _TILE_SIZE) / 2) + 2; // 14
	}

	private int getCameraHeight() {
		return ((_windowHeight / _TILE_SIZE) / 2) + 2; // 11
	}

	public TextureManager getTextureManager() {
		return _textureManager;
	}
}
