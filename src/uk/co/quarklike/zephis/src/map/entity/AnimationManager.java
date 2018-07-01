package uk.co.quarklike.zephis.src.map.entity;

import java.util.HashMap;

public class AnimationManager {
	private HashMap<String, Animation> _animations;
	private String _currentAnimation;
	private String _queuedAnim;

	public AnimationManager(String texturePrefix) {
		_animations = new HashMap<String, Animation>();

		_animations.put("idle", Animation.readFile("idle_default", texturePrefix));
		_animations.put("attack", Animation.readFile("attack_default", texturePrefix));
		_animations.put("walk", Animation.readFile("walk_default", texturePrefix));

		_currentAnimation = "idle";
	}

	public Animation getCurrentAnim() {
		return _animations.get(_currentAnimation);
	}

	public void queueAnim(String anim) {
		_queuedAnim = anim;
	}

	public void setCurrentAnim(String anim) {
		_animations.get(_currentAnimation).reset();
		_currentAnimation = anim;
	}

	public void update() {
		if (getCurrentAnim().update()) {
			if (_queuedAnim != null) {
				setCurrentAnim(_queuedAnim);
				_queuedAnim = null;
			}
		}
	}
}
