package com.naronco.infinityjam;

import com.deviotion.ld.eggine.Eggine;
import com.deviotion.ld.eggine.graphics.*;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.scenes.*;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Eggine {
	Sprite ui;

	public static Game instance;

	public static final int MODE_WALK = -1;
	public static final int MODE_LOOK = 0;
	public static final int MODE_USE = 1;
	public static final int MODE_TAKE = 2;
	public static final int MODE_PUNCH = 3;

	public Bedroom bedroom;
	public Hallway hallway;
	public Elevator elevator;
	public Street street;
	public Casino casino;

	public Game() {
		super(60, 30, new Window("InfinityJam", new Dimension2d(200, 150), 4));

		List<AudioFormat> supportedAudioFormats = Sound.getSupportedAudioFormats();

		instance = this;

		bedroom = new Bedroom();
		hallway = new Hallway();
		elevator = new Elevator();
		street = new Street();
		casino = new Casino();

		random = new Random();

		bedroom.load();
		hallway.load();
		elevator.load();
		street.load();
		casino.load();

		currentScene = bedroom;

		Sound backgroundMusic = currentScene.getBackgroundMusic();
		if (backgroundMusic != null) {
			backgroundMusic.playInfinitely();
		}

		player = new Character(90, 40);

		ui = new Sprite(new File("res/ui.png"));

		messageTextArea = new TextArea(0, 87, 200, 18, Font.standard);
		messageTextArea.setMaxLineCount(2);

		itemSprites.add(new Sprite(new File("res/key.png")));
		itemSprites.add(new Sprite(new File("res/key.png"))); // TODO: Leaf
		itemSprites.add(new Sprite(new File("res/key.png"))); // TODO: Coins
		itemSprites.add(new Sprite(new File("res/key.png"))); // TODO: Drug
		itemSprites.add(new Sprite(new File("res/key.png"))); // TODO: Saw
		itemSprites.add(new Sprite(new File("res/key.png"))); // TODO: Knife
		if (itemSprites.size() != (int) Item.values().length)
			throw new Error("Programmers were retards and didn't add sprites for every item");

		items.add(Item.KEY);

		clickSheet = new SpriteSheet(new Sprite(new File("res/click.png")), new Dimension2d(8, 8));
	}

	static final int LEFT_BUTTON_WIDTH = 63;
	static final int RIGHT_BUTTON_WIDTH = 119 - 63;
	static final int BUTTON_HEIGHT = 123 - 100;

	private void mixButton(Screen screen, int mode) {
		switch (mode) {
			case MODE_TAKE:
				screen.mixRectangle(0, 106 + BUTTON_HEIGHT, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
				break;
			case MODE_LOOK:
				screen.mixRectangle(0, 106, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
				break;
			case MODE_PUNCH:
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 106 + BUTTON_HEIGHT, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
				break;
			case MODE_USE:
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 106, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
				break;
		}
	}

	@Override
	public void render(Screen screen) {
		screen.fillScreen(0xF2F2F2);

		currentScene.renderBackground(screen);

		if (currentScene.showsPlayer()) {
			if (player.flipX)
				screen.renderAnimatedSpriteFlipped(player.getSpritePosition(), player);
			else
				screen.renderAnimatedSprite(player.getSpritePosition(), player);
			player.nextFrame();
		}

		currentScene.renderForeground(screen);

		screen.renderSprite(0, 0, ui);

		int mx = (int) getMouse().getLocation().getX();
		int my = (int) getMouse().getLocation().getY();

		boolean mouseDown = getMouse().isLeftClicking();
		if (mouseDown && !prevMouseDown) {
			if (my < 86) {
				currentScene.click(mx, my, mode);
				mode = MODE_WALK;
			} else if (mx < 63) {
				if (my > 123) {
					mode = MODE_TAKE;
					mixButton(screen, MODE_TAKE);
				} else if (my > 96) {
					mode = MODE_LOOK;
					mixButton(screen, MODE_LOOK);
				}
			} else if (mx < 122) {
				if (my > 123) {
					mode = MODE_PUNCH;
					mixButton(screen, MODE_PUNCH);
				} else if (my > 96) {
					mode = MODE_USE;
					mixButton(screen, MODE_USE);
				}
			}

			clickAnimation = new SpriteAnimation(clickSheet, 0, 7, 30);
			lastClickX = mx;
			lastClickY = my;
		}
		prevMouseDown = mouseDown;

		mixButton(screen, mode);

		if (detailTextArea != null) {
			detailTextArea.render(screen);
		}

		messageTextArea.render(screen);

		prevMx = mx;
		prevMy = my;

		int itemX = 124, itemY = 106;
		int itemN = 0;
		for (Item i : items) {
			screen.renderSprite(itemX, itemY, itemSprites.get(i.valueOf()));
			itemY += 12;
			itemN++;
			if (itemN % 3 == 0) {
				itemY -= 12 * 3 - 2;
				itemX += 10;
			}
		}

		if (clickAnimation != null) {
			if (clickAnimation.nextFrame()) {
				clickAnimation = null;
			} else {
				int animIndex = clickAnimation.getTile();
				screen.renderSprite(lastClickX - 4, lastClickY - 4, clickSheet.getTileVector(animIndex), 8, 8, clickSheet.getSprite());
			}
		}
	}

	@Override
	public void update(double delta) {
		currentScene.update();

		String lastDetail = currentDetail;
		if (prevMy < 86) {
			String detail = currentScene.detailAt(prevMx, prevMy);

			if (detail != null && mode != MODE_WALK) {
				switch (mode) {
					case MODE_TAKE:
						currentDetail = "Nimm " + detail;
						break;
					case MODE_LOOK:
						currentDetail = "Siehe " + detail + " an";
						break;
					case MODE_USE:
						currentDetail = "Benutze " + detail;
						break;
					case MODE_PUNCH:
						currentDetail = "Schlage " + detail;
						break;
				}
			} else {
				currentDetail = detail;
			}
		} else
			currentDetail = null;

		if (lastDetail != null && currentDetail == null) {
			detailTextArea = null;
		} else if (lastDetail == null && currentDetail != null) {
			detailTextArea = new TextArea(prevMx, prevMy, 80, 12, Font.standard);
			detailTextArea.setAnimated(false);
			detailTextArea.setBordered(true);
			detailTextArea.showText(currentDetail);
			detailTextArea.sizeToFit();
		} else if (lastDetail != null && currentDetail != null) {
			detailTextArea.showText(currentDetail);
			detailTextArea.sizeToFit();
		}

		messageTextArea.update();

		if (detailTextArea != null) {
			if (prevMx + detailTextArea.getWidth() > getWindow().getScreen().getDimension().getWidth()) {
				detailTextArea.setX(prevMx - detailTextArea.getWidth());
			} else {
				detailTextArea.setX(prevMx);
			}
			detailTextArea.setY(prevMy);

			detailTextArea.update();
		}
	}

	public void setScene(IScene scene) {
		currentScene.leave();

		IScene prev = currentScene;
		currentScene = scene;
		currentScene.enter(prev);

		if (prev.getBackgroundMusic() != currentScene.getBackgroundMusic()) {
			Sound backgroundMusic = prev.getBackgroundMusic();
			if (backgroundMusic != null) {
				backgroundMusic.stop();
			}

			backgroundMusic = currentScene.getBackgroundMusic();
			if (backgroundMusic != null) {
				backgroundMusic.playInfinitely();
			}
		}
	}

	public void showMessage(String message) {
		messageTextArea.showText(message);
	}

	public void addQuest(IQuest quest) {
		quest.activate();
		quests.add(quest);
	}

	public void finishQuest(IQuest quest) {
		items.addAll(quest.getRewards());
		quests.remove(quest);
		finishedQuests.add(quest);
	}

	public IQuest getQuest(Class<?> t) {
		for (IQuest q : quests)
			if (t.isInstance(q))
				return q;
		return null;
	}

	public boolean isQuestFinished(Class<?> t) {
		for (IQuest q : finishedQuests)
			if (t.isInstance(q))
				return true;
		return false;
	}

	public Random random;

	IScene currentScene;

	TextArea messageTextArea;
	TextArea detailTextArea;

	String currentDetail;

	public Character player;

	int prevMx, prevMy;
	boolean prevMouseDown;

	int mode = -1;

	/**
	 * @param item
	 * @param count
	 * @param max   Maximum count of this item in inventory
	 * @return true if at least one item has been given.
	 */
	public boolean giveItems(Item item, int count, int max) {
		int existing = 0;
		for (Item i : items)
			if (i == item)
				existing++;
		if (existing > max) {
			return false;
		}
		for (int i = 0; i < Math.min(count, max - existing); i++)
			items.add(item);
		return true;
	}

	public List<Sprite> itemSprites = new ArrayList<>();
	public List<Item> items = new ArrayList<>();
	public List<IQuest> quests = new ArrayList<>();
	public List<IQuest> finishedQuests = new ArrayList<>();
	public int selectedItem = -1;

	SpriteSheet clickSheet;
	SpriteAnimation clickAnimation;
	int lastClickX, lastClickY;

	public Character getPlayer() {
		return player;
	}
}
