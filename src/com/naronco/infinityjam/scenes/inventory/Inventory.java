package com.naronco.infinityjam.scenes.inventory;

import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.Game;
import com.naronco.infinityjam.IQuest;
import com.naronco.infinityjam.IScene;

import java.util.List;

public class Inventory implements IScene {
	private static final int PAGE_QUESTS = 0;
	private static final int PAGE_INVENTORY = 1;
	private static final int PAGE_OPTIONS = 2;

	private Dimension2d size;
	private int page = PAGE_QUESTS;
	private IScene prevScene;

	public Inventory(Dimension2d size) {
		this.size = size;
	}

	@Override
	public void load() {
	}

	@Override
	public void enter(IScene prev) {
		prevScene = prev;
	}

	@Override
	public void leave() {
	}

	@Override
	public String detailAt(int x, int y) {
		return null;
	}

	@Override
	public void click(int x, int y, int mode) {
		if (y < 12) {
			int section = (int) (size.getWidth() / 3.0);
			if (x < section) {
				setPage(PAGE_QUESTS);
			} else if (x < section * 2) {
				setPage(PAGE_INVENTORY);
			} else {
				setPage(PAGE_OPTIONS);
			}
		}
	}

	@Override
	public void renderBackground(Screen screen) {
		int section = (int)(size.getWidth() / 3.0);

		screen.renderRectangle(0, 0, size, 0x121212);

		if (page == PAGE_QUESTS) {
			screen.renderRectangle(0, 1, section - 1, 11, 0xF2F2F2);
		} else {
			screen.renderRectangle(0, 1, section - 1, 10, 0xA0A0A0);
		}

		if (page == PAGE_INVENTORY) {
			screen.renderRectangle(section, 1, section - 1, 11, 0xF2F2F2);
		} else {
			screen.renderRectangle(section, 1, section - 1, 10, 0xA0A0A0);
		}

		if (page == PAGE_OPTIONS) {
			screen.renderRectangle(section * 2, 1, section + 2, 11, 0xF2F2F2);
		} else {
			screen.renderRectangle(section * 2, 1, section + 2, 10, 0xA0A0A0);
		}

		screen.renderText(1, 2, Font.standard, "Quests");
		screen.renderText(1 + section, 2, Font.standard, "Inventar");
		screen.renderText(1 + section * 2, 2, Font.standard, "Optionen");

		screen.renderRectangle(0, 12, (int)size.getWidth(), (int)size.getHeight() - 12, 0xF2F2F2);

		switch (page) {
			case PAGE_QUESTS:
				int halfWidth = (int)(size.getWidth() / 2);

				screen.renderText(1, 13, Font.standard, "Offen");
				screen.renderRectangle(1, 21,  halfWidth - 7, 1, 0xA0A0A0);

				List<IQuest> quests = Game.instance.getQuests();
				if (quests.isEmpty()) {
					screen.renderText(1, 23, Font.standard, "Keine");
				} else {
					for (int i = 0; i < quests.size(); ++i) {
						screen.renderText(1, 23 + i * 9, Font.standard, quests.get(i).getDescription());
					}
				}

				screen.renderText(1 + halfWidth, 13, Font.standard, "Abgeschlossen");
				screen.renderRectangle(1 + halfWidth, 21, halfWidth - 7, 1, 0xA0A0A0);

				List<IQuest> finishedQuests = Game.instance.getFinishedQuests();
				if (finishedQuests.isEmpty()) {
					screen.renderText(1 + halfWidth, 23, Font.standard, "Keine");
				} else {
					for (int i = 0; i < finishedQuests.size(); ++i) {
						screen.renderText(1 + halfWidth, 23 + i * 9, Font.standard, finishedQuests.get(i).getDescription());
					}
				}

				break;
			case PAGE_INVENTORY:
				break;
			case PAGE_OPTIONS:
				break;
		}
	}

	@Override
	public void renderForeground(Screen screen) {
	}

	@Override
	public void update() {
	}

	@Override
	public Sound getBackgroundMusic() {
		return null;
	}

	@Override
	public boolean showsPlayer() {
		return false;
	}

	public void setPage(int page) {
		if (page == this.page) return;
		this.page = page;
	}
}
