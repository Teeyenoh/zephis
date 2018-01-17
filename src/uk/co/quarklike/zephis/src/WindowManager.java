package uk.co.quarklike.zephis.src;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class WindowManager {
	public void createWindow(int width, int height, String title) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void updateWindow() {
		if (Display.isCloseRequested())
			Main.instance.stop();

		Display.update();
	}

	public void destroyWindow() {
		Display.destroy();
	}
}
