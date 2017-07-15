package com.catangame.menu;

import com.catangame.comms.interfaces.ChatService;
import com.catangame.comms.listeners.ChatEventListener;
import com.catangame.model.game.Player;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ChatView extends VBox implements ChatEventListener {

	private ListView<Label> messageLog;
	private ObservableList<Label> log = FXCollections.observableArrayList();

	public ChatView(ChatService chatInterface, Player player) {
		super(10.0);
		initialiseFX(chatInterface, player);
		chatInterface.addListener(this);
	}

	private void initialiseFX(ChatService chatService, Player player) {
		messageLog = new ListView<>(log);
		ChatEntryView chatEntryView = new ChatEntryView(player, chatService);
		super.getChildren().addAll(messageLog, chatEntryView);
	}

	@Override
	public void newMessage(String message) {
		Platform.runLater(() -> {
			Label messageLabel = new Label(message);
			log.add(messageLabel);
			messageLog.scrollTo(messageLabel);
		});
	}
}