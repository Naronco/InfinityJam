package com.naronco.infinityjam;

import com.deviotion.ld.eggine.sound.Sound;

import java.io.File;

public class Sounds {
	public static Sound casino = load("casino.wav");
	public static Sound cityTheme = load("citytheme.wav");
	public static Sound home = load("home.wav");
	public static Sound miniGame = load("minigame.wav");

	private static Sound load(String name) {
		try {
			return new Sound(new File("res/music/" + name));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
