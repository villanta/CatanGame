package com.catangame.comms.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.register.KryoEnvironment;
import com.catangame.comms.server.MessageParser;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class CatanClient {

	private Map<Connection, Integer> map = new HashMap<>();
	private List<Connection> connections = new ArrayList<>();
	private Client client;

	public CatanClient() {
		client = new Client();
		client.setKeepAliveTCP(0);
		client.setTimeout(0);
		MessageParser listener = new MessageParser(connections, map);
		client.addListener(listener);
		KryoEnvironment.register(client.getKryo());

	}

	public boolean findServer() {
		InetAddress discoverHost = client.discoverHost(KryoEnvironment.DISCOVERY_PORT, 1000);
		System.err.println(discoverHost);

		if (discoverHost != null) {
			try {
				client.start();
				client.connect(3000, discoverHost.getHostAddress(), KryoEnvironment.GAME_PORT);
				System.err.println("Connected to: " + discoverHost.getHostAddress());
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	public void sendObject(Object o) {
		client.sendTCP(o);
	}
}
