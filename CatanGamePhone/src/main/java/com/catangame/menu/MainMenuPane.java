package com.catangame.menu;

import com.catangame.comms.client.GameClient;
import com.catangame.game.GameView;
import com.catangame.util.FXUtils;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainMenuPane extends AnchorPane {

	private VBox vbox;
	private Button newGameButton;
	private Button findGameButton;
	private GameClient client;

	public MainMenuPane(GameClient client) {
		this.client = client;
		initialiseFX();
	}

	private void initialiseFX() {
		createVbox();
		createButtons();

	}

	private void createButtons() {
		newGameButton = new Button("Start a game");
		findGameButton = new Button("Find a game");

		newGameButton.setOnAction(this::newGameAction);
		findGameButton.setOnAction(this::findGameAction);

		vbox.getChildren().addAll(newGameButton, findGameButton);
	}

	private void createVbox() {
		vbox = new VBox(10.0);

		super.getChildren().add(vbox);
		FXUtils.setAllAnchors(vbox, 10.0);
	}

	private void newGameAction(ActionEvent event) {
		GameView view = new GameView();
		Scene scene = newGameButton.getScene();
		scene.setRoot(view.getMapView());
		view.start();
		view.draw();
	}

	private void findGameAction(ActionEvent event) {
		new Thread(() -> {
			if (!client.findServer()) {
				System.err.println("Failed to find a server...");
			}
		}).start();
	}
}
