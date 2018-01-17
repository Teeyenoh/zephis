package uk.co.quarklike.zephis.src;

import static org.lwjgl.opengl.GL11.*;

public class RenderEngine {
	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-Main.WINDOW_WIDTH / 2, Main.WINDOW_WIDTH / 2, Main.WINDOW_HEIGHT / 2, -Main.WINDOW_HEIGHT / 2, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public void render(Map map) {
		glClear(GL_COLOR_BUFFER_BIT);

		for (short i = 0; i < map.getWidth(); i++) {
			for (short j = 0; j < map.getHeight(); j++) {
				for (byte k = 0; k < 3; k++) {
					drawTile(i, j, map.getTileTexture(i, j, k), map.getTileIndex(i, j, k));
				}
			}
		}
	}

	public void drawTile(int x, int y, int tileTexture, int tileIndex) {
		drawQuad(x * 32, y * 32, 32, 32, tileTexture, 512, 512, tileIndex);
	}

	public void drawQuad(int x, int y, int width, int height, int textureID, int textureWidth, int textureHeight, int textureSlot) {
		float xOffs = width / textureWidth;
		float yOffs = height / textureHeight;

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
}
