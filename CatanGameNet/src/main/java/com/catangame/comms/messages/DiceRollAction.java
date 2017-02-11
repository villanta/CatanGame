package com.catangame.comms.messages;

import java.util.List;
import java.util.stream.Collectors;

import com.catangame.game.ResourceType;

import javafx.util.Pair;

public class DiceRollAction extends GameActionMessage {

	private List<Pair<Integer, List<ResourceType>>> playerReceivedResources;
	private int rollValue;

	public DiceRollAction(int playerId, ActionType actionType, int rollValue,
			List<Pair<Integer, List<ResourceType>>> playerReceivedResources) {
		super(playerId, actionType);
		this.rollValue = rollValue;
		this.playerReceivedResources = playerReceivedResources;
	}

	public int getRoll() {
		return rollValue;
	}

	public List<Integer> getPlayersWhoReceivedResources() {
		return playerReceivedResources.stream().map(pair -> pair.getKey()).collect(Collectors.toList());
	}

	public List<ResourceType> getResourcesForPlayer(int playerId) {
		return playerReceivedResources.stream().filter(pair -> pair.getKey().equals(playerId))
				.flatMap(pair -> pair.getValue().stream()).collect(Collectors.toList());
	}

}
