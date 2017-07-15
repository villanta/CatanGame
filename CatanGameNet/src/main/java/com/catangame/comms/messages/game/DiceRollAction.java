package com.catangame.comms.messages.game;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.catangame.model.resources.ResourceType;

public class DiceRollAction extends GameActionMessage {

	private Map<Integer, List<ResourceType>> playerReceivedResources;
	private int rollValue;

	public DiceRollAction() {
		// no arg cons. for kryo
	}

	public DiceRollAction(int playerId, ActionType actionType, int rollValue,
			Map<Integer, List<ResourceType>> playerReceivedResources) {
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

	public void setPlayerReceivedResources(Map<Integer, List<ResourceType>> resources) {
		this.playerReceivedResources = resources;
	}

	public Set<Integer> getPlayersWhoReceivedResources() {
		return playerReceivedResources.keySet();
	}

	public List<ResourceType> getResourcesForPlayer(int playerId) {
		return playerReceivedResources.get(playerId);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("Dice roll action for roll: %d%n", rollValue));
		playerReceivedResources.keySet().stream().forEach(playerId -> sb.append(toString(playerId)));

		return sb.toString();
	}

	private String toString(int playerId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("Player: %d%n", playerId));
		getResourcesForPlayer(playerId).stream().forEach(resource -> sb.append("Received resource: " + resource + "\n"));

		return sb.toString();
	}
}
