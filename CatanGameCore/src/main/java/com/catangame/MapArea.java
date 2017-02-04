package com.catangame;

import java.util.ArrayList;
import java.util.List;

import com.catangame.model.EdgeLocation;
import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.Road;
import com.catangame.model.VertexLocation;
import com.catangame.util.FXUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class MapArea extends AnchorPane {

	private static final double RADIUS = 100;

	private Canvas canvas;
	private List<GameHex> hexes = new ArrayList<>();
	private List<Road> roads = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();

	private CatanMouseListener mouseListener;

	private DoubleProperty radius = new SimpleDoubleProperty(RADIUS);

	public MapArea() {
		super();

		initialiseFX();
		initialiseMouseListener();
	}

	private void initialiseMouseListener() {
		mouseListener = new CatanMouseListener(this, canvas, hexes, roads, buildings, radius);
		
		canvas.setOnMouseMoved(mouseListener::onMouseMoved);
	}

	private void initialiseFX() {
		initialiseCanvas();
		initialiseHexes();
		initialiseRoads();
		initialiseBuildings();

		draw();
	}

	private void initialiseBuildings() {
		VertexLocation settlementLocation = new VertexLocation(new HexCoordinate(0, 0, 0), 0);
		VertexLocation cityLocation = new VertexLocation(new HexCoordinate(0, -1, 1), 0);

		Building settlement = new Settlement(settlementLocation, Color.RED);
		Building city = new City(cityLocation, Color.RED);
		buildings.add(settlement);
		buildings.add(city);
	}

	private void initialiseRoads() {
		VertexLocation start = new VertexLocation(new HexCoordinate(0, 0, 0), 0);
		VertexLocation middle = new VertexLocation(new HexCoordinate(0, 0, 0), 1);
		VertexLocation end = new VertexLocation(new HexCoordinate(0, -1, 1), 0);
		EdgeLocation location1 = new EdgeLocation(start, middle);
		EdgeLocation location2 = new EdgeLocation(middle, end);
		Road road1 = new Road(location1, Color.RED);
		Road road2 = new Road(location2, Color.RED);
		roads.add(road1);
		roads.add(road2);
	}

	private void initialiseHexes() {
		hexes.addAll(HexGenerator.generate(2, 2, 2));
	}

	public void draw() {
		double xOffset = canvas.getWidth() / 2;
		double yOffset = canvas.getHeight() / 2;

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.setStroke(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);

		hexes.stream().forEach(hex -> hex.draw(gc, radius.get(), xOffset, yOffset));
		roads.stream().forEach(road -> road.draw(gc, radius.get(), xOffset, yOffset));
		buildings.stream().forEach(building -> building.draw(gc, radius.get(), xOffset, yOffset));

		canvas.autosize();
	}

	private void initialiseCanvas() {
		canvas = new Canvas();
		FXUtils.setAllAnchors(canvas, 0.0);
		super.widthProperty().addListener((obsV, oldV, newV) -> canvas.setWidth(newV.doubleValue()));
		super.heightProperty().addListener((obsV, oldV, newV) -> canvas.setHeight(newV.doubleValue()));
		super.getChildren().add(canvas);
	}
}
