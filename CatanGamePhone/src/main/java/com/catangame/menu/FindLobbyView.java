package com.catangame.menu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.client.CatanClient;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoRequest;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.actions.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.game.Player;
import com.catangame.util.FXUtils;
import com.esotericsoftware.kryonet.Connection;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class FindLobbyView extends AnchorPane implements LobbyEventListener {

	private static final String FXML_LOCATION = "/com/catangame/view/FindLobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(FindLobbyView.class);

	@FXML
	private ListView<LobbyInfoView> lobbyListView;

	private CatanClient client;

	private List<InetAddress> servers;

	private boolean awaitingMessage = false;
	private boolean awaitingConnection = false;

	private Player player;

	private boolean isRefreshing = false;

	public FindLobbyView(Player player) {
		this.player = player;
		loadFXML();
		initialiseFX();
	}

	public void connectToLobby(InetSocketAddress inetSocketAddress) {
		new Thread(() -> {
			try {
				LOG.info("Trying to connect to: " + inetSocketAddress.getAddress());
				client.connect(inetSocketAddress.getAddress());
				client.sendObject(new JoinLobbyRequest(player));
			} catch (IOException e) {
				LOG.error("Error while connecting to Lobby on " + inetSocketAddress, e);
			}
		}).start();
	}

	@Override
	public void updatedLobbyInfo(LobbyInfoResponse lobbyInfoResponse, Connection connection) {
		if (awaitingMessage) {
			LobbyInfoView lobbyInfoView = new LobbyInfoView(lobbyInfoResponse, connection.getRemoteAddressTCP(), this);
			Platform.runLater(() -> lobbyListView.getItems().add(lobbyInfoView));
			awaitingMessage = false;
			LOG.error("LobbyInfo Recieved: %s", lobbyInfoResponse.getLobby().getLobbyName());
		}
	}

	@Override
	public void joinLobbyResponse(JoinLobbyResponse joinLobbyResponse, Connection connection) {
		if (joinLobbyResponse.isAccepted()) {
			switchToLobby(joinLobbyResponse);
		} else {
			LOG.info("Lobby join request rejected: " + joinLobbyResponse.getReason());
			client.disconnect();
		}
	}

	@FXML
	protected void cancelAction(ActionEvent event) {
		MainMenuPane pane = new MainMenuPane();
		getScene().setRoot(pane);
		event.consume();
	}

	@FXML
	protected void refreshAction(ActionEvent event) {
		refreshLobbys();
	}

	private void initialiseFX() {
		client = new CatanClient();
		client.getLobbyService().addListener(this);
		refreshLobbys();
	}

	private void refreshLobbys() {
		lobbyListView.getItems().clear(); // clear list

		new Thread(() -> {
			isRefreshing = true;
			client.catanClientConnectedProperty().addListener((obsV, oldV, newV) -> catanClientConnectedUpdated(newV));
			servers = client.findAllServers();
			for (InetAddress server : servers) {
				awaitingMessage = true;
				awaitingConnection = true;
				try {
					connectToServer(server);
					sendLobbyInfoRequestAndWait();
					client.disconnect();
				} catch (IOException | InterruptedException e) {
					LOG.error("Error while refreshing Lobbys.", e);
				}
			}
			isRefreshing = false;
		}).start();
	}

	private void catanClientConnectedUpdated(Boolean isConnected) {
		if (isConnected && awaitingConnection) {
			awaitingConnection = false;
		}
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

	private void switchToLobby(JoinLobbyResponse joinLobbyResponse) {
		client.getLobbyService().removeListener(this);
		Lobby lobby = joinLobbyResponse.getLobby();
		LOG.info("Joined Lobby: " + lobby);
		LobbyView view = new LobbyView(this.client, lobby, player);
		getScene().setRoot(view);
	}

	private void connectToServer(InetAddress server) throws IOException, InterruptedException {
		client.connect(server);
		while (awaitingConnection) {
			Thread.sleep(50);
		}
	}

	private void sendLobbyInfoRequestAndWait() throws InterruptedException {
		client.sendObject(new LobbyInfoRequest());

		while (awaitingMessage) {
			Thread.sleep(50);
		}
	}

	@Override
	public void lobbyClosed() {
		if (!isRefreshing) {
			refreshLobbys();
		}
	}
}
