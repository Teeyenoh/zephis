package uk.co.quarklike.zephis.old.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import uk.co.quarklike.zephis.old.Log;

public class ResourceManager implements Manager {
	private static final float MAX_TIME = 1000f / 60;

	private ContentHub contentHub;
	private HashMap<Integer, String> preLoadTextures;
	private HashMap<String, Texture> textures;
	private ArrayList<String> queue;

	private Properties config;

	@Override
	public void preInit(ContentHub contentHub) {
		this.contentHub = contentHub;
		contentHub.setResources(this);
		loadConfig();
	}

	@Override
	public void init() {
		preLoadTextures = new HashMap<Integer, String>();
		textures = new HashMap<String, Texture>();
		queue = new ArrayList<String>();
	}

	@Override
	public void postInit() {
		loadTexture("blank.png");
		preLoad();
	}

	private void preLoad() {
		Properties p = new Properties();
		try {
			p.load(ResourceLoader.getResourceAsStream("res/textures/textures.properties"));
		} catch (IOException e) {
			Log.warn("Failed to load preload textures file", e);
			return;
		}
		int i = 0;
		while (p.getProperty(String.valueOf(i)) != null) {
			preLoadTextures.put(i, p.getProperty(String.valueOf(i)));
			i++;
		}
	}

	private void loadConfig() {
		config = new Properties();
		try {
			config.load(ResourceLoader.getResourceAsStream("res/config.properties"));
		} catch (IOException e) {
			Log.err("Failed to load config file", e);
			return;
		}

		contentHub.setWindowWidth(Integer.valueOf((String) config.getOrDefault("window_width", 800)));
		contentHub.setWindowHeight(Integer.valueOf((String) config.getOrDefault("window_height", 600)));
	}

	@Override
	public void update() {
		long time = Sys.getTime();

		while (!queue.isEmpty() && Sys.getTime() <= time + MAX_TIME) {
			loadTexture(queue.get(0));
			queue.remove(0);
		}
	}

	private void addToQueue(String name) {
		if (!queue.contains(name))
			queue.add(name);
	}

	public void requestTexture(int id) {
		requestTexture(preLoadTextures.get(id));
	}

	public void requestTexture(String name) {
		if (name == null)
			return;
		if (textures.containsKey(name) || queue.contains(name))
			return;
		addToQueue(name);
	}

	public Texture getTexture(int id) {
		return getTexture(preLoadTextures.get(id));
	}

	public Texture getTexture(String name) {
		if (name == null)
			return getTexture("blank.png");

		if (textures.get(name) == null) {
			addToQueue(name);
			return getTexture("blank.png");
		}

		return textures.get(name);
	}

	private void loadTexture(String name) {
		try {
			textures.put(name, TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/textures/" + name)));
		} catch (IOException | RuntimeException e) {
			textures.put(name, getTexture("blank.png"));
			Log.warn("Failed to load texture: " + name, e);
		}
	}

	@Override
	public void deinit() {
		queue.clear();
		textures.clear();
	}

	@Override
	public String getName() {
		return "Texture Manager";
	}
}
