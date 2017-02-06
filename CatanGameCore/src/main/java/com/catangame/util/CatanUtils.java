package com.catangame.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.catangame.Building;
import com.catangame.MapArea;
import com.catangame.Settlement;
import com.catangame.model.Player;
import com.catangame.model.Road;
import com.catangame.model.VertexLocation;

public class CatanUtils {

	private CatanUtils() {
		// private constructor
	}

	public static List<Building> getAvailableSettlementLocations(MapArea area, Player player) {
		List<Road> roads = area.getRoads();
		List<Building> availableBuildings = roads.stream().filter(road -> player.getId() == road.getPlayer().getId())
				.flatMap(road -> canASettlementBeBuiltHere(area, road, player)).collect(Collectors.toList());
		removeDuplicateBuildings(availableBuildings);
		return availableBuildings;
	}

	private static Stream<Building> canASettlementBeBuiltHere(MapArea area, Road road, Player player) {
		List<Building> buildings = new ArrayList<>();

		VertexLocation start = road.getLocation().getStart();
		VertexLocation end = road.getLocation().getStart();

		// if either of the nodes have a building then the road cant contain a
		// valid building.
		if (area.getBuildings().stream()
				.filter(building -> start.equals(building.getLocation()) || end.equals(building.getLocation()))
				.count() == 0) {

			List<VertexLocation> startAdjacentVertexes = getAdjacentVertexes(start);
			List<VertexLocation> endAdjacentVertexes = getAdjacentVertexes(start);

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
}