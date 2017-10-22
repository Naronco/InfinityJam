package com.naronco.infinityjam.scenes;

import com.deviotion.ld.eggine.graphics.Font;
import com.deviotion.ld.eggine.graphics.Screen;
import com.deviotion.ld.eggine.graphics.Sprite;
import com.deviotion.ld.eggine.graphics.SpriteSheet;
import com.deviotion.ld.eggine.math.Dimension2d;
import com.deviotion.ld.eggine.math.Vector2d;
import com.deviotion.ld.eggine.sound.Sound;
import com.naronco.infinityjam.*;
import com.naronco.infinityjam.quests.TripleSevenQuest;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SlotMachine implements IScene {
	private static int gameCount = 0;

	private Sprite background;
	private SpriteSheet slotsSheet;
	private SlotMachineRoll[] rolls;
	private boolean rolling = false;
	private SlotMachineSymbol[] currentResult = null;

	private static final SlotMachineSymbol[][] RESULTS = {
			{ SlotMachineSymbol.cherry, SlotMachineSymbol.cloverleaf, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.euro, SlotMachineSymbol.cherry },
			{ SlotMachineSymbol.seven, SlotMachineSymbol.heart, SlotMachineSymbol.cloverleaf },
			{ SlotMachineSymbol.seven, SlotMachineSymbol.seven, SlotMachineSymbol.seven },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.seven, SlotMachineSymbol.euro },
			{ SlotMachineSymbol.coins, SlotMachineSymbol.heart, SlotMachineSymbol.cloverleaf },
			{ SlotMachineSymbol.cherry, SlotMachineSymbol.bomb, SlotMachineSymbol.heart },
			/*{ SlotMachineSymbol.diamond, SlotMachineSymbol.bomb, SlotMachineSymbol.seven },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.diamond, SlotMachineSymbol.cloverleaf },
			{ SlotMachineSymbol.seven, SlotMachineSymbol.seven, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.euro, SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cloverleaf },
			{ SlotMachineSymbol.heart, SlotMachineSymbol.heart, SlotMachineSymbol.coins },
			{ SlotMachineSymbol.seven, SlotMachineSymbol.seven, SlotMachineSymbol.dollar },
			{ SlotMachineSymbol.seven, SlotMachineSymbol.heart, SlotMachineSymbol.heart },
			{ SlotMachineSymbol.diamond, SlotMachineSymbol.heart, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cloverleaf, SlotMachineSymbol.heart },
			{ SlotMachineSymbol.euro, SlotMachineSymbol.heart, SlotMachineSymbol.cloverleaf },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.diamond, SlotMachineSymbol.heart },
			{ SlotMachineSymbol.heart, SlotMachineSymbol.dollar, SlotMachineSymbol.euro },
			{ SlotMachineSymbol.coins, SlotMachineSymbol.heart, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.dollar, SlotMachineSymbol.dollar },
			{ SlotMachineSymbol.coins, SlotMachineSymbol.coins, SlotMachineSymbol.diamond },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cherry, SlotMachineSymbol.coins },
			{ SlotMachineSymbol.bomb, SlotMachineSymbol.heart, SlotMachineSymbol.seven },
			{ SlotMachineSymbol.coins, SlotMachineSymbol.euro, SlotMachineSymbol.dollar },
			{ SlotMachineSymbol.euro, SlotMachineSymbol.coins, SlotMachineSymbol.dollar },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.seven, SlotMachineSymbol.euro },
			{ SlotMachineSymbol.heart, SlotMachineSymbol.cherry, SlotMachineSymbol.coins },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.dollar, SlotMachineSymbol.diamond },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.heart, SlotMachineSymbol.coins },
			{ SlotMachineSymbol.coins, SlotMachineSymbol.seven, SlotMachineSymbol.heart },
			{ SlotMachineSymbol.cherry, SlotMachineSymbol.euro, SlotMachineSymbol.euro },
			{ SlotMachineSymbol.seven, SlotMachineSymbol.dollar, SlotMachineSymbol.euro },
			{ SlotMachineSymbol.cherry, SlotMachineSymbol.euro, SlotMachineSymbol.cherry },
			{ SlotMachineSymbol.euro, SlotMachineSymbol.euro, SlotMachineSymbol.heart },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.cherry, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cloverleaf },
			{ SlotMachineSymbol.diamond, SlotMachineSymbol.cherry, SlotMachineSymbol.diamond },
			{ SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cherry, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.dollar, SlotMachineSymbol.cherry, SlotMachineSymbol.bomb },
			{ SlotMachineSymbol.coins, SlotMachineSymbol.cloverleaf, SlotMachineSymbol.cherry },*/
	};

	public SlotMachine() {
		this.background = new Sprite(new File("res/slotmachine.png"));
		this.slotsSheet = new SpriteSheet(new Sprite(new File("res/slots.png")), new Dimension2d(20, 20));

		this.rolls = new SlotMachineRoll[3];
		for (int i = 0; i < rolls.length; ++i) {
			List<SlotMachineSymbol> slots = Arrays.asList(SlotMachineSymbol.values());
			Collections.shuffle(slots);
			rolls[i] = new SlotMachineRoll(slotsSheet, (SlotMachineSymbol[])slots.toArray());
		}
	}

	@Override
	public void load() {
	}

	@Override
	public void enter(IScene prev) {
	}

	@Override
	public void leave() {
	}

	@Override
	public String detailAt(int x, int y) {
		if (x < 15 && y < 15) {
			return "Zurück";
		}
		if (x < 15 || x > 30)
			return null;
		for (SlotMachineRoll roll : rolls) {
			if (roll.isRolling())
				return null;
		}
		return "Spielen";
	}

	@Override
	public void click(int x, int y, int mode) {
		if (x < 15 && y < 15) {
			Game.instance.setScene(Game.instance.casino);
			return;
		}
		if (x < 15 || x > 30)
			return;
		for (SlotMachineRoll roll : rolls) {
			if (roll.isRolling())
				return;
		}
		if (mode == Game.MODE_USE) {
			if (Game.instance.getSelectedItem() == Item.COINS) {
				Game.instance.removeItem(Item.COINS, 1);
				currentResult = RESULTS[gameCount % RESULTS.length];
				for (int i = 0; i < rolls.length; ++i) {
					rolls[i].roll(currentResult[i]);
					rolling = true;
				}
				Sounds.casinoRoll.play();
				++gameCount;
				Game.instance.showMessage("na dann mal viel glück");
			} else {
				Game.instance.showMessage("ich sollte eine münze einwerfen");
			}
		}
	}

	@Override
	public void renderBackground(Screen screen) {
		screen.renderSprite(0, 0, background);
	}

	@Override
	public void renderForeground(Screen screen) {
		for (int i = 0; i < rolls.length; ++i) {
			SlotMachineRoll roll = rolls[i];
			roll.render(screen, i * 33 + 57, -5);
		}
		screen.renderSprite(0, 0, Sprites.ARROW);
	}

	@Override
	public void update() {
		for (SlotMachineRoll roll : rolls) {
			roll.update();
		}

		if (rolling) {
			rolling = false;
			for (SlotMachineRoll roll : rolls) {
				if (roll.isRolling())
					rolling = true;
			}
			if (!rolling) {
				if (currentResult[0] == SlotMachineSymbol.seven && currentResult[1] == SlotMachineSymbol.seven && currentResult[2] == SlotMachineSymbol.seven) {
					Game.instance.finishQuest(Game.instance.getQuest(TripleSevenQuest.class));
					Game.instance.showMessage("3 mal die 7!Das ist der Hauptgewinn!");
				} else {
					Game.instance.showMessage("Dadumm.Leider verloren.1 Münze pro Spiel");
				}
				Sounds.casinoWin.play();
			}
		}
	}

	@Override
	public Sound getBackgroundMusic() {
		return Sounds.casino;
	}

	@Override
	public boolean showsPlayer() {
		return false;
	}
}
