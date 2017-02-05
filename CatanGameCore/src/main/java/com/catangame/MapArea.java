package com.catangame;

import java.util.ArrayList;
import java.util.List;

import com.catangame.model.GameHex;
import com.catangame.model.Road;
import com.catangame.util.FXUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class MapArea extends AnchorPane {

	private static final double RADIUS_DEFAULT = 100;

	private Canvas canvas;
	private List<GameHex> hexes = new ArrayList<>();
	private List<Road> roads = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();

	private DoubleProperty radius = new SimpleDoubleProperty(RADIUS_DEFAULT);

	private Point2D centerOffset = new Point2D(0, 0);

	public MapArea() {
		super();

		initialiseFX();
		initialiseMouseListener();
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

	private void initialiseMouseListener() {
		CatanMouseListener mouseListener = new CatanMouseListener(this, hexes, roads, buildings, radius);

		// for highlighting and tracking "selected" hexes/buildings/roads
		canvas.setOnMouseMoved(mouseListener::onMouseMoved);
		// for zooming
		canvas.setOnScroll(mouseListener::onScrollEvent);

		// for dragging the map around, pressed "starts" the drag and the the
		// other method moves it relative to the start.
		canvas.setOnMousePressed(mouseListener::onMousePressed);
		canvas.setOnMouseDragged(mouseListener::onMouseDragged);
	}

	private void initialiseFX() {
		initialiseCanvas();
		initialiseGame();

		draw();
	}

	private void initialiseGame() {
		hexes.addAll(MapGenerator.generateHexBoard(2, 2, 2));
		buildings.addAll(MapGenerator.generateBuildings(hexes));
		roads.addAll(MapGenerator.generateRoads(hexes, buildings));
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
