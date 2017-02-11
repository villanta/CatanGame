package com.catangame.comms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.catangame.comms.client.GameClient;
import com.catangame.comms.messages.ActionType;
import com.catangame.comms.messages.DiceRollAction;
import com.catangame.game.ResourceType;

import javafx.util.Pair;

public class ClientTest {

	public static void main(String[] args) {
		GameClient client = new GameClient();

		List<ResourceType> types = new ArrayList<>();
		List<ResourceType> typesToAdd = Arrays.asList(ResourceType.BRICK, ResourceType.LUMBER);
		types.addAll(typesToAdd);
		
		client.sendObject(new DiceRollAction(0, ActionType.DICE_ROLL, 5, Arrays.asList(
				new Pair<Integer, List<ResourceType>>(0, types))));
	}
}
