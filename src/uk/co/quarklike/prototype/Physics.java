package uk.co.quarklike.prototype;

public class Physics {
	public static int maxRange(float force, float mass, float height) {
		float impulse = force * 0.1f;
		float u = impulse / mass;
		// s = ut + (1/2)at^2
		float g = -9.81f;
		float time = (float) ((-Math.sqrt(-2 * (g * height))) / g);
		Log.debug(u + " " + time + " " + (int) (u * time));
		return (int) Math.round(u * time);
	}
}
