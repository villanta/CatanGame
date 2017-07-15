package com.catangame.comms.kryo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.catangame.Lobby;
import com.catangame.comms.messages.game.ActionType;
import com.catangame.comms.messages.game.DiceRollResponse;
import com.catangame.comms.messages.game.GameActionMessage;
import com.catangame.comms.messages.lobby.LobbyInfoRequest;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.comms.messages.lobby.PingMessage;
import com.catangame.comms.messages.lobby.actions.CloseLobbyAction;
import com.catangame.comms.messages.lobby.actions.JoinLobbyRequest;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.KickPlayerAction;
import com.catangame.comms.messages.lobby.actions.LeaveLobbyAction;
import com.catangame.comms.messages.lobby.actions.LobbyActionMessage;
import com.catangame.comms.messages.lobby.actions.LobbyActionType;
import com.catangame.comms.messages.lobby.actions.SendMessageLobbyAction;
import com.catangame.comms.messages.lobby.actions.StartGameMessage;
import com.catangame.core.CatanColour;
import com.catangame.core.CatanColour.CatanColourEnum;
import com.catangame.model.game.Game;
import com.catangame.model.game.GameRules;
import com.catangame.model.game.GameState;
import com.catangame.model.game.Player;
import com.catangame.model.locations.HexLocation;
import com.catangame.model.resources.PlayerResources;
import com.catangame.model.resources.ResourceType;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.City;
import com.catangame.model.structures.Settlement;
import com.catangame.model.tiles.GameHex;
import com.catangame.model.tiles.HexType;
import com.catangame.model.tiles.PortHex;
import com.esotericsoftware.kryo.Kryo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 * Class to define registered classes for communications via Kryo api.
 * 
 * @author Jamie
 *
 */
public class KryoEnvironment {

	public static final int GAME_PORT = 12345;
	public static final int DISCOVERY_PORT = 19999;
	public static final int KEEP_ALIVE_MESSAGE_PERIOD = 5000;
	public static final int TIMEOUT_PERIOD = 10000;

	private KryoEnvironment() {
		// cannot be instantiated
	}

	public static void register(Kryo kryo) {
		// Game
		kryo.register(GameActionMessage.class);
		kryo.register(DiceRollResponse.class);
		kryo.register(ActionType.class);

		kryo.register(ResourceType.class);

		// Lobby
		kryo.register(KickPlayerAction.class);
		kryo.register(CloseLobbyAction.class);
		kryo.register(LobbyMessage.class);
		kryo.register(LobbyActionMessage.class);
		kryo.register(LobbyActionType.class);
		kryo.register(JoinLobbyRequest.class);
		kryo.register(JoinLobbyResponse.class);
		kryo.register(LobbyInfoRequest.class);
		kryo.register(LobbyInfoResponse.class);
		kryo.register(Lobby.class);
		kryo.register(GameState.class);
		kryo.register(Game.class);
		kryo.register(GameRules.class);
		kryo.register(Player.class);
		kryo.register(PlayerResources.class);
		kryo.register(LeaveLobbyAction.class);
		kryo.register(SendMessageLobbyAction.class);
		kryo.register(PingMessage.class);
		kryo.register(StartGameMessage.class);
		kryo.register(GameHex.class);
		kryo.register(HexLocation.class);
		kryo.register(Building.class);
		kryo.register(Settlement.class);
		kryo.register(City.class);
		kryo.register(HexType.class);
		kryo.register(PortHex.class);

		kryo.register(CatanColour.class);
		kryo.register(CatanColourEnum.class);

		// Generic
		kryo.register(ArrayList.class);
		kryo.register(Arrays.class);
		kryo.register(Map.class);
		kryo.register(HashMap.class);
		kryo.register(StringProperty.class);
		kryo.register(SimpleStringProperty.class);
		kryo.register(SimpleIntegerProperty.class);
		kryo.register(Color.class);
		kryo.register(Integer.class);
	}
}
