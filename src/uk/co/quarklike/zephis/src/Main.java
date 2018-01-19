package uk.co.quarklike.zephis.src;

import java.util.Random;

public class Main implements Runnable {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String TITLE = "Zephis";

	public static Main instance;
	public static Random rand = new Random();

	private boolean _running;
	private Thread _thread;

	private WindowManager _mWindow;
	private GameManager _mGame;

	public void init() {
		_mWindow = new WindowManager();
		_mGame = new GameManager();

		_mWindow.createWindow(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Main.TITLE);
		_mGame.initGame();
	}

	public void update() {
		_mWindow.updateWindow();
		_mGame.updateGame();
	}

	public void deinit() {
		_mWindow.destroyWindow();
		_mGame.destroyGame();
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
		instance = new Main();
		instance.start();
	}
}
