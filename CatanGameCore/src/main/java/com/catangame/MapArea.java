package com.catangame;

import com.catangame.model.HexCoordinate;
import com.catangame.util.FXUtils;
import com.catangame.util.HexMath;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class MapArea extends AnchorPane {

	private static final double RADIUS = 100;
	private Canvas canvas;
	private HexCoordinate coord;
	private HexCoordinate coord2;
	
	public MapArea() {
		super();
		
		initialiseFX();
		System.err.println(canvas);
	}

	private void initialiseFX() {
		canvas = new Canvas();
		FXUtils.setAllAnchors(canvas, 0.0);
		super.widthProperty().addListener((obsV, oldV, newV) -> canvas.setWidth(newV.doubleValue()));
		super.heightProperty().addListener((obsV, oldV, newV) -> canvas.setHeight(newV.doubleValue()));
		super.getChildren().add(canvas);
		
		coord = new HexCoordinate(0, 0, 0);
		coord2 = new HexCoordinate(1, 0, -1);
		
		draw();
	}

	public void draw() {
		double xoffset = canvas.getWidth() / 2;
		double yoffset = canvas.getHeight() / 2;
		
		Pair<double[], double[]> pair = HexMath.getAllCorners(coord, RADIUS, xoffset, yoffset);
		Pair<double[], double[]> pair2 = HexMath.getAllCorners(coord2, RADIUS, xoffset, yoffset);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setStroke(Color.RED);
		gc.setFill(Color.RED);
		gc.fillPolygon(pair.getKey(), pair.getValue(), 6);
		gc.setFill(Color.GREEN);
		gc.fillPolygon(pair2.getKey(), pair2.getValue(), 6);
		canvas.autosize();
	}
}
