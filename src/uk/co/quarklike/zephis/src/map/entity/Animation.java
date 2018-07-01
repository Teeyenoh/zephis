package uk.co.quarklike.zephis.src.map.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import uk.co.quarklike.zephis.src.Const;
import uk.co.quarklike.zephis.src.graphics.TextureCell;

public class Animation {
	private String _animationName;
	private String _texturePath;
	private int _textureWidth;
	private int _textureHeight;
	private int _frameWidth;
	private int _frameHeight;
	private int _frames;
	private int[] _xCoords;
	private int[] _frameTimes;
	private int _frame;

	private int _timer;

	public Animation(String name, String texture, int textureWidth, int textureHeight, int frameWidth, int frameHeight) {
		_animationName = name;
		_texturePath = texture;
		_textureWidth = textureWidth;
		_textureHeight = textureHeight;
		_frameWidth = frameWidth;
		_frameHeight = frameHeight;

		_xCoords = new int[0];
		_frameTimes = new int[0];
	}

	public boolean update() {
		_timer++;

		if (_timer >= _frameTimes[_frame]) {
			_frame++;
			_timer = 0;
			if (_frame >= _frames) {
				_frame = 0;
				return true;
			}
		}

		return false;
	}

	public void addFrame(int x, int length) {
		_xCoords = Arrays.copyOf(_xCoords, _frames + 1);
		_frameTimes = Arrays.copyOf(_frameTimes, _frames + 1);

		_xCoords[_frames] = x;
		_frameTimes[_frames] = length;

		_frames++;
	}

	private int getY(byte direction) {
		int y = 0;

		switch (direction) {
		case Const.DIR_NONE:
			y = 0;
		case Const.DIR_FORWARD:
			y = 0;
		case Const.DIR_LEFT:
			y = 1;
		case Const.DIR_RIGHT:
			y = 2;
		case Const.DIR_BACKWARD:
			y = 3;
		default:
			y = 0;
		}

		return y * _frameHeight;
	}

	public TextureCell getTexture(byte direction) {
		return new TextureCell(_texturePath, _textureWidth, _textureHeight, _frameWidth, _frameHeight, _xCoords[_frame], getY(direction));
	}

	public static Animation readFile(String path, String prefix) {
		Animation out = null;

		try {
			FileReader fr = new FileReader("res/anim/" + path + ".qanim");
			BufferedReader br = new BufferedReader(fr);

			String animName = br.readLine();
			String texture = br.readLine();
			texture = texture.replace("%DEFAULT_PREFIX%", prefix);
			int texWidth = Integer.parseInt(br.readLine());
			int texHeight = Integer.parseInt(br.readLine());
			int frameWidth = Integer.parseInt(br.readLine());
			int frameHeight = Integer.parseInt(br.readLine());

			out = new Animation(animName, texture, texWidth, texHeight, frameWidth, frameHeight);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				out.addFrame(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out;
	}

	public void reset() {
		_frame = 0;
		_timer = 0;
	}
}
