package com.catangame.comms.server;

import java.util.Random;

import com.catangame.comms.interfaces.GameService;
import com.catangame.comms.messages.game.DiceRollRequest;
import com.catangame.comms.messages.game.GameActionMessage;
import com.catangame.model.cards.DevelopmentCard;
import com.catangame.model.game.Game;
import com.catangame.model.locations.EdgeLocation;
import com.catangame.model.locations.HexLocation;
import com.catangame.model.locations.VertexLocation;
import com.catangame.model.structures.Settlement;

public class GameServer implements GameService {

	private CatanServer server;
	private Game game;
	private Random rndGenerator;

	public GameServer(CatanServer server) {
		this.server = server;
		rndGenerator = new Random(System.currentTimeMillis());
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
		if(gameActionMessage instanceof DiceRollRequest) {
			DiceRollRequest diceRollRequest = (DiceRollRequest) gameActionMessage;
			processDiceRoll(diceRollRequest);
		}
	}

	private void processDiceRoll(DiceRollRequest diceRollRequest) {
		//generate dice roll
		int diceRoll1 = rndGenerator.nextInt(6) + 1;
		int diceRoll2 = rndGenerator.nextInt(6) + 1;
		
		int diceRoll = diceRoll1 + diceRoll2;
		
		//process resources per player
		//find hexes
		//TODO group resources to players
		game.getBuildings().stream().forEach(building -> {
			building.getResourcesOnDiceRoll(diceRoll, game.getHexes());
		});
		//match players settlements/cities
		
		
		//generate response messages using calculated resources from above
		
		//send responsemessages to players (including host player)
		server.getChatService().sendMessage(null /* For Each Player*/, "" /* Message for each player */ );
	}

	@Override
	public void rollDice(int playerId) {
		// TODO Auto-generated method stub
		
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
