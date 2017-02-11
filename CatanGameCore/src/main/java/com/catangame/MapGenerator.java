package com.catangame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.catangame.game.Player;
import com.catangame.game.ResourceType;
import com.catangame.model.EdgeLocation;
import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.HexType;
import com.catangame.model.PortHex;
import com.catangame.model.VertexLocation;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.City;
import com.catangame.model.structures.Road;
import com.catangame.model.structures.Settlement;
import com.catangame.util.HexMath;

public class MapGenerator {
	
	private static final Random rand = new Random();
	
	private static final int wheatCount = 4;
	private static final int sheepCount = 4;
	private static final int lumberCount  = 4;
	private static final int brickCount = 3;
	private static final int oreCount   = 3;
	private static final int barrenCount = 1;
	
	private static final int wheatPortCount   = 1;
	private static final int sheepPortCount   = 1;
	private static final int woodPortCount    = 1;
	private static final int brickPortCount   = 1;
	private static final int orePortCount     = 1;
	private static final int genericPortCount = 3;
	
	
	private MapGenerator() {
		// utility class
	}	
	
	public static List<GameHex> generateTestHexBoard(int xRadius, int yRadius, int zRadius) {
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
	
	public static List<GameHex> generateClassicBoard() {
		List<GameHex> hexes = new ArrayList<>();
		
		//generate center
		GameHex centerHex = new GameHex(new HexCoordinate(0, 0, 0), HexType.BARREN, 0);
		hexes.add(centerHex);
		
		int radius = 4;

		List<HexType> types = generateTypes();
		List<Integer> diceRolls = generateDiceRolls();
		
		// Create hex field in a spiral from the center
		for(int r = 0; r < radius; r++) {
			
			List<HexCoordinate> coords = HexMath.getHexRing(centerHex.getCoordinate(), r);
			
			if(r == radius - 1) {
				//add water hexes
				hexes.addAll(coords.stream().map(coord -> new GameHex(coord, HexType.WATER, 0)).collect(Collectors.toList()));
			} else {
				//add land hexes
				hexes.addAll(coords.stream().map(coord -> new GameHex(coord, types.remove(0), diceRolls.remove(0))).collect(Collectors.toList()));
			}
		}
		
		return hexes;
	}
	
	private static List<HexType> generateTypes() {
		List<HexType> types = new ArrayList<>();
		
		// add wheat
		for(int w = 0; w < wheatCount; w ++) {
			types.add(HexType.WHEAT);
		}
		for(int s = 0; s < sheepCount; s ++) {
			types.add(HexType.SHEEP);
		}
		for(int l = 0; l < lumberCount; l ++) {
			types.add(HexType.LUMBER);
		}
		for(int b = 0; b < brickCount; b ++) {
			types.add(HexType.BRICK);
		}
		for(int o = 0; o < oreCount; o ++) {
			types.add(HexType.ORE);
		}
		
		return types;
	}
	
	private static List<Integer> generateDiceRolls() {
		return Arrays.asList(2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6,
							 8, 8, 9, 9, 9, 10, 10, 11, 11, 11, 12, 12);
		
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

	public static List<Building> generateBuildings(List<Player> allPlayers, List<GameHex> hexes) {
		List<Building> buildings = new ArrayList<>();
		VertexLocation settlementLocation = new VertexLocation(new HexCoordinate(0, 0, 0), 0);
		VertexLocation cityLocation = new VertexLocation(new HexCoordinate(0, -1, 1), 0);

		Building settlement = new Settlement(settlementLocation, allPlayers.get(0));
		Building city = new City(cityLocation, allPlayers.get(1));
		buildings.add(settlement);
		buildings.add(city);

		return buildings;
	}

	public static List<Road> generateRoads(List<Player> allPlayers, List<GameHex> hexes, List<Building> buildings) {
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
		Road road1 = new Road(location1, allPlayers.get(0));
		Road road2 = new Road(location2, allPlayers.get(0));
		Road road3 = new Road(location3, allPlayers.get(0));
		Road road4 = new Road(location4, allPlayers.get(0));
		Road road5 = new Road(location5, allPlayers.get(0));
		Road road6 = new Road(location6, allPlayers.get(0));
		roads.add(road1);
		roads.add(road2);
		roads.add(road3);
		roads.add(road4);
		roads.add(road5);
		roads.add(road6);
		
		return roads;
	}
}
