package com.catangame.menu;

import java.util.function.Function;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ChatEntryView extends HBox {

	private Function<String, Void> callback;
	private Button messageEntryButton;
	private TextField messageEntryField;

	public ChatEntryView() {
		super(10.0);
		initialiseFX();
	}

	private void initialiseFX() {
		messageEntryField = new TextField("");
		messageEntryButton = new Button("Send");
		
		super.getChildren().addAll(messageEntryField, messageEntryButton);
	}

	public void setOnMessageEntry(Function<String, Void> callback) {
		this.callback = callback;
	}
}
