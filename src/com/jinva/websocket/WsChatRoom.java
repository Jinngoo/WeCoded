package com.jinva.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
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

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

@ServerEndpoint("/wsChatRoom")
public class WsChatRoom {

	private static Map<String, Session> sesMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private static Map<String, String> ses_user = Collections.synchronizedMap(new HashMap<String, String>());
	
	
	@OnOpen
	public void onOpen(Session session) {
	    System.out.println("WebSocket open");
		Map<String, List<String>> req = session.getRequestParameterMap();
		List<String> ids = req.get("id");
		String userId = ids.get(0);
		ses_user.put(session.getId(), userId);
	}

	@OnMessage
	public void onTextMessage(Session session, String msg, boolean last) throws IOException {
		Set<Session> set = session.getOpenSessions();
		for(Session ses : set){
//		    if(ses.equals(session)){
//		        continue;
//		    }
		    String userId = ses_user.get(ses.getId());
		    JSONObject json = new JSONObject();
		    json.put("id", userId);
		    json.put("name", "jinn");
		    json.put("text", msg);
		    send(ses, json);
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
		ses_user.remove(session.getId());
	}
	
	private void send(Session session, JSONObject json){
	    try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
