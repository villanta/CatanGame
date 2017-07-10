package com.catangame.comms.register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.catangame.comms.messages.game.ActionType;
import com.catangame.comms.messages.game.DiceRollAction;
import com.catangame.comms.messages.game.GameActionMessage;
import com.catangame.comms.messages.lobby.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.LeaveLobbyAction;
import com.catangame.comms.messages.lobby.LobbyActionMessage;
import com.catangame.comms.messages.lobby.LobbyActionType;
import com.catangame.comms.messages.lobby.SendMessage;
import com.catangame.game.ResourceType;
import com.esotericsoftware.kryo.Kryo;

/**
 * Class to define registered classes for communications via Kryo api.
 * 
 * @author Jamie
 *
 */
public class KryoEnvironment {

	public static final int GAME_PORT = 12345;
	public static final int DISCOVERY_PORT = 24680; 
	public static final int KEEP_ALIVE_MESSAGE_PERIOD = 5000;
	public static final int TIMEOUT_PERIOD = 10000;
	
	private KryoEnvironment() {
		// cannot be instantiated
	}

	public static void register(Kryo kryo) {
		// Game
		kryo.register(GameActionMessage.class);
		kryo.register(DiceRollAction.class);
		kryo.register(ActionType.class);
		
		kryo.register(ResourceType.class);
		
		// Lobby
		kryo.register(LobbyActionMessage.class);
		kryo.register(LobbyActionType.class);
		kryo.register(JoinLobbyRequest.class);
		kryo.register(LeaveLobbyAction.class);
		kryo.register(SendMessage.class);
		
		// Generic
		kryo.register(ArrayList.class);
		kryo.register(Arrays.class);
		kryo.register(Map.class);
		kryo.register(HashMap.class);
	}
}
