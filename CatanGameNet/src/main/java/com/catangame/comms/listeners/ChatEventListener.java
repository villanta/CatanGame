package com.catangame.comms.listeners;

@FunctionalInterface
public interface ChatEventListener {

	void newMessage(String message);
}
