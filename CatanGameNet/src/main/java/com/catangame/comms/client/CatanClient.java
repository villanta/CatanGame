package com.catangame.comms.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.register.KryoEnvironment;
import com.catangame.comms.server.ListenerInterface;
import com.catangame.comms.server.ListenerInterfaceWrapper;
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
		KryoEnvironment.register(client.getKryo());
		
	}

	public List<InetAddress> findAllServers() {
		return client.discoverHosts(KryoEnvironment.DISCOVERY_PORT, 10000);		
	}

	public void sendObject(Object o) {
		client.sendTCP(o);
	}

	public void connect(InetAddress server) throws IOException {
		client.start();
		client.connect(5000, server, KryoEnvironment.GAME_PORT);
	}

	public void addListener(ListenerInterface listenerInterface) {
		client.addListener(new ListenerInterfaceWrapper(listenerInterface));
	}

	public void disconnect() {
		client.stop();
		client.close();
	}
}