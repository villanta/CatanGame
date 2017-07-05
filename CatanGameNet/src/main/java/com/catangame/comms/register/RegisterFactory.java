package com.catangame.comms.register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.catangame.comms.messages.ActionType;
import com.catangame.comms.messages.DiceRollAction;
import com.catangame.comms.messages.GameActionMessage;
import com.catangame.game.ResourceType;
import com.esotericsoftware.kryo.Kryo;

/**
 * Class to define registered classes for communications via Kryo api.
 * 
 * @author Jamie
 *
 */
public class RegisterFactory {

	private RegisterFactory() {
		// cannot be instantiated
	}

	public static void register(Kryo kryo) {
		kryo.register(GameActionMessage.class);
		kryo.register(DiceRollAction.class);
		kryo.register(ActionType.class);
		kryo.register(ResourceType.class);
		kryo.register(ArrayList.class);
		kryo.register(Arrays.class);
		kryo.register(Map.class);
		kryo.register(HashMap.class);
	}
}
