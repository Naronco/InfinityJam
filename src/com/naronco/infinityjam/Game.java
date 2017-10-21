package com.naronco.infinityjam;

import com.deviotion.ld.eggine.Eggine;
import com.deviotion.ld.eggine.graphics.*;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.naronco.infinityjam.scenes.Bedroom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Game extends Eggine {
	Sprite ui;

	public static Game instance;

	public static final int MODE_WALK = -1;
	public static final int MODE_LOOK = 0;
	public static final int MODE_USE = 1;
	public static final int MODE_TAKE = 2;
	public static final int MODE_PUNCH = 3;

	public Game() {
		super(60, 30, new Window("InfinityJam", new Dimension2d(200, 150), 4));

		instance = this;

		currentScene = new Bedroom();

		player = new Character(80, 40);

		ui = new Sprite(new File("res/ui.png"));

		messageTextArea = new TextArea(0, 87, 200, 18, Font.standard);
	}

	static final int LEFT_BUTTON_WIDTH = 63;
	static final int RIGHT_BUTTON_WIDTH = 119 - 63;
	static final int BUTTON_HEIGHT = 123 - 100;

	@Override
	public void render(Screen screen) {
		screen.renderSprite(0, 0, ui);

		int mx = (int) getMouse().getLocation().getX();
		int my = (int) getMouse().getLocation().getY();
		if (mx < 63) {
			if (my > 123) {
				focusedButton = MODE_TAKE;
				screen.mixRectangle(0, 106 + BUTTON_HEIGHT, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			} else if (my > 96) {
				focusedButton = MODE_LOOK;
				screen.mixRectangle(0, 106, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			}
		} else if (mx < 122) {
			if (my > 123) {
				focusedButton = MODE_PUNCH;
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 106 + BUTTON_HEIGHT, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			} else if (my > 96) {
				focusedButton = MODE_USE;
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 106, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			}
		} else focusedButton = MODE_WALK;

		currentScene.renderBackground(screen);
		screen.renderAnimatedSprite(player.getSpritePosition(), player);
		player.nextFrame();
		currentScene.renderForeground(screen);

		if (currentDetail != null) {
			screen.mixRectangle(mx, my, 10, 10, 0x80FF0000);
		}

		messageTextArea.render(screen);

		prevMx = mx;
		prevMy = my;

		boolean mouseDown = getMouse().isLeftClicking();
		if (mouseDown && !prevMouseDown) {
			if (my < 86) {
				currentScene.click(mx, my, mode);
				mode = MODE_WALK;
			} else
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

		messageTextArea.update();
	}

	public void setScene(IScene scene) {
		currentScene = scene;
	}

	public void showMessage(String message) {
		messageTextArea.showText(message);
	}

	IScene currentScene;

	TextArea messageTextArea;

	String currentDetail;

	public Character player;

	int prevMx, prevMy;
	boolean prevMouseDown;

	int focusedButton = -1;
	int mode = -1;
}
