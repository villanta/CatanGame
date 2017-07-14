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

	private static final int WHEAT_TILE_COUNT = 4;
	private static final int SHEEP_TILE_COUNT = 4;
	private static final int LUMBER_TILE_COUNT = 4;
	private static final int BRICK_TILE_COUNT = 3;
	private static final int ORE_TILE_COUNT = 3;

	private static final int WHEAT_PORT_COUNT = 1;
	private static final int SHEEP_PORT_COUNT = 1;
	private static final int LUMBER_PORT_COUNT = 1;
	private static final int BRICK_PORT_COUNT = 1;
	private static final int ORE_PORT_COUNT = 1;
	private static final int GENERIC_PORT_COUNT = 4;

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

		// generate center
		GameHex centerHex = new GameHex(new HexCoordinate(0, 0, 0), HexType.BARREN, 0);
		hexes.add(centerHex);

		int radius = 4;

		List<HexType> types = generateTypes();
		List<Integer> diceRolls = generateDiceRolls();
		List<ResourceType> portTypes = generatePortTypes();

		// Create hex field in a spiral from the center
		for (int r = 0; r < radius; r++) {

			List<HexCoordinate> coords = HexMath.getHexRing(centerHex.getCoordinate(), r);

			if (r == radius - 1) {
				// add water hexes
				for (int i = 0; i < coords.size(); i++) {
					if (i % 2 == 0) {
						// get valid port
						PortHex validPort = generateValidPortAt(coords.get(i), portTypes.remove(0), hexes);

						// place port
						hexes.add(validPort);
					} else {
						// place water
						hexes.add(new GameHex(coords.get(i), HexType.WATER, 0));
					}
				}
			} else {

				// add land hexes
				hexes.addAll(coords.stream().map(coord -> new GameHex(coord, types.remove(0), diceRolls.remove(0)))
						.collect(Collectors.toList()));
			}
		}

		hexes = hexes.stream().sorted(MapGenerator::compareHex).collect(Collectors.toList());

		return hexes;
	}

	private static PortHex generateValidPortAt(HexCoordinate coord, ResourceType type, List<GameHex> board) {
		List<HexCoordinate> neighbours = HexMath.getAllNeighbours(coord);

		List<Integer> validRotations = new ArrayList<>();

		for (int i = 0; i < neighbours.size(); i++) {
			if (hexExists(neighbours.get(i), board) && hexIsLand(neighbours.get(i), board)) {
				validRotations.add(i);
			}
		}
		
		int validRotation = validRotations.get(Math.abs(rand.nextInt() % validRotations.size()));

		return new PortHex(coord, type, validRotation);
	}

	/**
	 * check if tile exists on board and return its index
	 * 
	 * @param hex
	 *            coordinate of tile to check whether it exists
	 * @param board
	 *            to check
	 * @return index of existing hex on given board list or null
	 */
	private static boolean hexExists(HexCoordinate hex, List<GameHex> board) {
		// looking at all hexes to see if their location matches the provided
		// location.
		return board.stream().filter(gameHex -> gameHex.getCoordinate().equals(hex)).count() > 0;
	}

	private static boolean hexIsLand(HexCoordinate hex, List<GameHex> board) {
		return board.stream().filter(gameHex -> gameHex.getCoordinate().equals(hex) && gameHex.isLand()).count() > 0;
	}

	private static List<HexType> generateTypes() {
		List<HexType> types = new ArrayList<>();

		// add wheat
		for (int w = 0; w < WHEAT_TILE_COUNT; w++) {
			types.add(HexType.WHEAT);
		}
		for (int s = 0; s < SHEEP_TILE_COUNT; s++) {
			types.add(HexType.SHEEP);
		}
		for (int l = 0; l < LUMBER_TILE_COUNT; l++) {
			types.add(HexType.LUMBER);
		}
		for (int b = 0; b < BRICK_TILE_COUNT; b++) {
			types.add(HexType.BRICK);
		}
		for (int o = 0; o < ORE_TILE_COUNT; o++) {
			types.add(HexType.ORE);
		}

		return randomiseArray(types);
	}

	private static List<ResourceType> generatePortTypes() {
		List<ResourceType> types = new ArrayList<>();

		// add wheat
		for (int w = 0; w < WHEAT_PORT_COUNT; w++) {
			types.add(ResourceType.WHEAT);
		}
		for (int s = 0; s < SHEEP_PORT_COUNT; s++) {
			types.add(ResourceType.SHEEP);
		}
		for (int l = 0; l < LUMBER_PORT_COUNT; l++) {
			types.add(ResourceType.LUMBER);
		}
		for (int b = 0; b < BRICK_PORT_COUNT; b++) {
			types.add(ResourceType.BRICK);
		}
		for (int o = 0; o < ORE_PORT_COUNT; o++) {
			types.add(ResourceType.ORE);
		}
		for (int g = 0; g < GENERIC_PORT_COUNT; g++) {
			types.add(null);
		}

		return randomiseArray(types);
	}

	private static List<Integer> generateDiceRolls() {
		List<Integer> list = new ArrayList<>(
				Arrays.asList(2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 8, 8, 9, 9, 9, 10, 10, 11, 11, 11, 12, 12));

		return randomiseArray(list);
	}

	private static int compareHex(GameHex hex1, GameHex hex2) {
		if (hex1.getType() == HexType.WATER || hex1.getType() == HexType.BARREN) {
			return -1;
		} else if (hex2.getType() == HexType.WATER || hex2.getType() == HexType.BARREN) {
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

	private static <T> List<T> randomiseArray(List<T> list) {
		List<T> randomList = new ArrayList<>();

		while (!list.isEmpty()) {
			int i = rand.nextInt(list.size());
			randomList.add(list.remove(i));
		}
		return randomList;
	}
}
