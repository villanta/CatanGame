package com.catangame.core;

import java.util.Optional;

import com.catangame.game.Player;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlayerEntryDialog extends Stage {

	private Stage parentWindow;
	private AnchorPane rootPane;

	public PlayerEntryDialog(Stage parentWindow) {
		super();
		this.parentWindow = parentWindow;
		initialiseFX();		
	}
	
	private void initialiseFX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAndWait() {
		
	}

	public Optional<Player> getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
