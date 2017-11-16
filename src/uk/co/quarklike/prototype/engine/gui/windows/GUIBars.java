package uk.co.quarklike.prototype.engine.gui.windows;

import uk.co.quarklike.prototype.engine.ContentHub;
import uk.co.quarklike.prototype.engine.gui.GUIImage;
import uk.co.quarklike.prototype.map.entity.EntityLiving;

public class GUIBars extends GUIWindow {
	private EntityLiving player;
	private GUIImage health, hunger, mana, tiredness, stamina, warmth;

	public GUIBars(ContentHub contentHub, EntityLiving player) {
		this.x = 0;
		this.width = 256;
		this.height = 64;
		this.y = -contentHub.getWindowHeight() / 2 + 16 + (height / 2);
		this.player = player;
		
		init();
	}

	@Override
	public void init() {
		comps.add(new GUIImage(x, y, width, height, "gui/stats.png"));
		comps.add(hunger = new GUIImage(x, y - 20, (width - 8), 16, "gui/backbar.png"));
		comps.add(health = new GUIImage(x, y - 20, (width - 8), 16, "gui/health.png"));
		comps.add(tiredness = new GUIImage(x, y, (width - 8), 16, "gui/backbar.png"));
		comps.add(mana = new GUIImage(x, y, (width - 8), 16, "gui/mana.png"));
		comps.add(warmth = new GUIImage(x, y + 20, (width - 8), 16, "gui/backbar.png"));
		comps.add(stamina = new GUIImage(x, y + 20, (width - 8), 16, "gui/stamina.png"));
	}

	@Override
	public void refresh() {
		health.setWidth((int) ((width - 8) * ((float) player.getStats().getHealth() / player.getStats().getHardMaxHealth())));
		hunger.setWidth((int) ((width - 8) * ((float) player.getStats().getHealth() / player.getStats().getSoftMaxHealth())));
		mana.setWidth((int) ((width - 8) * ((float) player.getStats().getMana() / player.getStats().getHardMaxMana())));
		tiredness.setWidth((int) ((width - 8) * ((float) player.getStats().getMana() / player.getStats().getSoftMaxMana())));
		stamina.setWidth((int) ((width - 8) * ((float) player.getStats().getStamina() / player.getStats().getHardMaxStamina())));
		warmth.setWidth((int) ((width - 8) * ((float) player.getStats().getStamina() / player.getStats().getSoftMaxStamina())));
	}
}
