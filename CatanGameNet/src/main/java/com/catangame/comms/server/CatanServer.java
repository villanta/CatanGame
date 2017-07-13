package com.catangame.comms.server;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.catangame.comms.kryo.KryoEnvironment;
import com.catangame.comms.kryo.ListenerInterface;
import com.catangame.comms.kryo.ListenerInterfaceWrapper;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class CatanServer {

	private static final Logger LOG = LogManager.getLogger(CatanServer.class);

	private Server server;

	public CatanServer() {
		Log.set(Log.LEVEL_TRACE);
		server = new Server();
		KryoEnvironment.register(server.getKryo());
	}

	public boolean start() {
		try {
			server.bind(KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
			new Thread(server).start();
			return true;
		} catch (IOException e) {
			LOG.error("Failed to initialise server.", e);
			return false;
		}
	}

	public void sendToAll(Object o) {
		server.sendToAllTCP(o);
	}

	public void addListener(ListenerInterface listenerInterface) {
		ListenerInterfaceWrapper wrapper = new ListenerInterfaceWrapper(listenerInterface);
		server.addListener(new ThreadedListener(wrapper));
	}

	public void removeListener(ListenerInterface listenerInterface) {
		ListenerInterfaceWrapper wrapper = new ListenerInterfaceWrapper(listenerInterface);
		server.removeListener(wrapper);
	}

	public void sendTo(Connection connection, Object message) {
		server.sendToTCP(connection.getID(), message);
	}
}
