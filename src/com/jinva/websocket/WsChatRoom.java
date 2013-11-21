package com.jinva.websocket;

import java.nio.ByteBuffer;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/wsChatRoom")
public class WsChatRoom {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("WebSocket open");
	}

	@OnMessage
	public void onTextMessage(Session session, String msg, boolean last) {
		System.out.println("onTextMessage");
	}

	@OnMessage
	public void onBinaryMessage(Session session, ByteBuffer bb, boolean last) {
		System.out.println("onBinaryMessage");
	}

	@OnMessage
	public void onPongMessage(PongMessage pm) {
		System.out.println("onPongMessage");
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("WebSocket close");
	}

}
