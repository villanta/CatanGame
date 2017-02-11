package com.catangame.comms.test;

import java.util.ArrayList;

import com.catangame.comms.client.GameClient;
import com.catangame.comms.messages.ActionType;
import com.catangame.comms.messages.DiceRollAction;

public class ClientTest {

	public static void main (String[] args) {
		GameClient client = new GameClient();
		
		client.sendObject(new DiceRollAction(0, ActionType.DICE_ROLL, 5, new ArrayList<>()));
	}
}
