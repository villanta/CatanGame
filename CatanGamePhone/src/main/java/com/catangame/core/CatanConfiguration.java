package com.catangame.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.model.game.Player;

import javafx.scene.paint.Color;
import javafx.stage.Window;

public class CatanConfiguration {

	private static final Logger LOG = LogManager.getLogger(CatanConfiguration.class);

	private static final String APP_DATA_LOCATION = getLocalDir();
	private static final String PLAYER_CONFIG_FILE = APP_DATA_LOCATION + "/CatanGame/player.props";

	private static CatanConfiguration configuration;

	public static CatanConfiguration getInstance() {
		if (CatanConfiguration.configuration == null) {
			CatanConfiguration.configuration = new CatanConfiguration();
		}
		return configuration;
	}

	private static String getLocalDir() {
		String os = System.getenv().get("OS");
		if (os.toUpperCase().contains("WIN")) {
			return System.getenv("APPDATA") + "\\..\\Local";
		} else {
			return System.getenv("TEMP");
		}
	}

	public Optional<Player> loadPlayerDetails(Window window) {
		File f = new File(PLAYER_CONFIG_FILE);
		if (f.canWrite()) {
			return loadPlayerInfo(f);
		} else {
			return createAndSave(window, f);
		}
	}

	private Optional<Player> loadPlayerInfo(File f) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(f));
			String name = (String) props.get("playerName");
			String colorString = (String) props.get("playerColour");
			Color color = Color.valueOf(colorString);
			return Optional.of(new Player(0, name, color));
		} catch (IOException e) {
			LOG.error("Failed to write to file: " + f, e);
			return Optional.empty();
		}
	}

	private Optional<Player> createAndSave(Window window, File f) {
		if (createFile(f)) {
			Optional<Player> player = getPlayerUI(window);
			if (player.isPresent()) {
				writePlayer(player.get(), f);
			}
			return player;
		} else {
			return Optional.empty();
		}
	}

	private boolean createFile(File f) {
		try {
			LOG.info("Make dirs: " + f.getParentFile().mkdirs());
			LOG.info("Make file: " + f.createNewFile());
			return f.canWrite();
		} catch (IOException e) {
			LOG.error("Failed to create file: " + f, e);
			return false;
		}
	}

	private Optional<Player> getPlayerUI(Window parentWindow) {
		PlayerEntryDialog playerEntryDialog = new PlayerEntryDialog(parentWindow);
		playerEntryDialog.showAndWait();
		return playerEntryDialog.getPlayer();
	}

	private void writePlayer(Player player, File f) {
		Properties props = new Properties();
		props.put("playerName", player.getName());
		props.put("playerColour", player.getColor().toString());

		try (FileOutputStream fos = new FileOutputStream(f)) {
			props.store(fos, "Saved on: " + LocalDateTime.now().toString());
		} catch (IOException e) {
			LOG.error("Failed to write to file: " + f, e);
		}
	}
}
