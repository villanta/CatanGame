package com.catangame.menu;

import com.catangame.game.Player;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

public class PlayerView extends HBox {

	private Player player;
	private Label nameLabel;
	private AnchorPane colourIndicator;
	private Label pingLabel;
	private Button kickButton;

	public PlayerView(Player player) {
		this.player = player;
		initialiseFX();
	}

	private void initialiseFX() {
		colourIndicator = new AnchorPane();
		colourIndicator
				.setBackground(new Background(new BackgroundFill(player.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
		colourIndicator.prefWidthProperty().bind(colourIndicator.heightProperty());
		nameLabel = new Label(player.getName());
		pingLabel = new Label("Ping: 30ms");
		kickButton = new Button("Kick");
		
		super.getChildren().addAll(colourIndicator, nameLabel, pingLabel, kickButton);
	}
}
