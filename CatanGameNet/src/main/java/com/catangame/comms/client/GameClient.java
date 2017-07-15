package com.catangame.comms.client;

import com.catangame.comms.interfaces.GameService;
import com.catangame.comms.messages.game.DiceRollRequest;
import com.catangame.comms.messages.game.GameActionMessage;
import com.catangame.model.cards.DevelopmentCard;
import com.catangame.model.game.Game;
import com.catangame.model.locations.EdgeLocation;
import com.catangame.model.locations.HexLocation;
import com.catangame.model.locations.VertexLocation;
import com.catangame.model.structures.Settlement;

public class GameClient implements GameService {
	
	private CatanClient client;
	private Game game;

	public GameClient(CatanClient client) {
		this.client = client;
	}
	
	@Override
	public Game getGame() {
		return game;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void messageReceived(GameActionMessage gameActionMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void rollDice(int playerId) {
		DiceRollRequest request = new DiceRollRequest(playerId);
		client.sendObject(request);
	}

	@Override
	public void drawCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playCard(DevelopmentCard card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildRoad(EdgeLocation location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildSettlement(VertexLocation location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildCity(Settlement settlement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRobber(HexLocation location) {
		// TODO Auto-generated method stub
		
	}

}
