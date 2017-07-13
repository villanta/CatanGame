package com.catangame.comms.kryo;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ListenerInterfaceWrapper extends Listener {

	private ListenerInterface listenerInterface;

	public ListenerInterfaceWrapper(ListenerInterface listenerInterface) {
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
