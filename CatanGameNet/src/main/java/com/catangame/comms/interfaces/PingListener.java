package com.catangame.comms.interfaces;

import com.catangame.comms.messages.lobby.PingMessage;

public interface PingListener {
	void updatePing(PingMessage pingMessage);
}
