package com.catangame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.catangame.MapArea;
import com.catangame.MapGenerator;
import com.catangame.control.GameMouseListener;
import com.catangame.model.GameHex;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;

import javafx.scene.paint.Color;

public class Game {

	private MapArea area;

	private Player player;
	private final List<Player> players = new ArrayList<>();
	private int playerWithTurn;

	private final List<GameHex> hexes = new ArrayList<>();
	private final List<Road> roads = new ArrayList<>();
	private final List<Building> buildings = new ArrayList<>();

	private final List<Building> availableBuildings = new ArrayList<>();
	private final List<Road> availableRoads = new ArrayList<>();

	private GameMouseListener mouseListener;

	public Game() {
		//this(Arrays.asList(new Player(0, Color.RED), new Player(1, Color.BLUE)),
		//		MapGenerator.generateTestHexBoard(3, 3, 3));
		this(Arrays.asList(new Player(0, Color.RED), new Player(1, Color.BLUE)),
					MapGenerator.generateClassicBoard());
		buildings.addAll(MapGenerator.generateBuildings(players, hexes));
		roads.addAll(MapGenerator.generateRoads(players, hexes, buildings));
		area = new MapArea(hexes, roads, buildings, availableRoads, availableBuildings);
	}

	public Game(List<Player> players, List<GameHex> hexes) {
		this.players.addAll(players);
		this.hexes.addAll(hexes);
		area = new MapArea(hexes, roads, buildings, availableRoads, availableBuildings);
	}

	public void start() {
		mouseListener = new GameMouseListener(this, area.radiusProperty());
		area.setMouseListener(mouseListener);
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
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

	public void repopulateHexes(List<GameHex> hexes) {
		this.getHexes().clear();
		this.getHexes().addAll(hexes);
		draw();
	}

	public void repopulateBuildings(List<Building> buildings) {
		this.getBuildings().clear();
		this.getBuildings().addAll(buildings);
		draw();
	}

	public void repopulateRoads(List<Road> roads) {
		this.getRoads().clear();
		this.getRoads().addAll(roads);
		draw();
	}

	public void repopulateAvailableBuildings(List<Building> availableBuildings) {
		this.getAvailableBuildings().clear();
		this.getAvailableBuildings().addAll(availableBuildings);
		draw();
	}

	public void repopulateAvailableRoads(List<Road> roads) {
		this.getAvailableRoads().clear();
		this.getAvailableRoads().addAll(roads);
		draw();
	}

	public void draw() {
		area.draw();
	}

	public MapArea getView() {
		return area;
	}

	public GameMouseListener getMouseListener() {
		return mouseListener;
	}

}
