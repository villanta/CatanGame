package com.catangame.model;

import javafx.scene.paint.Color;

public enum ResourceType {
	LUMBER("Lumber", Color.DARKGREEN),
	BRICK("Brick", Color.DARKGOLDENROD),
	WHEAT("Wheat", Color.YELLOW),
	SHEEP("Sheep", Color.LAWNGREEN), 
	ORE("Ore", Color.DARKGREY);
	
	private String name;
	private Color color;

	private ResourceType(String name, Color color) {
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
