package com.catangame.view;

import com.catangame.comms.interfaces.ChatService;
import com.catangame.comms.listeners.ChatEventListener;
import com.catangame.model.game.Player;
import com.esotericsoftware.minlog.Log;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ChatView extends VBox implements ChatEventListener {

	private ListView<Label> messageLog;
	private ObservableList<Label> log = FXCollections.observableArrayList();

	private ReadOnlyDoubleProperty bottomOffsetProperty;

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

	public void setBottomOffsetProperty(ReadOnlyDoubleProperty readOnlyDoubleProperty) {
		this.bottomOffsetProperty = readOnlyDoubleProperty;
	}

	@Override
	public void newMessage(String message) {
		Platform.runLater(() -> {
			Label messageLabel = new Label(message);
			log.add(messageLabel);
			messageLog.scrollTo(messageLabel);
		});
	}

	public void popUp(ObservableList<Node> children) {
		setScaleX(0.0);
		setScaleY(0.0);
		Platform.runLater(() -> children.add(this));
		new Thread(() -> {
			double chatScale = 0.0;
			while (chatScale < 1.0) {
				chatScale += 0.05;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					Log.error("Error Sleeping");
					updateChatScale(1.0);
				}
				if (chatScale > 1.0) {
					chatScale = 1.0;
				}
				updateChatScale(chatScale);
			}

		}).start();
	}

	public void popDown(ObservableList<Node> children) {
		setScaleX(0.0);
		setScaleY(0.0);
		new Thread(() -> {
			double chatScale = 1.0;
			while (chatScale > 0.0) {
				chatScale -= 0.05;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					Log.error("Error Sleeping");
					updateChatScale(0.0);
				}
				if (chatScale < 0.0) {
					chatScale = 0.0;
				}
				updateChatScale(chatScale);
			}
			Platform.runLater(() -> children.remove(this));
		}).start();
	}

	private void updateChatScale(double scale) {
		Platform.runLater(() -> {
			setScaleX(scale);
			setScaleY(scale);

			double xOffset = (getWidth() - (getWidth() * scale)) / 2.0;
			double yOffset = (getHeight() - (getHeight() * scale)) / 2.0;

			AnchorPane.setBottomAnchor(this, 30.0 + getBottomOffset() - yOffset);
			AnchorPane.setRightAnchor(this, 20.0 - xOffset);
		});
	}

	private double getBottomOffset() {
		if (bottomOffsetProperty == null) {
			return 0.0;
		} else {
			return bottomOffsetProperty.get();
		}
	}
}