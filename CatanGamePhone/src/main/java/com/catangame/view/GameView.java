package com.catangame.view;

import java.util.List;

import com.catangame.MapView;
import com.catangame.comms.interfaces.CatanEndPoint;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.StartGameMessage;
import com.catangame.control.GameKeyboardListener;
import com.catangame.control.GameMouseListener;
import com.catangame.control.GameMouseListener.SelectionMode;
import com.catangame.control.GameViewListener;
import com.catangame.interfaces.ClosableView;
import com.catangame.model.game.Game;
import com.catangame.model.game.Player;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.util.CatanUtils;
import com.catangame.util.FXUtils;
import com.catangame.view.interfaces.CommandViewListener;
import com.esotericsoftware.kryonet.Connection;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class GameView extends AnchorPane
		implements ClosableView, CommandViewListener, GameViewListener, GameViewInterface, LobbyEventListener {

	private Player player;
	private CatanEndPoint endPoint;

	private DoubleProperty scale = new SimpleDoubleProperty(0);

	// resource box ui element
	private PlayerResourceView resourceBox;
	// command box ui element
	private CommandView buttonBox;

	private Game game;

	private MapView mapView;
	private ChatView chatView;

	private GameMouseListener mouseListener;
	private GameKeyboardListener keyBoardListener;
	private GameMenuView gameMenu;
	private ToggleButton chatToggleButton;

	public GameView(Player player, Game game, CatanEndPoint endPoint) {
		this.player = player;
		this.endPoint = endPoint;
		this.game = game;
		mapView = new MapView(game.getHexes(), game.getRoads(), game.getBuildings(), game.getAvailableRoads(),
				game.getAvailableBuildings());
		game.setViewListener(this);
		initialiseFX();
		sceneProperty().addListener((obsV, oldV, newV) -> initialiseKeyboardListener(newV));

		endPoint.getLobbyService().addListener(this);
	}

	private void initialiseKeyboardListener(Scene newScene) {
		if (keyBoardListener == null) {
			keyBoardListener = new GameKeyboardListener(this);
			mapView.setKeyboardListener(keyBoardListener);
		}
		if (newScene != null) {
			newScene.setOnKeyPressed(event -> keyBoardListener.onKeyPressedEvent(event));
		}
	}

	public void start() {
		mouseListener = new GameMouseListener(game, this, mapView.radiusProperty());
		mapView.setMouseListener(mouseListener);
	}

	public void draw() {
		mapView.draw();
	}

	@Override
	public void onBuildSettlement() {
		List<Building> availableBuildings = CatanUtils.getAvailableSettlementLocations(getModel(), player);

		getModel().repopulateAvailableBuildings(availableBuildings);
		getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
	}

	@Override
	public void onBuildRoad() {
		List<Road> availableRoads = CatanUtils.getAvailableRoadLocations(getModel(), player);

		getModel().repopulateAvailableRoads(availableRoads);
		getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_ROAD);
	}

	@Override
	public void onClose() {
		endPoint.disconnect();
	}

	@Override
	public void onResize() {
		draw();
	}

	@Override
	public void closeGame() {
		endPoint.getLobbyService().removeListener(this);
		endPoint.disconnect();
		MainMenuPane view = new MainMenuPane();
		getScene().setRoot(view);
	}

	public GameMouseListener getMouseListener() {
		return mouseListener;
	}

	/**
	 * @return the game
	 */
	public Game getModel() {
		return game;
	}

	/**
	 * @param game
	 *            the game to set
	 */
	public void setModel(Game game) {
		this.game = game;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the mapView
	 */
	public MapView getMapView() {
		return mapView;
	}

	/**
	 * @param mapView
	 *            the mapView to set
	 */
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	@Override
	public void updateView() {
		draw();
	}

	@Override
	public void toggleMenu() {
		if (gameMenu == null) {
			initialiseGameMenu();
		}
		gameMenu.toggle();
	}

	@Override
	public void updatedLobbyInfo(LobbyInfoResponse lobbyInfoResponse, Connection connection) {
		// do nothing TODO?
	}

	@Override
	public void joinLobbyResponse(JoinLobbyResponse joinLobbyResponse, Connection connection) {
		// do nothing TODO?
	}

	@Override
	public void lobbyClosed() {
		endPoint.getLobbyService().removeListener(this);
		endPoint.disconnect();
		MainMenuPane view = new MainMenuPane();
		getScene().setRoot(view);
	}

	@Override
	public void gameStarted(StartGameMessage startGameMessage) {
		// do nothing TODO?
	}

	private void initialiseGameMenu() {
		gameMenu = new GameMenuView(getScene(), this);
		gameMenu.setOnKeyPressed(event -> keyBoardListener.onKeyPressedEvent(event));
	}

	@Override
	public void zoomOut() {
		mapView.zoomOut();
	}

	@Override
	public void zoomIn() {
		mapView.zoomIn();
	}

	@Override
	public double getTrueXOffset() {
		return mapView.getTrueXOffset();
	}

	@Override
	public double getTrueYOffset() {
		return mapView.getTrueYOffset();
	}

	@Override
	public Point2D getOffset() {
		return mapView.getOffset();
	}

	@Override
	public void setOffset(Point2D offset) {
		mapView.setOffset(offset);
	}

	private void initialiseFX() {
		initialiseMap();
		start();
		initialiseButtonPanel();
		initialiseResourcePanel();
		initialiseChatView();
	}

	private void initialiseChatView() {
		initialiseChatButton();
		initialiseChat();
	}

	private void initialiseChat() {
		chatView = new ChatView(endPoint.getChatService(), player);
		chatView.setBottomOffsetProperty(chatToggleButton.heightProperty());
		chatView.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, new Insets(-10.0))));
		chatView.maxHeightProperty().bind(this.heightProperty().divide(3));

		AnchorPane.setBottomAnchor(chatView, 20.0);
		AnchorPane.setRightAnchor(chatView, 20.0);
	}

	private void initialiseChatButton() {
		chatToggleButton = new ToggleButton("Chat");
		chatToggleButton.selectedProperty().addListener((obsV, oldV, newV) -> {
			if (newV) {				
				chatView.popUp(getChildren());
			} else {
				chatView.popDown(getChildren());
			}
		});
		AnchorPane.setBottomAnchor(chatToggleButton, 10.0);
		AnchorPane.setRightAnchor(chatToggleButton, 10.0);
		getChildren().add(chatToggleButton);
	}

	private void initialiseMap() {
		FXUtils.setAllAnchors(mapView, 0.0);
		super.getChildren().add(mapView);
	}

	private void initialiseResourcePanel() {
		resourceBox = new PlayerResourceView(player);
		resourceBox.update();
		super.getChildren().add(resourceBox);
	}

	private void initialiseButtonPanel() {
		buttonBox = new CommandView(player, scale);
		buttonBox.addCommandListener(this);
		super.getChildren().add(buttonBox);
	}

}