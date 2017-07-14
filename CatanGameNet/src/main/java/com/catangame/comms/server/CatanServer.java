package com.catangame.comms.server;

import java.io.IOException;

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
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.minlog.Log;

public class CatanServer extends Server implements CatanEndPoint, ListenerInterface {

	private static final Logger LOG = LogManager.getLogger(CatanServer.class);

	private ChatService chatServer;
	private LobbyService lobbyService;
	private GameService gameService;

	public CatanServer() {
		super();
		super.addListener(new ListenerInterfaceWrapper(this));
		Log.set(Log.LEVEL_ERROR);
		KryoEnvironment.register(getKryo());
		chatServer = new ChatServer(this);
		lobbyService = new LobbyServer(this);
		gameService = new GameServer(this);
		try {
			bind(KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
			super.start();
		} catch (IOException e) {
			LOG.error("Failed to initialise server.", e);
		}
	}

	public void sendToAll(Object o) {
		sendToAllTCP(o);
	}

	public void sendTo(Connection connection, Object message) {
		sendToTCP(connection.getID(), message);
	}

	@Override
	public void disconnect() {
		stop();
	}
	
	@Override
	public ChatService getChatService() {
		return chatServer;
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
	}

	@Override
	public void disconnected(Connection connection) {
		LOG.info("Connection disconnected: " + connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof KeepAlive) {
			return;
		}
		LOG.info("Received message of type: " + object.getClass());
		if (object instanceof SendMessageLobbyAction) {
			SendMessageLobbyAction sendMessageLobbyAction = (SendMessageLobbyAction) object;
			chatServer.messageReceived(sendMessageLobbyAction);
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
