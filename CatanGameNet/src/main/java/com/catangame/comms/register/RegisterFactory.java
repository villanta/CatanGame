package com.catangame.comms.register;

import java.util.ArrayList;

import com.catangame.comms.messages.ActionType;
import com.catangame.comms.messages.DiceRollAction;
import com.catangame.comms.messages.GameActionMessage;
import com.catangame.game.ResourceType;
import com.esotericsoftware.kryo.Kryo;

public class RegisterFactory {

	public static void register(Kryo kryo) {
		kryo.register(GameActionMessage.class);
		kryo.register(DiceRollAction.class);
		kryo.register(ActionType.class);
		kryo.register(ResourceType.class);
		kryo.register(ArrayList.class);		
		kryo.register(java.util.Arrays.class);
	}
}
