package com.catangame.comms.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.comms.register.KryoEnvironment;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class CatanServer {

	private static final Logger LOG = LogManager.getLogger(CatanServer.class);

	private Map<Connection, Integer> map = new HashMap<>();
	private List<Connection> connections = new ArrayList<>();
	private Server server;

	public CatanServer() {
		server = new Server();

		KryoEnvironment.register(server.getKryo());
	}

	public boolean start() {
		server.start();
		try {
			server.bind(KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
			return true;
		} catch (IOException e) {
			LOG.error("Failed to initialise server.", e);
			return false;
		}
	}

	public void sendToAll(Object o) {
		server.sendToAllTCP(o);
	}

	public void addListener(ServerListenerInterface listenerInterface) {
		ListenerInterfaceWrapper wrapper = new ListenerInterfaceWrapper(listenerInterface);
		server.addListener(wrapper);
	}

	public void removeListener(ServerListenerInterface listenerInterface) {
		ListenerInterfaceWrapper wrapper = new ListenerInterfaceWrapper(listenerInterface);
		server.removeListener(wrapper);
	}

	public void sendTo(Connection connection, Object message) {
		server.sendToTCP(connection.getID(), message);
	}
}
