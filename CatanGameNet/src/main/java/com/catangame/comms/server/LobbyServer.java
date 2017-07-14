package com.catangame.comms.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoRequest;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.comms.messages.lobby.actions.CloseLobbyAction;
import com.catangame.comms.messages.lobby.actions.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.LeaveLobbyAction;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.catangame.game.Player;
import com.esotericsoftware.kryonet.Connection;

public class LobbyServer implements LobbyService {

	private static final Logger LOG = LogManager.getLogger(LobbyServer.class);

	private CatanServer server;

	private Lobby lobby;

	private List<LobbyEventListener> lobbyEventListeners = new ArrayList<>();

	public LobbyServer(CatanServer server) {
		this.server = server;
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
	public void updateLobby(Lobby lobby) {
		this.lobby = lobby;
		LobbyInfoResponse lobbyInfoResponse = new LobbyInfoResponse(lobby);
		server.sendToAllTCP(lobbyInfoResponse);
	}

	@Override
	public void messageReceived(LobbyMessage lobbyActionMessage, Connection connection) {
		if (lobbyActionMessage instanceof LobbyInfoRequest) {
			server.sendTo(connection, new LobbyInfoResponse(lobby));
		} else if (lobbyActionMessage instanceof JoinLobbyRequest) {
			JoinLobbyRequest joinLobbyAction = (JoinLobbyRequest) lobbyActionMessage;
			Player player = joinLobbyAction.getPlayer();
			lobby.addPlayer(player);
			System.err.println("Sending lobby: " + lobby);
			server.sendTo(connection, new JoinLobbyResponse(player, true, lobby));
			LobbyInfoResponse lobbyInfoResponse = new LobbyInfoResponse(lobby);
			server.sendToAll(lobbyInfoResponse);
			lobbyEventListeners.stream().forEach(listener -> listener.updatedLobbyInfo(lobbyInfoResponse, connection));
			server.getChatService().sendMessage(player, String.format("%s has joined the server.", player.getName()));
		} else if (lobbyActionMessage instanceof LeaveLobbyAction) {
			LeaveLobbyAction leaveLobbyAction = (LeaveLobbyAction) lobbyActionMessage;
			Player player = leaveLobbyAction.getPlayer();
			lobby.removePlayer(player);
			server.sendToAll(new LobbyInfoResponse(lobby));
			server.sendToAll(
					new SendMessageLobbyAction(player, String.format("%s has left the server.", player.getName())));
		} else {
			LOG.error("Received unknown message type: " + lobbyActionMessage.getClass());
		}
	}

	@Override
	public void closeLobby(Player player) {
		server.sendToAll(new CloseLobbyAction());
		server.stop();
	}

}
