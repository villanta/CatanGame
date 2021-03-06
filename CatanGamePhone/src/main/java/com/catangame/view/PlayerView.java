package com.catangame.view;

import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.interfaces.PingListener;
import com.catangame.comms.messages.lobby.PingMessage;
import com.catangame.model.game.Player;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class PlayerView extends HBox implements PingListener {

	private Player player;
	private Label nameLabel;
	private Circle colourIndicator;
	private Label pingLabel;
	private Button kickButton;

	private LobbyService lobbyService;

	public PlayerView(Player player, LobbyService lobbyService) {
		super(10.0);
		this.player = player;
		this.lobbyService = lobbyService;
		this.lobbyService.addPingListener(this);
		initialiseFX();
		super.setFillHeight(true);
	}

	private void initialiseFX() {
		colourIndicator = new Circle();
		colourIndicator.setFill(player.getColor());
		colourIndicator.radiusProperty().bind(heightProperty().divide(2).subtract(5));

		nameLabel = new Label(player.getName());
		pingLabel = new Label("Ping: 0ms");
		kickButton = new Button("Kick");
		kickButton.setOnAction(this::kickAction);

		if (!lobbyService.isServer() || player.getId() == 0) {
			kickButton.setVisible(false);
		}

		super.getChildren().addAll(colourIndicator, nameLabel, pingLabel, kickButton);
	}

	private void kickAction(ActionEvent event) {
		lobbyService.kickPlayer(player);
		event.consume();
	}

	@Override
	public void updatePing(PingMessage pingMessage) {
		Platform.runLater(() -> pingLabel
				.setText(String.format("Ping: %dms", pingMessage.getPlayerIdToPingMap().get(player.getId()))));
	}
}
