package com.catangame.menu;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.Lobby;
import com.catangame.comms.interfaces.CatanEndPoint;
import com.catangame.comms.interfaces.LobbyService;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.server.CatanServer;
import com.catangame.game.Player;
import com.catangame.util.FXUtils;
import com.esotericsoftware.kryonet.Connection;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LobbyView extends AnchorPane implements LobbyEventListener {

	private static final String FXML_LOCATION = "/com/catangame/view/LobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(LobbyView.class);

	@FXML
	private AnchorPane chatPane;
	@FXML
	private TextField lobbyNameField;
	@FXML
	private Spinner<Integer> playerLimitSpinner;
	@FXML
	private CheckBox joinGameWhileActiveCheckbox;

	@FXML
	private Button startGameButton;
	@FXML
	private Button exitLobbyButton;
	
	@FXML
	private ListView<PlayerView> playerListView;

	private ChatView chatView;

	private Lobby lobby;

	private CatanEndPoint endPoint;

	private Player player;

	private boolean isHost;

	private LobbyService lobbyService;

	/**
	 * Constructor for player created lobby.
	 * 
	 * @param player
	 */
	public LobbyView(Player player) {
		this(new CatanServer(), new Lobby(), player, true);
	}

	/**
	 * Constructor for player joined lobby.
	 * 
	 * @param endPoint
	 * @param lobby
	 * @param player
	 */
	public LobbyView(CatanEndPoint endPoint, Lobby lobby, Player player) {
		this(endPoint, lobby, player, false);
	}

	public LobbyView(CatanEndPoint endPoint, Lobby lobby, Player player, boolean isHost) {
		this.endPoint = endPoint;
		this.lobby = lobby;
		this.player = player;
		this.isHost = isHost;
		lobbyService = endPoint.getLobbyService();
		lobbyService.setLobby(lobby);
		lobbyService.addListener(this);
		loadFXML();
		initialiseFX();
		refreshLobbyInfo();
	}

	@Override
	public void updatedLobbyInfo(LobbyInfoResponse lobbyInfoResponse, Connection connection) {
		// if client
		if (!isHost) {
			lobby = lobbyInfoResponse.getLobby();
			refreshLobbyInfo();
		}
	}

	@Override
	public void joinLobbyResponse(JoinLobbyResponse joinLobbyResponse, Connection connection) {
		// do nothing
	}

	@Override
	public void lobbyClosed() {
		Platform.runLater(() -> getScene().setRoot(new MainMenuPane()));
	}

	@FXML
	protected void exitLobbyButtonAction(ActionEvent event) {
		lobbyService.closeLobby(player);
		getScene().setRoot(new MainMenuPane());
		event.consume();
	}

	@FXML
	protected void joinGameWhileActiveCheckboxAction(ActionEvent event) {
		lobby.getGameRules().setJoinableWhileActive(joinGameWhileActiveCheckbox.isSelected());
		lobbyService.updateLobby(lobby);
		event.consume();
	}

	@FXML
	protected void startGameButtonAction(ActionEvent event) {
		LOG.info("Game started");
		event.consume();
	}

	private void refreshLobbyInfo() {
		this.lobby = endPoint.getLobbyService().getLobby();
		Platform.runLater(() -> {
			lobbyNameField.setText(lobby.getLobbyName());
			playerLimitSpinner.getValueFactory().setValue(lobby.getGameRules().getPlayerLimit());
			joinGameWhileActiveCheckbox.setSelected(lobby.getGameRules().isJoinableWhileActive());
			updatePlayerList(lobby.getPlayers());
		});
	}

	private void updatePlayerList(List<Player> players) {
		// TODO Auto-generated method stub

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
		initialiseChatView();
		playerLimitSpinner.setValueFactory(new IntegerSpinnerValueFactory(2, 6, 3));
		if (isHost) {
			playerLimitSpinner.valueProperty().addListener((obsV, oldV, newV) -> {
				lobby.getGameRules().setPlayerLimit(newV);
				lobbyService.updateLobby(lobby);
			});
			lobbyNameField.textProperty().addListener((obsV, oldV, newV) -> {
				lobby.setLobbyName(newV);
				lobbyService.updateLobby(lobby);
			});
		} else {
			playerLimitSpinner.setDisable(true);
			lobbyNameField.setDisable(true);
			joinGameWhileActiveCheckbox.setDisable(true);
		}
	}

	private void initialiseChatView() {
		chatView = new ChatView(endPoint.getChatService(), player);
		FXUtils.setAllAnchors(chatView, 0.0);
		chatPane.getChildren().add(chatView);
	}

}
