package com.catangame.main;

import java.util.List;

import com.catangame.comms.interfaces.CatanEndPoint;
import com.catangame.control.GameMouseListener.SelectionMode;
import com.catangame.interfaces.ClosableView;
import com.catangame.model.game.Game;
import com.catangame.model.game.Player;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.util.CatanUtils;
import com.catangame.util.FXUtils;
import com.catangame.view.CommandView;
import com.catangame.view.GameView;
import com.catangame.view.PlayerResourceView;
import com.catangame.view.interfaces.CommandViewListener;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GamePane extends AnchorPane implements ClosableView, CommandViewListener {

	private Player player;
	private CatanEndPoint endPoint;
	private GameView gameView;

	private DoubleProperty scale = new SimpleDoubleProperty(0);

	// resource box ui element
	private PlayerResourceView resourceBox;
	// command box ui element
	private CommandView buttonBox;

	public GamePane(Player player, Game game, CatanEndPoint endPoint) {
		this.player = player;
		this.endPoint = endPoint;
		gameView = new GameView(game, player);
		initialiseFX();
	}

	public void draw() {
		gameView.draw();
	}

	@Override
	public void onBuildSettlement() {
		List<Building> availableBuildings = CatanUtils.getAvailableSettlementLocations(gameView.getModel(), player);

		gameView.getModel().repopulateAvailableBuildings(availableBuildings);
		gameView.getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
	}

	@Override
	public void onBuildRoad() {
		List<Road> availableRoads = CatanUtils.getAvailableRoadLocations(gameView.getModel(), player);

		gameView.getModel().repopulateAvailableRoads(availableRoads);
		gameView.getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_ROAD);
	}

	@Override
	public void onClose() {
		endPoint.disconnect();
	}

	@Override
	public void onResize() {
		draw();
	}

	private void initialiseFX() {
		initialiseMap();
		gameView.start();
		initialiseButtonPanel();
		initialiseResourcePanel();
	}

	private void initialiseMap() {
		Pane pane = gameView.getMapView();
		FXUtils.setAllAnchors(pane, 0.0);
		super.getChildren().add(pane);
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