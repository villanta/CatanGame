package com.catangame.model.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.catangame.MapGenerator;
import com.catangame.control.GameViewListener;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.model.tiles.GameHex;

import javafx.scene.paint.Color;

public class Game {

	private GameState gameState;



	private final List<Player> players = new ArrayList<>();
	private int playerWithTurn;

	private final List<GameHex> hexes = new ArrayList<>();
	private final List<Road> roads = new ArrayList<>();
	private final List<Building> buildings = new ArrayList<>();

	private final List<Building> availableBuildings = new ArrayList<>();
	private final List<Road> availableRoads = new ArrayList<>();

	private GameViewListener view;

	public Game() {
		this(Arrays.asList(new Player(0, "Player 1", Color.RED), new Player(1, "Player 2", Color.BLUE)));
		buildings.addAll(MapGenerator.generateBuildings(players, hexes));
		roads.addAll(MapGenerator.generateRoads(players, hexes, buildings));
	}

	public Game(List<Player> players) {
		this(players, MapGenerator.generateClassicBoard());
	}

	public Game(List<Player> players, List<GameHex> hexes) {
		this.players.addAll(players);
		this.hexes.addAll(hexes);
	}

	public void setViewListener(GameViewListener view) {
		this.view = view;
	}

	/**
	 * @return the allPlayers
	 */
	public List<Player> getAllPlayers() {
		return players;
	}

	/**
	 * @return the playerWithTurn
	 */
	public Player getPlayerWithTurn() {
		return players.get(playerWithTurn);
	}

	/**
	 * @param playerWithTurn
	 *            the playerWithTurn to set
	 */
	public void nextTurn() {
		this.playerWithTurn = playerWithTurn++ % players.size();
	}

	/**
	 * @return the hexes
	 */
	public List<GameHex> getHexes() {
		return hexes;
	}

	/**
	 * @return the roads
	 */
	public List<Road> getRoads() {
		return roads;
	}

	/**
	 * @return the buildings
	 */
	public List<Building> getBuildings() {
		return buildings;
	}

	/**
	 * @return the availableBuildings
	 */
	public List<Building> getAvailableBuildings() {
		return availableBuildings;
	}

	/**
	 * @return the availableRoads
	 */
	public List<Road> getAvailableRoads() {
		return availableRoads;
	}

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @param gameState
	 *            the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public void repopulateHexes(List<GameHex> hexes) {
		this.getHexes().clear();
		this.getHexes().addAll(hexes);
		notifyView();
	}

	public void repopulateBuildings(List<Building> buildings) {
		this.getBuildings().clear();
		this.getBuildings().addAll(buildings);
		notifyView();
	}

	public void repopulateRoads(List<Road> roads) {
		this.getRoads().clear();
		this.getRoads().addAll(roads);
		notifyView();
	}

	public void repopulateAvailableBuildings(List<Building> availableBuildings) {
		this.getAvailableBuildings().clear();
		this.getAvailableBuildings().addAll(availableBuildings);
		notifyView();
	}

	public void repopulateAvailableRoads(List<Road> roads) {
		this.getAvailableRoads().clear();
		this.getAvailableRoads().addAll(roads);
		notifyView();
	}

	private void notifyView() {
		if (view != null) {
			view.updateView();
		}
	}
}
