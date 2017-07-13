package com.catangame.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ChatView extends VBox {

	private ListView<Label> messageLog;
	private ObservableList<Label> log = FXCollections.observableArrayList();
	private ChatEntryView chatEntryView;

	public ChatView(ChatInterface chatInterface) {
		super(10.0);
		initialiseFX(chatInterface);
	}

	private void initialiseFX(ChatInterface chatInterface) {
		messageLog = new ListView<>(log);
		chatEntryView = new ChatEntryView(chatInterface);
		super.getChildren().addAll(messageLog, chatEntryView);
	}

	public void addMessageToLog(String message) {

	}
}
