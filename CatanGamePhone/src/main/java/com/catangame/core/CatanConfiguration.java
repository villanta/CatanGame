package com.catangame.core;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.game.Player;

import javafx.stage.Stage;

public class CatanConfiguration {

	private static final Logger LOG = LogManager.getLogger(CatanConfiguration.class);

	private static final String PLAYER_CONFIG_FILE = "%APPDATA%/local/CatanGame/player.props";

	private static CatanConfiguration configuration;

	public static CatanConfiguration getInstance() {
		if (CatanConfiguration.configuration == null) {
			CatanConfiguration.configuration = new CatanConfiguration();
		}
		return configuration;
	}

	public Optional<Player> loadPlayerDetails(Stage parentWindow) {
		File f = new File(PLAYER_CONFIG_FILE);
		if (f.canRead()) {
			System.err.println("File can be read");
		} else {
			if (createFile(f)) {
				Optional<Player> player = getPlayerUI(parentWindow);
				if (player.isPresent()) {
					writePlayer(player.get(), f);	
				}					
				return player;
			} else {
				return Optional.empty();
			}			
		}
		return Optional.empty();
	}

	private boolean createFile(File f) {
		try {
			f.mkdirs();
			return f.createNewFile();
		} catch (IOException e) {
			LOG.error("Failed to create file: " + f, e);
			return false;
		}
	}

	private Optional<Player> getPlayerUI(Stage parentWindow) {
		PlayerEntryDialog playerEntryDialog = new PlayerEntryDialog(parentWindow);		
		playerEntryDialog.showAndWait();
		return playerEntryDialog.getPlayer();
	}

	private void writePlayer(Player player, File f) {
		// TODO Auto-generated method stub

	}
}
