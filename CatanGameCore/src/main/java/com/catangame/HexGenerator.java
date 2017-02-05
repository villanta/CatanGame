package com.catangame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.HexType;
import com.catangame.model.PortHex;
import com.catangame.model.ResourceType;

public class HexGenerator {

	private static final Random rand = new Random();

	private HexGenerator() {
		// utility class
	}

	public static List<GameHex> generate(int xRadius, int yRadius, int zRadius) {
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

		hexes = hexes.stream().sorted(HexGenerator::compareHex).collect(Collectors.toList());

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
		return a + 2;
	}

	private static HexType generateRandomType() {
		// we want to exclude barrens and water from being generated randomly
		// since barren and water are the laster 2 items in the HexType enum, we can exclude them using -2
		int a = rand.nextInt(HexType.values().length - 2);
		return HexType.values()[a];
	}
}
