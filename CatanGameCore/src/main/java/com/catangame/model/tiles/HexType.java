package com.catangame.model.tiles;

import javafx.scene.paint.Color;

public enum HexType {
	LUMBER("Lumber", Color.DARKGREEN),
	BRICK("Brick", Color.DARKGOLDENROD),
	WHEAT("Wheat", Color.YELLOW),
	SHEEP("Sheep",Color.LAWNGREEN), 
	ORE("Ore",Color.DARKGREY), 
	WATER("Water", Color.LIGHTSKYBLUE), 
	BARREN("Barren", Color.ROSYBROWN);

	private String name;
	private Color color;

	private HexType(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	@Override
	public String toString() {
		return name;
	}

	public Color getColor() {
		return color;
	}
}
