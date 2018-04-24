package uk.co.quarklike.src;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class RenderEngine {
	private int _windowWidth, _windowHeight;
	private String _windowTitle;
	private boolean _fullscreen;

	public RenderEngine(int width, int height, String title, boolean fullscreen) {
		_windowWidth = width;
		_windowHeight = height;
		_windowTitle = title;
		_fullscreen = fullscreen;
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
	}
}
