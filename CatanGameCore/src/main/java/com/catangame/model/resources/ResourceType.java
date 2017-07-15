package com.catangame.model.resources;

import com.catangame.model.tiles.HexType;

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
	
	public static ResourceType getResourceTypeFromHexType(HexType hexType){
		switch(hexType) {
		case BRICK:
			return ResourceType.BRICK;
		case LUMBER:
			return ResourceType.LUMBER;
		case ORE:
			return ResourceType.ORE;
		case SHEEP:
			return ResourceType.SHEEP;
		case WHEAT:
			return ResourceType.WHEAT;
		case BARREN:
		case WATER:
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return name;
	}

	public Color getColor() {
		return color;
	}
}
