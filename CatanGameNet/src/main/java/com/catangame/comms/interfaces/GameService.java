package com.catangame.comms.interfaces;

import com.catangame.comms.messages.game.GameActionMessage;
import com.catangame.model.cards.DevelopmentCard;
import com.catangame.model.game.Game;
import com.catangame.model.locations.EdgeLocation;
import com.catangame.model.locations.HexLocation;
import com.catangame.model.locations.VertexLocation;
import com.catangame.model.structures.Settlement;

public interface GameService {

	void messageReceived(GameActionMessage gameActionMessage);
	
	Game getGame();
	void setGame(Game game);
	
	//Client
	void rollDice(int playerId);
	void drawCard();
	void playCard(DevelopmentCard card);
	void buildRoad(EdgeLocation location);
	void buildSettlement(VertexLocation location);
	void buildCity(Settlement settlement);
	void moveRobber(HexLocation location);
	//TODO trade function
	
}
