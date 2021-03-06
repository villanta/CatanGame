package com.catangame;

import java.util.ArrayList;
import java.util.List;

import com.catangame.model.game.Game;
import com.catangame.model.game.GameRules;
import com.catangame.model.game.GameState;
import com.catangame.model.game.Player;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Game lobby
 * 
 * @author Stefan
 *
 */
public class Lobby {

	private StringProperty lobbyName = new SimpleStringProperty("");
	private List<Player> players = new ArrayList<>();

	private Game game;
	private GameRules gameRules;
	private GameState gameState;

	public Lobby() {
		// empty constructor for kryo
		gameRules = new GameRules();
	}

	public Lobby(String lobbyName, Game game, GameRules gameRules, GameState gameState) {
		super();
		this.lobbyName.set(lobbyName);
		this.game = game;
		this.gameRules = gameRules;
		this.gameState = gameState;
	}

	public final StringProperty lobbyNameProperty() {
		return this.lobbyName;
	}

	public final String getLobbyName() {
		return this.lobbyNameProperty().get();
	}

	public final void setLobbyName(final String lobbyName) {
		this.lobbyNameProperty().set(lobbyName);
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean addPlayer(Player player) {
		return players.add(player);
	}

	public boolean removePlayer(Player player) {
		return players.remove(player);
	}

	public void removePlayer(int playerIndex) {
		players.remove(playerIndex);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GameRules getGameRules() {
		return gameRules;
	}

	public void setGameRules(GameRules gameRules) {
		this.gameRules = gameRules;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public int getPlayerCount() {
		return players.size();
	}

	@Override
	public String toString() {
		return getLobbyName();
	}
}
