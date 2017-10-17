package uk.co.quarklike.prototype.engine;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import uk.co.quarklike.prototype.Log;
import uk.co.quarklike.prototype.Main;
import uk.co.quarklike.prototype.map.Entity;
import uk.co.quarklike.prototype.map.Map;

public class GraphicsManager implements Manager {
	private ContentHub contentHub;
	private RenderEngine renderEngine;
	private int width, height;

	@Override
	public void preInit(ContentHub contentHub) {
		this.contentHub = contentHub;
	}

	@Override
	public void init() {
		width = contentHub.getWindowWidth();
		height = contentHub.getWindowHeight();

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(Main.TITLE);
			Display.create();
		} catch (LWJGLException e) {
			Log.error("Failed to create display object", e);
		}

		renderEngine = new RenderEngine();
	}

	@Override
	public void postInit() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-width / 2, width / 2, height / 2, -height / 2, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void update() {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		if (Display.isCloseRequested())
			Main.instance.stop();

		Map map = contentHub.getMapToDraw();
		if (map != null)
			drawMap(map, contentHub.getCamera().getX(), contentHub.getCamera().getY(), contentHub.getCamera().getSubX(), contentHub.getCamera().getSubY());

		int offs = 48;
		int startY = (height / 2) - 32 - offs;

		renderEngine.drawQuad(0, startY, 36, 36, 1, 0, 0);

		for (int i = contentHub.slot - 3; i < contentHub.slot + 4; i++) {
			for (int j = contentHub.texture - 1; j < contentHub.texture + 2; j++) {
				Texture t = contentHub.getResources().getTexture(j);
				renderEngine.drawQuad(0 + (offs * (i - contentHub.slot)), startY - (offs * (j - contentHub.texture)), 32, 32, t.getTextureWidth() / 32, i, t.getTextureID());
			}
		}

		Display.update();
		Display.sync(60);

	}

	private void drawMap(Map map, int camX, int camY, int subX, int subY) {
		glTranslatef(-camX * 32 - subX, -camY * 32 - subY, 0);

		drawLayer(map, camX, camY, 0);
		drawLayer(map, camX, camY, 1);
		drawLayer(map, camX, camY, 2);

		for (int i = camY - (height / 64) - 2; i < camY + (height / 64) + 2; i++) {
			drawEntities(map, camX, camY, i);
			drawRow(map, camX, camY, 3, i);
			drawRow(map, camX, camY, 4, i);
			drawRow(map, camX, camY, 5, i);
		}

		drawLayer(map, camX, camY, 6);
		drawLayer(map, camX, camY, 7);
		drawLayer(map, camX, camY, 8);

		if (Main.DEBUG)
			drawColliders(map, camX, camY);

		glTranslatef(camX * 32 + subX, camY * 32 + subY, 0);
	}

	private void drawLayer(Map map, int camX, int camY, int layer) {
		for (int i = camX - (width / 64) - 2; i < camX + (width / 64) + 2; i++) {
			for (int j = camY - (height / 64) - 2; j < camY + (height / 64) + 2; j++) {
				int tile = map.getTile(i, j, layer) - 1;
				if (tile != -1) {
					int texture = map.getTexture(i, j, layer);
					Texture t = contentHub.getResources().getTexture(texture);
					renderEngine.drawQuad(i * 32, j * 32, 32, 32, t.getTextureWidth() / 32, tile, t.getTextureID());
				}
			}
		}
	}

	private void drawRow(Map map, int camX, int camY, int layer, int row) {
		for (int i = camX - (width / 64) - 2; i < camX + (width / 64) + 2; i++) {
			int tile = map.getTile(i, row, layer) - 1;
			if (tile != -1) {
				int texture = map.getTexture(i, row, layer);
				Texture t = contentHub.getResources().getTexture(texture);
				renderEngine.drawQuad(i * 32, row * 32, 32, 32, t.getTextureWidth() / 32, tile, t.getTextureID());
			}
		}
	}

	private void drawColliders(Map map, int camX, int camY) {
		for (int i = camX - (width / 64) - 2; i < camX + (width / 64) + 2; i++) {
			for (int j = camY - (height / 64) - 2; j < camY + (height / 64) + 2; j++) {
				for (byte k = 0; k < 4; k++) {
					if (map.getCollision(i, j, k)) {
						Texture t = contentHub.getResources().getTexture("tiles/colliders.png");
						renderEngine.drawQuad(i * 32, j * 32, 32, 32, t.getTextureWidth() / 32, k, t.getTextureID());
					}
				}
			}
		}
	}

	private void drawEntities(Map map, int camX, int camY, int row) {
		glColor3f(1.0f, 0.0f, 1.0f);

		for (Entity e : map.getEntities()) {
			int x = e.getX();
			int y = e.getY();
			if (y == row)
				if (x >= camX - ((width / 64) - 2) && x <= camX + ((width / 64) + 2) && y >= camY - ((height / 64) - 2) && y <= camY + ((height / 64) + 2)) {
					int slot = e.getTextureSlot() - 1;
					if (slot != -1) {
						Texture t = contentHub.getResources().getTexture(e.getTexture());
						renderEngine.drawQuad((e.getX() * 32) + e.getSubX(), (e.getY() * 32) + e.getSubY() - 12, 32, 32, t.getTextureWidth() / 32, slot, t.getTextureID());
					}
				}
		}

		glColor3f(1.0f, 1.0f, 1.0f);
	}

	@Override
	public void deinit() {
		Display.destroy();
	}

	@Override
	public String getName() {
		return "Graphics Manager";
	}

}
