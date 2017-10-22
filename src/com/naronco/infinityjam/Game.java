package com.naronco.infinityjam;

import com.deviotion.ld.eggine.Eggine;
import com.deviotion.ld.eggine.graphics.*;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.dialog.Dialog;
import com.naronco.infinityjam.dialog.IAnswer;
import com.naronco.infinityjam.scenes.*;
import com.naronco.infinityjam.scenes.inventory.Inventory;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Eggine {
	Sprite ui, uiLite;

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
	public Alley alley;

	public SpriteSheet characters;

	public Game() {
		super(60, 30, new Window("InfinityJam", new Dimension2d(200, 150), 4));

		List<AudioFormat> supportedAudioFormats = Sound.getSupportedAudioFormats();

		characters = new SpriteSheet(new Sprite(new File("res/char.png")), new Dimension2d(19, 35));

		instance = this;

		bedroom = new Bedroom();
		hallway = new Hallway();
		elevator = new Elevator();
		street = new Street();
		casino = new Casino();
		alley = new Alley();

		random = new Random();

		bedroom.load();
		hallway.load();
		elevator.load();
		street.load();
		casino.load();
		alley.load();

		currentScene = bedroom;

		Sound backgroundMusic = currentScene.getBackgroundMusic();
		if (backgroundMusic != null) {
			backgroundMusic.playInfinitely();
		}

		player = new Character(90, 40);

		ui = new Sprite(new File("res/ui.png"));
		uiLite = new Sprite(new File("res/ui-lite.png"));

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
		items.add(Item.KEY);
		items.add(Item.KEY);
		items.add(Item.KEY);
		items.add(Item.KEY);
		items.add(Item.KEY);
		items.add(Item.KEY);
		items.add(Item.KEY);
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
			player.draw(screen);
		}

		currentScene.renderForeground(screen);

		if (currentTransition != null)
			currentTransition.render(screen, transitionTime / transitionDuration);

		int mx = (int) getMouse().getLocation().getX();
		int my = (int) getMouse().getLocation().getY();

		boolean mouseDown = getMouse().isLeftClicking();
		boolean mouseClick = mouseDown && !prevMouseDown;
		if (mouseClick) {
			if (!animationPlaying && queuedDialogs.size() == 0 && activeDialog == null) {
				if (my < 86) {
					currentScene.click(mx, my, mode);
					mode = MODE_WALK;
					selectedItem = -1;
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
				} else if (mx > 200 - 22 && my > 150 - 22) {
					if (currentScene instanceof Inventory) {
						setScene(((Inventory) currentScene).getPrevScene());
					} else {
						setScene(new Inventory(new Dimension2d(200, 86)));
					}
				}
			} else if (queuedDialogs.size() > 0 && activeDialog == null) {
				Dialog d = queuedDialogs.remove(queuedDialogs.size() - 1);
				showMessage(d.title);
				if (d.answers != null && d.answers.length > 0)
					activeDialog = d;
			}

			clickAnimation = new SpriteAnimation(clickSheet, 0, 7, 30);
			lastClickX = mx;
			lastClickY = my;
		}

		if (activeDialog == null) {
			if (queuedDialogs.size() == 0)
				screen.renderSprite(0, 0, ui);
			else
				screen.renderSprite(0, 0, uiLite);
		} else {
			screen.renderSprite(0, 0, uiLite);
			int y = 0;
			for (IAnswer answer : activeDialog.answers) {
				boolean hovered = my >= 106 + y * 10 && my < 116 + y * 10;
				if (hovered)
					screen.mixRectangle(0, 106 + y * 10, 200, 10, 0x40000000);
				screen.renderText(2, 107 + y++ * 10, Font.standard, answer.getTitle());
				if (hovered && mouseDown && !prevMouseDown) {
					activeDialog = null;
					answer.run();
					break;
				}
			}
		}

		mixButton(screen, mode);

		messageTextArea.render(screen);

		if (detailTextArea != null) {
			detailTextArea.render(screen);
		}

		prevMx = mx;
		prevMy = my;
		prevMouseDown = mouseDown;

		if (activeDialog == null && queuedDialogs.size() == 0) {
			int itemX = 124, itemY = 106;
			int itemN = 0;
			for (Item i : items) {
				if (selectedItem == itemN)
					screen.renderCircle(itemX + 8, itemY + 8, 7, 0xA0A0A0);

				screen.renderSprite(itemX, itemY, itemSprites.get(i.ordinal()));

				if (mouseClick && mx >= itemX && my >= itemY && mx < itemX + 16 && my < itemY + 16)
					selectedItem = itemN;

				itemY += 12;
				itemN++;
				if (itemN % 3 == 0) {
					itemY -= 12 * 3 - 2;
					itemX += 10;
				}
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
						if (selectedItem != -1)
							currentDetail += " mit " + items.get(selectedItem).toString();
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

		if (currentTransition != null) {
			transitionTime += 1.0 / 30.0;
			if (transitionTime >= transitionDuration * 0.5 && pendingScene != null) {
				currentScene = pendingScene;
				currentScene.enter(prevScene);
				prevScene = pendingScene = null;
			}
			if (transitionTime >= transitionDuration) {
				currentTransition = null;

				Sound backgroundMusic = currentScene.getBackgroundMusic();
				if (backgroundMusic != null)
					backgroundMusic.playInfinitely();
			}
		}
	}

	public void setScene(IScene scene, ISceneTransition transition) {
		currentScene.leave();

		if (transition == null) {
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
		} else {
			currentTransition = transition;
			transitionTime = 0;

			prevScene = currentScene;
			pendingScene = scene;

			Sound backgroundMusic = currentScene.getBackgroundMusic();
			if (backgroundMusic != null)
				backgroundMusic.stop();
		}
	}

	public void setScene(IScene scene) {
		setScene(scene, null);
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

	IScene pendingScene, prevScene;
	ISceneTransition currentTransition;
	double transitionDuration = 1.0;
	double transitionTime = 0;

	TextArea messageTextArea;
	TextArea detailTextArea;

	String currentDetail;

	public Character player;

	int prevMx, prevMy;
	boolean prevMouseDown;

	public boolean animationPlaying = false;

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
		if (existing >= max) {
			return false;
		}
		for (int i = 0; i < Math.min(count, max - existing); i++)
			items.add(item);
		return true;
	}

	public Character getPlayer() {
		return player;
	}

	public List<IQuest> getQuests() {
		return quests;
	}

	public List<IQuest> getFinishedQuests() {
		return finishedQuests;
	}

	public Item getSelectedItem() {
		return selectedItem != -1 ? items.get(selectedItem) : null;
	}

	public void pushDialog(Dialog child) {
		queuedDialogs.add(child);
	}

	public List<Sprite> itemSprites = new ArrayList<>();
	public List<Item> items = new ArrayList<>();
	public List<IQuest> quests = new ArrayList<>();
	public List<IQuest> finishedQuests = new ArrayList<>();
	public List<Dialog> queuedDialogs = new ArrayList<>();
	Dialog activeDialog = null;
	public int selectedItem = -1;

	SpriteSheet clickSheet;
	SpriteAnimation clickAnimation;
	int lastClickX, lastClickY;
}
