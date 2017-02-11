package com.catangame.comms.register;

import com.catangame.comms.messages.DiceRollAction;
import com.catangame.comms.messages.GameActionMessage;
import com.esotericsoftware.kryo.Kryo;

public class RegisterFactory {

	public static void register(Kryo kryo) {
		kryo.register(GameActionMessage.class);
		kryo.register(DiceRollAction.class);
	}
}
