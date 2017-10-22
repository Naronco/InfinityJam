package com.naronco.infinityjam;

import com.deviotion.ld.eggine.Eggine;
import com.deviotion.ld.eggine.graphics.*;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.dialog.Dialog;
import com.naronco.infinityjam.dialog.IAnswer;
import com.naronco.infinityjam.quests.TripleSevenQuest;
import com.naronco.infinityjam.scenes.*;
import com.naronco.infinityjam.scenes.inventory.Inventory;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

		instance = this;

		List<AudioFormat> supportedAudioFormats = Sound.getSupportedAudioFormats();

		characters = new SpriteSheet(new Sprite(new File("res/char.png")), new Dimension2d(19, 35));
		player = new Player(90, 40);

		bedroom = new Bedroom();
		hallway = new Hallway();
		elevator = new Elevator();
		street = new Street();
		casino = new Casino();
		alley = new Alley();
		alley.setMode(Alley.MODE_BAD_GUYS_CONFRONTATION);

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

		gameOverBG = new Sprite(new File("res/dead.png"));
		ui = new Sprite(new File("res/ui.png"));
		uiLite = new Sprite(new File("res/ui-lite.png"));
		dialogIndicator = new Sprite(new File("res/dialog-indicator.png"));

		messageTextArea = new TextArea(0, 87, 200, 18, Font.standard);
		messageTextArea.setMaxLineCount(2);

		clickSheet = new SpriteSheet(new Sprite(new File("res/click.png")), new Dimension2d(8, 8));

		addQuest(new TripleSevenQuest());

		showMessage("Dein einziges Ziel ist es, die 777 am Spielautomat zu erreichen.");
	}

	static final int LEFT_BUTTON_WIDTH = 63;
	static final int RIGHT_BUTTON_WIDTH = 119 - 63;
	static final int BUTTON_HEIGHT = 123 - 100;

	private void revealItems(Item... items) {
		revealingItems = items;
		timeSinceItemRevealStart = 0;
		animationPlaying = true;
	}

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

	private void renderItemReveal(Screen screen) {
		String gainText = "Du bekamst";

		StringBuilder itemsTextBuilder = new StringBuilder();
		for (int i = 0; i < revealingItems.length; ++i) {
			if (i > 0)
				itemsTextBuilder.append(",");
			itemsTextBuilder.append(revealingItems[i].toString());
		}

		String itemsText = itemsTextBuilder.toString();

		int itemsWidth = revealingItems.length * 17 * 2 + 4;
		int minimumTextWidth = gainText.length() * 6 + 5;
		int itemsTextWidth = itemsText.length() * 6 + 5;

		int width = Math.max(itemsWidth, Math.max(minimumTextWidth, itemsTextWidth));

		int x0 = (int) (screen.getDimension().getWidth() * 0.5 - width * 0.5);
		int y0 = 30;

		int height = 16 * 2 + 4 + 8 + 8 + 4;

		screen.mixOutlinedRectangle(x0, y0 - 10, width - 2, height, 0xE0121212);
		screen.mixOutlinedRectangle(x0 + 1, y0 - 9, width - 2, height, 0xE0121212);
		screen.mixRectangle(x0 + 1, y0 - 9, width - 4, height - 2, 0xE0F2F2F2);

		screen.renderText((int) (screen.getDimension().getWidth() * 0.5 - gainText.length() * 6 * 0.5) - 1, y0 - 8, Font.standard, gainText);
		screen.renderText((int) (screen.getDimension().getWidth() * 0.5 - itemsText.length() * 6 * 0.5) - 1, y0 + height - 20, Font.standard, itemsText);

		double p = Math.min(1, timeSinceItemRevealStart);

		for (int i = 0; i < revealingItems.length; ++i) {
			Item item = revealingItems[i];

			double x = screen.getDimension().getWidth() * 0.5 - revealingItems.length * 17 * 2 * 0.5 + i * 17 * 2;
			double y = y0 + (1 - Math.sin(0.5 * Math.PI * p)) * 50;

			screen.renderScaledSprite((int) x, (int) y, 2, item.getSprite());
		}
	}

	@Override
	public void render(Screen screen) {
		if (won) {
			screen.renderRectangle(0, 0, 200, 150, 0x121212);
			for (int i = 0; i < WIN_TEXT_LINES.length; ++i) {
				screen.renderText(1, wonFrame + i * 8, Font.white, WIN_TEXT_LINES[i]);
			}
			return;
		}
		if (gameOver) {
			if (gameOverFrame > 0)
				gameOverFrame--;
			int f = 75 - gameOverFrame;
			screen.renderSprite(0, gameOverFrame, 0, 75 - f, 200, f * 2, gameOverBG);
			gameOverTipArea.render(screen);
			return;
		}
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
		boolean dialogActivatedNow = false;
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
				Dialog d = queuedDialogs.remove(0);
				showMessage(d.title, d.listener);
				if (d.answers != null && d.answers.length > 0)
					activeDialog = d;
				dialogActivatedNow = true;
			} else if (revealingItems != null) {
				revealingItems = null;
				animationPlaying = false;
			}

			clickAnimation = new SpriteAnimation(clickSheet, 0, 7, 30);
			lastClickX = mx;
			lastClickY = my;
		}

		if (activeDialog == null) {
			if (queuedDialogs.size() == 0) {
				screen.renderSprite(0, 0, ui);
				screen.renderSprite(200 - 21, 150 - 20, Sprites.SCROLL);
			} else {
				screen.renderSprite(0, 0, uiLite);
				screen.renderSprite(180, (int)(125 + Math.sin(t) * 5), dialogIndicator);
			}
		} else {
			screen.renderSprite(0, 0, uiLite);
			int y = 0;
			for (IAnswer answer : activeDialog.answers) {
				boolean hovered = my >= 106 + y * 10 && my < 116 + y * 10;
				if (hovered)
					screen.mixRectangle(0, 106 + y * 10, 200, 10, 0x40000000);
				screen.renderText(2, 107 + y++ * 10, Font.standard, ">" + answer.getTitle());
				if (hovered && mouseDown && !prevMouseDown && !dialogActivatedNow) {
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

				screen.renderSprite(itemX, itemY, i.getSprite());

				if (!animationPlaying && mouseClick && mx >= itemX && my >= itemY && mx < itemX + 16 && my < itemY + 16)
					selectedItem = itemN;

				itemY += 12;
				itemN++;
				if (itemN % 3 == 0) {
					itemY -= 12 * 3 - 2;
					itemX += 10;
				}
			}
		}

		if (revealingItems != null) {
			renderItemReveal(screen);
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

	int ticks = 0;

	@Override
	public void update(double delta) {
		if (won) {
			++ticks;
			if (ticks % 4 == 0)
				wonFrame--;
			return;
		}

		if (gameOver) {
			gameOverTipArea.update();
			return;
		}

		t += delta;

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
				currentScene.leave();
				currentScene = pendingScene;
				currentScene.enter(prevScene);

				// IScene.enter() could change scene
				if (prevScene != null) {
					if (prevScene.getBackgroundMusic() != currentScene.getBackgroundMusic()) {
						Sound backgroundMusic = prevScene.getBackgroundMusic();
						if (backgroundMusic != null)
							backgroundMusic.stop();

						backgroundMusic = currentScene.getBackgroundMusic();
						if (backgroundMusic != null)
							backgroundMusic.playInfinitely();
					}

					prevScene = pendingScene = null;
				}
			}

			if (transitionTime >= transitionDuration)
				currentTransition = null;
		}

		if (revealingItems != null)
			timeSinceItemRevealStart += 1.0 / 30.0;
	}

	public void setScene(IScene scene, ISceneTransition transition) {
		if (currentTransition != null)
			return;

		if (transition == null) {
			jumpToScene(scene);
		} else {
			currentTransition = transition;
			transitionTime = 0;

			prevScene = currentScene;
			pendingScene = scene;
		}
	}

	public void setScene(IScene scene) {
		if (currentTransition != null) {
			pendingScene = null;
			prevScene = null;
			currentTransition = null;
		}
		jumpToScene(scene);
	}

	private void jumpToScene(IScene scene) {
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

	public void showMessage(String message, ITextAreaListener listener) {
		messageTextArea.showText(message, listener);
	}

	public void showMessage(String message) {
		showMessage(message, null);
	}

	public void addQuest(IQuest quest) {
		quest.activate();
		quests.add(quest);
	}

	public void finishQuest(IQuest quest) {
		List<Item> rewards = quest.getRewards();
		items.addAll(rewards);

		quests.remove(quest);
		finishedQuests.add(quest);

		if (rewards.size() > 0)
			revealItems((Item[]) rewards.toArray());
	}

	public <T> T getQuest(Class<T> t) {
		for (IQuest q : quests)
			if (t.isInstance(q))
				return (T) q;
		return null;
	}

	public boolean isQuestFinished(Class<?> t) {
		for (IQuest q : finishedQuests)
			if (t.isInstance(q))
				return true;
		return false;
	}

	public Random random;

	Sprite gameOverBG;
	boolean gameOver = false;
	int gameOverFrame;
	TextArea gameOverTipArea = new TextArea(1, 106, 198, 100, Font.white);

	private static final String[] WIN_TEXT_LINES = {
		"\"Der Profit...", "", "", "",
		"...der kommt zwar vom Casino...","", "", "",
		"...aber der Verlust...","", "", "",
		"der kommt von dem Rest\"","", "", "",
		"-Anonynm","", "", "",
		"(Beziehungsweise Ich eben)","", "", "",
		"Das Team bestand aus 3 Leuten...","", "", "",
		"Zwei Programmierer/Designer...","", "", "",
		"...und ein Musiker","", "", "",
		"(Das war ich)","", "", "",
		"...und das waren die Credits!","", "", "",
		"Die Musik ist schon schön, ne","", "", "",
		"Hab ich selbst gemacht", "", "", "",
		"Okay, zugegeben...","", "", "",
		"Ich habe mich etwas an Musik", "",
		"von anderen Videospielen", "",
		"orientiert", "", "", "",
		"...und Pink Floyd!","", "", "",
		"Und naja, mehr habe ich nicht", "",
		"zu sagen, außer danke...","", "", "",
		"...für's Spielen!","", "", "",
		"P.s. Ich bin nicht mal Musiker","", "", "",
		"Ich habe nicht mal eine Band!","", "", "",
		"Und das ist das einzige Musik-", "",
		"stück,das ich je geschrieben", "",
		"habe", "", "", "",
		"...was sehr gut ist", "", "", "",
		"Das kann ich irgendwo hochladen", "", "", "",
		"Und alle werden neidisch!", "", "", "",
		"Mozart kann einpacken", "", "", "",

	};
	boolean won = false;
	int wonFrame;

	double t = 0;
	Sprite dialogIndicator;

	IScene currentScene;

	IScene pendingScene, prevScene;
	ISceneTransition currentTransition;
	double transitionDuration = 0.5;
	double transitionTime = 0;

	TextArea messageTextArea;
	TextArea detailTextArea;

	String currentDetail;

	public Player player;

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
		int itemsToAdd = Math.min(count, max - existing);
		for (int i = 0; i < itemsToAdd; i++)
			items.add(item);

		if (itemsToAdd > 0) {
			Item[] addedItems = new Item[itemsToAdd];
			Arrays.fill(addedItems, item);
			revealItems(addedItems);
		}

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

	public boolean removeItem(Item item, int count) {
		int existing = 0;
		for (Item i : items)
			if (i == item)
				existing++;
		if (existing < count)
			return false;
		for (int i = items.size() - 1; i >= 0; i--)
			if (items.get(i) == item) {
				items.remove(i);
				count--;
				if (count == 0)
					return true;
			}
		throw new Error("Impossible to reach");
	}

	public void pushDialog(Dialog child) {
		queuedDialogs.add(child);
	}

	public void die(String tip) {
		gameOver = true;
		if (currentScene.getBackgroundMusic() != null)
			currentScene.getBackgroundMusic().stop();
		Sounds.death.play();
		gameOverFrame = 75;
		gameOverTipArea.setMaxLineCount(50);
		gameOverTipArea.setAnimated(true);
		gameOverTipArea.showText(tip);
	}

	public void win() {
		won = true;
		if (currentScene.getBackgroundMusic() != null)
			currentScene.getBackgroundMusic().stop();
		wonFrame = 150;
	}

	public List<Item> items = new ArrayList<>();
	public List<IQuest> quests = new ArrayList<>();
	public List<IQuest> finishedQuests = new ArrayList<>();
	public List<Dialog> queuedDialogs = new ArrayList<>();
	Dialog activeDialog = null;
	public int selectedItem = -1;

	SpriteSheet clickSheet;
	SpriteAnimation clickAnimation;
	int lastClickX, lastClickY;

	Item revealingItems[] = null;
	double timeSinceItemRevealStart = 0;
}
