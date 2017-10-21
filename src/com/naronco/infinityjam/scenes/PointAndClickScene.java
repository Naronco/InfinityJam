package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Interactable;
import com.naronco.infinityjam.StepArea;
import com.naronco.infinityjam.interactables.Door;

import java.util.ArrayList;
import java.util.List;

public abstract class PointAndClickScene implements IScene {
	protected List<Interactable> interactables = new ArrayList<Interactable>();
	protected Sprite background, foreground;
	protected List<Polygon2d> movementAreas = new ArrayList<Polygon2d>();
	protected List<StepArea> stepAreas = new ArrayList<StepArea>();

	public void addMovementArea(Polygon2d area) {
		movementAreas.add(area);
	}

	@Override
	public void leave() {
	}

	@Override
	public String detailAt(int x, int y) {
		for (Interactable i : interactables) {
			if (i.intersects(x, y))
				return i.getName();
		}
		return null;
	}

	@Override
	public void click(int x, int y, int mode) {
		if (mode == Game.MODE_WALK) {
			Vector2d playerPos = Game.instance.player.getPosition();

			Polygon2d targetArea = movementAreaAt(x, y);
			if (targetArea != null) {
				boolean movable = true;

				if (movementAreaAt((int) playerPos.getX(), (int) playerPos.getY()) != null) {
					double dx = x - playerPos.getX();
					double dy = y - playerPos.getY();

					double dist = Math.sqrt(dx * dx + dy * dy);
					dx /= dist;
					dy /= dist;

					double tx = playerPos.getX();
					double ty = playerPos.getY();

					double step = 4.0;

					for (double p = 0; p < dist; p += step) {
						tx += dx * step;
						ty += dy * step;

						if (movementAreaAt((int) tx, (int) ty) == null) {
							movable = false;
							break;
						}
					}
				}

				if (movable) {
					Game.instance.player.walkTo(new Vector2d(x, y));
					return;
				}
			}
		}

		for (Interactable i : interactables) {
			if (i.intersects(x, y)) {
				switch (mode) {
					case Game.MODE_LOOK:
						if (!i.hasLook())
							Game.instance.showMessage("Die Entwickler waren zu blöd hier einen Text einzufügen");
						else
							i.look(x, y);
						break;
					case Game.MODE_USE:
						if (!i.hasUse())
							Game.instance.showMessage("Das ist nutzlos");
						else
							i.use(x, y);
						break;
					case Game.MODE_TAKE:
						if (!i.hasTake())
							Game.instance.showMessage("Ich kann das nicht nehmen");
						else
							i.take(x, y);
						break;
					case Game.MODE_PUNCH:
						if (!i.hasPunch())
							Game.instance.showMessage("Gewalt löst hierbei keine Probleme");
						else
							i.punch(x, y);
						break;
					default:
						if (i instanceof Door)
							Game.instance.player.walkTo(((Door) i).walkTo);
						else
							Game.instance.showMessage(i.getNameWithArticle() + ".");
						break;
				}
				return;
			}
		}
		if (mode == Game.MODE_WALK)
			Game.instance.showMessage("Da komm ich nicht hin");
	}

	@Override
	public void renderBackground(Screen screen) {
		if (background != null)
			screen.renderSprite(0, 0, background);
	}

	@Override
	public void renderForeground(Screen screen) {
		if (foreground != null)
			screen.renderSprite(0, 0, foreground);
	}

	@Override
	public void update() {
		if (Game.instance.player.walkingEnded()) {
			Vector2d pos = Game.instance.player.getPosition();
			int px = (int) pos.getX();
			int py = (int) pos.getY();
			for (StepArea area : stepAreas) {
				area.stepOn(px, py);
			}
		}
	}

	@Override
	public boolean showsPlayer() {
		return true;
	}

	private Polygon2d movementAreaAt(int x, int y) {
		for (Polygon2d area : movementAreas) {
			if (area.intersects(x, y)) {
				return area;
			}
		}
		return null;
	}
}
