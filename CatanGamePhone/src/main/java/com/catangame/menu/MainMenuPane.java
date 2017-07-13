package com.catangame.menu;

import com.catangame.game.GameView;
import com.catangame.util.FXUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainMenuPane extends AnchorPane {

	private VBox vbox;

	private Button newLocalGameButton;
	private Button createLobbyButton;
	private Button findGameButton;
	
	public MainMenuPane() {
		initialiseFX();
	}

	private void initialiseFX() {
		createVbox();
		createButtons();
	}

	private void createButtons() {
		newLocalGameButton = new Button("Start a game");
		createLobbyButton = new Button("Create a lobby");
		findGameButton = new Button("Find a game");

		newLocalGameButton.setOnAction(this::newGameAction);
		createLobbyButton.setOnAction(this::createLobbyAction);
		findGameButton.setOnAction(this::findGameAction);

		vbox.getChildren().addAll(newLocalGameButton, createLobbyButton, findGameButton);
	}

	private void createVbox() {
		vbox = new VBox(10.0);

		super.getChildren().add(vbox);
		FXUtils.setAllAnchors(vbox, 10.0);
	}

	private void newGameAction(ActionEvent event) {
		GameView view = new GameView();
		newLocalGameButton.getScene().setRoot(view.getMapView());
		view.start();
		view.draw();
		event.consume();
	}

	private void createLobbyAction(ActionEvent event) {
		LobbyView view = new LobbyView();
		newLocalGameButton.getScene().setRoot(view);
		event.consume();
	}

	private void findGameAction(ActionEvent event) {
		FindLobbyView view = new FindLobbyView();
		newLocalGameButton.getScene().setRoot(view);
		event.consume();
	}
}
