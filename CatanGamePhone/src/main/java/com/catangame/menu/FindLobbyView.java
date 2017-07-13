package com.catangame.menu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.client.CatanClient;
import com.catangame.comms.kryo.ListenerInterface;
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

public class FindLobbyView extends AnchorPane implements ListenerInterface {

	private static final String FXML_LOCATION = "/com/catangame/view/FindLobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(FindLobbyView.class);

	@FXML
	private ListView<LobbyInfoView> lobbyListView;

	private CatanClient client;

	private List<InetAddress> servers;

	private boolean awaitingMessage = false;
	private boolean awaitingConnection = false;

	public FindLobbyView() {
		loadFXML();
		initialiseFX();
	}

	private void initialiseFX() {
		client = new CatanClient();
		client.addListener(this);
		refreshLobbys();
	}

	private void refreshLobbys() {
		lobbyListView.getItems().clear(); // clear list

		new Thread(() -> {
			servers = client.findAllServers();
			for (InetAddress server : servers) {
				try {
					awaitingMessage = true;
					awaitingConnection = true;
					client.connect(server);
					while (awaitingConnection) {
						Thread.sleep(50);
					}

					client.sendObject(new LobbyInfoRequest());

					while (awaitingMessage) {
						Thread.sleep(50);
					}
					client.disconnect();
				} catch (IOException | InterruptedException e) {
					LOG.error("Error while refreshing Lobbys.", e);
				}

			}
		}).start();
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

	public void connectToLobby(InetSocketAddress inetSocketAddress) {
		Player testPlayer1 = new Player(); // TODO get player from game
		new Thread(() -> {
			try {
				LOG.info("Trying to connect to: " + inetSocketAddress.getAddress());
				client.connect(inetSocketAddress.getAddress());
				client.sendObject(new JoinLobbyRequest(testPlayer1));
			} catch (IOException e) {
				LOG.error("Error while connecting to Lobby on " + inetSocketAddress);
			}

		}).start();

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

	@Override
	public void connected(Connection connection) {
		LOG.info("Connected");
		awaitingConnection = false;
	}

	@Override
	public void disconnected(Connection connection) {
		LOG.info("Disconnected");
	}

	@Override
	public void received(Connection connection, Object object) {
		System.err.println("received response of :  " + object);
		if (object instanceof LobbyInfoResponse) {
			if (awaitingMessage) {
				LobbyInfoResponse lobbyInfoMessage = (LobbyInfoResponse) object;
				LobbyInfoView lobbyInfoView = new LobbyInfoView(lobbyInfoMessage, connection.getRemoteAddressTCP(),
						this);
				Platform.runLater(() -> lobbyListView.getItems().add(lobbyInfoView));
				awaitingMessage = false;
				LOG.error("LobbyInfo Recieved: %s", lobbyInfoMessage.getLobby().getLobbyName());
			}
		} else if (object instanceof JoinLobbyResponse) {
			System.err.println("JoinLobbyResponse");
			JoinLobbyResponse joinLobbyResponse = (JoinLobbyResponse) object;

			if (joinLobbyResponse.isAccepted()) {
				Lobby lobby = joinLobbyResponse.getLobby();
				LOG.info("Joined Lobby: " + lobby);
				LobbyView view = new LobbyView(this.client, lobby, null);
				getScene().setRoot(view);
			} else {
				LOG.info("Lobby join request rejected: " + joinLobbyResponse.getReason());
				client.disconnect();
			}

		} else {
			LOG.error("Invalid Message recieved from server.");
		}
	}

	@Override
	public void idle(Connection connection) {
		LOG.debug("Idle");
	}

}
