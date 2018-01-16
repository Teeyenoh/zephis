package uk.co.quarklike.zephis.old.engine.gui.windows;

import java.util.ArrayList;

import uk.co.quarklike.zephis.old.engine.RenderEngine;
import uk.co.quarklike.zephis.old.engine.gui.GUIComponent;

public abstract class GUIWindow {
	protected int x, y, width, height;
	protected ArrayList<GUIComponent> comps = new ArrayList<GUIComponent>();

	public void draw(RenderEngine renderEngine) {
		for (GUIComponent gui : comps) {
			gui.draw(renderEngine);
		}
	}

	protected void addComponent(GUIComponent c) {
		comps.add(c);
	}

	public abstract void init();

	public abstract void refresh();
}
