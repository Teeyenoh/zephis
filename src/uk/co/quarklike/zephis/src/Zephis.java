package uk.co.quarklike.zephis.src;

import java.util.Random;

import org.lwjgl.opengl.Display;

import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.graphics.TextureManager;
import uk.co.quarklike.zephis.src.map.Map;
import uk.co.quarklike.zephis.src.map.entity.Entity;
import uk.co.quarklike.zephis.src.map.item.Item;
import uk.co.quarklike.zephis.src.state.GameState;
import uk.co.quarklike.zephis.src.state.GameStatePlaying;

public class Zephis implements Runnable {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String WINDOW_TITLE = "Zephis 0.6.0-alpha";

	public static Zephis instance;

	private boolean _running;
	private Thread _thread;

	private Random _random;
	private RenderEngine _renderEngine;

	private Map _map;
	private Entity _player;

	private GameState _currentState;

	public void run() {
		preInit();
		init();
		postInit();

		while (_running)
			update();
		deinit();
	}

	public void preInit() {
		Translation.loadLocal("en", "GB", "trans");

		_random = new Random();
		_renderEngine = new RenderEngine(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, true);
	}

	public void init() {
		_renderEngine.init();

		_map = new Map(16, 16);
		_player = new Entity(_map);

		_player.getInventory().addItem(Item.animal_jar_insect_stick.getItemID(), 5);
		_player.getInventory().addItem(Item.weapon_shortsword_copper.getItemID(), 4);
		_player.getInventory().addItem(Item.tool_whistle_tin.getItemID(), 10);
		_player.getInventory().addItem(Item.flower_sunflower.getItemID(), 3);
		_player.getInventory().addItem(Item.armour_chest_leather.getItemID(), 1);
		_player.getInventory().addItem(Item.animal_jar_insect_stick.getItemID(), 1);

		_currentState = new GameStatePlaying();
		_currentState.init(this, _player, _map);
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

		_currentState.update(_renderEngine);
	}

	public void deinit() {
		_currentState.deinit();
		Display.destroy();
	}

	public void changeState(GameState newState) {
		_currentState.deinit();
		_currentState = newState;
		_currentState.init(this, _player, _map);
	}

	public Random getRandom() {
		return _random;
	}

	public TextureManager getTextureManager() {
		return _renderEngine.getTextureManager();
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
