package com.catangame.comms.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.server.GameListener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class GameClient {

	private Map<Connection, Integer> map = new HashMap<>();
	private List<Connection> connections = new ArrayList<>();
	private Client client;

	public GameClient() {
		client = new Client();
		GameListener listener = new GameListener(connections, map);
		client.addListener(listener);
		
		client.start();
		try {
			client.connect(5000, "192.168.0.8", 55666);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object o) {
		client.sendTCP(o);
	}
}
