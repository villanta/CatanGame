package com.catangame.comms.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.interfaces.PingListener;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoRequest;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.comms.messages.lobby.PingMessage;
import com.catangame.comms.messages.lobby.actions.CloseLobbyAction;
import com.catangame.comms.messages.lobby.actions.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.KickPlayerAction;
import com.catangame.comms.messages.lobby.actions.LeaveLobbyAction;
import com.catangame.game.Player;
import com.esotericsoftware.kryonet.Connection;

public class LobbyServer implements LobbyService {

	private static final Logger LOG = LogManager.getLogger(LobbyServer.class);

	private CatanServer server;

	private Lobby lobby;

	private List<LobbyEventListener> lobbyEventListeners = new ArrayList<>();

	private List<PingListener> pingListeners = new ArrayList<>();

	private Map<Integer, Connection> playerIdConnectionMap = new HashMap<>();

	private int nextId = 1;

	public LobbyServer(CatanServer server) {
		this.server = server;

		new Thread(() -> {
			while(server.isBound()) {
				try {
					PingMessage pingMessage = getPingMessage();
					pingListeners.stream().forEach(listener -> listener.updatePing(pingMessage));
					server.sendToAll(pingMessage);
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					LOG.error("Error in Ping Update Thread.", e);
				}
			}
		}).start();
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
		// update Lobby and send to all players
		LobbyInfoResponse lobbyInfoResponse = new LobbyInfoResponse(lobby);
		server.sendToAll(lobbyInfoResponse);
	}

	@Override
	public void messageReceived(LobbyMessage lobbyActionMessage, Connection connection) {
		if (lobbyActionMessage instanceof LobbyInfoRequest) {
			server.sendTo(connection, new LobbyInfoResponse(lobby));
		} else if (lobbyActionMessage instanceof JoinLobbyRequest) {
			// parse incoming JoinLobbyRequest
			JoinLobbyRequest joinLobbyAction = (JoinLobbyRequest) lobbyActionMessage;
			Player player = joinLobbyAction.getPlayer();

			player.setId(nextId++);
			playerIdConnectionMap.put(player.getId(), connection);

			// add player to lobby on server
			lobby.addPlayer(player);

			// reply success to player, sending updated lobby, TODO logic for
			// rejecting players
			server.sendTo(connection, new JoinLobbyResponse(player, true, lobby));

			// update lobby for server and all connected clients
			updateLobbyForAll(connection);

			// put joined message in chat
			server.getChatService().sendMessage(player, String.format("%s has joined the server.", player.getName()));
		} else if (lobbyActionMessage instanceof LeaveLobbyAction) {
			LeaveLobbyAction leaveLobbyAction = (LeaveLobbyAction) lobbyActionMessage;
			Player player = leaveLobbyAction.getPlayer();
			lobby.removePlayer(player);
			playerIdConnectionMap.remove(player.getId());
			updateLobbyForAll(connection);
			server.getChatService().sendMessage(player, String.format("%s has left the server.", player.getName()));
		} else {
			LOG.error("Received unknown message type: " + lobbyActionMessage.getClass());
		}
	}

	@Override
	public void closeLobby(Player player) {
		server.sendToAll(new CloseLobbyAction());
		server.stop();
	}

	@Override
	public void kickPlayer(Player player) {
		lobby.removePlayer(player);
		server.sendTo(playerIdConnectionMap.get(player.getId()), new KickPlayerAction(player));
		server.getChatService().sendMessage(player, String.format("%s has been kicked.", player.getName()));
		updateLobbyForAll(null);
	}

	/**
	 * Updates the lobby for all connected clients. Then update the lobby on the
	 * server.
	 * 
	 * @param connection
	 *            (not really used) connection that instigated this change
	 */
	private void updateLobbyForAll(Connection connection) {
		// update Lobby and send to all players
		LobbyInfoResponse lobbyInfoResponse = new LobbyInfoResponse(lobby);
		server.sendToAll(lobbyInfoResponse);
		// update Lobby on Server
		lobbyEventListeners.stream().forEach(listener -> listener.updatedLobbyInfo(lobbyInfoResponse, connection));
	}

	@Override
	public boolean isServer() {
		return true;
	}

	public PingMessage getPingMessage() {
		Map<Integer, Integer> pingMap = new HashMap<>();

		pingMap.put(0, 0); // add ping 0 for the host
		playerIdConnectionMap.keySet().stream()
				.forEach(playerId -> pingMap.put(playerId, playerIdConnectionMap.get(playerId).getReturnTripTime()));

		return new PingMessage(pingMap);
	}

	@Override
	public void addPingListener(PingListener pingListener) {
		pingListeners.add(pingListener);
	}

	@Override
	public void removePingListener(PingListener pingListener) {
		pingListeners.remove(pingListener);
	}

}
