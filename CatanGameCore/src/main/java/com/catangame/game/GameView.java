package com.catangame.game;

import com.catangame.MapView;
import com.catangame.control.GameMouseListener;

public class GameView {

	private Game game;

	private MapView mapView;

	private GameMouseListener mouseListener;
	private Player player;

	public GameView(Game game) {
		this.game = game;
		mapView = new MapView(game.getHexes(), game.getRoads(), game.getBuildings(), game.getAvailableRoads(),
				game.getAvailableBuildings());
	}

	public void start() {
		mouseListener = new GameMouseListener(this, mapView.radiusProperty());
		mapView.setMouseListener(mouseListener);
	}

	public void draw() {
		mapView.draw();
	}

	public GameMouseListener getMouseListener() {
		return mouseListener;
	}

	/**
	 * @return the game
	 */
	public Game getModel() {
		return game;
	}

	/**
	 * @param game
	 *            the game to set
	 */
	public void setModel(Game game) {
		this.game = game;
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
	 * @return the mapView
	 */
	public MapView getMapView() {
		return mapView;
	}

	/**
	 * @param mapView
	 *            the mapView to set
	 */
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
}
