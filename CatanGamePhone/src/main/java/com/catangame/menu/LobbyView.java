package com.catangame.menu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.client.CatanClient;
import com.catangame.comms.kryo.ListenerInterface;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.LobbyInfoRequest;
import com.catangame.comms.messages.lobby.actions.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.LeaveLobbyAction;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.catangame.comms.server.CatanServer;
import com.catangame.game.GameState;
import com.catangame.game.Player;
import com.catangame.util.FXUtils;
import com.esotericsoftware.kryonet.Connection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LobbyView extends AnchorPane implements ListenerInterface, ChatInterface {

	private static final String FXML_LOCATION = "/com/catangame/view/LobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(LobbyView.class);

	@FXML
	private AnchorPane chatPane;
	private Lobby lobby;

	private ChatView chatView;

	private CatanServer server;
	private CatanClient client;
	private boolean isHost;

	private Player player;

	public LobbyView(Player player) {
		this.server = new CatanServer();
		this.player = player;
		server.start();
		server.addListener(this);
		isHost = true;
		lobby = new Lobby("STATIC TEST LOBBY", null, null, GameState.LOBBY);
		loadFXML();
		initialiseFX();
	}

	public LobbyView(CatanClient client, Lobby lobby, Player player) {
		this.client = client;
		this.lobby = lobby;
		this.player = player;
		isHost = false;
		client.addListener(this);
		loadFXML();
		initialiseFX();
	}

	@Override
	public void connected(Connection connection) {
		LOG.error("Received connection from IP: " + connection.getRemoteAddressTCP());
		if (isHost) {
			LOG.info("Sending lobby info to newly connected client: " + connection.getRemoteAddressTCP());
			server.sendTo(connection, new LobbyInfoResponse(lobby));
		}
	}

	@Override
	public void disconnected(Connection connection) {
		LOG.info("Disconnection of remote IP: " + connection.getRemoteAddressTCP());
	}

	@Override
	public void received(Connection connection, Object object) {
		LOG.info("Recieved Message");
		if (isHost) {
			processServerMessage(connection, object);
		} else { // is client
			processClientMessage(connection, object);
			LOG.error("Reeep");
		}
	}

	@Override
	public void idle(Connection connection) {
		LOG.info("Idle status for IP: " + connection.getRemoteAddressTCP());
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
		chatView = new ChatView(this);
		FXUtils.setAllAnchors(chatView, 0.0);
		chatPane.getChildren().add(chatView);
	}

	private void processClientMessage(Connection connection, Object object) {
		LOG.info("Received message from server, IP: " + connection.getRemoteAddressTCP());
		if (object instanceof LobbyInfoResponse) {
			LobbyInfoResponse lobbyInfo = (LobbyInfoResponse) object;
			updateLobbyInfo(lobbyInfo);
		}
	}

	private void processServerMessage(Connection connection, Object object) {
		LOG.info("Received message from remote IP: " + connection.getRemoteAddressTCP());
		if (object instanceof LobbyInfoRequest) {
			LOG.info("Recieved Lobby Info Request. Sending info to IP: " + connection.getRemoteAddressTCP());
			server.sendTo(connection, new LobbyInfoResponse(this.lobby));
		} else if (object instanceof JoinLobbyRequest) {
			JoinLobbyRequest joinLobbyAction = (JoinLobbyRequest) object;
			Player player = joinLobbyAction.getPlayer();
			lobby.addPlayer(player);
			server.sendTo(connection, new JoinLobbyResponse(player, true));
			server.sendToAll(new LobbyInfoResponse(lobby));
			server.sendToAll(
					new SendMessageLobbyAction(null, String.format("%s has joined the server.", player.getName())));
		} else if (object instanceof LeaveLobbyAction) {
			LeaveLobbyAction leaveLobbyAction = (LeaveLobbyAction) object;
			Player player = leaveLobbyAction.getPlayer();
			lobby.removePlayer(player);
			server.sendToAll(new LobbyInfoResponse(lobby));
			server.sendToAll(
					new SendMessageLobbyAction(null, String.format("%s has left the server.", player.getName())));
		} else if (object instanceof SendMessageLobbyAction) {
			System.err.println("Received message");
			SendMessageLobbyAction message = (SendMessageLobbyAction) object;
			server.sendToAll(message);
			String actualMessage = String.format("%s: %s", message.getPlayer().getName() , message.getMessage());
			chatView.addMessageToLog(actualMessage);
		} else {
			LOG.error("Received unknown message type: " + object.getClass());
		}
	}

	private void updateLobbyInfo(LobbyInfoResponse lobbyInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String message) {
		if (isHost) {
			System.err.println("Sending message to all");
			server.sendToAll(new SendMessageLobbyAction(player, message));
			chatView.addMessageToLog(player.getName() + ": " + message);
		} else {
			System.err.println("Sending message");
			client.sendObject(new SendMessageLobbyAction(player, message));
		}
	}
}
