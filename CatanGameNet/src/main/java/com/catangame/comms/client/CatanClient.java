package com.catangame.comms.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.catangame.comms.kryo.KryoEnvironment;
import com.catangame.comms.kryo.ListenerInterface;
import com.catangame.comms.kryo.ListenerInterfaceWrapper;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class CatanClient {

	private Map<ListenerInterface, ThreadedListener> listenerMap = new HashMap<>();
	
	private Client client;

	public CatanClient() {
		Log.set(Log.LEVEL_TRACE);
		client = new Client();
		client.setKeepAliveTCP(0);
		client.setTimeout(0);
		client.start();
		KryoEnvironment.register(client.getKryo());
	}

	public List<InetAddress> findAllServers() {
		return client.discoverHosts(KryoEnvironment.DISCOVERY_PORT, 2000);
	}

	public void sendObject(Object o) {
		client.sendTCP(o);
	}

	public void connect(InetAddress server) throws IOException {
		client.start();
		client.connect(4000, server, KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
	}

	public void removeListener(ListenerInterface findLobbyView) {
		client.removeListener(listenerMap.get(findLobbyView));
	}

	public void addListener(ListenerInterface listenerInterface) {
		ThreadedListener listener = new ThreadedListener(new ListenerInterfaceWrapper(listenerInterface));
		listenerMap.put(listenerInterface, listener);
		client.addListener(listener);
	}

	public void disconnect() {
		client.stop();
	}

}