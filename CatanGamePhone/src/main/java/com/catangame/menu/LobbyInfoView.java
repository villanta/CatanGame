package com.catangame.menu;

import com.catangame.Lobby;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.esotericsoftware.kryonet.Connection;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LobbyInfoView extends HBox {
	
	private Lobby lobby;
	private Connection connection;
	
	private Label nameLabel;
	private Label playersLabel;
	private Button joinButton;
	private FindLobbyView findLobbyView;
	
	public LobbyInfoView(LobbyInfoResponse lobbyInfoMessage, Connection connection, FindLobbyView findLobbyView) {
		super(10.0);
		this.lobby = lobbyInfoMessage.getLobby();
		this.connection = connection;
		this.findLobbyView = findLobbyView;
		initialiseFX();
	}

	private void initialiseFX() {
		nameLabel = new Label(lobby.getLobbyName());
		playersLabel = new Label(String.format("Players: %d", lobby.getPlayerCount()));
		joinButton = new Button("Join");
		joinButton.setOnAction(this::joinLobby);
		super.getChildren().addAll(nameLabel, playersLabel, joinButton);
	}
	
	private void joinLobby(ActionEvent event) {
		findLobbyView.connectToLobby(connection);
	}

}
