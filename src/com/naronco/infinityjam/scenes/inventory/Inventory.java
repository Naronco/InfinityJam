package com.naronco.infinityjam.scenes.inventory;

import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.*;

import java.util.List;

public class Inventory implements IScene {
	private static final int PAGE_QUESTS = 0;
	private static final int PAGE_OPTIONS = 1;

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
			int section = (int) (size.getWidth() / 2.0);
			if (x < section) {
				setPage(PAGE_QUESTS);
			} else {
				setPage(PAGE_OPTIONS);
			}
		} else {
			switch (page) {
				case PAGE_OPTIONS:
					if (y >= 14 && y < 24)
						Options.musicEnabled = !Options.musicEnabled;
					if (y >= 24 && y < 34)
						Options.soundsEnabled = !Options.soundsEnabled;
					break;
			}
		}
	}

	@Override
	public void renderBackground(Screen screen) {
		int section = (int)(size.getWidth() / 2.0);

		screen.renderRectangle(0, 0, size, 0x121212);

		if (page == PAGE_QUESTS) {
			screen.renderRectangle(0, 1, section - 1, 11, 0xF2F2F2);
		} else {
			screen.renderRectangle(0, 1, section - 1, 10, 0xA0A0A0);
		}

		if (page == PAGE_OPTIONS) {
			screen.renderRectangle(section, 1, section + 2, 11, 0xF2F2F2);
		} else {
			screen.renderRectangle(section, 1, section + 2, 10, 0xA0A0A0);
		}

		screen.renderText(1, 2, Font.standard, "Quests");
		screen.renderText(1 + section, 2, Font.standard, "Optionen");

		screen.renderRectangle(0, 12, (int)size.getWidth(), (int)size.getHeight() - 12, 0xF2F2F2);

		switch (page) {
			case PAGE_QUESTS:
//				screen.renderRectangle(1, 22, (int)(size.getWidth()) - 2, 1, 0xA0A0A0);

				List<IQuest> quests = Game.instance.getQuests();
				List<IQuest> finishedQuests = Game.instance.getFinishedQuests();
				if (quests.isEmpty() && finishedQuests.isEmpty()) {
					screen.renderText(1, 15, Font.standard, "Keine");
				} else {
					for (int i = 0; i < quests.size(); ++i) {
						int y = 15 + i * 9;
						screen.renderSprite(1, y - 1, Sprites.CHECKBOX_OFF);
						screen.renderText(12, y, Font.standard, quests.get(i).getDescription());
					}

					for (int i = 0; i < finishedQuests.size(); ++i) {
						int y = 15 + (quests.size() + i) * 9;
						screen.renderSprite(1, y - 1, Sprites.CHECKBOX_ON);
						screen.renderText(12, y, Font.standard, finishedQuests.get(i).getDescription());
					}
				}

				break;
			case PAGE_OPTIONS:
				screen.renderSprite(1, 14, Options.musicEnabled ? Sprites.CHECKBOX_ON : Sprites.CHECKBOX_OFF);
				screen.renderText(12, 15, Font.standard, "Musik");

				screen.renderSprite(1, 24, Options.soundsEnabled ? Sprites.CHECKBOX_ON : Sprites.CHECKBOX_OFF);
				screen.renderText(12, 25, Font.standard, "Sounds");

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

	public IScene getPrevScene() {
		return prevScene;
	}
}
