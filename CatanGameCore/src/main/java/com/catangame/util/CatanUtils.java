package com.catangame.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.catangame.MapArea;
import com.catangame.game.Player;
import com.catangame.model.EdgeLocation;
import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.HexType;
import com.catangame.model.VertexLocation;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.model.structures.Settlement;

public class CatanUtils {

	private CatanUtils() {
		// private constructor
	}

	public static List<Building> getAvailableSettlementLocations(MapArea area, Player player) {
		// get all roads
		List<Road> roads = area.getRoads();
		// for each road, for only the roads owned by the required player, get
		// any settlements that can be built on those roads.
		List<Building> availableBuildings = roads.stream().filter(road -> player.getId() == road.getPlayer().getId())
				.flatMap(road -> canASettlementBeBuiltHere(area, road, player)).collect(Collectors.toList());

		// remove duplicates
		removeDuplicateBuildings(availableBuildings);
		return availableBuildings;
	}

	public static List<Road> getAvailableRoadLocations(MapArea area, Player player) {
		// get all roads owned by the player
		List<Road> roads = area.getRoads().stream().filter(road -> player.getId() == road.getPlayer().getId())
				.collect(Collectors.toList());

		List<Building> buildings = area.getBuildings().stream()
				.filter(building -> player.getId() == building.getPlayer().getId()).collect(Collectors.toList());

		// for each road, determine roads that can be built
		List<Road> availableRoads = getAvailableRoadsFromRoadsAndBuildings(player, roads, buildings);

		// remove roads that are on water
		removeWaterRoads(availableRoads, area.getHexes());

		// remove duplicates
		removeDuplicateRoads(availableRoads);

		// remove existing roads
		availableRoads = availableRoads.stream().filter(road -> !doesRoadExist(road, area.getRoads()))
				.collect(Collectors.toList());

		return availableRoads;
	}

	private static List<Road> getAvailableRoadsFromRoadsAndBuildings(Player player, List<Road> roads,
			List<Building> buildings) {
		List<Road> roadsFromBuildings = buildings.stream().map(building -> building.getLocation())
				.flatMap(vertex -> getRoadsFromVertex(vertex, player)).collect(Collectors.toList());

		List<Road> roadsFromRoads = roads.stream().flatMap(road -> getVertexes(road))
				.flatMap(vertex -> getRoadsFromVertex(vertex, player)).collect(Collectors.toList());

		roadsFromBuildings.addAll(roadsFromRoads);
		return roadsFromBuildings;
	}

	private static void removeWaterRoads(List<Road> availableRoads, List<GameHex> hexes) {
		List<Road> validRoads = availableRoads.stream().filter(road -> isRoadValid(road, hexes))
				.collect(Collectors.toList());
		availableRoads.clear();
		availableRoads.addAll(validRoads);
	}

	private static boolean isRoadValid(Road road, List<GameHex> hexes) {
		List<GameHex> adjacentHexes = getAdjacentHexes(road, hexes);

		return adjacentHexes.stream().filter(hex -> hex.getType().equals(HexType.WATER)).count() < 2;
	}

	private static List<GameHex> getAdjacentHexes(Road road, List<GameHex> hexes) {
		VertexLocation start = road.getLocation().getStart();
		VertexLocation end = road.getLocation().getEnd();

		List<HexCoordinate> adjacentCoords = new ArrayList<>();
		List<GameHex> adjacentHexes = new ArrayList<>();

		int dx = end.getReferenceHex().getX() - start.getReferenceHex().getX();
		int dy = end.getReferenceHex().getY() - start.getReferenceHex().getY();
		int dz = end.getReferenceHex().getZ() - start.getReferenceHex().getZ();

		if (start.getVertexIndex() == 0) {
			// if road is up
			if (dy == 1 && dz == -1) {
				adjacentCoords.add(start.getReferenceHex().deriveHex(0, 1, -1));
				adjacentCoords.add(start.getReferenceHex().deriveHex(1, 0, -1));
			} else if (dx == -1 && dy == 1) { // if road is left
				adjacentCoords.add(start.getReferenceHex());
				adjacentCoords.add(start.getReferenceHex().deriveHex(0, 1, -1));
			} else { // if road is right
				adjacentCoords.add(start.getReferenceHex());
				adjacentCoords.add(start.getReferenceHex().deriveHex(1, 0, -1));
			}
		} else { // else vertex == 1
			// if road is down
			if (dy == -1 && dz == 1) {
				adjacentCoords.add(start.getReferenceHex());
				adjacentCoords.add(start.getReferenceHex().deriveHex(1, -1, 0));
			} else if (dx == 1 && dy == -1) { // road is right
				adjacentCoords.add(start.getReferenceHex().deriveHex(1, -1, 0));
				adjacentCoords.add(start.getReferenceHex().deriveHex(1, 0, -1));
			} else { // road is left
				adjacentCoords.add(start.getReferenceHex());
				adjacentCoords.add(start.getReferenceHex().deriveHex(1, 0, -1));
			}
		}

		for (HexCoordinate adjacentCoord : adjacentCoords) {
			Optional<GameHex> opHex = hexes.stream().filter(hex -> hex.getCoordinate().equals(adjacentCoord))
					.findFirst();
			if (opHex.isPresent()) {
				adjacentHexes.add(opHex.get());
			}
		}
		return adjacentHexes;
	}

