package com.catangame.menu;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.comms.client.CatanClient;
import com.catangame.comms.messages.lobby.LobbyInfoMessage;
import com.catangame.comms.messages.lobby.LobbyInfoRequest;
import com.catangame.comms.server.ListenerInterface;
import com.catangame.comms.server.MessageParser;
import com.catangame.util.FXUtils;
import com.esotericsoftware.kryonet.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class FindLobbyView extends AnchorPane implements ListenerInterface{
	
	private static final String FXML_LOCATION = "/com/catangame/view/FindLobbyView.fxml";

	private static final Logger LOG = LogManager.getLogger(FindLobbyView.class);
	
    @FXML
    private ListView<LobbyInfoView> lobbyListView;

	private CatanClient client;

	private List<InetAddress> servers;

	private boolean awaitingMessage;

    public FindLobbyView() {
		loadFXML();
		initialiseFX();
	}
    
    private void initialiseFX() {
		client = new CatanClient();
		new Thread(() -> {
		servers = client.findAllServers();
		client.addListener(this);
		for(InetAddress server : servers) {
			try {
				awaitingMessage = true;
				client.connect(server);
				client.sendObject(new LobbyInfoRequest());
				System.err.println("Connecting");
				while(awaitingMessage) {
					Thread.sleep(50);
				}
				//client.disconnect();
			} catch (IOException | InterruptedException e) {
				LOG.error("Error while connecting Client.", e);
			}
			
		}
		}).start();
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

	@FXML
    void cancelAction(ActionEvent event) {

    }

	@Override
	public void connected(Connection connection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Connection connection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void received(Connection connection, Object object) {
		if(object instanceof LobbyInfoMessage){
			LobbyInfoMessage lobbyInfoMessage = (LobbyInfoMessage) object;
			LobbyInfoView lobbyInfoView = new LobbyInfoView(lobbyInfoMessage, connection);
			lobbyListView.getItems().add(lobbyInfoView);
			awaitingMessage = false;
			LOG.error("LobbyInfo Recieved: %s", lobbyInfoMessage.getLobby().getLobbyName());
		} else {
			LOG.error("Invalid Message recieved from server.");
		}
	}

	@Override
	public void idle(Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
