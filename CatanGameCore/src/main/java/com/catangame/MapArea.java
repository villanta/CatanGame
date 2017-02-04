package com.catangame;

import java.util.ArrayList;
import java.util.List;

import com.catangame.model.GameHex;
import com.catangame.util.FXUtils;

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

	public MapArea() {
		super();

		initialiseFX();
	}

	private void initialiseFX() {
		initialiseCanvas();
		initialiseHexes();

		draw();
	}

	private void initialiseHexes() {
		hexes.addAll(HexGenerator.generate(3, 3, 3));
	}

	public void draw() {
		double xOffset = canvas.getWidth() / 2;
		double yOffset = canvas.getHeight() / 2;
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		
		hexes.stream().forEach(hex -> hex.draw(gc, RADIUS, xOffset, yOffset));

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
