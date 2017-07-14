package com.catangame.menu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.interfaces.CatanEndPoint;
import com.catangame.comms.server.CatanServer;
import com.catangame.game.Player;
import com.catangame.util.FXUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LobbyView extends AnchorPane  {

	private static final String FXML_LOCATION = "/com/catangame/view/LobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(LobbyView.class);

	@FXML
	private AnchorPane chatPane;
	
	private Lobby lobby;

	private ChatView chatView;

	private CatanEndPoint endPoint;

	private Player player;

	public LobbyView(Player player) {
		this(new CatanServer(), new Lobby(), player);
	}

	public LobbyView(CatanEndPoint endPoint, Lobby lobby, Player player) {
		this.endPoint = endPoint;
		this.lobby = lobby;
		this.player = player;
		endPoint.getLobbyService().setLobby(lobby);
		loadFXML();
		initialiseFX();		
	}

	private void loadFXML() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LOCATION));
		loader.setController(this);
		try {
			AnchorPane pane = loader.load();
			FXUtils.setAllAnchors(pane, 0.0);
			super.getChildren().add(pane);
		} catch (IOException e) {
			LOG.error("Error loading fxml.", e);
		}
	}

	private void initialiseFX() {
		chatView = new ChatView(endPoint.getChatService(), player);
		FXUtils.setAllAnchors(chatView, 0.0);
		chatPane.getChildren().add(chatView);
	}
}
