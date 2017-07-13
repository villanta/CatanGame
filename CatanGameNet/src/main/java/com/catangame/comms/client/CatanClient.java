package com.catangame.comms.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.catangame.comms.kryo.KryoEnvironment;
import com.catangame.comms.kryo.ListenerInterface;
import com.catangame.comms.kryo.ListenerInterfaceWrapper;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class CatanClient {

	private Client client;

	public CatanClient() {
		Log.set(Log.LEVEL_INFO);
		client = new Client();
		new Thread(client).start();
		KryoEnvironment.register(client.getKryo());
	}

	public List<InetAddress> findAllServers() {
		return client.discoverHosts(KryoEnvironment.DISCOVERY_PORT, 2000);		
	}

	public void sendObject(Object o) {
		client.sendTCP(o);
	}

	public void connect(InetAddress server) throws IOException {
		client.setKeepAliveTCP(0);
		client.setTimeout(0);
		client.connect(4000, server, KryoEnvironment.GAME_PORT, KryoEnvironment.DISCOVERY_PORT);
	}

	public void addListener(ListenerInterface listenerInterface) {
		client.addListener(new ThreadedListener(new ListenerInterfaceWrapper(listenerInterface)));
	}

	public void disconnect() {
		client.stop();
		client.close();
	}
}