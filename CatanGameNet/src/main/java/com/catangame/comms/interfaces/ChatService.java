package com.catangame.comms.interfaces;

import com.catangame.comms.listeners.ChatEventListener;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.catangame.model.game.Player;

public interface ChatService {

	void messageReceived(SendMessageLobbyAction sendMessageLobbyAction);

	void sendMessage(Player player, String message);

	boolean removeListener(ChatEventListener listener);

	boolean addListener(ChatEventListener listener);

}
