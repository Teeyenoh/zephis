package uk.co.quarklike.prototype.engine;

public interface Manager {
	public void preInit(ContentHub contentHub);

	public void init();

	public void postInit();

	public void update();

	public void deinit();

	public String getName();
}
