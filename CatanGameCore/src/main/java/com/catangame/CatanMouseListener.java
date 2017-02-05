package com.catangame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.Road;
import com.catangame.util.HexMath;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class CatanMouseListener {

	private List<GameHex> hexes;
	private List<Road> roads;
	private List<Building> buildings;
	private DoubleProperty radius;
	private Canvas canvas;
	private GameHex selectedHex;
	private MapArea mapArea;

	public CatanMouseListener(MapArea mapArea, Canvas canvas, List<GameHex> hexes, List<Road> roads,
			List<Building> buildings, DoubleProperty radius) {
		this.mapArea = mapArea;
		this.canvas = canvas;
		this.hexes = hexes;
		this.roads = roads;
		this.buildings = buildings;
		this.radius = radius;
	}

	public void onScrollEvent(ScrollEvent event) {
		if (event.getDeltaY() > 0) {
			mapArea.zoomIn();
		} else {
			mapArea.zoomOut();
		}
	}

	public void onMouseMoved(MouseEvent event) {
		double xOffset = canvas.getWidth() / 2;
		double yOffset = canvas.getHeight() / 2;

		HexCoordinate coord = HexMath.getHexCoordFromPixel(event.getX() - xOffset, event.getY() - yOffset,
				radius.get());

		GameHex hex = findHexWithCoord(coord);

		if (selectedHex != hex) {
			if (selectedHex != null) {
				selectedHex.deselect();
				selectedHex = null;
			}
			if (hex != null) {
				selectedHex = hex;
				selectedHex.select();
			}
			mapArea.draw();
		}

	}

	private GameHex findHexWithCoord(HexCoordinate coord) {
		if (coord == null) {
			return null;
		}
		return hexes.stream().filter(hex -> coord.equals(hex.getCoordinate())).findFirst().orElse(null);
	}
}
