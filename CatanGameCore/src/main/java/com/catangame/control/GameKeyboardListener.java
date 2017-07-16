package com.catangame.control;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameKeyboardListener {

	private GameViewListener listener;

	public GameKeyboardListener(GameViewListener listener) {
		this.listener = listener;
	}
	
	public void onKeyPressedEvent(KeyEvent event) {
		KeyCode code = event.getCode();
		if (code.equals(KeyCode.ESCAPE)) {
			listener.toggleMenu();
		}
		event.consume();
	}

}
