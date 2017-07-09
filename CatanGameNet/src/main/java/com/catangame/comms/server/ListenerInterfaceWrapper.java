package com.catangame.comms.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ListenerInterfaceWrapper extends Listener {

	private ServerListenerInterface listenerInterface;

	public ListenerInterfaceWrapper(ServerListenerInterface listenerInterface) {
		this.listenerInterface = listenerInterface;
	}
	
	@Override
	public void connected (Connection connection) {
		listenerInterface.connected(connection);
	}

	@Override
	public void disconnected (Connection connection) {
		listenerInterface.disconnected(connection);
	}

	@Override
	public void received (Connection connection, Object object) {
		listenerInterface.received(connection, object);
	}

	@Override
	public void idle (Connection connection) {
		listenerInterface.idle(connection);
	}

}
