package com.naronco.infinityjam.dialog;

import com.naronco.infinityjam.Game;

public class Dialog {
	public String title;
	public IAnswer[] answers;

	public Dialog(String title, IAnswer... answers) {
		this.title = title;
		this.answers = answers;
	}
}
