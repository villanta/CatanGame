package com.catangame.comms.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.interfaces.PingListener;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.comms.messages.lobby.PingMessage;
import com.catangame.comms.messages.lobby.actions.CloseLobbyAction;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.KickPlayerAction;
import com.catangame.comms.messages.lobby.actions.StartGameMessage;
import com.catangame.model.game.Player;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class LobbyClient implements LobbyService {

	private static final Logger LOG = LogManager.getLogger(LobbyClient.class);

	private Client client;

	private Lobby lobby;

	private List<LobbyEventListener> lobbyEventListeners = new ArrayList<>();

	private List<PingListener> pingListeners = new ArrayList<>();

	public LobbyClient(Client client) {
		this.client = client;
	}

	@Override
	public void addListener(LobbyEventListener lobbyEventListener) {
		lobbyEventListeners.add(lobbyEventListener);
	}

	@Override
	public void removeListener(LobbyEventListener lobbyEventListener) {
		lobbyEventListeners.remove(lobbyEventListener);
	}

	@Override
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	@Override
	public Lobby getLobby() {
		return lobby;
	}

	@Override
	public void messageReceived(LobbyMessage lobbyMessage, Connection connection) {
		if (lobbyMessage instanceof PingMessage) {
			PingMessage pingMessage = (PingMessage) lobbyMessage;
			pingListeners.stream().forEach(client -> client.updatePing(pingMessage));
		} else if (lobbyMessage instanceof LobbyInfoResponse) {
			LOG.info("Lobby updated.");
			LobbyInfoResponse lobbyInfo = (LobbyInfoResponse) lobbyMessage;
			lobby = lobbyInfo.getLobby();
			new ArrayList<>(lobbyEventListeners).stream()
					.forEach(listener -> listener.updatedLobbyInfo(lobbyInfo, connection));
		} else if (lobbyMessage instanceof JoinLobbyResponse) {
			new ArrayList<>(lobbyEventListeners).stream()
					.forEach(listener -> listener.joinLobbyResponse((JoinLobbyResponse) lobbyMessage, connection));
		} else if (lobbyMessage instanceof CloseLobbyAction || lobbyMessage instanceof KickPlayerAction) {
			closedLobby();
		} else if (lobbyMessage instanceof StartGameMessage) {
			StartGameMessage startGameMessage = (StartGameMessage) lobbyMessage;
			new ArrayList<>(lobbyEventListeners).stream().forEach(listener -> listener.gameStarted(startGameMessage));
		}
	}

	private void closedLobby() {
		new ArrayList<>(lobbyEventListeners).stream().forEach(listener -> listener.lobbyClosed());
	}

	@Override
	public void updateLobby(Lobby lobby) {
		// do nothing, client
	}

	@Override
	public void closeLobby(Player player) {
		client.stop();
	}

	@Override
	public boolean isServer() {
		return false;
	}

	@Override
	public void kickPlayer(Player player) {
		// do nothing, is client
	}

	@Override
	public void addPingListener(PingListener pingListener) {
		pingListeners.add(pingListener);
	}

	@Override
	public void removePingListener(PingListener pingListener) {
		pingListeners.remove(pingListener);
	}

	@Override
	public void onClientDisconnect(Connection connection) {
		lobbyEventListeners.stream().forEach(listener -> listener.lobbyClosed());
		LOG.error("Lobby closed.");
	}

	@Override
	public void startGame(Lobby lobby) {
		// do nothing, is client
	}
}
