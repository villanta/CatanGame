package com.catangame.comms.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.comms.messages.lobby.actions.CloseLobbyAction;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.game.Player;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class LobbyClient implements LobbyService {

	private static final Logger LOG = LogManager.getLogger(LobbyClient.class);

	private Client client;

	private Lobby lobby;

	private List<LobbyEventListener> lobbyEventListeners = new ArrayList<>();

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
		if (lobbyMessage instanceof LobbyInfoResponse) {
			LOG.info("Lobby updated.");
			LobbyInfoResponse lobbyInfo = (LobbyInfoResponse) lobbyMessage;
			lobby = lobbyInfo.getLobby();
			lobbyEventListeners.stream().forEach(listener -> listener.updatedLobbyInfo(lobbyInfo, connection));
		} else if (lobbyMessage instanceof JoinLobbyResponse) {
			new ArrayList<>(lobbyEventListeners).stream()
					.forEach(listener -> listener.joinLobbyResponse((JoinLobbyResponse) lobbyMessage, connection));
		} else if (lobbyMessage instanceof CloseLobbyAction) {
			CloseLobbyAction closeLobbyAction = (CloseLobbyAction) lobbyMessage;
			lobbyEventListeners.stream().forEach(listener -> listener.lobbyClosed());
		}
	}

	@Override
	public void updateLobby(Lobby lobby) {
		// do nothing, client
	}

	@Override
	public void closeLobby(Player player) {
		client.stop();
	}

}
