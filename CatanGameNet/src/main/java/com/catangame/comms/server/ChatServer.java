package com.catangame.comms.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.comms.interfaces.ChatService;
import com.catangame.comms.listeners.ChatEventListener;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.catangame.game.Player;
import com.esotericsoftware.kryonet.Server;

public class ChatServer implements ChatService {

	private static final Logger LOG = LogManager.getLogger(ChatServer.class);

	private Server server;

	private List<ChatEventListener> chatEventListeners = new ArrayList<>();

	public ChatServer(Server server) {
		this.server = server;
	}

	@Override
	public void sendMessage(Player player, String message) {
		SendMessageLobbyAction sendMessageLobbyAction = new SendMessageLobbyAction(player, message);

		processMessage(sendMessageLobbyAction);
	}

	@Override
	public void messageReceived(SendMessageLobbyAction sendMessageLobbyAction) {
		LOG.info(String.format("Message received from player: %s, content: %s.",
				sendMessageLobbyAction.getPlayer().getName(), sendMessageLobbyAction.getMessage()));

		processMessage(sendMessageLobbyAction);
	}

	private void processMessage(SendMessageLobbyAction sendMessageLobbyAction) {
		server.sendToAllTCP(sendMessageLobbyAction);
		String actualMessage = String.format("%s: %s", sendMessageLobbyAction.getPlayer().getName(),
				sendMessageLobbyAction.getMessage());
		chatEventListeners.stream().forEach(listener -> listener.newMessage(actualMessage));
	}

	@Override
	public boolean addListener(ChatEventListener listener) {
		return chatEventListeners.add(listener);
	}

	@Override
	public boolean removeListener(ChatEventListener listener) {
		return chatEventListeners.remove(listener);
	}
}
