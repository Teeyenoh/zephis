package uk.co.quarklike.zephis.src;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String TITLE = "Zephis";

	private boolean _running;
	private Thread _thread;

	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
			Display.setTitle(TITLE);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glOrtho(-WINDOW_WIDTH / 2, WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, -WINDOW_HEIGHT / 2, -1, 1);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
	}

	public void update() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		if (Display.isCloseRequested())
			stop();

		Display.update();
	}

	public void deinit() {
		Display.destroy();
	}

	public void run() {
		init();
		while (_running)
			update();
		deinit();
	}

	public synchronized void start() {
		if (_running)
			return;
		_running = true;
		_thread = new Thread(this, TITLE);
		_thread.start();
	}

	public synchronized void stop() {
		if (!_running)
			return;
		_running = false;
		_thread.interrupt();
	}

	public static final void main(String[] args) {
		Main instance = new Main();
		instance.start();
	}
}
