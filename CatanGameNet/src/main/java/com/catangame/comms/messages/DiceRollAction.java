package com.catangame.comms.messages;

import java.util.List;
import java.util.stream.Collectors;

import com.catangame.game.ResourceType;

import javafx.util.Pair;

public class DiceRollAction extends GameActionMessage {

	private List<Pair<Integer, List<ResourceType>>> playerReceivedResources;
	private int rollValue;

	public DiceRollAction() {
		// no arg cons. for kryo
	}

	public DiceRollAction(int playerId, ActionType actionType, int rollValue,
			List<Pair<Integer, List<ResourceType>>> playerReceivedResources) {
		super(playerId, actionType);
		this.rollValue = rollValue;
		this.playerReceivedResources = playerReceivedResources;
	}

	public int getRoll() {
		return rollValue;
	}

	public void setRollValue(int rollValue) {
		this.rollValue = rollValue;
	}

	public void setPlayerReceivedResources(List<Pair<Integer, List<ResourceType>>> resources) {
		this.playerReceivedResources = resources;
	}

	public List<Integer> getPlayersWhoReceivedResources() {
		return playerReceivedResources.stream().map(pair -> pair.getKey()).collect(Collectors.toList());
	}

	public List<ResourceType> getResourcesForPlayer(int playerId) {
		return playerReceivedResources.stream().filter(pair -> pair.getKey().equals(playerId))
				.flatMap(pair -> pair.getValue().stream()).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("Dice roll action for roll: %d", rollValue));
		playerReceivedResources.stream().forEach(playerResourcePair -> sb.append(toString(playerResourcePair)));

		return sb.toString();
	}

	private String toString(Pair<Integer, List<ResourceType>> playerResourcePair) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("Player: %d", playerResourcePair.getKey()));
		playerResourcePair.getValue().stream().forEach(resource -> sb.append("Received resource: " + resource));

		return sb.toString();
	}
}
