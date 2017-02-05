package com.catangame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.catangame.model.EdgeLocation;
import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.HexType;
import com.catangame.model.PortHex;
import com.catangame.model.ResourceType;
import com.catangame.model.Road;
import com.catangame.model.VertexLocation;

import javafx.scene.paint.Color;

public class MapGenerator {

	private static final Random rand = new Random();

	private MapGenerator() {
		// utility class
	}

	public static List<GameHex> generateHexBoard(int xRadius, int yRadius, int zRadius) {
		List<GameHex> hexes = new ArrayList<>();

		for (int x = -xRadius; x <= xRadius; x++) {
			for (int y = -yRadius; y <= yRadius; y++) {
				int z = -(x + y);
				if (Math.abs(z) <= zRadius) {
					// we want 0,0,0 to be barren.
					if (x == 0 && y == 0) {
						hexes.add(new GameHex(new HexCoordinate(0, 0, 0), HexType.BARREN, 0));
					} else if (Math.abs(x) == xRadius || Math.abs(y) == yRadius || Math.abs(z) == zRadius) {
						hexes.add(new PortHex(new HexCoordinate(x, y, z), ResourceType.SHEEP, 0));
					} else if (Math.abs(z) <= zRadius) {
						HexCoordinate coord = new HexCoordinate(x, y, z);
						GameHex hex = new GameHex(coord, generateRandomType(), generateRandomDiceRoll());
						hexes.add(hex);
					}
				}
			}
		}

		hexes = hexes.stream().sorted(MapGenerator::compareHex).collect(Collectors.toList());

		return hexes;
	}

	private static int compareHex(GameHex hex1, GameHex hex2) {
		if (hex1.getType() == HexType.WATER) {
			return -1;
		} else if (hex2.getType() == HexType.WATER) {
			return 1;
		} else if (hex1.getType() == HexType.BARREN) {
			return -1;
		} else if (hex2.getType() == HexType.BARREN) {
			return 1;
		} else {
			return 0;
		}
	}

	private static int generateRandomDiceRoll() {
		int a = rand.nextInt(11);
		return a == 5 ? generateRandomDiceRoll() : a + 2;

	}

	private static HexType generateRandomType() {
		// we want to exclude barrens and water from being generated randomly
		// since barren and water are the laster 2 items in the HexType enum, we
		// can exclude them using -2
		int a = rand.nextInt(HexType.values().length - 2);
		return HexType.values()[a];
	}

	public static List<Building> generateBuildings(List<GameHex> hexes) {
		List<Building> buildings = new ArrayList<>();
		VertexLocation settlementLocation = new VertexLocation(new HexCoordinate(0, 0, 0), 0);
		VertexLocation cityLocation = new VertexLocation(new HexCoordinate(0, -1, 1), 0);

		Building settlement = new Settlement(settlementLocation, Color.RED);
		Building city = new City(cityLocation, Color.RED);
		buildings.add(settlement);
		buildings.add(city);

		return buildings;
	}

	public static List<Road> generateRoads(List<GameHex> hexes, List<Building> buildings) {
		List<Road> roads = new ArrayList<>();
		VertexLocation index0 = new VertexLocation(new HexCoordinate(0, 0, 0), 0);
		VertexLocation index1 = new VertexLocation(new HexCoordinate(0, 0, 0), 1);
		VertexLocation index2 = new VertexLocation(new HexCoordinate(0, -1, 1), 0);
		VertexLocation index3 = new VertexLocation(new HexCoordinate(-1, 0, 1), 1);
		VertexLocation index4 = new VertexLocation(new HexCoordinate(-1, 0, 1), 0);
		VertexLocation index5 = new VertexLocation(new HexCoordinate(-1, 1, 0), 1);
		EdgeLocation location1 = new EdgeLocation(index0, index1);
		EdgeLocation location2 = new EdgeLocation(index1, index2);
		EdgeLocation location3 = new EdgeLocation(index2, index3);
		EdgeLocation location4 = new EdgeLocation(index3, index4);
		EdgeLocation location5 = new EdgeLocation(index4, index5);
		EdgeLocation location6 = new EdgeLocation(index5, index0);
		Road road1 = new Road(location1, Color.RED);
		Road road2 = new Road(location2, Color.RED);
		Road road3 = new Road(location3, Color.RED);
		Road road4 = new Road(location4, Color.RED);
		Road road5 = new Road(location5, Color.RED);
		Road road6 = new Road(location6, Color.RED);
		roads.add(road1);
		roads.add(road2);
		roads.add(road3);
		roads.add(road4);
		roads.add(road5);
		roads.add(road6);

		return roads;
	}
}
