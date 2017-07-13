package com.catangame.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ChatEntryView extends HBox {

	private Button messageEntryButton;
	private TextField messageEntryField;

	private ChatInterface chatInterface;

	public ChatEntryView(ChatInterface chatInterface) {
		super(10.0);
		this.chatInterface = chatInterface;
		initialiseFX();
	}

	private void initialiseFX() {
		messageEntryField = new TextField("");
		messageEntryButton = new Button("Send");

		messageEntryButton.setOnAction(this::onMessageEntry);

		super.getChildren().addAll(messageEntryField, messageEntryButton);
	}

	private void onMessageEntry(ActionEvent event) {
		chatInterface.sendMessage(messageEntryField.getText());
		event.consume();
	}
}
