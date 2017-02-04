package com.catangame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catangame.model.GameHex;
import com.catangame.model.HexCoordinate;
import com.catangame.model.HexType;

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
				// we want 0,0,0 to be barren.
				if (x == 0 && y == 0) {
					hexes.add(new GameHex(new HexCoordinate(0, 0, 0), HexType.BARREN, generateRandomDiceRoll()));
				} else if (Math.abs(z) <= zRadius) {
					HexCoordinate coord = new HexCoordinate(x, y, z);
					GameHex hex = new GameHex(coord, generateRandomType(), generateRandomDiceRoll());
					hexes.add(hex);
				}
			}
		}
		return hexes;
	}

	private static int generateRandomDiceRoll() {
		int a = rand.nextInt(11);
		return a + 2;
	}

	private static HexType generateRandomType() {
		// barren is the last one, we dont want to generate a barren so we use -1
		int a = rand.nextInt(HexType.values().length-1);
		return HexType.values()[a];
	}
}
