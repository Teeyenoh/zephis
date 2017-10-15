package uk.co.quarklike.prototype.engine;

import static org.lwjgl.opengl.GL11.*;

public class RenderEngine {
	public void drawQuad(int x, int y, int width, int height, int textureWidth, int textureSlot, int texture) {
		float d = 1f / textureWidth;
		int tX = textureSlot % textureWidth;
		int tY = textureSlot / textureWidth;
		float x1 = d * tX;
		float x2 = x1 + d;
		float y1 = d * tY;
		float y2 = y1 + d;

		drawQuad(x, y, width, height, x1, y1, x2, y1, x2, y2, x1, y2, texture);
	}

	public void drawQuad(int x, int y, int width, int height, float texX1, float texY1, float texX2, float texY2, float texX3, float texY3, float texX4, float texY4, int texture) {
		int x1 = x - (width / 2);
		int x2 = x + (width / 2);
		int y1 = y - (height / 2);
		int y2 = y + (height / 2);

		drawQuad(x1, y1, x2, y1, x2, y2, x1, y2, texX1, texY1, texX2, texY2, texX3, texY3, texX4, texY4, texture);
	}

	public void drawQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, float texX1, float texY1, float texX2, float texY2, float texX3, float texY3, float texX4, float texY4, int texture) {
		glBindTexture(GL_TEXTURE_2D, texture);

		glBegin(GL_QUADS);

		glTexCoord2f(texX1, texY1);
		glVertex2f(x1, y1);
		glTexCoord2f(texX2, texY2);
		glVertex2f(x2, y2);
		glTexCoord2f(texX3, texY3);
		glVertex2f(x3, y3);
		glTexCoord2f(texX4, texY4);
		glVertex2f(x4, y4);

		glEnd();
	}
}