	private static boolean doesRoadExist(Road roadTest, List<Road> roads) {
		return roads.stream().filter(road -> road.getLocation().equals(roadTest.getLocation())).count() > 0;
	}

	private static Stream<Road> getRoadsFromVertex(VertexLocation start, Player player) {
		List<VertexLocation> vertexes = getAdjacentVertexes(start);

		return vertexes.stream().map(vertex -> new EdgeLocation(start, vertex)).map(edge -> new Road(edge, player));
	}

	private static Stream<VertexLocation> getVertexes(Road road) {
		return Arrays.asList(road.getLocation().getStart(), road.getLocation().getEnd()).stream();
	}

	private static Stream<Building> canASettlementBeBuiltHere(MapArea area, Road road, Player player) {
		List<Building> buildings = new ArrayList<>();

		VertexLocation start = road.getLocation().getStart();
		VertexLocation end = road.getLocation().getEnd();

		// if either of the nodes have a building then the road cant contain a
		// valid building.
		if (area.getBuildings().stream()
				.filter(building -> start.equals(building.getLocation()) || end.equals(building.getLocation()))
				.count() == 0) {

			List<VertexLocation> startAdjacentVertexes = getAdjacentVertexes(start);
			List<VertexLocation> endAdjacentVertexes = getAdjacentVertexes(end);

			boolean canBuildOnStart = startAdjacentVertexes.stream()
					.filter(vertex -> doesBuildingExistOnVertex(area.getBuildings(), vertex)).count() == 0;
			boolean canBuildOnEnd = endAdjacentVertexes.stream()
					.filter(vertex -> doesBuildingExistOnVertex(area.getBuildings(), vertex)).count() == 0;

			if (canBuildOnStart) {
				buildings.add(new Settlement(start, player));
			}
			if (canBuildOnEnd) {
				buildings.add(new Settlement(end, player));
			}
		}

		return buildings.stream();
	}

	private static boolean doesBuildingExistOnVertex(List<Building> buildings, VertexLocation vertex) {
		return buildings.stream().filter(building -> building.getLocation().equals(vertex)).count() > 0;
	}

	private static List<VertexLocation> getAdjacentVertexes(VertexLocation vertex) {
		List<VertexLocation> adjacentVertexes = new ArrayList<>();
		if (vertex.getVertexIndex() == 0) {
			adjacentVertexes.add(new VertexLocation(vertex.getReferenceHex(), 1));
			adjacentVertexes.add(new VertexLocation(vertex.getReferenceHex().deriveHex(0, 1, -1), 1));
			adjacentVertexes.add(new VertexLocation(vertex.getReferenceHex().deriveHex(-1, 1, 0), 1));
		} else {
			adjacentVertexes.add(new VertexLocation(vertex.getReferenceHex(), 0));
			adjacentVertexes.add(new VertexLocation(vertex.getReferenceHex().deriveHex(1, -1, 0), 0));
			adjacentVertexes.add(new VertexLocation(vertex.getReferenceHex().deriveHex(0, -1, 1), 0));
		}
		removeDuplicateVertexes(adjacentVertexes);
		return adjacentVertexes;
	}

	private static void removeDuplicateVertexes(List<VertexLocation> vertexes) {
		List<VertexLocation> duplicateFreeList = new ArrayList<>();

		for (VertexLocation location : vertexes) {
			if (duplicateFreeList.stream().filter(entry -> entry.equals(location)).count() == 0) {
				duplicateFreeList.add(location);
			}
		}

		vertexes.clear();
		vertexes.addAll(duplicateFreeList);
	}

	private static void removeDuplicateBuildings(List<Building> buildings) {
		List<Building> duplicateFreeList = new ArrayList<>();

		for (Building building : buildings) {
			if (duplicateFreeList.stream().filter(entry -> entry.getLocation().equals(building.getLocation()))
					.count() == 0) {
				duplicateFreeList.add(building);
			}
		}

		buildings.clear();
		buildings.addAll(duplicateFreeList);
	}

	private static void removeDuplicateRoads(List<Road> roads) {
		List<Road> duplicateFreeList = new ArrayList<>();

		for (Road road : roads) {
			if (duplicateFreeList.stream().filter(entry -> entry.getLocation().equals(road.getLocation()))
					.count() == 0) {
				duplicateFreeList.add(road);
			}
		}

		roads.clear();
		roads.addAll(duplicateFreeList);
	}
}