package uk.co.quarklike.zephis.src;

import java.util.Random;

import org.lwjgl.opengl.Display;

import uk.co.quarklike.zephis.src.graphics.RenderEngine;
import uk.co.quarklike.zephis.src.graphics.TextureManager;
import uk.co.quarklike.zephis.src.map.Map;
import uk.co.quarklike.zephis.src.map.MapData;
import uk.co.quarklike.zephis.src.map.MapManager;
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
	private MapManager _mapManager;

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

		MapData.initMapData();
	}

	public void init() {
		_renderEngine.init();
		_mapManager = new MapManager();

		_map = new Map(_mapManager, _mapManager.loadMap("testMap"));
		_mapManager.addMap(_map.getMapID(), _map);

		_player = new Entity(_map);

		_player.getBody().getInventory().addItem(Item.animal_jar_insect_stick.getItemID(), 5);
		_player.getBody().getInventory().addItem(Item.weapon_shortsword_copper.getItemID(), 4);
		_player.getBody().getInventory().addItem(Item.tool_whistle_tin.getItemID(), 10);
		_player.getBody().getInventory().addItem(Item.flower_sunflower.getItemID(), 3);
		_player.getBody().getInventory().addItem(Item.armour_chest_leather.getItemID(), 1);
		_player.getBody().getInventory().addItem(Item.animal_jar_insect_stick.getItemID(), 1);

		_currentState = new GameStatePlaying();
		_currentState.init(this, _player);
	}

	public void postInit() {
		_player.getBody().setPosition((byte) 1, (byte) 1);

		(new Entity(_map)).getBody().setPosition((byte) 10, (byte) 10);
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
		_currentState.init(this, _player);
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
