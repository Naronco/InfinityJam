package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.math.Polygon2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IScene;
import com.naronco.infinityjam.Interactable;

import java.util.ArrayList;
import java.util.List;

public class PointAndClickScene implements IScene {
	protected List<Interactable> interactables = new ArrayList<Interactable>();
	protected Sprite background, foreground;
	protected List<Polygon2d> movementAreas = new ArrayList<Polygon2d>();

	public void addMovementArea(Polygon2d area) {
		movementAreas.add(area);
	}

	@Override
	public void enter() {
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
			boolean moved = false;

			for (Polygon2d area : movementAreas) {
				if (area.intersects(x, y)) {
					Game.instance.player.walkTo(new Vector2d(x, y));
					moved = true;
					break;
				}
			}

			if (!moved)
				Game.instance.showMessage("Da komm ich nicht hin");
		} else {
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
					}
					return;
				}
			}
		}
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
	}
}
