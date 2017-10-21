package com.naronco.infinityjam;

import com.deviotion.ld.eggine.Eggine;
import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.Window;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.naronco.infinityjam.scenes.Bedroom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game extends Eggine {
	Sprite ui;

	public static Game instance;

	public static final int MODE_LOOK = 0;
	public static final int MODE_USE = 1;
	public static final int MODE_TAKE = 2;
	public static final int MODE_PUNCH = 3;

	public Game() {
		super(60, 30, new Window("InfinityJam", new Dimension2d(200, 150), 4));

		instance = this;

		currentScene = new Bedroom();

		ui = new Sprite(new File("res/ui.png"));
	}

	static final int LEFT_BUTTON_WIDTH = 63;
	static final int RIGHT_BUTTON_WIDTH = 119 - 63;
	static final int BUTTON_HEIGHT = 123 - 100;

	@Override
	public void render(Screen screen) {
		screen.renderSprite(0, 0, ui);

		int mx = (int)getMouse().getLocation().getX();
		int my = (int)getMouse().getLocation().getY();
		if (mx < 63) {
			if (my > 123) {
				focusedButton = MODE_TAKE;
				screen.mixRectangle(0, 106 + BUTTON_HEIGHT, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			} else if (my > 96) {
				focusedButton = MODE_LOOK;
				screen.mixRectangle(0, 106, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			}
		}
		else if (mx < 122) {
			if (my > 123) {
				focusedButton = MODE_PUNCH;
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 106 + BUTTON_HEIGHT, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			} else if (my > 96) {
				focusedButton = MODE_USE;
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 106, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			}
		}
		else focusedButton = -1;

		currentScene.render(screen);

		if (currentDetail != null) {
			screen.mixRectangle(mx, my, 10, 10, 0x80FF0000);
		}

		if (activeMessage != null) {
			String partialMessage = activeMessage.substring(0, visibleCharacters);
			screen.renderText(0, 87, Font.standard, partialMessage);
		}

		prevMx = mx;
		prevMy = my;

		boolean mouseDown = getMouse().isLeftClicking();
		if (mouseDown && !prevMouseDown)
		{
			if (my < 86)
				currentScene.click(mx, my, mode);
			mode = focusedButton;
		}
		prevMouseDown = mouseDown;
	}

	@Override
	public void update(double delta) {
		currentScene.update();
		if (prevMy < 86)
			currentDetail = currentScene.detailAt(prevMx, prevMy);
		else
			currentDetail = null;

		if (buildingMessage) {
			++ticksSinceLastCharacter;

			if (ticksSinceLastCharacter >= ticksPerCharacter) {
				++visibleCharacters;

				if (visibleCharacters == activeMessage.length()) {
					buildingMessage = false;
				}

				ticksSinceLastCharacter = 0;
			}
		}
	}

	public void setScene(IScene scene) {
		currentScene = scene;
	}

	public void showMessage(String message) {
		String[] words = message.split(" ");

		StringBuilder messageBuilder = new StringBuilder();
		StringBuilder currentLine = new StringBuilder();

		for (String word : words) {
			int newLength = currentLine.length() + word.length();
			boolean overBounds = newLength * Font.standard.getCharacterSize().getWidth() >= getWindow().getScreen().getDimension().getWidth();

			if (overBounds) {
				messageBuilder.append(currentLine).append('\n');
				currentLine = new StringBuilder();
			}

			currentLine.append(word).append(' ');
		}

		if (currentLine.length() > 0) {
			messageBuilder.append(currentLine);
		}

		activeMessage = messageBuilder.toString();
		visibleCharacters = 1;
		ticksSinceLastCharacter = 0;
		buildingMessage = true;
	}

	IScene currentScene;

	String activeMessage;
	boolean buildingMessage = false;
	int visibleCharacters;
	int ticksSinceLastCharacter;
	int ticksPerCharacter;

	String currentDetail;

	int prevMx, prevMy;
	boolean prevMouseDown;

	int focusedButton = -1;
	int mode = -1;
}
