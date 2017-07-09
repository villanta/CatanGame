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
	
	public ChatView() {
		super(10.0);
		initialiseFX();
	}

	private void initialiseFX() {
		messageLog = new ListView<>(log);
		chatEntryView = new ChatEntryView();		
		super.getChildren().addAll(messageLog, chatEntryView);
		
		chatEntryView.setOnMessageEntry(this::onMessageEntry);
	}
	
	private Void onMessageEntry(String message) {
		
		return (Void) null;
	}
}
