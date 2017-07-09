package com.catangame.comms.server;

import java.util.List;
import java.util.Map;

import com.catangame.comms.messages.game.GameActionMessage;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class MessageParser extends Listener {

	public List<Connection> connections;
	public Map<Connection, Integer> connectionPlayerIdMap;

	public MessageParser(List<Connection> connections, Map<Connection, Integer> map) {
		this.connections = connections;
		this.connectionPlayerIdMap = map;
	}

	@Override
	public void received(Connection connection, Object o) {
		if (o instanceof GameActionMessage) {
			System.err.println("Game message");
			GameActionMessage message = (GameActionMessage) o;

			System.err.println(message);
		} else {
			System.err.println("not game message");
		}
	}
}
