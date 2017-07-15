package com.catangame.comms.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.comms.interfaces.ChatService;
import com.catangame.comms.listeners.ChatEventListener;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.catangame.model.game.Player;
import com.esotericsoftware.kryonet.Client;

public class ChatClient implements ChatService {

	private static final Logger LOG = LogManager.getLogger(ChatClient.class);

	private Client client;

	private List<ChatEventListener> chatEventListeners = new ArrayList<>();

	public ChatClient(Client client) {
		this.client = client;
	}
	
	@Override
	public void sendMessage(Player player, String message) {
		SendMessageLobbyAction sendMessageLobbyAction = new SendMessageLobbyAction(player, message);
		client.sendTCP(sendMessageLobbyAction);
	}

	@Override
	public void messageReceived(SendMessageLobbyAction message) {
		LOG.info(String.format("Message received from player: %s, content: %s.", message.getPlayer().getName(), message.getMessage()));
		
		String actualMessage = String.format("%s: %s", message.getPlayer().getName(), message.getMessage());
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
