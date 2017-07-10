package com.catangame.comms.server;

import com.esotericsoftware.kryonet.Connection;

public interface ListenerInterface {

	public void connected (Connection connection);

	public void disconnected (Connection connection);

	public void received (Connection connection, Object object);

	public void idle (Connection connection);
}
