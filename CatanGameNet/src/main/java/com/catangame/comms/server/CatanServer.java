package com.catangame.comms.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.register.KryoEnvironment;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class CatanServer {

	private Map<Connection, Integer> map = new HashMap<>();
	private List<Connection> connections = new ArrayList<>();

	public CatanServer() {
		Server server = new Server();
		MessageParser listener = new MessageParser(connections, map);
		server.addListener(listener);
		KryoEnvironment.register(server.getKryo());

		server.start();
		try {
			server.bind(KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
