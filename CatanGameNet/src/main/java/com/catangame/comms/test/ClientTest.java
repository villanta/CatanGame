package com.catangame.comms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.client.GameClient;
import com.catangame.comms.messages.ActionType;
import com.catangame.comms.messages.DiceRollAction;
import com.catangame.game.ResourceType;

public class ClientTest {

	public static void main(String[] args) {
		GameClient client = new GameClient();

		List<ResourceType> types = new ArrayList<>();
		List<ResourceType> typesToAdd = Arrays.asList(ResourceType.BRICK, ResourceType.LUMBER);
		types.addAll(typesToAdd);

		Map<Integer, List<ResourceType>> p = new HashMap<>();
		p.put(0, types);

		client.sendObject(new DiceRollAction(0, ActionType.DICE_ROLL, 5, p));
	}
}
