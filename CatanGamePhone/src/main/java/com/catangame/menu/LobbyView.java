package com.catangame.menu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.client.CatanClient;
import com.catangame.comms.messages.lobby.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.LeaveLobbyAction;
import com.catangame.comms.messages.lobby.LobbyInfoMessage;
import com.catangame.comms.messages.lobby.SendMessage;
import com.catangame.comms.server.CatanServer;
import com.catangame.comms.server.ListenerInterface;
import com.catangame.game.GameState;
import com.catangame.game.Player;
import com.catangame.util.FXUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LobbyView extends AnchorPane implements ListenerInterface {

	private static final String FXML_LOCATION = "/com/catangame/view/LobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(LobbyView.class);

	@FXML
	private AnchorPane chatPane;
	private Lobby lobby;

	private ChatView chatView;

	private CatanServer server;
	private CatanClient client;
	private boolean isHost;

	public LobbyView() {
		this.server = new CatanServer();
		server.start();
		server.addListener(this);
		isHost = true;
		lobby = new Lobby("", null, null, GameState.LOBBY);
		loadFXML();
		initialiseFX();
	}

	public LobbyView(CatanClient client, Lobby lobby) {
		this.client = client;
		this.lobby = lobby;
		isHost = false;
		loadFXML();
		initialiseFX();
	}

	private void loadFXML() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LOCATION));
		loader.setController(this);
		try {
			AnchorPane pane = loader.load();
			FXUtils.setAllAnchors(pane, 0.0);
			super.getChildren().add(pane);
		} catch (IOException e) {
			LOG.error("Error loading fxml.", e);
		}
	}

	private void initialiseFX() {
		chatView = new ChatView();
		FXUtils.setAllAnchors(chatView, 0.0);
		chatPane.getChildren().add(chatView);
	}

	@Override
	public void connected(Connection connection) {
		LOG.info("Received connection from IP: " + connection.getRemoteAddressTCP());
		if (isHost) {
			LOG.info("Sending lobby info to newly connected client: " + connection.getRemoteAddressTCP());
			server.sendTo(connection, new LobbyInfoMessage(lobby));
		}
	}

	@Override
	public void disconnected(Connection connection) {
		LOG.info("Disconnection of remote IP: " + connection.getRemoteAddressTCP());
	}

	@Override
	public void received(Connection connection, Object object) {
		if (isHost) {
			LOG.info("Received message from remote IP: " + connection.getRemoteAddressTCP());
			if (object instanceof JoinLobbyRequest) {
				JoinLobbyRequest joinLobbyAction = (JoinLobbyRequest) object;
				Player player = joinLobbyAction.getPlayer();
				lobby.addPlayer(player);
				server.sendToAll(new LobbyInfoMessage(lobby));
				server.sendToAll(new SendMessage(null, String.format("%s has joined the server.", player.getName())));
			} else if (object instanceof LeaveLobbyAction) {
				LeaveLobbyAction leaveLobbyAction = (LeaveLobbyAction) object;
				Player player = leaveLobbyAction.getPlayer();
				lobby.removePlayer(player);
				server.sendToAll(new LobbyInfoMessage(lobby));
				server.sendToAll(new SendMessage(null, String.format("%s has left the server.", player.getName())));
			} else {
				LOG.error("Received unknown message type: " + object.getClass());
			}
		} else { // is client
			
		}
	}

	@Override
	public void idle(Connection connection) {
		LOG.info("Idle status for IP: " + connection.getRemoteAddressTCP());
	}
}
