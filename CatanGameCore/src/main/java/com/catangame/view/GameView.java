package com.catangame.view;

import com.catangame.MapView;
import com.catangame.control.GameMouseListener;
import com.catangame.control.GameViewListener;
import com.catangame.model.game.Game;
import com.catangame.model.game.Player;

public class GameView implements GameViewListener {

	private Game game;

	private MapView mapView;

	private GameMouseListener mouseListener;
	private Player player;

	public GameView(Game game, Player player) {
		this.game = game;
		this.player = player;
		mapView = new MapView(game.getHexes(), game.getRoads(), game.getBuildings(), game.getAvailableRoads(),
				game.getAvailableBuildings());
		game.setViewListener(this);
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

	@Override
	public void updateView() {
		draw();
	}
}
