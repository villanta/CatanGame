package com.catangame.comms.messages.lobby.actions;

import com.catangame.Lobby;
import com.catangame.game.Player;

public class JoinLobbyResponse extends LobbyActionMessage {

	private boolean response;
	private Lobby lobby;
	private String reason;

	public JoinLobbyResponse() {
		// empty constructor for kryo
	}
	
	public JoinLobbyResponse(Player player, boolean response) {
		this(player, response, null);
		reason = "Rejected"; //TODO put proper reason 'code' eg. using enum
	}
	
	public JoinLobbyResponse(Player player, boolean response, Lobby lobby) {
		super(player, LobbyActionType.JOIN_LOBBY_RESPONSE);
		this.response = response;
		this.lobby = lobby;
	}

	/**
	 * @return the response
	 */
	public boolean isAccepted() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(boolean response) {
		this.response = response;
	}
	
	/**
	 * 
	 * @return lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}
	
	/**
	 * 
	 * @param lobby
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
