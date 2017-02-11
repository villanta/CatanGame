package com.catangame.comms.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.register.RegisterFactory;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class GameServer {

	private Map<Connection, Integer> map = new HashMap<>();
	private List<Connection> connections = new ArrayList<>();

	public GameServer() {
		Server server = new Server();
		GameListener listener = new GameListener(connections, map);
		server.addListener(listener);
		RegisterFactory.register(server.getKryo());
		
		server.start();
		try {
			server.bind(55666);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
