package com.catangame.main;

import java.util.List;

import com.catangame.MapView;
import com.catangame.comms.interfaces.CatanEndPoint;
import com.catangame.control.GameMouseListener;
import com.catangame.control.GameMouseListener.SelectionMode;
import com.catangame.control.GameViewListener;
import com.catangame.interfaces.ClosableView;
import com.catangame.model.game.Game;
import com.catangame.model.game.Player;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.util.CatanUtils;
import com.catangame.util.FXUtils;
import com.catangame.view.CommandView;
import com.catangame.view.GameViewInterface;
import com.catangame.view.PlayerResourceView;
import com.catangame.view.interfaces.CommandViewListener;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

public class GameView extends AnchorPane
		implements ClosableView, CommandViewListener, GameViewListener, GameViewInterface {

	private Player player;
	private CatanEndPoint endPoint;

	private DoubleProperty scale = new SimpleDoubleProperty(0);

	// resource box ui element
	private PlayerResourceView resourceBox;
	// command box ui element
	private CommandView buttonBox;

	private Game game;

	private MapView mapView;

	private GameMouseListener mouseListener;

	public GameView(Player player, Game game, CatanEndPoint endPoint) {
		this.player = player;
		this.endPoint = endPoint;
		this.game = game;
		mapView = new MapView(game.getHexes(), game.getRoads(), game.getBuildings(), game.getAvailableRoads(),
				game.getAvailableBuildings());
		game.setViewListener(this);
		initialiseFX();
	}

	public void start() {
		mouseListener = new GameMouseListener(game, this, mapView.radiusProperty());
		mapView.setMouseListener(mouseListener);
	}

	public void draw() {
		mapView.draw();
	}

	@Override
	public void onBuildSettlement() {
		List<Building> availableBuildings = CatanUtils.getAvailableSettlementLocations(getModel(), player);

		getModel().repopulateAvailableBuildings(availableBuildings);
		getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
	}

	@Override
	public void onBuildRoad() {
		List<Road> availableRoads = CatanUtils.getAvailableRoadLocations(getModel(), player);

		getModel().repopulateAvailableRoads(availableRoads);
		getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_ROAD);
	}

	@Override
	public void onClose() {
		endPoint.disconnect();
	}

	@Override
	public void onResize() {
		draw();
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

	@Override
	public void zoomOut() {
		mapView.zoomOut();
	}

	@Override
	public void zoomIn() {
		mapView.zoomIn();
	}

	@Override
	public double getTrueXOffset() {
		return mapView.getTrueXOffset();
	}

	@Override
	public double getTrueYOffset() {
		return mapView.getTrueYOffset();
	}

	@Override
	public Point2D getOffset() {
		return mapView.getOffset();
	}

	@Override
	public void setOffset(Point2D offset) {
		mapView.setOffset(offset);
	}

	private void initialiseFX() {
		initialiseMap();
		start();
		initialiseButtonPanel();
		initialiseResourcePanel();
	}

	private void initialiseMap() {
		FXUtils.setAllAnchors(mapView, 0.0);
		super.getChildren().add(mapView);
	}

	private void initialiseResourcePanel() {
		resourceBox = new PlayerResourceView(player);
		resourceBox.update();
		super.getChildren().add(resourceBox);
	}

	private void initialiseButtonPanel() {
		buttonBox = new CommandView(player, scale);
		buttonBox.addCommandListener(this);
		super.getChildren().add(buttonBox);
	}
}