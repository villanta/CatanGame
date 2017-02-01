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
	
	public MapArea() {
		super();
		initialiseFX();
	}

	private void initialiseFX() {
		canvas = new Canvas();
		FXUtils.setAllAnchors(canvas, 0.0);
		super.getChildren().add(canvas);
		
		HexCoordinate coord = new HexCoordinate(0, 0, 0);
		HexCoordinate coord2 = new HexCoordinate(1, 0, -1);
		
		Pair<double[], double[]> pair = HexMath.getAllCorners(coord, RADIUS);
		Pair<double[], double[]> pair2 = HexMath.getAllCorners(coord2, RADIUS);
		
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		gc.setStroke(Color.RED);
		gc.setFill(Color.RED);
		gc.fillPolygon(pair.getKey(), pair.getValue(), 6);
		gc.setFill(Color.GREEN);
		gc.fillPolygon(pair2.getKey(), pair2.getValue(), 6);
	}
}
