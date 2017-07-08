package com.catangame.control;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.catangame.game.GameView;
import com.catangame.game.Player;
import com.catangame.model.Drawable;
import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.model.structures.Settlement;
import com.catangame.util.CatanUtils;
import com.catangame.util.HexMath;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class GameMouseListener {

	public enum SelectionMode {
		HIGHLIGHT_EVERYTHING, SELECT_EXISTING_BUILDING, SELECT_POTENTIAL_BUILDING, SELECT_EXISTING_ROAD, SELECT_POTENTIAL_ROAD, SELECT_EXISTING_HEX;
	}

	private List<GameHex> hexes;
	private List<Road> roads;
	private List<Building> buildings;
	private List<Building> availableBuildings;
	private List<Road> availableRoads;

	private DoubleProperty radius;
	private Drawable selectedDrawable;
	private Point2D startDragLocation;
	private Point2D startOffset;

	private SelectionMode mode = SelectionMode.HIGHLIGHT_EVERYTHING;

	private Function<Drawable, Void> selectionUpdated;
	private GameView gameView;

	public GameMouseListener(GameView gameView, DoubleProperty radiusProperty) {
		this.gameView = gameView;
		this.hexes = gameView.getModel().getHexes();
		this.buildings = gameView.getModel().getBuildings();
		this.roads = gameView.getModel().getRoads();
		this.availableBuildings = gameView.getModel().getAvailableBuildings();
		this.availableRoads = gameView.getModel().getAvailableRoads();
		this.radius = radiusProperty;
	}

	public void onScrollEvent(ScrollEvent event) {
		if (event.getDeltaY() > 0) {
			gameView.getMapView().zoomIn();
		} else {
			gameView.getMapView().zoomOut();
		}
	}

	public void onMouseClicked(MouseEvent event) {
		if (selectedDrawable instanceof Settlement && SelectionMode.SELECT_POTENTIAL_BUILDING.equals(mode)) {
			Settlement settlement = (Settlement) selectedDrawable;
			placeSettlement(settlement);
			selectedDrawable = null;
			gameView.draw();
		} else if (selectedDrawable instanceof Road && SelectionMode.SELECT_POTENTIAL_ROAD.equals(mode)) {
			Road road = (Road) selectedDrawable;
			placeRoad(road);
			selectedDrawable = null;
			gameView.draw();
		}
		event.consume();
	}

	private void placeRoad(Road road) {
		Player player = road.getPlayer();

		player.getResources().payFor(Road.COST);
		roads.add(road);
		availableRoads.remove(road);
		road.deselect();

		if (player.getResources().canAfford(Road.COST)) {
			gameView.getModel().repopulateAvailableRoads(CatanUtils.getAvailableRoadLocations(gameView.getModel(), road.getPlayer()));
			setMode(SelectionMode.SELECT_POTENTIAL_ROAD);
		} else {
			setMode(SelectionMode.HIGHLIGHT_EVERYTHING);
		}
	}

	private void placeSettlement(Settlement settlement) {
		Player player = settlement.getPlayer();

		player.getResources().payFor(Settlement.COST);
		buildings.add(settlement);
		availableBuildings.remove(settlement);
		settlement.deselect();

		if (player.getResources().canAfford(Settlement.COST)) {
			gameView.getModel().repopulateAvailableBuildings(CatanUtils.getAvailableSettlementLocations(gameView.getModel(), settlement.getPlayer()));
			setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
		} else {
			setMode(SelectionMode.HIGHLIGHT_EVERYTHING);
		}
	}

	public void onMouseMoved(MouseEvent event) {
		double xOffset = gameView.getMapView().getTrueXOffset();
		double yOffset = gameView.getMapView().getTrueYOffset();

		Optional<Drawable> drawable = getSelectedDrawable(event.getSceneX(), event.getSceneY(), xOffset, yOffset);
		if (drawable.isPresent()) {
			if (selectedDrawable != null && selectedDrawable != drawable.get()) {
				selectedDrawable.deselect();
			}
			selectedDrawable = drawable.get();
			if (selectionUpdated != null) {
				selectionUpdated.apply(selectedDrawable);
			}
			selectedDrawable.select();
			gameView.draw();
		} else if (selectedDrawable != null) {
			selectedDrawable.deselect();
			selectedDrawable = null;
			gameView.draw();
		}
	}

	public void onMousePressed(MouseEvent event) {
		startDragLocation = new Point2D(event.getSceneX(), event.getSceneY());
		startOffset = gameView.getMapView().getOffset();
		event.consume();
	}

	public void onMouseDragged(MouseEvent event) {
		Point2D currentPosition = new Point2D(event.getSceneX(), event.getSceneY());
		gameView.getMapView().setOffset(currentPosition.subtract(startDragLocation).add(startOffset));

		event.consume();
	}

	public Optional<Drawable> getSelectedItem() {
		return Optional.of(selectedDrawable);
	}

	/**
	 * @return the mode
	 */
	public SelectionMode getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(SelectionMode mode) {
		this.mode = mode;
	}

	public void setOnSelectionUpdated(Function<Drawable, Void> onUpdate) {
		this.selectionUpdated = onUpdate;
	}

	public Function<Drawable, Void> getOnSelectionUpdated() {
		return this.selectionUpdated;
	}

	private Optional<Drawable> getSelectedDrawable(double x, double y, double xOffset, double yOffset) {
		Optional<Drawable> building = findBuildingAtCoordinate(x, y);
		if (building.isPresent()) {
			return building;
		}
		Optional<Drawable> road = findRoadAtCoordinate(x, y);
		if (road.isPresent()) {
			return road;
		}
		return findHexAtCoordinate(x, y, xOffset, yOffset);
	}

	private Optional<Drawable> findBuildingAtCoordinate(double x, double y) {
		if (SelectionMode.SELECT_POTENTIAL_BUILDING.equals(mode)) {
			return availableBuildings.stream().filter(building -> building.isBuildingSelected(x, y))
					.map(building -> (Drawable) building).findFirst();
		} else if (SelectionMode.HIGHLIGHT_EVERYTHING.equals(mode)
				|| SelectionMode.SELECT_EXISTING_BUILDING.equals(mode)) {
			return buildings.stream().filter(building -> building.isBuildingSelected(x, y))
					.map(building -> (Drawable) building).findFirst();
		} else {
			return Optional.empty();
		}
	}

	private Optional<Drawable> findRoadAtCoordinate(double x, double y) {
		if (SelectionMode.SELECT_POTENTIAL_ROAD.equals(mode)) {
			return availableRoads.stream().filter(road -> road.isRoadSelected(x, y)).map(road -> (Drawable) road)
					.findFirst();
		} else if (SelectionMode.HIGHLIGHT_EVERYTHING.equals(mode) || SelectionMode.SELECT_EXISTING_ROAD.equals(mode)) {
			return roads.stream().filter(road -> road.isRoadSelected(x, y)).map(road -> (Drawable) road).findFirst();
		} else {
			return Optional.empty();
		}
	}

	private Optional<Drawable> findHexAtCoordinate(double x, double y, double xOffset, double yOffset) {
		if (SelectionMode.HIGHLIGHT_EVERYTHING.equals(mode) || SelectionMode.SELECT_EXISTING_HEX.equals(mode)) {
			HexCoordinate coord = HexMath.getHexCoordFromPixel(x - xOffset, y - yOffset, radius.get());
			if (coord == null) {
				return Optional.empty();
			}
			return hexes.stream().filter(hex -> coord.equals(hex.getCoordinate())).map(hex -> (Drawable) hex)
					.findFirst();
		} else {
			return Optional.empty();
		}
	}
}
