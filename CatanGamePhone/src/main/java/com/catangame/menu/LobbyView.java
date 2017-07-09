package com.catangame.menu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.util.FXUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LobbyView extends AnchorPane {

	private static final String FXML_LOCATION = "/com/catangame/view/LobbyView.fxml";
	
	private static final Logger LOG = LogManager.getLogger(LobbyView.class);

	// private Lobby lobby;

	public LobbyView() {
		loadFXML();
		initialiseFX();
	}

	private void loadFXML() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_LOCATION));
		try {
			AnchorPane pane = loader.load();
			FXUtils.setAllAnchors(pane, 0.0);
			super.getChildren().add(pane);
		} catch (IOException e) {
			LOG.error("Error loading fxml.", e);
		}
	}

	private void initialiseFX() {
		// TODO Auto-generated method stub

	}

}
