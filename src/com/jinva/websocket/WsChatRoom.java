package com.jinva.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/wsChatRoom")
public class WsChatRoom {

	private static Map<String, Session> sesMap = Collections.synchronizedMap(new HashMap<String, Session>());
	
	@OnOpen
	public void onOpen(Session session) {
		Map<String, List<String>> req = session.getRequestParameterMap();
		List<String> ids = req.get("id");
//		String id = ids.get(0);

//		sesMap.put(id, session);
		session.getId();
		
		System.out.println("WebSocket open");
	}

	@OnMessage
	public void onTextMessage(Session session, String msg, boolean last) throws IOException {
		Set<Session> set = session.getOpenSessions();
		for(Session ses : set){
			ses.getBasicRemote().sendText("hehe", last);
		}
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
