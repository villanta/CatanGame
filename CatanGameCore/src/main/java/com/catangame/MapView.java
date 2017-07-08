package com.catangame;

import java.util.ArrayList;
import java.util.List;

import com.catangame.control.GameMouseListener;
import com.catangame.model.GameHex;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.util.FXUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class MapView extends AnchorPane {

	private static final double RADIUS_DEFAULT = 100;

	private Canvas canvas;

	private DoubleProperty radius = new SimpleDoubleProperty(RADIUS_DEFAULT);
	private Point2D centerOffset = new Point2D(0, 0);

	private List<GameHex> hexes = new ArrayList<>();
	private List<Road> roads = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();

	private List<Building> availableBuildings = new ArrayList<>();
	private List<Road> availableRoads = new ArrayList<>();

	public MapView(List<GameHex> hexes, List<Road> roads, List<Building> buildings, List<Road> availableRoads,
			List<Building> availableBuildings) {
		super();

		this.hexes = hexes;
		this.roads = roads;
		this.buildings = buildings;

		this.availableRoads = availableRoads;
		this.availableBuildings = availableBuildings;

		initialiseFX();
	}

	public double getTrueXOffset() {
		return centerOffset.getX() + (canvas.getWidth() / 2);
	}

	public double getTrueYOffset() {
		return centerOffset.getY() + (canvas.getHeight() / 2);
	}

	/**
	 * Redraws the canvas.
	 */
	public void draw() {
		double xOffset = getTrueXOffset();
		double yOffset = getTrueYOffset();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		hexes.stream().forEach(hex -> hex.draw(gc, radius.get(), xOffset, yOffset));
		roads.stream().forEach(road -> road.draw(gc, radius.get(), xOffset, yOffset));
		buildings.stream().forEach(building -> building.draw(gc, radius.get(), xOffset, yOffset));

		availableBuildings.stream().forEach(building -> {
			if (building.isSelected()) {
				building.draw(gc, radius.get(), xOffset, yOffset);
			} else {
				building.calculateShape(radius.get(), xOffset, yOffset);
			}
		});

		availableRoads.stream().forEach(road -> {
			if (road.isSelected()) {
				road.draw(gc, radius.get(), xOffset, yOffset);
			} else {
				road.calculateShape(radius.get(), xOffset, yOffset);
			}
		});

		canvas.autosize();
	}

	/**
	 * Zooms in, called when the mouse scroll wheel scrolls up
	 */
	public void zoomIn() {
		if (radius.get() < 200) {
			radius.set(radius.get() + 5);
		}
		if (radius.get() > 200) {
			radius.set(200);
		}
		draw();
	}

	/**
	 * Zooms out, called when the mouse scroll wheel scrolls down
	 */
	public void zoomOut() {
		if (radius.get() > 50) {
			radius.set(radius.get() - 5);
		}
		if (radius.get() < 50) {
			radius.set(50);
		}
		draw();
	}

	/**
	 * Set the offset that comes from user moving the map around.
	 * 
	 * @param offset
	 */
	public void setOffset(Point2D offset) {
		this.centerOffset = offset;
		draw();
	}

	/**
	 * Get the offset that comes from user moving the map around.
	 * 
	 * @return
	 */
	public Point2D getOffset() {
		return centerOffset;
	}

	public void setMouseListener(GameMouseListener listener) {
		// for highlighting and tracking "selected" hexes/buildings/roads
		canvas.setOnMouseMoved(listener::onMouseMoved);
		// for zooming
		canvas.setOnScroll(listener::onScrollEvent);

		// for dragging the map around, pressed "starts" the drag and the the
		// other method moves it relative to the start.
		canvas.setOnMousePressed(listener::onMousePressed);
		canvas.setOnMouseDragged(listener::onMouseDragged);

		canvas.setOnMouseClicked(listener::onMouseClicked);
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

	public void setAvailableBuildings(List<Building> availableBuildings) {
		this.availableBuildings.clear();
		this.availableBuildings.addAll(availableBuildings);
		draw();
	}

	public void setAvailableRoads(List<Road> availableRoads) {
		this.availableRoads.clear();
		this.availableRoads.addAll(availableRoads);
		draw();
	}

	public List<Building> getAvailableBuildings() {
		return availableBuildings;
	}

	public List<Road> getAvailableRoads() {
		return availableRoads;
	}

	public DoubleProperty radiusProperty() {
		return radius;
	}

	private void initialiseFX() {
		initialiseCanvas();

		draw();
	}

	private void initialiseCanvas() {
		canvas = new Canvas();
		FXUtils.setAllAnchors(canvas, 0.0);

		super.widthProperty().addListener((obsV, oldV, newV) -> canvas.setWidth(newV.doubleValue()));
		super.heightProperty().addListener((obsV, oldV, newV) -> canvas.setHeight(newV.doubleValue()));

		canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
		canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);

		super.getChildren().add(canvas);
	}

}