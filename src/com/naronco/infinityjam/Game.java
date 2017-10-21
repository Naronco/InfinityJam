package com.naronco.infinityjam;

import com.deviotion.ld.eggine.Eggine;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.Window;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.naronco.infinityjam.scenes.Bedroom;

import java.io.File;

public class Game extends Eggine {
	Sprite ui;

	public static Game instance;

	public Game() {
		super(60, 30, new Window("InfinityJam", new Dimension2d(200, 150), 4));

		instance = this;

		currentScene = new Bedroom();

		ui = new Sprite(new File("res/ui.png"));
	}

	static final int LEFT_BUTTON_WIDTH = 63;
	static final int RIGHT_BUTTON_WIDTH = 119 - 63;
	static final int BUTTON_HEIGHT = 123 - 96;

	@Override
	public void render(Screen screen) {
		screen.fillScreen(0x000000);

		screen.renderSprite(0, 0, ui);

		int mx = (int)getMouse().getLocation().getX();
		int my = (int)getMouse().getLocation().getY();
		if (mx < 63) {
			if (my > 123) {
				focusedButton = 2;
				screen.mixRectangle(0, 123, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			} else if (my > 96) {
				focusedButton = 0;
				screen.mixRectangle(0, 96, LEFT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			}
		}
		else if (mx < 122) {
			if (my > 123) {
				focusedButton = 3;
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 123, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			} else if (my > 96) {
				focusedButton = 1;
				screen.mixRectangle(LEFT_BUTTON_WIDTH, 96, RIGHT_BUTTON_WIDTH, BUTTON_HEIGHT, 0x40000000);
			}
		}
		else focusedButton = -1;

		currentScene.render(screen);

		if (currentDetail != null) {
			screen.mixRectangle(mx, my, 10, 10, 0x80FF0000);
		}

		prevMx = mx;
		prevMy = my;

		boolean mouseDown = getMouse().isLeftClicking();
		if (mouseDown && !prevMouseDown)
		{
			currentScene.click(mx, my, mode);
			mode = focusedButton;
		}
		prevMouseDown = mouseDown;

		screen.setPixel(mx, my, 0x000000);
	}

	@Override
	public void update(double delta) {
		currentScene.update();
		currentDetail = currentScene.detailAt(prevMx, prevMy);
	}

	public void setScene(IScene scene) {
		currentScene = scene;
	}

	public void showMessage(String message) {
		activeMessage = message;
	}

	IScene currentScene;
	String activeMessage;

	String currentDetail;

	int prevMx, prevMy;
	boolean prevMouseDown;

	int focusedButton = -1;
	int mode = -1;
}
