package com.deviotion.ld.eggine.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
	private Clip clip;

	public Sound(File file) throws Exception {
		this.clip = AudioSystem.getClip();

		AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);

		clip.open(inputStream);
	}

	public void playInfinitely() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

	public Clip getClip() { return clip; }
}
