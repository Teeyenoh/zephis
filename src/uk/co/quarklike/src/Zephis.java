package uk.co.quarklike.src;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Zephis implements Runnable {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String WINDOW_TITLE = "Zephis";

	private static final int _TILE_SIZE = 32;
	private static final int _CAMERA_WIDTH = ((WINDOW_WIDTH / _TILE_SIZE) / 2) + 2; // 14
	private static final int _CAMERA_HEIGHT = ((WINDOW_HEIGHT / _TILE_SIZE) / 2) + 2; // 11

	public static Zephis instance;

	private boolean _running;
	private Thread _thread;

	private Random _random;
	private RenderEngine _renderEngine;
	private TextureManager _textureManager;

	private Map _map;
	private Entity _player;

	public void run() {
		preInit();
		init();
		postInit();

		while (_running)
			update();
		deinit();
	}

	public void preInit() {
		_random = new Random();
		_renderEngine = new RenderEngine(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, true);
		_textureManager = new TextureManager();
	}

	public void init() {
		_renderEngine.init();
		
		_map = new Map(16, 16);
		_player = new Entity(_map);
	}

	public void postInit() {
		_player.setPosition(10, 10);
		(new Entity(_map)).setPosition(5, 3);
		(new Entity(_map)).setPosition(5, 4);
		(new Entity(_map)).setPosition(5, 5);
		(new Entity(_map)).setPosition(5, 6);
		(new Entity(_map)).setPosition(5, 7);
	}

	public void update() {
		if (Display.isCloseRequested())
			stop();

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			_player.move(Entity.DIR_FORWARD);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			_player.move(Entity.DIR_RIGHT);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			_player.move(Entity.DIR_BACKWARD);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			_player.move(Entity.DIR_LEFT);
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
					_player.attack();
				}
			}
		}

		_map.update();

		for (Entity e : _map.getEntities()) {
			e.update();
		}

		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();

		int cameraX = _player.getX();
		byte cameraSubX = _player.getSubX();
		int cameraY = _player.getY();
		byte cameraSubY = _player.getSubY();

		glTranslatef((-cameraX * _TILE_SIZE) - cameraSubX, (cameraY * _TILE_SIZE) + cameraSubY, 0);

		for (int i = cameraX - _CAMERA_WIDTH; i < cameraX + _CAMERA_WIDTH; i++) {
			for (int j = cameraY - _CAMERA_HEIGHT; j < cameraY + _CAMERA_HEIGHT; j++) {
				if (_map.getTerrain(i, j) != 0) {
					drawTile(i, j);
				}
			}
		}

		for (Entity e : _map.getEntities()) {
			if (isOnScreen(cameraX, cameraY, e.getX(), e.getY())) {
				glBindTexture(GL_TEXTURE_2D, _textureManager.getTexture("player"));
				drawQuad(e.getX() * _TILE_SIZE + e.getSubX(), e.getY() * _TILE_SIZE + e.getSubY() + 20, _TILE_SIZE, 48);
				glBindTexture(GL_TEXTURE_2D, 0);
			}
		}

		glTranslatef((cameraX * _TILE_SIZE) + cameraSubX, (-cameraY * _TILE_SIZE) - cameraSubY, 0);

		Display.update();
		Display.sync(60);
	}

	private void drawTile(int x, int y) {
		glBindTexture(GL_TEXTURE_2D, Terrain.getTerrain(_map.getTerrain(x, y)).getTextureID());
		drawQuad(x * _TILE_SIZE, y * _TILE_SIZE, _TILE_SIZE, _TILE_SIZE);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private void drawQuad(int x, int y, int width, int height) {
		glTranslatef(x, -y, 0);

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

		glTranslatef(-x, y, 0);
	}

	private boolean isOnScreen(int camX, int camY, int x, int y) {
		return x >= camX - _CAMERA_WIDTH && x <= camX + _CAMERA_WIDTH && y >= camY - _CAMERA_HEIGHT && y <= camY + _CAMERA_HEIGHT;
	}

	public void deinit() {
		Display.destroy();
	}

	public Random getRandom() {
		return _random;
	}

	public TextureManager getTextureManager() {
		return _textureManager;
	}

	public void start() {
		if (_running)
			return;
		_running = true;
		_thread = new Thread(this, WINDOW_TITLE);
		_thread.start();
	}

	public void stop() {
		if (!_running)
			return;
		_running = false;
		_thread.interrupt();
	}

	public static final void main(String[] args) {
		instance = new Zephis();
		instance.start();
	}
}
