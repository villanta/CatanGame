package com.catangame.comms.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.comms.interfaces.CatanEndPoint;
import com.catangame.comms.interfaces.ChatService;
import com.catangame.comms.interfaces.GameService;
import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.kryo.KryoEnvironment;
import com.catangame.comms.kryo.ListenerInterface;
import com.catangame.comms.kryo.ListenerInterfaceWrapper;
import com.catangame.comms.messages.game.GameActionMessage;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.minlog.Log;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CatanClient extends Client implements ListenerInterface, CatanEndPoint {

	private static final Logger LOG = LogManager.getLogger(CatanClient.class);

	private BooleanProperty connected = new SimpleBooleanProperty(false);

	private ChatService chatService;
	private LobbyService lobbyService;
	private GameService gameService;

	public CatanClient() {
		super();
		super.addListener(new ListenerInterfaceWrapper(this));
		Log.set(Log.LEVEL_ERROR);
		KryoEnvironment.register(getKryo());
		chatService = new ChatClient(this);
		lobbyService = new LobbyClient(this);
		gameService = new GameClient(this);
	}

	public BooleanProperty catanClientConnectedProperty() {
		return this.connected;
	}

	public boolean isCatanClientConnected() {
		return this.catanClientConnectedProperty().get();
	}

	public List<InetAddress> findAllServers() {
		List<InetAddress> addresses = discoverHosts(KryoEnvironment.DISCOVERY_PORT, 100);
		return addresses.stream()
				.filter(address -> !address.getHostAddress().equals("127.0.0.1")).collect(Collectors.toList());
	}

	public void sendObject(Object o) {
		sendTCP(o);
	}

	public void connect(InetAddress server) throws IOException {
		start();
		connect(3000, server, KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
		setKeepAliveTCP(2000);
		setKeepAliveUDP(2000);
		setTimeout(5000);
	}

	@Override
	public void disconnect() {
		stop();
	}

	@Override
	public ChatService getChatService() {
		return chatService;
	}

	@Override
	public LobbyService getLobbyService() {
		return lobbyService;
	}

	@Override
	public GameService getGameService() {
		return gameService;
	}

	@Override
	public void connected(Connection connection) {
		LOG.info("Connection established: " + connection);
		connected.set(true);
	}

	@Override
	public void disconnected(Connection connection) {
		lobbyService.onClientDisconnect(connection);
		LOG.info("Connection disconnected: " + connection);
		connected.set(false);
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof KeepAlive) {
			return;
		}
		LOG.info("Received message of type: " + object.getClass());
		if (object instanceof SendMessageLobbyAction) {
			SendMessageLobbyAction sendMessageLobbyAction = (SendMessageLobbyAction) object;
			chatService.messageReceived(sendMessageLobbyAction);
		} else if (object instanceof LobbyMessage) {
			LobbyMessage lobbyMessage = (LobbyMessage) object;
			lobbyService.messageReceived(lobbyMessage, connection);
		} else if (object instanceof GameActionMessage) {
			GameActionMessage gameActionMessage = (GameActionMessage) object;
			gameService.messageReceived(gameActionMessage);
		}
	}

	@Override
	public void idle(Connection connection) {
		LOG.trace("Connection idle: " + connection);
	}

}