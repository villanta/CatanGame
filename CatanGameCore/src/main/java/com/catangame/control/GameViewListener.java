package com.catangame.control;

/**
 * Interface for game view listener.
 * @author Jamie
 *
 */
public interface GameViewListener {
	
	/**
	 * Method called when view updated
	 */
	void updateView();

	/**
	 * Toggle menu
	 */
	void toggleMenu();

	/*
	 * Close game
	 */
	void closeGame();
}
