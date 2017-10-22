package com.naronco.infinityjam.dialog;

import com.deviotion.ld.eggine.graphics.ITextAreaListener;
import com.naronco.infinityjam.Game;

public class Dialog {
	public String title;
	public IAnswer[] answers;
	public ITextAreaListener listener;

	public Dialog(String title, IAnswer... answers) {
		this.title = title;
		this.answers = answers;
	}

	public Dialog(String title, ITextAreaListener listener, IAnswer... answers) {
		this.title = title;
		this.answers = answers;
		this.listener = listener;
	}
}
