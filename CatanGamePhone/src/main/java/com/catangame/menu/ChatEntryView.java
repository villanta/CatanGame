package com.catangame.menu;

import com.catangame.comms.interfaces.ChatService;
import com.catangame.game.Player;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ChatEntryView extends HBox {

	private Button messageEntryButton;
	private TextField messageEntryField;

	private ChatService chatService;
	private Player player;

	public ChatEntryView(Player player, ChatService chatService) {
		super(10.0);
		this.player = player;
		this.chatService = chatService;
		initialiseFX();
	}

	private void initialiseFX() {
		messageEntryField = new TextField("");
		messageEntryButton = new Button("Send");

		messageEntryButton.setOnAction(this::onMessageEntry);
		messageEntryField.setOnAction(this::onMessageEntry);

		super.getChildren().addAll(messageEntryField, messageEntryButton);
	}

	private void onMessageEntry(ActionEvent event) {
		String messageText = messageEntryField.getText();
		if (!messageText.trim().isEmpty()) {
			chatService.sendMessage(player, messageText);
			messageEntryField.setText("");
		}
		event.consume();
	}
}
