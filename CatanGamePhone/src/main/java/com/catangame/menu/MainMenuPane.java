package com.catangame.menu;

import com.catangame.core.CatanConfiguration;
import com.catangame.model.game.Game;
import com.catangame.model.game.Player;
import com.catangame.util.FXUtils;
import com.catangame.view.GameView;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainMenuPane extends AnchorPane {

	private VBox vbox;

	private Button newLocalGameButton;
	private Button createLobbyButton;
	private Button findGameButton;

	private CatanConfiguration catanConfiguration = CatanConfiguration.getInstance();

	private Player player;

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
		GameView view = new GameView(new Game(), getUserPlayer());
		getScene().setRoot(view.getMapView());
		view.start();
		view.draw();
		event.consume();
	}

	private void createLobbyAction(ActionEvent event) {
		LobbyView view = new LobbyView(getUserPlayer());
		getScene().setRoot(view);
		event.consume();
	}

	private void findGameAction(ActionEvent event) {
		FindLobbyView view = new FindLobbyView(getUserPlayer());
		newLocalGameButton.getScene().setRoot(view);
		event.consume();
	}

	private Player getUserPlayer() {
		if (player == null) {
			player = catanConfiguration.loadPlayerDetails(getScene().getWindow()).orElse(null); 
		}
		return player;		
	}
}
